package com.example.backend.repository;

import com.example.backend.model.Comment;
import com.example.backend.model.Participation;
import com.example.backend.model.Post;
import com.example.backend.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    Optional<Participation> findParticipationByStudentIdAndCourseId(Long studentId, Long courseId);

    Boolean existsParticipationByStudentIdAndCourseIdAndLikedPostsContains(Long studentId, Long courseId, Post post);

    Boolean existsParticipationByStudentIdAndCourseIdAndLikedQuestionsContains(Long studentId, Long courseId, Question question);


    Boolean existsParticipationByStudentIdAndCourseIdAndLikedCommentsContains(Long studentId, Long courseId, Comment comment);

    List<Participation> getParticipationsByCourseIdOrderByPointsDesc(Long courseId);

    Participation getParticipationByPostsContains(Post post);

    Participation getParticipationByQuestionsContains(Question question);

}
