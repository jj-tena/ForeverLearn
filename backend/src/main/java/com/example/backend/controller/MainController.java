package com.example.backend.controller;

import com.example.backend.model.Category;
import com.example.backend.service.CategoryService;
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

    public MainController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String indexLink(Model model){
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
        model.addAttribute("categories", categoryService.findAll());
        return "help-center";
    }

    @GetMapping("/terms-of-service")
    public String termsOfServiceLink(Model model){
        model.addAttribute("categories", categoryService.findAll());
        return "terms-of-service";
    }

    @GetMapping("/privacy-policy")
    public String privacyPolicyLink(Model model){
        model.addAttribute("categories", categoryService.findAll());
        return "privacy-policy";
    }
}
