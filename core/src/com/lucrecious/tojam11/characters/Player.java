package com.lucrecious.tojam11.characters;

public class Player extends GravitatingEntity {
    public void init() {
        super.init();
        components.add(new Movement(this, props.get("player").asInt()));
    }
}
