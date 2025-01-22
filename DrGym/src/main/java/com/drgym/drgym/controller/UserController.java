package com.drgym.drgym.controller;

import com.drgym.drgym.model.Activity;
import com.drgym.drgym.model.Exercise;
import com.drgym.drgym.model.User;
import com.drgym.drgym.model.Workout;
import com.drgym.drgym.service.ExerciseService;
import com.drgym.drgym.service.UserService;
import com.drgym.drgym.service.WorkoutService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Clob;
import java.time.LocalDateTime;
import java.util.*;
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
        return user.map(u -> {
            String exerciseName = null;
            if (u.getFavoriteExercise() != null) {
                exerciseName = exerciseService.findById(u.getFavoriteExercise()).map(Exercise::getName).orElse(null);
            }
            return ResponseEntity.ok(new UserDTO(u.getUsername(), u.getName(), u.getSurname(), u.getWeight(), u.getHeight(), u.getFavoriteExercise(), exerciseName, u.getAvatar()));
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/{search}")
    public ResponseEntity<?> getUserSearch(@PathVariable String search) {
        List<User> users = userService.findBySearch(search);
        List<UserSearch> usernames = users.stream()
                .map(user -> new UserSearch(user.getUsername(), user.getAvatar()))
                .toList();
        return ResponseEntity.ok(usernames);
    }

    private record UserSearch(String username, String avatar) {}

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username, HttpServletRequest request) {
        if (!tokenOwner(username, request)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser, HttpServletRequest request) {
        String tokenUsername = getUsernameFromToken(request);
        if (isTokenExpired(request) || !tokenUsername.equals(updatedUser.getUsername())) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }
        return userService.updateUser(tokenUsername, updatedUser)
                .map(user -> ResponseEntity.ok("User updated successfully"))
                .orElse(ResponseEntity.notFound().build());
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

        List<Workout> futureWorkouts = new ArrayList<>();
        List<Workout> pastWorkouts = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (Workout workout : workouts) {
            List<Activity> activities = workoutService.findActivitiesByWorkoutId(workout.getId());
            workout.setActivities(activities);
            if (workout.getStartDate().isAfter(now)) {
                futureWorkouts.add(workout);
            } else {
                pastWorkouts.add(workout);
            }
        }

        Map<String, List<Workout>> response = new HashMap<>();
        response.put("futureWorkouts", futureWorkouts);
        response.put("pastWorkouts", pastWorkouts);

        return ResponseEntity.ok(response);
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

    @GetMapping("/{username}/ranking")
    public ResponseEntity<?> getExerciseRanking(
            @PathVariable String username,
            @RequestParam int exerciseId,
            HttpServletRequest request) {

        if (!tokenOwner(username, request)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }

        try {
            Clob rankingJson = exerciseService.getExerciseRanking(username, exerciseId);
            if (rankingJson == null || rankingJson.length() == 0) {
                return ResponseEntity.ok("[]");
            }
            String rankingJsonString = rankingJson.getSubString(1, (int) rankingJson.length());
            return ResponseEntity.ok(rankingJsonString);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("ERROR while fetching exercise ranking.");
        }
    }

    @GetMapping("/{username}/daily-exercise-count")
    public ResponseEntity<?> getUserDailyExerciseCount(
            @PathVariable String username,
            @RequestParam String startDate,
            @RequestParam String endDate,
            HttpServletRequest request) {

        if (!tokenOwnerOrFriend(username, request)) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Unauthorized");
        }

        try {
            Clob exercisesJson = exerciseService.getUserDailyExerciseCount(username, startDate, endDate);
            if (exercisesJson == null || exercisesJson.length() == 0) {
                return ResponseEntity.ok("[]");
            }
            String exercisesJsonString = exercisesJson.getSubString(1, (int) exercisesJson.length());
            return ResponseEntity.ok(exercisesJsonString);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("ERROR while fetching daily exercise count.");
        }
    }

    private record UserDTO(String username, String name, String surname, Double weight, Double height, Long favoriteExercise, String favoriteExerciseName, String avatar) {}

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

    public String getUsernameFromToken(HttpServletRequest request) {
        String jwtToken = getJwtTokenFromCookie(request);
        Claims claims = validateToken(jwtToken);
        return claims != null ? claims.getSubject() : null;
    }
}
