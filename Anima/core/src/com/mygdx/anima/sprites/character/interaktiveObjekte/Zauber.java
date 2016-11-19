package com.mygdx.anima.sprites.character.interaktiveObjekte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;

/**
 * Created by Steffen on 19.11.2016.
 */

public class Zauber extends Sprite {
    private static Array<Zauber> allZauber=new Array<Zauber>();
    protected Playscreen screen;
    protected World world;
    public Body b2body;
    BodyDef bdef;
    Animation zauber;
    Texture zauberQuelle;
    TextureRegion initialTexture;
    Array<TextureRegion> frames;
    int laenge, breite;
    float stateTimer;
    //Refernz auf Held:
    public Held zaubernder;
    public boolean setToDestroy,destroyed;
public Zauber(Held held) {
    this.zaubernder=held;
    this.world=held.world;
    this.screen=held.screen;
    BodyDef bdef=new BodyDef();
    bdef.position.set(held.getX(),held.getY());
    bdef.type = BodyDef.BodyType.DynamicBody;
    b2body =world.createBody(bdef);
    b2body.setActive(true);
    laenge = 20;
    breite = 20;
    stateTimer=0;
    zauberQuelle = new Texture("objekte/energieNova.png");
    initialTexture=new TextureRegion(zauberQuelle,13, 463, laenge, breite);
    setRegion(initialTexture);

    setBounds(b2body.getPosition().x-3/AnimaRPG.PPM,b2body.getPosition().y-13/AnimaRPG.PPM,50 / AnimaRPG.PPM,50/ AnimaRPG.PPM);
    frames = new Array<TextureRegion>();
    //for (int i = 0; i < 10; i++) {

    frames.add(new TextureRegion(zauberQuelle, 4, 9, breite, laenge));
    frames.add(new TextureRegion(zauberQuelle,24,10, breite, laenge));
    frames.add(new TextureRegion(zauberQuelle,47 , 7, 26, 24));
    frames.add(new TextureRegion(zauberQuelle, 77,13, 13, 13));
    frames.add(new TextureRegion(zauberQuelle, 95, 7, 25, 24));
    frames.add(new TextureRegion(zauberQuelle, 125, 7, 25, 24));
    frames.add(new TextureRegion(zauberQuelle, 154, 7, 25, 24));
    frames.add(new TextureRegion(zauberQuelle, 188, 3, 31, 30));
    frames.add(new TextureRegion(zauberQuelle, 229, 3, 33, 31));
    frames.add(new TextureRegion(zauberQuelle, 272, 2, 36, 33));
    //}
    zauber = new Animation(0.05f, frames);
    frames.clear();
    Gdx.app.log("1","");


    CircleShape circleShape = new CircleShape();
    circleShape.setRadius(30 / AnimaRPG.PPM);
    circleShape.setPosition(new Vector2(23/AnimaRPG.PPM,18/AnimaRPG.PPM));
    FixtureDef fdefAttack = new FixtureDef();
    fdefAttack.filter.categoryBits = AnimaRPG.HERO_CAST_BIT;
    fdefAttack.filter.maskBits = AnimaRPG.ENEMY_BIT;
    fdefAttack.shape = circleShape;
    fdefAttack.isSensor = true;
    Gdx.app.log("2","");

    held.castFixture = b2body.createFixture(fdefAttack);
    held.castFixture.setUserData(this);
   held.runCasting = true;
    held.castExists = true;
    setToDestroy=false;
    destroyed=false;
    Gdx.app.log("3","");
    allZauber.add(this);
}

    @Override
    public void draw(Batch batch) {
        Gdx.app.log("Beginn draw","");
        super.draw(batch);
    }
    public TextureRegion getFrame(float dt)
    {
        TextureRegion region;
        // System.out.println(getState().toString()+ stateTimer);
        stateTimer = stateTimer + dt;
        region= zauber.getKeyFrame(stateTimer,false);
        if(zauber.isAnimationFinished(stateTimer))
            setToDestroy=true;
        //if(zauber.isAnimationFinished())
        return region;
    }
    public void update(float dt)
    {  if(!destroyed)
    {   if(setToDestroy){
        destroyZauber();}
        setRegion(getFrame(dt));
    }}
    public static Array<Zauber> getAllZauber(){
        return allZauber;
    }
    public void destroyZauber (){
        Gdx.app.log("Zsrst","");

        world.destroyBody(this.b2body);
        b2body.setUserData(null);
        b2body=null;
         Gdx.app.log("Kapuut","");
        setToDestroy=false;
        destroyed=true;
    }
    public void remove() {
        allZauber.removeValue(this, true);
    }
}
