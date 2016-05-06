package com.lucrecious.tojam11.lut;

import com.nilunder.bdx.Bdx;
import com.nilunder.bdx.RayHit;

import java.util.ArrayList;

public class Helper {
    public static RayHit findFirstHitByProperty(ArrayList<RayHit> hits, String property) {
        for (RayHit hit : hits) {
            if (hit.object != null && hit.object.props.containsKey(property)) {
                return hit;
            }
        }

        return null;
    }

    public abstract static class Updater<T> {
        private float lastUpdate;
        private T held;

        public Updater() {
            lastUpdate = -1;
        }

        public abstract T doCalculation();

        public T calculate() {
            if (lastUpdate != Bdx.time) {
                lastUpdate = Bdx.time;
                held = doCalculation();
            }

            return held;
        }

        public void update() {
            if (lastUpdate != Bdx.time) {
                lastUpdate = Bdx.time;
                held = doCalculation();
            }
        }
    }

    public abstract static class Action {
        public abstract void run();
    }


}
