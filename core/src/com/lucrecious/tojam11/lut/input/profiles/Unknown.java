package com.lucrecious.tojam11.lut.input.profiles;

public class Unknown extends Profile {

    public final byte[] buttonLetters = "abcdefghijklmnop".getBytes();
    public final String[] stickNames = new String[] { "axis1", "axis2", "axis3", "axis4", "axis5", "axis6", "axis7" };
    public final String[] sliderNames = new String[]{"slide1", "slide2", "slider3", "slider4", "slider5", "slider6"};

    @Override
    public String stickName(int axisCode) {
        return stickNames[axisCode];
    }

    @Override
    public String buttonName(int buttonCode) {
        return String.valueOf((char)buttonLetters[buttonCode]);
    }

    @Override
    public String sliderName(int sliderCode) {
        return sliderNames[sliderCode];
    }
}
