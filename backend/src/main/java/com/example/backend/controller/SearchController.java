package com.example.backend.controller;

import com.example.backend.model.Category;
import com.example.backend.model.Course;
import com.example.backend.service.CategoryService;
import com.example.backend.service.CourseService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class SearchController {

    private final CourseService courseService;

    private final CategoryService categoryService;

    public SearchController(CourseService courseService, CategoryService categoryService) {
        this.courseService = courseService;
        this.categoryService = categoryService;
    }

    @GetMapping("/library")
    public String librayLink(Model model){
        List<Course> list = courseService.findCourses();
        model.addAttribute("results", list.size());
        model.addAttribute("courses", list);
        model.addAttribute("categories", categoryService.findAll());
        return "library";
    }

    @GetMapping("/library-list")
    public String librayListLink(Model model){
        model.addAttribute("categories", categoryService.findAll());
        return "library-list";
    }

    @GetMapping("/category-{id}")
    public String libraryByCategory(Model model, @PathVariable Long id){
        Optional<Category> category = categoryService.findById(id);
        List<Course> list = new LinkedList<>();
        if (category.isPresent())
            list = courseService.findCoursesByCategory(category.get());
        model.addAttribute("results", list.size());
        model.addAttribute("courses", list);
        model.addAttribute("categories", categoryService.findAll());
        return "library";
    }

    @GetMapping("/courses/{id}/picture")
    public ResponseEntity<Object> downloadCategoryPicture(@PathVariable long id) throws SQLException {
        Optional<Course> course = courseService.findCourseById(id);
        if (course.isPresent() && course.get().getPicture() != null) {
            Resource file = new InputStreamResource(course.get().getPicture().getBinaryStream());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(course.get().getPicture().length()).body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find-course")
    public String findCourse(Model model, String name){
        List<Course> list = new ArrayList<>();
        Optional<Course> course = courseService.findCourseByName(name);
        course.ifPresent(list::add);
        model.addAttribute("results", list.size());
        model.addAttribute("courses", list);
        model.addAttribute("categories", categoryService.findAll());
        return "library";
    }


}
