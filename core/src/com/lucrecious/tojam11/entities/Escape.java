package com.lucrecious.tojam11.entities;

import com.nilunder.bdx.Bdx;
import com.nilunder.bdx.Camera;

public class Escape extends Camera {
    public void main() {
        if (Bdx.keyboard.keyHit("esc")) {
            Bdx.end();
        }
    }
}
