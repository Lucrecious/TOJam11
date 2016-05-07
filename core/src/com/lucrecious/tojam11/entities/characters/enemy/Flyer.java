package com.lucrecious.tojam11.entities.characters.enemy;

import com.lucrecious.tojam11.Constants;
import com.lucrecious.tojam11.entities.Bullet;
import com.lucrecious.tojam11.entities.characters.LiveEntity;
import com.lucrecious.tojam11.lut.Time;
import com.lucrecious.tojam11.lut.mapping.AIController;
import com.nilunder.bdx.GameObject;
import com.nilunder.bdx.State;
import com.nilunder.bdx.utils.Random;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class Flyer extends Enemy {
    @Override
    protected AI AI(AIController cont) {
        return new FlyerAI(this, cont);
    }

    public void init() {
        super.init();
        movement.setMaxSpeed(5f);
        health.setMaxHealth(10);
    }

    public void main() {
        super.main();
        applyForce(relativeDown().mul(-Constants.GravitationalForce));
        applyForceForSpeed(0, new Vector3f(0, 0, 1), 0.05f);
    }

    @Override
    protected Integer direction() {
        return velocity().x < 0 ? -1 : 1;
    }

    private class FlyerAI extends AI {
        public FlyerAI(LiveEntity g, AIController cont) {
            super(g, cont);
            state(core);
        }

        private State core = new State() {
            private int direction;
            private boolean movingUp = true;
            private Time.Timer upDownTimer = new Time.Timer(1f);
            private Time.Timer shootTimer = new Time.Timer(0.2f);

            private GameObject flyerBarrel;

            public void enter() {
                direction = Random.direction().x < 0 ? -1 : 1;
                upDownTimer.start();
                shootTimer.start();

                flyerBarrel = children.get("FlyerBarrel");
            }

            public void main() {
                if (g.hitProperty("wall")) {
                    direction *= -1;
                }

                cont.push("move", new Vector2f(direction, 0));

                if (!upDownTimer.finished()) {
                    entity.applyForceForSpeed(2f*(movingUp ? 1 : -1), new Vector3f(0, 0, 1), 0.05f);
                } else {
                    upDownTimer.reset();
                    upDownTimer.start();
                    movingUp = !movingUp;
                }

                if (shootTimer.finished()) {
                    Bullet bullet = (Bullet)g.scene.add("Bullet");
                    bullet.position(flyerBarrel.position());
                    bullet.direction(new Vector2f(0, -1));
                    bullet.creator(g);
                    bullet.damage(10);
                    shootTimer.reset();
                    shootTimer.start();
                }
            }
        };
    }



}
