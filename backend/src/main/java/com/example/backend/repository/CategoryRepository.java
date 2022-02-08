package com.example.backend.repository;

import com.example.backend.model.Category;
import com.example.backend.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    Page<Category> findAll(Pageable page);

    Optional<List<Category>> findAllByName(String name);
}
