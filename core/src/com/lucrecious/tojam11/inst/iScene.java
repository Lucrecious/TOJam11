package com.lucrecious.tojam11.inst;

import com.badlogic.gdx.utils.JsonValue;
import com.nilunder.bdx.Instantiator;import com.nilunder.bdx.GameObject;
import com.lucrecious.tojam11.*;
public class iScene extends Instantiator {

	public GameObject newObject(JsonValue gobj){
		String name = gobj.name;

		if (gobj.get("class").asString().equals("Sacky"))
			return new com.lucrecious.tojam11.Sacky();

		return super.newObject(gobj);
	}
	
}
