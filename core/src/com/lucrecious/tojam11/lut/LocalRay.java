package com.lucrecious.tojam11.lut;

import com.badlogic.gdx.graphics.Color;
import com.nilunder.bdx.Bdx;
import com.nilunder.bdx.RayHit;

import javax.vecmath.Vector3f;
import java.util.ArrayList;

public class LocalRay {
    com.nilunder.bdx.GameObject g;
    float lastCastTime;
    ArrayList<RayHit> lastHits;

    Vector3f offset;
    Vector3f vec;
    boolean local;

    public LocalRay(com.nilunder.bdx.GameObject g, Vector3f offset, Vector3f vec, boolean local) {
        this.g = g;
        this.offset = offset;
        this.vec = vec;
        this.local = local;
        lastCastTime = -1;
    }

    public ArrayList<RayHit> cast() {
        return cast(false, null);
    }

    public ArrayList<RayHit> debug_cast() {
        return cast(true, Color.RED);
    }

    public ArrayList<RayHit> debug_cast(Color color) {
        return cast(true, color);
    }

    public RayHit findHitByProperty(String property) {
        ArrayList<RayHit> hits = cast();
        for (RayHit hit : hits) {
            if (hit.object != null && hit.object.props.containsKey(property)) {
                return hit;
            }
        }

        return null;
    }

    public ArrayList<RayHit> findHitsByProperty(String property) {
        ArrayList<RayHit> hits = cast();
        ArrayList<RayHit> found = new ArrayList<RayHit>();

        for (RayHit hit : hits) {
            if (hit.object != null && hit.object.props.containsKey(property)) {
                found.add(hit);
            }
        }

        return found;
    }

    private ArrayList<RayHit> cast(boolean debug, Color color) {
        if (Bdx.time != lastCastTime) {
            lastCastTime = Bdx.time;
            Vector3f src = Math.matrix3fByVector3f(g.orientation(), offset).plus(g.position());
            Vector3f localVec = Math.matrix3fByVector3f(g.orientation(), vec);

            if (!debug) {
                lastHits = g.scene.xray(src, localVec);
            } else {
                lastHits = g.scene.debug_xray(src, localVec, color);
            }
        }

        return lastHits;
    }

    public float distance(RayHit hit) {
        if (hit == null) {
            return Float.POSITIVE_INFINITY;
        }

        Vector3f src = Math.matrix3fByVector3f(g.orientation(), offset).plus(g.position());

        return hit.position.minus(src).length();
    }

}
