package com.shadowj3yz.bladedancer.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Obstacle {

BodyDef bodyDef;
	
	Body body;

	FixtureDef fixtureDef;
    
	final float PIXELS_TO_METERS = 100f;

	public Obstacle(Vector2 position, World world) {

		bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set((position.x + 16 / 2 )/ PIXELS_TO_METERS, (position.y  + 16 / 2 )/ PIXELS_TO_METERS);
		
		body = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(16 / 2 / PIXELS_TO_METERS, 16 / 2 / PIXELS_TO_METERS);
		
		fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		
		body.createFixture(fixtureDef);
		
		shape.dispose();
		
	}
	
}
