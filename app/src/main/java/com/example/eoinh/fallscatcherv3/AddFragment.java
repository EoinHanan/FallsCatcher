package com.example.eoinh.fallscatcherv3;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {
    private AlertDialog.Builder builder;
    private boolean selected[];
    private boolean buttonSelected[];
    private DatabaseHandler db;
    private View popup;

    private Button selectButton;
    private Button currentDateButton;
    private Button selectDateButton;
    private Button unknownFallTimeButton;
    private Button estimateFallTimeButton;
    private Button exactFallTimeButton;
    private Button unknownLengthButton;
    private Button estimateLengthButton;
    private Button exactLengthButton;
    private Button insideHomeButton;
    private Button outsideHomeButton;
    private Button awayFromHomeButton;
    private Button noHelpButton;
    private Button yesHelpButton;
    private Button noInjuryButton;
    private Button yesInjuryButton;
    private Button noMedicalButton;
    private Button yesMedicalButton;
    private Button noRelapseButton;
    private Button yesRelapseButton;

    private int patientID;
    private String date;
    private String timeStatus;
    private String location;
    private String cause;
    private String time;
    private String injury;
    private String help;
    private int lengthOfLie;
    private String lengthStatus;
    private String medical;
    private String comment;
    private String relapse;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add, container, false);
        db = new DatabaseHandler(getActivity(), null,null);

        patientID = db.getPatientID();

        builder = new AlertDialog.Builder(view.getContext());
        final AlertDialog dialog = builder.create();
        dialog.show();

        selected = new boolean[9];
        for (int i =0; i < selected.length;i++)
            selected[i]=false;
        buttonSelected = new boolean[19];
        for (int i =0; i < buttonSelected.length;i++)
            buttonSelected[i]=false;

        comment = "None";

        Button enterButton = (Button) view.findViewById(R.id.enterButton);
        Button clearButton = (Button) view.findViewById(R.id.clearButton);
        currentDateButton = (Button)view.findViewById(R.id.currentDateButton);
        selectDateButton = (Button)view.findViewById(R.id.selectDateButton);
        unknownFallTimeButton = (Button)view.findViewById(R.id.unknownFallTimeButton);
        estimateFallTimeButton = (Button)view.findViewById(R.id.estimateFallTimeButton);
        exactFallTimeButton = (Button)view.findViewById(R.id.exactFallTimeButton);
        unknownLengthButton = (Button)view.findViewById(R.id.unknownLengthButton);
        estimateLengthButton = (Button)view.findViewById(R.id.estimateLengthButton);
        exactLengthButton = (Button)view.findViewById(R.id.exactLengthButton);
        insideHomeButton = (Button)view.findViewById(R.id.insideHomeButton);
        outsideHomeButton = (Button)view.findViewById(R.id.outsideHomeButton);
        awayFromHomeButton = (Button)view.findViewById(R.id.awayButton);
        noHelpButton = (Button)view.findViewById(R.id.noHelpButton);
        yesHelpButton = (Button)view.findViewById(R.id.yesHelpButton);
        noInjuryButton = (Button)view.findViewById(R.id.noInjuryButton);
        yesInjuryButton = (Button)view.findViewById(R.id.yesInjuryButton);
        noMedicalButton = (Button)view.findViewById(R.id.noMedicalButton);
        yesMedicalButton = (Button)view.findViewById(R.id.yesMedicalButton);
        noRelapseButton = (Button)view.findViewById(R.id.noRelapseButton);
        yesRelapseButton = (Button)view.findViewById(R.id.yesRelapseButton);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean allValid = true;

                for (int i = 0; i < selected.length && allValid;i++)
                    allValid = selected[i];

                if (allValid){
                    Fall fall = new Fall(patientID, date, timeStatus, location,cause,time,injury,lengthOfLie, lengthStatus, medical,help,relapse,comment);
                    db.addFall(fall);
                    clearFall();
                    Toast.makeText(view.getContext(),"Fall Logged", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(view.getContext(),"Please enter answer all questions", Toast.LENGTH_SHORT).show();
                }
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFall();
            }
        });

        currentDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonSelected[0]){
                    selected[0] = true;

                    buttonSelected[0] = true;
                    currentDateButton.setBackground(getResources().getDrawable(R.drawable.basic_button_pressed));

                    buttonSelected[1] = false;
                    selectDateButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                    date = getCurrentDate();
                }
                else{
                    selected[0] = false;
                    buttonSelected[0] = false;
                    currentDateButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
            }
        });
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonSelected[1]) {
                    popup = getLayoutInflater().inflate(R.layout.dialog_date,null);
                    builder.setView(popup);
                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    final DatePicker datepicker = (DatePicker)popup.findViewById(R.id.datePicker);

                    selectButton = (Button)popup.findViewById(R.id.selectButton);

                    selectButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            date =   datepicker.getYear() + "/" + (datepicker.getMonth() + 1) + "/" + datepicker.getDayOfMonth();

                            Toast.makeText(view.getContext(),"Date set at " + date, Toast.LENGTH_SHORT).show();

                            //Get input
                            selected[0] = true;

                            buttonSelected[0] = false;
                            currentDateButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                            buttonSelected[1] = true;
                            selectDateButton.setBackground(getResources().getDrawable(R.drawable.basic_button_pressed));

                            dialog.dismiss();
                        }
                    });

                }
                else{
                    buttonSelected[1] = false;
                    selected[0] = false;
                    selectDateButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
            }
        });
        unknownFallTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonSelected[2]) {
                    timeStatus = "unknown";
                    time = "00:00:00";

                    selected[1] = true;

                    buttonSelected[2] = true;
                    unknownFallTimeButton.setBackground(getResources().getDrawable(R.drawable.basic_button_pressed));

                    buttonSelected[3] = false;
                    estimateFallTimeButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                    buttonSelected[4] = false;
                    exactFallTimeButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
                else{
                    selected[1] = false;
                    buttonSelected[2] = false;
                    unknownFallTimeButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
            }
        });
        estimateFallTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonSelected[3]) {
                    popup = getLayoutInflater().inflate(R.layout.dialog_time,null);
                    builder.setView(popup);
                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    selectButton = (Button)popup.findViewById(R.id.selectButton);

                    selectButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TextView hoursText = (TextView) popup.findViewById(R.id.hoursText);
                            TextView minutesText = (TextView) popup.findViewById(R.id.minutesText);

                            String pattern = "[0-9]+";

                            if (hoursText.getText().toString().matches(pattern) && minutesText.getText().toString().matches(pattern)) {
                                int hours = Integer.parseInt(hoursText.getText().toString());
                                int minutes = Integer.parseInt(minutesText.getText().toString());
                                if (hours > 0 && hours < 23 && minutes > 0 && minutes < 59) {
                                    time = "" + hoursText.getText().toString() + ":" + minutesText.getText().toString() + ":00";
                                    timeStatus = "estimate";
                                    selected[1] = true;

                                    buttonSelected[2] = false;
                                    unknownFallTimeButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                                    buttonSelected[3] = true;
                                    estimateFallTimeButton.setBackground(getResources().getDrawable(R.drawable.basic_button_pressed));

                                    buttonSelected[4] = false;
                                    exactFallTimeButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                                    dialog.dismiss();
                                } else
                                    Toast.makeText(view.getContext(), "Invalid times entered", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(view.getContext(), "Please enter numbers into the box", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //Get input

                }
                else{
                    selected[1] = false;
                    buttonSelected[3] = false;
                    estimateFallTimeButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
            }
        });
        exactFallTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonSelected[4]) {
                    popup = getLayoutInflater().inflate(R.layout.dialog_time,null);
                    builder.setView(popup);
                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    selectButton = (Button)popup.findViewById(R.id.selectButton);
                    selectButton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            TextView hoursText = (TextView)popup.findViewById(R.id.hoursText);
                            TextView minutesText = (TextView)popup.findViewById(R.id.minutesText);
                            String pattern = "[0-9]+";

                            if (hoursText.getText().toString().matches(pattern) && minutesText.getText().toString().matches(pattern)) {
                                int hours = Integer.parseInt(hoursText.getText().toString());
                                int minutes = Integer.parseInt(minutesText.getText().toString());
                                if (hours > 0 && hours < 23 && minutes > 0 && minutes < 59) {
                                    time = "" + hoursText.getText().toString() + ":" + minutesText.getText().toString() + ":00";
                                    timeStatus = "exact";
                                    selected[1] = true;

                                    buttonSelected[2] = false;
                                    unknownFallTimeButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                                    buttonSelected[3] = false;
                                    estimateFallTimeButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                                    buttonSelected[4] = true;
                                    exactFallTimeButton.setBackground(getResources().getDrawable(R.drawable.basic_button_pressed));

                                    dialog.dismiss();
                                }
                                else
                                    Toast.makeText(view.getContext(),"Invalid times entered", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(view.getContext(),"Please enter numbers into the box", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    selected[1] = false;
                    buttonSelected[4] = false;
                    exactFallTimeButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
            }
        });
        unknownLengthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonSelected[5]) {

                    lengthStatus = "unknown";
                    lengthOfLie = -1;
                    selected[2] = true;

                    buttonSelected[5] = true;
                    unknownLengthButton.setBackground(getResources().getDrawable(R.drawable.basic_button_pressed));

                    buttonSelected[6] = false;
                    estimateLengthButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                    buttonSelected[7] = false;
                    exactLengthButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                    dialog.dismiss();
                }
                else{
                    selected[2] = false;
                    buttonSelected[5] = false;
                    unknownLengthButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
            }
        });
        estimateLengthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonSelected[6]) {
                    popup = getLayoutInflater().inflate(R.layout.dialog_length,null);
                    builder.setView(popup);
                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    selectButton = (Button)popup.findViewById(R.id.selectButton);
                    selectButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TextView hoursText = (TextView) popup.findViewById(R.id.hoursText);
                            TextView minutesText = (TextView) popup.findViewById(R.id.minutesText);
                            String pattern = "[0-9]+";

                            if (hoursText.getText().toString().matches(pattern) && minutesText.getText().toString().matches(pattern)) {
                                int hours = Integer.parseInt(hoursText.getText().toString());
                                int minutes = Integer.parseInt(minutesText.getText().toString());
                                if (hours > 0 && minutes > 0) {
                                    lengthOfLie = (hours * 60) + minutes;

                                    lengthStatus = "estimate";
                                    selected[2] = true;

                                    buttonSelected[5] = false;
                                    unknownLengthButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                                    buttonSelected[6] = true;
                                    estimateLengthButton.setBackground(getResources().getDrawable(R.drawable.basic_button_pressed));

                                    buttonSelected[7] = false;
                                    exactLengthButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                                    dialog.dismiss();
                                }
                                else
                                    Toast.makeText(view.getContext(),"Invalid values entered", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(view.getContext(),"Invalid values entered", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    selected[2] = false;
                    buttonSelected[6] = false;
                    estimateLengthButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
            }
        });
        exactLengthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonSelected[7]) {
                    popup = getLayoutInflater().inflate(R.layout.dialog_length,null);
                    builder.setView(popup);
                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    selectButton = (Button)popup.findViewById(R.id.selectButton);
                    selectButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TextView hoursText = (TextView) popup.findViewById(R.id.hoursText);
                            TextView minutesText = (TextView) popup.findViewById(R.id.minutesText);
                            String pattern = "[0-9]+";

                            if (hoursText.getText().toString().matches(pattern) && minutesText.getText().toString().matches(pattern)) {
                                int hours = Integer.parseInt(hoursText.getText().toString());
                                int minutes = Integer.parseInt(minutesText.getText().toString());
                                if (hours >= 0 && minutes > 0) {
                                    lengthOfLie = (hours * 60) + minutes;
                                    lengthStatus = "exact";
                                    selected[2] = true;

                                    buttonSelected[5] = false;
                                    unknownLengthButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                                    buttonSelected[6] = false;
                                    estimateLengthButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                                    buttonSelected[7] = true;
                                    exactLengthButton.setBackground(getResources().getDrawable(R.drawable.basic_button_pressed));

                                    dialog.dismiss();
                                }
                                else
                                    Toast.makeText(view.getContext(),"Invalid values entered", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(view.getContext(),"Invalid values entered", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    buttonSelected[7] = false;
                    selected[2] = false;
                    exactLengthButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
            }
        });

        insideHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonSelected[8]) {
                    location = "Inside of home";
                    selected[3] = true;

                    buttonSelected[8] = true;
                    insideHomeButton.setBackground(getResources().getDrawable(R.drawable.basic_button_pressed));

                    buttonSelected[9] = false;
                    outsideHomeButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                    buttonSelected[10] = false;
                    awayFromHomeButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
                else{
                    buttonSelected[8] = false;
                    selected[3] = false;
                    insideHomeButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
            }
        });
        outsideHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonSelected[9]) {
                    location = "Outside of home";
                    selected[3] = true;

                    buttonSelected[8] = false;
                    insideHomeButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                    buttonSelected[9] = true;
                    outsideHomeButton.setBackground(getResources().getDrawable(R.drawable.basic_button_pressed));

                    buttonSelected[10] = false;
                    awayFromHomeButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
                else{
                    buttonSelected[9] = false;
                    selected[3] = false;
                    outsideHomeButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
            }
        });
        awayFromHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonSelected[10]) {
                    location = "Away from home";

                    selected[3] = true;

                    buttonSelected[8] = false;
                    insideHomeButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                    buttonSelected[9] = false;
                    outsideHomeButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                    buttonSelected[10] = true;
                    awayFromHomeButton.setBackground(getResources().getDrawable(R.drawable.basic_button_pressed));
                }
                else{
                    buttonSelected[10] = false;
                    selected[3] = false;
                    awayFromHomeButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
            }
        });

        noHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonSelected[11]) {
                    selected[4] = true;
                    help ="None";

                    buttonSelected[11] = true;
                    noHelpButton.setBackground(getResources().getDrawable(R.drawable.basic_button_pressed));

                    buttonSelected[12] = false;
                    yesHelpButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
                else{
                    buttonSelected[11] = false;
                    selected[4] = false;
                    noHelpButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
            }
        });
        yesHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonSelected[12]) {
                    popup = getLayoutInflater().inflate(R.layout.dialog_help,null);
                    builder.setView(popup);
                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    help = null;

                    RadioGroup radioGroup = (RadioGroup) popup.findViewById(R.id.helpGroup);
                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                    {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            switch (checkedId) {
                                case R.id.friendOrFamilyButton:
                                    help = "Friend or Family";
                                    break;
                                case R.id.emergencyServicesButton:
                                    help = "Emergency Services";
                                    break;

                            }
                        }
                    });

                    selectButton = (Button)popup.findViewById(R.id.selectButton);
                    selectButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (help!=null) {
                                selected[4] = true;

                                buttonSelected[11] = false;
                                noHelpButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                                buttonSelected[12] = true;
                                yesHelpButton.setBackground(getResources().getDrawable(R.drawable.basic_button_pressed));

                                dialog.dismiss();
                            }
                            else
                                Toast.makeText(view.getContext(),"Please choose an option", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    buttonSelected[12] = false;
                    selected[4] = false;
                    yesHelpButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
            }
        });
        noInjuryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonSelected[13]) {
                    selected[5] = true;
                    injury = "None";

                    buttonSelected[13] = true;
                    noInjuryButton.setBackground(getResources().getDrawable(R.drawable.basic_button_pressed));

                    buttonSelected[14] = false;
                    yesInjuryButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
                else{
                    buttonSelected[13] = false;
                    selected[5] = false;
                    noInjuryButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
            }
        });
        yesInjuryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonSelected[14]) {
                    popup = getLayoutInflater().inflate(R.layout.dialog_injury,null);
                    builder.setView(popup);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    //Get input

                    selectButton = (Button)popup.findViewById(R.id.selectButton);
                    selectButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            injury = getInjury();
                            selected[5] = true;

                            buttonSelected[13] = false;
                            noInjuryButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                            buttonSelected[14] = true;
                            yesInjuryButton.setBackground(getResources().getDrawable(R.drawable.basic_button_pressed));

                            dialog.dismiss();
                        }

                        public String getInjury(){
                            String injuryString = "";
                            RadioButton headBruisesButton = (RadioButton)popup.findViewById(R.id.headBruisesButton);
                            RadioButton headCutScrapeButton = (RadioButton)popup.findViewById(R.id.headCutScrapeButton);
                            RadioButton headSprainButton = (RadioButton)popup.findViewById(R.id.headSprainButton);
                            RadioButton headDislocationButton = (RadioButton)popup.findViewById(R.id.headDislocationButton);
                            RadioButton headBrokenBoneButton = (RadioButton)popup.findViewById(R.id.headBrokenBoneButton);
                            RadioButton bodyBruisesButton = (RadioButton)popup.findViewById(R.id.bodyBruisesButton);
                            RadioButton bodyCutScrapeButton = (RadioButton)popup.findViewById(R.id.bodyCutScrapeButton);
                            RadioButton bodySprainButton = (RadioButton)popup.findViewById(R.id.bodySprainButton);
                            RadioButton bodyDislocatedButton = (RadioButton)popup.findViewById(R.id.bodyDislocationButton);
                            RadioButton bodyBrokenBoneButton = (RadioButton)popup.findViewById(R.id.bodyBrokenBoneButton);
                            RadioButton armsBruisesButton = (RadioButton)popup.findViewById(R.id.armsBruisesButton);
                            RadioButton armsCutScrapeButton = (RadioButton)popup.findViewById(R.id.armsCutScrapesButton);
                            RadioButton armsSprainButton = (RadioButton)popup.findViewById(R.id.armsSprainButton);
                            RadioButton armsDislocatedButton = (RadioButton)popup.findViewById(R.id.armsDislocationButton);
                            RadioButton armsBrokenBoneButton = (RadioButton)popup.findViewById(R.id.armsBrokenBoneButton);
                            RadioButton legsBruisesButton = (RadioButton)popup.findViewById(R.id.legsBruisesButton);
                            RadioButton legsCutScrapeButton = (RadioButton)popup.findViewById(R.id.legsCutScrapesButton);
                            RadioButton legsSprainButton = (RadioButton)popup.findViewById(R.id.legsSprainButton);
                            RadioButton legsDislocationButton = (RadioButton)popup.findViewById(R.id.legsDislocationButton);
                            RadioButton legsBrokenBoneButton = (RadioButton)popup.findViewById(R.id.legsBrokenBoneButton);

                            if (headBruisesButton.isChecked())
                                injuryString+="headBruises-";
                            else
                                injury+="-";

                            if (headCutScrapeButton.isChecked())
                                injuryString+="headCutScrape-";
                            else
                                injury+="-";

                            if (headSprainButton.isChecked())
                                injuryString+="headSprain-";
                            else
                                injury+="-";

                            if (headDislocationButton.isChecked())
                                injuryString+="headDislocation-";
                            else
                                injury+="-";

                            if (headBrokenBoneButton.isChecked())
                                injuryString+="headBrokenBone-";
                            else
                                injury+="-";

                            if (bodyBruisesButton.isChecked())
                                injuryString+="bodyBruises-";
                            else
                                injury+="-";

                            if (bodyCutScrapeButton.isChecked())
                                injuryString+="bodyCutScrape-";
                            else
                                injury+="-";

                            if (bodySprainButton.isChecked())
                                injuryString+="bodySprain-";
                            else
                                injury+="-";

                            if (bodyDislocatedButton.isChecked())
                                injuryString+="bodyDislocation-";
                            else
                                injury+="-";

                            if (bodyBrokenBoneButton.isChecked())
                                injuryString+="bodyBrokenBone-";
                            else
                                injury+="-";

                            if (armsBruisesButton.isChecked())
                                injuryString+="armsBruises-";
                            else
                                injury+="-";

                            if (armsCutScrapeButton.isChecked())
                                injuryString+="armsCutScrape-";
                            else
                                injury+="-";

                            if (armsSprainButton.isChecked())
                                injuryString+="armsSprain-";
                            else
                                injury+="-";

                            if (armsDislocatedButton.isChecked())
                                injuryString+="armsDislocation-";
                            else
                                injury+="-";

                            if (armsBrokenBoneButton.isChecked())
                                injuryString+="armsBrokenBone-";
                            else
                                injury+="-";

                            if (legsBruisesButton.isChecked())
                                injuryString+="legsBruises-";
                            else
                                injury+="-";

                            if (legsCutScrapeButton.isChecked())
                                injuryString+="legsCutScrape-";
                            else
                                injury+="-";

                            if (legsSprainButton.isChecked())
                                injuryString+="legsSprain-";
                            else
                                injury+="-";

                            if (legsDislocationButton.isChecked())
                                injuryString+="legsDislocation-";
                            else
                                injury+="-";

                            if (legsBrokenBoneButton.isChecked())
                                injuryString+="legsBrokenBone";
                            else
                                injury+="";

                            return injuryString;
                        }
                    });
                }
                else{
                    buttonSelected[14] = false;
                    selected[5] = false;
                    yesInjuryButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
            }
        });
        noMedicalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonSelected[15]) {
                    medical = "None";
                    selected[6] = true;

                    buttonSelected[15] = true;
                    noMedicalButton.setBackground(getResources().getDrawable(R.drawable.basic_button_pressed));

                    buttonSelected[16] = false;
                    yesMedicalButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
                else{
                    selected[6] = false;
                    buttonSelected[15] = false;
                    noMedicalButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
            }
        });
        yesMedicalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonSelected[16]) {
                    popup = getLayoutInflater().inflate(R.layout.dialog_medical,null);
                    builder.setView(popup);
                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    selectButton = (Button)popup.findViewById(R.id.selectButton);
                    selectButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            medical = getMedical();
                            selected[6] = true;

                            buttonSelected[15] = false;
                            noMedicalButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                            buttonSelected[16] = true;
                            yesMedicalButton.setBackground(getResources().getDrawable(R.drawable.basic_button_pressed));
                            dialog.dismiss();
                        }
                    });
                }
                else{
                    selected[6] = false;
                    buttonSelected[16] = false;
                    yesMedicalButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
            }
            public String getMedical(){
                String medical = "";

                RadioButton nurseButton = (RadioButton)popup.findViewById(R.id.nurseButton);
                RadioButton careProviderButton = (RadioButton)popup.findViewById(R.id.careProviderButton);
                RadioButton doctorButton = (RadioButton)popup.findViewById(R.id.doctorButton);
                RadioButton emergencyButton = (RadioButton)popup.findViewById(R.id.emergencyButton);
                RadioButton hospitalButton = (RadioButton)popup.findViewById(R.id.hospitalButton);
                TextView daysText = (TextView)popup.findViewById(R.id.daysText);
                TextView commentText = (TextView)popup.findViewById(R.id.otherText);

                if (nurseButton.isChecked())
                    medical+="Nurse visit-";
                else
                    medical+="-";

                if (careProviderButton.isChecked())
                    medical+="Nurse visit-";
                else
                    medical+="-";

                if (doctorButton.isChecked())
                    medical+="Nurse visit-";
                else
                    medical+="-";

                if (emergencyButton.isChecked())
                    medical+="Emergency Department visit-";
                else
                    medical+="-";

                if (hospitalButton.isChecked())
                    medical+="Nurse visit-";
                else
                    medical+="-";

                String days = daysText.getText().toString();

                if (!days.isEmpty())
                    medical += days + "-";
                else
                    medical += "0-";

                String comment = commentText.getText().toString();

                if (!comment.isEmpty())
                    medical += comment;
                else
                    medical += "";

                return medical;
            }
        });

        noRelapseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonSelected[17]) {
                    selected[7] = true;
                    relapse = "None";

                    buttonSelected[17] = true;
                    noRelapseButton.setBackground(getResources().getDrawable(R.drawable.basic_button_pressed));

                    buttonSelected[18] = false;
                    yesRelapseButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
                else{
                    selected[7] = false;
                    buttonSelected[17] = false;
                    noRelapseButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
            }
        });
        yesRelapseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!buttonSelected[18]) {
                    popup = getLayoutInflater().inflate(R.layout.dialog_relapse,null);
                    builder.setView(popup);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    selectButton = (Button)popup.findViewById(R.id.selectButton);
                    selectButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selected[7] = true;
                            relapse = getRelapse();

                            buttonSelected[17] = false;
                            noRelapseButton.setBackground(getResources().getDrawable(R.drawable.basic_button));

                            buttonSelected[18] = true;
                            yesRelapseButton.setBackground(getResources().getDrawable(R.drawable.basic_button_pressed));
                            dialog.dismiss();
                        }

                        public String getRelapse(){
                            String relapse = "";

                            TextView whenRelapseText = (TextView)popup.findViewById(R.id.whenRelapseText);
                            TextView lengthRelapseText = (TextView)popup.findViewById(R.id.lengthRelapseText);
                            TextView symptomsRelapseText  = (TextView)popup.findViewById(R.id.symptomsRelapseText);
                            TextView healthCareRelapseButton = (TextView)popup.findViewById(R.id.healthCareRelapseButton);
                            TextView treatmentRelapseButton = (TextView)popup.findViewById(R.id.treatmentRelapseButton);

                            if (!whenRelapseText.getText().toString().isEmpty())
                                relapse += whenRelapseText.getText().toString() + "-";
                            else
                                relapse = "-";
                            if (!lengthRelapseText.getText().toString().isEmpty())
                                relapse += lengthRelapseText.getText().toString() + "-";
                            else
                                relapse = "-";
                            if (!symptomsRelapseText.getText().toString().isEmpty())
                                relapse += symptomsRelapseText.getText().toString() + "-";
                            else
                                relapse = "-";
                            if (!healthCareRelapseButton.getText().toString().isEmpty())
                                relapse += healthCareRelapseButton.getText().toString() + "-";
                            else
                                relapse = "-";
                            if (!treatmentRelapseButton.getText().toString().isEmpty())
                                relapse += treatmentRelapseButton.getText().toString() + "-";
                            else
                                relapse = "";

                            return relapse;
                        }
                    });
                }
                else{
                    selected[5] = false;
                    buttonSelected[18] = false;
                    yesRelapseButton.setBackground(getResources().getDrawable(R.drawable.basic_button));
                }
            }


        });

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selected[8] =true;
                switch (checkedId) {
                    case R.id.radioButton1:
                        cause = "Not sure";
                        break;
                    case R.id.radioButton2:
                        cause = "Poor Balance";
                        break;
                    case R.id.radioButton3:
                        cause = "Weak legs or gave way";
                        break;
                    case R.id.radioButton4:
                        cause = "Walking aid related";
                        break;
                    case R.id.radioButton5:
                        cause = "Slip or trip";
                        break;
                    case R.id.radioButton6:
                        cause = "Surface transfer";
                        break;
                    case R.id.radioButton7:
                        cause = "Not paying attention";
                        break;
                    case R.id.radioButton8:
                        cause = "Miscalculated distance";
                        break;
                    case R.id.radioButton9:
                        cause = "Medical problem";
                        break;
                    case R.id.radioButton10:
                        cause = "Risky activity";
                        break;
                    case R.id.radioButton11:
                        cause = "Weather";
                        break;
                    case R.id.radioButton12:
                        cause = "Poor Vision";
                        break;
                    case R.id.radioButton13:
                        cause = "Felt dizzy";
                        break;
                    case R.id.radioButton14:
                        cause = "Overheated";
                        break;
                    case R.id.radioButton15:
                        cause = "Fatigued";
                        break;
                    case R.id.radioButton16:
                        cause = "Other";
                        break;
                }
            }
        });



        return view;
    }

    public void clearFall(){

        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    public String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date currentDate = new Date();
        String dateString = dateFormat.format(currentDate);
        return dateString;
    }
}
