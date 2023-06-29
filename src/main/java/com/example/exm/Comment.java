package com.example.exm;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Comment extends Tweet {
    private String usrRepId;
    private int id;

    public Comment(String text, String username) {
        super(text, username);
        dt = LocalDateTime.now();
        likes = new ArrayList<>();
        comments = new ArrayList<>();
        retweet = new ArrayList<>();
    }
}
