/*
 *  Auzrk 2016
 */
package com.auzrk.moddeus.screens;

import com.auzrk.moddeus.Button;
import com.auzrk.moddeus.Moddeus;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;

/**
 *
 * @author Aurzrk
 */
public class LevelScreen extends AbstractScreen{

    ArrayList<Button> buttonList;
    Button backButton;
    Texture buttonImg;
    
    public LevelScreen(Moddeus game) {
        super(game);
        buttonList = new ArrayList<Button>();
        game.assets.load("img/button.png", Texture.class);
        game.assets.finishLoading();
        buttonImg = game.assets.get("img/button.png");
        FileHandle[] files = Gdx.files.local("level/").list();
        float startX = Gdx.graphics.getWidth()/2 - 125, endX = Gdx.graphics.getWidth()/2 + 125;
        float currX = startX, currY = Gdx.graphics.getHeight()-100;
        for(FileHandle file: files) {
           if(!file.name().contains("png")){
               Button b = new Button(currX, currY, 50, 50);
               b.text = file.nameWithoutExtension();
               buttonList.add(b);
               currX += 50;
               if(currX > endX){
                   currX = startX;
                   currY -= 50;
               }
           }
        }
        backButton = new Button(Gdx.graphics.getWidth()/2, currY - 200, 120, 60);
    }

    @Override
    public void show() {
        //At the moment I don't want to implement this so it just redirects immediately to a game screen on testlevel
        //game.setScreen(new GameScreen(game, "testlevel"));
    }

    @Override
    public void render(float delta) {
        
        //CLEAR SCREEN
        Gdx.gl.glClearColor(0.27f, 0, 0.3f, 1); 
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        game.batch.begin();
        for(Button b : buttonList){
            b.draw(game, buttonImg);
            b.drawText(game, b.text);
        }
        backButton.draw(game, buttonImg);
        backButton.drawText(game, "Back");
        game.batch.end();
        
        if(Gdx.input.isTouched()){
            int x = Gdx.input.getX(), y = Gdx.graphics.getHeight() - Gdx.input.getY();
            for(Button b : buttonList){
                if(b.isInside(x, y)){
                    game.setScreen(new GameScreen(game, b.text));
                }
            }
            if(backButton.isInside(x, y)){
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

    @Override
    public void dispose() {
        super.dispose();
    }
    
}
