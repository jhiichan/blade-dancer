package com.shadowj3yz.bladedancer.objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Rock {

	private Vector2 position, size;
	private Rectangle bounds;
	
	public Rock(Vector2 position, Vector2 size) {
		
		this.position = position;
		
		this.size = size;
		
		this.size.x = size.x;
		this.size.y = size.y;
		
		bounds = new Rectangle(position.x, position.y, size.x, size.y);
		
	}
	
	@SuppressWarnings("static-access")
	public void update(Player player, Rock curRock) {
		
		bounds.set(position.x, position.y, size.x, size.y);
		
		if(player.getBounds().overlaps(curRock.getBounds())) {
			player.setMotionDisabled(true);
			player.setMotionSlowed(true);
			player.setMoveSpeed(0.5f);
			player.setMotion("run");
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
