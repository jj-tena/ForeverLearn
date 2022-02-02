package com.example.backend.controller;

import com.example.backend.model.Course;
import com.example.backend.model.Theme;
import com.example.backend.model.User;
import com.example.backend.service.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@Controller
public class UserController {

    private final CategoryService categoryService;

    private final UserService userService;

    private final CourseService courseService;

    private final ThemeService themeService;

    private final LessonService lessonService;

    private final RequirementService requirementService;

    private final ObjectiveService objectiveService;

    public UserController(CategoryService categoryService, UserService userService, CourseService courseService, ThemeService themeService, LessonService lessonService, RequirementService requirementService, ObjectiveService objectiveService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.courseService = courseService;
        this.themeService = themeService;
        this.lessonService = lessonService;
        this.requirementService = requirementService;
        this.objectiveService = objectiveService;
    }

    @GetMapping("/user/{id}/profilePhoto")
    public ResponseEntity<Object> downloadUserProfilePhoto(@PathVariable long id) throws SQLException {
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            Resource file = new InputStreamResource(user.get().getProfilePhoto().getBinaryStream());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(user.get().getProfilePhoto().length()).body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user-profile")
    public String userProfileLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "user-profile";
    }

    @GetMapping("/user-edit-account")
    public String userEditAccountLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "user-edit-account";
    }

    @GetMapping("/user-edit-account-profile")
    public String userEditAccountProfileLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "user-edit-account-profile";
    }

    @GetMapping("/user-edit-account-password")
    public String userEditAccountPasswordLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "user-edit-account-password";
    }

    @PostMapping("/update-basic-information")
    public String updateBasicInformation(Model model, User user){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        userService.updateBasicInformation(user);
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "user-profile";
    }

    @PostMapping("/update-profile-information")
    public String updateProfileInformation(Model model, User user, @RequestParam("image") MultipartFile multipartFile) throws SQLException, IOException {
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        userService.updateProfileInformation(user, multipartFile);
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "user-profile";
    }

    @PostMapping("/update-password")
    public String updatePassword(Model model, String password1, String password2){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        userService.updatePassword(password1, password2);
        model.addAttribute("user", userService.getActiveUser());
        model.addAttribute("categories", categoryService.findAll());
        return "user-profile";
    }

    @GetMapping("/instructor-section")
    public String instructorSectionLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("courses", userService.getActiveUser().get().getUserCourses());
        model.addAttribute("categories", categoryService.findAll());
        return "instructor-courses";
    }

    @GetMapping("/instructor-create-course")
    public String instructorCreateCourse(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        return "instructor-create-course";
    }

    @PostMapping("/create-course")
    public String createCourse(Model model, Course course, String categoryName, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        courseService.createCourse(course, categoryName, multipartFile);
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("courses", userService.getActiveUser().get().getUserCourses());
        model.addAttribute("categories", categoryService.findAll());
        return "instructor-courses";
    }

    @GetMapping("/instructor-edit-course-{id}")
    public String instructorEditCourse(Model model, @PathVariable Long id){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        Course demo = courseService.findCourseById(id).get();
        model.addAttribute("course", courseService.findCourseById(id).get());
        return "instructor-edit-course";
    }

    @PostMapping("/edit-course-{courseId}")
    public String editCourse(Model model, Course course, String categoryName, @PathVariable Long courseId, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        courseService.updateCourse(courseId, course, categoryName, multipartFile);
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("courses", userService.getActiveUser().get().getUserCourses());
        model.addAttribute("categories", categoryService.findAll());
        return "instructor-courses";
    }

    @GetMapping("/instructor-delete-course-{id}")
    public String instructorDeleteCourse(Model model, @PathVariable Long id){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        Optional<Course> optionalCourse = courseService.findCourseById(id);
        if ((optionalCourse.isPresent()) && (userService.getActiveUser().isPresent())){
            userService.deleteCourse(userService.getActiveUser().get(), optionalCourse.get());
        }
        model.addAttribute("courses", userService.getActiveUser().get().getUserCourses());
        return "instructor-courses";
    }

    @GetMapping("/student-section")
    public String studentSectionLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("courses", userService.getActiveUser().get().getEnrolledCourses());
        return "student-courses-enrolled";
    }

    @GetMapping("/student-courses-enrolled")
    public String studentCoursesEnrolledLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("courses", userService.getActiveUser().get().getEnrolledCourses());
        model.addAttribute("categories", categoryService.findAll());
        return "student-courses-enrolled";
    }

    @GetMapping("/student-courses-completed")
    public String studentCoursesCompletedLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("courses", userService.getActiveUser().get().getCompletedCourses());
        return "student-courses-completed";
    }

    @GetMapping("/student-courses-wished")
    public String studentCoursesWishedLink(Model model){
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("courses", userService.getActiveUser().get().getWishedCourses());
        return "student-courses-wished";
    }

    @GetMapping("/enroll-course-{courseId}")
    public String enrollCourse(Model model, @PathVariable Long courseId){
        userService.enrollCourse(courseId);
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("courses", userService.getActiveUser().get().getEnrolledCourses());
        model.addAttribute("categories", categoryService.findAll());
        return "student-courses-enrolled";
    }

    @PostMapping("/create-theme-for-course-{courseId}")
    public String createTheme(Model model, String nameTheme, String descriptionTheme, @PathVariable Long courseId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        themeService.createTheme(nameTheme, descriptionTheme, optionalCourse.get());
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @PostMapping("/edit-theme-{themeId}-for-course-{courseId}")
    public String editTheme(Model model, String nameTheme, String descriptionTheme, @PathVariable Long themeId, @PathVariable Long courseId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        themeService.updateTheme(themeId, nameTheme, descriptionTheme);
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @GetMapping("/delete-theme-{themeId}-for-course-{courseId}")
    public String deleteTheme(Model model, @PathVariable Long themeId, @PathVariable Long courseId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        themeService.deleteTheme(themeId, optionalCourse.get());
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @PostMapping("/create-lesson-for-theme-{themeId}-for-course-{courseId}")
    public String createLesson(Model model, String nameLesson, String descriptionLesson, @PathVariable Long themeId, @PathVariable Long courseId){
        Optional<Theme> optionalTheme = themeService.findThemeById(themeId);
        lessonService.createLesson(nameLesson, descriptionLesson, optionalTheme.get());
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        Course demo = courseService.findCourseById(courseId).get();
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }


    @PostMapping("/edit-lesson-{lessonId}-for-theme-{themeId}-for-course-{courseId}")
    public String editLesson(Model model, String nameLesson, String descriptionLesson, @PathVariable Long lessonId, @PathVariable Long themeId, @PathVariable Long courseId){
        Optional<Theme> optionalTheme = themeService.findThemeById(themeId);
        lessonService.updateLesson(lessonId, nameLesson, descriptionLesson, optionalTheme.get());
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @GetMapping("delete-lesson-{lessonId}-for-theme-{themeId}-for-course-{courseId}")
    public String deleteLesson(Model model, @PathVariable Long lessonId, @PathVariable Long themeId, @PathVariable Long courseId){
        Optional<Theme> optionalTheme = themeService.findThemeById(themeId);
        lessonService.deleteLesson(lessonId, optionalTheme.get());
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @PostMapping("/create-requirement-for-course-{courseId}")
    public String createRequirement(Model model, String nameRequirement, @PathVariable Long courseId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        requirementService.createRequirement(nameRequirement, optionalCourse.get());
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @PostMapping("/edit-requirement-{requirementId}-for-course-{courseId}")
    public String editRequirement(Model model, String nameRequirement, @PathVariable Long requirementId, @PathVariable Long courseId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        requirementService.updateRequirement(requirementId, nameRequirement);
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @GetMapping("delete-requirement-{requirementId}-for-course-{courseId}")
    public String deleteRequirement(Model model, @PathVariable Long requirementId, @PathVariable Long courseId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        requirementService.deleteRequirement(requirementId, optionalCourse.get());
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @PostMapping("/create-objective-for-course-{courseId}")
    public String createObjective(Model model, String nameObjective, @PathVariable Long courseId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        objectiveService.createObjective(nameObjective, optionalCourse.get());
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @PostMapping("/edit-objective-{objectiveId}-for-course-{courseId}")
    public String editObjective(Model model, String nameObjective, @PathVariable Long objectiveId, @PathVariable Long courseId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        objectiveService.updateObjective(objectiveId, nameObjective);
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @GetMapping("/delete-objective-{objectiveId}-for-course-{courseId}")
    public String deleteObjective(Model model, @PathVariable Long objectiveId, @PathVariable Long courseId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        objectiveService.deleteObjective(objectiveId, optionalCourse.get());
        model.addAttribute("activeUser", userService.getActiveUser().isPresent());
        model.addAttribute("user", userService.getActiveUser().get());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }
}
