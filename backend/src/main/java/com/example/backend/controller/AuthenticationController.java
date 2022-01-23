package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginLink(){
        return "login";
    }

    @GetMapping("/signup")
    public String signupLink(){
        return "signup";
    }

    @GetMapping("/reset-password")
    public String resetPasswordLink(){
        return "reset-password";
    }

    @PostMapping("/signupUser")
    public String signupUser(Model model, User user){
        userService.create(user);
        return "user-profile";
    }
}
