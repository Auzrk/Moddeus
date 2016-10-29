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

public class MenuScreen extends AbstractScreen{
      
    Texture buttonImg;
    Texture titleImg;
    
    float width;
    float height;
    
    Button playButton;
    Button settingsButton;
    Button exitButton;
    
    float titleWidth;
    float titleHeight;
    float titleX;
    float titleY;
    
    public MenuScreen(Moddeus game) {
        super(game);
        
        //LOAD ASSETS
        game.assets.load("img/title.png", Texture.class);
        game.assets.load("img/button.png", Texture.class);
        game.assets.finishLoading();
        
        titleImg = game.assets.get("img/title.png");
        buttonImg = game.assets.get("img/button.png");
        
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
    }

    @Override
    public void show() {
        
        titleWidth = width * 0.75f;
        titleHeight = titleWidth/titleImg.getWidth() * titleImg.getHeight();
        titleX = width/2 - titleWidth/2;
        titleY = height/2 - titleHeight/2 + 0.25f * height;
        
        float buttonWidth = width/6;
        float buttonHeight = height/12;
        
        playButton = new Button( width/2 , titleY - buttonHeight - height/30, buttonWidth, buttonHeight); //Make each button a little bit bellow the above element
        settingsButton = new Button(width/2, playButton.y - buttonHeight - height/30, buttonWidth, buttonHeight);
        exitButton = new Button(width/2, settingsButton.y - buttonHeight - height/30, buttonWidth, buttonHeight);

        
    }

    @Override
    public void render(float delta) {
        //CLEAR SCREEN
        Gdx.gl.glClearColor(0.27f, 0, 0.3f, 1); 
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //BEGIN DRAWING
        game.batch.begin();
        
        game.batch.draw(titleImg, titleX, titleY, titleWidth, titleHeight);
        
        playButton.draw(game, buttonImg);
        settingsButton.draw(game, buttonImg);
        exitButton.draw(game, buttonImg);
        
        playButton.drawText(game, "Play");
        settingsButton.drawText(game, "Settings");
        exitButton.drawText(game, "Exit");
        
        game.batch.end();
        
        if(Gdx.input.justTouched()){
            float px = Gdx.input.getX();
            float py = height - Gdx.input.getY(); //Touch coords are y-down and i use y-up

            if(playButton.isInside(px, py)){
                game.setScreen(new LevelScreen(game));
            }
            if(settingsButton.isInside(px, py)){
                game.setScreen(new SettingsScreen(game));
            }
            if(exitButton.isInside(px, py)){
                Gdx.app.exit();
            }
        }
        
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        show();
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
