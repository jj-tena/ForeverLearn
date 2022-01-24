package com.example.backend.repository;

import com.example.backend.model.Category;
import com.example.backend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findCoursesByCategory(Category category);

    Optional<Course> findCourseByName(String name);
}
