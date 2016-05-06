package com.lucrecious.tojam11.entities.characters;

import com.nilunder.bdx.Component;
import com.nilunder.bdx.GameObject;
import com.nilunder.bdx.State;

public class Health extends Component {
    private LiveEntity entity;

    public Health(LiveEntity g) {
        super(g);
        entity = g;
        state(core);
    }

    private State core = new State() {
        public void enter() {

        }
    };
}
