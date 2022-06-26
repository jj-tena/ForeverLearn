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

    private int commentsReceived;

    @ManyToMany
    private List<Post> likedPosts;

    @OneToMany
    private List<Comment> comments;

    @OneToMany
    private List<Question> questions;

    private int answersReceived;

    @ManyToMany
    private List<Question> likedQuestions;

    @OneToMany
    private List<Answer> answers;

    @ManyToMany
    private List<Comment> likedComments;

    private String title;

    @ElementCollection
    private List<Boolean> badges;

    private int postsToOutstand;

    private int questionsToOutstand;

    private int commentsToOutstand;

    private int answersToOutstand;

    private int postsSize;

    private int questionsSize;

    public Participation(User student, Course course) {
        this.student = student;
        this.course = course;
        this.points = 0;
        this.posts = new LinkedList<>();
        this.commentsReceived = 0;
        this.likedPosts = new LinkedList<>();
        this.comments = new LinkedList<>();
        this.questions = new LinkedList<>();
        this.answersReceived = 0;
        this.likedQuestions = new LinkedList<>();
        this.answers = new LinkedList<>();
        this.likedComments = new LinkedList<>();
        this.title = "Principiante";
        this.badges = new LinkedList<>();
        for (int i = 0; i < 25; i++) {
            this.badges.add(false);
        }
        this.postsToOutstand = 0;
        this.questionsToOutstand = 0;
        this.commentsToOutstand = 0;
        this.answersToOutstand = 0;
        this.postsSize = 0;
        this.questionsSize = 0;
    }

    public Participation() {
    }

    public void addPost(Post post){
        this.posts.add(post);
        this.postsSize++;
        updatePoints(5);
        if (this.posts.size()==1){
            this.badges.set(0, true);
            updatePoints(10);
        } else if (this.posts.size()==15){
            this.badges.set(16, true);
            updatePoints(30);
        }
        if (!this.badges.get(21) && post.isInteresting()){
            this.badges.set(21, true);
            updatePoints(30);
        }
    }

    public void deletePost(){
        this.postsSize--;
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
        this.questionsSize++;
        updatePoints(5);
        if (this.questions.size()==1){
            this.badges.set(1, true);
            updatePoints(10);
        } else if (this.questions.size()==15){
            this.badges.set(17, true);
            updatePoints(30);
        }
        if (!this.badges.get(22) && question.isInteresting()){
            this.badges.set(22, true);
            updatePoints(30);
        }
    }

    public void deleteQuestion(){
        this.questionsSize--;
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
        updatePoints(3);
        if (this.comments.size()==1){
            this.badges.set(2, true);
            updatePoints(5);
        } else if (this.comments.size()==5){
            this.badges.set(8, true);
            updatePoints(10);
        }else if (this.comments.size()==15){
            this.badges.set(18, true);
            updatePoints(20);
        }
    }

    public void addAnswer(Answer answer){
        this.answers.add(answer);
        updatePoints(3);
        if (this.answers.size()==1){
            this.badges.set(3, true);
            updatePoints(5);
        } else if (this.answers.size()==5){
            this.badges.set(15, true);
            updatePoints(10);
        }else if (this.answers.size()==15){
            this.badges.set(19, true);
            updatePoints(20);
        }
    }

    public void receiveCommentInPost(){
        updatePoints(2);
    }

    public void receiveAnswerInQuestion(){
        updatePoints(2);
    }

    public void receiveLikeInPost(){
        updatePoints(1);
        if (this.totalLikesInPosts()==1){
            this.badges.set(4, true);
            updatePoints(5);
        } else if (this.totalLikesInPosts()==10){
            this.badges.set(11, true);
            updatePoints(15);
        } else if (this.totalLikesInPosts()==25){
            this.badges.set(20, true);
            updatePoints(20);
        }
    }

    public void receiveLikeInQuestion(){
        updatePoints(1);
        if (this.totalLikesInQuestions()==1){
            this.badges.set(5, true);
            updatePoints(5);
        } else if (this.totalLikesInQuestions()==10){
            this.badges.set(12, true);
            updatePoints(15);
        } else if (this.totalLikesInQuestions()==25){
            this.badges.set(23, true);
            updatePoints(20);
        }
    }

    public void checkViewsInPosts(){
        if (this.totalViewsInPosts()==20){
            this.badges.set(9, true);
            updatePoints(15);
        }
    }

    public void checkViewsInQuestions(){
        if (this.totalViewsInQuestions()==20){
            this.badges.set(10, true);
            updatePoints(15);
        }
    }

    public void updatePoints(int points){
        this.points+=points;
        checkTitle();
    }

    public void checkTitle(){
        switch (this.title){
            case "Principiante":
                if (this.points>=50){
                    this.title = "Intermedio";
                    updateElementsToOutstand(1);
                }
                break;
            case "Intermedio":
                if (this.points>=150){
                    this.title = "Avanzado";
                    updateElementsToOutstand(2);
                }
                break;
            case "Avanzado":
                if (this.points>=270) {
                    this.title = "Experto";
                    updateElementsToOutstand(3);
                }
                break;
            case "Experto":
                if (this.points>=350) {
                    this.title = "Héroe";
                    updateElementsToOutstand(4);
                }
                break;
            case "Héroe":
                if (this.points>=400){
                    this.title = "Leyenda";
                    this.student.getEnrolledCourses().remove(this.course);
                    this.student.getCompletedCourses().add(this.course);
                }
                break;
        }
    }

    public void updateElementsToOutstand(int number){
        this.postsToOutstand += number;
        this.questionsToOutstand += number;
        this.commentsToOutstand += number;
        this.answersToOutstand += number;
    }

    public void receiveComment(){
        this.commentsReceived++;
        if (this.commentsReceived==1){
            this.badges.set(6, true);
            updatePoints(5);
        } else if (this.commentsReceived==10){
            this.badges.set(13, true);
            updatePoints(15);
        }
    }

    public void receiveAnswer(){
        this.answersReceived++;
        if (this.answersReceived==1){
            this.badges.set(7, true);
            updatePoints(5);
        } else if (this.answersReceived==10){
            this.badges.set(14, true);
            updatePoints(15);
        }
    }

    public void receiveBestAnswer(){
        if(!this.badges.get(24)){
            this.badges.set(24, true);
            updatePoints(50);
        }
        updatePoints(15);
    }

    public void receiveInterestingQuestion(){
        if(!this.badges.get(22)){
            this.badges.set(22, true);
            updatePoints(30);
        }
        updatePoints(10);
    }

    public void receiveInterestingPost(){
        if(!this.badges.get(23)){
            this.badges.set(23, true);
            updatePoints(30);
        }
        updatePoints(10);
    }

    public void outstandPost() {
        this.postsToOutstand--;
    }

    public void outstandQuestion() {
        this.questionsToOutstand--;
    }

    public void outstandComment() {
        this.commentsToOutstand--;
    }

    public void outstandAnswer() {
        this.answersToOutstand--;
    }

    @Override
    public String toString() {
        return "Participation{" +
                "id=" + id +
                '}';
    }
}
