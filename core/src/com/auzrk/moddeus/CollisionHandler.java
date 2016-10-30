/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auzrk.moddeus;

import com.auzrk.moddeus.entities.PhysEntity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 *
 * @author jkost
 */
public class CollisionHandler implements ContactListener{

    @Override
    public void beginContact(Contact c) {
        Object a, b;
        a = c.getFixtureA().getBody().getUserData();
        b = c.getFixtureB().getBody().getUserData();
        if(a instanceof PhysEntity){
            ((PhysEntity) a).onCollide(c.getFixtureA(),c.getFixtureB());
        }
        if(b instanceof PhysEntity){
            ((PhysEntity) b).onCollide(c.getFixtureB(), c.getFixtureA());
        }
    }

    @Override
    public void endContact(Contact cntct) {
    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {
    }

    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {
    }
    
}
