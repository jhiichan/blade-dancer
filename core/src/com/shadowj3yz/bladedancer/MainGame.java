package com.shadowj3yz.bladedancer;

import com.badlogic.gdx.Game;
import com.shadowj3yz.bladedancer.screens.MainMenu;

public class MainGame extends Game {
	
	private Game game;
	
	@Override
	public void create () {
		game = this;
		setScreen(new MainMenu(game));
	}

	@Override
	public void render () {
		super.render();
	}
}
