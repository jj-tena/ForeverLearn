package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Blob;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "userTable")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String surname;

    private String email;

    private String password;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String contact;

    private String facebook;

    private String twitter;

    private String youtube;

    private boolean isAdmin;

    private Boolean banned = false;

    @OneToMany
    private List<Course> userCourses;

    @ManyToMany
    private List<Course> completedCourses = new LinkedList<>();

    @ManyToMany
    private List<Course> enrolledCourses = new LinkedList<>();

    @ManyToMany
    private List<Course> wishedCourses = new LinkedList<>();

    @ManyToMany
    private List<Course> likedCourses = new LinkedList<>();

    @ManyToMany
    private List<Course> dislikedCourses = new LinkedList<>();

    @Lob
    @JsonIgnore
    private Blob profilePhoto;

    public User(){

    }

    public User(String name, String surname, String email, String password) {
        super();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public User(String name, String surname, String email, String password, String description, String contact, String facebook, String twitter, String youtube, boolean isAdmin, Blob profilePhoto) {
        super();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.description = description;
        this.contact = contact;
        this.facebook = facebook;
        this.twitter = twitter;
        this.youtube = youtube;
        this.isAdmin = isAdmin;
        this.profilePhoto = profilePhoto;
    }

    public void addUserCourse(Course course){
        if(Objects.isNull(this.userCourses)){
            this.userCourses = new LinkedList<>();
        }
        this.userCourses.add(course);
    }

    public void deleteUserCourse(Course course){
        this.userCourses.remove(course);
    }

    public void addEnrolledCourse(Course course){
        if(Objects.isNull(this.enrolledCourses)){
            this.enrolledCourses = new LinkedList<>();
        }
        this.enrolledCourses.add(course);
    }

    public void deleteEnrolledCourse(Course course){
        this.enrolledCourses.remove(course);
    }

    public void addCompletedCourse(Course course){
        if(Objects.isNull(this.completedCourses)){
            this.completedCourses = new LinkedList<>();
        }
        this.completedCourses.add(course);
    }

    public void deleteCompletedCourse(Course course){
        this.completedCourses.remove(course);
    }

    public void addWishedCourse(Course course){
        if(Objects.isNull(this.wishedCourses)){
            this.wishedCourses = new LinkedList<>();
        }
        this.wishedCourses.add(course);
    }

    public void deleteWishedCourse(Course course){
        this.wishedCourses.remove(course);
    }

    public void likeCourse(Course course){
        if(Objects.isNull(this.likedCourses)){
            this.likedCourses = new LinkedList<>();
        }
        this.likedCourses.add(course);
        course.like();
        if (dislikedCourses.contains(course)) {
            dislikedCourses.remove(course);
            course.quitDislike();
        }
    }

    public void dislikeCourse(Course course){
        if(Objects.isNull(this.dislikedCourses)){
            this.dislikedCourses = new LinkedList<>();
        }
        this.dislikedCourses.add(course);
        course.dislike();
        if (likedCourses.contains(course)) {
            likedCourses.remove(course);
            course.quitLike();
        }
    }

    public void quitLikeCourse(Course course) {
        if (likedCourses.contains(course)) {
            likedCourses.remove(course);
            course.quitLike();
        }
    }

    public void quitDislikeCourse(Course course) {
        if (dislikedCourses.contains(course)) {
            dislikedCourses.remove(course);
            course.quitDislike();
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", contact='" + contact + '\'' +
                ", facebook='" + facebook + '\'' +
                ", twitter='" + twitter + '\'' +
                ", youtube='" + youtube + '\'' +
                '}';
    }

    public Boolean isCourseEnrolled(Course course) {
        return this.enrolledCourses.contains(course);
    }

    public Boolean isCourseWished(Course course) {
        return this.wishedCourses.contains(course);
    }

    public Boolean isCourseLiked(Course course) {
        return this.likedCourses.contains(course);
    }

    public Boolean isCourseDisliked(Course course) {
        return this.dislikedCourses.contains(course);
    }

    public Boolean isBanned(){
        return this.banned;
    }

    public void ban(){
        this.banned = true;
    }

    public void unban(){
        this.banned = false;
    }

}
