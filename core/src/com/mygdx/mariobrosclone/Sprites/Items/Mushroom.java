package com.mygdx.mariobrosclone.Sprites.Items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.mariobrosclone.MarioBrosClone;
import com.mygdx.mariobrosclone.Screens.PlayScreen;
import com.mygdx.mariobrosclone.Sprites.Mario;

public class Mushroom extends Item {

	public Mushroom(PlayScreen screen, float x, float y) {
		super(screen, x, y);
		setRegion(screen.getAtlas().findRegion("mushroom"), 0, 0, 16, 16);
		velocity = new Vector2(1, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createItem() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(getX(), getY());	//bdef.position.set(50/MarioBrosClone.PPM, 50/MarioBrosClone.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
 		
		body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(6 / MarioBrosClone.PPM);	
		fdef.shape = shape;
		fdef.filter.categoryBits = MarioBrosClone.ITEM_BIT;
		fdef.filter.maskBits = MarioBrosClone.OBJECT_BIT |MarioBrosClone.GROUND_BIT |
				MarioBrosClone.MARIO_BIT|MarioBrosClone.COIN_BIT |MarioBrosClone.BRICK_BIT  ;  
		body.createFixture(fdef).setUserData(this);		
	}
	
	public void update(float dt)
	{
		super.update(dt);
		setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
		velocity.y = body.getLinearVelocity().y;
		body.setLinearVelocity(velocity);
	}
	@Override
	public void used(Mario mario) {
		destroy();
		if(!mario.marioIsBig)			//if mario is already big dont consume mushrrom
			mario.grow();
		
	}

}
