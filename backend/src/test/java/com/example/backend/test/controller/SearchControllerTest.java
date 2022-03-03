package com.example.backend.test.controller;

import com.example.backend.controller.ConfigurationController;
import com.example.backend.controller.SearchController;
import com.example.backend.service.CategoryService;
import com.example.backend.service.CourseService;
import com.example.backend.service.LessonService;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(SearchController.class)
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private UserService userService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private LessonService lessonService;

    @Test
    void libraryByCategory() throws Exception {
        this.mockMvc.perform(get("/category-" + 1 + "-library-page-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("library"));
    }

    @Test
    void libraryPrevPageByCategory() throws Exception {
        this.mockMvc.perform(get("/category-" + 1 + "-library-prev-page-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("library"));
    }

    @Test
    void libraryNextPageByCategory() throws Exception {
        this.mockMvc.perform(get("/category-" + 1 + "-library-next-page-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("library"));
    }

    @Test
    void downloadCoursePicture() throws Exception {
        this.mockMvc.perform(get("/courses/" + 1 + "/picture"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void findCourseByName() throws Exception {
        this.mockMvc.perform(get("/find-course"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("library"));
    }

    @Test
    void getCourse() throws Exception {
        this.mockMvc.perform(get("/course-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getInstructorProfile() throws Exception {
        this.mockMvc.perform(get("/instructor-profile-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getLesson() throws Exception {
        this.mockMvc.perform(get("/lesson-" + 1 + "-from-course-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}