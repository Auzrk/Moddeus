/*
 *  Auzrk 2016
 */
package com.auzrk.moddeus.entities;

import com.badlogic.gdx.physics.box2d.Body;

/**
 *
 * @author Aaznec
 */
public interface PhysEntity {
    
    void createBody();
    
    void onCollider(PhysEntity collider);
    
}
