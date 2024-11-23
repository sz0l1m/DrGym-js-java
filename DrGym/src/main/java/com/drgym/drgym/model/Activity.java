//author: Michał Pędziwiatr


public class Activity {
    private Exercise exercise;

    public Activity(Exercise exercise) {
        this.exercise = exercise;
    }

    public Exercise getExercise(){
        return exercise;
    }

    public void setExercise(Exercise newExercise){
        exercise = newExercise;
    }

    @Override
    public String toString(){
        String output = exercise.toString();
        return output;
    }
}