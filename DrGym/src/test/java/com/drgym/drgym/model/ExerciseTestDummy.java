//author: Michał Pędziwiatr
package com.drgym.drgym.model;
import java.util.ArrayList;


public class ExerciseTestDummy extends Exercise {

    public ExerciseTestDummy(String name, ArrayList<String> musclesWorked) {
        super(name, musclesWorked);
    }

    public ExerciseTestDummy(String name){
        super(name);
    }
}