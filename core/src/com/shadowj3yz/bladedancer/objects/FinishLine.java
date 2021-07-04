package com.shadowj3yz.bladedancer.objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.shadowj3yz.bladedancer.screens.GameOverScreen;

public class FinishLine {
	
	private Vector2 position, size;
	private Rectangle bounds;
	
	public FinishLine(Vector2 position, Vector2 size) {
		
		this.position = position;
		
		this.size = size;
		
		this.size.x = size.x;
		this.size.y = size.y;
		
		bounds = new Rectangle(position.x, position.y, size.x, size.y);
		
	}
	
	public void update(Player player, FinishLine finishLine, Game game, int score) {
		
		bounds.set(position.x, position.y, size.x, size.y);
		
		if(player.getBounds().overlaps(finishLine.getBounds())) {
			game.setScreen(new GameOverScreen(game, score));
		}
		
	}

	public Vector2 getPosition() {
		return position;
	}

	public Vector2 getSize() {
		return size;
	}

	public Rectangle getBounds() {
		return bounds;
	}

}
