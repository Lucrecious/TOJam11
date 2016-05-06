package com.lucrecious.tojam11.lut.mapping;

import com.lucrecious.tojam11.lut.Math;
import com.nilunder.bdx.Bdx;

import javax.vecmath.Vector2f;

public class ButtonDirectionalMap extends DirectionalMap{

    private final String up;
    private final String down;
    private final String left;
    private final String right;

    private final Integer jIndex;

    public ButtonDirectionalMap(String name, float threshold,
                                String up, String down, String left, String right) {
        super(name, threshold, Controller.Type.Keyboard);
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.jIndex = null;
    }

    public ButtonDirectionalMap(String name, float threshold, int jIndex,
                                String up, String down, String left, String right) {
        super(name, threshold, Controller.Type.Joystick);
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.jIndex = jIndex;
    }

    @Override
    public Vector2f vector() {
        boolean upPressed = Controller.buttonAction(up, type, Status.Hit, jIndex)
                || Controller.buttonAction(up, type, Status.Down, jIndex);
        boolean downPressed = Controller.buttonAction(down, type, Status.Hit, jIndex)
                || Controller.buttonAction(down, type, Status.Down, jIndex);
        boolean rightPressed = Controller.buttonAction(right, type, Status.Hit, jIndex)
                || Controller.buttonAction(right, type, Status.Down, jIndex);
        boolean leftPressed = Controller.buttonAction(left, type, Status.Hit, jIndex)
                || Controller.buttonAction(left, type, Status.Down, jIndex);

        Vector2f v  = new Vector2f(
                Math.boolToInt(rightPressed) - Math.boolToInt(leftPressed),
                Math.boolToInt(upPressed) - Math.boolToInt(downPressed)
        );


        if (v.length() != 0) {
            v.normalize();
        }

        if (v.length() < threshold()) {
            return new Vector2f(0, 0);
        }

        return v;
    }
}
