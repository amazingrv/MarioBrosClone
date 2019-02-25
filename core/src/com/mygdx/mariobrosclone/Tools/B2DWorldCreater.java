package com.mygdx.mariobrosclone.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.mariobrosclone.MarioBrosClone;

public class B2DWorldCreater {
	public B2DWorldCreater(World world, TiledMap map)
	{
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		//ground bodies/fixtures
		for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class))
		{
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBrosClone.PPM, (rect.getY() + rect.getHeight() / 2) / MarioBrosClone.PPM);
			
			body = world.createBody(bdef);
			shape.setAsBox((rect.getWidth() / 2) / MarioBrosClone.PPM, (rect.getHeight() / 2) / MarioBrosClone.PPM);
			fdef.shape = shape;
			body.createFixture(fdef);
		}
		
		//pipe bodies/fixtures
		for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class))
		{
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBrosClone.PPM, (rect.getY() + rect.getHeight() / 2) / MarioBrosClone.PPM);
			
			body = world.createBody(bdef);
			shape.setAsBox((rect.getWidth() / 2) / MarioBrosClone.PPM, (rect.getHeight() / 2) / MarioBrosClone.PPM);
			fdef.shape = shape;
			body.createFixture(fdef);
		}
		
		
		//bricks bodies/fixtures
		for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class))
		{
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBrosClone.PPM, (rect.getY() + rect.getHeight() / 2) / MarioBrosClone.PPM);
			
			body = world.createBody(bdef);
			shape.setAsBox((rect.getWidth() / 2) / MarioBrosClone.PPM, (rect.getHeight() / 2) / MarioBrosClone.PPM);
			fdef.shape = shape;
			body.createFixture(fdef);
		}

		//coin bodies/fixtures
		for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class))
		{
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBrosClone.PPM, (rect.getY() + rect.getHeight() / 2) / MarioBrosClone.PPM);
			
			body = world.createBody(bdef);
			shape.setAsBox((rect.getWidth() / 2) / MarioBrosClone.PPM, (rect.getHeight() / 2) / MarioBrosClone.PPM);
			fdef.shape = shape;
			body.createFixture(fdef);
		}
	}
}
