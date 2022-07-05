package com.example.maru.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.maru.DI.DI;
import com.example.maru.Model.Meeting;
import com.example.maru.R;
import com.example.maru.View.Fragment.DateSelectorFragment;
import com.example.maru.View.Fragment.MeetingInfoFragment;
import com.example.maru.services.Meeting.ApiServiceMeeting;

public class MeetingInfoActivity extends AppCompatActivity {

    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_meeting_info);
        Intent intent = getIntent();
        mId = intent.getIntExtra("id",0);
        if (savedInstanceState == null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            MeetingInfoFragment fragment = MeetingInfoFragment.newInstance(mId);
            ft.replace(R.id.meetingInfoFragmentContainer,fragment);
            ft.commit();
        }
        init();
    }

    private void init(){

    }
}