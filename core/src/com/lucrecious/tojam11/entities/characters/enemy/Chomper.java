package com.lucrecious.tojam11.entities.characters.enemy;

import com.lucrecious.tojam11.entities.characters.LiveEntity;
import com.lucrecious.tojam11.entities.characters.player.Player;
import com.lucrecious.tojam11.lut.mapping.AIController;
import com.nilunder.bdx.RayHit;
import com.nilunder.bdx.State;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;

public class Chomper extends Enemy {

    public void init() {
        super.init();

        movement.maxSpeed(1f);
    }

    @Override
    protected AI AI(AIController cont) {
        return new FollowAI(this, cont);
    }

    @Override
    protected Integer direction() {
        Player f = nextPlayer();
        if (f != null) {
            Vector3f delta = f.position().minus(position());
            if (delta.dot(localize(new Vector3f(1, 0, 0))) < 0) {
                return -1;
            }

            return 1;
        }

        return null;
    }


    private class FollowAI extends AI {

        public FollowAI(LiveEntity g, AIController cont) {
            super(g, cont);
            state(core);
            health.setMaxHealth(10);
        }

        private State core = new State() {
            public void main() {
                Player f = nextPlayer();

                if (f != null) {
                    follow(f);

                    Integer d = direction();
                    if (d != null) {
                        Vector3f attackDirection = entity.localize(new Vector3f(d * (entity.width() + 0.1f), 0, 0));

                        attackDirection.length(entity.width() + 0.1f);

                        ArrayList<RayHit> hits = g.scene.xray(g.position(), attackDirection);

                        for (RayHit hit : hits) {
                            if (hit != null && hit.object == f) {
                                f.killHealth(3);
                            }
                        }
                    }
                } else {
                    cont.push("move", new Vector2f(0, 0));
                }
            }
        };
    }
}
