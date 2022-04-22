package com.example.backend.service;

import com.example.backend.model.Comment;
import com.example.backend.model.Participation;
import com.example.backend.model.Post;
import com.example.backend.model.User;
import com.example.backend.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(Participation participation, String title, String content){
        Post post = new Post(participation, title, content);
        return postRepository.save(post);
    }

    public void setOutstanding(Long postId){
        Post post = postRepository.getById(postId);
        post.setOutstanding();
        postRepository.save(post);
    }

    public void setInteresting(Long postId){
        Post post = postRepository.getById(postId);
        post.setInteresting();
        postRepository.save(post);
    }

    public List<Post> getStandardPosts(List<Post> posts){
        return posts.stream().filter(post -> (!post.isInteresting() && !post.isOutstanding())).collect(Collectors.toList());
    }

    public Object getOutstandingPosts(List<Post> posts) {
        return posts.stream().filter(post -> (!post.isInteresting() && post.isOutstanding())).collect(Collectors.toList());
    }

    public Object getInterestingPosts(List<Post> posts) {
        return posts.stream().filter(Post::isInteresting).collect(Collectors.toList());
    }

    public Post getPost(Long postId) {
        return postRepository.getById(postId);
    }

    public void addView(Long postId) {
        Post post = postRepository.getById(postId);
        post.addView();
        postRepository.save(post);
    }

    public void addComment(Long postId, Comment comment) {
        Post post = postRepository.getById(postId);
        post.addComment(comment);
        postRepository.save(post);
    }
}
