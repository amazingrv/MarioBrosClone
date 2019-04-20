package com.mygdx.mariobrosclone.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.mariobrosclone.MarioBrosClone;
import com.mygdx.mariobrosclone.Sprites.Mario;
import com.mygdx.mariobrosclone.Sprites.Enemies.Enemy;
import com.mygdx.mariobrosclone.Sprites.Items.Item;
import com.mygdx.mariobrosclone.Sprites.Tiles.InteractiveTileObject;

public class WorldContactListner implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

		switch (cDef) {
		case MarioBrosClone.MARIO_HEAD_BIT | MarioBrosClone.BRICK_BIT:
		case MarioBrosClone.MARIO_HEAD_BIT | MarioBrosClone.COIN_BIT:
			if (fixA.getFilterData().categoryBits == MarioBrosClone.BRICK_BIT
					|| fixA.getFilterData().categoryBits == MarioBrosClone.COIN_BIT)
				((InteractiveTileObject) fixA.getUserData()).onHeadHit((Mario) fixB.getUserData());
			else
				((InteractiveTileObject) fixB.getUserData()).onHeadHit((Mario) fixA.getUserData());
			break;

		case MarioBrosClone.ENEMY_HEAD_BIT | MarioBrosClone.MARIO_BIT:
			if (fixA.getFilterData().categoryBits == MarioBrosClone.ENEMY_HEAD_BIT)
				((Enemy) fixA.getUserData()).hitOnHead((Mario) fixB.getUserData());
			else
				((Enemy) fixB.getUserData()).hitOnHead((Mario) fixA.getUserData());
			break;

		case MarioBrosClone.ENEMY_BIT | MarioBrosClone.OBJECT_BIT:
			if (fixA.getFilterData().categoryBits == MarioBrosClone.ENEMY_BIT)
				((Enemy) fixA.getUserData()).reverseVelocity(true, false);
			else
				((Enemy) fixB.getUserData()).reverseVelocity(true, false);
			break;
		case MarioBrosClone.MARIO_BIT | MarioBrosClone.ENEMY_BIT:
			System.out.println("Mario Dead");
			if (fixA.getFilterData().categoryBits == MarioBrosClone.MARIO_BIT)
				((Mario) fixA.getUserData()).hit((Enemy) fixB.getUserData());
			else
				((Mario) fixB.getUserData()).hit((Enemy) fixA.getUserData());
			break;

		case MarioBrosClone.ENEMY_BIT | MarioBrosClone.ENEMY_BIT:
			((Enemy) fixA.getUserData()).onEnemyHit((Enemy) fixB.getUserData());
			((Enemy) fixB.getUserData()).onEnemyHit((Enemy) fixA.getUserData());
			break;

		case MarioBrosClone.ITEM_BIT | MarioBrosClone.OBJECT_BIT:
			if (fixA.getFilterData().categoryBits == MarioBrosClone.ITEM_BIT)
				((Item) fixA.getUserData()).reverseVelocity(true, false);
			else
				((Item) fixB.getUserData()).reverseVelocity(true, false);
			break;
		case MarioBrosClone.MARIO_BIT | MarioBrosClone.ITEM_BIT:
		case MarioBrosClone.MARIO_HEAD_BIT | MarioBrosClone.ITEM_BIT:
			if (fixA.getFilterData().categoryBits == MarioBrosClone.ITEM_BIT)
				((Item) fixA.getUserData()).used((Mario) fixB.getUserData());
			else
				((Item) fixB.getUserData()).used((Mario) fixA.getUserData());
			break;
		case MarioBrosClone.MARIO_BIT | MarioBrosClone.FLAG_POLE_BIT:
		case MarioBrosClone.MARIO_HEAD_BIT | MarioBrosClone.FLAG_POLE_BIT:
			if (fixA.getFilterData().categoryBits == MarioBrosClone.FLAG_POLE_BIT)
				((InteractiveTileObject) fixA.getUserData()).onHeadHit((Mario) fixB.getUserData());
			else
				((InteractiveTileObject) fixB.getUserData()).onHeadHit((Mario) fixA.getUserData());
			break;
		}

	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
