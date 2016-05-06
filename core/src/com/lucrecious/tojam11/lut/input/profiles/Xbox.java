package com.lucrecious.tojam11.lut.input.profiles;

public class Xbox extends Unknown {
    @Override
    public String stickName(int axisCode) {
        switch (axisCode) {
            case 0:
                return "ls-v";
            case 1:
                return "ls-h";
            case 2:
                return "rs-v";
            case 3:
                return "rs-h";
        }

        return "unknown";
    }

    @Override
    public String buttonName(int buttonCode) {
        switch (buttonCode) {
            case 0:
                return "a";
            case 1:
                return "b";
            case 2:
                return "x";
            case 3:
                return "y";
            case 4:
                return "lb";
            case 5:
                return "rb";
            case 6:
                return "menu-l";
            case 7:
                return "menu-r";
            case 8:
                return "ls";
            case 9:
                return "rs";
        }

        return "unknown";
    }
}
