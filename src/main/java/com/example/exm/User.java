package com.example.exm;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public class User implements Serializable {
    private String username;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String lastName;
    private String bio;
    private String location;
    private String website;
    public HashSet<String> followers;
    public HashMap<String, Following> following;
    public ConcurrentHashMap<Integer, Tweet> tweets;
    public HashSet<Integer> likes;
    private String avatar;
    private String header;
    private String birthDate;
    private LocalDateTime joinDate;
    private Gender gender;
    private String password;
    private transient UserController controller;
    private transient ShowUserController showController;

    public User() {
        followers = new HashSet<>();
        following = new HashMap<>();
        tweets = new ConcurrentHashMap<>();
        likes = new HashSet<>();
        gender = Gender.UNKNOWN;
        joinDate = LocalDateTime.MIN;
        avatar = "PdefaultAvatar.png";
        header = "Pheader.png";
    }

    public User(String firstName, String lastName, String username, String password, LocalDateTime joinDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        followers = new HashSet<>();
        following = new HashMap<>();
        tweets = new ConcurrentHashMap<>();
        likes = new HashSet<>();
        gender = Gender.UNKNOWN;
        this.joinDate = joinDate;
        avatar = "PdefaultAvatar.png";
        header = "Pheader.png";
    }

    public User(String firstName, String lastName, String username, LocalDateTime joinDate, String password, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        followers = new HashSet<>();
        following = new HashMap<>();
        tweets = new ConcurrentHashMap<>();
        likes = new HashSet<>();
        gender = Gender.UNKNOWN;
        this.joinDate = joinDate;
        this.birthDate = birthDate;
        avatar = "PdefaultAvatar.png";
        header = "Pheader.png";
    }

    public Pane toShow() throws IOException {
        makeShowController();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showUser.fxml"));
        fxmlLoader.setController(showController);
        Pane p = fxmlLoader.load();
        showController.showBuild(this);
        return p;
    }

    private void makeShowController() {
        showController = Client.showUserControllers.get(username);
        if (showController == null) {
            showController = new ShowUserController();
            Client.showUserControllers.put(username, showController);
        }
        if (Client.user.following.get(username) == null) {
            Following f = new Following(username);
            f.setUser(this);
            Client.user.following.put(username, f);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
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

    public Image getAvatar() {
        return new Image(String.valueOf(getClass().getResource(avatar)));
    }

    public String getAvatarAsString() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Image getHeader() {
        Image res = new Image(String.valueOf(getClass().getResource(header)));
        return res;
    }

    public String getHeaderAsString() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    public String getName() {
        return firstName + " " + lastName;
    }
    @Override
    public String toString() {
        return "id: " + ((phoneNumber == null) ? email : phoneNumber) + "\n"
                + "Name: " + firstName + " " + lastName + "\n"
                + "username: " + username;
    }
}