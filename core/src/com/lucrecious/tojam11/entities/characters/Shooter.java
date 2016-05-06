package com.lucrecious.tojam11.entities.characters;

import com.lucrecious.tojam11.entities.Bullet;
import com.lucrecious.tojam11.lut.Time;
import com.lucrecious.tojam11.lut.mapping.Controller;
import com.nilunder.bdx.Component;
import com.nilunder.bdx.GameObject;
import com.nilunder.bdx.State;

import javax.vecmath.Vector2f;

public class Shooter extends Component{
    private Controller cont;
    public Shooter(GameObject g, Controller cont) {
        super(g);
        this.cont = cont;
        state(core);
    }

    private State core = new State() {
        private Time.Timer shootTimer = null;

        public void enter() {
            shootTimer = new Time.Timer(0.05f);
            shootTimer.start();
        }

        public void main() {

            Vector2f dir = cont.vector("shoot");

            if (shootTimer.finished() && dir.length() != 0) {
                Bullet bullet = (Bullet)g.scene.add("Bullet");
                bullet.position(g.position());
                bullet.direction(cont.vector("shoot"));
                bullet.creator(g);

                shootTimer.reset();
                shootTimer.start();
            }
        }
    };
}
