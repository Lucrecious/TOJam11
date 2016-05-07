package com.lucrecious.tojam11.entities.characters.enemy;

import com.lucrecious.tojam11.entities.characters.LiveEntity;
import com.lucrecious.tojam11.entities.characters.player.Player;
import com.lucrecious.tojam11.lut.Time;
import com.lucrecious.tojam11.lut.mapping.AIController;
import com.nilunder.bdx.Component;
import com.nilunder.bdx.GameObject;
import com.nilunder.bdx.State;
import com.nilunder.bdx.utils.Random;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;

public class Bouncer extends Enemy {

    public void init() {
        super.init();
        health.setMaxHealth(4);
    }

    @Override
    protected AI AI(AIController cont) {
        return new BouncerAI(this, cont);
    }

    private class BouncerAI extends AI {
        public BouncerAI(LiveEntity e, AIController cont) {
            super(e, cont);
            state(core);
        }

        private State core = new State() {
            private int direction;
            private Time.Timer jumpTimer = new Time.Timer(0.5f);

            public void enter() {
                direction = Random.direction().x < 0 ? -1 : 1;
                jumpTimer.start();
            }

            public void main() {
                if (!jumpTimer.finished()) {
                    cont.press("jump");
                } else {
                    jumpTimer.reset();
                    jumpTimer.start();
                }

                Vector3f local = entity.localize(new Vector3f(direction, 0, 0));
                cont.push("move", new Vector2f(local.x, local.z));
                if (g.hitProperty("wall")) {
                    direction *= -1;
                }

                for (GameObject p : g.touchingObjects) {
                    if (p instanceof Player) {
                        ((Player) p).killHealth(10);
                    }
                }
            }
        };
    }
}
