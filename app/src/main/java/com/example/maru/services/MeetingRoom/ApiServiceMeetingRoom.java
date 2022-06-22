package com.example.maru.services.MeetingRoom;

import com.example.maru.Model.MeetingRoom;

import java.util.List;

public interface ApiServiceMeetingRoom {

    public List<MeetingRoom> getMeetingRooms();

    public MeetingRoom getMeetingRoom(int id);

    public void deleteMeetingRoom(int id);
}
