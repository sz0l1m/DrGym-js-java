// author: Michał Pędziwiatr
package com.drgym.drgym.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;


public class DragonExerciseTest {

    @Test
    public void testConstructorAndGetters(){
        ArrayList<String> muscles = new ArrayList<>();
        muscles.add("wings");
        muscles.add("claws");
        muscles.add("necks");
        DragonExercise exercise = new DragonExercise("Eating sheep", muscles, 7, 100);

        assertEquals("Eating sheep", exercise.getName());
        assertEquals(muscles, exercise.getMusclesWorked());
        assertEquals(7, exercise.getHeads());
        assertEquals(100, exercise.getHealth());
    }

    @Test
    public void testSetters(){
        ArrayList<String> musclesV1 = new ArrayList<>();
        DragonExercise exercise = new DragonExercise("Eating sheep", musclesV1, 7, 100);

        ArrayList<String> musclesV2 = new ArrayList<>();
        musclesV2.add("tongue");
        musclesV2.add("jaw");
        musclesV2.add("necks");
        exercise.setMusclesWorked(musclesV2);
        exercise.setName("Breathing fire");
        exercise.setHealth(200);
        exercise.setHeads(5);

        assertEquals("Breathing fire", exercise.getName());
        assertEquals(musclesV2, exercise.getMusclesWorked());
        assertEquals(5, exercise.getHeads());
        assertEquals(200, exercise.getHealth());
    }

    @Test
    public void testTakeDamage(){
        ArrayList<String> muscles = new ArrayList<>();
        muscles.add("wings");
        muscles.add("necks");
        DragonExercise exercise = new DragonExercise("Eating sheep", muscles, 7, 100);

        exercise.takeDamage(25);
        assertEquals(75, exercise.getHealth());
        exercise.takeDamage(200);
        assertEquals(0, exercise.getHealth());
    }
}