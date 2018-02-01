package com.example.eoinh.fallscatcherv3;

/**
 * Created by EoinH on 01/02/2018.
 */

public abstract class Observer {
    protected Subject subject;
    public abstract void update();
}