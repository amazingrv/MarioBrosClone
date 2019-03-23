package com.mygdx.mariobrosclone.Sprites.Tiles;

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
import com.mygdx.mariobrosclone.Sprites.Mario;

public class Brick extends InteractiveTileObject {
	
	public Brick(PlayScreen screen, MapObject object)
	{
		super(screen, object);
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();

		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / MarioBrosClone.PPM, (bounds.getY() + bounds.getHeight() / 2) / MarioBrosClone.PPM);
		body = world.createBody(bdef);
		shape.setAsBox((bounds.getWidth() / 2) / MarioBrosClone.PPM, (bounds.getHeight() / 2) / MarioBrosClone.PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = MarioBrosClone.BRICK_BIT;
		fixture = body.createFixture(fdef);
		fixture.setUserData(this);		 
	}

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
			MarioBrosClone.manager.get("audio/sounds/bump.wav", Sound.class).play();
			
		}

	}

}
