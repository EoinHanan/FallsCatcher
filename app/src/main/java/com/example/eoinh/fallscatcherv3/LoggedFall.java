package com.example.eoinh.fallscatcherv3;

/**
 * Created by EoinH on 04/01/2018.
 */

public class LoggedFall extends Fall {
    //Primary Key
    private int fallID;
    //Required Info
    private int patientID;
    private String date;
    private String timeStatus;
    private String location;
    private String cause;
    //Additional info
    private String time;
    private String injury;
    private String medical;
    private int lengthOfLie;
    private String lengthStatus;
    private String help;
    private String relapse;
    private String comment;
    private int sync;

    public LoggedFall(int patientID, String date, String timeStatus, String location, String cause){
        this.patientID = patientID;
        this.date = date;
        this.timeStatus = timeStatus;
        this.location = location;
        this.cause = cause;
        this.time= null;
        this.injury = null;
        this.lengthOfLie = -1;
        this.medical = null;
        this.help = null;
}

    public LoggedFall(int patientID, String date, String timeStatus, String location, String cause,
                      String time, String injury, int lengthOfLie, String lengthStatus, String medical, String help, String relapse, String comment ){
        this.patientID = patientID;
        this.date = date;
        this.timeStatus = timeStatus;
        this.time= time;
        this.location = location;
        this.cause = cause;
        this.injury = injury;
        this.lengthOfLie = lengthOfLie;
        this.lengthStatus = lengthStatus;
        this.medical = medical;
        this.help = help;
        this.relapse = relapse;
        this.comment = comment;
    }
    public LoggedFall(int fallID, int patientID, String date, String timeStatus, String location, String cause, String time, String injury, int lengthOfLie, String lengthStatus, String medical, String help, String relapse, String comment ){
        this.fallID = fallID;
        this.patientID = patientID;
        this.date = date;
        this.timeStatus = timeStatus;
        this.location = location;
        this.cause = cause;
        this.time= time;
        this.injury = injury;
        this.lengthOfLie = lengthOfLie;
        this.lengthStatus = lengthStatus;
        this.medical = medical;
        this.help = help;
        this.relapse = relapse;
        this.comment = comment;
        this.sync = 1;
    }
    public LoggedFall(int fallID, int patientID, String date, String timeStatus, String location,
                      String cause, String time, String injury, int lengthOfLie, String lengthStatus,
                      String medical, String help, String relapse, String comment, int sync){
        this.fallID = fallID;
        this.patientID = patientID;
        this.date = date;
        this.timeStatus = timeStatus;
        this.location = location;
        this.cause = cause;
        this.time= time;
        this.injury = injury;
        this.lengthOfLie = lengthOfLie;
        this.lengthStatus = lengthStatus;
        this.medical = medical;
        this.help = help;
        this.relapse = relapse;
        this.comment = comment;
        this.sync = sync;
    }
    public int getPatientID(){
        return patientID;
    }
    public int getFallID(){
        return fallID;
    }
    public String getDate(){
        return date;
    }
    public String getTimeStatus(){
        return timeStatus;
    }
    public String getTime(){
        return time;
    }
    public String getLocation(){
        return location;
    }
    public String getInjury(){
        return injury;
    }
    public String getCause(){
        return cause;
    }
    public int getLengthOfLie(){
        return lengthOfLie;
    }
    public String getMedical(){
        return medical;
    }
    public String getLengthStatus(){return lengthStatus;}
    public String getHelp(){
        return help;
    }
    public String getRelapse(){
            return relapse;
    }
    public String getComment(){
        return comment;
    }
    public int getSync(){
        return sync;
    }
}