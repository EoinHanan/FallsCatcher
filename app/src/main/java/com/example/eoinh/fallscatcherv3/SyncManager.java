package com.example.eoinh.fallscatcherv3;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SyncManager {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    private static final String ROOT_URL = "http://178.62.220.209/CommunicationApi/v1/api.php?apicall=";
    private static final String URL_CREATE_FALL = ROOT_URL + "createFall";
    private static final String URL_READ_FALLS = ROOT_URL + "getFalls&patientID=";
    private static final String URL_UPDATE_FALL = ROOT_URL + "updateFall";
    private static final String URL_DELETE_FALL = ROOT_URL + "deleteFall&fallID=";
    private ArrayList<Fall> falls;
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

        for (Fall fall: localEditedFalls) {
            updateFall(fall);
        }
        databaseHandler.setSynced(2);

        ArrayList<Fall> localDeletedFalls = databaseHandler.getFallsBySyncStatus(3);

        for (Fall fall: localDeletedFalls) {
            deleteFall(fall);
        }
        databaseHandler.clearDeletedFalls();

        falls = new ArrayList<Fall>();
        readCentralFalls();



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
        PerformNetworkRequest request = new PerformNetworkRequest(URL_DELETE_FALL + fall.getFallID() + "&patientID=" + fall.getPatientID(), null, CODE_GET_REQUEST);
        request.execute();
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

        PerformNetworkRequest request = new PerformNetworkRequest(URL_UPDATE_FALL, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void readCentralFalls() {
        PerformNetworkRequest request = new PerformNetworkRequest(URL_READ_FALLS + databaseHandler.getPatientID(), null, CODE_GET_REQUEST);
        request.execute();
    }
    private void getCentralFalls(JSONArray fallsJSON) throws JSONException {
        for (int i = 0; i < fallsJSON.length(); i++) {
            JSONObject obj = fallsJSON.getJSONObject(i);

            falls.add(new Fall(
                obj.getInt("localFallID"),
                obj.getInt("patientID"),
                obj.getString("date"),
                obj.getString("timeStatus"),
                obj.getString("location"),
                obj.getString("cause"),
                obj.getString("time"),
                obj.getString("injury"),
                obj.getInt("lengthOfLie"),
                obj.getString("lengthStatus"),
                obj.getString("medical"),
                obj.getString("help"),
                obj.getString("relapse"),
                obj.getString("comment"),
                obj.getInt("syncStatus")
            ));
        }
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
                    getCentralFalls(object.getJSONArray("falls"));
                    databaseHandler.sortCentralChanges(falls);
                    databaseHandler.clearDeletedFalls();
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
