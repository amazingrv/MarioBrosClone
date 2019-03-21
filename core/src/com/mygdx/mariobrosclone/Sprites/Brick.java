package com.mygdx.mariobrosclone.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.mariobrosclone.MarioBrosClone;
import com.mygdx.mariobrosclone.Scenes.Hud;
import com.mygdx.mariobrosclone.Screens.PlayScreen;

public class Brick extends InteractiveTileObject {
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
		// TODO Auto-generated method stub
			Gdx.app.log("Brick","Hit");
			setCategoryFilter(MarioBrosClone.DESTROYED_BIT);
			getCell().setTile(null);
			Hud.addScore(250);
			MarioBrosClone.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
		}
		MarioBrosClone.manager.get("audio/sounds/bump.wav", Sound.class).play();
	}
}
