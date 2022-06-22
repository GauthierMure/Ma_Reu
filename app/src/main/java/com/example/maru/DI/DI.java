package com.example.maru.DI;

import com.example.maru.services.Meeting.ApiServiceMeeting;
import com.example.maru.services.MeetingRoom.ApiServiceMeetingRoom;
import com.example.maru.services.MeetingRoom.DummyApiServiceMeetingRoom;
import com.example.maru.services.Meeting.DummyApiServiceMeeting;

public class DI {

    private static ApiServiceMeeting mMeetingService = new DummyApiServiceMeeting();
    private static ApiServiceMeetingRoom mMeetingRoomService = new DummyApiServiceMeetingRoom();

    public static ApiServiceMeeting getMeetingApiService(){return mMeetingService;}

    public static ApiServiceMeetingRoom getMeetingRoomApiService(){return mMeetingRoomService;}

    public static ApiServiceMeeting getNewInstanceMeetingApiService(){return new DummyApiServiceMeeting();}

    public static ApiServiceMeetingRoom getNewInstanceMeetinRoomApiService(){return new DummyApiServiceMeetingRoom();}
}
