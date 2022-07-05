package com.example.maru.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import android.widget.ImageButton;

import com.example.maru.DI.DI;
import com.example.maru.Model.Meeting;
import com.example.maru.Model.MeetingRoom;
import com.example.maru.R;
import com.example.maru.View.Fragment.DateSelectorDialogFragment;
import com.example.maru.View.Fragment.customDialogFragment;
import com.example.maru.ViewModel.MeetingListRecyclerViewAdapter;
import com.example.maru.services.MeetingRoom.ApiServiceMeetingRoom;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomePageActivity extends AppCompatActivity implements customDialogFragment.OnDataPass, DateSelectorDialogFragment.OnDataPass {

    private ImageButton mFilterBtn, mAddMeetingBtn;
    private RecyclerView mListMeetingRv;
    private PopupMenu mFilterMenu;
    private Menu mMenu;

    private List<Meeting> fullMeetingList;

    private boolean isFilteredByDate = false,
                    isFilteredByPlace = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_home_page);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
        initFilterMenu();
        setupBtns();
    }

    private void init(){
        mFilterBtn = findViewById(R.id.ibtnFilter);
        mAddMeetingBtn = findViewById(R.id.IBtnaddMeeting);
        mListMeetingRv = findViewById(R.id.rvListMeeting);
        mListMeetingRv.setLayoutManager(new LinearLayoutManager(this));
        mListMeetingRv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mListMeetingRv.setAdapter(new MeetingListRecyclerViewAdapter());
        MeetingListRecyclerViewAdapter adapter = (MeetingListRecyclerViewAdapter) mListMeetingRv.getAdapter();
        fullMeetingList = adapter.getMeetings();
        for (int i = 0; i < fullMeetingList.size(); i++){
            Log.i("listMeeting", fullMeetingList.get(i).getTitle());
        }
    }

    private void initFiltredList(List<Meeting> meetings){
        mListMeetingRv.setAdapter(new MeetingListRecyclerViewAdapter(meetings));
    }

    private void initFilterMenu() {
        mFilterMenu = new PopupMenu(this,mFilterBtn);
        mMenu = mFilterMenu.getMenu();
        mFilterMenu.getMenuInflater().inflate(R.menu.filter_menu, mMenu);

        mFilterMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.menuPlace:
                    showPlaceDialog();
                    return true;
                case R.id.menuDate:
                    showDateDialog();
                    return true;
                case R.id.menuAnnulerFiltre:
                    initFiltredList(fullMeetingList);
                    return true;
            }

            return false;
        });

        mFilterBtn.setOnClickListener(v -> mFilterMenu.show());
    }

    private void setupBtns(){
        mAddMeetingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddMeetingActivity.class);
            startActivity(intent);
        });
    }

    private void showPlaceDialog() {
        ApiServiceMeetingRoom api = DI.getMeetingRoomApiService();
        List<MeetingRoom> Rooms = api.getMeetingRooms();
        FragmentManager fm = getSupportFragmentManager();
        customDialogFragment DialogFragment = customDialogFragment.newInstance(Rooms);
        DialogFragment.show(fm,null);
    }

    private void showDateDialog(){
        FragmentManager fm = getSupportFragmentManager();
        DateSelectorDialogFragment dialogFragment = DateSelectorDialogFragment.newInstance();
        dialogFragment.show(fm,null);
    }

    @Override
    public void onDataPass(MeetingRoom room) {
        List<Meeting> meetings = new ArrayList<>();
        if (!isFilteredByPlace) {
            for (int i = 0; i < fullMeetingList.size(); i++) {
                if (fullMeetingList.get(i).getRoom().getId() == room.getId())
                    meetings.add(fullMeetingList.get(i));
            }
        }else{
            MeetingListRecyclerViewAdapter adapter = (MeetingListRecyclerViewAdapter) mListMeetingRv.getAdapter();
            List<Meeting> curentList = adapter.getMeetings();
            for (int i = 0; i < curentList.size(); i++) {
                if (curentList.get(i).getRoom().getId() == room.getId())
                    meetings.add(curentList.get(i));
            }
        }
        initFiltredList(meetings);
    }

    @Override
    public void onDataPass(Calendar calendar) {
        List<Meeting> meetings = new ArrayList<>();
        if (!isFilteredByDate) {
            for (int i = 0; i < fullMeetingList.size(); i++) {
                Calendar mCalendar = fullMeetingList.get(i).getDate();
                if (calendar.get(Calendar.DAY_OF_MONTH) == mCalendar.get(Calendar.DAY_OF_MONTH)
                        && calendar.get(Calendar.MONTH) == mCalendar.get(Calendar.MONTH)
                        && calendar.get(Calendar.YEAR) == mCalendar.get(Calendar.YEAR)) {
                    meetings.add(fullMeetingList.get(i));
                }
            }
        }else{
            MeetingListRecyclerViewAdapter adapter = (MeetingListRecyclerViewAdapter) mListMeetingRv.getAdapter();
            List<Meeting> curentList = adapter.getMeetings();
            for (int i = 0; i < curentList.size(); i++) {
                Calendar mCalendar = curentList.get(i).getDate();
                if (calendar.get(Calendar.DAY_OF_MONTH) == mCalendar.get(Calendar.DAY_OF_MONTH)
                        && calendar.get(Calendar.MONTH) == mCalendar.get(Calendar.MONTH)
                        && calendar.get(Calendar.YEAR) == mCalendar.get(Calendar.YEAR)) {
                    meetings.add(curentList.get(i));
                }
            }
        }
        initFiltredList(meetings);
    }
}