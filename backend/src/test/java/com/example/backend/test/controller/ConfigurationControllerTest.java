package com.example.backend.test.controller;

import com.example.backend.controller.ConfigurationController;
import com.example.backend.controller.MainController;
import com.example.backend.model.User;
import com.example.backend.service.CategoryService;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ConfigurationController.class)
class ConfigurationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private UserService userService;

    @Test
    void userEditAccountLink() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(categoryService.findAll()).thenReturn(new LinkedList<>());
        this.mockMvc.perform(get("/user-edit-account"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user-edit-account-page"));
    }

    @Test
    void userEditAccountProfileLink() throws Exception {
        User user = new User();
        user.setDescription("");
        user.setContact("");
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(categoryService.findAll()).thenReturn(new LinkedList<>());
        this.mockMvc.perform(get("/user-edit-account-profile"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user-edit-account-profile-page"));
    }

    @Test
    void userEditAccountPasswordLink() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(categoryService.findAll()).thenReturn(new LinkedList<>());
        this.mockMvc.perform(get("/user-edit-account-password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user-edit-account-password-page"));
    }

    @Test
    void updateBasicInformation() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(categoryService.findAll()).thenReturn(new LinkedList<>());
        this.mockMvc.perform(post("/update-basic-information"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile-page"));
    }

    @Test
    void updateProfileInformation() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(categoryService.findAll()).thenReturn(new LinkedList<>());
        this.mockMvc.perform(post("/update-basic-information"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile-page"));
    }

    @Test
    void updatePassword() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(categoryService.findAll()).thenReturn(new LinkedList<>());
        this.mockMvc.perform(post("/update-password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile-page"));
    }

    @Test
    void userDeleteAccountLink() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(categoryService.findAll()).thenReturn(new LinkedList<>());
        this.mockMvc.perform(get("/user-delete-account"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user-delete-account-page"));
    }

    @Test
    void deleteUser() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(categoryService.findAll()).thenReturn(new LinkedList<>());
        this.mockMvc.perform(get("/delete-account"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}