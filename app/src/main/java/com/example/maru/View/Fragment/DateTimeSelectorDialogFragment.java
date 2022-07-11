package com.example.maru.View.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.maru.R;

import java.util.Calendar;

public class DateTimeSelectorDialogFragment extends DialogFragment {

    public interface OnDateTimePass {
        public void onDatePass(Calendar calendar);
    }

    private OnDateTimePass dataPasser;

    private Button mButton;
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private Calendar mCalendar;

    public DateTimeSelectorDialogFragment() {
    }


    public static DateTimeSelectorDialogFragment newInstance() {
        return new DateTimeSelectorDialogFragment();
    }

    public void setDateTimePasser(OnDateTimePass dataPasser){
        this.dataPasser = dataPasser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_date_time_selector, container, false);
        if (getDialog() != null && getDialog().getWindow() != null){
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mButton = view.findViewById(R.id.dateNextBtn);
        mButton.setOnClickListener(v -> SelectDateTime());
        mDatePicker = view.findViewById(R.id.calendarView);
        mTimePicker = view.findViewById(R.id.timePicker);
        mTimePicker.setIs24HourView(true);
        super.onViewCreated(view, savedInstanceState);
    }

    private void SelectDateTime(){
        int Day = mDatePicker.getDayOfMonth();
        int Month = mDatePicker.getMonth();
        int Year = mDatePicker.getYear();
        int Hour = mTimePicker.getCurrentHour();
        int Minute = mTimePicker.getCurrentMinute();
        mCalendar = Calendar.getInstance();
        mCalendar.set(Year,Month,Day,Hour,Minute);
        dataPasser.onDatePass(mCalendar);
        dismiss();
    }
}