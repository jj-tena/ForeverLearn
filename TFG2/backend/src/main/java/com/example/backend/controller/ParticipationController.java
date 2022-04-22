package com.example.backend.controller;

import com.example.backend.model.*;
import com.example.backend.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class ParticipationController {

    private final ParticipationService participationService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final PostService postService;
    private final CourseService courseService;

    private final CommentService commentService;

    public ParticipationController(ParticipationService participationService, UserService userService, CategoryService categoryService, PostService postService, CourseService courseService, CommentService commentService) {
        this.participationService = participationService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.postService = postService;
        this.courseService = courseService;
        this.commentService = commentService;
    }

    private void setHeaderInfo(Model model){
        Optional<User> activeUser = userService.getActiveUser();
        model.addAttribute("activeUser", activeUser.isPresent());
        if (activeUser.isPresent()){
            User user = activeUser.get();
            model.addAttribute("activeUserAdmin", user.isAdmin());
            model.addAttribute("user", user);
        }
        model.addAttribute("categories", categoryService.findAll());
    }

    private void setParticipationHeader(Model model, Long courseId){
        Optional<Participation> optionalParticipation = Optional.empty();
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
        }
        model.addAttribute("enrolledUser", optionalParticipation.isPresent());
        model.addAttribute("courseId", courseId);
    }

    private void setPostsInfo(Model model, Long courseId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        if (optionalCourse.isPresent()){
            model.addAttribute("interestingPosts", postService.getInterestingPosts(optionalCourse.get().getPosts()));
            model.addAttribute("outstandingPosts", postService.getOutstandingPosts(optionalCourse.get().getPosts()));
            model.addAttribute("standardPosts", postService.getStandardPosts(optionalCourse.get().getPosts()));
        }
    }

    private void setCommentsInfo(Model model, Long postId){
        Post post = postService.getPost(postId);
        model.addAttribute("outstandingComments", commentService.getOutstandingComments(post.getComments()));
        model.addAttribute("standardComments", commentService.getStandardComments(post.getComments()));
    }

    @GetMapping("/students-area-{courseId}")
    public String studentsAreaLink(Model model, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        return "students-area";
    }

    @GetMapping("/posts-{courseId}")
    public String postsLink(Model model, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        setPostsInfo(model, courseId);
        return "posts";
    }

    @GetMapping("/post-{postId}-course-{courseId}")
    public String postLink(Model model, @PathVariable Long postId, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        postService.addView(postId);
        model.addAttribute("post", postService.getPost(postId));
        setCommentsInfo(model, postId);
        return "post";
    }

    @GetMapping("/create-post-for-course-{courseId}")
    public String createPostLink(Model model, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        return "create-post";
    }

    @GetMapping("/publish-post-for-course-{courseId}")
    public String publishPost(Model model, @PathVariable Long courseId, String title, String content){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            Optional<Participation> optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            if (optionalParticipation.isPresent()){
                Post post = postService.createPost(optionalParticipation.get(), title, content);
                participationService.addPost(courseId, optionalUser.get().getId(), post);
                courseService.addPost(courseId, post);
                setPostsInfo(model, courseId);
            }
        }
        return "posts";
    }

    @GetMapping("/publish-post-standard-for-course-{courseId}")
    public String publishPostStandard(Model model, @PathVariable Long courseId, String title, String content){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            Optional<Participation> optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            if (optionalParticipation.isPresent()){
                Post post = postService.createPost(optionalParticipation.get(), title, content);
                participationService.addPost(courseId, optionalUser.get().getId(), post);
                courseService.addPost(courseId, post);
                setPostsInfo(model, courseId);
            }
        }
        return "posts";
    }

    @GetMapping("/publish-post-outstanding-for-course-{courseId}")
    public String publishPostOutstanding(Model model, @PathVariable Long courseId, String title, String content){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            Optional<Participation> optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            if (optionalParticipation.isPresent()){
                Post post = postService.createPost(optionalParticipation.get(), title, content);
                postService.setOutstanding(post.getId());
                participationService.addPost(courseId, optionalUser.get().getId(), post);
                courseService.addPost(courseId, post);
                setPostsInfo(model, courseId);
            }
        }
        return "posts";
    }

    @GetMapping("/publish-post-interesting-for-course-{courseId}")
    public String publishPostInteresting(Model model, @PathVariable Long courseId, String title, String content){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            Optional<Participation> optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            if (optionalParticipation.isPresent()){
                Post post = postService.createPost(optionalParticipation.get(), title, content);
                postService.setInteresting(post.getId());
                participationService.addPost(courseId, optionalUser.get().getId(), post);
                courseService.addPost(courseId, post);
                setPostsInfo(model, courseId);
            }
        }
        return "posts";
    }

    @GetMapping("/participation-{courseId}")
    public String participationLink(Model model, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        return "participation";
    }

    @GetMapping("/comment-post-{postId}-course-{courseId}")
    public String comment(Model model, @PathVariable Long postId, @PathVariable Long courseId, String content){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            Optional<Participation> optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            if (optionalParticipation.isPresent()){
                Comment comment = commentService.createComment(optionalParticipation.get(), content);
                postService.addComment(postId, comment);
                setCommentsInfo(model, postId);
            }
        }
        model.addAttribute("post", postService.getPost(postId));
        return "post";
    }

    @GetMapping("/comment-standard-post-{postId}-course-{courseId}")
    public String commentStandard(Model model, @PathVariable Long postId, @PathVariable Long courseId, String content){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            Optional<Participation> optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            if (optionalParticipation.isPresent()){
                Comment comment = commentService.createComment(optionalParticipation.get(), content);
                postService.addComment(postId, comment);
                setCommentsInfo(model, postId);
            }
        }
        model.addAttribute("post", postService.getPost(postId));
        return "post";
    }

    @GetMapping("/comment-outstanding-post-{postId}-course-{courseId}")
    public String commentOutstanding(Model model, @PathVariable Long postId, @PathVariable Long courseId, String content){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            Optional<Participation> optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            if (optionalParticipation.isPresent()){
                Comment comment = commentService.createComment(optionalParticipation.get(), content);
                commentService.setOutstanding(comment);
                postService.addComment(postId, comment);
                setCommentsInfo(model, postId);
            }
        }
        model.addAttribute("post", postService.getPost(postId));
        return "post";
    }

}
