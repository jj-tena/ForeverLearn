package com.example.backend.test.controller;

import com.example.backend.controller.MainController;
import com.example.backend.model.Category;
import com.example.backend.model.User;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.CourseRepository;
import com.example.backend.repository.RequirementRepository;
import com.example.backend.service.CategoryService;
import com.example.backend.service.CourseService;
import com.example.backend.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private CategoryService categoryService;

    @MockBean private UserService userService;

    @Test
    void indexLink() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void downloadCategoryPicture() throws Exception {
        this.mockMvc.perform(get("/categories/" + 1 + "/picture"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void helpCenterLink() throws Exception {
        this.mockMvc.perform(get("/help-center"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("help"));
    }

    @Test
    void termsOfServiceLink() throws Exception {
        this.mockMvc.perform(get("/terms-of-service"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("terms"));
    }

    @Test
    void privacyPolicyLink() throws Exception {
        this.mockMvc.perform(get("/privacy-policy"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("privacy"));
    }

    @Test
    void errorLink() throws Exception {
        this.mockMvc.perform(get("/errores"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }
}