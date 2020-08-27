package com.marcos.procrastinationrepelent;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONTokener;

public class TaskIntentJSONSerializer {
    private Context mContext;
    private String mFilename;

    public TaskIntentJSONSerializer (Context c, String f){
        mContext = c;
        mFilename = f;
    }

    public void saveTasks(ArrayList<Task> tasks) throws JSONException, IOException{
        //Buils an array in JSON
        JSONArray array = new JSONArray();
        for (Task t : tasks){
            array.put(t.toJSON());
        }
        // Write the file to disk
        Writer writer = null;
        try{
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer =  new OutputStreamWriter(out);
            writer.write(array.toString());
        }finally{
            if(writer != null){
                writer.close();
            }
        }
    }

    public ArrayList<Task> loadTasks() throws IOException, JSONException{
        ArrayList<Task> tasks = new ArrayList<Task>();
        BufferedReader reader = null;
        try{
            // Open and read the file into a StringBuilder
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader (new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null){
                // Line breaks are  omitted and irrelevant
                jsonString.append(line);
            }
            // Parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            //Buld the array of taks from JSONObjects
            for (int i=0; i<array.length(); i++){
                tasks.add(new Task(array.getJSONObject(i)));
            }
        }
        catch (FileNotFoundException e) {
            //ignore this one; it happens when starting fresh
        }
        finally {
            if(reader != null){
                reader.close();
            }
        }
        return tasks;
    }
}
