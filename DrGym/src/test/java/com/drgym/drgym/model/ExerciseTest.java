// author: Michał Pędziwiatr
package com.drgym.drgym.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;


public class ExerciseTest{

    @Test
    public void testConstructorAndGetters(){
        ArrayList<String> muscles = new ArrayList<>();
        muscles.add("pectorals");
        muscles.add("triceps");
        muscles.add("front delts");
        ExerciseTestDummy exercise = new ExerciseTestDummy("Bench press", muscles);

        assertEquals("Bench press", exercise.getName());
        assertEquals(muscles, exercise.getMusclesWorked());
    }

    @Test
    public void testConstructorWithoutMusclesWorked(){
        ExerciseTestDummy exercise = new ExerciseTestDummy("Bench press");
        ArrayList<String> emptyList = new ArrayList<>();

        assertEquals(exercise.getMusclesWorked(), emptyList);
    }

    @Test
    public void testSetters(){
        ArrayList<String> musclesV1 = new ArrayList<>();
        musclesV1.add("pectorals");
        musclesV1.add("triceps");
        musclesV1.add("front delts");
        ExerciseTestDummy exercise = new ExerciseTestDummy("Bench press", musclesV1);

        exercise.setName("Bicep curl");
        assertTrue(exercise.getName() == "Bicep curl");

        ArrayList<String> musclesV2 = new ArrayList<>();
        musclesV2.add("biceps");
        musclesV2.add("brachialis");
        exercise.setMusclesWorked(musclesV2);
        assertEquals(musclesV2, exercise.getMusclesWorked());
    }

    @Test
    public void testMusclesWorkedOperations(){
        ArrayList<String> muscles = new ArrayList<>();
        muscles.add("pectorals");
        muscles.add("triceps");
        ExerciseTestDummy exercise = new ExerciseTestDummy("Bench press", muscles);

        assertTrue(exercise.getMusclesWorked().contains("pectorals"));
        assertTrue(exercise.getMusclesWorked().contains("triceps"));
        assertTrue(exercise.getMusclesWorked().size() == 2);

        exercise.addToMusclesWorked("front delts");
        assertTrue(exercise.getMusclesWorked().contains("front delts"));
        assertTrue(exercise.getMusclesWorked().size() == 3);

        exercise.removeFromMusclesWorked("triceps");
        assertTrue(exercise.getMusclesWorked().size() == 2);
        assertFalse(exercise.getMusclesWorked().contains("triceps"));
    }

    @Test
    public void testConstructorWithNullMuscles(){
        ExerciseTestDummy exercise = new ExerciseTestDummy("Bench press", null);

        assertTrue(exercise.getMusclesWorked() != null);

        exercise.addToMusclesWorked("pectorals");
        assertTrue(exercise.getMusclesWorked().size() == 1);
    }

    @Test
    public void testToString(){
        ArrayList<String> muscles = new ArrayList<>();
        muscles.add("pectorals");
        muscles.add("triceps");
        muscles.add("front delts");
        ExerciseTestDummy exercise = new ExerciseTestDummy("Bench press", muscles);

        String expectedString = "Exercise name: Bench press, "+
         "muscles worked: [pectorals, triceps, front delts]";
        assertEquals(exercise.toString(), expectedString);
    }

}