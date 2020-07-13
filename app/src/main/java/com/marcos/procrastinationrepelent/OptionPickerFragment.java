package com.marcos.procrastinationrepelent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.util.Date;

public class OptionPickerFragment extends DialogFragment {
    private RadioButton mModifyDateRadioButton;
    private RadioButton mModifyTimeRadioButton;
    private Date mDate;

    public static final String EXTRA_DATE_TIME_KEY = "com.marcos.procrastinationrepelent.datetime";
    public static final int REQUEST_DATE_TIME_CODE = 2;
    public static final String DIALOG_DATE_TIME = "Date and time dialog";

    @Override
    public Dialog onCreateDialog(Bundle onSavedInstanceState){
        mDate = (Date) getArguments().getSerializable(EXTRA_DATE_TIME_KEY);
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_option, null);
        mModifyDateRadioButton = (RadioButton) v.findViewById(R.id.modify_date_option);
        mModifyDateRadioButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                modifyDate();
            }
        });
        mModifyTimeRadioButton = (RadioButton) v.findViewById(R.id.modify_time_option);
        mModifyTimeRadioButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                modifyTime();
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.option_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == REQUEST_DATE_TIME_CODE){
            /* It will get the date from timepicker or datepicker
                I know that I could modify de code to optimize it, but I'm too lazy to do this.
            */
            try{
                mDate = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            }catch (NullPointerException e){
                mDate = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            }finally{
                getArguments().putSerializable(EXTRA_DATE_TIME_KEY, mDate);
            }
        }
    }

    public static OptionPickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE_TIME_KEY, date);
        OptionPickerFragment fragment = new OptionPickerFragment();
        fragment.setArguments(args);
        return fragment;

    }

    //these methods will redirect the user to the specific fragment
    private void modifyDate(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        DatePickerFragment dialog = DatePickerFragment.newInstance(mDate);
        dialog.setTargetFragment(OptionPickerFragment.this, REQUEST_DATE_TIME_CODE);
        dialog.show(fm, DIALOG_DATE_TIME);

    }
    private void modifyTime(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        TimePickerFragment dialog = TimePickerFragment.newInstance(mDate);
        dialog.setTargetFragment(OptionPickerFragment.this, REQUEST_DATE_TIME_CODE);
        dialog.show(fm, DIALOG_DATE_TIME);
    }

    //This method will redirect de the modified date and time to the TaskFragment, and further, the Button
    private void sendResult(int resultCode){
        if(getTargetFragment() != null){
            Intent i = new Intent();
            i.putExtra(EXTRA_DATE_TIME_KEY, mDate);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
        }


    }
}
