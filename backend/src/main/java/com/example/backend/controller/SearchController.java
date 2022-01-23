package com.example.backend.controller;

import com.example.backend.service.CategoryService;
import com.example.backend.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SearchController {

    private final CourseService courseService;

    public SearchController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/library")
    public String librayLink(){
        return "library";
    }

    @GetMapping("/library-list")
    public String librayListLink(){
        return "library-list";
    }

    @GetMapping("/searchCategory/{id}")
    public String libraryByCategory(Model model, @PathVariable Long id){
        model.addAttribute("courses", courseService.findCoursesByCategory(id));
        return "library";
    }


}
