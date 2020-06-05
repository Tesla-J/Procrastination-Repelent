package com.marcos.procrastinationrepelent;

import java.util.UUID;

public class Task {
    private UUID mId;
    private String mTitle;

    public Task(){
        mId = UUID.randomUUID();
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
}
