package com.example.backend.repository;

import com.example.backend.model.Category;
import com.example.backend.model.Course;
import com.example.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmailAndPassword(String email, String password);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(Long id);

    Optional<User> findUserByUserCoursesContains(Course course);

    List<User> findUsersByEnrolledCoursesContaining(Course course);

    List<User> findUsersByCompletedCoursesContaining(Course course);

    List<User> findUsersByWishedCoursesContaining(Course course);

    Page<User> findAll(Pageable page);


}
