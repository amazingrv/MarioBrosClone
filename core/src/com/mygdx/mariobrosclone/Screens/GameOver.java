package com.mygdx.mariobrosclone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mariobrosclone.MarioBrosClone;

public class GameOver extends Sprite implements Screen{

	Viewport viewport;
	Stage stage;
	MarioBrosClone game;
	
	TextureAtlas atlas;
	Animation marioRun;
	Image[] mario;
	
	float timer;
	
	public GameOver(MarioBrosClone game) {
		// TODO Auto-generated constructor stub
		mario = new Image[3]; 
		timer = 0;
		
		this.game = game;
		viewport = new FitViewport(MarioBrosClone.V_WIDTH, MarioBrosClone.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, ((MarioBrosClone) game).batch);
		atlas = new TextureAtlas("Mario_and_Enemies2.atlas");
		/*
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i=1; i<4 ; i++)
			frames.add(new TextureRegion(atlas.findRegion("little_mario"),i*16, 0, 16, 16));
		marioRun = new Animation(0.1f, frames);
		frames.clear();
		*/
		mario[0] = new Image(new TextureRegion(atlas.findRegion("little_mario"), 16, 0, 16, 16));
		mario[1] = new Image(new TextureRegion(atlas.findRegion("little_mario"), 32, 0, 16, 16));
		mario[2] = new Image(new TextureRegion(atlas.findRegion("little_mario"), 48, 0, 16, 16));
		
		Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
		Label gameOverLabel = new Label("GAME OVER", font);
		Label playAgainLabel = new Label("Press 'S' Key to Play Again", font);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
				
		table.add(gameOverLabel).expandX().center();
		table.row();
		
		//mario lives
		table.add(playAgainLabel).expandX().center();
		table.add(getFrame(timer));
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
		/*game.batch.begin();
		draw(game.batch);
		game.batch.end();*/
		}
	
	public void update(float dt)
	{
		if(dt >= 0.3f)
			timer = dt;
		//handleInput
		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			game.setScreen(new PlayScreen((MarioBrosClone)game));
			dispose();
		}
		
		/*setPosition(50/MarioBrosClone.PPM, 50/MarioBrosClone.PPM);
		setRegion(getFrame(dt));
	*/}
	
	public Image getFrame(float dt)
	{
		Image image;
		
		image = ;
		
		return region;
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
