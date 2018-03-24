package com.example.eoinh.fallscatcherv3;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SyncManager {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    private static final String ROOT_URL = "http://10.52.2.122/sync2/v1/Api.php?apicall=";
    private static final String URL_CREATE_FALL = ROOT_URL + "createFall";
    private static final String URL_READ_FALLS = ROOT_URL + "getFalls";
    private static final String URL_UPDATE_FALLS = ROOT_URL + "updateFalls";
    private static final String URL_DELETE_FALL = ROOT_URL + "deleteFall&id=";
    private DatabaseHandler databaseHandler;


    public SyncManager(DatabaseHandler databaseHandler){
        this.databaseHandler = databaseHandler;
    }

    public void sync(){
        ArrayList<LoggedFall> localNewLoggedFalls = databaseHandler.getFallsBySyncStatus(1);

        for (LoggedFall loggedFall : localNewLoggedFalls) {
            createFall(loggedFall);
        }
        databaseHandler.setSynced(1);
/*
        ArrayList<Fall> localEditedFalls = databaseHandler.getFallsBySyncStatus(2);

        for (Fall fall: localEditedFalls) {
            editFall(fall);
        }
        databaseHandler.setSynced(2);

        ArrayList<Fall> localDeletedFalls = databaseHandler.getFallsBySyncStatus(3);

        for (Fall fall: localDeletedFalls) {
            deleteFall(fall);
        }
        databaseHandler.clearDeletedFalls();

        ArrayList<Fall> centralFalls = getCentralFalls();
        databaseHandler.sortCentralChanges(centralFalls);

        databaseHandler.clearDeletedFalls();
*/
    }

    private void createFall(LoggedFall loggedFall) {
        HashMap<String, String> params = new HashMap<>();
        params.put("localFallID", "" + loggedFall.getFallID());
        params.put("patientID", "" + loggedFall.getPatientID());
        params.put("date", loggedFall.getDate());
        params.put("timeStatus", loggedFall.getTimeStatus());
        params.put("time", loggedFall.getTime());
        params.put("medical", loggedFall.getMedical());
        params.put("location", loggedFall.getLocation());
        params.put("cause", loggedFall.getCause());
        params.put("injury", loggedFall.getInjury());
        params.put("lengthOfLie", "" + loggedFall.getLengthOfLie());
        params.put("lengthStatus", loggedFall.getLengthStatus());
        params.put("help", loggedFall.getHelp());
        params.put("relapse", loggedFall.getRelapse());
        params.put("comment", loggedFall.getComment());

        PerformNetworkRequest request = new PerformNetworkRequest(URL_CREATE_FALL, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void deleteFall(LoggedFall loggedFall){

    }

    private void editFall(LoggedFall loggedFall){

    }

    public ArrayList<LoggedFall> getCentralFalls() {
        ArrayList<LoggedFall> loggedFalls = new ArrayList<>();
        //Get Stuff

        return loggedFalls;
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
                    //Return Error
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
