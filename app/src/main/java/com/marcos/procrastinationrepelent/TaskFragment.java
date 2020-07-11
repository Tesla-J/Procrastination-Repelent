package com.marcos.procrastinationrepelent;

import android.app.Activity;
import android.content.Intent;
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
import androidx.fragment.app.FragmentManager;

import java.util.Date;
import java.util.UUID;

import androidx.fragment.app.Fragment;

public class TaskFragment extends Fragment {
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mDoneCheckBox;
    private Task mTask;
    private EditText mTitleField;
    public static final String EXTRA_TASK_ID = "com.marcos.ProcrastinationRepelent.TaskId";
    private static final String DIALOG_DATE = "Task date";
    private static final String DIALOG_TIME = "Task time";
    private static final int REQUEST_DATE_CODE = 0;
    private static final int REQUEST_TIME_CODE = 1;

     @Override
    public void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         UUID taskId = (UUID) getArguments().getSerializable(EXTRA_TASK_ID);
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
         updateDate();
         mDateButton.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v){
                 FragmentManager fm = getActivity().getSupportFragmentManager();
                 DatePickerFragment dialog = DatePickerFragment.newInstance(mTask.getDate());
                 dialog.setTargetFragment(TaskFragment.this, REQUEST_DATE_CODE);
                 dialog.show(fm, DIALOG_DATE);
             }
         });
         mTimeButton = (Button) v.findViewById(R.id.task_time_button);
         updateTime();
         mTimeButton.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v){
                 FragmentManager fm = getActivity().getSupportFragmentManager();
                 TimePickerFragment dialog = TimePickerFragment.newInstance(mTask.getDate());
                 dialog.setTargetFragment(TaskFragment.this, REQUEST_TIME_CODE);
                 dialog.show(fm, DIALOG_TIME);
             }
         });
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

     @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data){
         if(resultCode != Activity.RESULT_OK) return;
         if(requestCode == REQUEST_DATE_CODE) {
             Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
             mTask.setDate(date);
             updateDate();
         }
         else if(requestCode == REQUEST_TIME_CODE){
             Date time = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
             mTask.setDate(time);
             updateTime();
         }
     }

     private void updateDate(){
         mDateButton.setText(mTask.getFormatedDate());
     }
     private void updateTime() {
         mTimeButton.setText(mTask.getFormatedTime());
     }

     public static Fragment newInstance(UUID taskId){
         Bundle args = new Bundle();
         args.putSerializable(EXTRA_TASK_ID, taskId);
         TaskFragment fragment = new TaskFragment();
         fragment.setArguments(args);
         return fragment;
     }
}
