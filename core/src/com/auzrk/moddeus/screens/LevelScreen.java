/*
 *  Auzrk 2016
 */
package com.auzrk.moddeus.screens;

import com.auzrk.moddeus.Moddeus;

/**
 *
 * @author Aaznec
 */
public class LevelScreen extends AbstractScreen{

    public LevelScreen(Moddeus game) {
        super(game);
    }

    @Override
    public void show() {
        //At the moment I don't want to implement this so it just redirects immediately to a game screen on testlevel
        game.setScreen(new GameScreen(game, "testlevel"));
    }

    @Override
    public void render(float delta) {
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
    }
    
}
