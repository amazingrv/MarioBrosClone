package com.mygdx.mariobrosclone.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.mariobrosclone.MarioBrosClone;
import com.mygdx.mariobrosclone.Screens.PlayScreen;
import com.mygdx.mariobrosclone.Sprites.Enemies.Enemy;
import com.mygdx.mariobrosclone.Sprites.Enemies.Turtle;

public class Mario extends Sprite {
	public enum State {
		FALLING, JUMPING, STANDING, GROWING, RUNNING, CLIMBING, SHRINKING, DEAD, WON
	};// shrinking not defined

	public State currentState, previousState;
	public World world;
	public Body b2body;
	TextureRegion marioStand;
	TextureRegion marioDead;
	TextureRegion marioClimb, bigMarioClimb;

	boolean runGrowAnimation, runShrinkAnimation;
	public boolean marioIsBig;
	boolean marioIsDead;

	TextureRegion marioJump;
	float stateTimer;
	boolean runningRight;
	boolean timeToDefineBigMario;
	boolean timeToRedefineMario;

	Animation marioRun;
	TextureRegion bigMarioStand;
	TextureRegion bigMarioJump;
	Animation bigMarioRun;
	Animation shrinkMario, growMario;

	// flag var's
	float flagPosition;
	boolean climb, completeLevel;
	boolean marioWins;

	final float JumpVelocity = 4f;
	final float RightVelocity = 0.1f;
	final float LeftVelocity = -0.1f;
	final int maxRightVelocity = 2;
	final int maxLeftVelocity = -2;

	public Mario(PlayScreen screen) {
		this.world = screen.getWorld();
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		runningRight = true;

		Array<TextureRegion> frames = new Array<TextureRegion>();
		for (int i = 1; i < 4; i++)
			frames.add(new TextureRegion(screen.getAtlas().findRegion("little_mario"), i * 16, 0, 16, 16));
		marioRun = new Animation(0.1f, frames);
		frames.clear();

		for (int i = 1; i < 4; i++)
			frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), i * 16, 0, 16, 32));
		bigMarioRun = new Animation(0.1f, frames);
		frames.clear();

		// grawing mario animation
		frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 240, 0, 16, 32));
		frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32));
		frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 240, 0, 16, 32));
		frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32));
		growMario = new Animation(0.2f, frames);
		frames.clear();

		/*
		 * frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 240,
		 * 0, 16, 32)); frames.add(new
		 * TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32));
		 * frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 240,
		 * 0, 16, 32)); frames.add(new
		 * TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32));
		 * shrinkMario = new Animation(0.2f, frames); frames.clear();
		 */

		marioClimb = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 128, 0, 16, 16);

		bigMarioClimb = new TextureRegion(screen.getAtlas().findRegion("big_mario"), 128, 0, 16, 16);

		marioDead = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 96, 0, 16, 16);

		marioJump = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 5 * 16, 0, 16, 16);

		bigMarioJump = new TextureRegion(screen.getAtlas().findRegion("big_mario"), 5 * 16, 0, 16, 32);

		marioStand = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 0, 0, 16, 16);
		bigMarioStand = new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32);
		createMario();
		setBounds(0, 0, 16 / MarioBrosClone.PPM, 16 / MarioBrosClone.PPM);
		setRegion(marioStand);

		runGrowAnimation = false;
		marioIsBig = false;

		climb = completeLevel = marioWins = false;
	}

	public void grow() {
		runGrowAnimation = true;
		marioIsBig = true;
		timeToDefineBigMario = true;
		setBounds(getX(), getY(), getWidth(), getHeight() * 2);
		MarioBrosClone.manager.get("audio/sounds/powerup.wav", Sound.class).play();
	}

	public void shrink() {

		// MarioBrosClone.manager.get("audio/sounds/powerup.wav", Sound.class).play();
	}

	public void createMario() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(32 / MarioBrosClone.PPM, 32 / MarioBrosClone.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;

		b2body = world.createBody(bdef);

		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(6 / MarioBrosClone.PPM);
		fdef.filter.categoryBits = MarioBrosClone.MARIO_BIT;
		fdef.filter.maskBits = MarioBrosClone.GROUND_BIT | MarioBrosClone.BRICK_BIT | MarioBrosClone.COIN_BIT
				| MarioBrosClone.ENEMY_BIT | MarioBrosClone.OBJECT_BIT | MarioBrosClone.ENEMY_HEAD_BIT
				| MarioBrosClone.ITEM_BIT | MarioBrosClone.FLAG_POLE_BIT;

		fdef.shape = shape;
		b2body.createFixture(fdef).setUserData(this);
		EdgeShape head = new EdgeShape();
		head.set(new Vector2(-2 / MarioBrosClone.PPM, 6 / MarioBrosClone.PPM),
				new Vector2(2 / MarioBrosClone.PPM, 6 / MarioBrosClone.PPM));
		fdef.shape = head;
		fdef.isSensor = true;
		fdef.filter.categoryBits = MarioBrosClone.MARIO_HEAD_BIT;
		b2body.createFixture(fdef).setUserData(this);

	}

	public void update(float dt) {
		if (!marioWins && !marioIsDead)
			handleInput(dt);
		if (completeLevel)
			finishLevel();

		if (marioIsBig)// || runShrinkAnimation
			setPosition(b2body.getPosition().x - getWidth() / 2,
					b2body.getPosition().y - getHeight() / 2 - 6 / MarioBrosClone.PPM);
		else
			setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(dt));

		if (timeToDefineBigMario)
			createBigMario();
		if (timeToRedefineMario)
			recreateMario();
		if (b2body.getPosition().y < 0 && !isDead()) {
			marioIsDead = true;
			die();
		}

	}

	private void recreateMario() {
		Vector2 position = b2body.getPosition();
		world.destroyBody(b2body);
		// TODO Auto-generated method stub
		BodyDef bdef = new BodyDef();
		bdef.position.set(position);
		bdef.type = BodyDef.BodyType.DynamicBody;

		b2body = world.createBody(bdef);

		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(6 / MarioBrosClone.PPM);
		fdef.filter.categoryBits = MarioBrosClone.MARIO_BIT;
		fdef.filter.maskBits = MarioBrosClone.GROUND_BIT | MarioBrosClone.BRICK_BIT | MarioBrosClone.COIN_BIT
				| MarioBrosClone.ENEMY_BIT | MarioBrosClone.OBJECT_BIT | MarioBrosClone.ENEMY_HEAD_BIT
				| MarioBrosClone.ITEM_BIT | MarioBrosClone.FLAG_POLE_BIT;

		fdef.shape = shape;
		b2body.createFixture(fdef).setUserData(this);
		EdgeShape head = new EdgeShape();
		head.set(new Vector2(-2 / MarioBrosClone.PPM, 6 / MarioBrosClone.PPM),
				new Vector2(2 / MarioBrosClone.PPM, 6 / MarioBrosClone.PPM));
		fdef.shape = head;
		fdef.isSensor = true;
		fdef.filter.categoryBits = MarioBrosClone.MARIO_HEAD_BIT;
		b2body.createFixture(fdef).setUserData(this);

		timeToRedefineMario = false;
	}

	public void createBigMario() {
		Vector2 position = b2body.getPosition();
		world.destroyBody(b2body);

		BodyDef bdef = new BodyDef();
		bdef.position.set(position.add(0, 10 / MarioBrosClone.PPM));
		bdef.type = BodyDef.BodyType.DynamicBody;

		b2body = world.createBody(bdef);

		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(6 / MarioBrosClone.PPM);
		fdef.filter.categoryBits = MarioBrosClone.MARIO_BIT;
		fdef.filter.maskBits = MarioBrosClone.GROUND_BIT | MarioBrosClone.BRICK_BIT | MarioBrosClone.COIN_BIT
				| MarioBrosClone.ENEMY_BIT | MarioBrosClone.OBJECT_BIT | MarioBrosClone.ENEMY_HEAD_BIT
				| MarioBrosClone.ITEM_BIT | MarioBrosClone.FLAG_POLE_BIT;

		fdef.shape = shape;
		b2body.createFixture(fdef).setUserData(this);
		shape.setPosition(new Vector2(0, -14 / MarioBrosClone.PPM));
		b2body.createFixture(fdef).setUserData(this);
		EdgeShape head = new EdgeShape();
		head.set(new Vector2(-2 / MarioBrosClone.PPM, 6 / MarioBrosClone.PPM),
				new Vector2(2 / MarioBrosClone.PPM, 6 / MarioBrosClone.PPM));
		fdef.shape = head;
		fdef.isSensor = true;
		fdef.filter.categoryBits = MarioBrosClone.MARIO_HEAD_BIT;
		b2body.createFixture(fdef).setUserData(this);

		timeToDefineBigMario = false;
	}

	public TextureRegion getFrame(float dt) {
		currentState = getState();
		TextureRegion region;
		switch (currentState) {
		case DEAD:
			region = marioDead;
			break;
		case GROWING:
			region = (TextureRegion) growMario.getKeyFrame(stateTimer);
			if (growMario.isAnimationFinished(stateTimer)) {
				runGrowAnimation = false;
			}

			break;
		case JUMPING:
			region = marioIsBig ? bigMarioJump : marioJump;
			break;
		case RUNNING:
			region = marioIsBig ? (TextureRegion) bigMarioRun.getKeyFrame(stateTimer, true)
					: (TextureRegion) marioRun.getKeyFrame(stateTimer, true);
			break;
		case CLIMBING:
			region = marioIsBig ? bigMarioClimb : marioClimb;
			break;
		case FALLING:
		case STANDING:
		case WON:
		default:
			region = marioIsBig ? bigMarioStand : marioStand;
		}
		if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
			region.flip(true, false);
			runningRight = false;
		}
		if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
			region.flip(true, false);
			runningRight = true;
		}

		stateTimer = currentState == previousState ? stateTimer + dt : 0;
		previousState = currentState;
		return region;

	}

	public State getState() {
		if (marioIsDead)
			return State.DEAD;
		else if (runGrowAnimation)
			return State.GROWING;
		else if (climb) {
			if (stateTimer >= 2.5f) {
				climb = false;
				stateTimer = 0;
			}
			return State.CLIMBING;
		} else if (marioWins)
			return State.WON;
		else if (b2body.getLinearVelocity().y > 0
				|| (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
			return State.JUMPING;
		else if (b2body.getLinearVelocity().y < 0)
			return State.FALLING;
		else if (b2body.getLinearVelocity().x != 0)
			return State.RUNNING;
		else
			return State.STANDING;
	}

	public void hit(Enemy enemy) {
		if (enemy instanceof Turtle && ((Turtle) enemy).getCurrentState() == Turtle.State.STANDING_SHELL) {
			((Turtle) enemy).kick(this.getX() <= enemy.getX() ? Turtle.KICK_RIGHT : Turtle.KICK_LEFT);
		} else {
			if (marioIsBig) {
				timeToRedefineMario = true;

				marioIsBig = false;
				setBounds(getX(), getY(), getWidth(), getHeight() / 2);
				MarioBrosClone.manager.get("audio/sounds/powerdown.wav", Sound.class).play();
			} else {
				marioIsDead = true;
				die();
			}
		}
	}

	public boolean isDead() {
		return marioIsDead;
	}

	public float getStateTimer() {
		return stateTimer;
	}

	public float getPosition() {
		return b2body.getPosition().x;
	}

	void die() {
		if (marioIsDead) {
			MarioBrosClone.manager.get("audio/music/mario_music.ogg", Music.class).stop();
			MarioBrosClone.manager.get("audio/sounds/mariodie.wav", Sound.class).play();
			Filter filter = new Filter();
			filter.maskBits = MarioBrosClone.NOTHING_BIT;
			for (Fixture fixture : b2body.getFixtureList())
				fixture.setFilterData(filter);
			if (b2body.getPosition().y < 0)
				b2body.applyLinearImpulse(new Vector2(0, 6f), b2body.getWorldCenter(), true);
			else
				b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
		}
	}

	public void goal(float flagPosition) {
		climb = true; // set the climb texture
		completeLevel = true; // run the complete sequence
		this.flagPosition = flagPosition; // flagPosition

	}

	public void finishLevel() {
		System.out.println("flag height = " + flagPosition + "\tB2body : " + b2body.getPosition().y);
		if (climb) {
			System.out.println(getState());
			if (b2body.getPosition().y <= 60 / MarioBrosClone.PPM) {
				runningRight = false;
				b2body.setTransform(flagPosition + 16 / MarioBrosClone.PPM, b2body.getPosition().y, 0);
				// b2body.setType(BodyDef.BodyType.StaticBody);
			} else {
				System.out.print("y is bigger");
				runningRight = true;
				b2body.setTransform(flagPosition + 4 / MarioBrosClone.PPM, b2body.getPosition().y, 0);
			}
			b2body.setLinearVelocity(new Vector2(0, -0.70f)); // to get the mario down
		} else {
			System.out.println(getState());
			if (getX() < 32.70f)
				b2body.applyLinearImpulse(new Vector2(b2body.getMass() * (1.0f - b2body.getLinearVelocity().x), 0.0f),
						b2body.getWorldCenter(), true);
			else {
				marioWins = true;
			}
		}

	}

	public void handleInput(float deltaTime) {
		if (currentState != Mario.State.DEAD) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
				if (getState() != currentState.JUMPING) {

					b2body.applyLinearImpulse(new Vector2(0, JumpVelocity), b2body.getWorldCenter(), true);
					currentState = State.JUMPING;
					if (marioIsBig)
						MarioBrosClone.manager.get("audio/sounds/jump_big.wav", Sound.class).play();
					else
						MarioBrosClone.manager.get("audio/sounds/jump_small.wav", Sound.class).play();

				}
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && b2body.getLinearVelocity().x <= maxRightVelocity)
				b2body.applyLinearImpulse(new Vector2(RightVelocity, 0), b2body.getWorldCenter(), true);
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && b2body.getLinearVelocity().x >= maxLeftVelocity)
				b2body.applyLinearImpulse(new Vector2(LeftVelocity, 0), b2body.getWorldCenter(), true);
		}
	}
}
