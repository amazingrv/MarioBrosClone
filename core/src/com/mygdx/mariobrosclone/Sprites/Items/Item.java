package com.mygdx.mariobrosclone.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.mariobrosclone.MarioBrosClone;
import com.mygdx.mariobrosclone.Screens.PlayScreen;
import com.mygdx.mariobrosclone.Sprites.Mario;

public abstract class Item extends Sprite {
	PlayScreen screen;
	World world;
	Vector2 velocity;
	boolean toDestroy;
	boolean destroyed;
	Body body;

	public Item(PlayScreen screen, float x, float y)
	{
		this.screen = screen;
		this.world = screen.getWorld();
		setPosition(x, y);
		setBounds(getX(), getY(), 16/MarioBrosClone.PPM, 16/MarioBrosClone.PPM);
		
		createItem();
		toDestroy = false;
		destroyed = false;
	}
	
	public abstract void createItem();
	public abstract void used(Mario mario);
	public void update(float dt)
	{
		if(toDestroy && !destroyed)
		{
			world.destroyBody(body);
			destroyed = true;
		}
	}

	public void destroy()
	{
		toDestroy = true;
	}
	
	public void draw(Batch batch)
	{
		if(!destroyed)
			super.draw(batch);
	}
	public void reverseVelocity(boolean x, boolean y)
	{
		if(x)
			velocity.x = -velocity.x;
		if(y)
			velocity.y = -velocity.y;
	}
}
