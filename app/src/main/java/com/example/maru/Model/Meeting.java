package com.example.maru.Model;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.List;

public class Meeting {

    private int mId;
    private String mTitle;
    private String mCreator;
    private String mColor;
    private Calendar mBeginingDate;
    private Calendar mEndingDate;
    private MeetingRoom mRoom;
    private String mTopic;
    private List<String> mParticipants;
    private boolean isFullDay;

    public Meeting (int id){
        this.mId = id;
    }

    public Meeting(int Id, String title, String creator, Calendar Date, MeetingRoom Room, String Topic, List<String> Participants) {
        this.mId = Id;
        this.isFullDay = true;
        this.mTitle = title;
        this.mCreator = creator;
        this.mColor = Room.getColor();
        this.mBeginingDate = Date;
        this.mEndingDate = Date;
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

    public Calendar getBeginingDate() {
        return mBeginingDate;
    }

    public void setBeginingDate(Calendar mDate) {
        this.mBeginingDate = mDate;
    }

    public Calendar getEndingDate() {
        return mEndingDate;
    }

    public void setEndingDate(Calendar mEndingDate) {
        this.mEndingDate = mEndingDate;
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

    public boolean isFullDay() {
        return isFullDay;
    }

    public void setFullDay(boolean fullDay) {
        isFullDay = fullDay;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Meeting other = (Meeting) obj;


        return this.mId == other.mId;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mCreator='" + mCreator + '\'' +
                ", mColor='" + mColor + '\'' +
                ", mBeginingDate=" + mBeginingDate +
                ", mEndingDate=" + mEndingDate +
                ", mRoom=" + mRoom +
                ", mTopic='" + mTopic + '\'' +
                ", mParticipants=" + mParticipants +
                ", isFullDay=" + isFullDay +
                '}';
    }
}
