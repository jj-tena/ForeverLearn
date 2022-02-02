package com.example.backend.controller;

import com.example.backend.model.Category;
import com.example.backend.model.Course;
import com.example.backend.model.Lesson;
import com.example.backend.model.User;
import com.example.backend.service.CategoryService;
import com.example.backend.service.CourseService;
import com.example.backend.service.LessonService;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class SearchController {

    private final CourseService courseService;

    private final CategoryService categoryService;

    private final UserService userService;

    private final LessonService lessonService;

    public SearchController(CourseService courseService, CategoryService categoryService, UserService userService, LessonService lessonService) {
        this.courseService = courseService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.lessonService = lessonService;
    }

    @GetMapping("/library")
    public String librayLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        List<Course> list = courseService.findCourses();
        model.addAttribute("results", list.size());
        model.addAttribute("courses", list);
        model.addAttribute("categories", categoryService.findAll());
        return "library";
    }

    @GetMapping("/library-list")
    public String librayListLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("categories", categoryService.findAll());
        return "library-list";
    }

    @GetMapping("/category-{id}")
    public String libraryByCategory(Model model, @PathVariable Long id){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
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
    public ResponseEntity<Object> downloadCoursePicture(@PathVariable long id) throws SQLException {
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
    public String findCourseByName(Model model, String name){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        List<Course> list = new ArrayList<>();
        Optional<Course> course = courseService.findCourseByName(name);
        course.ifPresent(list::add);
        model.addAttribute("results", list.size());
        model.addAttribute("courses", list);
        model.addAttribute("categories", categoryService.findAll());
        return "library";
    }

    @GetMapping("/course-{id}")
    public String getCourse(Model model, @PathVariable Long id){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("categories", categoryService.findAll());
        Optional<Course> course = courseService.findCourseById(id);
        if (course.isPresent()){
            model.addAttribute("course", course.get());
            return "course";
        } else {
            return "index";
        }
    }

    @GetMapping("/instructor-profile-{id}")
    public String getInstructorProfile(Model model, @PathVariable Long id){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("categories", categoryService.findAll());
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()){
            model.addAttribute("user", user.get());
            model.addAttribute("courses", user.get().getUserCourses());
            return "instructor-profile";
        } else {
            return "index";
        }
    }

    @GetMapping("/lesson-{lessonId}-from-course-{courseId}")
    public String getLesson(Model model, @PathVariable Long lessonId, @PathVariable Long courseId){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("categories", categoryService.findAll());
        Optional<Lesson> lesson = lessonService.findLessonById(lessonId);
        Optional<Course> course = courseService.findCourseById(courseId);
        if (lesson.isPresent() && course.isPresent()){
            model.addAttribute("lesson", lesson.get());
            model.addAttribute("course", course.get());
            return "lesson";
        } else {
            return "index";
        }
    }

    @GetMapping("/category-{id}-difficulty-filter")
    public String filterLibrary(Model model, @PathVariable Long id){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        Optional<Category> category = categoryService.findById(id);
        List<Course> list = new LinkedList<>();
        if (category.isPresent())
            list = courseService.findCoursesByCategory(category.get());
        model.addAttribute("results", list.size());
        model.addAttribute("courses", list);
        model.addAttribute("categories", categoryService.findAll());
        return "library";
    }


}
