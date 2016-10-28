/*
 *  Auzrk 2016
 */
package com.auzrk.moddeus.entities;

import com.auzrk.moddeus.screens.GameScreen;

/**
 *
 * @author Aaznec
 */

public abstract class Entity {
    
    float stateTime;
    GameScreen gameState;
    
    public Entity(GameScreen gameState){
        this.gameState = gameState;
    }
    
    public void update(float delta){
        stateTime += delta;
    }
    
}
