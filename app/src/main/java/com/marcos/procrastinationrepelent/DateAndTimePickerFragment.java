package com.marcos.procrastinationrepelent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Date;
import java.util.Calendar;

public class DateAndTimePickerFragment  extends DialogFragment {
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private Date mDate;
    private Calendar mCalendar;

    public static String DATE_AND_TIME_ARGUMENT = "Date and time";
    public static String DATE_AND_TIME_EXTRA = "com.marcos.procrastinationrepelent.dateandtime";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        mDate = (Date) getArguments().getSerializable(DATE_AND_TIME_ARGUMENT);
        mCalendar = Calendar.getInstance();
        View v = getActivity().getLayoutInflater().inflate(R.layout.date_and_time_picker, null);

        mDatePicker = (DatePicker) v.findViewById(R.id.date_picker);
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year, month, dayOfMonth, new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int dayOfMonth){
                mCalendar.set(year, month, dayOfMonth);
                mDate = mCalendar.getTime();
            }
        });
        mTimePicker = (TimePicker) v.findViewById(R.id.time_picker);
        mTimePicker.setIs24HourView(true);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener(){
            @Override
            public void onTimeChanged(TimePicker view, int hour, int minute){
                mCalendar.set(Calendar.HOUR_OF_DAY, hour);
                mCalendar.set(Calendar.MINUTE, minute);
                mDate = mCalendar.getTime();
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_and_time_dialog_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }

    public static DateAndTimePickerFragment newInstance(Date date_time){
        Bundle args = new Bundle();
        args.putSerializable(DATE_AND_TIME_ARGUMENT, date_time);
        DateAndTimePickerFragment fragment = new DateAndTimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode){
        if(getTargetFragment() != null) {
            Intent i = new Intent();
            i.putExtra(DATE_AND_TIME_EXTRA, mDate);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
        }
    }
}
