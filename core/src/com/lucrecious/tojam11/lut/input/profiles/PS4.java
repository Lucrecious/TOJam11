package com.lucrecious.tojam11.lut.input.profiles;

public class PS4 extends Unknown {
    @Override
    public String stickName(int axisCode) {
        switch (axisCode) {
            case 0:
                return "rs-v";
            case 1:
                return "rs-h";
            case 2:
                return "ls-v";
            case 3:
                return "ls-h";
        }

        return "unknown";
    }

    @Override
    public String buttonName(int buttonCode) {
        switch (buttonCode) {
            case 0:
                return "square";
            case 1:
                return "cross";
            case 2:
                return "circle";
            case 3:
                return "triangle";
            case 4:
                return "l1";
            case 5:
                return "r1";
            case 6:
                return "l2";
            case 7:
                return "r2";
            case 8:
                return "share";
            case 9:
                return "options";
            case 10:
                return "ls";
            case 11:
                return "rs";
            case 12:
                return "ps4";
        }

        return "unknown";
    }
}
