package com.lucrecious.tojam11.entities.characters;

import com.lucrecious.tojam11.entities.Bullet;
import com.lucrecious.tojam11.lut.Time;
import com.lucrecious.tojam11.lut.mapping.Controller;
import com.nilunder.bdx.Component;
import com.nilunder.bdx.GameObject;
import com.nilunder.bdx.State;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class Shooter extends Component{
    private Controller cont;
    public Shooter(GameObject g, Controller cont) {
        super(g);
        this.cont = cont;
        state(core);
    }

    private State core = new State() {
        private Time.Timer shootTimer;
        private GameObject arm;
        private GameObject barrel;

        public void enter() {
            shootTimer = new Time.Timer(0.05f);
            shootTimer.start();

            arm = g.children.get("Arm");
            barrel = arm.children.get(0);
        }

        public void main() {

            Vector2f dir = cont.vector("shoot");

            if (dir.length() != 0) {

                arm.alignAxisToVec(0, new Vector3f(dir.x, 0, dir.y), 0.9f);

                if (shootTimer.finished()) {
                    Bullet bullet = (Bullet) g.scene.add("Bullet");
                    bullet.position(barrel.position());
                    bullet.direction(cont.vector("shoot"));
                    bullet.creator(g);

                    shootTimer.reset();
                    shootTimer.start();
                }
            }
        }
    };
}
