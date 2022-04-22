package com.example.backend.service;

import com.example.backend.model.Comment;
import com.example.backend.model.Participation;
import com.example.backend.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment createComment(Participation participation, String content) {
        Comment comment = new Comment(participation, content);
        return commentRepository.save(comment);
    }

    public void setOutstanding(Comment comment) {
        comment.setOutstanding();
        commentRepository.save(comment);
    }

    public List<Comment> getOutstandingComments(List<Comment> comments) {
        return comments.stream().filter(Comment::isOutstanding).collect(Collectors.toList());
    }

    public List<Comment> getStandardComments(List<Comment> comments) {
        return comments.stream().filter(comment -> !comment.isOutstanding()).collect(Collectors.toList());
    }
}
