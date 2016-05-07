package com.lucrecious.tojam11;

import java.util.*;

import com.badlogic.gdx.*;

import com.nilunder.bdx.*;

public class BdxApp implements ApplicationListener {

	@Override
	public void create(){
		Bdx.init();

		Scene.instantiators = new HashMap<String, Instantiator>();
		Scene.instantiators.put("level1", new com.lucrecious.tojam11.inst.ilevel1());
		Scene.instantiators.put("sky", new com.lucrecious.tojam11.inst.isky());

		Bdx.scenes.add(new Scene("level1"));
		Bdx.firstScene = "level1";
	}

	@Override
	public void dispose(){
		Bdx.dispose();
	}

	@Override
	public void render(){
		Bdx.main();
	}

	@Override
	public void resize(int width, int height){
		Bdx.resize(width, height);
	}

	@Override
	public void pause(){
	}

	@Override
	public void resume(){
	}
}
