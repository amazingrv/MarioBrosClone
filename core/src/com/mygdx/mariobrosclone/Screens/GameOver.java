package com.mygdx.mariobrosclone.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mariobrosclone.MarioBrosClone;

public class GameOver implements Screen{

	Viewport viewport;
	Stage stage;
	
	Game game;			
	public GameOver(Game game) {
		// TODO Auto-generated constructor stub
		this.game = game;
		viewport = new FitViewport(MarioBrosClone.V_WIDTH, MarioBrosClone.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, ((MarioBrosClone) game).batch);
		
		Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
		Table table = new Table();
		table.center();
		table.setFillParent(true);
		
		Label gameOverLabel = new Label("GAME OVER", font);
		Label playAgainLabel = new Label("Press 'S' Key to Play Again", font);
		
		
		table.add(gameOverLabel).expandX();
		table.row();
		table.add(playAgainLabel).expandX().padTop(15);
		stage.addActor(table);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}
	
	public void update(float dt)
	{
		//handleInput
		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			game.setScreen(new PlayScreen((MarioBrosClone)game));
			dispose();
		}
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
		stage.dispose();
	}

}
