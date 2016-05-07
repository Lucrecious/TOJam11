package com.lucrecious.tojam11.entities.characters.player;

import com.lucrecious.tojam11.entities.characters.LiveEntity;
import com.lucrecious.tojam11.entities.characters.Movement;
import com.lucrecious.tojam11.entities.characters.Shooter;
import com.lucrecious.tojam11.lut.mapping.ButtonMap;
import com.lucrecious.tojam11.lut.mapping.Controller;
import com.lucrecious.tojam11.lut.mapping.PlayerController;
import com.lucrecious.tojam11.lut.mapping.StickDirectionalMap;
import com.nilunder.bdx.Bdx;
import com.nilunder.bdx.Scene;

public class Player extends LiveEntity {

    private static int playerCount = 0;

    public void init() {
        super.init();
        if (playerCount == 0)
            Bdx.scenes.add(0, new Scene("sky"));

        setup(playerCount);
        components.add(new Movement(this, cont));
        components.add(new Shooter(this, cont));
        components.add(new PlayerAnimation(this, playerCount, cont));
        health.setMaxHealth(50);
        setImmunityTimer(0.3f);

        playerCount++;
    }

    private Controller cont;
    private void setup(int i) {
        cont = new PlayerController()
                .add(new ButtonMap("jump", i, "a", "cross"))
                .add(new StickDirectionalMap("move", 0.3f, "left", i))
                .add(new StickDirectionalMap("shoot", 0.5f, "right", i));
    }
}
