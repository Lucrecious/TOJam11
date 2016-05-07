package com.lucrecious.tojam11.inst;

import com.badlogic.gdx.utils.JsonValue;
import com.nilunder.bdx.Instantiator;import com.nilunder.bdx.GameObject;
import com.lucrecious.tojam11.*;
public class isky extends Instantiator {

	public GameObject newObject(JsonValue gobj){
		String name = gobj.name;

		if (gobj.get("class").asString().equals("Cloud"))
			return new com.lucrecious.tojam11.entities.Cloud();

		return super.newObject(gobj);
	}
	
}
