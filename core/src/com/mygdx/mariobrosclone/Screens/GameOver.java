package com.mygdx.mariobrosclone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mariobrosclone.MarioBrosClone;

public class GameOver extends Sprite implements Screen{

	Viewport viewport;
	Stage stage;
	MarioBrosClone game;
	
	SpriteBatch batch;
	TextureAtlas atlas;
	TextureRegion mario[];
	float timer;
	int flagImage;
	
	public GameOver(MarioBrosClone game) {
		
		batch = game.batch;
		timer = 0;
		flagImage = 0;
		mario = new TextureRegion[3];
		// TODO Auto-generated constructor stub
		this.game = game;
		viewport = new FitViewport(MarioBrosClone.V_WIDTH, MarioBrosClone.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, ((MarioBrosClone) game).batch);
		atlas = new TextureAtlas("Mario_and_Enemies.pack");
		
		for(int i =1 ; i<4 ;i++)
		{
			mario[i-1] = new TextureRegion(atlas.findRegion("little_mario"), i*16, 0, 16, 16);
		}
		Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
		Label gameOverLabel = new Label("GAME OVER", font);
		Label playAgainLabel = new Label("Press 'S' Key to Play Again", font);
		Label livesLabel = new Label("X 3", font);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
				
		table.add(gameOverLabel).expandX().center().padTop(50).padBottom(20);
		table.row();
		table.add(playAgainLabel).expandX().center().padTop(20);
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
		batch.begin();
		batch.draw(getFrame(delta), 10,10);
		batch.end();
		}
	
	public void update(float dt)
	{
		timer += dt;
		//handleInput
		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			game.setScreen(new PlayScreen((MarioBrosClone)game));
			dispose();
		}
		setPosition(10, 10);
		setRegion(getFrame(dt));
	}
	
	public TextureRegion getFrame(float dt)
	{
		TextureRegion image;
		System.out.println(timer);
		if(timer >= 0.1f)
		{
			if(flagImage == 0)
			{
				image = mario[1];
				flagImage = 1;
			}
			else if(flagImage == 1)
			{
				image = mario[2];
				flagImage = 2;
			}
			
			else
			{
				image = mario[0];
				flagImage = 0;
			}
			timer = 0;
		}
		else 
			image = mario[flagImage];
		return image;
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
