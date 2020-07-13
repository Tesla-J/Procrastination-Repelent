package com.marcos.procrastinationrepelent;

import java.util.Date;
import java.util.UUID;
import android.text.format.DateFormat;

public class Task {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mDone;
    private final CharSequence DATE_FORMAT_STRING = "EEE, dd-MMMM-yyyy";
    private final CharSequence TIME_FORMAT_STRING = "H:mm";

    public Task(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }
    @Override
    public String toString(){
        return mTitle;
    }
    public UUID getId(){
        return mId;
    }
    public void setTitle(String title){
        mTitle = title;
    }
    public String getTitle(){
        return mTitle;
    }
    public void setDate(Date date){
        mDate = date;
    }
    public Date getDate(){
        return mDate;
    }
    public String getFormatedDate(){
        return DateFormat.format(DATE_FORMAT_STRING,mDate).toString();
    }
    public String getFormatedTime(){
        return DateFormat.format(TIME_FORMAT_STRING, mDate).toString();
    }
    public String getFormatedDateAndTime(){
        return DateFormat.format(DATE_FORMAT_STRING.toString() + " " + TIME_FORMAT_STRING.toString(), mDate).toString();
    }
    public void setDone(boolean done){
        mDone = done;
    }
    public boolean isDone(){
        return mDone;
    }
}
