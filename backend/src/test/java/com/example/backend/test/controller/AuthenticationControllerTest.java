package com.example.backend.test.controller;

import com.example.backend.controller.AuthenticationController;
import com.example.backend.controller.MainController;
import com.example.backend.model.User;
import com.example.backend.service.CategoryService;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private UserService userService;

    @MockBean private CategoryService categoryService;

    @Test
    void loginLink() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login-page"));
    }

    @Test
    void loginUser() throws Exception {
        User user = new User();
        when(userService.login(user)).thenReturn(Optional.of(user));
        when(categoryService.findAll()).thenReturn(new LinkedList<>());
        this.mockMvc.perform(post("/loginUser"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile-page"));
    }

    @Test
    void signupLink() throws Exception {
        this.mockMvc.perform(get("/signup"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("signup-page"));
    }

    @Test
    void signupUser() throws Exception {
        User user = new User();
        when(userService.create(user)).thenReturn(user);
        when(categoryService.findAll()).thenReturn(new LinkedList<>());
        this.mockMvc.perform(post("/signupUser"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile-page"));
    }

    @Test
    void logoutUser() throws Exception {
        this.mockMvc.perform(get("/logout"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}