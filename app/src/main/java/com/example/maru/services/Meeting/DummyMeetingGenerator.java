package com.example.maru.services.Meeting;

import com.example.maru.Model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingGenerator {

    private static List<String> participants = Arrays.asList(
            "maxime@lamzone.com",
            "alex@lamzone.com",
            "paul@lamzone.com",
            "viviane@lamzone.com",
            "amandine@lamzone.com",
            "luc@lamzone.com"
    );

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(1, "Réunion A", "Caroline", "#4E92DF", "14h00", "Salle 1", "sujet 1", participants),
            new Meeting(2,"Réunion B","Jack", "#6200EE","16h00", "Salle 2", "sujet 2", participants),
            new Meeting(3, "Réunion C", "Chloé", "#03DAC5","19h00","Salle 3", "sujet 3", participants)
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
