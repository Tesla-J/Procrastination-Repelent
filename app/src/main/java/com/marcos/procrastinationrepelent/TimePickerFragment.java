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

import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment {
    public static final String EXTRA_TIME = "com.marcos.procrastinationrepelent.time";
    private Date mTime;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        mTime = (Date) getArguments().getSerializable(EXTRA_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTime);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);

        TimePicker timePicker = (TimePicker) v.findViewById(R.id.dialog_time_layout);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener( new TimePicker.OnTimeChangedListener(){
            @Override
            public void onTimeChanged(TimePicker v, int hourOfDay, int minute){
                mTime.setTime( mTime.getTime() + hourToMilliseconds(hourOfDay) + minuteToMilliseconds(minute));
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

    private long hourToMilliseconds(int hour){
        return hour*3600*1000;
    }
    private long minuteToMilliseconds(int minute){
        return minute*60000;
    }
}
