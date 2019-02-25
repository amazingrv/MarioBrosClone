package com.mygdx.mariobrosclone.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.mariobrosclone.MarioBrosClone;
import com.mygdx.mariobrosclone.Screens.PlayScreen;

public class Mario extends Sprite{
	public World world;
	public Body b2body;
	TextureRegion marioStand;
	
	public Mario(World world, PlayScreen screen)
	{
		super(screen.getAtlas().findRegion("little_mario"));
		this.world = world;
		createMario();
		marioStand = new TextureRegion(getTexture(), 0, 0, 16, 16);
		setBounds(0, 0, 16 / MarioBrosClone.PPM, 16 / MarioBrosClone.PPM);
		setRegion(marioStand);
	}
	
	public void createMario()
	{
		BodyDef bdef = new BodyDef();
		bdef.position.set(32 / MarioBrosClone.PPM, 32 / MarioBrosClone.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
 		
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(5 / MarioBrosClone.PPM);
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
	}
}
