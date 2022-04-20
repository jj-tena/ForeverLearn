package com.example.backend.service;

import com.example.backend.model.Course;
import com.example.backend.model.Participation;
import com.example.backend.model.User;
import com.example.backend.repository.ParticipationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParticipationService {

    private final ParticipationRepository participationRepository;

    public ParticipationService(ParticipationRepository participationRepository) {
        this.participationRepository = participationRepository;
    }

    public void createParticipation(User user, Course course){
        Participation participation = new Participation(user, course);
        participationRepository.save(participation);
    }

    public void deleteParticipation(Long id){
        participationRepository.deleteById(id);
    }

    public Optional<Participation> existsParticipation(Long userId, Long courseId){
        return participationRepository.findParticipationByStudentIdAndCourseId(userId, courseId);
    }


}
