package com.example.eoinh.fallscatcherv3;


/**
 * Created by EoinH on 15/01/2018.
 */

public class User {
    private String userName;
    private int userID;
    private boolean notification;
    private int notificationMinute;
    private int notificationHour;


    public User(String userName, int userID){
        this.userName = userName;
        this.userID = userID;
        this.notification = false;
        this.notificationMinute = -1;
        this.notificationHour = -1;
    }
    public User(String userName, int userID, boolean notification, int notificationMinute, int notificationHour){
        this.userName = userName;
        this.userID = userID;
        this.notification = notification;
        this.notificationMinute = notificationMinute;
        this.notificationHour = notificationHour;
    }


    public String getUserName(){
        return userName;
    }

    public int getUserID(){
        return userID;
    }
    public boolean isNotification(){
        return notification;
    }
    public int getNotificationMinute(){
        return notificationMinute;
    }
    public int getNotificationHour(){
        return notificationHour;
    }
}
