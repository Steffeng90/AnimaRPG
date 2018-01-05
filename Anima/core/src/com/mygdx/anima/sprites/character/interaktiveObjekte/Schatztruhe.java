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
import com.mygdx.anima.sprites.character.zauber.ZauberGenerator;

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
    public int truhenID,nachbedfalse,nachbedtrue,truhentyp;

    public Schatztruhe(){
       super();
    }
    public void init(Playscreen screen, float x, float y,int truhenTyp,int truhenID,String inhalt,boolean isClosed,int nachbedfalse,int nachbedtrue){
        super.init(screen,x,y);
        this.inhalt=inhalt;
        defineItem(8,8);
        this.truhentyp=truhenTyp;
        this.truhenID=truhenID;
        this.nachbedtrue=nachbedtrue;
        this.nachbedfalse=nachbedfalse;
        closed=isClosed;
        switch(truhenTyp){
            case 1:
            spriteOpen=screen.getGame().getAssetManager().getSchatztruheOpenGold();
            spriteClose=screen.getGame().getAssetManager().getSchatztruheCloseGold();
            openTruhe =screen.getGame().getAssetManager().getTruheGoldAnimation();
            break;
            case 2:
            spriteOpen=screen.getGame().getAssetManager().getSchatztruheOpenBraun();
            spriteClose=screen.getGame().getAssetManager().getSchatztruheCloseBraun();
            openTruhe =screen.getGame().getAssetManager().getTruheBraunAnimation();
            break;
            case 3:
                spriteOpen=screen.getGame().getAssetManager().getSchatztruheOpenBlau();
                spriteClose=screen.getGame().getAssetManager().getSchatztruheCloseBlau();
                openTruhe =screen.getGame().getAssetManager().getTruheBlauAnimation();
                break;
        }

    }
    @Override
    public void defineItem(float x,float y) {
        BodyDef bdef= new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        body =world.createBody(bdef);
        body.setActive(true);

        FixtureDef fdef= new FixtureDef();
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(x/ AnimaRPG.PPM,y/AnimaRPG.PPM,new Vector2(getWidth()/2, getHeight()/2),0);
        fdef.filter.categoryBits=AnimaRPG.OBJECT_BIT;
        fdef.filter.maskBits=AnimaRPG.HERO_BIT | AnimaRPG.ENEMY_BIT | AnimaRPG.HERO_SENSOR ;
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
                region = (TextureRegion) openTruhe.getKeyFrame(stateTimer, false);
                if(openTruhe.isAnimationFinished(stateTimer)){
                    runOpenAnimation=false;closed=false;
                    if(nachbedtrue!=0){
                        getHeld().getEventList()[nachbedtrue]=true;
                    }
                    if(nachbedfalse!=0){
                        getHeld().getEventList()[nachbedfalse]=false;
                    }
                    if(truhentyp==1 || truhentyp==2){
                        screen.setCurrentItemsprite(ItemGenerator.generateItem(screen,getX(),getY()+getHeight()/2,inhalt));
                    }
                    else{
                        screen.setCurrentZauberfundSprite(ZauberGenerator.generateZauber(screen,getX(),getY()+getHeight()/2,inhalt));
                    }

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
