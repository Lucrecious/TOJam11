package com.lucrecious.tojam11.entities.characters.player;

import com.lucrecious.tojam11.entities.characters.LiveEntity;
import com.lucrecious.tojam11.lut.Math;
import com.lucrecious.tojam11.lut.mapping.Controller;
import com.nilunder.bdx.Bdx;
import com.nilunder.bdx.Component;
import com.nilunder.bdx.GameObject;
import com.nilunder.bdx.State;
import com.nilunder.bdx.components.SpriteAnim;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class PlayerAnimation extends Component{

    private int row;
    private LiveEntity entity;
    private SpriteAnim anim;
    private Controller cont;

    private GameObject arm;

    public PlayerAnimation(LiveEntity g, int row, Controller cont) {
        super(g);
        this.row = row;
        this.cont = cont;
        entity = g;

        anim = new SpriteAnim(g.children.get("G_Player"), 16, 16);
        anim.add("idle", row, new int[] { 1 });
        anim.add("walk", row, new int[] {0, 1});

        g.components.add(anim);

        SpriteAnim armAnim = new SpriteAnim(arm = g.children.get("Arm"), 16, 8);
        armAnim.add("core", row, new int[]{0});
        g.components.add(armAnim);
        armAnim.play("core");

        state(core);
    }

    private State core = new State() {
        public void main() {

            Vector3f localX = entity.localize(new Vector3f(1, 0, 0));

            Vector3f move = Math.project(g.velocity(), localX);

            if (java.lang.Math.abs(move.x) > 0.05f) {
                anim.play("walk");
            } else {
                anim.play("idle");
            }

            float angle = Math.matrix3fByVector3f(arm.orientation(), new Vector3f(1, 0, 0)).angle(localX);
            double sign = java.lang.Math.copySign(1, java.lang.Math.PI/2 - angle);
            anim.uvScaleX((float)sign);
        }
    };
}
