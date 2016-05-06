package com.lucrecious.tojam11.lut.input;

import com.badlogic.gdx.controllers.Controller;
import com.lucrecious.tojam11.lut.input.profiles.Profile;
import com.lucrecious.tojam11.lut.mapping.Status;
import javafx.util.Pair;

import java.util.HashMap;

public class Joystick {
    public static final Joystick dead = new DeadJoystick();


    public final Profile profile;
    public Joystick(Profile profile) {
        this.profile = profile;
        statuses = new HashMap<String, Pair<Status, Status>>();
        sticks = new HashMap<String, Float>();
    }


    public Status status(String buttonName) {
        Status status = Status.Up;
        if (statuses.containsKey(buttonName)) {
            Pair<Status, Status> pair =  statuses.get(buttonName);
            status = pair.getValue();
            if (status == Status.Hit) {
                statuses.put(buttonName, new Pair<Status, Status>(Status.Hit, Status.Down));
            }
        }

        return status;
    }

    public float stick(String stickName) {
        if (sticks.containsKey(stickName)) {
            return sticks.get(stickName);
        }

        return 0.0f;
    }

    protected void addOrUpdateButtonStatus(String buttonName, Status status) {
        statuses.put(buttonName, new Pair<Status, Status>(status(buttonName), status));
    }

    protected HashMap<String, Pair<Status, Status>> statuses;
    protected HashMap<String, Float> sticks;

    private static class DeadJoystick extends Joystick{
        public DeadJoystick() {
            super(null);
        }

        @Override
        public Status status(String buttonName) {
            return Status.Up;
        }

        @Override
        public float stick(String stickName) {
            return 0.0f;
        }
    }

}
