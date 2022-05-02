package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User student;

    @ManyToOne
    private Course course;

    private int points;

    @OneToMany
    private List<Post> posts;

    @ManyToMany
    private List<Post> likedPosts;

    @OneToMany
    private List<Comment> comments;

    @OneToMany
    private List<Question> questions;

    @ManyToMany
    private List<Question> likedQuestions;

    @OneToMany
    private List<Answer> answers;

    @ManyToMany
    private List<Comment> likedComments;

    private String title;

    @ElementCollection
    private List<Boolean> badges;

    public Participation(User student, Course course) {
        this.student = student;
        this.course = course;
        this.points = 0;
        this.posts = new LinkedList<>();
        this.likedPosts = new LinkedList<>();
        this.comments = new LinkedList<>();
        this.questions = new LinkedList<>();
        this.likedQuestions = new LinkedList<>();
        this.answers = new LinkedList<>();
        this.likedComments = new LinkedList<>();
        this.title = "Principiante";
        this.badges = new LinkedList<>();
        for (int i = 0; i < 25; i++) {
            this.badges.add(false);
        }
    }

    public Participation() {
    }

    public void addPost(Post post){
        this.posts.add(post);
        this.points += 5;
        if (this.posts.size()==1){
            this.badges.set(0, true);
            this.points += 10;
        } else if (this.posts.size()==10){
            this.badges.set(16, true);
            this.points += 30;
        }

    }

    public void likePost(Post post){
        this.likedPosts.add(post);
    }

    public void quitLikePost(Post post) {
        this.likedPosts.remove(post);
    }

    public void likeComment(Comment comment){
        this.likedComments.add(comment);
    }

    public void quitLikeComment(Comment comment){
        this.likedComments.remove(comment);
    }

    public boolean isCommentLiked(Comment comment){
        return this.likedComments.contains(comment);
    }

    public int totalLikesInPosts(){
        final int[] totalLikes = {0};
        this.posts.forEach(post -> totalLikes[0] +=post.getLikes());
        return totalLikes[0];
    }

    public int totalViewsInPosts() {
        final int[] totalViews = {0};
        this.posts.forEach(post -> totalViews[0] +=post.getViews());
        return totalViews[0];
    }

    public int totalInterestingPosts() {
        final int[] totalInteresting = {0};
        this.posts.forEach(post -> {
            if (post.isInteresting()){
                totalInteresting[0] ++;
            }
        });
        return totalInteresting[0];
    }

    public void addQuestion(Question question){
        this.questions.add(question);
        this.points += 5;
        if (this.questions.size()==1){
            this.badges.set(1, true);
            this.points += 10;
        } else if (this.questions.size()==10){
            this.badges.set(17, true);
            this.points += 30;
        }
    }

    public void likeQuestion(Question question){
        this.likedQuestions.add(question);
    }

    public void quitLikeQuestion(Question question) {
        this.likedQuestions.remove(question);
    }

    public int totalLikesInQuestions(){
        final int[] totalLikes = {0};
        this.questions.forEach(question -> totalLikes[0] +=question.getLikes());
        return totalLikes[0];
    }

    public int totalViewsInQuestions() {
        final int[] totalViews = {0};
        this.questions.forEach(question -> totalViews[0] +=question.getViews());
        return totalViews[0];
    }

    public int totalInterestingQuestions() {
        final int[] totalInteresting = {0};
        this.questions.forEach(question -> {
            if (question.isInteresting()){
                totalInteresting[0] ++;
            }
        });
        return totalInteresting[0];
    }

    public void obtainBadge(int badge){
        this.badges.set(badge, true);
    }

    public boolean getBadge(int badge){
        return this.badges.get(badge);
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
        this.points += 3;
        if (this.comments.size()==1){
            this.badges.set(2, true);
            this.points += 10;
        } else if (this.comments.size()==10){
            this.badges.set(18, true);
            this.points += 30;
        }
    }

    public void addAnswer(Answer answer){
        this.answers.add(answer);
        this.points += 3;
        if (this.answers.size()==1){
            this.badges.set(3, true);
            this.points += 10;
        } else if (this.answers.size()==10){
            this.badges.set(19, true);
            this.points += 30;
        }
    }

    public void receiveCommentInPost(){
        this.points+=2;
    }

    public void receiveAnswerInQuestion(){
        this.points+=2;
    }

    public void receiveLikeInPost(){
        this.points+=1;
        if (this.totalLikesInPosts()==1){
            this.badges.set(4, true);
            this.points += 5;
        } else if (this.totalLikesInPosts()==10){
            this.badges.set(11, true);
            this.points += 15;
        }
    }

    public void receiveLikeInQuestion(){
        this.points+=1;
        if (this.totalLikesInQuestions()==1){
            this.badges.set(5, true);
            this.points += 5;
        } else if (this.totalLikesInQuestions()==10){
            this.badges.set(12, true);
            this.points += 15;
        }
    }

    public void checkViewsInPosts(){
        if (this.totalViewsInPosts()==20){
            this.badges.set(9, true);
            this.points += 20;
        }
    }

    public void checkViewsInQuestions(){
        if (this.totalViewsInQuestions()==20){
            this.badges.set(10, true);
            this.points += 20;
        }
    }


}
