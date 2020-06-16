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
        //temporary task generator
        for(int i=0; i<100; i++){
            Task c = new Task();
            c.setTitle("Task #"+i);
            c.setDone(i%2 == 0);
            mTasks.add(c);
        }
    }
    public static TaskLab getInstance(Context context){
        if(sTaskLabInstance == null){
            sTaskLabInstance = new TaskLab(context.getApplicationContext());
        }
        return sTaskLabInstance;
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
