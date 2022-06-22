package com.example.maru.services.Meeting;

import com.example.maru.Model.Meeting;

import java.util.List;

public interface ApiServiceMeeting {

    public List<Meeting> getMeetings();

    public Meeting createNewMeeting();

    public void saveMeeting(Meeting meeting);

    public Meeting getSavedMeeting();

    public void addMeeting();

    public Meeting getMeeting(int Id);

    public void deleteMeeting(int Id);
}
