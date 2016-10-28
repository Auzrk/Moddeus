/*
 *  Auzrk 2016
 */
package com.auzrk.moddeus.entities;

import com.auzrk.moddeus.screens.GameScreen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Aaznec
 */
public abstract class DrawableEntity extends Entity{
    
    /* I had to seperate Entity and DrawableEntity due to the fact that
        i want tiles in the map to be entities so that I can update them
        and what-not but as the renderer in the gamescreen already draws
        the tiles I didn't want to draw them again. I know i could just
        render the tiles by myself instead of the using the renderer but
        im lazy and the writer of the class probably does it better than 
        me
    */
    
    public Vector2 pos;
    public float width;
    public float height;
    public float dir;
    public float rot;
    
    public DrawableEntity(Vector2 pos, float width, float height, float dir, GameScreen gameState) {
        super(gameState);
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.dir = dir;
        rot = 0;
    }
    
    public abstract TextureRegion getFrame();
}
