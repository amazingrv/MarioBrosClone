package com.mygdx.mariobrosclone.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.mariobrosclone.MarioBrosClone;
import com.mygdx.mariobrosclone.Scenes.Hud;
import com.mygdx.mariobrosclone.Screens.PlayScreen;

public class Brick extends InteractiveTileObject {

	Vector2 originalPosition, targetPosition, movablePosition;

	public Brick(PlayScreen screen, MapObject object)
	{
		super(screen, object);
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();

		bdef.type = BodyDef.BodyType.KinematicBody;
		bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / MarioBrosClone.PPM, (bounds.getY() + bounds.getHeight() / 2) / MarioBrosClone.PPM);
		body = world.createBody(bdef);
		shape.setAsBox((bounds.getWidth() / 2) / MarioBrosClone.PPM, (bounds.getHeight() / 2) / MarioBrosClone.PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = MarioBrosClone.BRICK_BIT;
		fixture = body.createFixture(fdef);
		fixture.setUserData(this);


		/*originalPosition = new Vector2(body.getPosition().x, body.getPosition().y);
		targetPosition  = originalPosition;
		movablePosition = new Vector2(body.getPosition().x, body.getPosition().y + 0.2f);
	*/}

	@Override
	public void onHeadHit(Mario mario) {
		if(mario.marioIsBig)
		{
			Gdx.app.log("Brick","Hit");
			setCategoryFilter(MarioBrosClone.DESTROYED_BIT);
			getCell().setTile(null);
			Hud.addScore(250);
			MarioBrosClone.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
		}
		else
		{
			//targetPosition = movablePosition;
			//playshound

		}

	}

}
