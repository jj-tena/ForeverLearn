package com.example.backend.test.integration.controller;

import com.example.backend.controller.MainController;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.CourseRepository;
import com.example.backend.service.CategoryService;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MainController.class)
class MainControllerTest {

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    @Test
    void indexLink() throws Exception {
    }

    @Test
    void downloadCategoryPicture() {
    }

    @Test
    void helpCenterLink() {
    }

    @Test
    void termsOfServiceLink() {
    }

    @Test
    void privacyPolicyLink() {
    }

    @Test
    void errorLink() {
    }
}