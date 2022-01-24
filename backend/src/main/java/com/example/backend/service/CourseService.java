package com.example.backend.service;

import com.example.backend.model.Category;
import com.example.backend.model.Course;
import com.example.backend.repository.CourseRepository;
import org.hibernate.engine.OptimisticLockStyle;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
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

    public void create(String name, String description, Category category, String difficulty, String imagePath, Integer length) throws IOException {
        Course course = new Course(name, description, category, difficulty, length);
        Resource image = new ClassPathResource(imagePath);
        course.setPicture(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
        courseRepository.save(course);
    }
}
