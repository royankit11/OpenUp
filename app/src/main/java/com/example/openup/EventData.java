package com.example.openup;

public class EventData {

    private String mName;
    private String mTime;
    private int mImage;

    public EventData(String mName, String mTime, int mImage) {
        this.mName = mName;
        this.mTime = mTime;
        this.mImage = mImage;
    }

    public String getmHost() {
        return mName;
    }

    public String getmTime() {
        return mTime;
    }

    public int getmImage() { return mImage; }

}