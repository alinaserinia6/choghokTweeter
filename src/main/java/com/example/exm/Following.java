package com.example.exm;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Following implements Serializable {
    private final String username;
    private LocalDateTime time;
    private boolean isFollow;
    private User user;
    public Following(String username, LocalDateTime time) {
        this.username = username;
        this.time = time;
        isFollow = true;
    }

    public Following(String username) {
        this.username = username;
        this.time = LocalDateTime.MIN;
        this.isFollow = false;
    }

    public void follow() {
        isFollow = !isFollow;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public boolean isFollowing() {
        return isFollow;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
