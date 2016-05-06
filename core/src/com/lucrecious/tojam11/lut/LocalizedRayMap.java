package com.lucrecious.tojam11.lut;

import javax.vecmath.Vector3f;
import java.util.HashMap;

public class LocalizedRayMap {
    private HashMap<String, LocalRay> rays;
    private com.nilunder.bdx.GameObject g;

    public LocalizedRayMap(com.nilunder.bdx.GameObject g) {
        this.g = g;
        rays = new HashMap<String, LocalRay>();
    }

    public void add(String hash, Vector3f offset, Vector3f vec, boolean local) {
        rays.put(hash, new LocalRay(g, offset, vec, local));
    }

    public LocalRay get(String hash) {
        return rays.get(hash);
    }
}
