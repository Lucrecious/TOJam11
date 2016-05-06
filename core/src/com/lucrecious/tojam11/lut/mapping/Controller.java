package com.lucrecious.tojam11.lut.mapping;

import com.lucrecious.tojam11.lut.input.Joysticks;
import com.nilunder.bdx.Bdx;

import javax.vecmath.Vector2f;

public abstract class Controller {
    public enum Type {
        Keyboard,
        Joystick
    }

    public abstract boolean buttonDown(String name);
    public abstract boolean buttonHit(String name);
    public abstract boolean buttonUp(String name);
    public abstract Vector2f vector(String name);

    public static boolean buttonAction(String button, Type type, Status status, Integer jIndex) {
        if (status == Status.Down) {
            return type == Type.Keyboard ?
                    Bdx.keyboard.keyDown(button) :
                    Joysticks.main.get(jIndex).status(button) == Status.Down;
        } else if (status == Status.Hit) {
            return type == Type.Keyboard ?
                    Bdx.keyboard.keyHit(button) :
                    Joysticks.main.get(jIndex).status(button) == Status.Hit;
        } else {
            return type == Type.Keyboard ?
                    Bdx.keyboard.keyUp(button) :
                    Joysticks.main.get(jIndex).status(button) == Status.Up;
        }
    }

    public static boolean buttonAction(String[] buttons, Type type, Status status, Integer jIndex) {

        boolean result;

        if (status != Status.Up) {
            result = false;

            for (String b : buttons) {
                result = result || buttonAction(b, type, status, jIndex);
            }

            return result;
        } else {
            result = true;

            for (String b : buttons) {
                result = result && buttonAction(b, type, status, jIndex);
            }

            return result;
        }
    }
}
