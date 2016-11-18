package com.mygdx.anima.sprites.character.interaktiveObjekte;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;

/**
 * Created by Steffen on 13.11.2016.
 */

public class Schatztruhe extends InteraktivesObjekt {

    Texture spriteQuelle;
    Body body;
    Animation openTruhe;
    public enum State {CLOSED, OPENING, OPEN};
    public State currentState,previousState;
    public boolean runOpenAnimation, closed;
    TextureRegion spriteClose,spriteOpen;
    public float stateTimer;

    public Schatztruhe(Playscreen screen, float x, float y){
        super(screen,x,y);
        spriteQuelle=new Texture("objekte/schatztruhe.png");
        spriteOpen=new TextureRegion(spriteQuelle,0,224, 32,32);
                spriteClose=new TextureRegion(spriteQuelle,0,128, 32,32);
        defineItem();

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(spriteQuelle, 0, i*32+128, 32, 32));
        }
        openTruhe = new Animation(0.1f, frames);
        frames.clear();
    }

    @Override
    public void defineItem() {
        BodyDef bdef= new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        body =world.createBody(bdef);
        body.setActive(true);
        closed=true;

        FixtureDef fdef= new FixtureDef();
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(8/ AnimaRPG.PPM,8/AnimaRPG.PPM,new Vector2(getWidth()/2, getHeight()/2),0);
        fdef.filter.categoryBits=AnimaRPG.OBJECT_BIT;
        fdef.filter.maskBits=AnimaRPG.HERO_BIT | AnimaRPG.ENEMY_BIT | AnimaRPG.HERO_SENSOR | AnimaRPG.ARROW_BIT;
        fdef.shape=shape;

        body.createFixture(fdef).setUserData(this);
        }
    public void update(float dt){
        setRegion(getFrame(dt));
    }

    @Override
    public void use(Held hero){
        if(closed){
        runOpenAnimation=true;
           }
    }
    public State getState(){
        if(runOpenAnimation){
            return State.OPENING;}
        else if(closed){
            return State.CLOSED;}
        else {
            return State.OPEN;}
    }
    public TextureRegion getFrame(float dt){
        currentState=getState();
        TextureRegion region;
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;

        switch(currentState){
            case CLOSED:
                    region=spriteClose;
                break;
            case OPENING:
                region = openTruhe.getKeyFrame(stateTimer, false);
                if(openTruhe.isAnimationFinished(stateTimer))
                    runOpenAnimation=false;closed=false;
                break;
            case OPEN:
            default:
                region=spriteOpen;
                break;
        }
        return region;
}
}