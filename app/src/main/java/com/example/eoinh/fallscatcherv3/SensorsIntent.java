package com.example.eoinh.fallscatcherv3;

import android.app.IntentService;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class SensorsIntent extends IntentService implements SensorEventListener {
    private static final String TAG= "com.example.eoinh.motionsensors";

    private Sensor sensor;
    private SensorManager sensorManager;
    private FallHandler fallHandler;
    private ArrayList<SensorEvent> sensorEvents;
    private ArrayList<String> falls;

    public SensorsIntent(){
        super("SensorsIntent");
        fallHandler = new FallHandler();
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        sensorEvents.add(sensorEvent);
        if (sensorEvents.size() > 20){
            SensorEvent[] events = new SensorEvent[sensorEvents.size()];
            for (int i =0; i < events.length;i++){
                events[i]= sensorEvents.get(i);
            }

            if (fallHandler.check(events))
                falls.add("Fall detected");

            sensorEvents = new ArrayList<SensorEvent>();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
