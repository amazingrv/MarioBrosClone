package com.mygdx.mariobrosclone.Sprites.Items;

import com.badlogic.gdx.math.Vector2;

public class ItemDef {

	public Vector2 position;
	public Class<?> type;
	public ItemDef(Class<?> type, Vector2 position)
	{
		this.type = type;
		this.position = position;
	}

}
