package com.mygdx.mariobrosclone.Sprites.Tiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();

		bdef.type = BodyDef.BodyType.StaticBody;
		System.out.print(bounds.getX() + " " + bounds.getWidth());
		bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / MarioBrosClone.PPM,
				(bounds.getY() + bounds.getHeight() / 2) / MarioBrosClone.PPM);
		body = world.createBody(bdef);
		shape.setAsBox((bounds.getWidth() / 2) / MarioBrosClone.PPM, (bounds.getHeight() / 2) / MarioBrosClone.PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = MarioBrosClone.FLAG_POLE_BIT;
		// fdef.filter.maskBits = MarioBrosClone.MARIO_BIT |
		// MarioBrosClone.MARIO_HEAD_BIT | MarioBrosClone.GROUND_BIT
		// | MarioBrosClone.OBJECT_BIT;
		fixture = body.createFixture(fdef);
		fixture.setUserData(this);
		createFlag();
		flag = new Flag(screen, body.getPosition().x, body.getPosition().y, this);
	}

	private void createFlag() {
		Vector2 pos = new Vector2(body.getPosition().x - 8 / MarioBrosClone.PPM,
				body.getPosition().y + 20 / MarioBrosClone.PPM);
		Vector2 dims = new Vector2(16 / MarioBrosClone.PPM, 16 / MarioBrosClone.PPM);

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();

		bdef.type = BodyType.StaticBody;
		bdef.position.set(pos);

		flagStart = world.createBody(bdef);
		shape.setAsBox(dims.x / 2, dims.y / 2);
		fdef.shape = shape;
		fdef.filter.categoryBits = MarioBrosClone.FLAG_BIT;
		flagStart.createFixture(fdef).setUserData(this);

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
				System.out.println("\n" + pole.flagStart.getPosition().x + " pullFlag() -> goal()mario");
				mario.goal(pole.flagStart.getPosition().x);
			}
		}
	}
}
