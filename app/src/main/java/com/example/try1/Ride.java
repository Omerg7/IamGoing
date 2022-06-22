package com.example.try1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class Ride implements Serializable {
    private User driver;
    private String goingFrom, goingTo;
    public     int day, month,  hour, minute;
    private ArrayList<User> arrHitchikers;
    private String chosenRideKey;


    public Ride() {

    }

    public Ride(User driver, String goingFrom, String goingTo, int day, int month, int hour, int minute) {
        this.driver = driver;
        this.goingFrom = goingFrom;
        this.goingTo = goingTo;
        this.day = day;
        this.month = month;
        this.hour = hour;
        this.minute = minute;
        this.arrHitchikers = new ArrayList<>();

    }

    public ArrayList<User> getArrHitchikers() {
        return arrHitchikers;
    }

    public void setArrHitchikers(ArrayList<User> arrHitchikers) {
        this.arrHitchikers = arrHitchikers;
    }

    public String getChosenRideKey() {
        return chosenRideKey;
    }

    public void setChosenRideKey(String chosenRideKey) {
        this.chosenRideKey = chosenRideKey;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }


    public void setArrHitchHikers(ArrayList<User> arrHitchHikers) {
    }

    public String getGoingFrom() {
        return goingFrom;
    }

    public void setGoingFrom(String goingFrom) {
        this.goingFrom = goingFrom;
    }

    public String getGoingTo() {
        return goingTo;
    }

    public void setGoingTo(String goingTo) {
        this.goingTo = goingTo;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
