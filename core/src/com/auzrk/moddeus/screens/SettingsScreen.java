/*
 *  Auzrk 2016
 */
package com.auzrk.moddeus.screens;

import com.auzrk.moddeus.Button;
import com.auzrk.moddeus.Moddeus;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author Aurzrk
 */
public class SettingsScreen extends AbstractScreen{

    Texture buttonImg;
    
    float width;
    float height;
    
    Button setKey;
    Button incSound;
    Button decSound;
    Button incMusic;
    Button decMusic;
    Button back;
    
    
    SettingsScreen(Moddeus game) {
        super(game);
        
        game.assets.load("img/button.png", Texture.class);
        game.assets.finishLoading();
        
        buttonImg = game.assets.get("img/button.png");
        
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        
        float buttonWidth = width/6;
        float buttonHeight = height/12;
        
        setKey = new Button(width/2, height*11f/12f - game.layout.height, buttonWidth, buttonHeight);
        incSound = new Button(width/2 - buttonWidth/4, setKey.centerY - buttonHeight - game.layout.height - height/20, buttonWidth/2, buttonHeight);
        decSound = new Button(width/2 + buttonWidth/4, setKey.centerY - buttonHeight - game.layout.height - height/20, buttonWidth/2, buttonHeight);
        incMusic = new Button(width/2 - buttonWidth/4, incSound.centerY - buttonHeight - game.layout.height - height/20, buttonWidth/2, buttonHeight);
        decMusic = new Button(width/2 + buttonWidth/4, incSound.centerY - buttonHeight - game.layout.height - height/20, buttonWidth/2, buttonHeight);
        back = new Button(width/2, incMusic.centerY - buttonHeight - height/20, buttonWidth, buttonHeight);
    }

    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) {
        
        Gdx.gl.glClearColor(0.27f, 0, 0.3f, 1); 
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        game.batch.begin();
        
        setKey.draw(game, buttonImg);
        setKey.drawText(game, "Rebind keys");
        incSound.draw(game, buttonImg);
        incSound.drawText(game, "+");
        decSound.draw(game, buttonImg);
        decSound.drawText(game, "-");
        incMusic.draw(game, buttonImg);
        incMusic.drawText(game, "+");
        decMusic.draw(game, buttonImg);
        decMusic.drawText(game, "-");
        back.draw(game, buttonImg);
        back.drawText(game, "Back");
        
        game.batch.end();
        
        if(Gdx.input.justTouched()){
            float px = Gdx.input.getX();
            float py = height - Gdx.input.getY(); //Touch coords are y-down and i use y-up

            if(setKey.isInside(px, py)){
                //
            }
            if(incSound.isInside(px, py)){
                //
            }
            if(decSound.isInside(px, py)){
                
            }
            if(incMusic.isInside(px, py)){
                
            }
            if(decMusic.isInside(px, py)){
                
            }
            if(back.isInside(px, py)){
                game.setScreen(new MenuScreen(game));
            }
        }
        
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}
