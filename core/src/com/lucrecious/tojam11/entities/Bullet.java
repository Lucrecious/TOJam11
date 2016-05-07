package com.lucrecious.tojam11.entities;

import com.lucrecious.tojam11.entities.characters.LiveEntity;
import com.lucrecious.tojam11.entities.characters.player.Player;
import com.lucrecious.tojam11.lut.Time;
import com.nilunder.bdx.GameObject;
import com.nilunder.bdx.components.SpriteAnim;

import javax.vecmath.Vector2f;
import java.util.ArrayList;

public class Bullet extends GameObject {
    private Vector2f velocity = new Vector2f(0, 0);
    private GameObject creator = null;
    private boolean playerHeal = false;
    private int damage = 1;

    private final float speed = 0.1f;
    private final float pullForceMag = 150f;
    private final Time.Timer endTimer = new Time.Timer(5f);

    private SpriteAnim anim;


    public void init() {
        endTimer.start();

        anim = new SpriteAnim(children.get("G_Bullet"), 8, 8);
        anim.add("core_yellow", 0, new int[] {0, 1});
        anim.add("core_red", 1, new int[]{0, 1});
        components.add(anim);
    }

    public void main() {

        if (playerHeal) {
            anim.play("core_yellow");
        } else {
            anim.play("core_red");
        }

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
                        if (playerHeal && entity instanceof Player) {
                            entity.restoreHealth(1);
                        } else {
                            entity.killHealth(damage);
                        }
                    }

                }
            }
        }

        if (hit || endTimer.finished()) {
            end();
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

    public void playerHeal(boolean b) {
        playerHeal = b;
    }

    public void damage(int d) {
        damage = d;
    }
}
