package com.lucrecious.tojam11.lut.mapping;

import javafx.util.Pair;

import javax.vecmath.Vector2f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class AIController extends Controller {

    public AIController() {
        statuses = new HashMap<String, Pair<Status, Status>>();
        pressed = new HashSet<String>();
        sticks = new HashMap<String, Vector2f>();
    }

    private HashMap<String, Pair<Status, Status>> statuses;
    private HashSet<String> pressed;
    private HashMap<String, Vector2f> sticks;

    public void update() {
        for (String key : new ArrayList<String>(statuses.keySet())) {
            Pair<Status, Status> pair = statuses.get(key);
            if (pressed.contains(key)) {
                if (pair.getValue() == Status.Up) {
                    statuses.put(key, new Pair<Status, Status>(Status.Up, Status.Hit));
                } else {
                    statuses.put(key, new Pair<Status, Status>(Status.Hit, Status.Down));
                }

                pressed.remove(key);
            } else {
                statuses.put(key, new Pair<Status, Status>(pair.getValue(), Status.Up));
            }
        }

        for (String key : pressed) {
            statuses.put(key, new Pair<Status, Status>(Status.Up, Status.Hit));
        }

        pressed.clear();
    }

    public void press(String key) {
        if (!pressed.contains(key)) {
            pressed.add(key);
        }
    }

    public void push(String stick, Vector2f vec) {
        sticks.put(stick, vec);
    }

    @Override
    public boolean buttonDown(String name) {
        if (statuses.containsKey(name) && statuses.get(name).getValue() == Status.Down) {
            return true;
        }

        return false;
    }

    @Override
    public boolean buttonHit(String name) {
        if (statuses.containsKey(name) && statuses.get(name).getValue() == Status.Hit) {
            return true;
        }

        return false;
    }

    @Override
    public boolean buttonUp(String name) {
        if (!statuses.containsKey(name) || statuses.get(name).getValue() == Status.Up) {
            return true;
        }

        return false;
    }

    @Override
    public Vector2f vector(String name) {
        if (sticks.containsKey(name)) {
            return sticks.get(name);
        }

        return new Vector2f(0, 0);
    }
}
