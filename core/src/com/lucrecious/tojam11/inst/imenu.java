package com.lucrecious.tojam11.inst;

import com.badlogic.gdx.utils.JsonValue;
import com.nilunder.bdx.Instantiator;import com.nilunder.bdx.GameObject;
import com.lucrecious.tojam11.*;
public class imenu extends Instantiator {

	public GameObject newObject(JsonValue gobj){
		String name = gobj.name;

		if (gobj.get("class").asString().equals("MenuConfig"))
			return new com.lucrecious.tojam11.entities.MenuConfig();

		return super.newObject(gobj);
	}
	
}
