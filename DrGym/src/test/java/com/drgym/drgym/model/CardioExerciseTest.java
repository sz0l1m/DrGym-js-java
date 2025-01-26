// author: Michał Pędziwiatr
package com.drgym.drgym.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.time.LocalTime;


public class CardioExerciseTest {

    @Test
    public void testConstructorWithoutDuration(){
        ArrayList<String> muscles = new ArrayList<>();
        muscles.add("hamstrings");
        muscles.add("glutes");
        muscles.add("quadriceps");
        muscles.add("calves");
        CardioExercise cardio = new CardioExercise("Running", muscles);

        assertEquals("Running", cardio.getName());
        assertEquals(muscles, cardio.getMusclesWorked());
    }

    @Test
    public void testConstructorWithDuration(){
        ArrayList<String> muscles = new ArrayList<>();
        muscles.add("hamstrings");
        muscles.add("glutes");
        muscles.add("quadriceps");
        muscles.add("calves");
        CardioExercise cardio = new CardioExercise("Running", muscles, LocalTime.of(0, 20));

        assertEquals("Running", cardio.getName());
        assertEquals(LocalTime.of(0, 20), cardio.getDuration());
        assertEquals(muscles, cardio.getMusclesWorked());
    }

    @Test
    public void testGettersAndSetters(){
        ArrayList<String> musclesV1 = new ArrayList<>();
        musclesV1.add("hamstrings");
        musclesV1.add("glutes");
        ArrayList<String> musclesV2 = new ArrayList<>();
        musclesV2.add("quadriceps");
        musclesV2.add("calves");

        CardioExercise cardio = new CardioExercise("Running", musclesV1, LocalTime.of(0, 20));

        cardio.setDuration(LocalTime.of(1, 15));
        assertEquals(cardio.getDuration(), LocalTime.of(1, 15));
        cardio.setMusclesWorked(musclesV2);
        assertEquals(cardio.getMusclesWorked(), musclesV2);
    }

    @Test
    public void testToString(){
        ArrayList<String> muscles = new ArrayList<>();
        muscles.add("hamstrings");
        muscles.add("glutes");
        muscles.add("quadriceps");
        muscles.add("calves");
        CardioExercise cardio = new CardioExercise("Running", muscles, LocalTime.of(0, 20));

        String expectedString = "Exercise name: Running, "+
         "muscles worked: [hamstrings, glutes, quadriceps, calves],\n"+
         "Duration: 00:20";
        assertEquals(cardio.toString(), expectedString);
    }
}