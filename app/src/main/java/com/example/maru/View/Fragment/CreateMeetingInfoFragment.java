package com.example.maru.View.Fragment;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maru.DI.DI;
import com.example.maru.Model.Meeting;
import com.example.maru.Model.MeetingRoom;
import com.example.maru.R;
import com.example.maru.View.Activity.HomePageActivity;
import com.example.maru.ViewModel.AddMailRecyclerViewAdapter;
import com.example.maru.ViewModel.MeetingRoomListAdapter;
import com.example.maru.services.Meeting.ApiServiceMeeting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateMeetingInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateMeetingInfoFragment extends Fragment implements DateSelectorDialogFragment.OnDatePass, DateTimeSelectorDialogFragment.OnDateTimePass {

    private ConstraintLayout mActionBar;
    private ImageButton mReturnBtn, mAddBtn, mBeginingCalendarBtn, mEndingCalendarBtn;
    private Button mCreateBtn;
    private Switch mIsFullDaySw;
    private EditText mMeetingNameEt, mCreatorEt, mAddMailEt;
    private TextView mActionBarTitle, mBeginingDateEt, mEndingDateEt;
    private Spinner mRoomChooserSp;
    private RecyclerView mMailRv;
    private View buttonClicked;

    private int mMaxMail;
    private boolean isChecked = false;
    private List<String> mMails = new ArrayList<>();
    private Calendar mBeginingCalendar, mEndingCalendar;

    private AddMailRecyclerViewAdapter adapter;

    public CreateMeetingInfoFragment() {
    }

    public static CreateMeetingInfoFragment newInstance() {
        return new CreateMeetingInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_meeting_infos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new AddMailRecyclerViewAdapter(mMails,mMaxMail);
        mBeginingCalendar = Calendar.getInstance();
        mEndingCalendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy - HH'h'mm");

        mActionBar = view.findViewById(R.id.addMeetingActionBar);
        mActionBar.setBackgroundColor(getActivity().getResources().getColor(R.color.appTheme));
        mActionBarTitle = view.findViewById(R.id.actionBarActivityTitleTv);
        if (!getActivity().getResources().getBoolean(R.bool.isTablet)) {
            mReturnBtn = view.findViewById(R.id.iBtnAddMeetingReturn);
            mReturnBtn.setOnClickListener(v -> Return());
        }

        mMeetingNameEt = view.findViewById(R.id.addMeetingMeetingNameEt);
        mCreatorEt = view.findViewById(R.id.addMeetingCreatorNameEt);
        mAddMailEt = view.findViewById(R.id.addMeetingAddMailEt);
        mBeginingDateEt = view.findViewById(R.id.addMeetingBeginingDateTv);
        mBeginingDateEt.setText(format.format(mBeginingCalendar.getTime()));
        mEndingDateEt = view.findViewById(R.id.addMeetingEndingDateTv);
        mEndingDateEt.setText(format.format(mEndingCalendar.getTime()));

        mRoomChooserSp = view.findViewById(R.id.addMeetingMeetingRoomSelectorSp);
        mRoomChooserSp.setAdapter(new MeetingRoomListAdapter(getContext()));
        mRoomChooserSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MeetingRoom room = (MeetingRoom) mRoomChooserSp.getAdapter().getItem(position);
                mMaxMail = room.getCapacity();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mIsFullDaySw = view.findViewById(R.id.addMeetingIsFullDaySw);
        mIsFullDaySw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            this.isChecked = isChecked;
        });

        mCreateBtn = view.findViewById(R.id.createMeetingBtn);
        mCreateBtn.setOnClickListener(v -> {
            Create();
        });
        mAddBtn = view.findViewById(R.id.addMeetingAddMailIBtn);
        mAddBtn.setOnClickListener(v -> {
            addMail();
        });
        mBeginingCalendarBtn = view.findViewById(R.id.addMeetingBeginingDateBtn);
        mBeginingCalendarBtn.setOnClickListener(v -> ShowCalendarChooser(v));
        mEndingCalendarBtn = view.findViewById(R.id.addMeetingEndingDateBtn);
        mEndingCalendarBtn.setOnClickListener(v -> ShowCalendarChooser(v));

        mMailRv = view.findViewById(R.id.addMeetingMailsRv);
        mMailRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mMailRv.setAdapter(adapter);
    }

    private void ShowCalendarChooser(View v) {
        buttonClicked = v;
        if (this.isChecked){
            FragmentManager fm = getActivity().getSupportFragmentManager();
            DateSelectorDialogFragment dialogFragment = DateSelectorDialogFragment.newInstance();
            dialogFragment.setDatePasser(CreateMeetingInfoFragment.this);
            dialogFragment.show(fm,null);
        }else{
            FragmentManager fm = getActivity().getSupportFragmentManager();
            DateTimeSelectorDialogFragment dialogFragment = DateTimeSelectorDialogFragment.newInstance();
            dialogFragment.setDateTimePasser(CreateMeetingInfoFragment.this);
            dialogFragment.show(fm,null);
        }
    }

    private void Create(){
        String name,creator;
        MeetingRoom room;
        List<String> mails;
        name = mMeetingNameEt.getText().toString();
        creator = mCreatorEt.getText().toString();
        room = (MeetingRoom) mRoomChooserSp.getSelectedItem();
        mails = adapter.getListMails();
        ApiServiceMeeting api = DI.getMeetingApiService();
        Meeting mMeeting = new Meeting(api.generateId());
        mMeeting.setTitle(name);
        mMeeting.setCreator(creator);
        mMeeting.setFullDay(isChecked);
        mMeeting.setBeginingDate(mBeginingCalendar);
        mMeeting.setEndingDate(mEndingCalendar);
        mMeeting.setRoom(room);
        mMeeting.setColor(room.getColor());
        mMeeting.setParticipants(mails);
        api.addMeeting(mMeeting);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        if (getActivity().getResources().getBoolean(R.bool.isTablet)){
            ListMeetingFragment listFrag = ListMeetingFragment.newInstance();
            int Tag = HomePageActivity.TAG_LIST_FRAGMENT;
            HomePageActivity.setFragment(fm,listFrag,Tag);
            MeetingInfoFragment frag = MeetingInfoFragment.newInstance(mMeeting.getmId());
            Tag = HomePageActivity.TAG_OTHER_FRAGMENT;
            HomePageActivity.setFragment(fm,frag,Tag);
        }else{
            ListMeetingFragment frag = ListMeetingFragment.newInstance();
            int Tag = HomePageActivity.TAG_LIST_FRAGMENT;
            HomePageActivity.setFragment(fm,frag,Tag);
        }
    }

    private void Return(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        MeetingInfoFragment frag = MeetingInfoFragment.newInstance(1);
        int Tag = HomePageActivity.TAG_OTHER_FRAGMENT;
        HomePageActivity.setFragment(fm, frag, Tag);
    }

    private void addMail(){
        int size = adapter.getItemCount();
        if (size - 1 >= mMaxMail){
            Toast.makeText(getContext(),"La salle de réunion ne peut acceuillir plus de personnes.\n" +
                    "Veuillez selectionnerr une autre salle ou limiter le nombre de personnes qui participent a la réunion",Toast.LENGTH_SHORT).show();
        }else{
            adapter.addMail(mAddMailEt.getText().toString());
        }

    }

    @Override
    public void onDatePass(Calendar calendar) {
        if (calendar != null){
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy - HH'h'mm");

            if (buttonClicked.getId() == R.id.addMeetingBeginingDateBtn) {
                mBeginingCalendar = calendar;
                mBeginingDateEt.setText(format.format(calendar.getTime()));
            }else if (buttonClicked.getId() == R.id.addMeetingEndingDateBtn) {
                mEndingCalendar = calendar;
                mEndingDateEt.setText(format.format(calendar.getTime()));
            }
        }
    }

    @Override
    public void onDateTimePass(Calendar calendar) {
        if (calendar != null){
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy");

            if (buttonClicked.getId() == R.id.addMeetingBeginingDateBtn) {
                mBeginingCalendar = calendar;
                mBeginingDateEt.setText(format.format(calendar.getTime()));
            }else if (buttonClicked.getId() == R.id.addMeetingEndingDateBtn) {
                mEndingCalendar = calendar;
                mEndingDateEt.setText(format.format(calendar.getTime()));
            }
        }
    }
}