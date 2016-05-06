package com.lucrecious.tojam11.entities.characters;

public class LiveEntity extends GravitatingEntity {
    protected Health health;
    public void init() {
        super.init();
        components.add(health = new Health());
    }
}
