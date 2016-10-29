/*
 *  Auzrk 2016
 */
package com.auzrk.moddeus;

import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author Aurzrk
 */
public class Button {
    
    public float x, y, centerX, centerY, width, height; //Everywhere else spells it 'center' so its easier to just use the american spelling
    public String text;
    public Button(float centerX, float centerY, float width, float height){
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.height = height;
        x = centerX - width/2;
        y = centerY - height/2;
    }
    
    public boolean isInside(float px, float py){
        boolean result = false;
        if(px > x && px < x + width){
            if(py > y && py < y + height){
                return true;
            }
        }
        return result;
    }
    
    public void drawText(Moddeus game, String text){
        game.layout.setText(game.font, text);
        game.font.draw(game.batch, text, centerX - game.layout.width/2, centerY - game.layout.height/2);
    }
    
    public void draw(Moddeus game, Texture texture){
        game.batch.draw(texture, x, y, width, height);
    }
    
    public static void drawCenteredText(Moddeus game, String text, float x, float y){ //Helper function so I dont have to centre text all the time, i didnt know where else to put it
        game.layout.setText(game.font, text);
        game.font.draw(game.batch, text, x - game.layout.width/2, y - game.layout.height/2);
    }
}
