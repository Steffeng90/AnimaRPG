package com.mygdx.anima.sprites.character.zauber.fixtures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.HumanoideSprites;

/**
 * Created by Steffen on 03.01.2017.
 */

public class ZauberFixture extends Sprite {
    public static Array<ZauberFixture> allZauber=new Array<ZauberFixture>();

    protected Playscreen screen;
    protected World world;
    public Body b2body;
    BodyDef bdef;
    Animation zauber;
    Texture zauberQuelle;
    TextureRegion initialTexture;
    Array<TextureRegion> frames;
    int laenge, breite;
    public float stateTimer,rueckstoss, zauberFixtureTimer;
    int radius;
    //Refernz auf Held:
    public HumanoideSprites zaubernder;
    public boolean setToDestroy,destroyed,fixtureErzeugen,fixtureistErzeugt;
    FixtureDef fdefAttack;
    Vector2 zauberStartVector,zauberFlugVector;
    public ZauberFixture(float zauberFixtureTimer){
        this.zauberFixtureTimer=zauberFixtureTimer;
    }
    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
    public static Array<ZauberFixture> getAllZauber(){
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
        region= (TextureRegion) zauber.getKeyFrame(stateTimer,false);
        if(!fixtureistErzeugt && stateTimer>=zauber.getAnimationDuration()*zauberFixtureTimer)
            fixtureErzeugen=true;
        if(zauber.isAnimationFinished(stateTimer))
            setToDestroy=true;
        return region;
    }
    public void richtungBestimmen(Held held)
    {
        Vector2 koerper=b2body.getPosition();
        switch (held.getCurrentRichtung()) {
            //Hier sind bei Y immer schon mind. -5 Abzug, weil man es ein bisschen nach unten ziehen muss, um die Mitte der Bodentexture und nicht
            // die der Grafikmitte zu treffen
            case Rechts:
                zauberStartVector = new Vector2(koerper.x+20 / AnimaRPG.PPM,koerper.y -8 / AnimaRPG.PPM);
                zauberFlugVector = new Vector2(200 / AnimaRPG.PPM, 0);break;
            case Links:
                zauberStartVector = new Vector2(koerper.x-20 / AnimaRPG.PPM,koerper.y-8 / AnimaRPG.PPM);
                zauberFlugVector = new Vector2(-200 / AnimaRPG.PPM, 0);break;
            case Oben:
                zauberStartVector = new Vector2(koerper.x,koerper.y +17 / AnimaRPG.PPM);
                zauberFlugVector = new Vector2(0, 200 / AnimaRPG.PPM);break;
            case Unten:
                zauberStartVector = new Vector2(koerper.x,koerper.y -33 / AnimaRPG.PPM);
                zauberFlugVector= new Vector2(0, -200 / AnimaRPG.PPM);break;
            default:
                zauberStartVector = new Vector2(0, 0);
                zauberFlugVector = new Vector2(10, 10);break;
        }
    }
}
