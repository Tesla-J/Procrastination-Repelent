package com.marcos.procrastinationrepelent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment {
    public static final String EXTRA_TIME = "com.marcos.procrastinationrepelent.time";
    private Date mTime;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        mTime = (Date) getArguments().getSerializable(EXTRA_TIME);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);

        TimePicker timePicker = (TimePicker) v.findViewById(R.id.dialog_time_layout);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener( new TimePicker.OnTimeChangedListener(){
            @Override
            public void onTimeChanged(TimePicker v, int hourOfDay, int minute){
                GregorianCalendar calendar = new GregorianCalendar();
                //calendar.setTime(mTime);
                int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);
                int month = calendar.get(calendar.MONTH);
                int year = calendar.get(calendar.YEAR);
                calendar.set(year, month, day, hourOfDay, minute);
                mTime=calendar.getTime();
                getArguments().putSerializable(EXTRA_TIME, mTime);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                   @Override
                   public void onClick(DialogInterface dialog, int which){
                       sendResult(Activity.RESULT_OK);
                   }
        })
                .create();
    }

    public static TimePickerFragment newInstance(Date time){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TIME, time);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode){
        if(getTargetFragment() != null){
            Intent i = new Intent();
            i.putExtra(EXTRA_TIME, mTime);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
        }
    }
}
