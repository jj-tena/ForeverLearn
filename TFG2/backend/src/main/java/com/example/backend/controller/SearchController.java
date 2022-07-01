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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/category-{id}-library-page-{pageNumber}")
    public String libraryByCategory(Model model, @PathVariable Long id, @PathVariable Integer pageNumber){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));

        Page<Course> courses;
        int results = 0;
        if (id.equals(Long.valueOf("-1"))){
            model.addAttribute("activeCategory", -1);
            courses =  courseService.findPageCourses(pageNumber, 12);
            model.addAttribute("courses", courses);
            model.addAttribute("firstPage", !courses.hasPrevious());
            model.addAttribute("lastPage", !courses.hasNext());
            model.addAttribute("coursesFound", courses.hasContent());
            results = courses.getNumberOfElements();
        } else {
            Optional<Category> category = categoryService.findById(id);
            if (category.isPresent()) {
                courses = courseService.findPageCoursesByCategory(pageNumber, category.get());
                model.addAttribute("courses", courses);
                model.addAttribute("activeCategory", category.get().getId());
                model.addAttribute("firstPage", !courses.hasPrevious());
                model.addAttribute("lastPage", !courses.hasNext());
                model.addAttribute("coursesFound", courses.hasContent());
                results = courses.getNumberOfElements();
            }
        }

        model.addAttribute("results", results);
        model.addAttribute("categories", categoryService.findAll());
        pageNumber++;
        model.addAttribute("numberPage", pageNumber);
        return "library";
    }

    @GetMapping("/category-{id}-library-prev-page-{pageNumber}")
    public String libraryPrevPageByCategory(Model model, @PathVariable Long id, @PathVariable Integer pageNumber){
        if (pageNumber>0){
            pageNumber--;
        }
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));

        Page<Course> courses;
        int results = 0;
        if (id.equals(Long.valueOf("-1"))){
            model.addAttribute("activeCategory", -1);
            courses =  courseService.findPageCourses(pageNumber, 12);
            model.addAttribute("courses", courses);
            model.addAttribute("firstPage", !courses.hasPrevious());
            model.addAttribute("lastPage", !courses.hasNext());
            model.addAttribute("coursesFound", courses.hasContent());
            results = courses.getNumberOfElements();
        } else {
            Optional<Category> category = categoryService.findById(id);
            if (category.isPresent()) {
                courses = courseService.findPageCoursesByCategory(pageNumber, category.get());
                model.addAttribute("courses", courses);
                model.addAttribute("activeCategory", category.get().getId());
                model.addAttribute("firstPage", !courses.hasPrevious());
                model.addAttribute("lastPage", !courses.hasNext());
                model.addAttribute("coursesFound", courses.hasContent());
                results = courses.getNumberOfElements();
            }
        }

        model.addAttribute("results", results);
        model.addAttribute("categories", categoryService.findAll());
        pageNumber++;
        model.addAttribute("numberPage", pageNumber);
        return "library";
    }

    @GetMapping("/category-{id}-library-next-page-{pageNumber}")
    public String libraryNextPageByCategory(Model model, @PathVariable Long id, @PathVariable Integer pageNumber){
        pageNumber++;
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));

        Page<Course> courses;
        int results = 0;
        if (id.equals(Long.valueOf("-1"))){
            model.addAttribute("activeCategory", -1);
            courses =  courseService.findPageCourses(pageNumber, 12);
            model.addAttribute("courses", courses);
            model.addAttribute("firstPage", !courses.hasPrevious());
            model.addAttribute("lastPage", !courses.hasNext());
            model.addAttribute("coursesFound", courses.hasContent());
            results = courses.getNumberOfElements();
        } else {
            Optional<Category> category = categoryService.findById(id);
            if (category.isPresent()) {
                courses = courseService.findPageCoursesByCategory(pageNumber, category.get());
                model.addAttribute("courses", courses);
                model.addAttribute("activeCategory", category.get().getId());
                model.addAttribute("firstPage", !courses.hasPrevious());
                model.addAttribute("lastPage", !courses.hasNext());
                model.addAttribute("coursesFound", courses.hasContent());
                results = courses.getNumberOfElements();
            }
        }

        model.addAttribute("results", results);
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
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
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

        model.addAttribute("activeCategory", -1);

        return "library";
    }

    @GetMapping("/course-{id}")
    public String getCourse(Model model, @PathVariable Long id){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("categories", categoryService.findAll());
        Optional<Course> course = courseService.findCourseById(id);
        if (course.isPresent()){
            if (course.get().isBanned()){
                return "/error";
            }
            model.addAttribute("course", course.get());
            Boolean courseOwner = false;
            Boolean courseEnrolled = false;
            Boolean courseWished = false;
            Boolean courseCompleted = false;
            if (activeUser.isPresent()){
                courseOwner = userService.isCourseOwner(activeUser.get(), course.get());
                courseEnrolled = userService.isCourseEnrolled(activeUser.get(), course.get());
                courseWished = userService.isCourseWished(activeUser.get(), course.get());
                courseCompleted = userService.isCourseCompleted(activeUser.get(), course.get());
            }
            model.addAttribute("courseOwner", courseOwner);
            model.addAttribute("courseEnrolled", courseEnrolled);
            model.addAttribute("courseWished", courseWished);
            model.addAttribute("courseCompleted", courseCompleted);
            if (activeUser.isPresent()){
                model.addAttribute("likedCourse", userService.isCourseLiked(activeUser.get(), course.get()));
                model.addAttribute("dislikedCourse", userService.isCourseDisliked(activeUser.get(), course.get()));
            }

            Integer likes = course.get().getLikes();
            Integer dislikes = course.get().getDislikes();
            Integer totalVotes = likes + dislikes;
            Integer effectiveLikes = likes - dislikes;
            if (effectiveLikes <= 0){
                model.addAttribute("borderStars", Arrays.asList(1,1,1,1,1));
            } else {
                Integer numFilledStars = ((effectiveLikes*5)/totalVotes);
                if (numFilledStars.equals(5)){
                    model.addAttribute("filledStars", Arrays.asList(1,1,1,1,1));
                } else {
                    Integer numBorderStars = 5 - numFilledStars;
                    ArrayList<Integer> filledStars = new ArrayList<>();
                    for (int i = 0; i < numFilledStars; i++) {
                        filledStars.add(1);
                    }
                    model.addAttribute("filledStars", filledStars);
                    ArrayList<Integer> borderStars = new ArrayList<>();
                    for (int i = 0; i < numBorderStars; i++) {
                        borderStars.add(1);
                    }
                    model.addAttribute("borderStars", borderStars);
                }
            }
            model.addAttribute("hasThemes", !course.get().getThemes().isEmpty());
            model.addAttribute("hasRequirements", !course.get().getRequirements().isEmpty());
            model.addAttribute("hasObjectives", !course.get().getObjectives().isEmpty());

            return "course";
        } else {
            return "index";
        }
    }

    @GetMapping("/instructor-profile-{id}")
    public String getInstructorProfile(Model model, @PathVariable Long id){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("categories", categoryService.findAll());
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()){
            model.addAttribute("user", user.get());
            model.addAttribute("courses", user.get().getUserCourses());
            Boolean hasFacebook = !user.get().getFacebook().contentEquals("");
            model.addAttribute("hasFacebook", hasFacebook);
            Boolean hasTwitter = !user.get().getTwitter().contentEquals("");
            model.addAttribute("hasTwitter", hasTwitter);
            Boolean hasYoutube = !user.get().getYoutube().contentEquals("");
            model.addAttribute("hasYoutube", hasYoutube);
            return "instructor-profile";
        } else {
            return "index";
        }
    }

    @GetMapping("/lesson-{lessonId}-from-course-{courseId}")
    public String getLesson(Model model, @PathVariable Long lessonId, @PathVariable Long courseId){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
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



}
