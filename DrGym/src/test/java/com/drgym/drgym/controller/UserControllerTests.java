// author: ksiemion
package com.drgym.drgym.controller;

import com.drgym.drgym.model.User;
import com.drgym.drgym.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUser() throws Exception {
        User user = new User("ziutson", "test", "test", "test", "test", 80.0, 180.0);
        when(userService.findByUsername("ziutson")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/ziutson"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("ziutson"))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.surname").value("test"))
                .andExpect(jsonPath("$.weight").value(80.0))
                .andExpect(jsonPath("$.height").value(180.0));
    }

    @Test
    void testGetUserByEmail() throws Exception {
        User user = new User("ziutson", "test", "test", "test@test.test", "test", 80.0, 180.0);
        when(userService.findByEmail("test@test.test")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/email/test@test.test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("ziutson"))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.surname").value("test"))
                .andExpect(jsonPath("$.weight").value(80.0))
                .andExpect(jsonPath("$.height").value(180.0));
    }

    @Test
    public void testGetUserNotFound() throws Exception {
        when(userService.findByUsername("  ")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/  "))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User("ziutson", "test", "test", "test", "test", 80.0, 180.0);
        when(userService.saveUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"ziutson\",\"name\":\"test\",\"surname\":\"test\",\"email\":\"test\",\"password\":\"test\",\"weight\":80.0,\"height\":180.0}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("ziutson"))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.surname").value("test"))
                .andExpect(jsonPath("$.weight").value(80.0))
                .andExpect(jsonPath("$.height").value(180.0));
    }

    @Test
    void testCreateAndDeleteUser() throws Exception {
        User user = new User("ziutson", "test", "test", "test", "test", 80.0, 180.0);
        when(userService.saveUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"ziutson\",\"name\":\"test\",\"surname\":\"test\",\"email\":\"test\",\"password\":\"test\",\"weight\":80.0,\"height\":180.0}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("ziutson"));

        mockMvc.perform(delete("/api/users/ziutson"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/users/ziutson"))
                .andExpect(status().isNotFound());
    }
}