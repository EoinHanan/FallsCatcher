package com.example.eoinh.fallscatcherv3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private TextView hourBox, minuteBox;
    private ToggleButton reminderButton;
    private View view;
    private Button syncButton, logOutButton;
    private DatabaseHandler databaseHandler;
    private SyncManager syncManager;
    private NotificationManager notificationManager;
    private LoginManager loginManager;


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = getLayoutInflater().inflate(R.layout.fragment_settings,null);

        hourBox = (TextView)view.findViewById (R.id.reminderHourBox);
        minuteBox = (TextView)view.findViewById (R.id.reminderMinuteBox);
        reminderButton = (ToggleButton)view.findViewById (R.id.reminderButton);

        notificationManager = new NotificationManager(this.getContext());

        databaseHandler = new DatabaseHandler(getActivity(), null,null);
        syncManager = new SyncManager(databaseHandler);

        User user = databaseHandler.getUser();

        if (!user.getNotification().equals("")) {
            String[] parts = user.getNotification().split(":");
            long time = (Integer.parseInt(parts[0]) * 360000) + (Integer.parseInt(parts[1]) * 60000);
            notificationManager.turnOnNotification(time);
        }
        else
            notificationManager.turnOffNotification();

        reminderButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (!minuteBox.getText().toString().equals("")){
                    int minute =  Integer.parseInt(minuteBox.getText().toString());
                    int hour = 0;
                    if (!hourBox.getText().toString().equals(""))
                        hour = Integer.parseInt(hourBox.getText().toString());

                    long time = (hour * 360000) + (minute * 60000);
                    notificationManager.turnOnNotification(time);
                    User user = databaseHandler.getUser();
                    user.setNotification(hourBox.getText().toString() + ":" + minuteBox.getText().toString());
                    databaseHandler.setUser(user);
                }
                else
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter a time", Toast.LENGTH_SHORT).show();
            } else {
                notificationManager.turnOffNotification();
                User user = databaseHandler.getUser();
                user.setNotification("");
                databaseHandler.setUser(user);
                Toast.makeText(getActivity().getApplicationContext(), "Please enter a time", Toast.LENGTH_SHORT).show();
            }
            }
        });

        syncButton = (Button)view.findViewById(R.id.syncButton);
        logOutButton = (Button)view.findViewById(R.id.syncButton);

        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startSync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity().getApplicationContext(), "Sync Complete", Toast.LENGTH_SHORT).show();
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               loginManager.logout();
               databaseHandler.logout();

                Intent intent = new Intent(getContext(), LoginPage.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    public void startSync() throws InterruptedException {

        syncManager.sync();
    }

}
