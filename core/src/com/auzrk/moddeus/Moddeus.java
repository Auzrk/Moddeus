package com.auzrk.moddeus;

import com.auzrk.moddeus.screens.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Moddeus extends Game {
    
    public SpriteBatch batch;
    public BitmapFont font;
    public AssetManager assets;
    public GlyphLayout layout;
    
    @Override
    public void create() {
        layout = new GlyphLayout();
        batch = new SpriteBatch();
        font = new BitmapFont();
        assets = new AssetManager();
        setScreen(new MenuScreen(this)); //Initialise the game to the menuscreen and pass a ref to itself.
    }
    
    @Override
    public void dispose(){
        assets.dispose();
        batch.dispose();
        font.dispose();
        super.dispose();
    }
}
