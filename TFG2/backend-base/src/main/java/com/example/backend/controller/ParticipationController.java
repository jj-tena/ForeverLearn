package com.example.backend.controller;

import com.example.backend.model.*;
import com.example.backend.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class ParticipationController {

    private final ParticipationService participationService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final PostService postService;
    private final CourseService courseService;
    private final CommentService commentService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    private final ThemeService themeService;

    public ParticipationController(ParticipationService participationService, UserService userService, CategoryService categoryService, PostService postService, CourseService courseService, CommentService commentService, QuestionService questionService, AnswerService answerService, ThemeService themeService) {
        this.participationService = participationService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.postService = postService;
        this.courseService = courseService;
        this.commentService = commentService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.themeService = themeService;
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
        List<Theme> themes = new ArrayList<>();
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        if (optionalCourse.isPresent()){
            themes = optionalCourse.get().getThemes();
        }
        model.addAttribute("themes", themes);
    }

    private boolean isUserBanned(Long courseId){
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            Optional<Course> optionalCourse = courseService.findCourseById(courseId);
            if (optionalCourse.isPresent()){
                Optional<Participation> optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
                if (optionalParticipation.isPresent()) {
                    return courseService.isUserBanned(courseId, optionalParticipation.get());
                }
            }
        }
        return false;
    }

    private void setPostsInfo(Model model, Long courseId, Long themeId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        Optional<Theme> optionalTheme = themeService.findThemeById(themeId);
        if (optionalCourse.isPresent()){
            List<Post> postList = new LinkedList<>();
            if (themeId == -2) {
                postList = optionalCourse.get().getPosts();
            } else if (themeId == -1) {
                List<Post> finalPostList = postList;
                optionalCourse.get().getPosts().forEach(post -> {if (Objects.isNull(post.getTheme())) {
                    finalPostList.add(post);}});
                postList = finalPostList;
            } else if (optionalTheme.isPresent()){
                List<Post> finalPostList = postList;
                optionalCourse.get().getPosts().forEach(post -> {if (Objects.nonNull(post.getTheme()) && post.getTheme().equals(optionalTheme.get())) {
                    finalPostList.add(post);}});
                postList = finalPostList;
            }
            model.addAttribute("interestingPosts", postService.getInterestingPosts(postList));
            model.addAttribute("outstandingPosts", postService.getOutstandingPosts(postList));
            model.addAttribute("standardPosts", postService.getStandardPosts(postList));
        }
    }

    private void setQuestionsInfo(Model model, Long courseId, Long themeId){
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        Optional<Theme> optionalTheme = themeService.findThemeById(themeId);
        if (optionalCourse.isPresent()){
            List<Question> questionList = new LinkedList<>();
            if (themeId == -2) {
                questionList = optionalCourse.get().getQuestions();
            } else if (themeId == -1) {
                List<Question> finalQuestionList = questionList;
                optionalCourse.get().getQuestions().forEach(question -> {if (Objects.isNull(question.getTheme())) {
                    finalQuestionList.add(question);}});
                questionList = finalQuestionList;
            } else if (optionalTheme.isPresent()){
                List<Question> finalQuestionList = questionList;
                optionalCourse.get().getQuestions().forEach(question -> {if (Objects.nonNull(question.getTheme()) && question.getTheme().equals(optionalTheme.get())) {
                    finalQuestionList.add(question);}});
                questionList = finalQuestionList;
            }
            model.addAttribute("interestingQuestions", questionService.getInterestingQuestions(questionList));
            model.addAttribute("outstandingQuestions", questionService.getOutstandingQuestions(questionList));
            model.addAttribute("standardQuestions", questionService.getStandardQuestions(questionList));
        }
    }

    private void setCommentsInfo(Model model, Long postId, Long courseId){
        Post post = postService.getPost(postId);
        List<Comment> outstandingComments = commentService.getOutstandingComments(post.getComments());
        List<Comment> standardComments = commentService.getStandardComments(post.getComments());
        Optional<User> optionalUser = userService.getActiveUser();
        model.addAttribute("outstandingComments", outstandingComments);
        model.addAttribute("standardComments", standardComments);

    }

    private void setAnswersInfo(Model model, Long questionId, Long courseId){
        Question question = questionService.getQuestion(questionId);
        List<Answer> answers = question.getAnswers();
        Answer bestAnswer = question.getBestAnswer();
        answers.remove(bestAnswer);
        List<Answer> outstandingAnswers = answerService.getOutstandingAnswers(answers);
        List<Answer> standardAnswers = answerService.getStandardAnswers(answers);
        model.addAttribute("outstandingAnswers", outstandingAnswers);
        model.addAttribute("standardAnswers", standardAnswers);
        model.addAttribute("bestAnswer", bestAnswer);
    }

    private void setPostInfo(Model model, Long postId, Long courseId){
        boolean postLiked = false;
        boolean isOwnPost = false;
        boolean isOwnCourse = false;
        boolean commentToOutstand = false;
        Post post = postService.getPost(postId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            postLiked = participationService.isPostLiked(courseId, optionalUser.get().getId(), post);
            isOwnPost = participationService.isOwnPost(courseId, optionalUser.get(), post);
            isOwnCourse = courseService.isOwnCourse(courseId, optionalUser.get());
            Optional<Participation> optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            if (optionalParticipation.isPresent()){
                if (optionalParticipation.get().getCommentsToOutstand()>0){
                    commentToOutstand = true;
                }
            }
        }
        model.addAttribute("likedPost", postLiked);
        model.addAttribute("isOwnPost", isOwnPost);
        model.addAttribute("isOwnCourse", isOwnCourse);
        model.addAttribute("interestingPost", post.isInteresting());
        model.addAttribute("commentToOutstand", commentToOutstand);
    }

    private void setQuestionInfo(Model model, Long questionId, Long courseId){
        boolean questionLiked = false;
        boolean isOwnQuestion = false;
        boolean isOwnCourse = false;
        boolean answerToOutstand = false;
        Question question = questionService.getQuestion(questionId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            questionLiked = participationService.isQuestionLiked(courseId, optionalUser.get().getId(), question);
            isOwnQuestion = participationService.isOwnQuestion(courseId, optionalUser.get(), question);
            isOwnCourse = courseService.isOwnCourse(courseId, optionalUser.get());
            Optional<Participation> optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            if (optionalParticipation.isPresent()){
                if (optionalParticipation.get().getAnswersToOutstand()>0){
                    answerToOutstand = true;
                }
            }
        }
        model.addAttribute("likedQuestion", questionLiked);
        model.addAttribute("isOwnQuestion", isOwnQuestion);
        model.addAttribute("isOwnCourse", isOwnCourse);
        model.addAttribute("interestingQuestion", question.isInteresting());
        model.addAttribute("answerToOutstand", answerToOutstand);
    }

    @GetMapping("/students-area-{courseId}")
    public String studentsAreaLink(Model model, @PathVariable Long courseId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        model.addAttribute("topGlobal", participationService.getTopGlobal(courseId));
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()) {
            Optional<Participation> optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            optionalParticipation.ifPresent(participation -> model.addAttribute("topRelative", participationService.getTopRelative(courseId, participation)));
        }
        return "students-area";
    }

    @GetMapping("/posts-{courseId}-for-theme-{themeId}")
    public String postsLink(Model model, @PathVariable Long courseId, @PathVariable Long themeId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        setPostsInfo(model, courseId, themeId);
        return "posts";
    }

    @GetMapping("/questions-{courseId}-for-theme-{themeId}")
    public String questionsLink(Model model, @PathVariable Long courseId, @PathVariable Long themeId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        setQuestionsInfo(model, courseId, themeId);
        return "questions";
    }

    @GetMapping("/post-{postId}-course-{courseId}")
    public String postLink(Model model, @PathVariable Long postId, @PathVariable Long courseId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        postService.addView(postId);
        Post post = postService.getPost(postId);
        participationService.checkViewsInPosts(post);
        model.addAttribute("post", post);
        setCommentsInfo(model, postId, courseId);
        setPostInfo(model, postId, courseId);
        return "post";
    }

    @GetMapping("/question-{questionId}-course-{courseId}")
    public String questionLink(Model model, @PathVariable Long questionId, @PathVariable Long courseId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        questionService.addView(questionId);
        Question question = questionService.getQuestion(questionId);
        participationService.checkViewsInQuestions(question);
        model.addAttribute("question", question);
        setAnswersInfo(model, questionId, courseId);
        setQuestionInfo(model, questionId, courseId);
        return "question";
    }

    @GetMapping("/create-post-for-course-{courseId}")
    public String createPostLink(Model model, @PathVariable Long courseId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        boolean postToOutstand = false;
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()) {
            Optional<Participation> optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            if (optionalParticipation.isPresent()){
                if (optionalParticipation.get().getPostsToOutstand()>0){
                    postToOutstand = true;
                }
            }
        }
        model.addAttribute("postToOutstand", postToOutstand);
        return "create-post";
    }

    @GetMapping("/create-question-for-course-{courseId}")
    public String createQuestionLink(Model model, @PathVariable Long courseId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        boolean questionToOutstand = false;
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()) {
            Optional<Participation> optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            if (optionalParticipation.isPresent()){
                if (optionalParticipation.get().getQuestionsToOutstand()>0){
                    questionToOutstand = true;
                }
            }
        }
        model.addAttribute("questionToOutstand", questionToOutstand);
        return "create-question";
    }

    @GetMapping("/publish-post-for-course-{courseId}")
    public String publishPost(Model model, @PathVariable Long courseId, String title, String content, Long themeId, String type){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            Optional<Participation> optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            if (optionalParticipation.isPresent()){
                Optional<Theme> optionalTheme = themeService.findThemeById(themeId);
                Theme theme = null;
                if (optionalTheme.isPresent()){
                    theme = optionalTheme.get();
                }
                Post post = postService.createPost(optionalParticipation.get(), title, content, theme);
                participationService.addPost(courseId, optionalUser.get().getId(), post);
                courseService.addPost(courseId, post);
                if (type.contentEquals("Destacado") && (optionalParticipation.get().getPostsToOutstand()>0)){
                    participationService.outstandPost(optionalParticipation.get());
                    postService.setOutstanding(post.getId());
                }
                setPostsInfo(model, courseId, (long) -2);
            }
        }
        return "posts";
    }

    @GetMapping("/publish-question-for-course-{courseId}")
    public String publishQuestion(Model model, @PathVariable Long courseId, String title, String content, Long themeId, String type){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            Optional<Participation> optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            if (optionalParticipation.isPresent()){
                Optional<Theme> optionalTheme = themeService.findThemeById(themeId);
                Theme theme = null;
                if (optionalTheme.isPresent()){
                    theme = optionalTheme.get();
                }
                Question question = questionService.createQuestion(optionalParticipation.get(), title, content, theme);
                participationService.addQuestion(courseId, optionalUser.get().getId(), question);
                courseService.addQuestion(courseId, question);
                if (type.contentEquals("Destacado") && (optionalParticipation.get().getQuestionsToOutstand()>0)){
                    participationService.outstandQuestion(optionalParticipation.get());
                    questionService.setOutstanding(question.getId());
                }
                setQuestionsInfo(model, courseId, themeId);
            }
        }
        return "questions";
    }

    @GetMapping("/set-post-{postId}-interesting-for-course-{courseId}")
    public String setPostInteresting(Model model, @PathVariable Long postId, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            if (courseService.isOwnCourse(courseId, optionalUser.get())){
                postService.setInteresting(postId);
                participationService.receiveInterestingPost(courseId, postService.getPost(postId).getParticipation().getStudent());
            }
        }
        return postLink(model, postId, courseId);
    }

    @GetMapping("/reset-post-{postId}-interesting-for-course-{courseId}")
    public String resetPostInteresting(Model model, @PathVariable Long postId, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            if (courseService.isOwnCourse(courseId, optionalUser.get())){
                postService.resetInteresting(postId);
            }
        }
        return postLink(model, postId, courseId);
    }

    @GetMapping("/set-question-{questionId}-interesting-for-course-{courseId}")
    public String setQuestionInteresting(Model model, @PathVariable Long questionId, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            if (courseService.isOwnCourse(courseId, optionalUser.get())){
                questionService.setInteresting(questionId);
                participationService.receiveInterestingQuestion(courseId, questionService.getQuestion(questionId).getParticipation().getStudent());
            }
        }
        return questionLink(model, questionId, courseId);
    }

    @GetMapping("/reset-question-{questionId}-interesting-for-course-{courseId}")
    public String resetQuestionInteresting(Model model, @PathVariable Long questionId, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            if (courseService.isOwnCourse(courseId, optionalUser.get())){
                questionService.resetInteresting(questionId);
            }
        }
        return questionLink(model, questionId, courseId);
    }

    @GetMapping("/comment-post-{postId}-course-{courseId}")
    public String comment(Model model, @PathVariable Long postId, @PathVariable Long courseId, String content, String type){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            Optional<Participation> optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            if (optionalParticipation.isPresent()){
                Comment comment = commentService.createComment(optionalParticipation.get(), content);
                postService.addComment(postId, comment);
                participationService.receiveComment(postService.getPost(postId).getParticipation());
                if (type.contentEquals("Destacado") && (optionalParticipation.get().getCommentsToOutstand()>0)){
                    participationService.outstandComment(optionalParticipation.get());
                    commentService.setOutstanding(comment);
                }
            }
        }
        model.addAttribute("post", postService.getPost(postId));
        setCommentsInfo(model, postId, courseId);
        setPostInfo(model, postId, courseId);
        return "post";
    }

    @GetMapping("/answer-question-{questionId}-course-{courseId}")
    public String answer(Model model, @PathVariable Long questionId, @PathVariable Long courseId, String content, String type){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            Optional<Participation> optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            if (optionalParticipation.isPresent()){
                Answer answer = answerService.createAnswer(optionalParticipation.get(), content);
                questionService.addAnswer(questionId, answer);
                participationService.receiveAnswer(questionService.getQuestion(questionId).getParticipation());
                if (type.contentEquals("Destacado") && (optionalParticipation.get().getAnswersToOutstand()>0)){
                    participationService.outstandAnswer(optionalParticipation.get());
                    answerService.setOutstanding(answer);
                }
            }
        }
        model.addAttribute("question", questionService.getQuestion(questionId));
        setAnswersInfo(model, questionId, courseId);
        setQuestionInfo(model, questionId, courseId);
        return "question";
    }

    @GetMapping("/participation-user-{userId}-course-{courseId}")
    public String participationLink(Model model, @PathVariable Long userId, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<Participation> optionalParticipation = participationService.existsParticipation(userId, courseId);
        if (optionalParticipation.isPresent()){
            model.addAttribute("participation", optionalParticipation.get());
            model.addAttribute("postNumber", optionalParticipation.get().getPosts().size());
            model.addAttribute("commentNumber", optionalParticipation.get().getComments().size());
            model.addAttribute("questionNumber", optionalParticipation.get().getQuestions().size());
            model.addAttribute("answerNumber", optionalParticipation.get().getAnswers().size());
            model.addAttribute("postLikes", optionalParticipation.get().totalLikesInPosts());
            model.addAttribute("postViews", optionalParticipation.get().totalViewsInPosts());
            model.addAttribute("interestingPosts", optionalParticipation.get().totalInterestingPosts());
            model.addAttribute("questionLikes", optionalParticipation.get().totalLikesInQuestions());
            model.addAttribute("questionViews", optionalParticipation.get().totalViewsInQuestions());
            model.addAttribute("interestingQuestions", optionalParticipation.get().totalInterestingQuestions());
            int badge;
            for (int i = 0; i < 25; i++) {
                badge = i+1;
                model.addAttribute("badge"+badge, optionalParticipation.get().getBadge(i));
            }
            Optional<User> activeUser = userService.getActiveUser();
            boolean isOwnParticipation = false;
            if (activeUser.isPresent()){
                isOwnParticipation = (activeUser.get().equals(optionalParticipation.get().getStudent()));
            }
            model.addAttribute("isOwnParticipation", isOwnParticipation);
        }
        return "participation";
    }

    @GetMapping("/like-post-{postId}-course-{courseId}")
    public String likePost(Model model, @PathVariable Long postId, @PathVariable Long courseId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()) {
            postService.like(postId);
            participationService.likePost(courseId, optionalUser.get().getId(), postService.getPost(postId));

            model.addAttribute("likedPost", true);
        }
        model.addAttribute("post", postService.getPost(postId));
        setCommentsInfo(model, postId, courseId);
        return "post";
    }

    @GetMapping("/like-question-{questionId}-course-{courseId}")
    public String likeQuestion(Model model, @PathVariable Long questionId, @PathVariable Long courseId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()) {
            questionService.like(questionId);
            participationService.likeQuestion(courseId, optionalUser.get().getId(), questionService.getQuestion(questionId));
            model.addAttribute("likedQuestion", true);
        }
        model.addAttribute("question", questionService.getQuestion(questionId));
        setAnswersInfo(model, questionId, courseId);
        return "question";
    }

    @GetMapping("/quit-like-post-{postId}-course-{courseId}")
    public String quitLikePost(Model model, @PathVariable Long postId, @PathVariable Long courseId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()) {
            postService.quitLike(postId);
            participationService.quitLikePost(courseId, optionalUser.get().getId(), postService.getPost(postId));
            model.addAttribute("likedPost", false);
        }
        model.addAttribute("post", postService.getPost(postId));
        setCommentsInfo(model, postId, courseId);
        return "post";
    }

    @GetMapping("/quit-like-question-{questionId}-course-{courseId}")
    public String quitLikeQuestion(Model model, @PathVariable Long questionId, @PathVariable Long courseId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()) {
            questionService.quitLike(questionId);
            participationService.quitLikeQuestion(courseId, optionalUser.get().getId(), questionService.getQuestion(questionId));
            model.addAttribute("likedQuestion", false);
        }
        model.addAttribute("question", questionService.getQuestion(questionId));
        setAnswersInfo(model, questionId, courseId);
        return "question";
    }

    @GetMapping("/set-best-answer-{answerId}-question-{questionId}-course-{courseId}")
    public String setBestAnswer(Model model, @PathVariable Long answerId, @PathVariable Long questionId, @PathVariable Long courseId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            Optional<Participation> optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            if (optionalParticipation.isPresent()){
                Answer answer = answerService.getAnswer(answerId);
                questionService.setBestAnswer(questionId, answer);
                participationService.receiveBestAnswer(answer.getParticipation());
            }
        }
        model.addAttribute("question", questionService.getQuestion(questionId));
        setAnswersInfo(model, questionId, courseId);
        setQuestionInfo(model, questionId, courseId);
        return "question";
    }

    @GetMapping("/reset-best-answer-question-{questionId}-course-{courseId}")
    public String resetBestAnswer(Model model, @PathVariable Long questionId, @PathVariable Long courseId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            Optional<Participation> optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            if (optionalParticipation.isPresent()){
                questionService.resetBestAnswer(questionId);
            }
        }
        model.addAttribute("question", questionService.getQuestion(questionId));
        setAnswersInfo(model, questionId, courseId);
        setQuestionInfo(model, questionId, courseId);
        return "question";
    }

    @GetMapping("/delete-post-{postId}-course-{courseId}")
    public String deletePost(Model model, @PathVariable Long postId, @PathVariable Long courseId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        Post post = postService.getPost(postId);
        if (optionalUser.isPresent()){
            Optional<Participation> optionalParticipation;
            if (courseService.isOwnCourse(courseId, optionalUser.get())){
                optionalParticipation = Optional.of(postService.getPost(postId).getParticipation());
            } else {
                optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            }
            if (optionalParticipation.isPresent() && (participationService.isOwnPost(courseId, optionalUser.get(), post) || courseService.isOwnCourse(courseId, optionalUser.get()))){
                courseService.deletePost(courseId, post);
                participationService.deletePost(courseId, optionalParticipation.get().getStudent(), post);
                post.getComments().forEach(commentService::delete);
                postService.delete(postId);
            }
        }
        return postsLink(model, courseId, (long) -2);
    }

    @GetMapping("/delete-question-{questionId}-course-{courseId}")
    public String deleteQuestion(Model model, @PathVariable Long questionId, @PathVariable Long courseId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        Question question = questionService.getQuestion(questionId);
        if (optionalUser.isPresent()){
            Optional<Participation> optionalParticipation;
            if (courseService.isOwnCourse(courseId, optionalUser.get())){
                optionalParticipation = Optional.of(questionService.getQuestion(questionId).getParticipation());
            } else {
                optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            }
            if (optionalParticipation.isPresent() && (participationService.isOwnQuestion(courseId, optionalUser.get(), question) || courseService.isOwnCourse(courseId, optionalUser.get()))){
                courseService.deleteQuestion(courseId, question);
                participationService.deleteQuestion(courseId, optionalParticipation.get().getStudent(), question);
                question.getAnswers().forEach(answerService::delete);
                questionService.delete(questionId);
            }
        }
        return questionsLink(model, courseId, (long) -2);
    }

    @GetMapping("/link-edit-post-{postId}-course-{courseId}")
    public String linkEditPost(Model model, @PathVariable Long postId, @PathVariable Long courseId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        model.addAttribute("post", postService.getPost(postId));
        return "edit-post";
    }

    @GetMapping("/edit-post-{postId}-course-{courseId}")
    public String editPost(Model model, @PathVariable Long postId, @PathVariable Long courseId, String title, String content, Long themeId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            Optional<Participation> optionalParticipation;
            if (courseService.isOwnCourse(courseId, optionalUser.get())){
                optionalParticipation = Optional.of(postService.getPost(postId).getParticipation());
            } else {
                optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            }
            Post post = postService.getPost(postId);
            if (optionalParticipation.isPresent() && (participationService.isOwnPost(courseId, optionalUser.get(), post) || courseService.isOwnCourse(courseId, optionalUser.get()))){
                Optional<Theme> optionalTheme = themeService.findThemeById(themeId);
                Theme theme = null;
                if (optionalTheme.isPresent()){
                    theme = optionalTheme.get();
                }
                postService.editPost(postId, title, content, theme);
                setPostsInfo(model, courseId, (long) -2);
            }
        }
        return "posts";
    }

    @GetMapping("/link-edit-question-{questionId}-course-{courseId}")
    public String linkEditQuestion(Model model, @PathVariable Long questionId, @PathVariable Long courseId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        model.addAttribute("question", questionService.getQuestion(questionId));
        return "edit-question";
    }

    @GetMapping("/edit-question-{questionId}-course-{courseId}")
    public String editQuestion(Model model, @PathVariable Long questionId, @PathVariable Long courseId, String title, String content, Long themeId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            Optional<Participation> optionalParticipation;
            if (courseService.isOwnCourse(courseId, optionalUser.get())){
                optionalParticipation = Optional.of(questionService.getQuestion(questionId).getParticipation());
            } else {
                optionalParticipation = participationService.existsParticipation(optionalUser.get().getId(), courseId);
            }
            Question question = questionService.getQuestion(questionId);
            if (optionalParticipation.isPresent() && (participationService.isOwnQuestion(courseId, optionalUser.get(), question) || courseService.isOwnCourse(courseId, optionalUser.get()))){
                Optional<Theme> optionalTheme = themeService.findThemeById(themeId);
                Theme theme = null;
                if (optionalTheme.isPresent()){
                    theme = optionalTheme.get();
                }
                questionService.editQuestion(questionId, title, content, theme);
                setQuestionsInfo(model, courseId, (long) -2);
            }
        }
        return "questions";
    }

    @GetMapping("/statistics-course-{courseId}")
    public String statisticsLink(Model model, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        Optional<Course> optionalCourse = courseService.findCourseById(courseId);
        if (optionalUser.isPresent() && optionalCourse.isPresent() && courseService.isOwnCourse(courseId, optionalUser.get())){
            model.addAttribute("topPoints", participationService.getTopGlobal(courseId));
            model.addAttribute("topPosts", participationService.getTopPosts(courseId));
            model.addAttribute("topQuestions", participationService.getTopQuestions(courseId));
            List<Participation> participations = participationService.getStudents(courseId);
            int totalParticipations = participations.size();
            AtomicInteger totalPoints = new AtomicInteger();
            AtomicInteger totalPosts = new AtomicInteger();
            AtomicInteger totalQuestions = new AtomicInteger();
            AtomicInteger totalComments = new AtomicInteger();
            AtomicInteger totalAnswers = new AtomicInteger();
            participations.forEach(participation -> {
                totalPoints.addAndGet(participation.getPoints());
                totalPosts.addAndGet(participation.getPostsSize());
                totalQuestions.addAndGet(participation.getQuestionsSize());
                totalComments.addAndGet(participation.getComments().size());
                totalAnswers.addAndGet(participation.getAnswers().size());
            });
            model.addAttribute("totalParticipations", totalParticipations);
            model.addAttribute("totalPoints", totalPoints);
            model.addAttribute("totalPosts", totalPosts);
            model.addAttribute("totalQuestions", totalQuestions);
            model.addAttribute("totalComments", totalComments);
            model.addAttribute("totalAnswers", totalAnswers);
            model.addAttribute("courseId", courseId);
            model.addAttribute("reportedPosts", optionalCourse.get().getReportedPosts());
            model.addAttribute("reportedQuestions", optionalCourse.get().getReportedQuestions());
            model.addAttribute("reportedParticipations", optionalCourse.get().getReportedParticipations());
            model.addAttribute("bannedParticipations", optionalCourse.get().getBannedParticipations());
        }
        return "statistics";
    }

    @GetMapping("/report-post-{postId}-course-{courseId}")
    public String reportPost(Model model, @PathVariable Long postId, @PathVariable Long courseId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        Post post = postService.getPost(postId);
        if (optionalUser.isPresent()){
            courseService.reportPost(courseId, post);
        }
        return postsLink(model, courseId, (long) -2);
    }

    @GetMapping("/report-question-{questionId}-course-{courseId}")
    public String reportQuestion(Model model, @PathVariable Long questionId, @PathVariable Long courseId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        Question question = questionService.getQuestion(questionId);
        if (optionalUser.isPresent()){
            courseService.reportQuestion(courseId, question);
        }
        return postsLink(model, courseId, (long) -2);
    }

    @GetMapping("/report-participation-user-{userId}-course-{courseId}")
    public String reportParticipation(Model model, @PathVariable Long userId, @PathVariable Long courseId){
        if (isUserBanned(courseId)){
            return "error";
        }
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent()){
            Optional<Participation> optionalParticipation = participationService.existsParticipation(userId, courseId);
            optionalParticipation.ifPresent(participation -> courseService.reportParticipation(courseId, participation));
        }
        return postsLink(model, courseId, (long) -2);
    }

    @GetMapping("/unreport-participation-user-{userId}-course-{courseId}")
    public String unreportParticipation(Model model, @PathVariable Long userId, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent() && courseService.isOwnCourse(courseId, optionalUser.get())){
            Optional<Participation> optionalParticipation = participationService.existsParticipation(userId, courseId);
            optionalParticipation.ifPresent(participation -> courseService.unreportParticipation(courseId, participation));
        }
        return statisticsLink(model, courseId);
    }

    @GetMapping("/unreport-post-{postId}-course-{courseId}")
        public String unreportPost(Model model, @PathVariable Long postId, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent() && courseService.isOwnCourse(courseId, optionalUser.get())){
            courseService.unreportPost(courseId, postService.getPost(postId));
        }
        return statisticsLink(model, courseId);
    }

    @GetMapping("/unreport-question-{postId}-course-{courseId}")
    public String unreportQuestion(Model model, @PathVariable Long questionId, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent() && courseService.isOwnCourse(courseId, optionalUser.get())){
            courseService.unreportQuestion(courseId, questionService.getQuestion(questionId));
        }
        return statisticsLink(model, courseId);
    }

    @GetMapping("/ban-participation-user-{userId}-course-{courseId}")
    public String banParticipation(Model model, @PathVariable Long userId, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent() && courseService.isOwnCourse(courseId, optionalUser.get())){
            Optional<Participation> optionalParticipation = participationService.existsParticipation(userId, courseId);
            optionalParticipation.ifPresent(participation -> courseService.banParticipation(courseId, participation));
        }
        return statisticsLink(model, courseId);
    }

    @GetMapping("/unban-participation-user-{userId}-course-{courseId}")
    public String unbanParticipation(Model model, @PathVariable Long userId, @PathVariable Long courseId){
        setHeaderInfo(model);
        setParticipationHeader(model, courseId);
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent() && courseService.isOwnCourse(courseId, optionalUser.get())){
            Optional<Participation> optionalParticipation = participationService.existsParticipation(userId, courseId);
            optionalParticipation.ifPresent(participation -> courseService.unbanParticipation(courseId, participation));
        }
        return statisticsLink(model, courseId);
    }



}
