import java.util.ArrayList;
import java.util.List;

public class User extends UserTemplate {
    private double weight;
    private double height;
    private List<User> friends = new ArrayList<>();
    // private Exercise favoriteExercise; (when Excercise class will be available)
    // private List<Training> trainingHistory = new ArrayList<>(); (when Training class will be available)
    // private List<Post> posts = new ArrayList<>(); (when Post class will be available)

    // empty constructor
    public User() {}

    // basic constructor without lists
    public User(
            int id,
            String username,
            String name,
            String surname,
            String email,
            String password,
            double weight,
            double height
    ) {
        super(id, username, name, surname, email, password);
        this.weight = weight;
        this.height = height;
    }

    /* soon
    public User(
            int id,
            String username,
            String name,
            String surname,
            String email,
            String password,
            double weight,
            double height,
            List<User> friends,
            Exercise favoriteExercise,
            List<Training> trainingHistory,
            List<Post> posts
    ) {
        super(id, username, name, surname, email, password);
        this.weight = weight;
        this.height = height;
    }
    */

    // getters
    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public List<User> getFriends() {
        return friends;
    }
    /*
    public Exercise getFavoriteExercise() {
        return favouriteExcercise;
    }

    public List<Training> getTrainingHistory {
        return trainingHistory;
    }

    public List<Post> getPosts() {
        return posts;
    }
    */

    // setters
    public void setWeight(double newWeight) {
        this.weight = newWeight;
    }

    public void setHeight(double newHeight) {
        this.height = newHeight;
    }

    public void setFriends(List<User> newFriends) {
        this.friends = newFriends;
    }

    /*
    public void setFavoriteExercise(Exercise newFavouriteExercise) {
        this.favouriteExercise = newFavouriteExercise
    }

    public void setTrainingHistory(List<Training> newTrainingHistory) {
        this.trainingHistory = newTrainingHistory
    }

    public void setPosts(List<Post> newPosts) {
        this.posts = newPosts;
    }
    */
}
