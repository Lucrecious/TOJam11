package com.lucrecious.tojam11.entities;

import com.lucrecious.tojam11.entities.characters.LiveEntity;
import com.lucrecious.tojam11.entities.characters.Player;
import com.lucrecious.tojam11.lut.Time;
import com.nilunder.bdx.GameObject;
import com.nilunder.bdx.components.SpriteAnim;

import javax.vecmath.Vector2f;
import java.util.ArrayList;

public class Bullet extends GameObject {
    private Vector2f velocity = new Vector2f(0, 0);
    private GameObject creator = null;

    private final float speed = 0.1f;
    private final float pullForceMag = 150f;
    private final Time.Timer endTimer = new Time.Timer(5f);


    public void init() {
        endTimer.start();

        SpriteAnim anim = new SpriteAnim(children.get("G_Bullet"), 8, 8);
        anim.add("core", 0, new int[] {0, 1});
        components.add(anim);
        anim.play("core");
    }

    public void main() {
        move(velocity.x, 0, velocity.y);

        boolean hit = false;

        ArrayList<GameObject> hits = touchingObjects;

        if (!hits.isEmpty()) {
            Vector2f pullForce = new Vector2f(velocity);
            if (pullForce.length() != 0) {
                pullForce.length(pullForceMag);
                pullForce.negate();
            }

            for (GameObject g : hits) {
                if (creator == null || (creator != g && !creator.childrenRecursive().contains(g))) {
                    hit = true;
                    g.applyForce(pullForce.x, 0, pullForce.y);

                    if (g instanceof LiveEntity) {
                        LiveEntity entity = (LiveEntity)g;
                        if (entity instanceof Player) {
                            entity.restoreHealth(1);
                        } else {
                            entity.killHealth(1);
                        }
                    }

                }
            }
        }

        if (hit || endTimer.finished()) {
            end();
            return;
        }
    }

    public void direction(Vector2f dir) {
        if (dir.length() != 0) {
            dir.length(speed);
        }

        velocity = dir;
    }

    public void creator(GameObject g) {
        creator = g;
    }
}
