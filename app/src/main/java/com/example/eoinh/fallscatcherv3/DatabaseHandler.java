package com.example.eoinh.fallscatcherv3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int databaseVersion = 1;
    private static final String databaseName = "falls.db";

    private static final String Table_Fall = "fall";
    private static final String Column_PatientID = "PatientID";
    private static final String Column_FallID = "fallID";
    private static final String Column_Date = "date";
    private static final String Column_TimeStatus = "timeStatus";
    private static final String Column_Location = "location";
    private static final String Column_Cause = "cause";
    private static final String Column_Time= "time";
    private static final String Column_Injury= "injury";
    private static final String Column_LengthOfLie = "lengthOfLie";
    private static final String Column_LengthStatus = "lengthStatus";
    private static final String Column_Medical= "medical";
    private static final String Column_Help= "help";
    private static final String Column_Relapse= "relapse";
    private static final String Column_Comment= "comment";
    private static final String Column_Sync= "sync";

    private static final String Table_User = "user";
    //Patient ID already intialised
    private static final String Column_UserName = "name";
    private static final String Column_Notification = "notification";
    private static final String Column_NotificationMinute = "notificationMinute";
    private static final String Column_NotificationHour = "notificationHour";

    public DatabaseHandler(Context context, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        super(context, databaseName, factory, databaseVersion, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " +  Table_Fall + " (" +
            Column_FallID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Column_PatientID + " INTEGER, " +
            Column_Date + " TEXT, " +
            Column_TimeStatus + " TEXT, " +
            Column_Medical + " TEXT, " +
            Column_Location + " TEXT, " +
            Column_Cause + " TEXT, " +
            Column_Time + " TEXT, " +
            Column_Injury + " TEXT, " +
            Column_LengthOfLie + " INTEGER," +
            Column_LengthStatus + " TEXT, " +
            Column_Help + " TEXT, " +
            Column_Relapse + " TEXT, " +
            Column_Comment + " TEXT, " +
            Column_Sync + " INTEGER " +
            ");";
        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE " +  Table_User + " (" +
                Column_PatientID + " INTEGER, " +
                Column_UserName + " TEXT, " +
                Column_Notification + " TEXT, " +
                Column_NotificationMinute + " INTEGER, " +
                Column_NotificationHour + " INTEGER " +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Fall);
        onCreate(sqLiteDatabase);
    }

    public void fix(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_Fall);
        onCreate(sqLiteDatabase);
    }

    public void addFall(LoggedFall loggedFall){
        ContentValues values = new ContentValues();
        values.put(Column_PatientID, loggedFall.getPatientID());
        values.put(Column_Date, loggedFall.getDate());
        values.put(Column_Time, loggedFall.getTime());
        values.put(Column_TimeStatus, loggedFall.getTimeStatus());
        values.put(Column_Location, loggedFall.getLocation());
        values.put(Column_Cause, loggedFall.getCause());
        values.put(Column_Injury, loggedFall.getInjury());
        values.put(Column_LengthOfLie, loggedFall.getLengthOfLie());
        values.put(Column_LengthStatus, loggedFall.getLengthStatus());
        values.put(Column_Medical, loggedFall.getMedical());
        values.put(Column_Help, loggedFall.getHelp());
        values.put(Column_Relapse, loggedFall.getRelapse());
        values.put(Column_Comment, loggedFall.getComment());
        values.put(Column_Sync, 1);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(Table_Fall, null, values);
        db.close();
        Log.d("Fall added", "True");
    }

    public void deleteLocalFall(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("Update " + Table_Fall + " set " + Column_Sync + " = 3 where " + Column_FallID + " = " + id);
        db.close();
    }

    public void clearDeletedFalls(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + Table_Fall + " WHERE " + Column_Sync + " = 3");
        db.close();
    }

    public void editFall(LoggedFall loggedFall){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Column_PatientID, loggedFall.getPatientID());
        values.put(Column_Date, loggedFall.getDate());
        values.put(Column_Time, loggedFall.getTime());
        values.put(Column_TimeStatus, loggedFall.getTimeStatus());
        values.put(Column_Location, loggedFall.getLocation());
        values.put(Column_Cause, loggedFall.getCause());
        values.put(Column_Injury, loggedFall.getInjury());
        values.put(Column_LengthOfLie, loggedFall.getLengthOfLie());
        values.put(Column_LengthStatus, loggedFall.getLengthStatus());
        values.put(Column_Medical, loggedFall.getMedical());
        values.put(Column_Help, loggedFall.getHelp());
        values.put(Column_Relapse, loggedFall.getRelapse());
        values.put(Column_Comment, loggedFall.getComment());
        values.put(Column_Comment, 2);

        sqLiteDatabase.update(Table_Fall, values, "_id="+ loggedFall.getFallID(), null);
    }

    public void editUser(User user){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Column_PatientID, user.getUserID());
        values.put(Column_UserName, user.getUserName());
        values.put(Column_Notification, user.isNotification());
        values.put(Column_NotificationMinute, user.getNotificationMinute());
        values.put(Column_NotificationHour, user.getNotificationHour());

        sqLiteDatabase.update(Table_User, values, "_id="+user.getUserID(), null);
    }

    public int getPatientID(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String query = "SELECT COUNT (" +  Column_PatientID + ") FROM " + Table_User + " WHERE 1";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();

        int count= cursor.getInt(0);

        if (count != 1) {
            return -1;
        }


        query = "Select " + Column_PatientID + " from " + Table_Fall + " where 1";
        cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();

        int ID = cursor.getInt(cursor.getColumnIndex(Column_PatientID));

        return ID;
    }

    public User getUser(){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String query = "Select * from " + Table_User + " where 1";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();

        String name = cursor.getString(cursor.getColumnIndex(Column_UserName));
        int id = cursor.getInt(cursor.getColumnIndex(Column_PatientID));
        boolean notificaiton = (cursor.getString(cursor.getColumnIndex(Column_Notification))).contains("true");
        int notificationMinute = cursor.getInt(cursor.getColumnIndex(Column_NotificationMinute));
        int notificationHour = cursor.getInt(cursor.getColumnIndex(Column_NotificationHour));

        User user = new User (name,id,notificaiton, notificationMinute, notificationHour);

        return user;
    }

    public ArrayList<LoggedFall> getFalls(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ArrayList<LoggedFall> loggedFalls = new ArrayList<>();
        String query = "Select * from " + Table_Fall + " where 1";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();

        if (cursor.moveToFirst()){
            do {
                int fallID = cursor.getInt(cursor.getColumnIndex(Column_FallID));
                int patientID = cursor.getInt(cursor.getColumnIndex(Column_PatientID));
                String date  = cursor.getString(cursor.getColumnIndex(Column_Date));
                String timeStatus  = cursor.getString(cursor.getColumnIndex(Column_TimeStatus));
                String time  = cursor.getString(cursor.getColumnIndex(Column_Time));
                String location  = cursor.getString(cursor.getColumnIndex(Column_Location));
                String cause  = cursor.getString(cursor.getColumnIndex(Column_Cause));
                String injury = cursor.getString(cursor.getColumnIndex(Column_Injury));
                int lengthOfLie  = cursor.getInt(cursor.getColumnIndex(Column_LengthOfLie));
                String lengthStatus = cursor.getString(cursor.getColumnIndex(Column_LengthStatus));
                String help = cursor.getString(cursor.getColumnIndex(Column_Help));
                String relapse = cursor.getString(cursor.getColumnIndex(Column_Relapse));
                String comment = cursor.getString(cursor.getColumnIndex(Column_Comment));
                String medical  = cursor.getString(cursor.getColumnIndex(Column_Medical));
                int sync  = cursor.getInt(cursor.getColumnIndex(Column_Sync));

                LoggedFall loggedFall = new LoggedFall(fallID, patientID, date, timeStatus, location,cause,time,injury,lengthOfLie, lengthStatus, medical,help,relapse,comment,sync);

                loggedFalls.add(loggedFall);
            }while(cursor.moveToNext());
        }

        return loggedFalls;
    }

    public ArrayList<LoggedFall> getFallsBySyncStatus(int status){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ArrayList<LoggedFall> loggedFalls = new ArrayList<>();
        String query = "Select * from " + Table_Fall + " where sync = " + status;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();

        if (cursor.moveToFirst()){
            do {
                int fallID = cursor.getInt(cursor.getColumnIndex(Column_FallID));
                int patientID = cursor.getInt(cursor.getColumnIndex(Column_PatientID));
                String date  = cursor.getString(cursor.getColumnIndex(Column_Date));
                String timeStatus  = cursor.getString(cursor.getColumnIndex(Column_TimeStatus));
                String time  = cursor.getString(cursor.getColumnIndex(Column_Time));
                String location  = cursor.getString(cursor.getColumnIndex(Column_Location));
                String cause  = cursor.getString(cursor.getColumnIndex(Column_Cause));
                String injury = cursor.getString(cursor.getColumnIndex(Column_Injury));
                int lengthOfLie  = cursor.getInt(cursor.getColumnIndex(Column_LengthOfLie));
                String lengthStatus = cursor.getString(cursor.getColumnIndex(Column_LengthStatus));
                String help = cursor.getString(cursor.getColumnIndex(Column_Help));
                String relapse = cursor.getString(cursor.getColumnIndex(Column_Relapse));
                String comment = cursor.getString(cursor.getColumnIndex(Column_Comment));
                String medical  = cursor.getString(cursor.getColumnIndex(Column_Medical));

                LoggedFall loggedFall = new LoggedFall(fallID, patientID, date, timeStatus, location,cause,time,injury,lengthOfLie, lengthStatus, medical,help,relapse,comment);

                loggedFalls.add(loggedFall);
            }while(cursor.moveToNext());
        }
        return loggedFalls;
    }


    public void sortCentralChanges(ArrayList<LoggedFall> loggedFalls){
        for (LoggedFall loggedFall : loggedFalls){
            switch (loggedFall.getSync()) {
                case 1: addFall(loggedFall); ; break;
                case 2: editFall(loggedFall); break;
                case 3: deleteLocalFall(loggedFall.getFallID()); break;
            }
        }
    }



    public void clearAll() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "DELETE FROM `fall` WHERE 1";
        sqLiteDatabase.execSQL(query);
    }

    public void setSynced(int sync) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "UPDATE " + Table_Fall + " set " + Column_Sync + " = 0 WHERE " + sync;
        sqLiteDatabase.execSQL(query);
    }
}
