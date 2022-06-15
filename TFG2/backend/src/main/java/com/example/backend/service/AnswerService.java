package com.example.backend.service;

import com.example.backend.model.Answer;
import com.example.backend.model.Comment;
import com.example.backend.model.Participation;
import com.example.backend.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer createAnswer(Participation participation, String content) {
        Answer answer = new Answer(participation, content);
        participation.addAnswer(answer);
        return answerRepository.save(answer);
    }

    public void setOutstanding(Answer answer) {
        answer.setOutstanding();
        answerRepository.save(answer);
    }

    public List<Answer> getOutstandingAnswers(List<Answer> answers) {
        return answers.stream().filter(Answer::isOutstanding).collect(Collectors.toList());
    }

    public List<Answer> getStandardAnswers(List<Answer> answers) {
        return answers.stream().filter(answer -> !answer.isOutstanding()).collect(Collectors.toList());
    }

    public void like(Long answerId) {
        Answer answer = answerRepository.getById(answerId);
        answer.like();
        answerRepository.save(answer);
    }

    public void quitLike(Long answerId) {
        Answer answer = answerRepository.getById(answerId);
        answer.quitLike();
        answerRepository.save(answer);
    }

    public Answer getAnswer(Long answerId) {
        return answerRepository.getById(answerId);
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }
}
