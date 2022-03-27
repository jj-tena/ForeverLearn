package com.example.backend.test.controller;

import com.example.backend.controller.ConfigurationController;
import com.example.backend.controller.UserController;
import com.example.backend.model.Course;
import com.example.backend.model.Theme;
import com.example.backend.model.User;
import com.example.backend.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private UserService userService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private ThemeService themeService;

    @MockBean
    private LessonService lessonService;

    @MockBean
    private RequirementService requirementService;

    @MockBean
    private ObjectiveService objectiveService;

    @Test
    void downloadUserProfilePhoto() throws Exception {
        this.mockMvc.perform(get("/user/" + 1 + "/profilePhoto"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void userProfileLink() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(categoryService.findAll()).thenReturn(new LinkedList<>());
        this.mockMvc.perform(get("/user-profile"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile-page"));
    }

    @Test
    void instructorSectionLink() throws Exception {
        User user = new User();
        user.addUserCourse(new Course());
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(categoryService.findAll()).thenReturn(new LinkedList<>());
        this.mockMvc.perform(get("/instructor-section"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("instructor-courses"));
    }

    @Test
    void instructorCreateCourse() throws Exception {
        User user = new User();
        user.addUserCourse(new Course());
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(categoryService.findAll()).thenReturn(new LinkedList<>());
        this.mockMvc.perform(get("/instructor-create-course"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("instructor-create-course-page"));
    }

    @Test
    void createCourse() throws Exception {
        this.mockMvc.perform(get("/create-course"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void instructorEditCourse() throws Exception {
        User user = new User();
        user.addUserCourse(new Course());
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(categoryService.findAll()).thenReturn(new LinkedList<>());
        when(courseService.findCourseById(anyLong())).thenReturn(Optional.of(new Course()));
        this.mockMvc.perform(get("/instructor-edit-course-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("instructor-edit-course"));
    }

    @Test
    void editCourse() throws Exception {
        this.mockMvc.perform(get("/edit-course"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void instructorDeleteCourse() throws Exception {
        User user = new User();
        user.addUserCourse(new Course());
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(categoryService.findAll()).thenReturn(new LinkedList<>());
        when(courseService.findCourseById(anyLong())).thenReturn(Optional.of(new Course()));
        this.mockMvc.perform(get("/instructor-delete-course-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("instructor-courses"));
    }

    @Test
    void studentSectionLink() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        this.mockMvc.perform(get("/student-section"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("student-courses-enrolled-page"));
    }

    @Test
    void studentCoursesEnrolledLink() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        this.mockMvc.perform(get("/student-courses-enrolled"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("student-courses-enrolled-page"));
    }

    @Test
    void studentCoursesCompletedLink() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        this.mockMvc.perform(get("/student-courses-completed"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("student-courses-completed-page"));
    }

    @Test
    void studentCoursesWishedLink() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        this.mockMvc.perform(get("/student-courses-wished"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("student-courses-wished-page"));
    }

    @Test
    void enrollCourse() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        this.mockMvc.perform(get("/student-courses-enrolled"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("student-courses-enrolled-page"));
    }

    @Test
    void unenrollCourse() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        this.mockMvc.perform(get("/student-courses-enrolled"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("student-courses-enrolled-page"));
    }

    @Test
    void wishCourse() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        this.mockMvc.perform(get("/student-courses-wished"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("student-courses-wished-page"));
    }

    @Test
    void deleteWishCourse() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        this.mockMvc.perform(get("/student-courses-wished"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("student-courses-wished-page"));
    }

    @Test
    void createTheme() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(courseService.findCourseById(anyLong())).thenReturn(Optional.of(new Course()));
        this.mockMvc.perform(post("/create-theme-for-course-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("instructor-edit-course"));
    }

    @Test
    void editTheme() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(courseService.findCourseById(anyLong())).thenReturn(Optional.of(new Course()));
        this.mockMvc.perform(post("/edit-theme-" + 1 + "-for-course-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("instructor-edit-course"));
    }

    @Test
    void deleteTheme() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(courseService.findCourseById(anyLong())).thenReturn(Optional.of(new Course()));
        this.mockMvc.perform(get("/delete-theme-" + 1 + "-for-course-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("instructor-edit-course"));
    }

    @Test
    void createLesson() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(themeService.findThemeById(anyLong())).thenReturn(Optional.of(new Theme()));
        when(courseService.findCourseById(anyLong())).thenReturn(Optional.of(new Course()));
        this.mockMvc.perform(post("/create-lesson-for-theme-" + 1 + "-for-course-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("instructor-edit-course"));
    }

    @Test
    void editLesson() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(themeService.findThemeById(anyLong())).thenReturn(Optional.of(new Theme()));
        when(courseService.findCourseById(anyLong())).thenReturn(Optional.of(new Course()));
        this.mockMvc.perform(post("/edit-lesson-" + 1 + "-for-theme-" + 1 + "-for-course-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("instructor-edit-course"));
    }

    @Test
    void deleteLesson() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(themeService.findThemeById(anyLong())).thenReturn(Optional.of(new Theme()));
        when(courseService.findCourseById(anyLong())).thenReturn(Optional.of(new Course()));
        this.mockMvc.perform(get("/delete-lesson-" + 1 + "-for-theme-" + 1 + "-for-course-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("instructor-edit-course"));
    }

    @Test
    void createRequirement() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(courseService.findCourseById(anyLong())).thenReturn(Optional.of(new Course()));
        this.mockMvc.perform(post("/create-requirement-for-course-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("instructor-edit-course"));
    }

    @Test
    void editRequirement() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(courseService.findCourseById(anyLong())).thenReturn(Optional.of(new Course()));
        this.mockMvc.perform(post("/edit-requirement-" + 1 + "-for-course-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("instructor-edit-course"));
    }

    @Test
    void deleteRequirement() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(courseService.findCourseById(anyLong())).thenReturn(Optional.of(new Course()));
        this.mockMvc.perform(get("/delete-requirement-" + 1 + "-for-course-" + 1))
                .andDo(print()
                ).andExpect(status().isOk())
                .andExpect(view().name("instructor-edit-course"));
    }

    @Test
    void createObjective() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(courseService.findCourseById(anyLong())).thenReturn(Optional.of(new Course()));
        this.mockMvc.perform(post("/create-objective-for-course-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("instructor-edit-course"));
    }

    @Test
    void editObjective() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(courseService.findCourseById(anyLong())).thenReturn(Optional.of(new Course()));
        this.mockMvc.perform(post("/edit-objective-" + 1 + "-for-course-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("instructor-edit-course"));
    }

    @Test
    void deleteObjective() throws Exception {
        User user = new User();
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(courseService.findCourseById(anyLong())).thenReturn(Optional.of(new Course()));
        this.mockMvc.perform(get("/delete-objective-" + 1 + "-for-course-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("instructor-edit-course"));
    }

    @Test
    void likeCourse() throws Exception {
        this.mockMvc.perform(get("/like-course-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void quitLikeCourse() throws Exception {
        this.mockMvc.perform(get("/quit-like-course-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void dislikeCourse() throws Exception {
        this.mockMvc.perform(get("/dislike-course-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void quitDislikeCourse() throws Exception {
        this.mockMvc.perform(get("/quit-dislike-course-" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}