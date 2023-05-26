package com.example.exm;

import javafx.scene.image.ImageView;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

enum Gender {
    MALE,
    FEMALE,
    UNKNOWN
}
public class User implements Serializable {
    private String username;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String bio;
    public ArrayList<User> followers;
    public ArrayList<User> following;
    public ArrayList<Tweet> tweets;
    private ImageView avatar;
    private ImageView header;
    private LocalDateTime birthDate;
    private LocalDate joinDate;
    private Gender gender;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public ImageView getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }

    public ImageView getHeader() {
        return header;
    }

    public void setHeader(ImageView header) {
        this.header = header;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return phoneNumber;
    }
}
