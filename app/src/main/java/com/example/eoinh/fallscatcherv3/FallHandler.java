package com.example.eoinh.fallscatcherv3;

import android.hardware.SensorEvent;

/**
 * Created by EoinH on 24/03/2018.
 */

public class FallHandler {
    private boolean simpleThresholdOn, fallIndexOn, twoPhaseDetectionOn, iFallOn;
    private float simpleThreshold, fallIndexThreshold, thresholdFF, thresholdI, angleThresholdFF, angleThresholdI,iFallUpperThreshold, iFallLowerThreshold;

    public FallHandler(){
        this.simpleThresholdOn = true;
        this.fallIndexOn = true;
        this.twoPhaseDetectionOn = true;
        this.iFallOn = false;

        this.simpleThreshold = 10;
        this.fallIndexThreshold = 10;
        this.thresholdFF = 10;
        this.thresholdI = 10;
        this.angleThresholdFF = 10;
        this.angleThresholdI = 10;
        this.iFallUpperThreshold = 10;
        this.iFallLowerThreshold = 10;
    }

    public FallHandler(boolean simpleThresholdOn, boolean fallIndexOn, boolean twoPhaseDetectionOn, boolean iFallOn , float simpleThreshold, float fallIndexThreshold, float thresholdFF, float thresholdI, float angleThresholdFF, float angleThresholdI, float iFallUpperThreshold,   float iFallLowerThreshold){

        this.simpleThresholdOn = simpleThresholdOn;
        this.fallIndexOn = fallIndexOn;
        this.twoPhaseDetectionOn = twoPhaseDetectionOn;
        this.iFallOn = iFallOn;

        this.simpleThreshold = simpleThreshold;
        this.fallIndexThreshold = fallIndexThreshold;
        this.thresholdFF = thresholdFF;
        this.thresholdI = thresholdI;
        this.angleThresholdFF = angleThresholdFF;
        this.angleThresholdI = angleThresholdI;
        this.iFallUpperThreshold = iFallUpperThreshold;
        this.iFallLowerThreshold =iFallLowerThreshold;
    }

    public void changeOn(){
        this.simpleThresholdOn = simpleThresholdOn;
        this.fallIndexOn = fallIndexOn;
        this.twoPhaseDetectionOn = twoPhaseDetectionOn;
        this.iFallOn = iFallOn;
    }

    public boolean check(SensorEvent sensorEvents[]){

        if (fallIndexOn)
            for (int i =0; i < sensorEvents.length; i++)
                if (fallIndex(sensorEvents,fallIndexThreshold))
                    return true;

        if (twoPhaseDetectionOn)
            for (int i =0; i < sensorEvents.length; i++)
                if (twoPhaseDetection(sensorEvents,thresholdFF, thresholdI, angleThresholdFF, angleThresholdI))
                    return true;

        if (iFallOn)
            for (int i =0; i < sensorEvents.length; i++)
                if (iFall(sensorEvents,iFallUpperThreshold,iFallLowerThreshold))
                    return true;

        return false;
    }

    public boolean checkSimple(SensorEvent sensorEvent){
        if (simpleThresholdOn)
            if (simpleThreshold(sensorEvent, simpleThreshold))
                return true;
        return false;
    }

    public boolean simpleThreshold(SensorEvent sensorEvent, float threshold){
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        double vector = Math.pow((Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2)), 0.5);
        return (vector > threshold);
    }

    public boolean fallIndex(SensorEvent sensorEvents[], double threshold){
        double sum = 0;
        for (int i =1; i < sensorEvents.length; i++){
            sum+= Math.pow(sensorEvents[i].values[0] - sensorEvents[i - 1].values[0],2);
            sum+= Math.pow(sensorEvents[i].values[1] - sensorEvents[i - 1].values[1],2);
            sum+= Math.pow(sensorEvents[i].values[2] - sensorEvents[i - 1].values[2],2);
        }
        sum = Math.pow(sum, 0.5);
        return (sum > threshold);
    }

    public boolean twoPhaseDetection(SensorEvent sensorEvents[], double thresholdFF, double thresholdI, double angleThresholdFF, double angleThresholdI){

        double vector = Math.pow((Math.pow(sensorEvents[0].values[0],2) + Math.pow(sensorEvents[0].values[1],2) + Math.pow(sensorEvents[0].values[0],2)), 0.5);
        double highest = vector;
        double lowest = vector;
        boolean thresholdHit = false;

        double angle = (sensorEvents[0].values[0] * Math.sin(sensorEvents[0].values[1])) + (sensorEvents[0].values[1] * Math.cos(sensorEvents[0].values[0] )) + (sensorEvents[0].values[2] + Math.cos(sensorEvents[0].values[1]) * Math.cos(sensorEvents[0].values[2]));
        double highestAngle = angle;
        double lowestAngle = angle;
        boolean angleThresholdHit = false;

        int i = 1;
        //Free Fall phase
        for (;i < sensorEvents.length && (!thresholdHit || !angleThresholdHit); i++) {
            vector = Math.pow((Math.pow(sensorEvents[i].values[0], 2) + Math.pow(sensorEvents[i].values[1], 2) + Math.pow(sensorEvents[i].values[2], 2)), 0.5);
            angle = (sensorEvents[i].values[0] * Math.sin(sensorEvents[i].values[1])) + (sensorEvents[i].values[1] * Math.cos(sensorEvents[i].values[0] )) + (sensorEvents[i].values[2] + Math.cos(sensorEvents[i].values[1]) * Math.cos(sensorEvents[i].values[2]));
            if (vector < lowest)
                lowest = vector;
            if (vector > highest)
                highest = vector;
            if (angle < lowestAngle)
                lowestAngle = angle;
            if (angle > highestAngle)
                highestAngle = angle;
            if (highest - lowest > thresholdFF)
                thresholdHit = true;
            if (highestAngle - lowestAngle > angleThresholdFF)
                angleThresholdHit = true;
        }

        thresholdHit = false;
        angleThresholdHit = false;
        //Impact phase
        for (;i < sensorEvents.length && (!thresholdHit || !angleThresholdHit); i++) {
            vector = Math.pow((Math.pow(sensorEvents[i].values[0], 2) + Math.pow(sensorEvents[i].values[1], 2) + Math.pow(sensorEvents[i].values[2], 2)), 0.5);
            angle = (sensorEvents[i].values[0] * Math.sin(sensorEvents[i].values[1])) + (sensorEvents[i].values[1] * Math.cos(sensorEvents[i].values[0] )) + (sensorEvents[i].values[2] + Math.cos(sensorEvents[i].values[1]) * Math.cos(sensorEvents[i].values[2]));
            if (vector < lowest)
                lowest = vector;
            if (vector > highest)
                highest = vector;
            if (angle < lowestAngle)
                lowestAngle = angle;
            if (angle > highestAngle)
                highestAngle = angle;
            if (highest - lowest > thresholdI)
                thresholdHit = true;
            if (highestAngle - lowestAngle > angleThresholdI)
                angleThresholdHit = true;
        }

        return thresholdHit && angleThresholdHit;
    }



    public boolean iFall(SensorEvent sensorEvents[], double upperThreshold, double lowerThreshold){
        boolean upperHit = false;
        boolean lowerHit = false;
        double vector;

        for (int i =0; i < sensorEvents.length && (!lowerHit || !upperHit);i++) {
            vector = Math.pow((Math.pow(sensorEvents[i].values[0], 2) + Math.pow(sensorEvents[i].values[1], 2) + Math.pow(sensorEvents[i].values[2], 2)), 0.5);
            if (vector> upperThreshold)
                upperHit = true;
            if (vector<lowerThreshold)
                lowerHit = true;
        }
//        if (upperHit && lowerHit)
//            return checkOrientation();
//        else
//            return false;
        return false;
    }

}
