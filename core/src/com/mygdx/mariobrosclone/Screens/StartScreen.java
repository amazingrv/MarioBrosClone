package com.mygdx.mariobrosclone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mariobrosclone.MarioBrosClone;

public class StartScreen implements Screen{

	MarioBrosClone game;
	Viewport viewport;
	Stage stage;
	
	TmxMapLoader mapLoader;
	TiledMap map;
	OrthogonalTiledMapRenderer renderer;
	OrthographicCamera cam;
	
	Texture title;
	
	public StartScreen(MarioBrosClone game)
	{
		this.game = game;
		cam = new OrthographicCamera();
		viewport = new FitViewport(MarioBrosClone.V_WIDTH, MarioBrosClone.V_HEIGHT, cam);
		stage = new Stage(viewport,  game.batch);
		
		mapLoader = new TmxMapLoader();
		map = mapLoader.load("level1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1);
		
		title = new Texture("Mario GFX/title.png");
	}
	
	
	

	@Override
	public void render(float delta) {
		update(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.render();
		game.batch.begin();
		game.batch.draw(title, 95, 180, 350, 180);
		game.batch.end();
		//stage.draw();
		
		
	}

	public void update(float dt)
	{
		//handle Input
		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			game.setScreen(new PlayScreen((MarioBrosClone)game));
			dispose();
		}
		
		renderer.setView(cam);
	}
	public void resize(int width, int height) {
		viewport.update(width, height);
		
	}
	@Override
	public void show() {
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
		stage.dispose();
		
	}

}
