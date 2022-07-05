package com.example.maru.services.Meeting;

import com.example.maru.Model.Meeting;

import java.util.List;

public interface ApiServiceMeeting {

    public List<Meeting> getMeetings();

    public int generateId();

    public void addMeeting(Meeting meeting);

    public Meeting getMeeting(int Id);

    public void deleteMeeting(int Id);
}
