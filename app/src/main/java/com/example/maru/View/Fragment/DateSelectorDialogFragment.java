package com.example.maru.View.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.maru.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateSelectorDialogFragment extends DialogFragment {

    public interface OnDatePass {
        public void onDateTimePass(Calendar calendar);
    }

    private OnDatePass dataPasser;

    private DatePicker mDatePicker;
    private Button mSelectBtn;

    public DateSelectorDialogFragment(){

    }

    public static DateSelectorDialogFragment newInstance(){
        return new DateSelectorDialogFragment();
    }

    public void setDatePasser(OnDatePass dataPasser){
        this.dataPasser = dataPasser;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_date_selector,container,false);
        if (getDialog() != null && getDialog().getWindow() != null){
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatePicker = view.findViewById(R.id.datefilterPicker);
        mSelectBtn = view.findViewById(R.id.selectDateFilterBtn);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSelectBtn.setOnClickListener(v -> SelectDate());
    }

    private void SelectDate() {
        int day = mDatePicker.getDayOfMonth();
        int month = mDatePicker.getMonth();
        int year = mDatePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        dataPasser.onDateTimePass(calendar);
        dismiss();
    }

}
