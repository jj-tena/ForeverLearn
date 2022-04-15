package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.service.CategoryService;
import com.example.backend.service.ParticipationService;
import com.example.backend.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class ParticipationController {

    private final ParticipationService participationService;
    private final UserService userService;

    private final CategoryService categoryService;

    public ParticipationController(ParticipationService participationService, UserService userService, CategoryService categoryService) {
        this.participationService = participationService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    private void setCommonData(Model model){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        if (activeUser.isPresent()){
            User user = activeUser.get();
            model.addAttribute("activeUserAdmin", user.isAdmin());
            model.addAttribute("user", user);

        }
        model.addAttribute("categories", categoryService.findAll());
    }

    @GetMapping("/students-area-link")
    public String studentsAreaLink(Model model){
        setCommonData(model);
        return "students-area";
    }

    @GetMapping("/posts-link")
    public String postsLink(Model model){
        setCommonData(model);
        return "posts";
    }

    @GetMapping("/post-link")
    public String postLink(Model model){
        setCommonData(model);
        return "post";
    }

    @GetMapping("/create-post-link")
    public String createPostLink(Model model){
        setCommonData(model);
        return "create-post";
    }

}
