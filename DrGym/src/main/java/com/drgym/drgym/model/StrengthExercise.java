//author: Michał Pędziwiatr

import java.util.ArrayList;


public class StrengthExercise extends Exercise {
    private int repetitions;
    private int weight;
    private String weightUnit;

    public StrengthExercise(String name, ArrayList<String> musclesWorked, int repetitions, int weight, String weightUnit){
        super(name, musclesWorked);
        this.repetitions = repetitions;
        this.weight = weight;
        this.weightUnit = weightUnit;
    }

    public StrengthExercise(String name, ArrayList<String> musclesWorked, int repetitions, int weight){
        this(name, musclesWorked, repetitions, weight, "kg");
    }

    public int getRepetitions(){
        return repetitions;
    }

    public void setRepetitions(int newRepetitions) {
        this.repetitions = newRepetitions;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getWeightUnit(){
        return weightUnit;
    }

    public void setWeightUnit(String newWeightUnit){
        weightUnit = newWeightUnit;
    }

    @Override
    public String toString(){
        String output = super.toString() + ",\n Repetitions: " + repetitions + ", Weight: " + weight + weightUnit;
        return output;
    }


}