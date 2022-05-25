package com.example.f1ticketing.Service;

import com.example.f1ticketing.Model.Race;
import com.example.f1ticketing.Model.User;

public class Validator {

    //SINGLETON DP
    private static final Validator instance = new Validator();

    private Validator(){
    }

    public static Validator getInstance(){
        return instance;
    }

    public boolean isUserValid(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty())
            return false;
        if (user.getEmail() == null || user.getEmail().isEmpty())
            return false;
        if (! (user.getEmail().contains("@") && user.getEmail().contains(".com")))
            return false;
        return user.getPassword() != null && !user.getPassword().isEmpty();
    }

    public boolean isRaceValid(Race race) {
        if (race.getLocation() == null || race.getLocation().isEmpty())
            return false;
        else return race.getName() != null && !race.getName().isEmpty();
    }
}
