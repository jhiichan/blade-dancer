package com.shadowj3yz.bladedancer.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MainMenu implements Screen {
	
	private SpriteBatch batch;

	private Stage stage;
	
	private Texture cover;
	
	private Sprite coverSprite;
	
	private TextureAtlas playBtnAtlas;
	private ButtonStyle playBtnStyle;
	private Button playBtn;
	
	private Skin skin;
	
	private Game game;
	
	public MainMenu(Game game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		
		cover = new Texture(Gdx.files.internal("images/bladeDancerCover.jpg"));
		
		coverSprite = new Sprite(cover);
		
		coverSprite.setPosition(0, 0);
		
		stage = new Stage();
		
		skin = new Skin();
		playBtnAtlas = new TextureAtlas("buttons/playBtn.atlas");
		skin.addRegions(playBtnAtlas);
		
		playBtnStyle = new ButtonStyle();
		playBtnStyle.up = skin.getDrawable("playBtn");
		playBtnStyle.over = skin.getDrawable("playBtnDown");
		playBtnStyle.down = skin.getDrawable("playBtnDown");
		
		playBtn = new Button(playBtnStyle);
		playBtn.setPosition(Gdx.graphics.getWidth()/2 - playBtn.getWidth()/2, 100);
		
		stage.addActor(playBtn);
		Gdx.input.setInputProcessor(stage);
		
		playBtn.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				game.setScreen(new PlayLevel1(game));
				return true;
			}
		});
		
		batch = new SpriteBatch();
		
		Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		coverSprite.draw(batch);
		
		batch.end();
		
		stage.act();
		
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
