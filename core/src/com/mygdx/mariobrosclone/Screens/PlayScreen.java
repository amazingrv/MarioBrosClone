package com.mygdx.mariobrosclone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.mygdx.mariobrosclone.MarioBrosClone;
import com.mygdx.mariobrosclone.Scenes.Hud;
import com.mygdx.mariobrosclone.Sprites.Mario;
import com.mygdx.mariobrosclone.Tools.B2DWorldCreater;

public class PlayScreen implements Screen{

	MarioBrosClone game;
	TextureAtlas atlas;
	
	OrthographicCamera gamecam;
	Viewport gamePort;
	Hud hud;
	
	//Box 2d 
	private World world;
	Box2DDebugRenderer box2dr;
	
	//For Tile Map
	TmxMapLoader mapLoader;
	TiledMap map;
	OrthogonalTiledMapRenderer renderer;	//renders map to the screen
	
	Mario player;
	
	final float JumpVelocity = 4f;
	final float RightVelocity = 0.1f;
	final float LeftVelocity = -0.1f;
	final int maxRightVelocity = 2;
	final int maxLeftVelocity = -2;
		
	public PlayScreen(MarioBrosClone game)
	{
		this.game = game;
		
		atlas = new TextureAtlas("Mario_and_Enemies.pack");
		
		
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(MarioBrosClone.V_WIDTH / MarioBrosClone.PPM, MarioBrosClone.V_HEIGHT/ MarioBrosClone.PPM, gamecam);
		hud = new Hud(game.batch);
		
		mapLoader = new TmxMapLoader();
		map = mapLoader.load("level1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBrosClone.PPM);
		//intially set our gamecam to be centered at the start of the map
		gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);
		
		world = new World(new Vector2(0, -10), true);
		box2dr = new Box2DDebugRenderer();
		box2dr.SHAPE_STATIC.set(1,0,0,1);
		
		player = new Mario(world, this);
		
		new B2DWorldCreater(world, map);
		
	}
	public void handleInput(float deltaTime)
	{
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
			player.b2body.applyLinearImpulse(new Vector2(0, JumpVelocity), player.b2body.getWorldCenter(), true);
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= maxRightVelocity)
			player.b2body.applyLinearImpulse(new Vector2(RightVelocity , 0), player.b2body.getWorldCenter(), true);
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= maxLeftVelocity)
			player.b2body.applyLinearImpulse(new Vector2(LeftVelocity , 0), player.b2body.getWorldCenter(), true);
				
	}
	
	public TextureAtlas getAtlas()
	{
		return atlas;
	}
	public void update(float deltaTime)
	{
		handleInput(deltaTime);
		player.update(deltaTime);
		//1/60 times to calculate the collisions 
		world.step(1/60f, 6, 2);		
		gamecam.position.x = player.b2body.getPosition().x;
		gamecam.update();
		//to tell renderer to draw only what the camera can see
		renderer.setView(gamecam);
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		//update game logic
		update(delta);
		
		//clearing screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//render game map
		renderer.render();
		
		//render Box2d Object lines
		box2dr.render(world, gamecam.combined);
		
		game.batch.setProjectionMatrix(gamecam.combined);
		game.batch.begin();
		player.draw(game.batch);
		game.batch.end();
		//Set batch to draw hud of game
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
		
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
		map.dispose();
		renderer.dispose();
		world.dispose();
		box2dr.dispose();
		hud.dispose();
		
	}
	
}
