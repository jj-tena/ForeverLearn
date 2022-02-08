package com.example.backend.controller;

import com.example.backend.model.Category;
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

import java.util.ArrayList;
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

        model.addAttribute("pageNumber", pageNumber);

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

    @GetMapping("/admin-search-course")
    public String findCourseByName(Model model, String name){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", activeUser.get());

        Optional<List<Course>> courses = courseService.findCoursesByName(name);
        Boolean coursesFound = courses.get().size()>0;
        model.addAttribute("coursesFound", coursesFound);
        model.addAttribute("courses", courses.get());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("pageNumber", 1);
        model.addAttribute("numberPage", 1);
        model.addAttribute("firstPage", true);
        model.addAttribute("lastPage", true);
        return "admin-courses";
    }

    @GetMapping("/ban-course-{id}")
    public String banCourse(Model model, @PathVariable Long id){

        model.addAttribute("pageNumber", 0);

        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", activeUser.get());
        model.addAttribute("categories", categoryService.findAll());

        if (activeUser.isPresent() && activeUser.get().isAdmin()){
            courseService.banCourse(id);
        }

        Page<Course> pageCourses = courseService.findPageCourses(0, 6);
        model.addAttribute("lastPage", !pageCourses.hasNext());
        model.addAttribute("firstPage", !pageCourses.hasPrevious());
        model.addAttribute("courses", pageCourses);
        model.addAttribute("coursesFound", pageCourses.hasContent());

        model.addAttribute("numberPage", 1);

        return "admin-courses";
    }

    @GetMapping("/unban-course-{id}")
    public String unbanCourse(Model model, @PathVariable Long id){

        model.addAttribute("pageNumber", 0);

        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", activeUser.get());
        model.addAttribute("categories", categoryService.findAll());

        if (activeUser.isPresent() && activeUser.get().isAdmin()){
            courseService.unbanCourse(id);
        }

        Page<Course> pageCourses = courseService.findPageCourses(0, 6);
        model.addAttribute("lastPage", !pageCourses.hasNext());
        model.addAttribute("firstPage", !pageCourses.hasPrevious());
        model.addAttribute("courses", pageCourses);
        model.addAttribute("coursesFound", pageCourses.hasContent());

        model.addAttribute("numberPage", 1);

        return "admin-courses";
    }

    @GetMapping("/admin-delete-course-{id}")
    public String deleteCourse(Model model, @PathVariable Long id){

        model.addAttribute("pageNumber", 0);

        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", activeUser.get());
        model.addAttribute("categories", categoryService.findAll());

        if (activeUser.isPresent() && activeUser.get().isAdmin()){
            courseService.adminDeleteCourse(id);
        }

        Page<Course> pageCourses = courseService.findPageCourses(0, 6);
        model.addAttribute("lastPage", !pageCourses.hasNext());
        model.addAttribute("firstPage", !pageCourses.hasPrevious());
        model.addAttribute("courses", pageCourses);
        model.addAttribute("coursesFound", pageCourses.hasContent());

        model.addAttribute("numberPage", 1);

        return "admin-courses";
    }

    @GetMapping("/admin-users-page-{pageNumber}")
    public String adminUsers(Model model, @PathVariable Integer pageNumber){

        model.addAttribute("pageNumber", pageNumber);

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

    @GetMapping("/admin-search-user")
    public String findUserByName(Model model, String name){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", activeUser.get());

        Optional<List<User>> users = userService.findUserByName(name);
        Boolean coursesFound = users.get().size()>0;
        model.addAttribute("usersFound", coursesFound);
        model.addAttribute("users", users.get());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("pageNumber", 1);
        model.addAttribute("numberPage", 1);
        model.addAttribute("firstPage", true);
        model.addAttribute("lastPage", true);
        return "admin-users";
    }

    @GetMapping("/ban-user-{id}")
    public String banUser(Model model, @PathVariable Long id){

        model.addAttribute("pageNumber", 0);

        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", activeUser.get());
        model.addAttribute("categories", categoryService.findAll());

        if (activeUser.isPresent() && activeUser.get().isAdmin()){
            userService.banUser(id);
        }

        Page<User> pageUsers = userService.findPageUsers(0, 6);
        model.addAttribute("lastPage", !pageUsers.hasNext());
        model.addAttribute("firstPage", !pageUsers.hasPrevious());
        model.addAttribute("users", pageUsers);
        model.addAttribute("usersFound", pageUsers.hasContent());

        model.addAttribute("numberPage", 1);

        return "admin-users";
    }

    @GetMapping("/unban-user-{id}")
    public String unbanUser(Model model, @PathVariable Long id){

        model.addAttribute("pageNumber", 0);

        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", activeUser.get());
        model.addAttribute("categories", categoryService.findAll());

        if (activeUser.isPresent() && activeUser.get().isAdmin()){
            userService.unbanUser(id);
        }

        Page<User> pageUsers = userService.findPageUsers(0, 6);
        model.addAttribute("lastPage", !pageUsers.hasNext());
        model.addAttribute("firstPage", !pageUsers.hasPrevious());
        model.addAttribute("users", pageUsers);
        model.addAttribute("usersFound", pageUsers.hasContent());

        model.addAttribute("numberPage", 1);

        return "admin-users";
    }

    @GetMapping("/admin-delete-user-{id}")
    public String deleteUser(Model model, @PathVariable Long id){

        model.addAttribute("pageNumber", 0);

        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", activeUser.get());
        model.addAttribute("categories", categoryService.findAll());

        if (activeUser.isPresent() && activeUser.get().isAdmin()){
            userService.adminDeleteUser(id);
        }

        Page<User> pageUsers = userService.findPageUsers(0, 6);
        model.addAttribute("lastPage", !pageUsers.hasNext());
        model.addAttribute("firstPage", !pageUsers.hasPrevious());
        model.addAttribute("users", pageUsers);
        model.addAttribute("usersFound", pageUsers.hasContent());

        model.addAttribute("numberPage", 1);

        if (id.equals(activeUser.get().getId())){
            userService.logout();
            model.addAttribute("activeUser", false);
            model.addAttribute("activeUserAdmin", false);
            return "index";
        }

        return "admin-users";
    }





    @GetMapping("/admin-categories-page-{pageNumber}")
    public String adminCategories(Model model, @PathVariable Integer pageNumber){

        model.addAttribute("pageNumber", pageNumber);

        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", activeUser.get());
        model.addAttribute("categories", categoryService.findAll());

        Page<Category> pageCategories = categoryService.findPageCategories(pageNumber, 6);
        model.addAttribute("lastPage", !pageCategories.hasNext());
        model.addAttribute("firstPage", !pageCategories.hasPrevious());
        model.addAttribute("pageCategories", pageCategories);
        model.addAttribute("categoriesFound", pageCategories.hasContent());

        pageNumber++;
        model.addAttribute("numberPage", pageNumber);

        return "admin-categories";
    }

    @GetMapping("/admin-categories-prev-page-{pageNumber}")
    public String adminCategoriesPrevPage(Model model, @PathVariable Integer pageNumber){
        if (pageNumber>0){
            pageNumber--;
            model.addAttribute("pageNumber", pageNumber);
        }

        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", activeUser.get());

        Page<Category> pageCategories = categoryService.findPageCategories(pageNumber, 6);
        model.addAttribute("categoriesFound", pageCategories.hasContent());
        model.addAttribute("pageCategories", pageCategories);
        model.addAttribute("firstPage", !pageCategories.hasPrevious());
        model.addAttribute("categories", categoryService.findAll());

        pageNumber++;
        model.addAttribute("numberPage", pageNumber);

        return "admin-categories";
    }

    @GetMapping("/admin-categories-next-page-{pageNumber}")
    public String adminCategoriesNextPage(Model model, @PathVariable Integer pageNumber){
        pageNumber++;
        model.addAttribute("pageNumber", pageNumber);

        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", activeUser.get());

        Page<Category> pageCategories = categoryService.findPageCategories(pageNumber, 6);
        model.addAttribute("categoriesFound", pageCategories.hasContent());
        model.addAttribute("lastPage", !pageCategories.hasNext());
        model.addAttribute("pageCategories", pageCategories);
        model.addAttribute("categories", categoryService.findAll());

        pageNumber++;
        model.addAttribute("numberPage", pageNumber);
        return "admin-categories";
    }

    @GetMapping("/admin-search-category")
    public String findCategoryByName(Model model, String name){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("user", activeUser.get());
        model.addAttribute("categories", categoryService.findAll());

        Optional<List<Category>> categoriesByName = categoryService.findCategoriesByName(name);
        Boolean categoriesFound = categoriesByName.get().size()>0;
        model.addAttribute("categoriesFound", categoriesFound);
        model.addAttribute("pageCategories", categoriesByName.get());
        model.addAttribute("pageNumber", 1);
        model.addAttribute("numberPage", 1);
        model.addAttribute("firstPage", true);
        model.addAttribute("lastPage", true);
        return "admin-categories";
    }


}
