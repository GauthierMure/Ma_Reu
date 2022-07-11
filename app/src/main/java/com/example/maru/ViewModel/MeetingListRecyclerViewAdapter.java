package com.example.maru.ViewModel;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.DI.DI;
import com.example.maru.Model.Meeting;
import com.example.maru.R;
import com.example.maru.View.Activity.HomePageActivity;
import com.example.maru.View.Activity.MeetingInfoActivity;
import com.example.maru.View.Fragment.MeetingInfoFragment;
import com.example.maru.services.Meeting.ApiServiceMeeting;

import java.text.SimpleDateFormat;
import java.util.List;

public class MeetingListRecyclerViewAdapter extends RecyclerView.Adapter<MeetingListRecyclerViewAdapter.ViewHolder> {

    private final ApiServiceMeeting mApiserviceMeeting = DI.getMeetingApiService();
    private List<Meeting> mMeetings;
    private static HomePageActivity mActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private int id;
        private final ImageButton deleteBtn;
        private final TextView titleTv, participantsTv;
        private final TextView colorTv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                FragmentManager fm = mActivity.getSupportFragmentManager();
                MeetingInfoFragment frag = MeetingInfoFragment.newInstance(id);
                int Tag;
                if (mActivity.getResources().getBoolean(R.bool.isTablet))
                    Tag = HomePageActivity.TAG_OTHER_FRAGMENT;
                else
                    Tag = HomePageActivity.TAG_LIST_FRAGMENT;
                HomePageActivity.setFragment(fm,frag,Tag);
            });
            deleteBtn = itemView.findViewById(R.id.btnDeleteItem);
            titleTv = itemView.findViewById(R.id.tvInfoMeeting);
            participantsTv = itemView.findViewById(R.id.tvListParticipants);
            colorTv = itemView.findViewById(R.id.ivColorMeeting);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public ImageButton getDeleteBtn() {
            return deleteBtn;
        }

        public TextView getTitleTv() {
            return titleTv;
        }

        public TextView getParticipantsTv() {
            return participantsTv;
        }

        public TextView getColorTv() {
            return colorTv;
        }

        public void setColor(String color){
            Drawable unwrappedDrawable = AppCompatResources.getDrawable(itemView.getContext(), R.drawable.rounded_corner);
            Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
            DrawableCompat.setTint(wrappedDrawable, Color.parseColor(color));
            colorTv.setBackground(wrappedDrawable);
        }
    }

    public MeetingListRecyclerViewAdapter(HomePageActivity activity){
        mActivity = activity;
        mMeetings = mApiserviceMeeting.getMeetings();
    }

    public MeetingListRecyclerViewAdapter(HomePageActivity activity, List<Meeting> meeting){
        mActivity = activity;
        mMeetings = meeting;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meeting_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingListRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.setId(mMeetings.get(position).getmId());
        holder.setColor(mMeetings.get(position).getColor());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH'h'mm");
        String title = mMeetings.get(position).getTitle()+" - "+format.format(mMeetings.get(position).getBeginingDate().getTime())+" - "+mMeetings.get(position).getCreator();
        holder.getTitleTv().setText(title);
        String participants = "";
        for(int i = 0;i<mMeetings.get(position).getParticipants().size();i++){
            if(participants.isEmpty())
                participants = mMeetings.get(position).getParticipants().get(i);
            else
                participants = " - "+mMeetings.get(position).getParticipants().get(i);
        }
        holder.getParticipantsTv().setText(participants);
        holder.getDeleteBtn().setOnClickListener(v -> {
            mApiserviceMeeting.deleteMeeting(mMeetings.get(position).getmId());
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,mMeetings.size());
        });
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public List<Meeting> getMeetings(){
        return mMeetings;
    }
}
