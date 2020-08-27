package com.marcos.procrastinationrepelent;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.UUID;

public class TaskLab {
    private static final String TAG = "TaskLab";
    private static final String FILENAME = "tasks.json";

    private static TaskLab sTaskLabInstance;
    private Context mAppContext;

    private ArrayList<Task> mTasks;
    private TaskIntentJSONSerializer mSerializer;
    private TaskIntentJSONSerializer mJsonSerializer;

    private static TaskLab sTaskLab;

    private TaskLab(Context appContext){
        mAppContext = appContext;
        mSerializer = new TaskIntentJSONSerializer(mAppContext, FILENAME);

        try {
            mTasks = mSerializer.loadTasks();
        }
        catch (Exception e) {
            mTasks = new ArrayList<Task>();
            Log.e(TAG, "Error loading tasks: ", e);
        }
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

    //saving data
    public boolean saveTasks(){
        try{
            mSerializer.saveTasks(mTasks);
            Log.d(TAG, "Tasks saved to file");
            return true;
        }
        catch (Exception e){
            Log.e(TAG, "error saving tasks: ", e);
            return false;
        }
    }
}
