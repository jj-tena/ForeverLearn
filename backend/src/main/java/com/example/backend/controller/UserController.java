package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.service.CategoryService;
import com.example.backend.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final CategoryService categoryService;

    private final UserService userService;

    public UserController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/user-profile")
    public String userProfileLink(Model model){
        model.addAttribute("user", userService.getActiveUser());
        model.addAttribute("categories", categoryService.findAll());
        return "user-profile";
    }

    @GetMapping("/user-edit-account")
    public String userEditAccountLink(Model model){
        model.addAttribute("user", userService.getActiveUser());
        model.addAttribute("categories", categoryService.findAll());
        return "user-edit-account";
    }

    @GetMapping("/user-edit-account-profile")
    public String userEditAccountProfileLink(Model model){
        model.addAttribute("user", userService.getActiveUser());
        model.addAttribute("categories", categoryService.findAll());
        return "user-edit-account-profile";
    }

    @GetMapping("/user-edit-account-password")
    public String userEditAccountPasswordLink(Model model){
        model.addAttribute("user", userService.getActiveUser());
        model.addAttribute("categories", categoryService.findAll());
        return "user-edit-account-password";
    }

    @PostMapping("/update-basic-information")
    public String updateBasicInformation(Model model, User user){
        userService.updateBasicInformation(user);
        model.addAttribute("user", userService.getActiveUser());
        model.addAttribute("categories", categoryService.findAll());
        return "user-profile";
    }

    @PostMapping("/update-profile-information")
    public String updateProfileInformation(Model model, User user){
        userService.updateProfileInformation(user);
        model.addAttribute("user", userService.getActiveUser());
        model.addAttribute("categories", categoryService.findAll());
        return "user-profile";
    }

    @PostMapping("/update-password")
    public String updatePassword(Model model, String password1, String password2){
        userService.updatePassword(password1, password2);
        model.addAttribute("user", userService.getActiveUser());
        model.addAttribute("categories", categoryService.findAll());
        return "user-profile";
    }

    @GetMapping("/instructor-section")
    public String instructorSectionLink(Model model){
        model.addAttribute("courses", userService.getUserCourses());
        model.addAttribute("user", userService.getActiveUser());
        model.addAttribute("categories", categoryService.findAll());
        return "instructor-courses";
    }

    @GetMapping("/instructor-create-course")
    public String instructorCreateCourse(Model model){
        model.addAttribute("user", userService.getActiveUser());
        model.addAttribute("categories", categoryService.findAll());
        return "instructor-create-course";
    }

    @GetMapping("/instructor-edit-course")
    public String instructorEditCourse(Model model){
        model.addAttribute("user", userService.getActiveUser());
        model.addAttribute("categories", categoryService.findAll());
        return "instructor-edit-course";
    }

    @GetMapping("/student-section")
    public String studentSectionLink(Model model){
        model.addAttribute("user", userService.getActiveUser());
        model.addAttribute("categories", categoryService.findAll());
        return "student-courses-enrolled";
    }




}
