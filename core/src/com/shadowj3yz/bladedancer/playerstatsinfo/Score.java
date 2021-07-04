package com.shadowj3yz.bladedancer.playerstatsinfo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Score extends Actor {

	private int score;
	
	private Stage stage;
	private Label label;
	private LabelStyle style;
	private BitmapFont font;
	private Vector2 position;
	
	public Score() {
		score = 0;
		
		stage = new Stage();
		
		font = new BitmapFont(Gdx.files.internal("fonts/tektonProCond.fnt"), false);
		font.setScale(1f);
		style = new LabelStyle(font, Color.WHITE);
		label = new Label("Score: " + score, style);
		position = new Vector2(0, 0);
		label.setPosition(position.x, position.y);
		stage.addActor(label);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		//super.draw(batch, parentAlpha);
		label.setText("Score: " + score);
		
		label.setPosition(position.x, position.y);
		
		stage.act();
		stage.draw();
	}
	
	public void drawFinalScore(int score) {
		this.score = score;
		position.set(Gdx.graphics.getWidth()/2 - label.getWidth()/2, Gdx.graphics.getHeight() - label.getHeight() - 20);
		label.setColor(Color.RED);
	}
	
	public void incrementScore() {
		score ++;
	}

	public int getScore() {
		return score;
	}
	
}
