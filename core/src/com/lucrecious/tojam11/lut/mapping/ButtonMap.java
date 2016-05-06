package com.lucrecious.tojam11.lut.mapping;

public class ButtonMap {
    private String _name;
    private String[] buttons;
    private Controller.Type type;
    private Integer joystickIndex;

    public ButtonMap(String name, Integer joystickIndex, String... buttons) {
        _name = name;
        this.buttons = buttons;
        this.type = Controller.Type.Joystick;
        this.joystickIndex = joystickIndex;
    }

    public ButtonMap(String name, String... buttons) {
        _name = name;
        this.buttons = buttons;
        this.type = Controller.Type.Keyboard;
        this.joystickIndex = null;
    }

    public String name() {
        return _name;
    }

    public boolean hit() {
        return Controller.buttonAction(buttons, type, Status.Hit, joystickIndex);
    }

    public boolean down() {
        return Controller.buttonAction(buttons, type, Status.Down, joystickIndex);
    }

    public boolean up() {
        return Controller.buttonAction(buttons, type, Status.Up, joystickIndex);
    }
}

