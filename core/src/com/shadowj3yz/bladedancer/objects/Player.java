package com.shadowj3yz.bladedancer.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player {
	
	private Vector2 position;

	private static final int col = 8;
	private static final int row = 3;
	
	private Animation animation;
	
	private Texture playerTexture;
	private TextureRegion[] frames;
	private TextureRegion currentFrame;
	
	private float stateTime;
	
	private Rectangle bounds;
    
	private BodyDef bodyDef;
	
	private Body body;

	private FixtureDef fixtureDef;
	
	private static float moveSpeed = 1f;
	
	private static String motion = "run";
	
	private static boolean isMotionDisabled = false;
	
	private float disableTime = 0f;
	
	private float disableLimit = 1.5f;
	
	private static boolean isMotionSlowed = false;
	
	private float slowTime = 0f;
	
	private float slowLimit = 0.5f;
    
	private final float PIXELS_TO_METERS = 100f;
	
	public Player(Vector2 position, World world) {
		
		this.position = position;
		
		playerTexture = new Texture(Gdx.files.internal("championSprites/temporarySlasherSprite.png"));
		TextureRegion[][] tmp = TextureRegion.split(playerTexture, playerTexture.getWidth() / col, playerTexture.getHeight() / row);
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
		
		position.x -= currentFrame.getRegionWidth()/1.5;

		bounds = new Rectangle(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
        
		bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set((position.x - currentFrame.getRegionWidth()/2/3) / PIXELS_TO_METERS , position.y / PIXELS_TO_METERS);
        
        body = world.createBody(bodyDef);
        
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((currentFrame.getRegionWidth()/2 / PIXELS_TO_METERS)/3, currentFrame.getRegionHeight()/2 / PIXELS_TO_METERS);
        
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        
        body.createFixture(fixtureDef);

        shape.dispose();
		
		body.setLinearVelocity(0f, moveSpeed);
		
	}
	
	public void update() {
		
		bounds.set(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
		
		if(isMotionDisabled())
			disableTime += Gdx.graphics.getDeltaTime();
		if(disableTime > disableLimit) {
			disableTime = 0;
			isMotionDisabled = false;
		}
		if(isMotionSlowed())
			slowTime += Gdx.graphics.getDeltaTime();
		if(slowTime > slowLimit) {
			slowTime = 0;
			moveSpeed = 1f;
			isMotionSlowed = false;
		}
		
		if(motion.equals("run")) {
			
			bounds.set(position.x + currentFrame.getRegionWidth()/3, position.y, currentFrame.getRegionWidth()/3, currentFrame.getRegionHeight());
			
			if(stateTime < col - 1 + 0.6) {
				stateTime += Gdx.graphics.getDeltaTime() + 0.1;
			}
			else {
				stateTime = 0;
			}
		}
		else if(motion.equals("slashRight") && isMotionDisabled == false) {
			if(stateTime < col + col - 1 + 0.6) {
				
				if(stateTime > 10)
					bounds.set(position.x + currentFrame.getRegionWidth()/3, position.y, currentFrame.getRegionWidth()/3, currentFrame.getRegionHeight());
				
				stateTime += Gdx.graphics.getDeltaTime() + 0.1;
			}
			else {
				motion = "run";
				stateTime = 0;
			}
		}
		else if(motion.equals("slashLeft") && isMotionDisabled == false) {
			if(stateTime < col + col + col - 1 + 0.6) {
				
				if(stateTime > 18)
					bounds.set(position.x + currentFrame.getRegionWidth()/3, position.y, currentFrame.getRegionWidth()/3, currentFrame.getRegionHeight());
				
				stateTime += Gdx.graphics.getDeltaTime() + 0.1;
			}
			else {
				motion = "run";
				stateTime = 0;
			}
		}

		currentFrame = animation.getKeyFrame(stateTime);
		
		if(Gdx.input.getAccelerometerX() > 3) {
			body.setLinearVelocity(-moveSpeed, moveSpeed);
		}
		else if(Gdx.input.getAccelerometerX() < -3) {
			body.setLinearVelocity(moveSpeed, moveSpeed);
		}
		else {
			body.setLinearVelocity(0f, moveSpeed);
		}
		
	}
	
	public void movement() {
		if(Gdx.input.isKeyPressed(Keys.A)) 
			body.setLinearVelocity(-moveSpeed, moveSpeed);
		else if(Gdx.input.isKeyPressed(Keys.D))
			body.setLinearVelocity(moveSpeed, moveSpeed);
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

	public Animation getAnimation() {
		return animation;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setCurrentFrame(TextureRegion currentFrame) {
		this.currentFrame = currentFrame;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public Body getBody() {
		return body;
	}

	public static void setMotion(String motion) {
		Player.motion = motion;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public static String getMotion() {
		return motion;
	}

	public static void setMotionDisabled(boolean isMotionDisabled) {
		Player.isMotionDisabled = isMotionDisabled;
	}

	public static boolean isMotionDisabled() {
		return isMotionDisabled;
	}

	public static void setMoveSpeed(float moveSpeed) {
		Player.moveSpeed = moveSpeed;
	}

	public static boolean isMotionSlowed() {
		return isMotionSlowed;
	}

	public static void setMotionSlowed(boolean isMotionSlowed) {
		Player.isMotionSlowed = isMotionSlowed;
	}

}
