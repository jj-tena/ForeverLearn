package com.example.backend.service;

import com.example.backend.model.Category;
import com.example.backend.model.Course;
import com.example.backend.model.User;
import com.example.backend.repository.CourseRepository;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final UserService userService;

    public CourseService(CourseRepository courseRepository, UserService userService) {
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    public List<Course> findCourses(){
        return courseRepository.findAll();
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
}
