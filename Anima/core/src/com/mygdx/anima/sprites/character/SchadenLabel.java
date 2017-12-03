package com.mygdx.anima.sprites.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.anima.AnimaRPG;

/**
 * Created by Steffen on 13.12.2016.
 */

public class SchadenLabel extends BitmapFont implements Pool.Poolable {
    // private static Skin skin = new Skin(Gdx.files.internal("ui-skin/uiskin.json"));
    Vector2 position;

    private float timer;
    private String schaden;
        public SchadenLabel(){
            super(Gdx.files.internal("ui-skin/damageLabel.fnt"));
        }
    public void init(int schaden, SpriteVorlage getroffener,Vector2 treffender){
        this.schaden = String.valueOf(schaden);
        if(getroffener instanceof Held)
        {   this.setColor(Color.RED);
        }else if(schaden<0){
            this.setColor(Color.FOREST);
        }else{
            this.setColor(Color.WHITE);
        }
        position=new Vector2((getroffener.b2body.getPosition().x+treffender.x)/2,(getroffener.b2body.getPosition().y+treffender.y)/2);
        timer=0;
        this.setUseIntegerPositions(false);
        // hier wird größe der Schrift definiert
        this.getData().setScale(1.5f/AnimaRPG.W_WIDTH/5f,0.75f/AnimaRPG.W_Height/5f);
        this.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }
    public Vector2 positionBestimmen(Vector2 posLeider,Vector2 posVerurs){
        // Dmg von links
        return new Vector2(posLeider.x+(posLeider.x+posVerurs.x)/(posLeider.x+posVerurs.x),posLeider.y+(posLeider.y+posVerurs.y)/(posLeider.x+posVerurs.x));
    }
    public void reset(){
        position=new Vector2(0,0);
        timer=0;
        String schaden="";

    }
    public float getTimer() {
        return timer;
    }
    public void addTime(float time) {
        this.timer += time;
    }
    public void draw(Batch batch) {
        position.y+=0.01f;
        super.draw(batch, schaden,position.x,position.y);
    }
}