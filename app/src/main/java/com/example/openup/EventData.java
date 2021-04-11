package com.example.openup;

public class EventData {

    private String mHost;
    private String mTime;
    private String mImage;

    public EventData(String mHost, String mTime) {
        this.mHost = mHost;
        this.mTime = mTime;
    }

    public String getmHost() {
        return mHost;
    }

    public String getmTime() {
        return mTime;
    }

}