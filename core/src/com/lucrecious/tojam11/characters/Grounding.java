package com.lucrecious.tojam11.characters;

import com.lucrecious.tojam11.Constants;
import com.lucrecious.tojam11.lut.Helper;
import com.lucrecious.tojam11.lut.LocalRay;
import com.lucrecious.tojam11.lut.Math;
import com.nilunder.bdx.*;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

public class Grounding extends Component {
    public Grounding(GravitatingEntity entity) {
        super(entity);
        this.entity = entity;
        state = core;
    }

    private GravitatingEntity entity;

    public static final float touchingGroundDelta = 0.03f;
    public static final float touchingGroundAngle = (float) java.lang.Math.PI / 4;

    public GameObject ground() {
        RayHit hit = groundHit();
        return hit == null ? null : hit.object;
    }

    public boolean ice() {
        GameObject ground = ground();
        if (ground != null) {
            return ground.props.get(Constants.GroundProperty).asString().equals(Constants.IceSubProperty);
        }

        return false;
    }

    public RayHit slideHit() {
        RayHit hit = groundHit();

        if (hit != null && hit.object.props.containsKey(Constants.SlideProperty)) {
            return hit;
        }

        return null;
    }

    public RayHit groundHit() {

        LocalRay groundRayRight = entity.rayMap.get(GravitatingEntity.GroundRayRightHash);
        LocalRay groundRayLeft = entity.rayMap.get(GravitatingEntity.GroundRayLeftHash);

        // Debugging...
        groundRayRight.debug_cast();
        groundRayLeft.debug_cast();

        RayHit groundHitRight = groundRayRight.findHitByProperty(Constants.GroundProperty);
        RayHit groundHitLeft = groundRayLeft.findHitByProperty(Constants.GroundProperty);

        if (groundHitRight == null && groundHitLeft == null) {
            return null;
        }

        float deltaRight = groundRayRight.distance(groundHitRight) - entity.height();
        float deltaLeft = groundRayLeft.distance(groundHitLeft) - entity.height();

        if (deltaRight < touchingGroundDelta || deltaLeft < touchingGroundDelta ||
                (groundHitRight != null && g.touching(groundHitRight.object.name)) ||
                (groundHitLeft != null && g.touching(groundHitLeft.object.name))) {

            float angleRight = groundHitRight == null ? Float.POSITIVE_INFINITY :
                    groundHitRight.normal.angle(entity.relativeDown().negated());
            float angleLeft = groundHitLeft == null ? Float.POSITIVE_INFINITY :
                    groundHitLeft.normal.angle(entity.relativeDown().negated());

            if (angleRight < touchingGroundAngle || angleLeft < touchingGroundAngle) {
                return deltaLeft < deltaRight ? groundHitLeft : groundHitRight;
            }
        }

        return null;
    }

    public Matrix3f groundRotation() {
        return _groundRotation.calculate();
    }

    public Vector3f groundTranslation() {
        return _groundTranslation.calculate();
    }

    private State core = new State() {
        public void main() {
            _groundRotation.update();
            _groundTranslation.update();

            moveWithPlatforms();
            stickToGround();
        }

        private void stickToGround() {
            if (groundHit() != null) {
                // Negates the gravitational force and pushes towards the ground
                //   this prevents sliding down slopes
                g.applyForce(entity.relativeDown()
                        .mul(Constants.GravitationalForce).negated()
                        .plus(groundHit().normal.negated().mul(Constants.GravitationalForce)));
            }
        }

        private void moveWithPlatforms() {
            // Translate with ground
            Vector3f translation = groundTranslation();
            if (translation != null) {
                g.position(g.position().plus(translation));
            }

            // Rotate with ground
            Matrix3f rotation = groundRotation();
            GameObject ground = ground();
            if (rotation != null && ground != null) {

                Vector3f delta = g.position().minus(ground.position());
                Vector3f rotatedDelta = Math.matrix3fByVector3f(rotation, delta);

                g.position(ground.position().plus(rotatedDelta));
            }
        }
    };

    private Helper.Updater<Vector3f> _groundTranslation = new Helper.Updater<Vector3f>() {
        GameObject lastGround = null;
        Vector3f lastGroundPosition = null;

        @Override
        public Vector3f doCalculation() {
            GameObject ground = ground();

            if (ground == null || lastGround != ground) {
                lastGround = null;
                lastGroundPosition = null;

                if (ground == null) {
                    return null;
                }
            }

            Vector3f translation = null;
            if (lastGround != null) {
                translation = ground.position().minus(lastGroundPosition);
            }

            lastGround = ground;
            lastGroundPosition = ground.position();

            return translation;
        }
    };
    private Helper.Updater<Matrix3f> _groundRotation = new Helper.Updater<Matrix3f>() {
        GameObject lastGround = null;
        Matrix3f lastOrientation = null;

        @Override
        public Matrix3f doCalculation() {
            GameObject ground = ground();

            if (ground == null || lastGround != ground) {
                lastGround = null;
                lastOrientation = null;

                if (ground == null) {
                    return null;
                }
            }

            Matrix3f rotation = null;
            if (lastGround != null) {
                Matrix3f invertedLastOrientation = new Matrix3f(lastOrientation);
                invertedLastOrientation.invert();

                rotation = ground.orientation().mult(invertedLastOrientation);
            }

            lastGround = ground;
            lastOrientation = ground.orientation();

            return rotation;
        }
    };

}
