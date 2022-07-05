package com.example.maru.View.Fragment;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.maru.DI.DI;
import com.example.maru.Model.Meeting;
import com.example.maru.R;
import com.example.maru.ViewModel.MailListAdapter;
import com.example.maru.services.Meeting.ApiServiceMeeting;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;


public class MeetingInfoFragment extends Fragment {

    private ConstraintLayout mActionBar;

    private MaterialCardView mCardView;

    private ImageButton mReturnBtn;
    private RadioButton mFullDayRBtn;

    private TextView mFragmentTitleTv, mMeetingTitleTv, mMeetingBeginingTv, mMeetingEndingTv, mRoomNameTv, mRoomFloorTv, mRoomCapacityTv, mSeparator1, mSeparator2, mSeparator3;

    private RecyclerView mParticipantRv;

    private ApiServiceMeeting api = DI.getMeetingApiService();
    private Meeting mMeeting;
    private String mColor;

    public MeetingInfoFragment() {
    }

    public static MeetingInfoFragment newInstance(int id) {
        MeetingInfoFragment fragment = new MeetingInfoFragment();
        Bundle arg = new Bundle();
        arg.putInt("id",id);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if (arg != null){
            int id  = arg.getInt("id",0);
            mMeeting = api.getMeeting(id);
            Log.i("meeting",mMeeting.getTitle());
        }else
            mMeeting = api.getMeeting(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meeting_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActionBar = view.findViewById(R.id.meetingInfoActionBar);
        mCardView = view.findViewById(R.id.meetingInfoCardView);
        mReturnBtn = view.findViewById(R.id.iBtnAddMeetingReturn);
        mFragmentTitleTv = view.findViewById(R.id.actionBarActivityTitleTv);
        mFullDayRBtn = view.findViewById(R.id.meetingInfoFullDayRb);
        mMeetingTitleTv = view.findViewById(R.id.meetingInfoNameTv);
        mMeetingBeginingTv = view.findViewById(R.id.meetingInfoStartDateTv);
        mMeetingEndingTv = view.findViewById(R.id.meetingInfoEndDateTv);
        mRoomNameTv = view.findViewById(R.id.meetingInfoRoomNameTv);
        mRoomFloorTv = view.findViewById(R.id.meetingInfoRoomFloorTv);
        mRoomCapacityTv = view.findViewById(R.id.meetingInfoRoomCapacityTv);
        mParticipantRv = view.findViewById(R.id.meetingInfoParticipantRv);
        mSeparator1 = view.findViewById(R.id.meetingInfoSeparator1);
        mSeparator2 = view.findViewById(R.id.meetingInfoSeparator2);
        mSeparator3 = view.findViewById(R.id.meetingInfoSeparator3);
        init();
    }

    private void init() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH'h'mm");
        mFragmentTitleTv.setText("Meeting Informations");
        mMeetingTitleTv.setText(mMeeting.getTitle());
        mMeetingBeginingTv.setText(dateFormat.format(mMeeting.getDate().getTime()));
        mMeetingEndingTv.setText("pas encore impléenté");
        mRoomNameTv.setText(mMeeting.getRoom().getName());
        mRoomFloorTv.setText(String.valueOf(mMeeting.getRoom().getFloor()));
        mRoomCapacityTv.setText(String.valueOf(mMeeting.getRoom().getCapacity()));
        mColor = mMeeting.getColor();

        mActionBar.setBackgroundColor(Color.parseColor(mColor));
        mCardView.setStrokeColor(Color.parseColor(mColor));
        mSeparator1.setBackgroundColor(Color.parseColor(mColor));
        mSeparator2.setBackgroundColor(Color.parseColor(mColor));
        mSeparator3.setBackgroundColor(Color.parseColor(mColor));

        mParticipantRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mParticipantRv.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mParticipantRv.setAdapter(new MailListAdapter(mMeeting.getParticipants()));

        for (int i = 0; i < mMeeting.getParticipants().size(); i++){
            Log.i("Participants", mMeeting.getParticipants().get(i));
        }

        mReturnBtn.setOnClickListener(v -> getActivity().finish());
    }
}