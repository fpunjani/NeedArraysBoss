package com.example.moodtracker;

import java.util.ArrayList;

public class User {

    private String userID, email, password, phone, imageurl;
    private int numFollwers=0;
    private ArrayList<Mood> moodHistory = new ArrayList<>();
    private ArrayList<String> followingList = new ArrayList<>();
    private ArrayList<Integer> followingPermissionList = new ArrayList<>();
    private ArrayList<Notification> notification = new ArrayList<>();

    public User(String userID, String email, String password, ArrayList<Mood> moodHistory, String imageurl, String phone) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.moodHistory = moodHistory;
        this.imageurl=imageurl;
    }

    public User(String userID, String email, String password, ArrayList<Mood> moodHistory) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.phone = "";
        this.moodHistory = moodHistory;
        imageurl="";
    }

    public User(String userID,String imageurl, String id, String email, String password) {
        this.userID = userID;
        this.imageurl=imageurl;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
    public User(String userID, String email, String password) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.phone = "";
        imageurl="";
        //moodHistory = null;
    }
    public User(String userID, String email, String password, String id, String phone, String imageurl) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.imageurl=imageurl;
        moodHistory = null;
    }
    public User(String userID, String password) {
        this.userID = userID;
        this.email = null;
        this.password = password;
        this.phone = null;
    }
    public User() {
        //do nothing
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }


    public ArrayList<Mood> getMoodHistory() { return moodHistory; }
    public void setMoodHistory(ArrayList<Mood> moods) { moodHistory = moods; }
<<<<<<< HEAD
    public ArrayList<String> getFollowingList() { return followingList; }
    public void setFollowingList(ArrayList<String> friends) { followingList = friends; }
    public ArrayList<Integer> getFollowingPermissionList() { return followingPermissionList; }
    public void setFollowingPermissionList(ArrayList<Integer> code) { followingPermissionList = code; }
    public ArrayList<Notification> getNotification() { return notification; }
    public void setNotification(ArrayList<Notification> notification) { this.notification = notification; }
=======
    public ArrayList<String> getFriendList() {  return friendList; }
    public void setFriendList(ArrayList<String> friends) { friendList = friends; }
>>>>>>> 735740fa58aea85c1ef11175f658f4629f4bdf27
}