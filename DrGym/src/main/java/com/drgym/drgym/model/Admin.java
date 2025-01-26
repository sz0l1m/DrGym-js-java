// author: ksiemion
package com.drgym.drgym.model;


public class Admin extends UserTemplate {
    public Admin() {}

    public Admin(
            Integer id,
            String username,
            String name,
            String surname,
            String email,
            String password
    ) {
        super(id, username, name, surname, email, password);
    }
}
