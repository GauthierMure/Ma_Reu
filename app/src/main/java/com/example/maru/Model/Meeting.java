package com.example.maru.Model;

import android.graphics.Color;

import java.util.Calendar;
import java.util.List;

public class Meeting {

    private int mId;
    private String mTitle;
    private String mCreator;
    private String mColor;
    private Calendar mDate;
    private MeetingRoom mRoom;
    private String mTopic;
    private List<String> mParticipants;

    public Meeting (int id){
        this.mId = id;
    }

    public Meeting(int Id, String title, String creator, Calendar Date, MeetingRoom Room, String Topic, List<String> Participants) {
        this.mId = Id;
        this.mTitle = title;
        this.mCreator = creator;
        this.mColor = Room.getColor();
        this.mDate = Date;
        this.mRoom = Room;
        this.mTopic = Topic;
        this.mParticipants = Participants;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getCreator() {
        return mCreator;
    }

    public void setCreator(String mCreator) {
        this.mCreator = mCreator;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String mColor) {
        this.mColor = mColor;
    }

    public Calendar getDate() {
        return mDate;
    }

    public void setDate(Calendar mDate) {
        this.mDate = mDate;
    }

    public MeetingRoom getRoom() {
        return mRoom;
    }

    public void setRoom(MeetingRoom mRoom) {
        this.mRoom = mRoom;
    }

    public String getTopic() {
        return mTopic;
    }

    public void setTopic(String mTopic) {
        this.mTopic = mTopic;
    }

    public List<String> getParticipants() {
        return mParticipants;
    }

    public void setParticipants(List<String> mParticipants) {
        this.mParticipants = mParticipants;
    }
}
