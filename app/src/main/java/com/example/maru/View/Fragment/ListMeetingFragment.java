package com.example.maru.View.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.maru.DI.DI;
import com.example.maru.Model.Meeting;
import com.example.maru.Model.MeetingRoom;
import com.example.maru.R;
import com.example.maru.View.Activity.HomePageActivity;
import com.example.maru.ViewModel.MeetingListRecyclerViewAdapter;
import com.example.maru.services.MeetingRoom.ApiServiceMeetingRoom;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListMeetingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListMeetingFragment extends Fragment implements customDialogFragment.OnRoomPass, DateSelectorDialogFragment.OnDatePass {

    private ImageButton mFilterBtn, mAddMeetingBtn;
    private RecyclerView mListMeetingRv;
    private PopupMenu mFilterMenu;
    private Menu mMenu;

    private List<Meeting> fullMeetingList;

    private boolean isFilteredByDate = false,
            isFilteredByPlace = false;

    public ListMeetingFragment() {

    }

    public static ListMeetingFragment newInstance() {
        return new ListMeetingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_meeting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFilterBtn = view.findViewById(R.id.ibtnFilter);
        mAddMeetingBtn = view.findViewById(R.id.IBtnaddMeeting);
        mListMeetingRv = view.findViewById(R.id.rvListMeeting);
        mListMeetingRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mListMeetingRv.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mListMeetingRv.setAdapter(new MeetingListRecyclerViewAdapter((HomePageActivity) getActivity()));
        MeetingListRecyclerViewAdapter adapter = (MeetingListRecyclerViewAdapter) mListMeetingRv.getAdapter();
        fullMeetingList = adapter.getMeetings();
        for (int i = 0; i < fullMeetingList.size(); i++){
            Log.i("listMeeting", fullMeetingList.get(i).getTitle());
        }
        initFilterMenu();
        setupBtns();
    }

    private void initFiltredList(List<Meeting> meetings){
        mListMeetingRv.setAdapter(new MeetingListRecyclerViewAdapter((HomePageActivity) getActivity(),meetings));
    }

    private void initFilterMenu() {
        mFilterMenu = new PopupMenu(getContext(),mFilterBtn);
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
            FragmentManager fm = getActivity().getSupportFragmentManager();
            CreateMeetingInfoFragment frag = CreateMeetingInfoFragment.newInstance();
            int Container;
            if (getActivity().getResources().getBoolean(R.bool.isTablet))
                Container = HomePageActivity.OTHER_FRAGMENT;
            else
                Container = HomePageActivity.LIST_FRAGMENT;
            String Tag = HomePageActivity.FRAGMENT_CREATE_MEETING;
            HomePageActivity.setFragment(fm,frag, Container, Tag);

        });
    }

    private void showPlaceDialog() {
        ApiServiceMeetingRoom api = DI.getMeetingRoomApiService();
        List<MeetingRoom> Rooms = api.getMeetingRooms();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        customDialogFragment DialogFragment = customDialogFragment.newInstance(Rooms);
        DialogFragment.setRoomPasser(ListMeetingFragment.this);
        DialogFragment.show(fm,null);
    }

    private void showDateDialog(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        DateSelectorDialogFragment dialogFragment = DateSelectorDialogFragment.newInstance();
        dialogFragment.setDatePasser(ListMeetingFragment.this);
        dialogFragment.show(fm,null);
    }

    public void reInitList(List<Meeting> meetings){
        initFiltredList(meetings);
    }

    @Override
    public void onRoomPass(MeetingRoom room) {
        if (room != null){
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
    }

    @Override
    public void onDateTimePass(Calendar calendar) {
        if (calendar != null){
            List<Meeting> meetings = new ArrayList<>();
            if (!isFilteredByDate) {
                for (int i = 0; i < fullMeetingList.size(); i++) {
                    Calendar mCalendar = fullMeetingList.get(i).getBeginingDate();
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
                    Calendar mCalendar = curentList.get(i).getBeginingDate();
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
}