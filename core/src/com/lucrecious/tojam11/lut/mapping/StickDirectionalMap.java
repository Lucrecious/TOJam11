package com.lucrecious.tojam11.lut.mapping;

import com.lucrecious.tojam11.lut.input.Joysticks;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class StickDirectionalMap extends DirectionalMap {

    private final int stick;
    private final Integer index;

    public StickDirectionalMap(String name, float threshold, String stick, Integer index) {
        super(name, threshold, Controller.Type.Joystick);
        this.stick = stick == "right" ? 1 : 0;
        this.index = index;
    }

    @Override
    public Vector2f vector() {
        Vector2f vec;
        if (stick == 0) {
            vec =  new Vector2f(Joysticks.main.get(index).stick("ls-h"), -Joysticks.main.get(index).stick("ls-v"));
        } else {
            vec =  new Vector2f(Joysticks.main.get(index).stick("rs-h"), -Joysticks.main.get(index).stick("rs-v"));
        }

        if (vec.length() >= threshold()) {
            return vec;
        }

        return new Vector2f(0, 0);
    }
}
