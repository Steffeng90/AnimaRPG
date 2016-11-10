package com.mygdx.anima.sprites.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;

/**
 * Created by Steffen on 09.11.2016.
 */

public class Held extends Sprite{

    public World world;
    public Body b2body;
    private float stateTimer;
    // Alles zur Animation, Texturen und States
    public enum State {STANDING,WALKING,DEAD};
    public enum Richtung{Links,Rechts,Oben,Unten};
    public State currentState,previousState;
    public Richtung currentRichtung;
    public Animation UpWalk,DownWalk,LeftWalk,RightWalk;
    public TextureRegion standingDownSprite,standingUpSprite,
            standingLeftSprite,standingRightSprite;
    public Texture spriteQuelle;

    public Held(Playscreen screen)
    {
        this.world=screen.getWorld();
        currentState=State.STANDING;
        previousState=State.STANDING;
        currentRichtung=Richtung.Unten;
        stateTimer=0;

        //Animationen erstellen
        spriteQuelle=new Texture("character/female_hero_sprite.png");
        Array<TextureRegion> frames=new Array<TextureRegion>();
        for(int i=1; i<9;i++){frames.add(new TextureRegion(spriteQuelle,18+i*27+i*37,527,27,50));}
        UpWalk=new Animation(0.1f,frames);
        frames.clear();
        for(int i=1; i<9;i++){frames.add(new TextureRegion(spriteQuelle,18+i*27+i*37,653,27,50));}
        DownWalk=new Animation(0.1f,frames);
        frames.clear();
        for(int i=1; i<9;i++){frames.add(new TextureRegion(spriteQuelle,18+i*27+i*37,589,27,54));}
        LeftWalk=new Animation(0.1f,frames);
        frames.clear();
        for(int i=1; i<9;i++){frames.add(new TextureRegion(spriteQuelle,18+i*27+i*37,717,27,54));}
        RightWalk=new Animation(0.1f,frames);
        frames.clear();

        standingDownSprite =new TextureRegion(spriteQuelle,18,653,27,50);
        standingUpSprite =new TextureRegion(spriteQuelle,18,527,27,50);
                standingLeftSprite =new TextureRegion(spriteQuelle,18,590,27,50);
        standingRightSprite =new TextureRegion(spriteQuelle,18,718,27,50);


        createHero();
        setBounds(0,0,24/AnimaRPG.PPM, 50/AnimaRPG.PPM);
        setRegion(standingDownSprite);
    }
    public TextureRegion getFrame(float dt){
    currentState=getState();
        TextureRegion region;
        switch(currentState)
        {
            case STANDING:
                switch(currentRichtung)
                {
                    case Links:region=  standingLeftSprite;break;
                    case Rechts:region= standingRightSprite;break;
                    case Oben:region= standingUpSprite;break;
                    case Unten:region= standingDownSprite;break;
                    default: region=standingDownSprite;break;
                }break;
            case WALKING:
                switch(currentRichtung)
                {
                    case Links:region=  LeftWalk.getKeyFrame(stateTimer,true);break;
                    case Rechts:region= RightWalk.getKeyFrame(stateTimer,true);break;
                    case Oben:region= UpWalk.getKeyFrame(stateTimer,true);break;
                    case Unten:region=  DownWalk.getKeyFrame(stateTimer,true);break;
                    default: region=standingDownSprite;break;
                }
                break;
            default: region= standingDownSprite;break;
        }
        stateTimer= currentState ==previousState ? stateTimer +dt:0;
        previousState=currentState;
        return region;
    }
    public State getState(){
        Vector2 velocity=b2body.getLinearVelocity();
        if(velocity.x==0 && velocity.y==0)
            return State.STANDING;
        else
            return State.WALKING;
    }
    public void createHero(){
        BodyDef bdef=new BodyDef();
        bdef.position.set(30/ AnimaRPG.PPM,30/AnimaRPG.PPM);
        bdef.type=BodyDef.BodyType.DynamicBody;
        b2body=world.createBody(bdef);

        FixtureDef fdef=new FixtureDef();
        PolygonShape shape =new PolygonShape();

        shape.setAsBox(10/ AnimaRPG.PPM,10/AnimaRPG.PPM,new Vector2(0,-10/AnimaRPG.PPM),0);
        fdef.shape=shape;
        b2body.createFixture(fdef).setUserData(this);

    }
    public void update(float dt)
    {
        setRegion(getFrame(dt));

        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);

    }
    public void setCurrentRichtung(int richtung)
    {
        switch(richtung){
            case 0: currentRichtung=Richtung.Links;break;
            case 1: currentRichtung=Richtung.Rechts;break;
            case 2: currentRichtung=Richtung.Oben;break;
            case 3: currentRichtung=Richtung.Unten;break;
            default: break;
        }


    }
}
