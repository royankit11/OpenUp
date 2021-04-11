package com.example.openup;

public class CalendarEventData {

    private String mHost;
    private String mTime;
    private String mName;
    private String mLink;

    public CalendarEventData(String mHost, String mTime, String mName, String mLink) {
        this.mHost = mHost;
        this.mTime = mTime;
        this.mName = mName;
        this.mLink = mLink;
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

    public String getmLink() {
        return mLink;
    }

}