package com.lucrecious.tojam11.lut.input;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.lucrecious.tojam11.lut.input.profiles.Profile;
import com.lucrecious.tojam11.lut.mapping.Status;
import com.nilunder.bdx.Bdx;

import java.util.ArrayList;

// A much simplifed version of the gamepad class
public class Joysticks {

    public static Joysticks main = new Joysticks();

    public Joysticks() {
        controllers = new ArrayList<Joystick>();
        init();
    }

    public Joystick get(Integer index) {
        if (index == null) {
            return Joystick.dead;
        }

        return controllers.get(index);
    }

    private void init() {
        for (Controller c : Controllers.getControllers()) {
            Joystick joystick;
            if (c.getName().toLowerCase().contains("xbox")) {
                controllers.add(joystick = new Joystick(Profile.Xbox));
            } else if (c.getName().toLowerCase().contains("wireless controller")) {
                controllers.add(joystick = new Joystick(Profile.PS4));
            } else {
                controllers.add(joystick = new Joystick(Profile.Unknown));
            }

            c.addListener(new GeneralListener(joystick));
        }
    }


    private ArrayList<Joystick> controllers;

    public static class GeneralListener implements ControllerListener {

        private Joystick joystick;

        public GeneralListener(Joystick joystick) {
            this.joystick = joystick;
        }

        @Override
        public void connected(Controller controller) {
            Bdx.profiler.props.put("connected", controller.getName());
        }

        @Override
        public void disconnected(Controller controller) {
            Bdx.profiler.props.put("disconnected", controller.getName());
        }

        @Override
        public boolean buttonDown(Controller controller, int buttonCode) {
            String buttonName = joystick.profile.buttonName(buttonCode);
            joystick.addOrUpdateButtonStatus(buttonName, Status.Hit);
            return true;
        }

        @Override
        public boolean buttonUp(Controller controller, int buttonCode) {
            String buttonName = joystick.profile.buttonName(buttonCode);
            joystick.addOrUpdateButtonStatus(buttonName, Status.Up);
            return true;
        }

        @Override
        public boolean axisMoved(Controller controller, int axisCode, float value) {
            String stickName = joystick.profile.stickName(axisCode);
            joystick.sticks.put(stickName, value);
            return true;
        }

        @Override
        public boolean povMoved(Controller controller, int povCode, PovDirection value) {
            return false;
        }

        @Override
        public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
            return false;
        }

        @Override
        public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
            return false;
        }

        @Override
        public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
            return false;
        }
    }

}
