package com.lucrecious.tojam11.entities.characters;

import com.lucrecious.tojam11.entities.GravitatingEntity;
import com.lucrecious.tojam11.lut.Time;
import com.lucrecious.tojam11.lut.mapping.Controller;
import com.nilunder.bdx.Component;
import com.nilunder.bdx.State;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class Movement extends Component{

    private GravitatingEntity entity;
    private Controller cont;

    public Movement(GravitatingEntity g, Controller cont) {
        super(g);
        entity = g;
        this.cont = cont;
        state(core);
    }

    private float maxSpeed = 3f;
    public void maxSpeed(float speed) {
        maxSpeed = speed;
    }

    private float maxJumpSpeed = 5f;
    public void maxJumpSpeed(float speed) {
        maxJumpSpeed = speed;
    }

    private Time.Timer jumpTimer = new Time.Timer(0.3f);
    public void jumpTimer(float sec) {
        jumpTimer = new Time.Timer(sec);
    }


    private State core = new State() {
        public void main() {
            handleJumping();
            handleMovement();
        }

        private void handleMovement() {
            Vector2f move = entity.localizeController(cont.vector("move"));

            entity.applyForceForSpeed(maxSpeed*move.x, entity.localize(new Vector3f(1, 0 ,0)), 0.05f);

        }

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
                entity.applyForceForSpeed(5f, entity.localize(new Vector3f(0, 0, 1)), 0.05f);
            }
        }
    };
}
