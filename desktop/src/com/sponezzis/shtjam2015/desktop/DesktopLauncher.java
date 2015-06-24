package com.sponezzis.shtjam2015.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sponezzis.shtjam2015.ShtJam2015;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "SHAMEFUL BIG GAME HUNTER OMEGA";
		config.width = 1024;
		config.height = 768;
		config.resizable = false;
		new LwjglApplication(new ShtJam2015(), config);
	}
}
