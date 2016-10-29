/*
 *  Auzrk 2016
 */
package com.auzrk.moddeus.entities;

import com.auzrk.moddeus.screens.GameScreen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 *
 * @author Aurzrk
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
        //This is kinda long as just creating a simple rectangle doesn't work (http://www.iforce2d.net/b2dtut/ghost-vertices)
        //this only works for 1x2 characters, ill made specific code for other ents or just make a better function
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(pos.x, pos.y);
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox((float)width/2, 0.75f * 0.5f * height, new Vector2(width/2, height/2+0.125f*height),0); //for some reason this function takes halfwidth and halfheight? the vector2 bit sets the origin of the box to the bottom left
        body = gameState.world.createBody(bodyDef);
        body.createFixture(boxShape, 0f);
        CircleShape circShape = new CircleShape();
        circShape.setRadius(width/2);
        circShape.setPosition(new Vector2(width/2, height/4));
        body.createFixture(circShape, 0f);
        body.getFixtureList().get(1).setFriction(30f);
        boxShape.dispose();
        circShape.dispose();
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
