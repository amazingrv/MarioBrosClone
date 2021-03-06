package com.mygdx.mariobrosclone.Screens;

import java.util.concurrent.LinkedBlockingQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mariobrosclone.MarioBrosClone;
import com.mygdx.mariobrosclone.Scenes.Hud;
import com.mygdx.mariobrosclone.Sprites.Mario;
import com.mygdx.mariobrosclone.Sprites.Enemies.Enemy;
import com.mygdx.mariobrosclone.Sprites.Items.Item;
import com.mygdx.mariobrosclone.Sprites.Items.ItemDef;
import com.mygdx.mariobrosclone.Sprites.Items.Mushroom;
import com.mygdx.mariobrosclone.Sprites.Tiles.FlagPole;
import com.mygdx.mariobrosclone.Tools.B2DWorldCreater;
import com.mygdx.mariobrosclone.Tools.WorldContactListner;

public class PlayScreen implements Screen {

	public MarioBrosClone game;
	TextureAtlas atlas;

	OrthographicCamera gamecam;
	Viewport gamePort;
	Hud hud;

	FlagPole flag;

	// Box 2d
	private World world;
	Box2DDebugRenderer box2dr;
	B2DWorldCreater creator;

	// For Tile Map
	TmxMapLoader mapLoader;
	TiledMap map;
	OrthogonalTiledMapRenderer renderer; // renders map to the screen

	Mario player;
	Array<Item> items;
	LinkedBlockingQueue<ItemDef> itemsToSpawn;

	private Music music;

	public PlayScreen(MarioBrosClone game) {
		this.game = game;

		atlas = new TextureAtlas("Mario_and_Enemies.atlas");

		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(MarioBrosClone.V_WIDTH / MarioBrosClone.PPM,
				MarioBrosClone.V_HEIGHT / MarioBrosClone.PPM, gamecam);
		hud = new Hud(game.batch);

		mapLoader = new TmxMapLoader();
		map = mapLoader.load("level1.tmx");

		renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBrosClone.PPM);

		// intially set our gamecam to be centered at the start of the map
		gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

		world = new World(new Vector2(0, -10), true);
		box2dr = new Box2DDebugRenderer();
		box2dr.SHAPE_STATIC.set(1, 0, 0, 1);

		player = new Mario(this); // add mario in game world
		creator = new B2DWorldCreater(this);

		// head hits mechnism
		world.setContactListener(new WorldContactListner());

		// handling bakground music
		music = MarioBrosClone.manager.get("audio/music/mario_music.ogg", Music.class);
		music.setLooping(true);
		music.play();

		// goomba = new Goomba(this);
		items = new Array<Item>();
		itemsToSpawn = new LinkedBlockingQueue<ItemDef>();

	}

	public void spawnItem(ItemDef idef) {
		itemsToSpawn.add(idef);
	}

	public void handleSpawningItems() {
		if (!itemsToSpawn.isEmpty()) {
			ItemDef idef = itemsToSpawn.poll();
			if (idef.type == Mushroom.class) {
				items.add(new Mushroom(this, idef.position.x, idef.position.y));
			}
		}
	}

	public TextureAtlas getAtlas() {
		return atlas;
	}

	public void update(float deltaTime) {

		handleSpawningItems();
		player.update(deltaTime);
		hud.update(deltaTime);
		flag.update(deltaTime);

		for (Enemy enemy : creator.getEnemies()) {
			enemy.update(deltaTime);
			if (enemy.getX() < player.getX() + 224 / MarioBrosClone.PPM)
				enemy.b2body.setActive(true);
		}

		for (Item item : items)
			item.update(deltaTime);
		// goomba.update(deltaTime);

		// 1/60 times to calculate the collisions
		world.step(1 / 60f, 6, 2);

		if (player.currentState != Mario.State.DEAD && player.b2body.getPosition().x > 2)
			gamecam.position.x = player.b2body.getPosition().x;

		gamecam.update();
		// to tell renderer to draw only what the camera can see
		renderer.setView(gamecam);

		// testin purpose
		// System.out.println(mapWidth + " " + player.b2body.getPosition().x);
		// System.out.println("GameCam pos : " + gamecam.position.x +" " +
		// gamecam.position.y);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// update game logic
		update(delta);

		// clearing screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// render game map
		renderer.render();

		// render Box2d Object lines
		// box2dr.render(world, gamecam.combined);

		game.batch.setProjectionMatrix(gamecam.combined);
		game.batch.begin();
		player.draw(game.batch);
		// goomba.draw(game.batch); //test
		for (Enemy enemy : creator.getEnemies()) // drawing Enemies graphics
			enemy.draw(game.batch);
		for (Item item : items)
			item.draw(game.batch);
		game.batch.end();
		// Set batch to draw hud of game
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
		flag.draw(game.batch);
		if (gameOver()) {
			game.setScreen(new GameOver(game));
			MarioBrosClone.manager.get("audio/sounds/gameover.wav", Sound.class).play();
			dispose();
		}
		if (goal()) {
			game.setScreen(new WonScreen(game));
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);

	}

	public boolean gameOver() {
		if (player.currentState == Mario.State.DEAD && player.getStateTimer() > 3)
			return true;
		if (hud.worldTimer <= 0)
			return true;
		return false;
	}

	private boolean goal() {
		return (player.currentState == Mario.State.WON && player.getStateTimer() > 3);
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

	public TiledMap getMap() {
		return map;
	}

	public World getWorld() {
		return world;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		map.dispose();
		renderer.dispose();
		world.dispose();
		box2dr.dispose();
		hud.dispose();

	}

	public void jump() {

	}

	public Viewport getViewport() {
		// TODO Auto-generated method stub
		return gamePort;
	}

	public MarioBrosClone getGame() {
		// TODO Auto-generated method stub
		return game;
	}

	public void setFlag(FlagPole flag) {
		// TODO Auto-generated method stub
		this.flag = flag;
	}

}
