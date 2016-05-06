package com.lucrecious.tojam11.entities.characters;

import com.lucrecious.tojam11.lut.Math;
import com.nilunder.bdx.Component;
import com.nilunder.bdx.GameObject;
import com.nilunder.bdx.State;

import javax.vecmath.Vector3f;

public class YLock extends Component{
    public YLock(GameObject g) {
        super(g);
        state(core);
    }
    private State core = new State() {
        private Vector3f startPosition;

        public void enter() {
            startPosition = g.position();
        }

        public void main() {
            Vector3f p = g.position();
            g.position(new Vector3f(p.x, startPosition.y, p.z));
        }
    };
}
