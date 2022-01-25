package com.example.backend.controller;

import com.example.backend.model.Course;
import com.example.backend.model.User;
import com.example.backend.service.CategoryService;
import com.example.backend.service.CourseService;
import com.example.backend.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Optional;

@Controller
public class UserController {

    private final CategoryService categoryService;

    private final UserService userService;

    private final CourseService courseService;

    public UserController(CategoryService categoryService, UserService userService, CourseService courseService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping("/user-profile")
    public String userProfileLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "user-profile";
    }

    @GetMapping("/user-edit-account")
    public String userEditAccountLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "user-edit-account";
    }

    @GetMapping("/user-edit-account-profile")
    public String userEditAccountProfileLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "user-edit-account-profile";
    }

    @GetMapping("/user-edit-account-password")
    public String userEditAccountPasswordLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "user-edit-account-password";
    }

    @PostMapping("/update-basic-information")
    public String updateBasicInformation(Model model, User user){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        userService.updateBasicInformation(user);
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "user-profile";
    }

    @PostMapping("/update-profile-information")
    public String updateProfileInformation(Model model, User user){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        userService.updateProfileInformation(user);
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "user-profile";
    }

    @PostMapping("/update-password")
    public String updatePassword(Model model, String password1, String password2){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        userService.updatePassword(password1, password2);
        model.addAttribute("user", userService.getActiveUser());
        model.addAttribute("categories", categoryService.findAll());
        return "user-profile";
    }

    @GetMapping("/instructor-section")
    public String instructorSectionLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("courses", userService.getActiveUser().get().getUserCourses());
        model.addAttribute("categories", categoryService.findAll());
        return "instructor-courses";
    }

    @GetMapping("/instructor-create-course")
    public String instructorCreateCourse(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "instructor-create-course";
    }

    @PostMapping("/create-course")
    public String createCourse(Model model, Course course, String categoryName) throws IOException {
        courseService.createCourse(course);
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("courses", userService.getActiveUser().get().getUserCourses());
        model.addAttribute("categories", categoryService.findAll());
        return "instructor-courses";
    }

    @GetMapping("/instructor-edit-course-{id}")
    public String instructorEditCourse(Model model, @PathVariable Long id){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "instructor-edit-course";
    }

    @GetMapping("/instructor-delete-course-{id}")
    public String instructorDeleteCourse(Model model, @PathVariable Long id){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        Optional<Course> optionalCourse = courseService.findCourseById(id);
        if ((optionalCourse.isPresent()) && (userService.getActiveUser().isPresent())){
            userService.deleteCourse(userService.getActiveUser().get(), optionalCourse.get());
        }
        return "instructor-courses";
    }

    @GetMapping("/student-section")
    public String studentSectionLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "student-courses-enrolled";
    }

    @GetMapping("/student-courses-enrolled")
    public String studentCoursesEnrolledLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "student-courses-enrolled";
    }

    @GetMapping("/student-courses-completed")
    public String studentCoursesCompletedLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "student-courses-completed";
    }

    @GetMapping("/student-courses-wished")
    public String studentCoursesWishedLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "student-courses-wished";
    }





}
