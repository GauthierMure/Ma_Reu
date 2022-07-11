package com.example.maru.View.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import android.widget.FrameLayout;

import com.example.maru.R;
import com.example.maru.View.Fragment.ListMeetingFragment;
import com.example.maru.View.Fragment.MeetingInfoFragment;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    public static final int TAG_LIST_FRAGMENT = 0;
    public static final int TAG_OTHER_FRAGMENT = 1;

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
            setFragment(fm, ListFragment,TAG_LIST_FRAGMENT);
            setFragment(fm, meetingInfoFragment,TAG_OTHER_FRAGMENT);


        }else {
            fl = findViewById(R.id.frameLayout);
            frameLayouts.add(fl);

            FragmentManager fm = getSupportFragmentManager();
            ListMeetingFragment ListFragment = ListMeetingFragment.newInstance();
            setFragment(fm, ListFragment,TAG_LIST_FRAGMENT);
        }
    }

    public static void setFragment(FragmentManager fm, Fragment fragment, int tag){
        if (tag == TAG_LIST_FRAGMENT)
            fm.beginTransaction().replace(frameLayouts.get(0).getId(),fragment,null).commit();
        else if (tag == TAG_OTHER_FRAGMENT)
            fm.beginTransaction().replace(frameLayouts.get(1).getId(),fragment,null).commit();

    }
}