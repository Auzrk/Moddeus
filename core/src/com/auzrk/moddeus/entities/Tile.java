/*
 *  Auzrk 2016
 */
package com.auzrk.moddeus.entities;

import com.auzrk.moddeus.screens.GameScreen;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 *
 * @author Aurzrk
 */

//I realise that implementing Tile as an entity is kinda hacky but its much easier this way
public class Tile extends Entity implements PhysEntity{
    
    float x, y;
    Body body;
    
    public Tile(float x, float y, Cell cell, GameScreen gameState){
        super(gameState);
        this.x = x;
        this.y = y;
        createBody();
        //to be created, just made this so I didn't get errors while writing GameScreen
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(x + 0.5f, y + 0.5f); //Box2D's origins are centered while mine are bottom left so i need to offset for this
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox((float)1/2, (float)1/2, new Vector2(1/2, 1/2),0); //for some reason this function takes halfwidth and halfheight? the vector2 bit sets the origin of the box to the bottom left
        body = gameState.world.createBody(bodyDef);
        body.createFixture(boxShape, 0f);
        boxShape.dispose();
        body.setUserData(this);
    }

    @Override
    public void onCollide(PhysEntity collider) {
    }
}
