package com.example.maru.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.maru.DI.DI;
import com.example.maru.Model.Meeting;
import com.example.maru.Model.MeetingRoom;
import com.example.maru.R;
import com.example.maru.View.Fragment.CreateMeetingInfoFragment;
import com.example.maru.View.Fragment.DateSelectorFragment;
import com.example.maru.ViewModel.AddMailRecyclerViewAdapter;
import com.example.maru.services.Meeting.ApiServiceMeeting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddMeetingActivity extends AppCompatActivity implements DateSelectorFragment.OnDataPass, CreateMeetingInfoFragment.OnDataPass {

    private ConstraintLayout mActionBar;

    private ImageButton returnBtn;

    private static final ApiServiceMeeting Api = DI.getMeetingApiService();
    private static Meeting mMeeting;

    private int CurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_add_meeting);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).add(R.id.fragmentContainer, DateSelectorFragment.class,null).commit();
            CurrentFragment = 1;
        }
        init();
    }

    private void init(){
        mMeeting  = new Meeting(Api.generateId());
        mActionBar = findViewById(R.id.meetingInfoActionBar);
        mActionBar.setBackgroundColor(getResources().getColor(R.color.appTheme));
        returnBtn = findViewById(R.id.iBtnAddMeetingReturn);
        returnBtn.setOnClickListener(v -> Return());
    }

    private void Return(){
        if (CurrentFragment == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, DateSelectorFragment.class, null).commit();
            CurrentFragment = 1;
        }
        else
            finish();
    }

    @Override
    public void onDataPass(Calendar calendar) {
        mMeeting.setDate(calendar);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,CreateMeetingInfoFragment.class,null).commit();
        CurrentFragment = 2;
    }

    @Override
    public void onDataPass(String name, String creator, MeetingRoom room, List<String> mails) {
        mMeeting.setTitle(name);
        mMeeting.setCreator(creator);
        mMeeting.setRoom(room);
        mMeeting.setColor(room.getColor());
        mMeeting.setParticipants(mails);
        Api.addMeeting(mMeeting);
        finish();
    }
}