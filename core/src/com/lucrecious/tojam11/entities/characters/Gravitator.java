package com.lucrecious.tojam11.entities.characters;

import com.badlogic.gdx.graphics.Color;
import com.lucrecious.tojam11.Constants;
import com.lucrecious.tojam11.lut.Math;
import com.nilunder.bdx.Component;
import com.nilunder.bdx.GameObject;
import com.nilunder.bdx.RayHit;
import com.nilunder.bdx.State;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;

public class Gravitator extends Component {
    public Gravitator(GravitatingEntity entity) {
        super(entity);
        state = core;
        this.entity = entity;
    }

    private GravitatingEntity entity;

    private State core = new State() {
        public void main() {
            Vector3f gravityDirection = gravityDirection();
            gravityDirection.length(Constants.GravitationalForce);

            g.applyForce(gravityDirection);

            Matrix3f rotation = entity.grounding.groundRotation();
            if (rotation != null) {
                g.orientation(g.orientation().mult(rotation));
            }

            g.alignAxisToVec("Z", gravityDirection.negated(), 0.15f);
            g.alignAxisToVec("Y", new Vector3f(0, 1, 0), 1f);

        }
    };

    private Vector3f lastGravityDirection = null;
    private Vector3f gravityDirection() {
        GameObject field = gravityField();

        if (field != null && field.parent() != null) {
            if (field.parent().props.containsKey(Constants.GravitatorProperty)) {
                RayHit gravitatorHit = gravitatorRay();
                if (gravitatorHit != null && gravitatorHit.object == field.parent()) {
                    if (overGravitatorGround(gravitatorHit)) {
                        return lastGravityDirection = gravitatorHit.normal.negated();
                    }
                }
                return lastGravityDirection = field.parent().position().minus(g.position());
            }

            // This means that the Z-axis for the object must be facing in the direction of the gravity.
            //   Recommend using an empty.
            return lastGravityDirection = Math.matrix3fByVector3f(field.parent().orientation(), new Vector3f(0, 0, 1));
        }
        return lastGravityDirection = lastGravityDirection != null ? lastGravityDirection : new Vector3f(0, 0, -1);
    }

    private boolean overGravitatorGround(RayHit hit) {
        Vector3f overGravitatorVector = Math.project(hit.position.minus(g.position()), hit.normal.negated());
        overGravitatorVector.length(overGravitatorVector.length() + 0.1f);

        ArrayList<RayHit> hits = g.scene.debug_xray(g.position(), overGravitatorVector, Color.BLUE);

        for (RayHit h : hits) {
            if (h.object == hit.object) {
                return true;
            }
        }

        return false;
    }

    private GameObject gravityField() {
        ArrayList<RayHit> hits = entity.rayMap.get(GravitatingEntity.BackgroundRayHash).debug_cast();

        for (RayHit hit : hits) {
            if (hit != null && hit.object.props.containsKey(Constants.GravityFieldProperty)) {
                return hit.object;
            }
        }

        return null;
    }

    private RayHit gravitatorRay() {
        ArrayList<RayHit> hits = g.scene.debug_xray(g.position(),
                Math.matrix3fByVector3f(g.orientation(), new Vector3f(0, 0, -entity.height() * 15)), Color.GREEN);

        for (RayHit hit : hits) {
            if (hit != null && hit.object.props.containsKey(Constants.GravitatorProperty)) {
                return hit;
            }
        }

        return null;
    }
}
