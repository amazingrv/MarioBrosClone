package com.mygdx.mariobrosclone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mariobrosclone.Screens.StartScreen;

public class MarioBrosClone extends Game {
	public SpriteBatch batch;
	public static int V_WIDTH = 400;
	public static int V_HEIGHT = 200;
	public static float PPM = 100;

	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short MARIO_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short ITEM_BIT = 256;
	public static final short MARIO_HEAD_BIT = 512;
	public static final short FLAG_POLE_BIT = 1024;
	public static final short FLAG_BIT = 2048;

	public static AssetManager manager;

	@Override
	public void create() {
		batch = new SpriteBatch();

		manager = new AssetManager();
		manager.load("audio/music/mario_music.ogg", Music.class);
		manager.load("audio/sounds/coin.wav", Sound.class);
		manager.load("audio/sounds/bump.wav", Sound.class);
		manager.load("audio/sounds/breakblock.wav", Sound.class);
		manager.load("audio/sounds/jump_small.wav", Sound.class);
		manager.load("audio/sounds/jump_big.wav", Sound.class);
		manager.load("audio/sounds/powerup_spawn.wav", Sound.class);
		manager.load("audio/sounds/powerup.wav", Sound.class);
		manager.load("audio/sounds/powerdown.wav", Sound.class);
		manager.load("audio/sounds/stomp.wav", Sound.class);
		manager.load("audio/sounds/mariodie.wav", Sound.class);
		manager.load("audio/sounds/gameover.wav", Sound.class);
		manager.finishLoading();
		setScreen(new StartScreen(this));

	}

	@Override
	public void render() {
		super.render();
		if (manager.update()) {

		}
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		manager.dispose();

	}
}
