package com.example.eoinh.fallscatcherv3;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by EoinH on 11/03/2018.
 */

public class SyncManager {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    private static final String ROOT_URL = "http://10.52.2.122/sync2/v1/Api.php?apicall=";
    private static final String URL_CREATE_FALL = ROOT_URL + "createFall";
    private static final String URL_READ_HEROES = ROOT_URL + "getheroes";
    private static final String URL_UPDATE_HERO = ROOT_URL + "updatehero";
    private static final String URL_DELETE_HERO = ROOT_URL + "deletehero&id=";
    private DatabaseHandler databaseHandler;


    public SyncManager(DatabaseHandler databaseHandler){
        this.databaseHandler = databaseHandler;
    }

    public void sync(){
        ArrayList<Fall> localNewFalls = databaseHandler.getFallsBySyncStatus(1);

        for (Fall fall: localNewFalls) {
            createFall(fall);
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

        ArrayList<Fall> centralNewFalls = getCentralNewFalls();
        databaseHandler.newFallsFromCentral(centralNewFalls);

        ArrayList<Fall> centralUpdatedFalls = getCentralUpdateFalls();
        databaseHandler.updateFallsFromCentral(centralUpdatedFalls);

        ArrayList<Fall> centralDeletedFalls = getCentralDeletedFalls();
        databaseHandler.deleteFallsFromCentral(centralDeletedFalls);
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

    private void editFall(Fall fall){

    }

    public ArrayList<Fall> getCentralNewFalls() {
        ArrayList<Fall> falls = new ArrayList<>();
        //Get Stuff

        return falls;
    }
    public ArrayList<Fall> getCentralUpdateFalls() {
        ArrayList<Fall> falls = new ArrayList<>();
        //Get Stuff

        return falls;
    }

    public ArrayList<Fall> getCentralDeletedFalls() {
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
