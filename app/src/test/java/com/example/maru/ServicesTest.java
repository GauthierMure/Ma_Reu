package com.example.maru;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


import com.example.maru.DI.DI;
import com.example.maru.Model.Meeting;
import com.example.maru.Model.MeetingRoom;
import com.example.maru.services.Meeting.ApiServiceMeeting;
import com.example.maru.services.Meeting.DummyMeetingGenerator;
import com.example.maru.services.MeetingRoom.ApiServiceMeetingRoom;
import com.example.maru.services.MeetingRoom.DummyMeetingRoomGenerator;

import java.util.List;


public class ServicesTest {
    private ApiServiceMeeting meetingService;
    private ApiServiceMeetingRoom meetingRoomService;

    @Before
    public void setup(){
        meetingService = DI.getMeetingApiService();
        meetingRoomService = DI.getMeetingRoomApiService();
    }

    @Test
    public void getMeetingsWithSuccess(){
        List<Meeting> meetings = meetingService.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.generateMeetings();

        if (meetings.size() == expectedMeetings.size()){
            for (int i = 0; i < meetings.size(); i++){
                assertEquals(expectedMeetings.get(i).getmId(),meetings.get(i).getmId()-1);
            }
        }
    }

    @Test
    public void getMeetingWithIdWithSuccess(){
        // Change Id value for testing other Meeting
        int ID = 2;

        Meeting meeting = meetingService.getMeeting(ID);
        List<Meeting> expectedMeetings = DummyMeetingGenerator.generateMeetings();
        assertEquals(meeting.getmId(), expectedMeetings.get(ID-1).getmId());
    }

    @Test
    public void deleteMeetingWithSuccess(){
        int ID = 1;

        Meeting meeting = meetingService.getMeeting(ID);
        meetingService.deleteMeeting(ID);
        assertFalse(meetingService.getMeetings().contains(meeting));
    }

    @Test
    public void generateNewIdWithSuccess(){
        int lastId = meetingService.getMeetings().get(meetingService.getMeetings().size()-1).getmId();
        int generatedID = meetingService.generateId();
        assertEquals(generatedID, lastId + 1);
    }

    @Test
    public void addMeetingWithSuccess(){
        Meeting newMeeting = new Meeting(meetingService.generateId());
        meetingService.addMeeting(newMeeting);
        assertTrue(meetingService.getMeetings().contains(newMeeting));
    }

    @Test
    public void getMeetingRoomsWithSuccess(){
        List<MeetingRoom> meetingRooms = meetingRoomService.getMeetingRooms();
        List<MeetingRoom> expectedMeetingRooms = DummyMeetingRoomGenerator.generateMeetingRoom();
        if (meetingRooms.size() == expectedMeetingRooms.size()){
            for(int i = 0; i < meetingRooms.size(); i++){
                assertEquals(expectedMeetingRooms.get(i).getId(),meetingRooms.get(i).getId()-1);
            }
        }
    }

    @Test
    public void getMeetingRoomWithIdWithSuccess(){
        int ID = 0;

        MeetingRoom meetingRoom = meetingRoomService.getMeetingRoom(ID);
        List<MeetingRoom> expectedMeetingRooms = DummyMeetingRoomGenerator.generateMeetingRoom();
        assertEquals(meetingRoom.getId(),expectedMeetingRooms.get(ID).getId());
    }

    @Test
    public void deleteMeetingRoomWithSuccess(){
        int ID = 1;

        MeetingRoom meetingRoom = meetingRoomService.getMeetingRoom(ID);
        meetingRoomService.deleteMeetingRoom(ID);
        assertFalse(meetingRoomService.getMeetingRooms().contains(meetingRoom));
    }
}