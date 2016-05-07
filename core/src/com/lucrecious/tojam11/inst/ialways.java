package com.lucrecious.tojam11.inst;

import com.badlogic.gdx.utils.JsonValue;
import com.nilunder.bdx.Instantiator;import com.nilunder.bdx.GameObject;
import com.lucrecious.tojam11.*;
public class ialways extends Instantiator {

	public GameObject newObject(JsonValue gobj){
		String name = gobj.name;

		if (gobj.get("class").asString().equals("Escape"))
			return new com.lucrecious.tojam11.entities.Escape();

		return super.newObject(gobj);
	}
	
}
