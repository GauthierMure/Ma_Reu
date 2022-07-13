package com.example.maru.services.Meeting;

import android.util.Log;

import com.example.maru.Model.Meeting;

import java.util.List;

public class DummyApiServiceMeeting implements ApiServiceMeeting {

    private List<Meeting> mMeetings = DummyMeetingGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeetings() {
        return mMeetings;
    }

    @Override
    public int generateId() {
        return mMeetings.get(mMeetings.size()-1).getmId() + 1;
    }

    @Override
    public void addMeeting(Meeting meeting) {
        mMeetings.add(meeting);
    }

    @Override
    public Meeting getMeeting(int Id) {
        for(int i = 0; i < mMeetings.size(); i++){
            if(mMeetings.get(i).getmId() == Id)
                return mMeetings.get(i);
        }
        return null;
    }

    @Override
    public void deleteMeeting(int Id) {
        for(int i = 0; i < mMeetings.size(); i++){
            if(mMeetings.get(i).getmId() == Id)
                mMeetings.remove(i);
        }
    }
}
