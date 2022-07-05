package com.example.maru.View.Fragment;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.maru.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Calendar;

public class DateSelectorDialogFragment extends DialogFragment {

    public interface OnDataPass{
        public void onDataPass(Calendar calendar);
    }

    private OnDataPass dataPasser;

    private TextView mTitleTv;
    private DatePicker mDatePicker;
    private SwitchMaterial mChangePickerTypeSw;
    private Button mSelectBtn;

    public DateSelectorDialogFragment(){

    }

    public static DateSelectorDialogFragment newInstance(){
        return new DateSelectorDialogFragment();
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
        mTitleTv = view.findViewById(R.id.dateFilterTitle);
        mDatePicker = view.findViewById(R.id.datefilterPicker);
        mChangePickerTypeSw = view.findViewById(R.id.dateFilterTypeSw);
        mSelectBtn = view.findViewById(R.id.selectDateFilterBtn);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSelectBtn.setOnClickListener(v -> SelectFilter());
        mChangePickerTypeSw.setOnCheckedChangeListener(this::ChangeDatePicker);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    private void SelectFilter() {
        int day = mDatePicker.getDayOfMonth();
        int month = mDatePicker.getMonth();
        int year = mDatePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        dataPasser.onDataPass(calendar);
        dismiss();
    }

    private void ChangeDatePicker(CompoundButton buttonView, boolean isChecked) {

    }

}
