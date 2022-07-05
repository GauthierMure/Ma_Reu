package com.example.maru.services.Meeting;

import com.example.maru.DI.DI;
import com.example.maru.Model.Meeting;
import com.example.maru.Model.MeetingRoom;
import com.example.maru.services.MeetingRoom.ApiServiceMeetingRoom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DummyMeetingGenerator {

    private static final List<String> participants = Arrays.asList(
            "maxime@lamzone.com",
            "alex@lamzone.com",
            "paul@lamzone.com",
            "viviane@lamzone.com",
            "amandine@lamzone.com",
            "luc@lamzone.com"
    );

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(1, "Réunion A", "Caroline", getCalendar(0), getMeetingRoom(0), "sujet 1", participants),
            new Meeting(2,"Réunion B","Jack",getCalendar(1), getMeetingRoom(1), "sujet 2", participants),
            new Meeting(3, "Réunion C", "Chloé",getCalendar(2), getMeetingRoom(2), "sujet 3", participants)
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }

    static Calendar getCalendar(int date){
        Calendar calendar = Calendar.getInstance();
        if (date == 0)
                calendar.set(2022,4,30,14, 0);
        else if (date == 1)
                calendar.set(2022,5,1,16,0);
        else if (date == 2)
                calendar.set(2022,6,2,19,0);

        return calendar;
    }

    static MeetingRoom getMeetingRoom(int id){
        MeetingRoom room;
        if (id == 0)
            room = new MeetingRoom(0,"Meeting Room 1","#aeceb8",1,10);
        else if (id == 1)
            room = new MeetingRoom(1,"Meeting Room 2","#edd9d0",2,15);
        else if (id == 2)
            room = new MeetingRoom(3,"Meeting Room 3","#B9BBED",3,20);
        else
            room = new MeetingRoom(4,"Meeting Room 4","#C8EDB9",2,7);
        return room;
    }
}
