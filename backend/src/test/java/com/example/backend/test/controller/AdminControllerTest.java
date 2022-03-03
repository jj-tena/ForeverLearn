package com.example.backend.test.controller;

import com.example.backend.controller.AdminController;
import com.example.backend.controller.SearchController;
import com.example.backend.model.Category;
import com.example.backend.model.Course;
import com.example.backend.model.User;
import com.example.backend.service.CategoryService;
import com.example.backend.service.CourseService;
import com.example.backend.service.LessonService;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private UserService userService;

    @MockBean
    private CourseService courseService;

    @Test
    void adminOptions() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        this.mockMvc.perform(get("/admin-options"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin-options-page"));
    }

    @Test
    void adminCourses() throws Exception {

    }

    @Test
    void adminCoursesPrevPage() {
    }

    @Test
    void adminCoursesNextPage() {
    }

    @Test
    void findCourseByName() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(courseService.findCoursesByName(any())).thenReturn(Optional.of(new LinkedList<>()));
        this.mockMvc.perform(get("/admin-search-course"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin-courses"));
    }

    @Test
    void banCourse() throws Exception {
    }

    @Test
    void unbanCourse() {
    }

    @Test
    void deleteCourse() {
    }

    @Test
    void adminUsers() {
    }

    @Test
    void adminUsersPrevPage() {
    }

    @Test
    void adminUsersNextPage() {
    }

    @Test
    void findUserByName() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(userService.findUserByName(any())).thenReturn(Optional.of(new LinkedList<>()));
        this.mockMvc.perform(get("/admin-search-user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin-users"));
    }

    @Test
    void banUser() {
    }

    @Test
    void unbanUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void adminCategories() {
    }

    @Test
    void adminCategoriesPrevPage() {
    }

    @Test
    void adminCategoriesNextPage() throws Exception {
    }

    @Test
    void findCategoryByName() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(categoryService.findCategoriesByName(any())).thenReturn(Optional.of(new LinkedList<>()));
        this.mockMvc.perform(get("/admin-search-category"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin-categories"));

    }

    @Test
    void createCategory() throws Exception {
        this.mockMvc.perform(get("/create-category"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void editCategory() throws Exception {
        this.mockMvc.perform(get("/edit-category-" + 1))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void deleteCategory() throws Exception {
        this.mockMvc.perform(get("/delete-category-" + 1))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}