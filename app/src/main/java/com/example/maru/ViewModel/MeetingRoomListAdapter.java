package com.example.maru.ViewModel;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.maru.DI.DI;
import com.example.maru.Model.MeetingRoom;
import com.example.maru.R;

import java.util.List;


public class MeetingRoomListAdapter extends BaseAdapter {
    Context context;
    List<MeetingRoom>  meetingRooms = DI.getMeetingRoomApiService().getMeetingRooms();
    LayoutInflater inflater;

    public MeetingRoomListAdapter(Context appContext){
        context = appContext;
        inflater = LayoutInflater.from(appContext);
    }

    @Override
    public int getCount() {
        return meetingRooms.size();
    }

    @Override
    public Object getItem(int position) {
        return meetingRooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return meetingRooms.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = inflater.inflate(R.layout.spinner_item_meeting_room,null);

        TextView colorTv = view.findViewById(R.id.roomColorTv);
        TextView nameTv = view.findViewById(R.id.roomNameTv);
        TextView capacityTv = view.findViewById(R.id.roomCapacityTv);

        setColor(view,colorTv,meetingRooms.get(position).getColor());
        nameTv.setText(meetingRooms.get(position).getName());
        capacityTv.setText(String.valueOf(meetingRooms.get(position).getCapacity()));

        return view;
    }

    public void setColor(View context, TextView colorTv, String color){
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(context.getContext(), R.drawable.rounded_corner);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor(color));
        colorTv.setBackground(wrappedDrawable);
    }
}
