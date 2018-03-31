package com.example.eoinh.fallscatcherv3;

/**
 * Created by EoinH on 04/01/2018.
 */

public class Fall {
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


    public Fall(int patientID, String date, String timeStatus, String location, String cause){
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

    public Fall(int patientID, String date, String timeStatus, String location, String cause,
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
    public Fall(int fallID, int patientID, String date, String timeStatus, String location, String cause, String time, String injury, int lengthOfLie, String lengthStatus, String medical, String help, String relapse, String comment ){
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
    public Fall(int fallID, int patientID, String date, String timeStatus, String location,
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

    public void setPatientID(int patientID){
        this.patientID = patientID;
    }
    public void setFallID(int fallID){
        this.fallID = fallID;
    }
    public void setDate(String date){
        this.date = date;
    }
    public void setTimeStatus(String timeStatus){
        this.timeStatus = timeStatus;
    }
    public void setTime(String time){
        this.time = time;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public void setInjury(String injury){
        this.injury = injury;
    }
    public void setCause(String cause){
        this.cause = cause;
    }
    public void setLengthOfLie(int lengthOfLie){
        this.lengthOfLie = lengthOfLie;
    }
    public void setMedical(String medical){
        this.medical = medical;
    }
    public void setLengthStatus(String lengthStatus){this.lengthStatus = lengthStatus;}
    public void setHelp(String help){
        this.help = help;
    }
    public void setRelapse(String relapse){
        this.relapse = relapse;
    }
    public void setComment(String comment){
        this.comment = comment;
    }
    public void setSync(int sync){
        this.sync = sync;
    }
}