package com.mygdx.anima.sprites.character.interaktiveObjekte;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.HumanoideSprites;

/**
 * Created by Steffen on 03.01.2017.
 */

public class Zauber extends Sprite {
    protected Playscreen screen;
    protected World world;
    public Body b2body;
    BodyDef bdef;
    Animation zauber;
    Texture zauberQuelle;
    TextureRegion initialTexture;
    Array<TextureRegion> frames;
    int laenge, breite;
    public float stateTimer,rueckstoss;
    int radius;
    //Refernz auf Held:
    public HumanoideSprites zaubernder;
    public boolean setToDestroy,destroyed,fixtureErzeugen,fixtureistErzeugt;
    FixtureDef fdefAttack;
    public static Array<Zauber> allZauber=new Array<Zauber>();
    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
    public static Array<Zauber> getAllZauber(){
        return allZauber;
    }
    public void remove() {
        allZauber.removeValue(this, true);
    }
    public void update(float dt)
    {  if(!destroyed)
    {   if(fixtureErzeugen){
        zaubernder.castFixture = b2body.createFixture(fdefAttack);
        zaubernder.castFixture.setUserData(this);
        fixtureErzeugen=false;
        fixtureistErzeugt=true;
    }
        if(setToDestroy){
            destroyZauber();}
        setRegion(getFrame(dt));
    }}
    public void destroyZauber (){

        world.destroyBody(this.b2body);
        b2body.setUserData(null);
        b2body=null;
        setToDestroy=false;
        destroyed=true;
    }
    public TextureRegion getFrame(float dt)
    {
        TextureRegion region;
        stateTimer = stateTimer + dt;
        region= zauber.getKeyFrame(stateTimer,false);
        if(!fixtureistErzeugt && stateTimer>=zauber.getAnimationDuration()*0.8)
            fixtureErzeugen=true;


        if(zauber.isAnimationFinished(stateTimer))
            setToDestroy=true;
        return region;
    }

}
