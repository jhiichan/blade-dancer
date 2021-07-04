package com.shadowj3yz.bladedancer.screens;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.shadowj3yz.bladedancer.objects.FinishLine;
import com.shadowj3yz.bladedancer.objects.Monster;
import com.shadowj3yz.bladedancer.objects.Obstacle;
import com.shadowj3yz.bladedancer.objects.Player;
import com.shadowj3yz.bladedancer.objects.Rock;
import com.shadowj3yz.bladedancer.playerstatsinfo.Health;
import com.shadowj3yz.bladedancer.playerstatsinfo.Score;

public class PlayLevel1 implements Screen, InputProcessor {
	
	private final float PIXELS_TO_METERS = 100f;
	
	OrthographicCamera cam;
	
	Game game;

	SpriteBatch batch;
	
	World world;
	
	Player player;
	
	Health healthBar;
	
	Score score;
	
	ArrayList<Monster> monsters;
	Iterator<Monster> monsterIterator;
	
	ArrayList<Obstacle> obstacles;
	ArrayList<Rock> rocks;
	Iterator<Rock> rockIterator;
	ArrayList<FinishLine> grasses;
	Iterator<FinishLine> grassIterator;
	
	TiledMap map;
	OrthogonalTiledMapRenderer renderer;
	
	public PlayLevel1(Game game) {
		this.game = game;
	}

	@Override
	public void show() {
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		batch = new SpriteBatch();
		
		world = new World(new Vector2(0, 0),true);
		
		player = new Player(new Vector2(Gdx.graphics.getWidth()/2, 200), world);
		
		healthBar = new Health(4);
		
		score = new Score();
		
		monsters = new ArrayList<Monster>();
		
		Vector2 monsLeft = new Vector2(300, 400);
		Vector2 monsRight = new Vector2(-170, 600);
		
		monsters.add(new Monster(new Vector2(monsLeft.x, monsLeft.y), new Texture(Gdx.files.internal("monsterSprites/virusSpriteLeft.png")), "left"));
		monsters.add(new Monster(new Vector2(monsRight.x, monsRight.y), new Texture(Gdx.files.internal("monsterSprites/virusSpriteRight.png")), "right"));
		monsters.add(new Monster(new Vector2(0, 0), new Texture(Gdx.files.internal("monsterSprites/virusSpriteLeft.png")), "none"));
		//monsters.add(new Monster(new Vector2(monsRight.x + -130 * 2, monsRight.y + 200 * 2), new Texture(Gdx.files.internal("monsterSprites/virusSpriteRight.png")), "right"));
		
		map = new TmxMapLoader().load("maps/map_1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		
		obstacles = new ArrayList<Obstacle>();
		rocks = new ArrayList<Rock>();
		grasses = new ArrayList<FinishLine>();
		
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 1000; j++) {
				TiledMapTileLayer tree = (TiledMapTileLayer) map.getLayers().get(1);
				
				TiledMapTileLayer rock = (TiledMapTileLayer) map.getLayers().get(2);
				
				TiledMapTileLayer finishL = (TiledMapTileLayer) map.getLayers().get(4);
				
				if(rock.getCell(i, j) != null) {
					rocks.add(new Rock(new Vector2(i * 16, j * 16), new Vector2(16, 16)));
				}
				
				if(tree.getCell(i, j) != null) {
					obstacles.add(new Obstacle(new Vector2(i * 16, j * 16), world));
				}
				
				if(finishL.getCell(i, j) != null) {
					grasses.add(new FinishLine(new Vector2(i * 16, j * 16), new Vector2(16, 16)));
				}
			}
		}
		
		Gdx.input.setInputProcessor(this);
		
	}

	@SuppressWarnings("static-access")
	@Override
	public void render(float delta) {
		
		world.step(1f/60f, 6, 2);
		
		player.setPosition(new Vector2((player.getBody().getPosition().x * PIXELS_TO_METERS) - player.getCurrentFrame().getRegionWidth()/2 ,(player.getBody().getPosition().y * PIXELS_TO_METERS) -player.getCurrentFrame().getRegionHeight()/2 ));

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.setView(cam);
		renderer.render();
		
		player.update();
		player.movement();
		
		cam.position.set(player.getPosition().x + player.getCurrentFrame().getRegionWidth() / 2, player.getPosition().y + player.getCurrentFrame().getRegionHeight(), 0);
		batch.setProjectionMatrix(cam.combined);
		cam.update();
		
		batch.begin();
		
		batch.draw(player.getCurrentFrame(), player.getPosition().x, player.getPosition().y);

		monsterIterator = monsters.iterator();
		while(monsterIterator.hasNext()) {
			
			Monster curMonster = monsterIterator.next();
			
			curMonster.update();
			
			batch.draw(curMonster.getCurrentFrame(), curMonster.getPosition().x, curMonster.getPosition().y);
			
			if(curMonster.getBounds().overlaps(player.getBounds()) && player.getMotion().equals("slashRight") && curMonster.getMoveDir().equals("left")) {
				curMonster.reposition();
				score.incrementScore();
			}
			else if(curMonster.getBounds().overlaps(player.getBounds()) && player.getMotion().equals("slashLeft") && curMonster.getMoveDir().equals("right")) {
				curMonster.reposition();
				score.incrementScore();
			}
			else if(curMonster.getBounds().overlaps(player.getBounds()) && !player.getMotion().equals("slashRight") && !player.getMotion().equals("slashLeft")) {
				healthBar.playerHit();
				curMonster.reposition();
			}
			
			if(curMonster.getPosition().x < 0 && curMonster.getMoveDir().equals("left")) {
				curMonster.setOutOfScreen(true);
				curMonster.reposition();
			}
			else if(curMonster.getPosition().x > Gdx.graphics.getWidth() && curMonster.getMoveDir().equals("right")) {
				curMonster.setOutOfScreen(true);
				curMonster.reposition();
			}
			
		}
		
		rockIterator = rocks.iterator();
		while(rockIterator.hasNext()) {
			
			Rock curRock = rockIterator.next();
			
			curRock.update(player, curRock);
			
		}
		
		grassIterator = grasses.iterator();
		while(grassIterator.hasNext()) {
			
			FinishLine curGrass = grassIterator.next();
			
			curGrass.update(player, curGrass, game, score.getScore());
			
		}
		
		healthBar.draw(batch, 0);
		
		score.draw(batch, 0);
		
		batch.end();
		
		if(healthBar.getCurHealth() <= 0)
			game.setScreen(new GameOverScreen(game, score.getScore()));
		
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
		dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		player.getBody().setLinearVelocity(0, 1f);
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(player.isMotionDisabled() == false) {
			if(screenX > Gdx.graphics.getWidth() - (Gdx.graphics.getWidth()/2)) {
				player.setStateTime(9);
				player.setMotion("slashRight");
			}
			else {
				player.setStateTime(17);
				player.setMotion("slashLeft");
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
