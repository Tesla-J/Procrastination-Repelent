package com.marcos.procrastinationrepelent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class TaskFragment extends Fragment {
    private Task mTask;
    private EditText mTitleField;

     @Override
    public void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         mTask = new Task();
     }

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
         View v = inflater.inflate(R.layout.fragment_task, parent, false);
         mTitleField = (EditText) v.findViewById(R.id.titleField);
         mTitleField.addTextChangedListener(new TextWatcher(){
             @Override
             public void onTextChanged(CharSequence c, int start, int before, int count){
                 mTask.setTitle(c.toString());
             }
             @Override
             public void beforeTextChanged(CharSequence c, int start, int count, int before){
                 //intentionally left blank
             }
             @Override
             public void afterTextChanged(Editable c){
                 //intentionally left blank
             }
         });
         return v;
     }
}
