package com.example.maru.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.maru.DI.DI;
import com.example.maru.Model.Meeting;
import com.example.maru.R;
import com.example.maru.services.Meeting.ApiServiceMeeting;

public class MeetingInfoActivity extends AppCompatActivity {

    private ApiServiceMeeting mApiServiceMeeting = DI.getMeetingApiService();
    private Meeting mMeeting;

    private ImageButton mReturnIBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_meeting_info);
        init();
    }

    private void init(){
        mReturnIBtn = findViewById(R.id.iBtnReturnInfoMeeting);
        mReturnIBtn.setOnClickListener(v -> finish());
    }
}