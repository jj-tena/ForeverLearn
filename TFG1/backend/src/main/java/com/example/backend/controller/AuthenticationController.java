package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.service.CategoryService;
import com.example.backend.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Controller
public class AuthenticationController {

    private final UserService userService;

    private final CategoryService categoryService;

    public AuthenticationController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/login")
    public String loginLink(Model model){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("categories", categoryService.findAll());
        return "login-page";
    }

    @PostMapping("/loginUser")
    public String loginUser(Model model, User user) throws IOException {
        model.addAttribute("categories", categoryService.findAll());
        Optional<User> activeUser = userService.login(user);
        if (activeUser.isPresent() && !activeUser.get().isBanned()){
            userService.setActiveUser(activeUser.get());
            model.addAttribute("activeUser", activeUser.isPresent());
            activeUser.ifPresent(userActive -> model.addAttribute("activeUserAdmin", userActive.isAdmin()));
            model.addAttribute("user", activeUser.get());
            return "user-profile-page";
        }
        else
            return "/error";
    }


    @GetMapping("/signup")
    public String signupLink(Model model){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("categories", categoryService.findAll());
        return "signup-page";
    }

    @PostMapping("/signupUser")
    public String signupUser(Model model, User user) throws IOException {
        User activeUser = userService.create(user);
        if (Objects.isNull(activeUser)){
            return "error";
        }
        userService.setActiveUser(activeUser);
        model.addAttribute("activeUser", true);
        model.addAttribute("activeUserAdmin", activeUser.isAdmin());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("user", user);
        return "user-profile-page";
    }

    @GetMapping("/logout")
    public String logoutUser(Model model){
        userService.logout();
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("categories", categoryService.findAll());
        return "index";
    }
}
