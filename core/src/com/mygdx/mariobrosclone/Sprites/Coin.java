package com.mygdx.mariobrosclone.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mgdx.mariobrosclone.Items.ItemDef;
import com.mgdx.mariobrosclone.Items.Mushroom;
import com.mygdx.mariobrosclone.MarioBrosClone;
import com.mygdx.mariobrosclone.Scenes.Hud;
import com.mygdx.mariobrosclone.Screens.PlayScreen;

public class Coin extends InteractiveTileObject {
	private static TiledMapTileSet tileSet;
	private final int BLANK_COIN = 28;
	public Coin(PlayScreen screen, MapObject object)
	{
		super(screen, object);
		tileSet = map.getTileSets().getTileSet("tileset_gutter");
		fixture.setUserData(this);
		setCategoryFilter(MarioBrosClone.COIN_BIT);
	}

	@Override
	public void onHeadHit(Mario mario) {
		// TODO Auto-generated method stub
		Gdx.app.log("Coin","Hit");
		if(getCell().getTile().getId() == BLANK_COIN)
		{
			MarioBrosClone.manager.get("audio/sounds/bump.wav", Sound.class).play();
		}
		else
		{
			Hud.addScore(150);
			if(object.getProperties().containsKey("mushroom"))
			{
				screen.spawnItem(new ItemDef(Mushroom.class, new Vector2(body.getPosition().x, body.getPosition().y + 16 / MarioBrosClone.PPM)));
				MarioBrosClone.manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();
			}
			else
				MarioBrosClone.manager.get("audio/sounds/coin.wav", Sound.class).play();
		}
		getCell().setTile(tileSet.getTile(BLANK_COIN));
		
		
	}
}

