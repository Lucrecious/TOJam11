package com.lucrecious.tojam11.entities.characters;

import com.lucrecious.tojam11.entities.GravitatingEntity;
import com.lucrecious.tojam11.lut.Time;

public class LiveEntity extends GravitatingEntity {
    protected Health health;
    public void init() {
        super.init();
        components.add(health = new Health(this));
    }

    public void main() {
        if (health.health() <= 0) {
            end();
        }
    }

    public void restoreHealth(int restoreAmount) {
        health.health(health.health() + restoreAmount);
    }

    public void killHealth(int killAmount) {
        if (immunityTimer == null || immunityTimer.finished()) {
            health.health(health.health() - killAmount);
            if (immunityTimer != null) {
                immunityTimer.reset();
                immunityTimer.start();
            }
        }
    }

    private Time.Timer immunityTimer = null;

    public void setImmunityTimer(float sec) {
        if (sec <= 0) {
            immunityTimer = null;
        }

        immunityTimer = new Time.Timer(sec);
        immunityTimer.start();
    }
}
