package com.example.backend.model;

public class CommentDAO{

    private Participation participation;
    private String content;
    private boolean liked;

    public CommentDAO(Participation participation, String content, boolean liked) {
        this.participation = participation;
        this.content = content;
        this.liked = liked;
    }
}
