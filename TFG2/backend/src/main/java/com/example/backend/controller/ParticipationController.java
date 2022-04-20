package com.example.backend.controller;

import com.example.backend.model.Participation;
import com.example.backend.model.User;
import com.example.backend.service.CategoryService;
import com.example.backend.service.CourseService;
import com.example.backend.service.ParticipationService;
import com.example.backend.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class ParticipationController {

    private final ParticipationService participationService;
    private final UserService userService;
    private final CategoryService categoryService;

    private final CourseService courseService;

    public ParticipationController(ParticipationService participationService, UserService userService, CategoryService categoryService, CourseService courseService) {
        this.participationService = participationService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.courseService = courseService;
    }

    private void setHeaderInfo(Model model){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        if (activeUser.isPresent()){
            User user = activeUser.get();
            model.addAttribute("activeUserAdmin", user.isAdmin());
            model.addAttribute("user", user);
        }
        model.addAttribute("categories", categoryService.findAll());
    }

    private void setParticipationHeader(Model model, Long courseId){
        Optional<Participation> optionalParticipation = Optional.empty();
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
        }
        model.addAttribute("enrolledUser", optionalParticipation.isPresent());
        model.addAttribute("courseId", courseId);
    }



    @GetMapping("/students-area-{courseId}")
    public String studentsAreaLink(Model model, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        return "students-area";
    }

    @GetMapping("/posts-{courseId}")
    public String postsLink(Model model, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        return "posts";
    }

    @GetMapping("/post-link-course-{courseId}")
    public String postLink(Model model, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        return "post";
    }

    @GetMapping("/create-post-for-course-{courseId}")
    public String createPostLink(Model model, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        return "create-post";
    }

    @GetMapping("/publish-post-for-course-{courseId}")
    public String publishPost(Model model, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        return "participation";
    }

    @GetMapping("/participation-{courseId}")
    public String participationLink(Model model, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        return "participation";
    }

}
