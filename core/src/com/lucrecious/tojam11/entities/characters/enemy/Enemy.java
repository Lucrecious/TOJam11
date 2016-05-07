package com.lucrecious.tojam11.entities.characters.enemy;

import com.lucrecious.tojam11.entities.characters.LiveEntity;
import com.lucrecious.tojam11.entities.characters.Movement;
import com.lucrecious.tojam11.entities.characters.player.Player;
import com.lucrecious.tojam11.lut.Helper;
import com.lucrecious.tojam11.lut.Math;
import com.lucrecious.tojam11.lut.mapping.AIController;
import com.lucrecious.tojam11.lut.mapping.Controller;
import com.nilunder.bdx.Component;
import com.nilunder.bdx.GameObject;
import com.nilunder.bdx.State;
import com.nilunder.bdx.components.SpriteAnim;

import javax.vecmath.Vector3f;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public abstract class Enemy extends LiveEntity {

    protected AIController cont;
    protected Movement movement;

    protected ArrayList<Player> players;

    public void init() {
        super.init();

        cont = new AIController();

        components.add(AI(cont));
        components.add(new Updater(this, cont));
        components.add(animation());
        components.add(movement = new Movement(this, cont));

        players = new ArrayList<Player>();
        for (GameObject o : scene.objects) {
            if (o instanceof Player) {
                players.add((Player)o);
            }
        }
    }

    protected abstract AI AI(AIController cont);

    protected Component animation() {
        return new BasicAnimation(this);
    }

    protected Integer direction() {
        return null;
    }

    protected Player nextPlayer() {
        players.sort(new Comparator<GameObject>() {
            @Override
            public int compare(GameObject o1, GameObject o2) {
                Vector3f delta1 = o1.position().minus(position());
                Vector3f delta2 = o2.position().minus(position());

                return Float.compare(delta1.length(), delta2.length());
            }
        });

        for (Player p : players) {
            if (p.valid()) {
                return p;
            }
        }

        return null;
    }

    private static class BasicAnimation extends Component{

        private SpriteAnim anim;
        private Enemy entity;

        public BasicAnimation(Enemy g) {
            super(g);
            entity = g;
            anim = new SpriteAnim(g.children.get(0), 16, 16);
            anim.add("core", 0, new int[]{0, 1});
            g.components.add(anim);
            anim.play("core");
            state(core);
        }

        private State core = new State() {
            public void main() {
                float onX = Math.project(entity.velocity(), entity.localize(new Vector3f(1, 0, 0))).x;
                if (java.lang.Math.abs(onX) > 0.3f) {
                    Integer direction = entity.direction();
                    float sign;
                    if (direction == null) {
                        sign = java.lang.Math.copySign(1, onX);
                    }
                    else {
                        sign = (float)direction;
                    }

                    anim.uvScaleX(sign);
                }
            }
        };
    }

    private static class Updater extends Component {
        private AIController cont;
        public Updater(GameObject g, AIController cont) {
            super(g);
            this.cont = cont;
            state(core);
        }

        private State core = new State() {
            public void main() {
                cont.update();
            }
        };
    }
}
