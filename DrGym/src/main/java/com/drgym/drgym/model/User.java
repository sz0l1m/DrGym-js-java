// author: ksiemion
package com.drgym.drgym.model;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "height")
    private Double height;

    @Column(name = "verified")
    private boolean verified;

    @Column(name = "favorite_exercise")
    private Long favoriteExercise;

    @Column(name = "avatar")
    private String avatar;

    @Transient
    private String token;

    @Transient
    private String identifier;

    @Transient
    private String newPassword;

    public User() {}

    public User(
            String username,
            String name,
            String surname,
            String email,
            String password,
            Double weight,
            Double height
    ) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.weight = weight;
        this.height = height;
        this.verified = false;
    }

    public User(String email) {
        this.email = email;
    }

    public User(String username, String name, String surname, Double weight, Double height, Long favoriteExercise, String avatar) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.weight = weight;
        this.height = height;
        this.favoriteExercise = favoriteExercise;
        this.avatar = avatar;
    }

    public User(String email, String newPassword, String token) {
        this.email = email;
        this.newPassword = newPassword;
        this.token = token;
    }

    public User(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }

    public User(
            String username,
            String name,
            String surname,
            String email,
            String password,
            Double weight,
            Double height,
            Long favoriteExercise
    ) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.weight = weight;
        this.height = height;
        this.verified = false;
        this.favoriteExercise = favoriteExercise;
    }

    // getters

    public String getUsername() {return username;}

    public String getName() {return name;}

    public String getSurname() {return surname;}

    public String getEmail() {return email;}

    public String getPassword() {return password;}

    public Double getWeight() {return weight;}

    public Double getHeight() {return height;}

    public boolean isVerified() { return verified; }

    public Long getFavoriteExercise() { return favoriteExercise; }

    public String getToken() { return token; }

    public String getIdentifier() { return identifier; }

    public String getNewPassword() { return newPassword; }

    // setters

    public void setUsername(String newUsername) {this.username = newUsername;}

    public void setName(String newName) {this.name = newName;}

    public void setSurname(String newSurname) {this.surname = newSurname;}

    public void setEmail(String newEmail) {this.email = newEmail;}

    public void setPassword(String newPassword) {this.password = newPassword;}

    public void setWeight(Double newWeight) {this.weight = newWeight;}

    public void setHeight(Double newHeight) {this.height = newHeight;}

    public void setVerified(boolean verified) { this.verified = verified; }

    public void setFavoriteExercise(Long favoriteExercise) { this.favoriteExercise = favoriteExercise; }

    public void setToken(String token) { this.token = token; }

    public void setIdentifier(String identifier) { this.identifier = identifier; }

    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }

    public String getAvatar() { return avatar; }

    public void setAvatar(String avatar) { this.avatar = avatar; }
}
