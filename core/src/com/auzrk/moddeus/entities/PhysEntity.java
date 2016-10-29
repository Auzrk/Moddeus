/*
 *  Auzrk 2016
 */
package com.auzrk.moddeus.entities;

/**
 *
 * @author Aurzrk
 */
public interface PhysEntity {
    
    void createBody();
    
    void onCollide(PhysEntity collider);
    
}
