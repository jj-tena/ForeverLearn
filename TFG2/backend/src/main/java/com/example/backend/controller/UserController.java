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
import java.util.*;

@Controller
public class UserController {

    private final CategoryService categoryService;

    private final UserService userService;

    private final CourseService courseService;

    private final ThemeService themeService;

    private final LessonService lessonService;

    private final RequirementService requirementService;

    private final ObjectiveService objectiveService;

    private final ParticipationService participationService;

    public UserController(CategoryService categoryService, UserService userService, CourseService courseService, ThemeService themeService, LessonService lessonService, RequirementService requirementService, ObjectiveService objectiveService, ParticipationService participationService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.courseService = courseService;
        this.themeService = themeService;
        this.lessonService = lessonService;
        this.requirementService = requirementService;
        this.objectiveService = objectiveService;
        this.participationService = participationService;
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
        setHeaderInfo(model);
        return "user-profile-page";
    }

    @GetMapping("/instructor-section")
    public String instructorSectionLink(Model model){
        setHeaderInfo(model);
        List<Course> userCourses = userService.getActiveUser().get().getUserCourses();
        Boolean coursesFound = userCourses.size()>0;
        model.addAttribute("coursesFound", coursesFound);
        model.addAttribute("courses", userCourses);
        return "instructor-courses";
    }

    @GetMapping("/instructor-create-course")
    public String instructorCreateCourse(Model model){
        setHeaderInfo(model);
        return "instructor-create-course-page";
    }

    @PostMapping("/create-course")
    public String createCourse(Model model, Course course, String categoryName, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        courseService.createCourse(course, categoryName, multipartFile);
        setHeaderInfo(model);
        List<Course> userCourses = userService.getActiveUser().get().getUserCourses();
        Boolean coursesFound = userCourses.size()>0;
        model.addAttribute("coursesFound", coursesFound);
        model.addAttribute("courses", userCourses);
        return "instructor-courses";
    }

    @GetMapping("/instructor-edit-course-{id}")
    public String instructorEditCourse(Model model, @PathVariable Long id){
        setHeaderInfo(model);
        Course demo = courseService.findCourseById(id).get();
        model.addAttribute("course", courseService.findCourseById(id).get());
        return "instructor-edit-course";
    }

    @PostMapping("/edit-course-{courseId}")
    public String editCourse(Model model, Course course, String categoryName, @PathVariable Long courseId, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        courseService.updateCourse(courseId, course, categoryName, multipartFile);
        setHeaderInfo(model);
        List<Course> userCourses = userService.getActiveUser().get().getUserCourses();
        Boolean coursesFound = userCourses.size()>0;
        model.addAttribute("coursesFound", coursesFound);
        model.addAttribute("courses", userCourses);
        return "instructor-courses";
    }

    @GetMapping("/instructor-delete-course-{id}")
    public String instructorDeleteCourse(Model model, @PathVariable Long id){
        setHeaderInfo(model);
        Optional<Course> optionalCourse = courseService.findCourseById(id);
        if ((optionalCourse.isPresent()) && (userService.getActiveUser().isPresent())){
            userService.deleteCourse(userService.getActiveUser().get(), optionalCourse.get());
        }
        List<Course> userCourses = userService.getActiveUser().get().getUserCourses();
        Boolean coursesFound = userCourses.size()>0;
        model.addAttribute("coursesFound", coursesFound);
        model.addAttribute("courses", userCourses);
        return "instructor-courses";
    }

    @GetMapping("/student-section")
    public String studentSectionLink(Model model){
        setHeaderInfo(model);
        List<Course> enrolledCourses = userService.getActiveUser().get().getEnrolledCourses();
        Boolean coursesFound = enrolledCourses.size()>0;
        model.addAttribute("coursesFound", coursesFound);
        model.addAttribute("courses", enrolledCourses);
        return "student-courses-enrolled-page";
    }

    @GetMapping("/student-courses-enrolled")
    public String studentCoursesEnrolledLink(Model model){
        setHeaderInfo(model);
        List<Course> enrolledCourses = userService.getActiveUser().get().getEnrolledCourses();
        Boolean coursesFound = enrolledCourses.size()>0;
        model.addAttribute("coursesFound", coursesFound);
        model.addAttribute("courses", enrolledCourses);
        return "student-courses-enrolled-page";
    }

    @GetMapping("/student-courses-completed")
    public String studentCoursesCompletedLink(Model model){
        setHeaderInfo(model);
        List<Course> completedCourses = userService.getActiveUser().get().getCompletedCourses();
        Boolean coursesFound = completedCourses.size()>0;
        model.addAttribute("coursesFound", coursesFound);
        model.addAttribute("courses", completedCourses);
        return "student-courses-completed-page";
    }

    @GetMapping("/student-courses-wished")
    public String studentCoursesWishedLink(Model model){
        setHeaderInfo(model);
        model.addAttribute("courses", userService.getActiveUser().get().getWishedCourses());
        List<Course> wishedCourses = userService.getActiveUser().get().getWishedCourses();
        Boolean coursesFound = wishedCourses.size()>0;
        model.addAttribute("coursesFound", coursesFound);
        model.addAttribute("courses", wishedCourses);
        return "student-courses-wished-page";
    }

    @GetMapping("/enroll-course-{courseId}")
    public String enrollCourse(Model model, @PathVariable Long courseId){
        userService.enrollCourse(courseId);
        setHeaderInfo(model);
        List<Course> enrolledCourses = new LinkedList<>();
        Optional<User> user = userService.getActiveUser();
        if (user.isPresent()){
            enrolledCourses = user.get().getEnrolledCourses();
            Optional<Course> course = courseService.findCourseById(courseId);
            course.ifPresent(value -> participationService.createParticipation(user.get(), value));
        }
        Boolean coursesFound = !enrolledCourses.isEmpty();
        model.addAttribute("coursesFound", coursesFound);
        model.addAttribute("courses", enrolledCourses);
        return "student-courses-enrolled-page";
    }

    @GetMapping("/unenroll-course-{courseId}")
    public String unenrollCourse(Model model, @PathVariable Long courseId){
        userService.unenrollCourse(courseId);
        setHeaderInfo(model);
        List<Course> enrolledCourses = new LinkedList<>();
        Optional<User> user = userService.getActiveUser();
        if (user.isPresent()){
            enrolledCourses = user.get().getEnrolledCourses();
        }
        Boolean coursesFound = !enrolledCourses.isEmpty();
        model.addAttribute("coursesFound", coursesFound);
        model.addAttribute("courses", enrolledCourses);
        return "student-courses-enrolled-page";
    }

    @GetMapping("/wish-course-{courseId}")
    public String wishCourse(Model model, @PathVariable Long courseId){
        userService.wishCourse(courseId);
        setHeaderInfo(model);
        List<Course> wishedCourses = userService.getActiveUser().get().getWishedCourses();
        Boolean coursesFound = wishedCourses.size()>0;
        model.addAttribute("coursesFound", coursesFound);
        model.addAttribute("courses", wishedCourses);
        return "student-courses-wished-page";
    }

    @GetMapping("/unwish-course-{courseId}")
    public String deleteWishCourse(Model model, @PathVariable Long courseId){
        userService.unwishCourse(courseId);
        setHeaderInfo(model);
        List<Course> wishedCourses = userService.getActiveUser().get().getWishedCourses();
        Boolean coursesFound = wishedCourses.size()>0;
        model.addAttribute("coursesFound", coursesFound);
        model.addAttribute("courses", wishedCourses);
        return "student-courses-wished-page";
    }

    @PostMapping("/create-theme-for-course-{courseId}")
    public String createTheme(Model model, String nameTheme, String descriptionTheme, @PathVariable Long courseId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        themeService.createTheme(nameTheme, descriptionTheme, optionalCourse.get());
        setHeaderInfo(model);
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @PostMapping("/edit-theme-{themeId}-for-course-{courseId}")
    public String editTheme(Model model, String nameTheme, String descriptionTheme, @PathVariable Long themeId, @PathVariable Long courseId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        themeService.updateTheme(themeId, nameTheme, descriptionTheme);
        setHeaderInfo(model);
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @GetMapping("/delete-theme-{themeId}-for-course-{courseId}")
    public String deleteTheme(Model model, @PathVariable Long themeId, @PathVariable Long courseId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        themeService.deleteTheme(themeId, optionalCourse.get());
        setHeaderInfo(model);
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @PostMapping("/create-lesson-for-theme-{themeId}-for-course-{courseId}")
    public String createLesson(Model model, String nameLesson, String descriptionLesson, String iframeLesson, @PathVariable Long themeId, @PathVariable Long courseId){
        Optional<Theme> optionalTheme = themeService.findThemeById(themeId);
        lessonService.createLesson(nameLesson, descriptionLesson, iframeLesson, optionalTheme.get());
        setHeaderInfo(model);
        Course demo = courseService.findCourseById(courseId).get();
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }


    @PostMapping("/edit-lesson-{lessonId}-for-theme-{themeId}-for-course-{courseId}")
    public String editLesson(Model model, String nameLesson, String descriptionLesson, String iframeLesson, @PathVariable Long lessonId, @PathVariable Long themeId, @PathVariable Long courseId){
        Optional<Theme> optionalTheme = themeService.findThemeById(themeId);
        lessonService.updateLesson(lessonId, nameLesson, descriptionLesson, iframeLesson);
        setHeaderInfo(model);
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @GetMapping("delete-lesson-{lessonId}-for-theme-{themeId}-for-course-{courseId}")
    public String deleteLesson(Model model, @PathVariable Long lessonId, @PathVariable Long themeId, @PathVariable Long courseId){
        Optional<Theme> optionalTheme = themeService.findThemeById(themeId);
        lessonService.deleteLesson(lessonId, optionalTheme.get());
        setHeaderInfo(model);
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @PostMapping("/create-requirement-for-course-{courseId}")
    public String createRequirement(Model model, String nameRequirement, @PathVariable Long courseId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        requirementService.createRequirement(nameRequirement, optionalCourse.get());
        setHeaderInfo(model);
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @PostMapping("/edit-requirement-{requirementId}-for-course-{courseId}")
    public String editRequirement(Model model, String nameRequirement, @PathVariable Long requirementId, @PathVariable Long courseId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        requirementService.updateRequirement(requirementId, nameRequirement);
        setHeaderInfo(model);
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @GetMapping("delete-requirement-{requirementId}-for-course-{courseId}")
    public String deleteRequirement(Model model, @PathVariable Long requirementId, @PathVariable Long courseId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        requirementService.deleteRequirement(requirementId, optionalCourse.get());
        setHeaderInfo(model);
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @PostMapping("/create-objective-for-course-{courseId}")
    public String createObjective(Model model, String nameObjective, @PathVariable Long courseId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        objectiveService.createObjective(nameObjective, optionalCourse.get());
        setHeaderInfo(model);
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @PostMapping("/edit-objective-{objectiveId}-for-course-{courseId}")
    public String editObjective(Model model, String nameObjective, @PathVariable Long objectiveId, @PathVariable Long courseId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        objectiveService.updateObjective(objectiveId, nameObjective);
        setHeaderInfo(model);
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @GetMapping("/delete-objective-{objectiveId}-for-course-{courseId}")
    public String deleteObjective(Model model, @PathVariable Long objectiveId, @PathVariable Long courseId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        objectiveService.deleteObjective(objectiveId, optionalCourse.get());
        setHeaderInfo(model);
        model.addAttribute("course", courseService.findCourseById(courseId).get());
        return "instructor-edit-course";
    }

    @GetMapping("like-course-{courseId}")
    public String likeCourse(Model model, @PathVariable Long courseId){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("categories", categoryService.findAll());
        Optional<Course> course = courseService.findCourseById(courseId);
        if (course.isPresent()){
            model.addAttribute("course", course.get());
            Boolean courseOwner = false;
            Boolean courseEnrolled = false;
            Boolean courseWished = false;
            if (activeUser.isPresent()){
                courseOwner = userService.isCourseOwner(activeUser.get(), course.get());
                courseEnrolled = userService.isCourseEnrolled(activeUser.get(), course.get());
                courseWished = userService.isCourseWished(activeUser.get(), course.get());
            }
            model.addAttribute("courseOwner", courseOwner);
            model.addAttribute("courseEnrolled", courseEnrolled);
            model.addAttribute("courseWished", courseWished);

            userService.likeCourse(activeUser.get(), course.get());
            model.addAttribute("likedCourse", true);

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

            return "course";
        } else {
            return "index";
        }
    }

    @GetMapping("quit-like-course-{courseId}")
    public String quitLikeCourse(Model model, @PathVariable Long courseId){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("categories", categoryService.findAll());
        Optional<Course> course = courseService.findCourseById(courseId);
        if (course.isPresent()){
            model.addAttribute("course", course.get());
            Boolean courseOwner = false;
            Boolean courseEnrolled = false;
            Boolean courseWished = false;
            if (activeUser.isPresent()){
                courseOwner = userService.isCourseOwner(activeUser.get(), course.get());
                courseEnrolled = userService.isCourseEnrolled(activeUser.get(), course.get());
                courseWished = userService.isCourseWished(activeUser.get(), course.get());
            }
            model.addAttribute("courseOwner", courseOwner);
            model.addAttribute("courseEnrolled", courseEnrolled);
            model.addAttribute("courseWished", courseWished);

            userService.quitLikeCourse(activeUser.get(), course.get());

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

            return "course";
        } else {
            return "index";
        }
    }

    @GetMapping("dislike-course-{courseId}")
    public String dislikeCourse(Model model, @PathVariable Long courseId){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("categories", categoryService.findAll());
        Optional<Course> course = courseService.findCourseById(courseId);
        if (course.isPresent()){
            model.addAttribute("course", course.get());
            Boolean courseOwner = false;
            Boolean courseEnrolled = false;
            Boolean courseWished = false;
            if (activeUser.isPresent()){
                courseOwner = userService.isCourseOwner(activeUser.get(), course.get());
                courseEnrolled = userService.isCourseEnrolled(activeUser.get(), course.get());
                courseWished = userService.isCourseWished(activeUser.get(), course.get());
            }
            model.addAttribute("courseOwner", courseOwner);
            model.addAttribute("courseEnrolled", courseEnrolled);
            model.addAttribute("courseWished", courseWished);

            userService.dislikeCourse(activeUser.get(), course.get());
            model.addAttribute("dislikedCourse", true);

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

            return "course";
        } else {
            return "index";
        }
    }

    @GetMapping("quit-dislike-course-{courseId}")
    public String quitDislikeCourse(Model model, @PathVariable Long courseId){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        activeUser.ifPresent(user -> model.addAttribute("activeUserAdmin", user.isAdmin()));
        model.addAttribute("categories", categoryService.findAll());
        Optional<Course> course = courseService.findCourseById(courseId);
        if (course.isPresent()){
            model.addAttribute("course", course.get());
            Boolean courseOwner = false;
            Boolean courseEnrolled = false;
            Boolean courseWished = false;
            if (activeUser.isPresent()){
                courseOwner = userService.isCourseOwner(activeUser.get(), course.get());
                courseEnrolled = userService.isCourseEnrolled(activeUser.get(), course.get());
                courseWished = userService.isCourseWished(activeUser.get(), course.get());
            }
            model.addAttribute("courseOwner", courseOwner);
            model.addAttribute("courseEnrolled", courseEnrolled);
            model.addAttribute("courseWished", courseWished);

            userService.quitDislikeCourse(activeUser.get(), course.get());

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
            return "course";
        } else {
            return "index";
        }
    }

}
