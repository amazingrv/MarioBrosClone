package com.mygdx.mariobrosclone.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mariobrosclone.MarioBrosClone;

public class Hud implements Disposable{
	public Stage stage;
	Viewport viewport;
	private OrthographicCamera cam;
	public Integer worldTimer;
	float timeCount;
	static Integer score;
	
	Label countdownLabel;
	static Label scoreLabel;
	Label timeLabel;
	Label levelLabel;
	Label worldLabel;
	Label marioLabel;
	
	public Hud(SpriteBatch batch)
	{
		worldTimer = 300;
		timeCount = 0;
		score = 0;
		cam = new OrthographicCamera(); 
		viewport = new FitViewport(MarioBrosClone.V_WIDTH, MarioBrosClone.V_HEIGHT, cam);
		stage = new Stage(viewport, batch);	
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		marioLabel = new Label("MARIO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		table.add(marioLabel).expandX().padTop(10);
		table.add(worldLabel).expandX().padTop(10);
		table.add(timeLabel).expandX().padTop(10);
		table.row();
		table.add(scoreLabel).expandX();
		table.add(levelLabel).expandX();
		table.add(countdownLabel).expandX();
		
		stage.addActor(table);
	}
	
	public void update(float dt)
	{
		timeCount += dt;
		if(timeCount >= 1)
		{
			worldTimer--;
			countdownLabel.setText(String.format("%03d", worldTimer));	
			timeCount = 0;
		}
	}
	
	public static void addScore(int val)
	{
		score += val;
		scoreLabel.setText(String.format("%06d", score));
	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}
	
}
