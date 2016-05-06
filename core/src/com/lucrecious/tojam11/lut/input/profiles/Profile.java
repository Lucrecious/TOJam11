package com.lucrecious.tojam11.lut.input.profiles;

public abstract class Profile {

    public static final Profile Xbox = new Xbox();
    public static final Profile Unknown = new Unknown();

    public abstract String stickName(int axisCode);

    public abstract String buttonName(int buttonCode);

    public abstract String sliderName(int sliderCode);
}

