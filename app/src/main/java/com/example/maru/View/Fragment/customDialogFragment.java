package com.example.maru.View.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.Model.MeetingRoom;
import com.example.maru.R;
import com.example.maru.ViewModel.meetingRoomRecyclerViewAdapter;

import java.util.List;

public class customDialogFragment extends DialogFragment {

    public interface OnDataPass{
        public void onDataPass(MeetingRoom room);
    }

    private OnDataPass dataPasser;

    private final List<MeetingRoom> mRooms;

    private TextView mTitleTv;
    private RecyclerView mListRv;
    private Button mSelectBtn;

    private MeetingRoom mRoom;

    public customDialogFragment(List<MeetingRoom> meetingRooms){
        mRooms = meetingRooms;
    }

    public static customDialogFragment newInstance(List<MeetingRoom> meetingRooms){
        return new customDialogFragment(meetingRooms);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_list_room,container,false);
        if (getDialog() != null && getDialog().getWindow() != null){
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitleTv = view.findViewById(R.id.filterTitleTv);
        mListRv = view.findViewById(R.id.filterListRv);
        mSelectBtn = view.findViewById(R.id.filterSelectBtn);
    }

    @Override
    public void onResume() {
        super.onResume();

        mSelectBtn.setOnClickListener(v -> selectFilter());

        mListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mListRv.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mListRv.setAdapter(new meetingRoomRecyclerViewAdapter(mRooms, this::selectRoom));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    private void selectRoom(MeetingRoom room) {
        mRoom = room;
    }

    private void selectFilter() {
        dataPasser.onDataPass(mRoom);
        dismiss();
    }
}
