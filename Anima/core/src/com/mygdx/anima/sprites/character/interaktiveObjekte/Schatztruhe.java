package com.mygdx.anima.sprites.character.interaktiveObjekte;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.items.ItemGenerator;

import static com.mygdx.anima.AnimaRPG.getHeld;
import static com.mygdx.anima.tools.KartenManager.aktuelleKartenId;

/**
 * Created by Steffen on 13.11.2016.
 */

public class Schatztruhe extends InteraktivesObjekt implements Pool.Poolable{

    Body body;
    Animation openTruhe;
    public enum State {CLOSED, OPENING, OPEN};
    public State currentState,previousState;
    public boolean runOpenAnimation, closed;
    TextureRegion spriteClose,spriteOpen;
    public float stateTimer;
    public String inhalt;
    public int truhenID,nachbedfalse,nachbedtrue;

    public Schatztruhe(){
       super();
    }
    public void init(Playscreen screen, float x, float y,int truhenTyp,int truhenID,String inhalt,boolean isClosed,int nachbedfalse,int nachbedtrue){
        super.init(screen,x,y);
        this.inhalt=inhalt;
        defineItem();
        this.truhenID=truhenID;
        this.nachbedtrue=nachbedtrue;
        this.nachbedfalse=nachbedfalse;
        closed=isClosed;
        if(truhenTyp==1){
            spriteOpen=screen.getGame().getAssetManager().getSchatztruheOpenBraun();
            spriteClose=screen.getGame().getAssetManager().getSchatztruheCloseBraun();
            openTruhe =screen.getGame().getAssetManager().getTruheBraunAnimation();
        }
        else{
            spriteOpen=screen.getGame().getAssetManager().getSchatztruheOpenGold();
            spriteClose=screen.getGame().getAssetManager().getSchatztruheCloseGold();
            openTruhe =screen.getGame().getAssetManager().getTruheGoldAnimation();
        }

    }
    @Override
    public void defineItem() {
        BodyDef bdef= new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        body =world.createBody(bdef);
        body.setActive(true);

        FixtureDef fdef= new FixtureDef();
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(8/ AnimaRPG.PPM,8/AnimaRPG.PPM,new Vector2(getWidth()/2, getHeight()/2),0);
        fdef.filter.categoryBits=AnimaRPG.OBJECT_BIT;
        fdef.filter.maskBits=AnimaRPG.HERO_BIT | AnimaRPG.ENEMY_BIT | AnimaRPG.HERO_SENSOR | AnimaRPG.ENEMY_ARROW | AnimaRPG.ENEMY_ARROW;
        fdef.shape=shape;

        body.createFixture(fdef).setUserData(this);
        }
    public void reset(){
        body=null;
        openTruhe=null;
        currentState=null;
        previousState=null;
        runOpenAnimation=false;
        closed=false;
        spriteClose=null;
        spriteOpen=null;
        stateTimer=0;
        inhalt="";
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
                if(openTruhe.isAnimationFinished(stateTimer)){
                    runOpenAnimation=false;closed=false;
                    if(nachbedtrue!=0){
                        getHeld().getEventList()[nachbedtrue]=true;
                    }
                    if(nachbedfalse!=0){
                        getHeld().getEventList()[nachbedfalse]=false;
                    }
                    screen.setCurrentItemsprite(ItemGenerator.generateItem(screen,getX(),getY()+getHeight()/2,inhalt));
                    getHeld().getGeoeffneteTruhen().add(new SchatztruhenSpeicherObjekt(aktuelleKartenId,truhenID));
                }
                break;
            case OPEN:
            default:
                region=spriteOpen;
                break;
        }
        return region;
}
}
