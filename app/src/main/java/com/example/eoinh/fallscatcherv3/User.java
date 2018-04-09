package com.example.eoinh.fallscatcherv3;


/**
 * Created by EoinH on 15/01/2018.
 */

public class User {
    private String userName;
    private int userID;
    private String password;
    private String notification;


    public User(String userName, String password, int userID){
        this.userName = userName;
        this.userID = userID;
        this.notification = null;

    }
    public User(String userName, String password, int userID,
                String notification){
        this.userName = userName;
        this.password = password;
        this.userID = userID;
        this.notification = notification;
    }


    public String getUserName(){
        return userName;
    }
    public String getPassword() {
        return password;
    }

    public int getUserID(){
        return userID;
    }
    public boolean isNotification(){
        return (notification != null);
    }
    public String getNotification(){
        return notification;}
}
