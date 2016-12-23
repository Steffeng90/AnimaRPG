package com.mygdx.anima.sprites.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;

/**
 * Created by Steffen on 13.12.2016.
 */

public class SchadenLabel extends BitmapFont {
    // private static Skin skin = new Skin(Gdx.files.internal("ui-skin/uiskin.json"));
    Vector2 position;

    private static Array<SchadenLabel> schadensLabel=new Array<SchadenLabel>();
    private float timer;
    private String schaden;
        public SchadenLabel(int schaden, HumanoideSprites getroffener,Vector2 treffender){
            //super("Blabla123",skin);
            super(Gdx.files.internal("ui-skin/damageLabel.fnt"));
            //super();
            this.schaden = String.valueOf(schaden);
            if(getroffener instanceof Held)
            {
                this.setColor(Color.RED);
            }else{
                this.setColor(Color.WHITE);
            }
            position=new Vector2((getroffener.b2body.getPosition().x+treffender.x)/2,(getroffener.b2body.getPosition().y+treffender.y)/2);
            timer=0;
            this.setUseIntegerPositions(false);
            this.getData().setScale(getScaleX()/AnimaRPG.W_WIDTH/5,getScaleY()/AnimaRPG.W_Height/5);
            this.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            addSchadenLabel(this);
        }
    public Vector2 positionBestimmen(Vector2 posLeider,Vector2 posVerurs){
        // Dmg von links
        return new Vector2(posLeider.x+(posLeider.x+posVerurs.x)/(posLeider.x+posVerurs.x),posLeider.y+(posLeider.y+posVerurs.y)/(posLeider.x+posVerurs.x));
    }
    public static Array<SchadenLabel> getSchadensLabelArray() {
        return schadensLabel;
    }
    public static void addSchadenLabel(SchadenLabel schadenLabel) {
        schadensLabel.add(schadenLabel);
    }
    public void removeSchadenLabel(int i){
        schadensLabel.removeIndex(i);
    }
    public static SchadenLabel getSchadenLabel(int i){
        return schadensLabel.get(i);
    }
    public float getTimer() {
        return timer;
    }
    public void addTime(float time) {
        this.timer += time;
    }
    public void draw(Batch batch) {
        position.y+=0.005f;
        super.draw(batch, schaden,position.x,position.y);
    }
}