package com.example.backend.service;

import com.example.backend.model.*;
import com.example.backend.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question createQuestion(Participation participation, String title, String content) {
        Question question = new Question(participation, title, content);
        return questionRepository.save(question);
    }

    public void setOutstanding(Long questionId){
        Question question = questionRepository.getById(questionId);
        question.setOutstanding();
        questionRepository.save(question);
    }

    public void setInteresting(Long questionId){
        Question question = questionRepository.getById(questionId);
        question.setInteresting();
        questionRepository.save(question);
    }

    public List<Question> getStandardQuestions(List<Question> questions){
        return questions.stream().filter(question -> (!question.isInteresting() && !question.isOutstanding())).collect(Collectors.toList());
    }

    public List<Question> getOutstandingQuestions(List<Question> questions) {
        return questions.stream().filter(question -> (!question.isInteresting() && question.isOutstanding())).collect(Collectors.toList());
    }

    public List<Question> getInterestingQuestions(List<Question> questions) {
        return questions.stream().filter(Question::isInteresting).collect(Collectors.toList());
    }

    public Question getQuestion(Long questionId) {
        return questionRepository.getById(questionId);
    }

    public void addView(Long questionId) {
        Question question = questionRepository.getById(questionId);
        question.addView();
        questionRepository.save(question);
    }

    public void addAnswer(Long questionId, Answer answer) {
        Question question = questionRepository.getById(questionId);
        question.addAnswer(answer);
        questionRepository.save(question);
    }

    public void like(Long questionId) {
        Question question = questionRepository.getById(questionId);
        question.like();
        questionRepository.save(question);
    }

    public void quitLike(Long questionId) {
        Question question = questionRepository.getById(questionId);
        question.quitLike();
        questionRepository.save(question);
    }

    public void setBestAnswer(Long questionId, Answer answer) {
        Question question = getQuestion(questionId);
        question.setBestAnswer(answer);
        questionRepository.save(question);
    }

    public void resetBestAnswer(Long questionId) {
        Question question = getQuestion(questionId);
        question.setBestAnswer(null);
        questionRepository.save(question);
    }
}
