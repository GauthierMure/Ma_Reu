package com.example.maru.services.Meeting;

import com.example.maru.Model.Meeting;

import java.util.List;

public class DummyApiServiceMeeting implements ApiServiceMeeting {

    private List<Meeting> mMeetings = DummyMeetingGenerator.generateMeetings();
    private Meeting tempMeeting;

    @Override
    public List<Meeting> getMeetings() {
        return mMeetings;
    }

    @Override
    public Meeting createNewMeeting() {
        tempMeeting = new Meeting(mMeetings.size());
        return tempMeeting;
    }

    @Override
    public void saveMeeting(Meeting meeting) {
        tempMeeting = meeting;
    }

    @Override
    public Meeting getSavedMeeting(){
        return tempMeeting;
    }

    @Override
    public void addMeeting() {
        mMeetings.add(tempMeeting);
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
