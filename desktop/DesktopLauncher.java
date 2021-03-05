package com.myfkd.stopit.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.myfkd.stopit.Stopee;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "StopIt";
		config.width = 800;
		config.height = 400;
		new LwjglApplication(new Stopee(), config);
	}
}
