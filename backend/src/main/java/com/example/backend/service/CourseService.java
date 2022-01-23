package com.example.backend.service;

import com.example.backend.model.Course;
import com.example.backend.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findCoursesByCategory(Long categoryId){
        return courseRepository.findCoursesByCategory(categoryId);
    }
}
