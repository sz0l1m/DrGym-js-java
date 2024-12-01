// author: ksiemion
package com.drgym.drgym.model;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public abstract class UserTemplate {
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

    public UserTemplate() {}

    public UserTemplate(
            Integer id,
            String username,
            String name,
            String surname,
            String email,
            String password, // will be encrypted in the future
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
    }

    // getters

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Double getWeight() {return weight;}

    public Double getHeight() {return height;}

    // setters

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setSurname(String newSurname) {
        this.surname = newSurname;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }



}
