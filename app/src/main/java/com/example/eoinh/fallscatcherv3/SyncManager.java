package com.example.eoinh.fallscatcherv3;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SyncManager {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    private static final String ROOT_URL = "http://10.52.2.110/sync2/v1/Api.php?apicall=";
    private static final String URL_CREATE_FALL = ROOT_URL + "createFall";
    private static final String URL_READ_FALLS = ROOT_URL + "getFalls";
    private static final String URL_UPDATE_FALL = ROOT_URL + "updateFall";
    private static final String URL_DELETE_FALL = ROOT_URL + "deleteFall&id=";
    private DatabaseHandler databaseHandler;


    public SyncManager(DatabaseHandler databaseHandler){
        this.databaseHandler = databaseHandler;
    }

    public void sync(){
        ArrayList<Fall> localNewFalls = databaseHandler.getFallsBySyncStatus(1);

        for (Fall fall : localNewFalls) {
            createFall(fall);
        }
        databaseHandler.setSynced(1);

        ArrayList<Fall> localEditedFalls = databaseHandler.getFallsBySyncStatus(2);
        Log.d("Falls", String.valueOf(localEditedFalls.size()));

        for (Fall fall: localEditedFalls) {
            updateFall(fall);
        }
        databaseHandler.setSynced(2);

        ArrayList<Fall> localDeletedFalls = databaseHandler.getFallsBySyncStatus(3);

        for (Fall fall: localDeletedFalls) {
            deleteFall(fall);
        }
        databaseHandler.clearDeletedFalls();
         /*
        ArrayList<Fall> centralFalls = getCentralFalls();
        databaseHandler.sortCentralChanges(centralFalls);

        databaseHandler.clearDeletedFalls();
*/
    }

    private void createFall(Fall fall) {
        HashMap<String, String> params = new HashMap<>();
        params.put("localFallID", "" + fall.getFallID());
        params.put("patientID", "" + fall.getPatientID());
        params.put("date", fall.getDate());
        params.put("timeStatus", fall.getTimeStatus());
        params.put("time", fall.getTime());
        params.put("medical", fall.getMedical());
        params.put("location", fall.getLocation());
        params.put("cause", fall.getCause());
        params.put("injury", fall.getInjury());
        params.put("lengthOfLie", "" + fall.getLengthOfLie());
        params.put("lengthStatus", fall.getLengthStatus());
        params.put("help", fall.getHelp());
        params.put("relapse", fall.getRelapse());
        params.put("comment", fall.getComment());

        PerformNetworkRequest request = new PerformNetworkRequest(URL_CREATE_FALL, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void deleteFall(Fall fall){

    }

    private void updateFall(Fall fall){
        HashMap<String, String> params = new HashMap<>();
        params.put("localFallID", String.valueOf(fall.getFallID()));
        params.put("patientID", String.valueOf(fall.getPatientID()));
        params.put("date", fall.getDate());
        params.put("timeStatus", fall.getTimeStatus());
        params.put("time", fall.getTime());
        params.put("medical", fall.getMedical());
        params.put("location", fall.getLocation());
        params.put("cause", fall.getCause());
        params.put("injury", fall.getInjury());
        params.put("lengthOfLie", String.valueOf(fall.getLengthOfLie()));
        params.put("lengthStatus", fall.getLengthStatus());
        params.put("help", fall.getHelp());
        params.put("relapse", fall.getRelapse());
        params.put("comment", fall.getComment());

        Log.d("localFallID", String.valueOf(fall.getFallID()));
        Log.d("patientID", String.valueOf(fall.getPatientID()));
        Log.d("date", fall.getDate());
        Log.d("timeStatus", fall.getTimeStatus());
        Log.d("time", fall.getTime());
        Log.d("medical", fall.getMedical());
        Log.d("location", fall.getLocation());
        Log.d("cause", fall.getCause());
        Log.d("injury", fall.getInjury());
        Log.d("lengthOfLie", String.valueOf(fall.getLengthOfLie()));
        Log.d("lengthStatus", fall.getLengthStatus());
        Log.d("help", fall.getHelp());
        Log.d("relapse", fall.getRelapse());
        Log.d("comment", fall.getComment());

        PerformNetworkRequest request = new PerformNetworkRequest(URL_UPDATE_FALL, params, CODE_POST_REQUEST);
        request.execute();
    }

    public ArrayList<Fall> getCentralFalls() {
        ArrayList<Fall> falls = new ArrayList<>();
        //Get Stuff

        return falls;
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
