package com.example.maru.services.MeetingRoom;

import com.example.maru.Model.MeetingRoom;

import java.util.List;

public class DummyApiServiceMeetingRoom implements ApiServiceMeetingRoom{
    private List<MeetingRoom> mMeetingRooms = DummyMeetingRoomGenerator.generateMeetingRoom();


    @Override
    public List<MeetingRoom> getMeetingRooms() {
        return mMeetingRooms;
    }

    @Override
    public MeetingRoom getMeetingRoom(int id) {
        for (int i = 0; i < mMeetingRooms.size(); i++){
            if (mMeetingRooms.get(i).getId() == id){
                return mMeetingRooms.get(i);
            }
        }
        return null;
    }

    @Override
    public void deleteMeetingRoom(int id) {
        for (int i = 0; i < mMeetingRooms.size(); i++){
            if (mMeetingRooms.get(i).getId() == id){
                mMeetingRooms.remove(i);
            }
        }
    }
}
