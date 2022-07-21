package com.example.maru.View.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import android.widget.FrameLayout;

import com.example.maru.DI.DI;
import com.example.maru.R;
import com.example.maru.View.Fragment.ListMeetingFragment;
import com.example.maru.View.Fragment.MeetingInfoFragment;
import com.example.maru.services.Meeting.ApiServiceMeeting;
import com.example.maru.services.MeetingRoom.ApiServiceMeetingRoom;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    public static final int LIST_FRAGMENT = 0;
    public static final int OTHER_FRAGMENT = 1;

    public static final String FRAGMENT_LIST_MEETINGS = "list_meetings";
    public static final String FRAGMENT_CREATE_MEETING = "create_meeting";
    public static final String FRAGMENT_INFO_MEETING = "info_meeting";

    private static List<FrameLayout> frameLayouts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_home_page);
        init();
    }

    private void init(){
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        FrameLayout fl;
        if (isTablet){
            fl = findViewById(R.id.frameLayout);
            frameLayouts.add(fl);
            fl = findViewById(R.id.contentFrameLayout);
            frameLayouts.add(fl);

            FragmentManager fm = getSupportFragmentManager();
            ListMeetingFragment ListFragment = ListMeetingFragment.newInstance();
            MeetingInfoFragment meetingInfoFragment = MeetingInfoFragment.newInstance(1);
            setFragment(fm, ListFragment, LIST_FRAGMENT,FRAGMENT_LIST_MEETINGS);
            setFragment(fm, meetingInfoFragment, OTHER_FRAGMENT,FRAGMENT_INFO_MEETING);


        }else {
            fl = findViewById(R.id.frameLayout);
            frameLayouts.add(fl);

            FragmentManager fm = getSupportFragmentManager();
            ListMeetingFragment ListFragment = ListMeetingFragment.newInstance();
            setFragment(fm, ListFragment, LIST_FRAGMENT,FRAGMENT_LIST_MEETINGS);
        }
    }

    public static void setFragment(FragmentManager fm, Fragment fragment, int Container, String Tag){
        if (Container == LIST_FRAGMENT)
            fm.beginTransaction().replace(frameLayouts.get(0).getId(),fragment,Tag).commit();
        else if (Container == OTHER_FRAGMENT)
            fm.beginTransaction().replace(frameLayouts.get(1).getId(),fragment,Tag).commit();

    }
}