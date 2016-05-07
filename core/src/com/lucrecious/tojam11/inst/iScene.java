package com.lucrecious.tojam11.inst;

import com.badlogic.gdx.utils.JsonValue;
import com.nilunder.bdx.Instantiator;import com.nilunder.bdx.GameObject;
import com.lucrecious.tojam11.*;
public class iScene extends Instantiator {

	public GameObject newObject(JsonValue gobj){
		String name = gobj.name;

		if (gobj.get("class").asString().equals("Flyer"))
			return new com.lucrecious.tojam11.entities.characters.enemy.Flyer();
		if (gobj.get("class").asString().equals("Player"))
			return new com.lucrecious.tojam11.entities.characters.player.Player();
		if (gobj.get("class").asString().equals("Bullet"))
			return new com.lucrecious.tojam11.entities.Bullet();
		if (gobj.get("class").asString().equals("Chomper"))
			return new com.lucrecious.tojam11.entities.characters.enemy.Chomper();
		if (gobj.get("class").asString().equals("Bouncer"))
			return new com.lucrecious.tojam11.entities.characters.enemy.Bouncer();

		return super.newObject(gobj);
	}
	
}
