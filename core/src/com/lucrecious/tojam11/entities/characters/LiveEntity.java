package com.lucrecious.tojam11.entities.characters;

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
        health.health(health.health() - killAmount);
    }
}
