package com.lucrecious.tojam11.lut.mapping;

import javax.vecmath.Vector2f;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerController extends Controller {
    private HashMap<String, ArrayList<ButtonMap>> buttonMaps;
    private HashMap<String, ArrayList<DirectionalMap>> directionalMaps;

    public PlayerController() {
        buttonMaps = new HashMap<String, ArrayList<ButtonMap>>();
        directionalMaps = new HashMap<String, ArrayList<DirectionalMap>>();
    }

    public PlayerController add(ButtonMap buttonMap) {
        if (!buttonMaps.containsKey(buttonMap.name())) {
            buttonMaps.put(buttonMap.name(), new ArrayList<ButtonMap>());
        }

        buttonMaps.get(buttonMap.name()).add(buttonMap);

        return this;
    }

    public PlayerController add(DirectionalMap directionalMap) {
        if (!directionalMaps.containsKey(directionalMap.name())) {
            directionalMaps.put(directionalMap.name(), new ArrayList<DirectionalMap>());
        }

        directionalMaps.get(directionalMap.name()).add(directionalMap);

        return this;
    }

    @Override
    public boolean buttonDown(String button) {
        ArrayList<ButtonMap> maps = buttonMaps.get(button);

        boolean result = false;

        for (ButtonMap map : maps) {
            result = result || map.down();
        }

        return result;
    }

    @Override
    public boolean buttonHit(String button) {
        ArrayList<ButtonMap> maps = buttonMaps.get(button);

        boolean result = false;

        for (ButtonMap map : maps) {
            result = result || map.hit();
        }

        return result;
    }

    @Override
    public boolean buttonUp(String button) {
        ArrayList<ButtonMap> maps = buttonMaps.get(button);

        boolean result = true;

        for (ButtonMap map : maps) {
            result = result && map.up();
        }

        return result;
    }

    @Override
    public Vector2f vector(String directional) {
        Vector2f summation = new Vector2f();
        int count = 0;

        for (DirectionalMap map : directionalMaps.get(directional)) {
            summation = summation.plus(map.vector());
            count++;
        }

        if (count != 0) {
            summation.mul(1 / count);
        }

        return summation;
    }

}
