// author: Michał Pedziwiatr
package com.drgym.drgym.model;

import java.util.ArrayList;


public class DragonExercise extends Exercise {
    private int heads;
    private float health;

    public DragonExercise(String name, ArrayList<String> musclesWorked, int heads, float health){
        super(name, musclesWorked);
        this.heads = heads;
        this.health = health;
        this.roar();
    }

    public float getHealth(){
        return health;
    }

    public void setHealth(float newHealth){
        health = newHealth;
    }

    public int getHeads(){
        return heads;
    }

    public void setHeads(int newHeads){
        heads = newHeads;
    }

    public void takeDamage(float damage){
        health = health - damage;
        if(health < 0){
            health = 0;
        }
    }

    public void roar(){
        System.out.println("grrrrr!");
    }
}