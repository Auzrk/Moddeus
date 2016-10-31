/*
 *  Auzrk 2016
 */
package com.auzrk.moddeus.entities;

import com.auzrk.moddeus.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 *
 * @author Aurzrk
 */
public class Player extends Mob{
    
    final float PLAYERWIDTH = 1;
    final float PLAYERHEIGHT = 2;
    
    enum State{
        standing, walking, jumping, attacking
    }
    State state;
    
    enum AttackState{
        laserbolt //more to be added
    }
    
    Animation stand;
    Animation walk;
    
    Texture texture;
    TextureRegion[][] frameSet;
    
    Boolean canJump;
    
    public Player(Vector2 pos, GameScreen gameState) {
        super(pos, 1, 2, 1, gameState);
        
        texture = new Texture("img/player-set.png");
        frameSet = TextureRegion.split(texture, 16, 32);
        
        state = state.standing;
        
        stand = new Animation(0.4f, frameSet[0][0], frameSet[0][0], frameSet[0][0], frameSet[0][0], frameSet[0][0], frameSet[0][0],  frameSet[0][0], frameSet[0][0], frameSet[0][0], frameSet[0][0], frameSet[0][0], frameSet[0][0], frameSet[0][0], frameSet[0][4]);
        walk = new Animation(0.225f, frameSet[0][0], frameSet[0][1] ,frameSet[0][2]);
        stand.setPlayMode(Animation.PlayMode.LOOP);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        
        acc = 500;
        maxSpeed = 25;
        
        canJump = true;
    }

    @Override
    public TextureRegion getFrame() {
        TextureRegion frame;
        switch(state){
            case standing:
                frame = stand.getKeyFrame(stateTime);
                break;
            case walking:
                frame = walk.getKeyFrame(stateTime);
                break;
            //case attacking
            case jumping:
                frame = frameSet[0][5];
                break;
            default:
                frame = frameSet[0][0];
        }
        if(dir == 1){
            if(frame.isFlipX()){
                frame.flip(true, false);
            }
            return frame;
        }else{
            if(!frame.isFlipX()){
                frame.flip(true, false);
            }
            return frame;
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        
        Vector2 vel = body.getLinearVelocity();
        vel.x = 0;
        state = state.standing;
        if(Gdx.input.isKeyPressed(Keys.D)){
            vel.x = maxSpeed;
            dir = 1;
            state = state.walking;
        }
        if(Gdx.input.isKeyPressed(Keys.A)){
            vel.x = -maxSpeed;
            dir = -1;
            state=state.walking;
        }
        if((Gdx.input.isKeyJustPressed(Keys.W)||Gdx.input.isKeyJustPressed(Keys.SPACE)) && canJump){
            vel.y = maxSpeed *2.5f ;
            canJump = false;
        }
        
        if(!canJump){
            state = state.jumping;
        }
        
        body.setLinearVelocity(vel);
        
        if(pos.y < 0){
            die();
        }
        //clampVel();
    }

    @Override
    public void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(pos.x, pos.y);
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox((float)width/2 -0.1f, 0.75f * 0.5f * height -0.1f, new Vector2(width/2, height/2+0.125f*height),0); //for some reason this function takes halfwidth and halfheight? the vector2 bit sets the origin of the box to the bottom left
        body = gameState.world.createBody(bodyDef);
        body.createFixture(boxShape, 0f);
        CircleShape circShape = new CircleShape();
        circShape.setRadius(width/2 - 0.075f);
        circShape.setPosition(new Vector2(width/2, height/4));
        body.createFixture(circShape, 0f);
        body.getFixtureList().get(0).setFriction(0f);
        body.getFixtureList().get(1).setFriction(0f);
        boxShape.setAsBox(0.125f, 0.025f, new Vector2(width/2 -0.03125f, 0.05f), 0);
        body.createFixture(boxShape, 0f);
        body.getFixtureList().get(2).setSensor(true);
        body.getFixtureList().get(2).setUserData("feet");
        boxShape.dispose();
        circShape.dispose();
        body.setUserData(this);
    }

    @Override
    public void onCollide(Fixture self, Fixture other) {
        if(self.isSensor()){
            if( ((String) self.getUserData()).equalsIgnoreCase("feet")){
                canJump = true; 
            }
            
            if(!other.isSensor()){ //It feet come into contact with something hard at high speed, splat
                if(Math.abs(body.getLinearVelocity().y) > 50){
                    die();
                }
            }
        }
    }
    
    public void die(){
        gameState.game.setScreen(new GameScreen(gameState.game, gameState.levelname));
    }
    
}
