package com.mygdx.mariobrosclone.Tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.mariobrosclone.MarioBrosClone;

public class BodyFactory {
	private static final short BASE_MASK = MarioBrosClone.GROUND_BIT | MarioBrosClone.COIN_BIT
			| MarioBrosClone.BRICK_BIT | MarioBrosClone.OBJECT_BIT;
	private static final short ENEMY_MASK = BASE_MASK | MarioBrosClone.MARIO_BIT | MarioBrosClone.ENEMY_BIT
			| MarioBrosClone.ENEMY_HEAD_BIT;
	private static final short MARIO_MASK = BASE_MASK | MarioBrosClone.ITEM_BIT | MarioBrosClone.ENEMY_BIT
			| MarioBrosClone.ENEMY_HEAD_BIT | MarioBrosClone.FLAG_POLE_BIT;

	private static final short FIREBALL_MASK = BASE_MASK | MarioBrosClone.ENEMY_BIT | MarioBrosClone.ENEMY_HEAD_BIT;
	private static final short ITEM_MASK = BASE_MASK | MarioBrosClone.MARIO_BIT;
	private static volatile BodyFactory thisInstance = null;
	private World world;

	private BodyFactory() {
	}

	public static BodyFactory getInstance() {
		synchronized (BodyFactory.class) {
			if (thisInstance == null)
				thisInstance = new BodyFactory();
			return thisInstance;
		}
	}

	public void init(World world) {
		this.world = world;
	}

	public Body makeBody(Vector2 position, float radius, int offset_y, short catBit) {
		Body body = makeBody(position, BodyDef.BodyType.DynamicBody);
		body = makeCircularFixture(body, radius, offset_y, catBit);
		return makeContactSensor(body, MarioBrosClone.MARIO_HEAD_BIT);
	}

	private Body makeBody(Vector2 position, BodyDef.BodyType type) {
		BodyDef bdef = new BodyDef();
		bdef.type = type;
		bdef.position.set(position);
		return world.createBody(bdef);
	}

	private Body makeCircularFixture(Body body, float radius, float offset_y, short catBit) {
		CircleShape shape = new CircleShape();
		shape.setRadius(radius / MarioBrosClone.PPM);
		body.createFixture(makeFixture(catBit, shape));
		shape.setPosition(new Vector2(0, offset_y / MarioBrosClone.PPM));
		body.createFixture(makeFixture(catBit, shape));
		shape.dispose();
		return body;
	}

	private Body makeContactSensor(Body body, short catBit) {
		return makeEdgeFixture(body, catBit);
	}

	private FixtureDef makeFixture(short catBit, Shape shape) {
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		if (catBit != MarioBrosClone.NOTHING_BIT)
			fdef.filter.categoryBits = catBit;
		switch (catBit) {
		case MarioBrosClone.MARIO_BIT:
			fdef.filter.maskBits = MARIO_MASK;
			break;
		case MarioBrosClone.MARIO_HEAD_BIT:
			fdef.filter.maskBits = MARIO_MASK;
			fdef.isSensor = true;
			break;
		case MarioBrosClone.ENEMY_BIT:
			fdef.filter.maskBits = ENEMY_MASK;
			break;
		case MarioBrosClone.ITEM_BIT:
			fdef.filter.maskBits = ITEM_MASK;
			break;
		/*
		 * case MarioBrosClone.FIREBALL_BIT: fdef.filter.maskBits = FIREBALL_MASK;
		 * fdef.restitution = 1; fdef.friction = 0; break;
		 */ default:
			break;
		}

		return fdef;
	}

	private Body makeEdgeFixture(Body body, short catBit) {
		EdgeShape shape = new EdgeShape();
		shape.set(new Vector2(-2, 6).scl(1 / MarioBrosClone.PPM), new Vector2(2, 6).scl(1 / MarioBrosClone.PPM));
		body.createFixture(makeFixture(catBit, shape));
		shape.dispose();
		return body;
	}

	public Body makeBody(Vector2 position, float radius, float restitution, short catBit) {
		Body body = makeBody(position, radius, catBit);

		if (catBit == MarioBrosClone.ENEMY_BIT)
			body = makeContactSensor(body, restitution, MarioBrosClone.ENEMY_HEAD_BIT);

		return body;
	}

	public Body makeBody(Vector2 position, float radius, short catBit) {
		Body body = makeBody(position, BodyDef.BodyType.DynamicBody);
		body = makeCircularFixture(body, radius, catBit);
		if (catBit == MarioBrosClone.MARIO_BIT)
			body.setLinearDamping(0.75f);
		body = makeContactSensor(body, MarioBrosClone.MARIO_HEAD_BIT);

		return body;
	}

	private Body makeContactSensor(Body body, float restitution, short catBit) {
		return makePolyFixture(body, restitution, catBit);
	}

	private Body makeCircularFixture(Body body, float radius, short catBit) {
		CircleShape shape = new CircleShape();
		shape.setRadius(radius / MarioBrosClone.PPM);
		body.createFixture(makeFixture(catBit, shape));
		shape.dispose();
		return body;
	}

	private Body makePolyFixture(Body body, float restitution, short catBit) {
		PolygonShape shape = new PolygonShape();

		Vector2[] vertices = new Vector2[4];
		vertices[0] = new Vector2(-7, 8).scl(1 / MarioBrosClone.PPM);
		vertices[1] = new Vector2(7, 8).scl(1 / MarioBrosClone.PPM);
		vertices[2] = new Vector2(-5, 3).scl(1 / MarioBrosClone.PPM);
		vertices[3] = new Vector2(5, 3).scl(1 / MarioBrosClone.PPM);
		shape.set(vertices);

		FixtureDef fdef = makeFixture(catBit, shape);
		fdef.restitution = restitution;
		body.createFixture(fdef);

		shape.dispose();
		return body;
	}

	public Body makeBody(Vector2 position, Vector2 boxDims, short catBit, BodyDef.BodyType type) {
		Body body = null;
		body = makeBody(position, type);
		return makePolyFixture(body, boxDims, catBit);
	}

	private Body makePolyFixture(Body body, Vector2 boxDims, short catBit) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(boxDims.x, boxDims.y);
		body.createFixture(makeFixture(catBit, shape));
		shape.dispose();
		return body;
	}

}
