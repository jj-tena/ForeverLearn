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
import java.util.*;

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

    @GetMapping("/library-page-{pageNumber}")
    public String librayLink(Model model, @PathVariable Integer pageNumber){
        if (pageNumber==0){
            model.addAttribute("firstPage", true);
        }
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        List<Course> list = courseService.findCourses();
        model.addAttribute("results", list.size());
        Boolean coursesFound = list.size()>0;
        model.addAttribute("coursesFound", coursesFound);
        model.addAttribute("courses", courseService.findPageCourses(pageNumber));
        model.addAttribute("categories", categoryService.findAll());
        pageNumber++;
        model.addAttribute("numberPage", pageNumber);

        return "library";
    }

    @GetMapping("/library-prev-page-{pageNumber}")
    public String librayLinkPrevPage(Model model, @PathVariable Integer pageNumber){
        if (pageNumber==0){
            model.addAttribute("firstPage", true);
        }
        if (pageNumber>0){
            pageNumber--;
        }
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        List<Course> list = courseService.findCourses();
        model.addAttribute("results", list.size());
        Boolean coursesFound = list.size()>0;
        model.addAttribute("coursesFound", coursesFound);
        model.addAttribute("courses", courseService.findPageCourses(pageNumber));
        model.addAttribute("categories", categoryService.findAll());
        pageNumber++;
        model.addAttribute("numberPage", pageNumber);
        return "library";
    }

    @GetMapping("/library-next-page-{pageNumber}")
    public String librayLinkNextPage(Model model, @PathVariable Integer pageNumber){
        pageNumber++;
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        List<Course> list = courseService.findCourses();
        model.addAttribute("results", list.size());
        Boolean coursesFound = list.size()>0;
        model.addAttribute("coursesFound", coursesFound);
        if ( ( ((list.size()/12) == pageNumber + 1) && ((list.size() % 12)==0) ) || ( ((list.size()/12) < pageNumber + 1) && ((list.size() % 12)>0) ) ) {
            model.addAttribute("lastPage", true);
        }
        model.addAttribute("courses", courseService.findPageCourses(pageNumber));
        model.addAttribute("categories", categoryService.findAll());
        pageNumber++;
        model.addAttribute("numberPage", pageNumber);
        return "library";
    }

    @GetMapping("/category-{id}-library-page-{pageNumber}")
    public String libraryByCategory(Model model, @PathVariable Long id, @PathVariable Integer pageNumber){
        if (pageNumber==0){
            model.addAttribute("firstPage", true);
        }
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        Optional<Category> category = categoryService.findById(id);
        List<Course> list = new LinkedList<>();
        if (category.isPresent()) {
            list = courseService.findCoursesByCategory(category.get());
            Boolean coursesFound = list.size()>0;
            model.addAttribute("coursesFound", coursesFound);
            if ( ( ((list.size()/12) == pageNumber + 1) && ((list.size() % 12)==0) ) || ( ((list.size()/12) < pageNumber + 1) && ((list.size() % 12)>0) ) ) {
                model.addAttribute("lastPage", true);
            }
            model.addAttribute("courses", courseService.findPageCoursesByCategory(pageNumber, category.get()));
            model.addAttribute("activeCategory", category.get().getId());
        }
        model.addAttribute("results", list.size());
        model.addAttribute("categories", categoryService.findAll());
        pageNumber++;
        model.addAttribute("numberPage", pageNumber);
        return "library";
    }

    @GetMapping("/category-{id}-library-prev-page-{pageNumber}")
    public String libraryPrevPageByCategory(Model model, @PathVariable Long id, @PathVariable Integer pageNumber){
        if (pageNumber==0){
            model.addAttribute("firstPage", true);
        }
        if (pageNumber>0){
            pageNumber--;
        }
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        Optional<Category> category = categoryService.findById(id);
        List<Course> list = new LinkedList<>();
        if (category.isPresent()) {
            list = courseService.findCoursesByCategory(category.get());
            model.addAttribute("courses", courseService.findPageCoursesByCategory(pageNumber, category.get()));
            model.addAttribute("activeCategory", category.get().getId());
        }
        model.addAttribute("results", list.size());
        Boolean coursesFound = list.size()>0;
        model.addAttribute("coursesFound", coursesFound);
        model.addAttribute("categories", categoryService.findAll());
        pageNumber++;
        model.addAttribute("numberPage", pageNumber);
        return "library";
    }

    @GetMapping("/category-{id}-library-next-page-{pageNumber}")
    public String libraryNextPageByCategory(Model model, @PathVariable Long id, @PathVariable Integer pageNumber){
        pageNumber++;
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        Optional<Category> category = categoryService.findById(id);
        List<Course> list = new LinkedList<>();
        if (category.isPresent()) {
            list = courseService.findCoursesByCategory(category.get());
            if ( ( ((list.size()/12) == pageNumber + 1) && ((list.size() % 12)==0) ) || ( ((list.size()/12) < pageNumber + 1) && ((list.size() % 12)>0) ) ) {
                model.addAttribute("lastPage", true);
            }
            model.addAttribute("courses", courseService.findPageCoursesByCategory(pageNumber, category.get()));
            model.addAttribute("activeCategory", category.get().getId());
        }
        model.addAttribute("results", list.size());
        Boolean coursesFound = list.size()>0;
        model.addAttribute("coursesFound", coursesFound);
        model.addAttribute("categories", categoryService.findAll());
        pageNumber++;
        model.addAttribute("numberPage", pageNumber);
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
        Boolean coursesFound = list.size()>0;
        model.addAttribute("coursesFound", coursesFound);
        model.addAttribute("courses", list);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("pageNumber", 1);
        model.addAttribute("numberPage", 1);
        model.addAttribute("firstPage", true);
        model.addAttribute("lastPage", true);
        return "library";
    }

    @GetMapping("/course-{id}")
    public String getCourse(Model model, @PathVariable Long id){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("categories", categoryService.findAll());
        Optional<Course> course = courseService.findCourseById(id);
        if (course.isPresent()){
            model.addAttribute("course", course.get());
            Boolean courseOwner = false;
            Boolean courseEnrolled = false;
            if (userService.getActiveUser().isPresent()){
                courseOwner = userService.isCourseOwner(userService.getActiveUser().get(), course.get());
                courseEnrolled = userService.isCourseEnrolled(userService.getActiveUser().get(), course.get());
            }
            model.addAttribute("courseOwner", courseOwner);
            model.addAttribute("courseEnrolled", courseEnrolled);
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
        Boolean coursesFound = list.size()>0;
        model.addAttribute("coursesFound", coursesFound);
        model.addAttribute("courses", list);
        model.addAttribute("categories", categoryService.findAll());
        return "library";
    }


}
