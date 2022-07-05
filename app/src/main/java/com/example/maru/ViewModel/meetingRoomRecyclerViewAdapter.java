package com.example.maru.ViewModel;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.Model.MeetingRoom;
import com.example.maru.R;

import java.util.List;

public class meetingRoomRecyclerViewAdapter extends RecyclerView.Adapter<meetingRoomRecyclerViewAdapter.ViewHolder> {

    private List<MeetingRoom> mRooms;
    private onClickListener mListener;

    public meetingRoomRecyclerViewAdapter (List<MeetingRoom> meetingRooms, onClickListener listener){
        mRooms = meetingRooms;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item_meeting_room,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setId(mRooms.get(position).getId());
        holder.getNameTv().setText(mRooms.get(position).getName());
        setColor(holder.getColorTv(),mRooms.get(position).getColor());
        holder.getCapacityTv().setText(String.valueOf(mRooms.get(position).getCapacity()));
        holder.itemView.setOnClickListener(view -> {
            mListener.onClick(mRooms.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return mRooms.size();
    }

    public void setColor(TextView colorTv, String color){
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(colorTv.getContext(), R.drawable.rounded_corner);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor(color));
        colorTv.setBackground(wrappedDrawable);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private int id;
        private TextView colorTv, nameTv, capacityTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            colorTv = itemView.findViewById(R.id.roomColorTv);
            nameTv = itemView.findViewById(R.id.roomNameTv);
            capacityTv = itemView.findViewById(R.id.roomCapacityTv);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public TextView getColorTv() {
            return colorTv;
        }

        public void setColorTv(TextView colorTv) {
            this.colorTv = colorTv;
        }

        public TextView getNameTv() {
            return nameTv;
        }

        public void setNameTv(TextView nameTv) {
            this.nameTv = nameTv;
        }

        public TextView getCapacityTv() {
            return capacityTv;
        }

        public void setCapacityTv(TextView capacityTv) {
            this.capacityTv = capacityTv;
        }
    }

    public interface onClickListener{
        void onClick(MeetingRoom meetingRoom);
    }
}
