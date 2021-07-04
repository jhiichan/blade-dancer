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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.shadowj3yz.bladedancer.playerstatsinfo.Score;

public class GameOverScreen implements Screen {
	
	private Stage stage;
	
	private SpriteBatch batch;
	
	private Texture gameOverTexture;
	
	private Sprite gameOverSprite;
	
	private TextureAtlas playBtnAtlas;
	private ButtonStyle playBtnStyle;
	private Button playBtn;
	
	private Skin skin;
	
	private Score finalScore;
	
	private Game game;
	
	public GameOverScreen(Game game, int score) {
		this.game = game;
		finalScore = new Score();
		finalScore.drawFinalScore(score);
	}

	@Override
	public void show() {
		gameOverTexture = new Texture(Gdx.files.internal("images/gameOver.jpg"));
		gameOverSprite = new Sprite(gameOverTexture);
		
		gameOverSprite.setPosition(0, 0);
		
		stage = new Stage();
		
		skin = new Skin();
		playBtnAtlas = new TextureAtlas("buttons/playBtn.atlas");
		skin.addRegions(playBtnAtlas);
		
		playBtnStyle = new ButtonStyle();
		playBtnStyle.up = skin.getDrawable("playBtn");
		playBtnStyle.over = skin.getDrawable("playBtnDown");
		playBtnStyle.down = skin.getDrawable("playBtnDown");
		
		playBtn = new Button(playBtnStyle);
		playBtn.setPosition(Gdx.graphics.getWidth()/2 - playBtn.getWidth()/2, 30);
		
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
		
		gameOverSprite.draw(batch);
		
		batch.end();
		
		finalScore.draw(batch, 0);
		
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
