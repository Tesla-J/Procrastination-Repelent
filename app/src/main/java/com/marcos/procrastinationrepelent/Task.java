package com.marcos.procrastinationrepelent;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;
import java.util.UUID;
import android.text.format.DateFormat;

public class Task {
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_DONE = "solved";
    private static final String JSON_DATE = "date";

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

    public Task(JSONObject json) throws JSONException{
        mId = UUID.fromString(json.getString(JSON_ID));
        if(json.has(JSON_TITLE)){
            mTitle = json.getString(JSON_TITLE);
        }
        mDone = json.getBoolean(JSON_DONE);
        mDate = new Date (json.getLong(JSON_DATE));
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

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_DONE, mDone);
        json.put(JSON_DATE, mDate.getTime());
        return json;
    }
}
