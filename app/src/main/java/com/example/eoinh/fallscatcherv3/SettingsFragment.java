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

    private TextView timeBox;
    private ToggleButton reminderButton;
    private View view;
    private Button syncButton;
    private DatabaseHandler databaseHandler;
    private SyncManager syncManager;

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

        databaseHandler = new DatabaseHandler(getActivity(), null,null);
        syncManager = new SyncManager(databaseHandler);

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
                startSync();
                Toast.makeText(getActivity().getApplicationContext(), "Sync Complete", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void setNotification(boolean isOn){

    }

    public void startSync() {

        syncManager.sync();
    }

}
