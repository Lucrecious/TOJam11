package com.lucrecious.tojam11.entities.characters;

import com.nilunder.bdx.Bdx;
import com.nilunder.bdx.Component;
import com.nilunder.bdx.GameObject;
import com.nilunder.bdx.State;

import javax.vecmath.Vector3f;

public class Health extends Component {
    private LiveEntity entity;
    private int maxHealth = 100;
    private int currentHealth = 100;

    public Health(LiveEntity g) {
        super(g);
        entity = g;
        state(core);
    }

    public void setMaxHealth(int health) {
        maxHealth = currentHealth = health;
    }

    public void health(int health) {
        if (health > maxHealth) {
            currentHealth = maxHealth;
        } else if (health < 0) {
            currentHealth = 0;
        } else {
            currentHealth = health;
        }
    }

    public int health() {
        return currentHealth;
    }

    private State core = new State()
    {
        private GameObject health;
        public void enter() {
            health = g.scene.add("G_Health");
            health.parent(g);
            health.position(entity.position()
                    .plus(new Vector3f(0, 0, entity.height() + 0.1f))
                    .plus(new Vector3f(-entity.width(), 0, 0))
                    .plus(new Vector3f(0, entity.depth() + 1, 0)));
        }

        public void main() {
            health.scale((float)currentHealth/(float)maxHealth, 1, 1);
        }
    };
}




