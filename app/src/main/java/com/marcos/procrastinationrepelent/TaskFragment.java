package com.marcos.procrastinationrepelent;

import android.os.Bundle;
import android.text.Editable;
import android.text.format.DateFormat;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import java.util.UUID;

import androidx.fragment.app.Fragment;

public class TaskFragment extends Fragment {
    private Button mDateButton;
    private CheckBox mDoneCheckBox;
    private Task mTask;
    private EditText mTitleField;
    public static final String EXTRA_TASK_ID = "com.marcos.ProcrastinationRepelent.TaskId";

     @Override
    public void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         UUID taskId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_TASK_ID);
         mTask = TaskLab.getInstance(getActivity()).getTask(taskId);
     }

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
         View v = inflater.inflate(R.layout.fragment_task, parent, false);
         mTitleField = (EditText) v.findViewById(R.id.titleField);
         mTitleField.setText(mTask.getTitle());
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
         mDateButton = (Button) v.findViewById(R.id.task_date_button);
         mDateButton.setText(mTask.getFormatedDate());
         mDateButton.setEnabled(false);
         mDoneCheckBox = (CheckBox) v.findViewById(R.id.task_done_checkBox);
         mDoneCheckBox.setChecked(mTask.isDone());
         mDoneCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                 mTask.setDone(isChecked);
             }
         });
         return v;
     }
}
