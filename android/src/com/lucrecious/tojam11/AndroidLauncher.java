package com.lucrecious.tojam11.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.surfaceview.RatioResolutionStrategy;
import com.lucrecious.tojam11.BdxApp;

public class AndroidLauncher extends AndroidApplication {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		int width = 1080;
		int height = 720;
		config.resolutionStrategy = new RatioResolutionStrategy(width, height);
		initialize(new BdxApp(), config);
	}
}
