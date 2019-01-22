package com.example.root.sens.view_layer.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import com.example.root.sens.R;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int hour;
        int minute;
        if(getArguments() != null){
           hour = (getArguments().get("hour") == null) ? 0 : (int) getArguments().get("hour");
           minute = (getArguments().get("minute") == null) ? 0 : (int) getArguments().get("minute");
        }
        else{
            hour = 0;
            minute = 0;
        }
        // Use the current time as the default values for the picker


        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), R.style.MyTimePickerDialogStyle, this, hour, minute,
                true);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Bundle bundle = new Bundle();
        bundle.putInt("hour", hourOfDay);
        bundle.putInt("minute", minute);
        Intent intent = new Intent().putExtras(bundle);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        dismiss();
    }
}
