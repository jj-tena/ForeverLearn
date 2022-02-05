package com.example.backend.controller;

import com.example.backend.model.Course;
import com.example.backend.model.User;
import com.example.backend.service.CategoryService;
import com.example.backend.service.CourseService;
import com.example.backend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    private final UserService userService;

    private final CategoryService categoryService;

    private final CourseService courseService;

    public AdminController(UserService userService, CategoryService categoryService, CourseService courseService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.courseService = courseService;
    }

    @GetMapping("/admin-options")
    public String adminOptions(Model model){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "admin-options";
    }

    @GetMapping("/admin-courses-page-{pageNumber}")
    public String adminCourses(Model model, @PathVariable Integer pageNumber){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", activeUser.get());
        model.addAttribute("categories", categoryService.findAll());

        Page<Course> pageCourses = courseService.findPageCourses(pageNumber, 6);
        model.addAttribute("lastPage", !pageCourses.hasNext());
        model.addAttribute("firstPage", !pageCourses.hasPrevious());
        model.addAttribute("courses", pageCourses);
        model.addAttribute("coursesFound", pageCourses.hasContent());

        pageNumber++;
        model.addAttribute("numberPage", pageNumber);

        return "admin-courses";
    }

    @GetMapping("/admin-courses-prev-page-{pageNumber}")
    public String adminCoursesPrevPage(Model model, @PathVariable Integer pageNumber){
        if (pageNumber>0){
            pageNumber--;
            model.addAttribute("pageNumber", pageNumber);
        }

        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", activeUser.get());

        Page<Course> pageCourses = courseService.findPageCourses(pageNumber, 6);
        model.addAttribute("coursesFound", pageCourses.hasContent());
        model.addAttribute("courses", pageCourses);
        model.addAttribute("firstPage", !pageCourses.hasPrevious());
        model.addAttribute("categories", categoryService.findAll());

        pageNumber++;
        model.addAttribute("numberPage", pageNumber);

        return "admin-courses";
    }

    @GetMapping("/admin-courses-next-page-{pageNumber}")
    public String adminCoursesNextPage(Model model, @PathVariable Integer pageNumber){
        pageNumber++;
        model.addAttribute("pageNumber", pageNumber);

        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", activeUser.get());

        Page<Course> pageCourses = courseService.findPageCourses(pageNumber, 6);
        model.addAttribute("coursesFound", pageCourses.hasContent());
        model.addAttribute("lastPage", !pageCourses.hasNext());
        model.addAttribute("courses", pageCourses);
        model.addAttribute("categories", categoryService.findAll());

        pageNumber++;
        model.addAttribute("numberPage", pageNumber);
        return "admin-courses";
    }

    @GetMapping("/admin-users-page-{pageNumber}")
    public String adminUsers(Model model, @PathVariable Integer pageNumber){

        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());

        Page<User> pageUsers = userService.findPageUsers(pageNumber, 6);
        model.addAttribute("lastPage", !pageUsers.hasNext());
        model.addAttribute("firstPage", !pageUsers.hasPrevious());
        model.addAttribute("users", pageUsers);
        model.addAttribute("usersFound", pageUsers.hasContent());

        pageNumber++;
        model.addAttribute("numberPage", pageNumber);

        return "admin-users";
    }

    @GetMapping("/admin-users-prev-page-{pageNumber}")
    public String adminUsersPrevPage(Model model, @PathVariable Integer pageNumber){

        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());

        if (pageNumber>0){
            pageNumber--;
            model.addAttribute("pageNumber", pageNumber);
        }

        Page<User> pageUsers = userService.findPageUsers(pageNumber, 6);
        model.addAttribute("lastPage", !pageUsers.hasNext());
        model.addAttribute("firstPage", !pageUsers.hasPrevious());
        model.addAttribute("users", pageUsers);
        model.addAttribute("usersFound", pageUsers.hasContent());

        pageNumber++;
        model.addAttribute("numberPage", pageNumber);

        return "admin-users";
    }

    @GetMapping("/admin-users-next-page-{pageNumber}")
    public String adminUsersNextPage(Model model, @PathVariable Integer pageNumber){

        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());

        pageNumber++;
        model.addAttribute("pageNumber", pageNumber);

        Page<User> pageUsers = userService.findPageUsers(pageNumber, 6);
        model.addAttribute("lastPage", !pageUsers.hasNext());
        model.addAttribute("firstPage", !pageUsers.hasPrevious());
        model.addAttribute("users", pageUsers);
        model.addAttribute("usersFound", pageUsers.hasContent());

        pageNumber++;
        model.addAttribute("numberPage", pageNumber);

        return "admin-users";
    }


}
