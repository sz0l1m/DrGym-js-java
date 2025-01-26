// author: Michał Pędziwiatr
package com.drgym.drgym.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class StrengthExerciseTest {

    @Test
    public void testConstructorWithoutWeightUnit(){
        ArrayList<String> muscles = new ArrayList<>();
        muscles.add("pectorals");
        muscles.add("triceps");
        muscles.add("front delts");
        StrengthExercise exercise = new StrengthExercise("Bench press", muscles, 1, 120);

        assertEquals("Bench press", exercise.getName());
        assertEquals(muscles, exercise.getMusclesWorked());
        assertEquals(1, exercise.getRepetitions());
        assertEquals(120, exercise.getWeight());
        assertEquals("kg", exercise.getWeightUnit());
    }

    @Test
    public void testConstructorWithtWeightUnit(){
        ArrayList<String> muscles = new ArrayList<>();
        muscles.add("pectorals");
        muscles.add("triceps");
        muscles.add("front delts");
        StrengthExercise exercise = new StrengthExercise("Bench press", muscles, 1, 265, "lb");

        assertEquals("Bench press", exercise.getName());
        assertEquals(muscles, exercise.getMusclesWorked());
        assertEquals(1, exercise.getRepetitions());
        assertEquals(265, exercise.getWeight());
        assertEquals("lb", exercise.getWeightUnit());
    }

    @Test
    public void testSetters(){
        ArrayList<String> muscles = new ArrayList<>();
        muscles.add("pectorals");
        muscles.add("triceps");
        muscles.add("front delts");
        StrengthExercise exercise = new StrengthExercise("Bench press", muscles, 1, 120);

        exercise.setRepetitions(6);
        exercise.setWeight(225);
        exercise.setWeightUnit("lb");
        assertEquals(6, exercise.getRepetitions());
        assertEquals(225, exercise.getWeight());
        assertEquals("lb", exercise.getWeightUnit());
    }

    @Test
    public void testToString(){
        ArrayList<String> muscles = new ArrayList<>();
        muscles.add("pectorals");
        muscles.add("triceps");
        muscles.add("front delts");
        StrengthExercise exercise = new StrengthExercise("Bench press", muscles, 1, 120);

        String expectedString = "Exercise name: Bench press, "+
         "muscles worked: [pectorals, triceps, front delts],\n" +
         "Repetitions: 1, Weight: 120kg";
        assertEquals(exercise.toString(), expectedString);
    }
}