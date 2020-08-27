package com.marcos.procrastinationrepelent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.format.DateFormat;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import androidx.core.app.NavUtils;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;

import java.util.Date;
import java.util.UUID;

public class TaskFragment extends Fragment {
    private Button mDateAndTimeButton;
    private CheckBox mDoneCheckBox;
    private Task mTask;
    private EditText mTitleField;
    public static final String EXTRA_TASK_ID = "com.marcos.ProcrastinationRepelent.TaskId";
    private static final String DIALOG_DATE_AND_TIME = "Task date";
    private static final int REQUEST_DATE_AND_TIME_CODE = 0;

     @Override
    public void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         UUID taskId = (UUID) getArguments().getSerializable(EXTRA_TASK_ID);
         mTask = TaskLab.getInstance(getActivity()).getTask(taskId);
     }

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
         View v = inflater.inflate(R.layout.fragment_task, parent, false);
         //verify if there is a parent activity. If not, doesn't display the home arrow
         if(NavUtils.getParentActivityName(getActivity()) != null){
             getActivity().getActionBar().setDisplayHomeAsUpEnabled(true); //sets the left arrow in the action bar to return the last fragment as visible
              }
         mTitleField = (EditText) v.findViewById(R.id.titleField);
         mTitleField.setText(mTask.getTitle());
         mTitleField.addTextChangedListener(new TextWatcher(){
             @Override
             public void onTextChanged(CharSequence c, int start, int before, int count){
                 mTask.setTitle(c.toString());
                 updateTitle();
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
         mDateAndTimeButton = (Button) v.findViewById(R.id.task_date_time_button);
         updateDateAndTime();
         mDateAndTimeButton.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v){
                 FragmentManager fm = getActivity().getSupportFragmentManager();
                 //OptionPickerFragment dialog = OptionPickerFragment.newInstance(mTask.getDate());
                 DateAndTimePickerFragment dialog = DateAndTimePickerFragment.newInstance(mTask.getDate());
                 dialog.setTargetFragment(TaskFragment.this, REQUEST_DATE_AND_TIME_CODE);
                 dialog.show(fm, DIALOG_DATE_AND_TIME);
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
         updateTitle();
         setHasOptionsMenu(true);
         return v;
     }

     @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data){
         if(resultCode != Activity.RESULT_OK) return;
         if(requestCode == REQUEST_DATE_AND_TIME_CODE) {
             Date date = (Date) data.getSerializableExtra(DateAndTimePickerFragment.DATE_AND_TIME_EXTRA);
             mTask.setDate(date);
             updateDateAndTime();
         }
     }

     //Setting the home arrow
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
         switch(item.getItemId()){
             case android.R.id.home:
                 //To be implemented next
                 if(NavUtils.getParentActivityName(getActivity()) != null){
                     NavUtils.navigateUpFromSameTask(getActivity());
                 }
                 return true;
             default:
                 return super.onOptionsItemSelected(item);
         }
    }

    @Override
    public void onPause(){
         super.onPause();
         TaskLab.getInstance(getActivity()).saveTasks();
    }

     private void updateDateAndTime(){
         mDateAndTimeButton.setText(mTask.getFormatedDateAndTime());
     }

     public static Fragment newInstance(UUID taskId){
         Bundle args = new Bundle();
         args.putSerializable(EXTRA_TASK_ID, taskId);
         TaskFragment fragment = new TaskFragment();
         fragment.setArguments(args);
         return fragment;
     }

     //Update the title in the action bar
     private void updateTitle(){
         getActivity().setTitle(mTask.getTitle());
     }
}
