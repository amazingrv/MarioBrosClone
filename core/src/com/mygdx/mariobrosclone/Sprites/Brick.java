package com.mygdx.mariobrosclone.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.utils.Array;
import com.mygdx.mariobrosclone.MarioBrosClone;
import com.mygdx.mariobrosclone.Scenes.Hud;
import com.mygdx.mariobrosclone.Screens.PlayScreen;

public class Brick extends InteractiveTileObject {
	
	//temp
	Array<TextureRegion> frames;
	Animation brickBump;
	
	public Brick(PlayScreen screen, MapObject object)
	{
		super(screen, object);
		fixture.setUserData(this);
		setCategoryFilter(MarioBrosClone.BRICK_BIT);
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
			body.
		}
	}
}
