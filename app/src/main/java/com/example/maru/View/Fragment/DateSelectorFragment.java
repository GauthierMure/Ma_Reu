package com.example.maru.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.maru.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DateSelectorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateSelectorFragment extends Fragment {

    public interface OnDataPass{
        public void onDataPass(Calendar calendar);
    }

    private OnDataPass dataPasser;

    private Button mButton;
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private Calendar mCalendar;

    public DateSelectorFragment() {
    }


    public static DateSelectorFragment newInstance(String param1, String param2) {
        return new DateSelectorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_selector, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mButton = view.findViewById(R.id.dateNextBtn);
        mButton.setOnClickListener(v -> Next());
        mDatePicker = view.findViewById(R.id.calendarView);
        mTimePicker = view.findViewById(R.id.timePicker);
        mTimePicker.setIs24HourView(true);
        super.onViewCreated(view, savedInstanceState);
    }

    private void Next(){
        int Day = mDatePicker.getDayOfMonth();
        int Month = mDatePicker.getMonth() + 1;
        int Year = mDatePicker.getYear();
        int Hour = mTimePicker.getCurrentHour();
        int Minute = mTimePicker.getCurrentMinute();
        mCalendar = Calendar.getInstance();
        mCalendar.set(Year,Month,Day,Hour,Minute);
        dataPasser.onDataPass(mCalendar);
    }
}