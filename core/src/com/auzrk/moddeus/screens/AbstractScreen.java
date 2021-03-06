/*
 *  Auzrk 2016
 */
package com.auzrk.moddeus.screens;

import com.auzrk.moddeus.Moddeus;
import com.badlogic.gdx.Screen;

/**
 *
 * @author Aurzrk
 */
public abstract class AbstractScreen implements Screen{
    
    public Moddeus game;   //Whatever screen I am in, I need a reference to the game object.
    public AbstractScreen(Moddeus game){ 
        this.game = game;
    }
    
    @Override
    public void dispose(){
        game.assets.clear();
    }
}
