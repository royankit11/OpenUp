package com.example.openup;

public class CalendarEventData {

    private String mHost;
    private String mTime;
    private String mName;
    private int mID;
    private boolean mRegistered;

    public CalendarEventData(String mHost, String mTime, String mName, int mID, boolean mRegistered) {
        this.mHost = mHost;
        this.mTime = mTime;
        this.mName = mName;
        this.mID = mID;
        this.mRegistered = mRegistered;
    }

    public String getmHost() {
        return mHost;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmName() {
        return mName;
    }

    public int getmID() {
        return mID;
    }

    public boolean getmRegistered() {
        return mRegistered;
    }

}