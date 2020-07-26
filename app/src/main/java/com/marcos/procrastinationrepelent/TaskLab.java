package com.marcos.procrastinationrepelent;

import android.content.Context;
import java.util.ArrayList;
import java.util.UUID;

public class TaskLab {
    private static TaskLab sTaskLabInstance;
    private Context mAppContext;
    private ArrayList<Task> mTasks;

    private TaskLab(Context appContext){
        mAppContext = appContext;
        mTasks = new ArrayList<Task>();
    }

    //Singleton
    public static TaskLab getInstance(Context context){
        if(sTaskLabInstance == null){
            sTaskLabInstance = new TaskLab(context.getApplicationContext());
        }
        return sTaskLabInstance;
    }

    public void addTask(Task t){
        mTasks.add(t);
    }

    public Task getTask(UUID id){
        for(Task t : mTasks){
            if( id.equals( t.getId() ) ){
                return t;
            }
        }
        return null;
    }
    public ArrayList<Task> getTasks(){
        return mTasks;
    }
}
