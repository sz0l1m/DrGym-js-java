package com.drgym.drgym.controller;

import com.drgym.drgym.model.Activity;
import com.drgym.drgym.model.User;
import com.drgym.drgym.model.Workout;
import com.drgym.drgym.model.Post;
import com.drgym.drgym.model.PostReaction;
import com.drgym.drgym.service.ExerciseService;
import com.drgym.drgym.service.UserService;
import com.drgym.drgym.service.WorkoutService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.drgym.drgym.service.PostService;
import com.drgym.drgym.service.PostReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.sql.Clob;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.security.Key;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostReactionService postReactionService;

    private final Key SECRET_KEY;

    @Autowired
    public UserController(Key jwtSecretKey) {
        this.SECRET_KEY = jwtSecretKey;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username, HttpServletRequest request) {
        if (!tokenOwnerOrFriend(username, request)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }

        Optional<User> user = userService.findByUsername(username);
        return user.map(u -> ResponseEntity.ok(new UserDTO(u.getUsername(), u.getName(), u.getSurname(), u.getWeight(), u.getHeight())))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/{search}")
    public ResponseEntity<?> getUserSearch(@PathVariable String search) {
        List<User> users = userService.findBySearch(search);
        List<String> usernames = users.stream()
                .map(User::getUsername)
                .toList();
        return ResponseEntity.ok(usernames);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}/workouts")
    public ResponseEntity<?> getWorkoutsForUser(@PathVariable String username, HttpServletRequest request) {
        if (!tokenOwnerOrFriend(username, request)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }

        List<Workout> workouts = workoutService.findByUsername(username);
        if (workouts.isEmpty()) {
            return ResponseEntity.ok("[]");
        }

        List<WorkoutResponse> workoutResponses = workouts.stream()
                .map(workout -> {
                    List<Activity> activities = workoutService.findActivitiesByWorkoutId(workout.getId());

                    return new WorkoutResponse(
                            workout.getId(),
                            workout.getStartDate(),
                            workout.getUsername(),
                            workout.getEndDate(),
                            workout.getDescription(),
                            activities.stream()
                                    .map(a -> {
                                        String exerciseName = exerciseService.findById(a.getExerciseId())
                                                .map(exercise -> exercise.getName())
                                                .orElse("Unknown Exercise");

                                        return new ActivityResponse(
                                                a.getId(),
                                                a.getExerciseId(),
                                                exerciseName,
                                                a.getDuration(),
                                                a.getWeight(),
                                                a.getReps()
                                        );
                                    })
                                    .toList()
                    );
                })
                .toList();

        return ResponseEntity.ok(workoutResponses);
    }

    @GetMapping("/{username}/exercises")
    public ResponseEntity<?> getUserExercisesInPeriod(
            @PathVariable String username,
            @RequestParam String startDate,
            @RequestParam String endDate,
            HttpServletRequest request) {

        if (!tokenOwnerOrFriend(username, request)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }

        try {
            Clob exercisesJson = exerciseService.getExercisesForUserInPeriod(username, startDate, endDate);
            if (exercisesJson == null || exercisesJson.length() == 0) {
                return ResponseEntity.ok("[]");
            }
            String exercisesJsonString = exercisesJson.getSubString(1, (int) exercisesJson.length());
            return ResponseEntity.ok(exercisesJsonString);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("ERROR while fetching exercises.");
        }
    }

    @GetMapping("/{username}/daily-exercise-count")
    public ResponseEntity<?> getUserDailyExerciseCount(
            @PathVariable String username,
            HttpServletRequest request) {

        if (!tokenOwnerOrFriend(username, request)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }

        try {
            Clob exercisesJson = exerciseService.getUserDailyExerciseCount(username);
            if (exercisesJson == null || exercisesJson.length() == 0) {
                return ResponseEntity.ok("[]");
            }
            String exercisesJsonString = exercisesJson.getSubString(1, (int) exercisesJson.length());
            return ResponseEntity.ok(exercisesJsonString);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("ERROR while fetching daily exercise count.");
        }
    }

    @PostMapping("/{username}/posts")
    public ResponseEntity<?> createPost(@PathVariable String username, @RequestBody String content) {
        Post createdPost = postService.createPost(username, content);
        return ResponseEntity.ok(createdPost);
    }

    @DeleteMapping("/{username}/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable String username, @PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}/posts")
    public ResponseEntity<?> getUserPosts(@PathVariable String username) {
        List<Post> posts = postService.findPostsByUsername(username);
        if (posts.isEmpty()) {
            return ResponseEntity.ok("[]");
        }
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{username}/posts/{postId}/reactions")
    public ResponseEntity<?> getReactionsForPost(@PathVariable String username, @PathVariable Long postId) {
        List<PostReaction> reactions = postReactionService.findByPostId(postId);
        if (reactions.isEmpty()) {
            return ResponseEntity.ok("[]");
        }
        return ResponseEntity.ok(reactions);
    }

    public record WorkoutResponse(
            Long workoutId,
            LocalDateTime startDate,
            String username,
            LocalDateTime endDate,
            String description,
            List<ActivityResponse> activities
    ) {}

    public record ActivityResponse(
            Long activityId,
            Long exerciseId,
            String exerciseName,
            LocalTime duration,
            Long weight,
            Long reps
    ) {
        public ActivityResponse(
                Long activityId,
                Long exerciseId,
                String exerciseName,
                Timestamp duration,
                Long weight,
                Long reps
        ) {
            this(
                    activityId,
                    exerciseId,
                    exerciseName,
                    (duration != null) ? duration.toLocalDateTime().toLocalTime() : null,
                    weight,
                    reps
            );
        }
    }

    private record UserDTO(String username, String name, String surname, Double weight, Double height) {}

    private String getJwtTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean tokenOwnerOrFriend(String username, HttpServletRequest request) {
        String jwtToken = getJwtTokenFromCookie(request);
        if (jwtToken == null) {
            return false;
        }

        Claims claims = validateToken(jwtToken);
        if (claims == null || (!claims.getSubject().equals(username) && !userService.areUsersFriends(claims.getSubject(), username))) {
            return false;
        }

        return true;
    }

    public boolean tokenOwner(String username, HttpServletRequest request) {
        String jwtToken = getJwtTokenFromCookie(request);
        if (jwtToken == null) {
            return false;
        }

        Claims claims = validateToken(jwtToken);
        return claims != null && claims.getSubject().equals(username);
    }

    public boolean isTokenExpired(HttpServletRequest request) {
        String jwtToken = getJwtTokenFromCookie(request);
        if (jwtToken == null) {
            return true;
        }

        Claims claims = validateToken(jwtToken);
        if (claims == null) {
            return true;
        }

        return claims.getExpiration().before(new Date());
    }
}
