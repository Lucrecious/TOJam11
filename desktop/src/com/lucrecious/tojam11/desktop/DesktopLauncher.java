package com.lucrecious.tojam11.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lucrecious.tojam11.BdxApp;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "tojam11";
		config.width = 1080;
		config.height = 720;
		new LwjglApplication(new BdxApp(), config);
	}
}
