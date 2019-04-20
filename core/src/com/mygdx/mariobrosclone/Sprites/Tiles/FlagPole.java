package com.mygdx.mariobrosclone.Sprites.Tiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.mygdx.mariobrosclone.MarioBrosClone;
import com.mygdx.mariobrosclone.Screens.PlayScreen;
import com.mygdx.mariobrosclone.Sprites.Mario;

public class FlagPole extends InteractiveTileObject {
	public static Flag flag;
	private Body flagStart;

	public FlagPole(PlayScreen screen, MapObject object) {
		super(screen, object);
		flag = null;
		flagStart = null;
		BodyDef bdef

		fixture.setUserData(this);
		createAnchor();
		flag = new Flag(screen, body.getPosition().x, body.getPosition().y, this);
	}

	private void createAnchor() {
		Vector2 position = new Vector2(body.getPosition().x - 8 / MarioBrosClone.PPM,
				body.getPosition().y + 64 / MarioBrosClone.PPM);
		Vector2 boxDims = new Vector2(16 / 2 / MarioBrosClone.PPM, 16 / 2 / MarioBrosClone.PPM);

		/*
		 * flagStart = BodyFactory.getInstance().makeBody(position, boxDims,
		 * MarioBrosClone.FLAG_BIT, BodyDef.BodyType.StaticBody);
		 */for (Fixture fixture : flagStart.getFixtureList())
			fixture.setUserData(this);

	}

	@Override
	public void onHeadHit(Mario mario) {
		flag.pullFlag(mario);
	}

	public void update(float dt) {
		flag.update(dt);
	}

	public void draw(Batch batch) {
		flag.completeLevel.draw();
	}

	public class Flag extends Actor {
		public final PlayScreen screen;
		public final World world;
		private final FlagPole pole;
		private boolean down;
		private Sprite flagSprite;
		Stage completeLevel;

		private Flag(PlayScreen screen, float x, float y, FlagPole pole) {
			this.screen = screen;
			this.world = screen.getWorld();
			this.pole = pole;
			down = false;

			flagSprite = new Sprite(new TextureRegion(screen.getAtlas().findRegion("flag"), 0, 0, 16, 16));
			flagSprite.setBounds(x - 15.9f / MarioBrosClone.PPM, y + 56 / MarioBrosClone.PPM, 16 / MarioBrosClone.PPM,
					16 / MarioBrosClone.PPM);
			setBounds(flagSprite.getX(), flagSprite.getY(), flagSprite.getWidth(), flagSprite.getHeight());
			makeFlag();
		}

		private void makeFlag() {
			MoveToAction flagSlide = new MoveToAction();
			flagSlide.setPosition(pole.flagStart.getPosition().x - 8.3f / MarioBrosClone.PPM,
					(32 + 3.5f) / MarioBrosClone.PPM);
			flagSlide.setDuration(1.5f);

			this.addAction(flagSlide);

			completeLevel = new Stage(screen.getViewport(), screen.getGame().batch);
			completeLevel.addActor(this);
		}

		void update(float dt) {
			if (down)
				completeLevel.act(dt);
		}

		@Override
		public void draw(Batch batch, float parentAlpha) {
			flagSprite.draw(batch);
		}

		@Override
		protected void positionChanged() {
			flagSprite.setPosition(getX(), getY());
		}

		public void pullFlag(Mario mario) {
			if (!down) {
				down = true;
				// mario.goal(pole.flagStart.getPosition().x);
			}
		}
	}

}
