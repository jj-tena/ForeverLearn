package com.example.backend.service;

import com.example.backend.model.*;
import com.example.backend.repository.ParticipationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void addPost(Long courseId, Long userId, Post post){
        Optional<Participation> optionalParticipation = participationRepository.findParticipationByStudentIdAndCourseId(userId, courseId);
        if (optionalParticipation.isPresent()){
            optionalParticipation.get().addPost(post);
            participationRepository.save(optionalParticipation.get());
        }
    }


    public void likePost(Long courseId, Long userId, Post post) {
        Optional<Participation> optionalParticipation = participationRepository.findParticipationByStudentIdAndCourseId(userId, courseId);
        if (optionalParticipation.isPresent()){
            optionalParticipation.get().likePost(post);
            participationRepository.save(optionalParticipation.get());
        }
    }

    public boolean isPostLiked(Long courseId, long userId, Post post) {
        return participationRepository.existsParticipationByStudentIdAndCourseIdAndLikedPostsContains(userId, courseId, post);
    }

    public void quitLikePost(Long courseId, long userId, Post post) {
        Optional<Participation> optionalParticipation = participationRepository.findParticipationByStudentIdAndCourseId(userId, courseId);
        if (optionalParticipation.isPresent()){
            optionalParticipation.get().quitLikePost(post);
            participationRepository.save(optionalParticipation.get());
        }
    }

    public void likeComment(Long courseId, long userId, Comment comment) {
        Optional<Participation> optionalParticipation = participationRepository.findParticipationByStudentIdAndCourseId(userId, courseId);
        if (optionalParticipation.isPresent()){
            optionalParticipation.get().likeComment(comment);
            participationRepository.save(optionalParticipation.get());
        }
    }

    public void quitLikeComment(Long courseId, long userId, Comment comment) {
        Optional<Participation> optionalParticipation = participationRepository.findParticipationByStudentIdAndCourseId(userId, courseId);
        if (optionalParticipation.isPresent()){
            optionalParticipation.get().quitLikeComment(comment);
            participationRepository.save(optionalParticipation.get());
        }
    }

    public boolean isCommentLiked(Long courseId, long userId, Comment comment) {
        return participationRepository.existsParticipationByStudentIdAndCourseIdAndLikedCommentsContains(userId, courseId, comment);
    }

    public List<Participation> getTopGlobal(Long courseId) {
        List<Participation> top = participationRepository.getParticipationsByCourseIdOrderByPointsDesc(courseId);
        if (top.size()>10){
            return top.subList(0,9);
        }
        return top;
    }
}
