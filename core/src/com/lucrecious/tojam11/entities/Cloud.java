package com.lucrecious.tojam11.entities;

import com.nilunder.bdx.GameObject;
import com.nilunder.bdx.components.SpriteAnim;

import javax.vecmath.Vector3f;
import java.util.Random;

public class Cloud extends GameObject {
    private Vector3f ul;
    private Vector3f dr;
    private Vector3f delta;

    private float velocity;

    private SpriteAnim anim;

    private Random random;
    public void init() {
        ul = parent().position();
        dr = parent().parent().position();
        delta = dr.minus(ul);
        delta.setX(0);
        delta.setY(0);

        random = new Random();
        velocity = random.nextFloat();

        velocity = (velocity * 0.005f + 0.001f);


        anim = new SpriteAnim(this, 16, 16);
        anim.add("core", 0, new int[]{0, 1});
        components.add(anim);
        anim.play("core");

        restart();
    }

    public void restart() {
        int dir = random.nextFloat() - 0.5 < 0 ? -1 : 1;
        velocity *= dir;

        Vector3f down = new Vector3f(delta);
        down.length(random.nextFloat() * delta.length() * 0.7f);

        position(ul.plus(down));

        Vector3f pos;
        Vector3f myPos = position();
        if (velocity < 0) {
            pos = new Vector3f(dr.x, myPos.y, myPos.z);
        } else {
            pos = new Vector3f(ul.x, myPos.y, myPos.z);
        }

        position(pos);

        anim.speed = random.nextFloat() * 0.1f + 0.01f;
    }

    public void main() {
        move(velocity, 0, 0);

        if (position().x > dr.x || position().x < ul.x) {
            restart();
        }
    }
}
