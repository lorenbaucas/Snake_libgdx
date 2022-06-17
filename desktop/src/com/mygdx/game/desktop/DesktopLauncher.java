package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.SnakeGame;

import static com.badlogic.gdx.Application.ApplicationType.Android;

public class DesktopLauncher {

	public static int FPS = 15;

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.width = 800;
		config.height = 800;
		config.foregroundFPS = FPS;
		config.fullscreen = false;
		new LwjglApplication(new SnakeGame(), config);
	}
}
