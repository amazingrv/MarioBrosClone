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
import com.badlogic.gdx.utils.Array;
import com.mygdx.mariobrosclone.MarioBrosClone;
import com.mygdx.mariobrosclone.Screens.PlayScreen;
import com.mygdx.mariobrosclone.Sprites.Enemies.Enemy;
import com.mygdx.mariobrosclone.Sprites.Enemies.Goomba;
import com.mygdx.mariobrosclone.Sprites.Enemies.Turtle;
import com.mygdx.mariobrosclone.Sprites.Tiles.Brick;
import com.mygdx.mariobrosclone.Sprites.Tiles.Coin;

public class B2DWorldCreater {
	
	Array<Goomba> goombas;
	Array<Turtle> turtles;
	
	public B2DWorldCreater(PlayScreen  screen)
	{
		World world = screen.getWorld();
		TiledMap map = screen.getMap();
		
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
			fdef.filter.categoryBits = MarioBrosClone.OBJECT_BIT;
			body.createFixture(fdef);
		}
		
		
		//bricks bodies/fixtures
		for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class))
		{
			new Brick(screen, object);
		}

		//coin bodies/fixtures
		for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class))
		{
			new Coin(screen, object);
		}
		
		//create goombas
		goombas = new Array<Goomba>();
		for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class))
		{
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			goombas.add(new Goomba(screen, rect.getX()/MarioBrosClone.PPM, rect.getY()/MarioBrosClone.PPM));
		}
		
		//turtles
		turtles = new Array<Turtle>();
		for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class))
		{
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			turtles.add(new Turtle(screen, rect.getX()/MarioBrosClone.PPM, rect.getY()/MarioBrosClone.PPM));
		}
	}
	
	public Array<Goomba> getGoombas(){
		return goombas;
	}
	public Array<Enemy> getEnemies(){
		Array<Enemy>  enemy = new Array<Enemy>();
		enemy.addAll(goombas);
		enemy.addAll(turtles);
		return enemy;
	}
	
	/*public void clearTurtle(Turtle turtle)
	{
		turtles.removeValue(turtle, true);
	}*/
}
