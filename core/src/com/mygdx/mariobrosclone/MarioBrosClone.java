package com.mygdx.mariobrosclone;

import com.mygdx.mariobrosclone.Screens.PlayScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MarioBrosClone extends Game {
	public SpriteBatch batch;
	public static int V_WIDTH = 400; 
	public static int V_HEIGHT = 200;
	public static float PPM = 100;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
		
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		
	}
}
