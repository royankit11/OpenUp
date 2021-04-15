package com.example.openup;

public class RegisteredEventData {

    private String mHost;
    private String mTime;
    private String mName;
    private String mDate;
    private String mLink;

    public RegisteredEventData(String mHost, String mTime, String mName, String mDate, String mLink) {
        this.mHost = mHost;
        this.mTime = mTime;
        this.mName = mName;
        this.mDate = mDate;
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

    public String getmDate() {
        return mDate;
    }

    public String getmLink() {
        return mLink;
    }

}