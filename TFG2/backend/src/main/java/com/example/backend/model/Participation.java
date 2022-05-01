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

    @OneToMany
    private List<Post> likedPosts;

    @OneToMany
    private List<Comment> comments;

    @OneToMany
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

    public void obtainBadge(int badge){
        this.badges.set(badge, true);
    }

    public boolean getBadge(int badge){
        return this.badges.get(badge);
    }
}
