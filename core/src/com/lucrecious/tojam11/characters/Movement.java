package com.lucrecious.tojam11.characters;

import com.lucrecious.tojam11.lut.DimensionalGameObject;
import com.lucrecious.tojam11.lut.mapping.*;
import com.nilunder.bdx.Component;
import com.nilunder.bdx.GameObject;
import com.nilunder.bdx.State;

public class Movement extends Component{

    private DimensionalGameObject gobj;
    private Controller cont;

    public Movement(DimensionalGameObject g, int jIndex) {
        super(g);
        gobj = g;
        setup(jIndex);
        state(core);
    }

    private void setup(int i) {
        cont = new PlayerController()
                .add(new ButtonMap("jump", i, "a", "x"))
                .add(new StickDirectionalMap("move", 0.5f, "ls", i));
    }

    private State core = new State() {
        public void main() {

        }
    };
}
