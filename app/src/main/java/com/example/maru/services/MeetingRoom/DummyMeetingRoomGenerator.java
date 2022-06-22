package com.example.maru.services.MeetingRoom;

import com.example.maru.Model.MeetingRoom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingRoomGenerator {

    public static List<MeetingRoom> DUMMY_MEETING_ROOM = Arrays.asList(
            new MeetingRoom(0,"Meeting Room 1","#aeceb8",1,10),
            new MeetingRoom(1,"Meeting Room 2","#edd9d0",2,15),
            new MeetingRoom(3,"Meeting Room 3","#B9BBED",3,20),
            new MeetingRoom(4,"Meeting Room 4","#C8EDB9",2,7)
    );

    public static List<MeetingRoom> generateMeetingRoom (){
        return new ArrayList<>(DUMMY_MEETING_ROOM);
    }
}
