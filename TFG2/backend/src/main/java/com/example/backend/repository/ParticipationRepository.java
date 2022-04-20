package com.example.backend.repository;

import com.example.backend.model.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    Optional<Participation> findParticipationByStudentIdAndCourseId(Long studentId, Long courseId);

}
