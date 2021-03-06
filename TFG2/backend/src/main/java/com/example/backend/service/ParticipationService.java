package com.example.backend.service;

import com.example.backend.model.*;
import com.example.backend.repository.ParticipationRepository;
import org.springframework.stereotype.Service;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.ArrayList;
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

    public void addQuestion(Long courseId, Long userId, Question question){
        Optional<Participation> optionalParticipation = participationRepository.findParticipationByStudentIdAndCourseId(userId, courseId);
        if (optionalParticipation.isPresent()){
            optionalParticipation.get().addQuestion(question);
            participationRepository.save(optionalParticipation.get());
        }
    }


    public void likePost(Long courseId, Long userId, Post post) {
        Optional<Participation> optionalParticipation = participationRepository.findParticipationByStudentIdAndCourseId(userId, courseId);
        if (optionalParticipation.isPresent()){
            optionalParticipation.get().likePost(post);
            participationRepository.getParticipationByPostsContains(post).receiveLikeInPost();
            participationRepository.save(optionalParticipation.get());
        }
    }

    public void likeQuestion(Long courseId, Long userId, Question question) {
        Optional<Participation> optionalParticipation = participationRepository.findParticipationByStudentIdAndCourseId(userId, courseId);
        if (optionalParticipation.isPresent()){
            optionalParticipation.get().likeQuestion(question);
            participationRepository.getParticipationByQuestionsContains(question).receiveLikeInQuestion();
            participationRepository.save(optionalParticipation.get());
        }
    }

    public boolean isPostLiked(Long courseId, long userId, Post post) {
        return participationRepository.existsParticipationByStudentIdAndCourseIdAndLikedPostsContains(userId, courseId, post);
    }

    public boolean isQuestionLiked(Long courseId, long userId, Question question) {
        return participationRepository.existsParticipationByStudentIdAndCourseIdAndLikedQuestionsContains(userId, courseId, question);
    }

    public void quitLikePost(Long courseId, long userId, Post post) {
        Optional<Participation> optionalParticipation = participationRepository.findParticipationByStudentIdAndCourseId(userId, courseId);
        if (optionalParticipation.isPresent()){
            optionalParticipation.get().quitLikePost(post);
            participationRepository.save(optionalParticipation.get());
        }
    }

    public void quitLikeQuestion(Long courseId, long userId, Question question) {
        Optional<Participation> optionalParticipation = participationRepository.findParticipationByStudentIdAndCourseId(userId, courseId);
        if (optionalParticipation.isPresent()){
            optionalParticipation.get().quitLikeQuestion(question);
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
            return top.subList(0,10);
        }
        return top;
    }

    public List<Participation> getTopRelative(Long courseId, Participation participation) {
        List<Participation> top = participationRepository.getParticipationsByCourseIdOrderByPointsDesc(courseId);
        if (top.contains(participation)){
            int index = top.indexOf(participation);
            if (index<=9){
                return top;
            } else {
                return top.subList(index-9,index+1);
            }
        }
        return new ArrayList<>();
    }

    public void checkViewsInPosts(Post post) {
        Participation participation = participationRepository.getParticipationByPostsContains(post);
        participation.checkViewsInPosts();
        participationRepository.save(participation);
    }

    public void checkViewsInQuestions(Question question) {
        Participation participation = participationRepository.getParticipationByQuestionsContains(question);
        participation.checkViewsInQuestions();
        participationRepository.save(participation);
    }

    public void receiveComment(Participation participation){
        participation.receiveComment();
        participationRepository.save(participation);
    }

    public void receiveAnswer(Participation participation){
        participation.receiveAnswer();
        participationRepository.save(participation);
    }

    public void receiveBestAnswer(Participation participation) {
        participation.receiveBestAnswer();
        participationRepository.save(participation);
    }

    public Boolean isOwnQuestion(Long courseId, User user, Question question) {
        boolean isOwnQuestion = false;
        Optional<Participation> optionalParticipation = participationRepository.findParticipationByStudentIdAndCourseId(user.getId(), courseId);
        if (optionalParticipation.isPresent()) {
            isOwnQuestion = optionalParticipation.get().getQuestions().contains(question);
        }
        return isOwnQuestion;
    }

    public void receiveInterestingQuestion(Long courseId, User user) {
        Optional<Participation> optionalParticipation = participationRepository.findParticipationByStudentIdAndCourseId(user.getId(), courseId);
        optionalParticipation.ifPresent(Participation::receiveInterestingQuestion);
    }

    public void receiveInterestingPost(Long courseId, User user) {
        Optional<Participation> optionalParticipation = participationRepository.findParticipationByStudentIdAndCourseId(user.getId(), courseId);
        optionalParticipation.ifPresent(Participation::receiveInterestingPost);
    }

    public Boolean isOwnPost(Long courseId, User user, Post post) {
        boolean isOwnPost = false;
        Optional<Participation> optionalParticipation = participationRepository.findParticipationByStudentIdAndCourseId(user.getId(), courseId);
        if (optionalParticipation.isPresent()) {
            isOwnPost = optionalParticipation.get().getPosts().contains(post);
        }
        return isOwnPost;
    }

    public void deletePost(Long courseId, User user, Post post) {
        Optional<Participation> optionalParticipation = participationRepository.findParticipationByStudentIdAndCourseId(user.getId(), courseId);
        if (optionalParticipation.isPresent()) {
            optionalParticipation.get().getPosts().remove(post);
            optionalParticipation.get().deletePost();
            participationRepository.save(optionalParticipation.get());
        }
    }

    public void deleteQuestion(Long courseId, User user, Question question) {
        Optional<Participation> optionalParticipation = participationRepository.findParticipationByStudentIdAndCourseId(user.getId(), courseId);
        if (optionalParticipation.isPresent()) {
            optionalParticipation.get().getQuestions().remove(question);
            optionalParticipation.get().deleteQuestion();
            participationRepository.save(optionalParticipation.get());
        }
    }

    public void outstandPost(Participation participation) {
        participation.outstandPost();
        participationRepository.save(participation);
    }

    public void outstandQuestion(Participation participation) {
        participation.outstandQuestion();
        participationRepository.save(participation);
    }

    public void outstandComment(Participation participation) {
        participation.outstandComment();
        participationRepository.save(participation);
    }

    public void outstandAnswer(Participation participation) {
        participation.outstandAnswer();
        participationRepository.save(participation);
    }

    public Object getTopPosts(Long courseId) {
        List<Participation> top = participationRepository.getParticipationsByCourseIdOrderByPostsSizeDesc(courseId);
        if (top.size()>10){
            return top.subList(0,9);
        }
        return top;
    }

    public Object getTopQuestions(Long courseId) {
        List<Participation> top = participationRepository.getParticipationsByCourseIdOrderByQuestionsSizeDesc(courseId);
        if (top.size()>10){
            return top.subList(0,9);
        }
        return top;
    }

    public List<Participation> getStudents(Long courseId){
        return participationRepository.getParticipationsByCourseId(courseId);
    }
}
