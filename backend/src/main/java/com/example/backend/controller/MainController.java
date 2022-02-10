package com.example.backend.controller;

import com.example.backend.model.Category;
import com.example.backend.model.User;
import com.example.backend.service.CategoryService;
import com.example.backend.service.UserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.Optional;

@Controller
public class MainController {

    private final CategoryService categoryService;

    private final UserService userService;

    public MainController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String indexLink(Model model){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("categories", categoryService.findAll());
        return "index";
    }

    @GetMapping("/categories/{id}/picture")
    public ResponseEntity<Object> downloadCategoryPicture(@PathVariable long id) throws SQLException {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent() && category.get().getPicture() != null) {
            Resource file = new InputStreamResource(category.get().getPicture().getBinaryStream());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(category.get().getPicture().length()).body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/help-center")
    public String helpCenterLink(Model model){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("categories", categoryService.findAll());
        return "help-center";
    }

    @GetMapping("/terms-of-service")
    public String termsOfServiceLink(Model model){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("categories", categoryService.findAll());
        return "terms-of-service";
    }

    @GetMapping("/privacy-policy")
    public String privacyPolicyLink(Model model){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("categories", categoryService.findAll());
        return "privacy-policy";
    }

    @GetMapping("/errores")
    public String errorLink(Model model){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("categories", categoryService.findAll());
        return "error";
    }
}
