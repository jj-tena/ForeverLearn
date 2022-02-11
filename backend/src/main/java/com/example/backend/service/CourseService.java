package com.example.backend.service;

import com.example.backend.model.Category;
import com.example.backend.model.Course;
import com.example.backend.model.Theme;
import com.example.backend.model.User;
import com.example.backend.repository.CourseRepository;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final UserService userService;

    private final CategoryService categoryService;

    public CourseService(CourseRepository courseRepository, UserService userService, CategoryService categoryService) {
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    public List<Course> findCourses(){
        return courseRepository.findAll();
    }

    public Page<Course> findPageCourses(Integer numberPage, Integer content){
        return courseRepository.findAll(PageRequest.of(numberPage, content));
    }

    public Optional<Course> findCourseById(Long id){
        return courseRepository.findById(id);
    }

    public Optional<Course> findCourseByName(String name){
        return courseRepository.findCourseByName(name);
    }

    public List<Course> findCoursesByCategory(Category category){
        return courseRepository.findCoursesByCategory(category);
    }

    public Page<Course> findPageCoursesByCategory(Integer pageNumber, Category category){
        return  courseRepository.findCoursesByCategory(category, PageRequest.of(pageNumber, 12));
    }

    @Transactional
    public Optional<Course> create(String name, String description, Category category, String difficulty, String imagePath, Integer length, Long id) throws IOException {
        Optional<Course> optionalCourse = Optional.empty();
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()){
            Course course = new Course(name, description, category, difficulty, length, user.get());
            Resource image = new ClassPathResource(imagePath);
            course.setPicture(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
            Course savedCourse = courseRepository.save(course);
            userService.addUserCourse(user.get(), savedCourse);
            optionalCourse = Optional.of(savedCourse);
        }
        return optionalCourse;
    }

    @Transactional
    public Optional<Course> createCourse(Course course, String categoryName, MultipartFile image) throws IOException {
        Optional<Course> optionalCourse = Optional.empty();
        Optional<User> optionalUser = userService.getActiveUser();
        Optional<Category> optionalCategory = categoryService.findByName(categoryName);
        if (optionalUser.isPresent() && optionalCategory.isPresent()){
            course.setAuthor(optionalUser.get());
            course.setCategory(optionalCategory.get());
            if (image.getOriginalFilename() != "")  {
                course.setPicture(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
            }
            Course savedCourse = courseRepository.save(course);
            userService.addUserCourse(optionalUser.get(), savedCourse);
            optionalCourse = Optional.of(savedCourse);
        }
        return optionalCourse;
    }

    public void updateCourse(Long courseId, Course newCourse, String categoryName, MultipartFile image) throws IOException {
        Optional<Course> optionalOldCourse = courseRepository.findById(courseId);
        if (optionalOldCourse.isPresent()){
            Course oldCourse = optionalOldCourse.get();
            if (!newCourse.getName().contentEquals("")){
                oldCourse.setName(newCourse.getName());
            } if (!newCourse.getDescription().contentEquals("")){
                oldCourse.setDescription(newCourse.getDescription());
            } if (!newCourse.getDescription().contentEquals("")){
                oldCourse.setDescription(newCourse.getDescription());
            } if (!newCourse.getDifficulty().contentEquals("")){
                oldCourse.setDifficulty(newCourse.getDifficulty());
            }
            oldCourse.setLength(newCourse.getLength());
            Optional<Category> optionalCategory = categoryService.findByName(categoryName);
            optionalCategory.ifPresent(oldCourse::setCategory);
            if (image.getOriginalFilename() != "") {
                oldCourse.setPicture(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
            }
            courseRepository.save(oldCourse);
        }
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public Optional<List<Course>> findCoursesByName(String name) {
        return courseRepository.findCoursesByName(name);
    }

    public void like(Course course){
        course.like();
        courseRepository.save(course);
    }

    public void quitLike(Course course){
        course.quitLike();
        courseRepository.save(course);
    }

    public void dislike(Course course){
        course.dislike();
        courseRepository.save(course);
    }

    public void quitDislike(Course course){
        course.quitDislike();
        courseRepository.save(course);
    }

    public void banCourse(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        optionalCourse.ifPresent(Course::ban);
        courseRepository.save(optionalCourse.get());
    }

    public void unbanCourse(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        optionalCourse.ifPresent(Course::unban);
        courseRepository.save(optionalCourse.get());
    }

    public void adminDeleteCourse(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()){
            Optional<User> userOptional = userService.getActiveUser();
            if (userOptional.isPresent() && userOptional.get().isAdmin()){
                Optional<User> courseOwner = userService.findCourseOwner(optionalCourse.get());
                if (courseOwner.isPresent()){
                    courseOwner.get().deleteUserCourse(optionalCourse.get());
                }
                userService.findUsersByEnrolledCourse(optionalCourse.get()).forEach(user -> user.deleteEnrolledCourse(optionalCourse.get()));
                userService.findUsersByWishedCourse(optionalCourse.get()).forEach(user -> user.deleteWishedCourse(optionalCourse.get()));
                userService.findUsersByCompletedCourse(optionalCourse.get()).forEach(user -> user.deleteCompletedCourse(optionalCourse.get()));
                courseRepository.delete(optionalCourse.get());
            }
        }
    }

    public void addTheme(Theme theme, Course course){
        course.addTheme(theme);
        courseRepository.save(course);
    }
}
