package com.lucrecious.tojam11.entities;

import com.lucrecious.tojam11.entities.characters.player.Player;
import com.nilunder.bdx.Bdx;
import com.nilunder.bdx.GameObject;
import com.nilunder.bdx.Scene;

import java.util.ArrayList;

public class LevelConfig extends GameObject {
    private ArrayList<Player> players;
    public void init() {

        Bdx.scenes.add(0, new Scene("sky"));
        Bdx.music.put("level_loop", Bdx.music.loadAudio("loop1"));
        Bdx.music.get("level_loop").setLooping(true);
        Bdx.music.get("level_loop").play();

        players = new ArrayList<Player>();
        for (GameObject g : scene.objects) {
            if (g instanceof Player) {
                players.add((Player)g);
            }
        }
    }

    private boolean gameOverAdded = false;
    public void main() {
        if (gameFinished() && !gameOverAdded) {
            gameOverAdded = true;
            Bdx.scenes.add(new Scene("gameover"));
        }

        if (Bdx.keyboard.keyHit("r")) {
            Bdx.scenes.add("menu");

            if (gameOverAdded)
                Bdx.scenes.remove("gameover");

            scene.end();
        }
    }

    private boolean gameFinished() {
        for (Player p : players) {
            if (p.valid()) {
                return false;
            }
        }

        return true;
    }

    public void onEnd() {
        Bdx.music.get("level_loop").stop();
        Bdx.music.remove("level_loop");

        Bdx.scenes.remove("sky");

        Player.playerCount = 0;
    }
}
