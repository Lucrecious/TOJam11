package com.lucrecious.tojam11.entities;

import com.nilunder.bdx.Bdx;
import com.nilunder.bdx.GameObject;

public class MenuConfig extends GameObject {

    private static boolean alwaysSceneAdded = false;

    public void init() {
        if (!alwaysSceneAdded) {
            alwaysSceneAdded = true;
            Bdx.scenes.add("always");
        }
    }

    public void main() {
        if (Bdx.keyboard.keyHit("1")) {
            Bdx.scenes.add("level1");
            scene.end();
        }
    }
}
