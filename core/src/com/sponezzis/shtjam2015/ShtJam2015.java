package com.sponezzis.shtjam2015;

import com.badlogic.gdx.Game;
import com.sponezzis.shtjam2015.screens.SplashScreen;

public class ShtJam2015 extends Game {

	public static ShtJam2015 game;

	@Override
	public void create() {
		game = this;
		ResourceManager.initialize();
		setScreen(new SplashScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

}
