package com.lucrecious.tojam11.characters;

import com.lucrecious.tojam11.lut.Time;
import com.lucrecious.tojam11.lut.mapping.*;
import com.nilunder.bdx.Bdx;
import com.nilunder.bdx.Component;
import com.nilunder.bdx.State;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class Movement extends Component{

    private GravitatingEntity entity;
    private Controller cont;

    public Movement(GravitatingEntity g, int jIndex) {
        super(g);
        entity = g;
        setup(jIndex);
        state(core);
    }

    private void setup(int i) {
        cont = new PlayerController()
                .add(new ButtonMap("jump", i, "a", "x"))
                .add(new StickDirectionalMap("move", 0.3f, "ls", i))
                .add(new StickDirectionalMap("shoot", 0.5f, "rs", i));
    }

    private State core = new State() {
        public void main() {
            handleJumping();
            handleMovement();
        }

        private void handleMovement() {
            Vector2f move = entity.localizeController(cont.vector("move"));

            entity.applyForceForSpeed(3f*move.x, entity.localize(new Vector3f(1, 0 ,0)), 0.05f);

        }

        private Time.Timer jumpTimer = new Time.Timer(0.3f);
        private boolean inJump = false;
        private void handleJumping() {
            if (cont.buttonHit("jump") && entity.grounding.ground() != null) {
                inJump = true;
                jumpTimer.start();
            }

            if (cont.buttonUp("jump") || jumpTimer.finished()) {
                inJump = false;
                jumpTimer.reset();
            }

            if (inJump) {
                entity.applyForceForSpeed(4f, entity.localize(new Vector3f(0, 0, 1)), 0.05f);
            }
        }
    };
}
