package com.mygdx.mariobrosclone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
	
	float timer;
	boolean flag;
	
	Label msg;
	Label.LabelStyle fontOne, fontTwo;
	
	public StartScreen(MarioBrosClone game)
	{
		this.game = game;
		timer = 0;
		flag = true;
		
		cam = new OrthographicCamera();
		viewport = new FitViewport(MarioBrosClone.V_WIDTH, MarioBrosClone.V_HEIGHT, cam);
		stage = new Stage(viewport,  game.batch);
		
		mapLoader = new TmxMapLoader();
		map = mapLoader.load("level1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1);
		fontOne = new Label.LabelStyle(new BitmapFont(), Color.BROWN);
		fontTwo = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
		msg = new Label("Press Enter to Start!", fontOne);
		msg.setPosition(100, 50);
		title = new Texture("Mario GFX/title.png");
		stage.addActor(msg);
	}
	
	public void blink(float dt)
	{
		if(timer >= 0.3f)
		{
			if(flag)
			{
				msg = new Label("Press Enter to Start!", fontTwo);
				flag = false;
			}
			else 
			{
				msg = new Label("Press Enter to Start!", fontOne);
				flag = true;
			}
			timer = 0;
		}
		msg.setPosition(100, 50);
		stage.addActor(msg);
	}
	

	@Override
	public void render(float delta) {
		update(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.render();
		game.batch.begin();
		game.batch.draw(title, 50, 90, 200, 100);
		game.batch.end();
		stage.draw();
		
		
	}

	public void update(float dt)
	{
		timer += dt;
		blink(dt);
		//handle Input
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
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
