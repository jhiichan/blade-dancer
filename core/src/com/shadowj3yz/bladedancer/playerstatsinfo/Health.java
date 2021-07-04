package com.shadowj3yz.bladedancer.playerstatsinfo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Health extends Actor{

	private static int maxHealth;
	private int curHealth;
	
	private Stage stage;
	private Label label;
	private LabelStyle style;
	private BitmapFont font;
	
	@SuppressWarnings("static-access")
	public Health(int maxHealth) {
		
		this.maxHealth = maxHealth;
		curHealth = this.maxHealth;
		
		stage = new Stage();
		
		font = new BitmapFont(Gdx.files.internal("fonts/tektonProCond.fnt"), false);
		font.setScale(1f);
		style = new LabelStyle(font, Color.GREEN);
		label = new Label(drawHealthBars(), style);
		label.setPosition(0, Gdx.graphics.getHeight() - label.getHeight());
		stage.addActor(label);
		
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		//super.draw(batch, parentAlpha);
		label.setText(drawHealthBars());
		
		if(curHealth <= maxHealth/2)
			style.fontColor = Color.RED;
		
		label.setPosition(0, Gdx.graphics.getHeight() - label.getHeight());
		
		stage.act();
		stage.draw();
	}
	
	public String drawHealthBars() {
		
		String healthBars = "Health: ";
		
		for(int i = 1; i <= curHealth; i++) {
			healthBars += "[] ";
		}
		
		return healthBars;
	}
	
	public void playerHit() {
		
		if(curHealth >= 1) {
			curHealth--;
		}
		
	}

	public int getCurHealth() {
		return curHealth;
	}
	
}
