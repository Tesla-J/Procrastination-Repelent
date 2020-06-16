package com.marcos.procrastinationrepelent;

import java.util.Date;
import java.util.UUID;

public class Task {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mDone;

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
    public void setDone(boolean done){
        mDone = done;
    }
    public boolean isDone(){
        return mDone;
    }
}
