package com.mygdx.mariobrosclone.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.mariobrosclone.Screens.PlayScreen;
import com.mygdx.mariobrosclone.Sprites.Mario;

public abstract class Enemy extends Sprite{
	
	World world;
	PlayScreen screen;
	public Vector2 velocity;
	public Body b2body;
	
	public Enemy(PlayScreen screen, float x, float y)
	{
		this.world = screen.getWorld();
		this.screen = screen;
		setPosition(x, y);
		createEnemy();
		velocity = new Vector2(-1, -2);
		b2body.setActive(false);
	}
	
	public abstract void onEnemyHit(Enemy enemy);
	protected abstract void createEnemy();
	public abstract void hitOnHead(Mario mario);
	public abstract void update(float dt);
	public void reverseVelocity(boolean x, boolean y)
	{
		if(x)
			velocity.x = -velocity.x;
		if(y)
			velocity.y = -velocity.y;
	}
	
}
