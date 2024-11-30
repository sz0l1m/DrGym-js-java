// author: ksiemion
package com.drgym.drgym.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


public class AdminTest {
    @Test
    public void addAdminShouldAddAdminToList() {
        Admin admin = new Admin(1, "adminUsername", "adminName", "adminSurname", "adminEmail@example.com", "adminPassword");
        List<Admin> adminList = new ArrayList<>();
        adminList.add(admin);
        assertTrue(adminList.contains(admin));
    }

    @Test
    public void removeAdminShouldRemoveAdminFromList() {
        Admin admin = new Admin(1, "adminUsername", "adminName", "adminSurname", "adminEmail@example.com", "adminPassword");
        List<Admin> adminList = new ArrayList<>();
        adminList.add(admin);
        adminList.remove(admin);
        assertFalse(adminList.contains(admin));
    }
}
