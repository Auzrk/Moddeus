/*
 *  Auzrk 2016
 */
package com.auzrk.moddeus.entities;

import com.auzrk.moddeus.screens.GameScreen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 *
 * @author Aaznec
 */
public abstract class Mob extends DrawableEntity implements PhysEntity{
    
    public Body body;
    public float acc;
    public float maxSpeed;
    int health;
    
    public Mob(Vector2 pos, float width, float height, float dir, GameScreen gameState) {
        super(pos, width, height, dir, gameState);
        health = 100;
        createBody();
    }
    
    public void createBody(){
        
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(pos.x, pos.y);
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox((float)width/2, (float)height/2, new Vector2(width/2, height/2),0); //for some reason this function takes halfwidth and halfheight? the vector2 bit sets the origin of the box to the bottom left
        body = gameState.world.createBody(bodyDef);
        body.createFixture(boxShape, 0f);
        boxShape.dispose();
        body.setUserData(this);
    }
    
    public void update(float delta){
        super.update(delta);
        pos = body.getPosition();
        
    }
    
    public void clampVel(){
        Vector2 vel = body.getLinearVelocity();
        if(vel.x > maxSpeed){
            body.setLinearVelocity(maxSpeed, vel.y);
        } else if(vel.x < -maxSpeed){
            body.setLinearVelocity(-maxSpeed, vel.y);
        }
    }
    
}
