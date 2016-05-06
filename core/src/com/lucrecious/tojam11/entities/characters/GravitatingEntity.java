package com.lucrecious.tojam11.entities.characters;

import com.lucrecious.tojam11.lut.DimensionalGameObject;
import com.lucrecious.tojam11.lut.LocalizedRayMap;
import com.lucrecious.tojam11.lut.Math;
import com.lucrecious.tojam11.lut.Physics;
import com.nilunder.bdx.Bdx;
import com.nilunder.bdx.GameObject;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class GravitatingEntity extends DimensionalGameObject {

    public static final String GroundRayRightHash = "ground_right";
    public static final String GroundRayLeftHash = "ground_left";
    public static final String BackgroundRayHash = "background";

    public void init() {
        scene.gravity(new Vector3f(0, 0, 0));

        rayMap.add(GroundRayRightHash, new Vector3f(width() + 0.01f, 0, 0), new Vector3f(0, 0, -(height() * 5)), true);
        rayMap.add(GroundRayLeftHash, new Vector3f(-(width() + 0.01f), 0, 0), new Vector3f(0, 0, -(height() * 5)), true);
        rayMap.add(BackgroundRayHash, new Vector3f(0, 0, 0), new Vector3f(0, depth() * 10, 0), false);

        components.add(new YLock(this));
        components.add(gravitator = new Gravitator(this));
        components.add(grounding = new Grounding(this));
;
    }

    protected Grounding grounding;
    protected Gravitator gravitator;

    public LocalizedRayMap rayMap = new LocalizedRayMap(this);

    public Vector3f relativeDown() {
        return Math.matrix3fByVector3f(orientation(), new Vector3f(0, 0, -1));
    }

    public void applyForceForSpeed(float finalVel, Vector3f vecDir, float sec){
        Vector3f force = forceForSpeed(finalVel, vecDir, sec);
        applyForce(force);
    }

    // Returns the force needed to accelerate the entity to a given final speed from its 0 velocity
    //   in the direction the entity wants to move
    // As a side note, as a the entity approaches the given final velocity, the function bounds the
    //   force to stay at a the given constant velocity in the given direction.
    public Vector3f forceForSpeed(float finalVel, Vector3f vecDir, float sec) {
        Vector3f vel = velocity();

        // this is to make sure we take a copy of the vector, not actually manipulate it...
        vecDir = new Vector3f(vecDir);
        vecDir.normalize();

        float mass = mass();

        float initVel = Math.project(vel, vecDir).dot(vecDir);

        float force = Physics.forceForVelocity(initVel, finalVel, mass, sec);
        float timeToVel = Physics.timeForVelocity(initVel, finalVel, mass, force);

        if (timeToVel < (sec <= 0.05f ? sec / 2 : 0.05f)) {
            force = Physics.forceForVelocity(0, finalVel, mass, sec);
        }

        return vecDir.mul(force);
    }

    public Vector3f localize(Vector3f vec) {
        return Math.matrix3fByVector3f(orientation(), vec);
    }

    // Since this method is kind of expensive, because of the matrix inverting, I want to limit
    //   the creation of the localized vector to once per frame. I use time to keep track of it.
    //   Also, for the sake of keeping things clean, I am aware that technically inverting with a 2D matrix,
    //   and I can use the simple O(1) algorithm to find the determinant and then invert. I don't do that
    //   because I don't think it's worth the extra code when I can just cache the end value (less code).
    float lastLocalizeControllerTime = 0;
    Vector2f localizedControllerVector = null;
    public Vector2f localizeController(Vector2f vector) {
        if (lastLocalizeControllerTime == Bdx.time && localizedControllerVector != null) {
            return localizedControllerVector;
        }

        if (vector.length() == 0) {
            return vector;
        }

        lastLocalizeControllerTime = Bdx.time;

        Matrix3f matrix = orientation();
        matrix.invert();
        matrix.setM01(0);
        matrix.setM10(0);
        matrix.setM11(1);
        matrix.setM12(0);
        matrix.setM21(0);

        Vector3f global = new Vector3f(vector.x, 0, vector.y);

        Vector3f local = Math.matrix3fByVector3f(matrix, global);

        GameObject camera = scene.camera.parent();

        if (camera != null) {
            local = Math.matrix3fByVector3f(camera.orientation(), local);
        }

        if (1 - local.getX() < 0.01f) {
            local.setX(java.lang.Math.round(local.getX()));
        }

        if (1 - local.getY() < 0.01f) {
            local.setZ(java.lang.Math.round(local.getZ()));
        }

        localizedControllerVector = new Vector2f(local.x, local.z);

        return localizedControllerVector;
    }
}
