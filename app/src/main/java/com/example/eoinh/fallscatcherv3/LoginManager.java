package com.example.eoinh.fallscatcherv3;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class LoginManager {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    private static final String ROOT_URL = "http://178.62.220.209/UsersApi/v1/api.php?apicall=";
    private static final String URL_GET_USERS = ROOT_URL + "getUsers";
    private static final String URL_UPDATE_USER = ROOT_URL + "updateUser";

    private ArrayList<User> users;
    private DatabaseHandler databaseHandler;

    public LoginManager(DatabaseHandler databaseHandler){
        this.databaseHandler  = databaseHandler;
    }

    public boolean isLoggedIn(){
        return databaseHandler.checkLoggedIn();
    }

    public void getUsers(){
        LoginManager.PerformNetworkRequest request = new LoginManager.PerformNetworkRequest(URL_GET_USERS, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void fillArray(JSONArray usersJSON) throws JSONException {
        users = new ArrayList<>();
        for (int i = 0; i < usersJSON.length(); i++) {
            JSONObject obj = usersJSON.getJSONObject(i);

            users.add(new User(
                    obj.getString("userName"),
                    obj.getString("password"),
                    obj.getInt("userID"),
                    obj.getString("notification")
            ));
        }
    }

    public boolean checkLogin(String name, String password){
        for (User user: users){
            if (user.getUserName().equals(name) & user.getPassword().equals(password)) {
                setUser(user);
                return true;
            }
        }
        return false;
    }

    public void setUser(User user){
        databaseHandler.setUser(user);
    }

    public void logout(){
        databaseHandler.logout();
    }
    public void updateNotificationTimes(User user){
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", user.getUserName());
        params.put("userID", String.valueOf(user.getUserID()));
        params.put("password", user.getPassword());
        params.put("notificationMinute", String.valueOf(user.getNotification()));


        LoginManager.PerformNetworkRequest request = new LoginManager.PerformNetworkRequest(URL_UPDATE_USER, params, CODE_POST_REQUEST);
        request.execute();
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;

        HashMap<String, String> params;

        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    fillArray(object.getJSONArray("falls"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }
}