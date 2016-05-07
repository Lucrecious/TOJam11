package com.lucrecious.tojam11.entities.characters.enemy;

import com.lucrecious.tojam11.entities.characters.LiveEntity;
import com.lucrecious.tojam11.lut.Math;
import com.lucrecious.tojam11.lut.mapping.AIController;
import com.nilunder.bdx.Component;
import com.nilunder.bdx.GameObject;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public abstract class AI extends Component {

    protected LiveEntity entity;
    protected AIController cont;

    public AI(LiveEntity g, AIController cont) {
        super(g);
        entity = g;
        this.cont = cont;
    }

    public void follow(GameObject g) {
        Vector3f delta = g.position().minus(entity.position());
        Vector3f direction = Math.project(delta, entity.localize(new Vector3f(1, 0, 0)));
        direction.normalize();

        cont.push("move", new Vector2f(direction.x, direction.z));
    }
}
