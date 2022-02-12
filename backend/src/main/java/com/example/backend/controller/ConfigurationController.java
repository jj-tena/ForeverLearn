package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.service.CategoryService;
import com.example.backend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@Controller
public class ConfigurationController {

    private final UserService userService;

    private final CategoryService categoryService;

    public ConfigurationController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/user-edit-account")
    public String userEditAccountLink(Model model){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "user-edit-account";
    }

    @GetMapping("/user-edit-account-profile")
    public String userEditAccountProfileLink(Model model){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", userService.getActiveUser().get());
        Boolean hasDescription = !userService.getActiveUser().get().getDescription().contentEquals("");
        model.addAttribute("hasDescription", hasDescription);
        Boolean hasContact = !userService.getActiveUser().get().getContact().contentEquals("");
        model.addAttribute("hasContact", hasContact);
        model.addAttribute("categories", categoryService.findAll());
        return "user-edit-account-profile";
    }

    @GetMapping("/user-edit-account-password")
    public String userEditAccountPasswordLink(Model model){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "user-edit-account-password";
    }

    @PostMapping("/update-basic-information")
    public String updateBasicInformation(Model model, User user){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(userActive -> model.addAttribute("activeUserAdmin", userActive.isAdmin()));
        userService.updateBasicInformation(user);
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "user-profile";
    }

    @PostMapping("/update-profile-information")
    public String updateProfileInformation(Model model, User user, @RequestParam("image") MultipartFile multipartFile) throws SQLException, IOException {
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(userActive -> model.addAttribute("activeUserAdmin", userActive.isAdmin()));
        userService.updateProfileInformation(user, multipartFile);
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "user-profile";
    }

    @PostMapping("/update-password")
    public String updatePassword(Model model, String password1, String password2){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        userService.updatePassword(password1, password2);
        model.addAttribute("user", userService.getActiveUser());
        model.addAttribute("categories", categoryService.findAll());
        return "user-profile";
    }

    @GetMapping("/user-delete-account")
    public String userDeleteAccountLink(Model model){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "user-delete-account";
    }

    @GetMapping("/delete-account")
    public String deleteUser(Model model){

        model.addAttribute("pageNumber", 0);

        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", activeUser.get());
        model.addAttribute("categories", categoryService.findAll());

        if (activeUser.isPresent()){
            userService.deleteAccount();
        }

        userService.logout();
        model.addAttribute("activeUser", false);
        model.addAttribute("activeUserAdmin", false);
        return "index";
    }
}
