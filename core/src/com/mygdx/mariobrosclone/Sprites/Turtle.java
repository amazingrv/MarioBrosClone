package com.mygdx.mariobrosclone.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.mariobrosclone.MarioBrosClone;
import com.mygdx.mariobrosclone.Screens.PlayScreen;

public class Turtle extends Enemy {

	public enum State{WALKING, STANDING_SHELL, MOVING_SHELL, DEAD};
	State currentState, previousState;
	float stateTime;
	Animation<TextureRegion> walkAnimation, shellAnimation;
	Array<TextureRegion> frames;
	
	 public static final int KICK_LEFT = -2;
	 public static final int KICK_RIGHT = 2;
	
	boolean setToDestroy;
	boolean destroyed;
	
	private float deadRotationDegrees;
	public Turtle(PlayScreen screen, float x, float y) {
		super(screen, x, y);
		stateTime = 0;
		
		frames = new Array<TextureRegion>();
		frames.add(new TextureRegion(screen.getAtlas().findRegion("turtle"), 0, 0, 16, 24));
		frames.add(new TextureRegion(screen.getAtlas().findRegion("turtle"), 16, 0 , 16, 24));
		walkAnimation = new Animation(0.2f, frames);
		frames.clear();
		
		frames.add(new TextureRegion(screen.getAtlas().findRegion("turtle"), 64, 0 , 16, 24));
		frames.add(new TextureRegion(screen.getAtlas().findRegion("turtle"), 80, 0 , 16, 24));
		shellAnimation = new Animation(0.2f, frames);
		frames.clear();
		
		deadRotationDegrees = 0;
		currentState = previousState = State.WALKING;
		setBounds(getX(), getY(), 16/MarioBrosClone.PPM, 24/MarioBrosClone.PPM);
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
		fdef.restitution = 1.5f;	//bounce
		fdef.filter.categoryBits = MarioBrosClone.ENEMY_HEAD_BIT;
		b2body.createFixture(fdef).setUserData(this);
		
	}
	@Override
	public void hitOnHead(Mario mario) {
		if(currentState != State.STANDING_SHELL) {
			currentState = State.STANDING_SHELL;
			velocity.x = 0;
		}
		else
		{
			kick(mario.getX() <= this.getX() ? KICK_RIGHT : KICK_RIGHT);
		}
	}
	
	public void kick(int speed)
	{
		velocity.x = speed;
		currentState = State.MOVING_SHELL;
	}
	@Override
	public void update(float dt) {
		setRegion(getFrame(dt));
		
		if(currentState == State.STANDING_SHELL && stateTime > 5) {
			currentState = State.WALKING;
			velocity.x = 1;
		}
		setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - 8/MarioBrosClone.PPM);
		if(currentState ==  State.DEAD)
		{
			deadRotationDegrees += 3;
			rotate(deadRotationDegrees);
			if(stateTime > 5 && !destroyed)
			{
				world.destroyBody(b2body);
				destroyed = true;
			}
		}
		else
			b2body.setLinearVelocity(velocity);
	}
	public State getCurrentState() {
		return currentState;
	}
	public TextureRegion getFrame(float dt)
	{
		TextureRegion region;
		
		switch(currentState){
			case STANDING_SHELL:
			case MOVING_SHELL:
				region = shellAnimation.getKeyFrame(stateTime, true);
				break;
			case WALKING:
			default:
				region = walkAnimation.getKeyFrame(stateTime, true);
				break;
		}
		
		if(velocity.x > 0 && region.isFlipX() == false)
			region.flip(true, false);
		if(velocity.x < 0 && region.isFlipX() == true)
			region.flip(true, false);
		
		stateTime = currentState == previousState ? stateTime +dt : 0;
		previousState = currentState;
		return region;
	}
	
	public void onEnemyHit(Enemy enemy)
	{
		if(enemy instanceof Turtle)
		{
			if(((Turtle)enemy).getCurrentState() == State.MOVING_SHELL && currentState !=State.MOVING_SHELL)
				killed();
			else if(currentState == State.MOVING_SHELL || ((Turtle)enemy).getCurrentState() == State.WALKING)
				return;
			else
				reverseVelocity(true, false);
		}
		else if(currentState != State.MOVING_SHELL)
			reverseVelocity(true, false);
		
	}
	public void killed() {
		currentState = State.DEAD;
		Filter filter = new Filter();
		filter.maskBits = MarioBrosClone.NOTHING_BIT;
		
		for(Fixture fixture: b2body.getFixtureList())
			fixture.setFilterData(filter);
		
		b2body.applyLinearImpulse(new Vector2(0, 5f), b2body.getWorldCenter(), true);
	}
}
