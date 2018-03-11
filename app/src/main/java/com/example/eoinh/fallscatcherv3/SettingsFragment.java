package com.example.eoinh.fallscatcherv3;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.*;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    private TextView timeBox;
    private ToggleButton reminderButton;
    private View view;
    private Button syncButton;
    private DatabaseHandler db;

    NotificationCompat.Builder notification;
    private static final int uniqueID = 334;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = getLayoutInflater().inflate(R.layout.fragment_settings,null);

        timeBox = (TextView)view.findViewById (R.id.reminderTimeBox);
        reminderButton = (ToggleButton)view.findViewById (R.id.reminderButton);

        db = new DatabaseHandler(getActivity(), null,null);

        notification = new NotificationCompat.Builder(getActivity(), "Channel id");
        notification.setAutoCancel(true);

        reminderButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setNotification(true);
                } else {
                    setNotification(false);
                }
            }
        });

        syncButton = (Button)view.findViewById(R.id.syncButton);

        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syncFalls();
            }
        });

        return view;
    }

    public void setNotification(boolean isOn){

    }
    public void syncFalls() {
        createHero();
    }

    private void createHero() {
        //Calling the create hero API
        HashMap<String, String> params = new HashMap<>();
        params.put("localFallID", "2");
        params.put("patientID", "1");
        params.put("date", "1898-03-03");
        params.put("timeStatus", "Estimate");

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_HERO, params, CODE_POST_REQUEST);
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
                    Toast.makeText(getActivity().getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
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

//    class HeroAdapter extends ArrayAdapter<Hero> {
//
//        //our hero list
//        List<Hero> heroList;
//
//
//        //constructor to get the list
//        public HeroAdapter(List<Hero> heroList) {
//            super(MainActivity.this, R.layout.layout_hero_list, heroList);
//            this.heroList = heroList;
//        }
//
//
//        //method returning list item
//        public View getView(int position, View convertView, ViewGroup parent) {
//            LayoutInflater inflater = getLayoutInflater();
//            View listViewItem = inflater.inflate(R.layout.layout_hero_list, null, true);
//
//            //getting the textview for displaying name
//            TextView textViewName = listViewItem.findViewById(R.id.textViewName);
//
//            //the update and delete textview
//            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
//            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);
//
//            final Hero hero = heroList.get(position);
//
//            textViewName.setText(hero.getName());
//
//            //attaching click listener to update
//            textViewUpdate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //so when it is updating we will
//                    //make the isUpdating as true
//                    isUpdating = true;
//
//                    //we will set the selected hero to the UI elements
//                    editTextHeroId.setText(String.valueOf(hero.getId()));
//                    editTextName.setText(hero.getName());
//                    editTextRealname.setText(hero.getRealname());
//                    ratingBar.setRating(hero.getRating());
//                    spinnerTeam.setSelection(((ArrayAdapter<String>) spinnerTeam.getAdapter()).getPosition(hero.getTeamaffiliation()));
//
//                    //we will also make the button text to Update
//                    buttonAddUpdate.setText("Update");
//                }
//            });
//
//            //when the user selected delete
//            textViewDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    // we will display a confirmation dialog before deleting
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//
//                    builder.setTitle("Delete " + hero.getName())
//                            .setMessage("Are you sure you want to delete it?")
//                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //if the choice is yes we will delete the hero
//                                    //method is commented because it is not yet created
//                                    //deleteHero(hero.getId());
//                                }
//                            })
//                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                }
//                            })
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .show();
//
//                }
//            });
//
//            return listViewItem;
//        }
//    }

    private void readHeroes() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_HEROES, null, CODE_GET_REQUEST);
        request.execute();
    }

//    private void updateHero() {
//
//
//
//        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_HERO, params, CODE_POST_REQUEST);
//        request.execute();
//
//        buttonAddUpdate.setText("Add");
//
//        editTextName.setText("");
//        editTextRealname.setText("");
//        ratingBar.setRating(0);
//        spinnerTeam.setSelection(0);
//
//        isUpdating = false;
//    }
//
//    private void deleteHero(int id) {
//        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_HERO + id, null, CODE_GET_REQUEST);
//        request.execute();
//    }
/*
    public void syncFalls() {

        ArrayList <Fall> falls = db.getFalls();

        //Toast.makeText(view.getContext(),"Now syncing", Toast.LENGTH_SHORT).show();

        for (int i =0; i < falls.size(); i++) {
            HashMap<String, String> params = new HashMap<>();
            Fall fall = falls.get(i);

            params.put("localID", "1");
            params.put("patientID", "1");
            params.put("date", "");
            params.put("timeStatus", "");
            params.put("time", "");
            params.put("location", "");
            params.put("cause", "");
            params.put("injury", "");
            params.put("lengthOfLie", "-1");
            params.put("lengthStatus", "");
            params.put("medical", "");
            params.put("help", "");
            params.put("relapse", "");
            params.put("comment", "");

//            params.put("localID", String.valueOf(fall.getFallID()));
//            params.put("patientID", String.valueOf(fall.getPatientID()));
//            params.put("date", fall.getDate());
//            params.put("timeStatus", fall.getTimeStatus());
//            params.put("time", fall.getTime());
//            params.put("location", fall.getLocation());
//            params.put("cause", fall.getCause());
//            params.put("injury", fall.getInjury());
//            params.put("lengthOfLie", String.valueOf(fall.getLengthOfLie()));
//            params.put("lengthStatus", fall.getLengthStatus());
//            params.put("medical", fall.getMedical());
//            params.put("help", fall.getHelp());
//            params.put("relapse", fall.getRelapse());
//            params.put("comment", fall.getComment());

            Log.d("localID", String.valueOf(fall.getFallID()));
            Log.d("patientID", String.valueOf(fall.getPatientID()));
            Log.d("date", fall.getDate());
            Log.d("timeStatus", fall.getTimeStatus());
            Log.d("time", fall.getTime());
            Log.d("location", fall.getLocation());
            Log.d("cause", fall.getCause());
            Log.d("injury", fall.getInjury());
            Log.d("lengthOfLie", String.valueOf(fall.getLengthOfLie()));
            Log.d("lengthStatus", String.valueOf(fall.getLengthStatus()));
            Log.d("medical", fall.getMedical());
            Log.d("help", fall.getHelp());
            Log.d("relapse", fall.getRelapse());
            Log.d("comment", fall.getComment());

            PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_FALL, params, CODE_POST_REQUEST);
            request.execute();
        }
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;

        HashMap<String, String> params;

        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            progressBar.setVisibility(GONE);
            try {
                Log.i("tagconvertstr", "["+s+"]");
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getActivity().getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
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
*/

}
