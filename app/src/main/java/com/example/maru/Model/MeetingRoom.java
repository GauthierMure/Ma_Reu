package com.example.maru.Model;

public class MeetingRoom {
    private int id;
    private String name;
    private String color;
    private int floor;
    private int accomodationCapacity;

    public MeetingRoom(int id, String name, String color, int floor, int accomodationCapacity) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.floor = floor;
        this.accomodationCapacity = accomodationCapacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getCapacity() {
        return accomodationCapacity;
    }

    public void setCapacity(int Capacity) {
        this.accomodationCapacity = Capacity;
    }
}
