package com.shadowj3yz.bladedancer.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Monster {
	
private Vector2 position;
	
	private static final int col = 4;
	private static final int row = 1;
	
	private Animation animation;
	
	private Texture monsterTexture;
	private TextureRegion[] frames;
	private TextureRegion currentFrame;
	
	private float stateTime;
	
	private Rectangle bounds;
	
	private float moveSpeed = 1f;
	
	private String moveDir;
	
	private boolean isOutOfScreen = false;
	
	public Monster (Vector2 position, Texture texture, String moveDir) {
		
		this.position = position;
		
		monsterTexture = texture;
		TextureRegion[][] tmp = TextureRegion.split(monsterTexture, monsterTexture.getWidth() / col, monsterTexture.getHeight() / row);
		frames = new TextureRegion[col * row];
		
		int index = 0;
		for(int r = 0; r < row; r++) {
			for(int c = 0; c < col; c ++) {
				frames[index++] = tmp[r][c];
			}
		}
		
		stateTime = 0f;
		animation = new Animation(1, frames);
		currentFrame = animation.getKeyFrame(0);

		bounds = new Rectangle(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
		
		this.moveDir = moveDir;
		
	}
	
	public void update() {
		
		bounds.set(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
		
		if(stateTime < col) {
			stateTime += Gdx.graphics.getDeltaTime();
		}
		else {
			stateTime = 0;
		}

		currentFrame = animation.getKeyFrame(stateTime);
		
		if(moveDir.equals("left"))
			position.x -= moveSpeed;
		else if(moveDir.equals("right"))
			position.x += moveSpeed;
		
	}
	
	public void reposition() {
		
		if(moveDir == "left") {
			//50 - 80
			position.x = Gdx.graphics.getWidth() + 80;
			position.y += 200;
		}
		else {
			position.x = 0 - 80;
			position.y += 200;
		}
		
		if(isOutOfScreen == true) {
//			if(moveDir == "left")
//				position.x += 0;
//			else
//				position.x += -130;
			position.y += 350;
			
			isOutOfScreen = false;
		}
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public String getMoveDir() {
		return moveDir;
	}

	public void setOutOfScreen(boolean isOutOfScreen) {
		this.isOutOfScreen = isOutOfScreen;
	}

}
