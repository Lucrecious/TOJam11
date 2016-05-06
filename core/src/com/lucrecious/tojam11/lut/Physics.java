package com.lucrecious.tojam11.lut;

import com.nilunder.bdx.Bdx;

import javax.vecmath.Vector3f;

public class Physics {
    public static Vector3f forceForVelocity(Vector3f initVel, Vector3f finalVel, float mass, float sec){
        return finalVel.minus(initVel).mul(1f/sec).mul(mass);
    }

    public static float forceForVelocity(float init_speed, float fin_speed, float mass, float sec){
        return ((fin_speed - init_speed) * (1f/sec)) * mass;
    }

    public static float timeForVelocity(float initVelocity, float finalVelocity, float mass, float force) {
        return ((finalVelocity - initVelocity)*mass)/force;
    }
}
