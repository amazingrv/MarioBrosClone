package com.mygdx.mariobrosclone.Sprites.Enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.mariobrosclone.MarioBrosClone;
import com.mygdx.mariobrosclone.Screens.PlayScreen;
import com.mygdx.mariobrosclone.Sprites.Mario;
import com.mygdx.mariobrosclone.Sprites.Enemies.Turtle.State;

public class Goomba extends Enemy{

		float stateTime;
		Animation<TextureRegion> walkAnimation;
		Array<TextureRegion> frames;
		
		boolean setToDestroy;
		boolean destroyed;
		
	public Goomba(PlayScreen screen, float x, float y)
	{
		super(screen, x, y);
		frames = new Array<TextureRegion>();
		for(int i=0 ; i<2 ; i++)
			frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i*16, 0, 16, 16));
		walkAnimation = new Animation<TextureRegion>(0.1f, frames);
		stateTime = 0;
		
		setBounds(getX(), getY(), .16f, .16f);
		
		setToDestroy = false;
		destroyed = false;
	}
	public void update(float dt)
	{
		stateTime += dt;
		if(setToDestroy && !destroyed)
		{
			world.destroyBody(b2body);
			destroyed = true;
			setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"), 32, 0, 16, 16));
			stateTime = 0;
		}
		else if(!destroyed)
		{
			b2body.setLinearVelocity(velocity);
			setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
			setRegion(walkAnimation.getKeyFrame(stateTime, true));
		}
	
	}

	@Override
	protected void createEnemy() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(getX(), getY());	//bdef.position.set(50/MarioBrosClone.PPM, 50/MarioBrosClone.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
 		
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(6 / MarioBrosClone.PPM);
		fdef.filter.categoryBits = MarioBrosClone.ENEMY_BIT;
		fdef.filter.maskBits = MarioBrosClone.GROUND_BIT | MarioBrosClone.BRICK_BIT |
				MarioBrosClone.COIN_BIT | MarioBrosClone.MARIO_BIT |
				MarioBrosClone.OBJECT_BIT | MarioBrosClone.ENEMY_BIT;
		
		fdef.shape = shape;
		b2body.createFixture(fdef).setUserData(this);;
		
		//head
		PolygonShape head = new PolygonShape();
		Vector2[] vertice = new Vector2[4];
		vertice[0] = new Vector2(-5, 8).scl(1/MarioBrosClone.PPM);
		vertice[1] = new Vector2(5, 8).scl(1/MarioBrosClone.PPM);
		vertice[2] = new Vector2(-3, 3).scl(1/MarioBrosClone.PPM);
		vertice[3] = new Vector2(3, 3).scl(1/MarioBrosClone.PPM);
		head.set(vertice);
		
		fdef.shape = head;
		fdef.restitution = 0.5f;	//bounce
		fdef.filter.categoryBits = MarioBrosClone.ENEMY_HEAD_BIT;
		b2body.createFixture(fdef).setUserData(this);
	}
	@Override
	public void hitOnHead(Mario mario) {
		// TODO Auto-generated method stub
		setToDestroy = true;
		//world.destroyBody(b2body);		//doesnt respond
		MarioBrosClone.manager.get("audio/sounds/stomp.wav", Sound.class).play();
		
	}
	
	public void draw(Batch batch)
	{
		if(!destroyed || stateTime < 1)
			super.draw(batch);
	}
	public void onEnemyHit(Enemy enemy)
	{
		if(enemy instanceof Turtle && ((Turtle)enemy).getCurrentState() == Turtle.State.MOVING_SHELL)
			setToDestroy = true;
		else
			reverseVelocity(true, false);
			
	}
}
