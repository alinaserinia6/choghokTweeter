package com.example.exm;

import com.gluonhq.charm.glisten.control.Avatar;
import javafx.scene.image.ImageView;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

enum Gender {
    MALE,
    FEMALE,
    UNKNOWN
}
public class User implements Serializable {
    private String username;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String lastName;
    private String bio;
    public HashSet<String> followers;
    public HashMap<String, LocalDateTime> following;
    public ArrayList<Tweet> tweets;
    public ArrayList<Comment> comments;
    private Avatar avatar;
    private ImageView header;
    private String birthDate;
    private String joinDate;
    private Gender gender;
    private String password;

    public User() {
        followers = new HashSet<>();
        following = new HashMap<>();
        tweets = new ArrayList<>();
        comments = new ArrayList<>();
        gender = Gender.UNKNOWN;
    }

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

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public ImageView getHeader() {
        return header;
    }

    public void setHeader(ImageView header) {
        this.header = header;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "id: " + ((phoneNumber == null) ? email : phoneNumber) + "\n"
                + "Name: " + firstName + " " + lastName + "\n"
                + "username: " + username;
    }
}
