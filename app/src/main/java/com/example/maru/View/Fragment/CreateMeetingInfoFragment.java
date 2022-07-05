package com.example.maru.View.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.maru.Model.Meeting;
import com.example.maru.Model.MeetingRoom;
import com.example.maru.R;
import com.example.maru.ViewModel.AddMailRecyclerViewAdapter;
import com.example.maru.ViewModel.MeetingRoomListAdapter;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateMeetingInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateMeetingInfoFragment extends Fragment {

    public interface OnDataPass{
        public void onDataPass(String name, String creator, MeetingRoom room,List<String> mails);
    }
    private OnDataPass dataPasser;

    private ImageButton mAddBtn;
    private Button mCreateBtn;
    private EditText mMeetingNameEt, mCreatorEt, mAddMailEt;
    private Spinner mRoomChooserSp;
    private RecyclerView mMailRv;
    private CardView mAddMailCv;

    private int mMaxMail;
    private List<String> mMails = new ArrayList<>();

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
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
        mMeetingNameEt = view.findViewById(R.id.meetingNameEt);
        mCreatorEt = view.findViewById(R.id.creatorNameEt);
        mAddMailEt = view.findViewById(R.id.addMailEt);

        mRoomChooserSp = view.findViewById(R.id.meetingRoomSelectorSp);
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

        mCreateBtn = view.findViewById(R.id.createMeetingBtn);
        mCreateBtn.setOnClickListener(v -> {
            Create();
        });
        mAddBtn = view.findViewById(R.id.addMailIBtn);
        mAddBtn.setOnClickListener(v -> {
            addMail();
        });

        mMailRv = view.findViewById(R.id.mailsRv);
        mMailRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mMailRv.setAdapter(adapter);
    }

    private void Create(){
        String name,creator;
        MeetingRoom room;
        List<String> mails;
        name = mMeetingNameEt.getText().toString();
        creator = mCreatorEt.getText().toString();
        room = (MeetingRoom) mRoomChooserSp.getSelectedItem();
        mails = adapter.getListMails();
        dataPasser.onDataPass(name,creator,room,mails);
    }

    private void addMail(){
        adapter.addMail(mAddMailEt.getText().toString());
    }
}