package com.example.try1;

import android.content.Intent;

import java.util.ArrayList;

public class User{
    private String name, pass, number;
    private Ride ride;

    public User() {
    }

    public User(String name, String pass, String number) {
        this.name = name;
        this.pass = pass;
        this.number = number;

    }

    public String getName() {
    return name;
}
    public String getPass() {
        return pass;
    }
    public String getNumber() { return number;}

    public void setName(String name) {
        this.name = name;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public void setNumber(String number)
    { this.number = number;}

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public void startService(Intent my_notification) {
//        ride.getDriver().startService(my_notification);
    }
}
