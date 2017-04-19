
package com.badlogic.gradletest;

import coffeefox.HelloApp;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Texture.setEnforcePotImages(false);

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new HelloApp(), config);
	}
}
