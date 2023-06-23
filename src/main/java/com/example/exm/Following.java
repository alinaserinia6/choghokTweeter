package com.example.exm;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Following implements Serializable {
    private String username;
    private LocalDateTime time;
    private boolean isFollow;

    public Following(String username, LocalDateTime time) {
        this.username = username;
        this.time = time;
        isFollow = true;
    }

    public void follow() {
        isFollow = !isFollow;
    }

    public String getUser() {
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

}
