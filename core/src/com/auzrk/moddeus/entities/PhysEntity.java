/*
 *  Auzrk 2016
 */
package com.auzrk.moddeus.entities;

import com.badlogic.gdx.physics.box2d.Fixture;

/**
 *
 * @author Aurzrk
 */
public interface PhysEntity {
    
    void createBody();
    
    void onCollide(Fixture self, Fixture other);
    
}
