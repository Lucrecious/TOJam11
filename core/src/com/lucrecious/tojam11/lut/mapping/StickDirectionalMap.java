package com.lucrecious.tojam11.lut.mapping;

import com.lucrecious.tojam11.lut.input.Joysticks;

import javax.vecmath.Vector2f;

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
        if (stick == 0) {
            return new Vector2f(Joysticks.main.get(index).stick("ls-h"), -Joysticks.main.get(index).stick("ls-v"));
        }

        return new Vector2f(Joysticks.main.get(index).stick("rs-h"), -Joysticks.main.get(index).stick("rs-v"));
    }
}
