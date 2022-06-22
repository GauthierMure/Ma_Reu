package com.example.maru.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.maru.DI.DI;
import com.example.maru.Model.Meeting;
import com.example.maru.Model.MeetingRoom;
import com.example.maru.R;
import com.example.maru.ViewModel.AddMailRecyclerViewAdapter;
import com.example.maru.ViewModel.MeetingRoomListAdapter;
import com.example.maru.services.Meeting.ApiServiceMeeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddMeetingActivity extends AppCompatActivity {

    private ImageButton mReturnBtn, mAddBtn;
    private EditText mMeetingNameEt, mCreatorEt, mAddMailEt;
    private Spinner mRoomChooserSp;
    private RecyclerView mMailRv;
    private CardView mAddMailCv;

    private int mMaxMail;
    private List<String> mMails = new ArrayList<>();

    private ApiServiceMeeting Api = DI.getMeetingApiService();
    private Meeting mMeeting;

    private AddMailRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_add_meeting);
        init();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("MeetingName", mMeetingNameEt.getText().toString());
        outState.putString("CreatorName", mCreatorEt.getText().toString());
        outState.putInt("SelectedRoom", mRoomChooserSp.getSelectedItemPosition());
        outState.putStringArrayList("ListMail",(ArrayList<String>) mMails);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mMeetingNameEt.setText(savedInstanceState.getString("MeetingName"));
        mCreatorEt.setText(savedInstanceState.getString("CreatorName"));
        mRoomChooserSp.setSelection(savedInstanceState.getInt("SelectedRoom"));
        mMails = savedInstanceState.getStringArrayList("ListMail");
    }

    private void init(){
        mAddMailCv = findViewById(R.id.addMailCv);
        mMeetingNameEt = findViewById(R.id.meetingNameEt);
        mCreatorEt = findViewById(R.id.creatorNameEt);
        mAddMailEt = findViewById(R.id.addMailEt);
        mRoomChooserSp = findViewById(R.id.meetingRoomSelectorSp);
        setupSpinner();
        mMailRv = findViewById(R.id.mailsRv);
        setupRecyclerView();
        mReturnBtn = findViewById(R.id.iBtnAddMeetingReturn);

        mReturnBtn.setOnClickListener(v -> finish());
        mAddBtn = findViewById(R.id.addMailIBtn);
        mAddBtn.setOnClickListener(v -> {
            addMail();
        });
        mMeeting = Api.createNewMeeting();
    }

    private void setupSpinner(){
        List<MeetingRoom> meetingRooms = DI.getMeetingRoomApiService().getMeetingRooms();
        MeetingRoomListAdapter adapter = new MeetingRoomListAdapter(this);
        mRoomChooserSp.setAdapter(adapter);
        mRoomChooserSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MeetingRoom room = (MeetingRoom) adapter.getItem(position);
                mMaxMail = room.getCapacity();
                if (mMails.size() < mMaxMail){
                    mAddMailCv.setVisibility(View.VISIBLE);
                }
                mMeeting.setRoom(room.getName());
                mMeeting.setColor(room.getColor());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupRecyclerView(){
        //Toast.makeText(this, "Setup RecyclerView adapter",Toast.LENGTH_SHORT).show();
        adapter = new AddMailRecyclerViewAdapter(mMails, mMaxMail);
        mMailRv.setLayoutManager(new LinearLayoutManager(this));
        mMailRv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mMailRv.setAdapter(adapter);
    }

    private void addMail(){
        String mail = mAddMailEt.getText().toString();
        mMails.add(mail);
        adapter.notifyItemInserted(adapter.getItemCount());
        adapter.notifyItemRangeChanged(0,adapter.getItemCount());
        //mAddMailEt.setText("");
        if (mMails.size() == mMaxMail - 1){
            mAddMailCv.setVisibility(View.INVISIBLE);
        }
    }

    private void saveMeeting(){
        mMeeting.setTitle(mMeetingNameEt.getText().toString());
        mMeeting.setCreator(mCreatorEt.getText().toString());
        //mMeeting.
    }
}