package com.lucrecious.tojam11.entities.characters;

import com.lucrecious.tojam11.lut.mapping.ButtonMap;
import com.lucrecious.tojam11.lut.mapping.Controller;
import com.lucrecious.tojam11.lut.mapping.PlayerController;
import com.lucrecious.tojam11.lut.mapping.StickDirectionalMap;
import com.nilunder.bdx.Bdx;

public class Player extends LiveEntity {

    private static int playerCount = 0;

    public void init() {
        super.init();
        setup(playerCount++);
        components.add(new Movement(this, cont));
        components.add(new Shooter(this, cont));
        health.setMaxHealth(50);
    }

    private Controller cont;
    private void setup(int i) {
        cont = new PlayerController()
                .add(new ButtonMap("jump", i, "a", "cross"))
                .add(new StickDirectionalMap("move", 0.3f, "left", i))
                .add(new StickDirectionalMap("shoot", 0.5f, "right", i));
    }
}
