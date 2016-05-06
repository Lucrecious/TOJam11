package com.lucrecious.tojam11.characters;

import com.lucrecious.tojam11.lut.DimensionalGameObject;
import com.lucrecious.tojam11.lut.mapping.ButtonMap;
import com.lucrecious.tojam11.lut.mapping.Controller;
import com.lucrecious.tojam11.lut.mapping.PlayerController;
import com.nilunder.bdx.Bdx;
import com.nilunder.bdx.GameObject;

public class Player extends DimensionalGameObject{
    PlayerController controller;

    public void init() {
        controller = new PlayerController();
        controller.add(new ButtonMap("jump", "space"));
        controller.add(new ButtonMap("jump", props.get("player").asInt(), "a"));
    }

    public void main() {
        if (controller.buttonHit("jump")) {
            applyForce(0, 0, 300);
        }

        Bdx.profiler.props.put("player", props.get("player").asString());
    }
}
