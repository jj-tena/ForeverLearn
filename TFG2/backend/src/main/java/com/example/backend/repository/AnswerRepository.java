package com.example.backend.repository;

import com.example.backend.model.Answer;
import com.example.backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
