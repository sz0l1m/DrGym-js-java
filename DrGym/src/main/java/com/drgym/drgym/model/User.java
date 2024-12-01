// author: ksiemion
package com.drgym.drgym.model;

import jakarta.persistence.Entity;

@Entity
public class User extends UserTemplate {
    private double weight;
    private double height;
    private Integer favouriteExerciseId;

    // empty constructor
    public User() {}

    // basic constructor without lists
    public User(
            String username,
            String name,
            String surname,
            String email,
            String password,
            double weight,
            double height
    ) {
        super(username, name, surname, email, password);
        this.weight = weight;
        this.height = height;
    }

    public User(
            String username,
            String name,
            String surname,
            String email,
            String password,
            double weight,
            double height,
            Integer favouriteExerciseId
    ) {
        super(username, name, surname, email, password);
        this.weight = weight;
        this.height = height;
        this.favouriteExerciseId = favouriteExerciseId;
    }

    // getters
    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public Integer getFavoriteExerciseId() {
        return favouriteExerciseId;
    }

    // setters

    public void setWeight(double newWeight) {
        this.weight = newWeight;
    }

    public void setHeight(double newHeight) {
        this.height = newHeight;
    }

    public void setFavoriteExerciseId(Integer newFavouriteExerciseId) {
        this.favouriteExerciseId = newFavouriteExerciseId;
    }


    public void updateUserInfo(
            String newUsername,
            String newName,
            String newSurname,
            String newEmail,
            String newPassword
    ) {
        this.setUsername(newUsername);
        this.setName(newName);
        this.setSurname(newSurname);
        this.setEmail(newEmail);
        this.setPassword(newPassword);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        final User user = (User) obj;
        return this.getUsername() != null && this.getUsername().equals(user.getUsername());
    }

}
