package com.mygdx.anima.sprites.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;

/**
 * Created by Steffen on 13.11.2016.
 */

public class HumanoideSprites extends Sprite {
    public World world;
    public Body b2body;
    public float stateTimer;

    public enum State {STANDING, WALKING, MELEE, DYING, DEAD, ARCHERY};
    public enum Richtung {Links, Rechts, Oben, Unten};
    public State currentState, previousState;
    public Richtung previousRichtung, currentRichtung;
    public Animation UpWalk, DownWalk, LeftWalk, RightWalk;
    public Animation UpMelee, DownMelee, LeftMelee, RightMelee;
    public Animation UpBow, DownBow, LeftBow, RightBow;

    public Animation Dying;
    public TextureRegion Died;
    public TextureRegion standingDownSprite, standingUpSprite,
            standingLeftSprite, standingRightSprite;
    public Texture spriteQuelle;
    public Vector2 velocity;

    public Fixture meleeFixture, sensorFixture;
    CircleShape sensorCircleShape;
    public boolean meleeExists;
    public boolean runMeleeAnimation,istHeld,runArchery;
    public boolean runDying,dead,destroyed;

    //BreiteEinstellungen, da man mit verschiedenen Waffen verschieden breit ist.
    public int breite;
    public int hoehe;
    public HumanoideSprites(Playscreen screen, String quelle,Boolean istHeld) {
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        currentRichtung = Richtung.Unten;
        stateTimer = 0;
        runMeleeAnimation=false;
        runDying=false;
        meleeExists=false;

        //Spritebreite
        breite=64;
        hoehe=64;

        //Animationen erstellen
        updateTextures(quelle);
        setBounds(0, 0, 42 / AnimaRPG.PPM, 42/ AnimaRPG.PPM);
        setRegion(standingDownSprite);
    }
    public void updateTextures(String quelle){
        spriteQuelle = new Texture(quelle);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(spriteQuelle, i*breite, 512, breite, hoehe));
        }
        UpWalk = new Animation(0.1f, frames);
        frames.clear();
        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(spriteQuelle,i *breite, 640, breite, hoehe));
        }
        DownWalk = new Animation(0.1f, frames);
        frames.clear();
        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 577, breite, hoehe));
        }
        LeftWalk = new Animation(0.1f, frames);
        frames.clear();
        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 704, breite, hoehe));
        }
        RightWalk = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(spriteQuelle,i* breite, 768, breite, hoehe));
        }
        UpMelee = new Animation(0.07f, frames);
        frames.clear();
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(spriteQuelle, i*breite,896, breite, hoehe));
        }
        DownMelee = new Animation(0.07f, frames);
        frames.clear();
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(spriteQuelle, i * breite, 832, breite, hoehe));
        }
        LeftMelee= new Animation(0.07f, frames);
        frames.clear();
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 960, breite, hoehe));
        }
        RightMelee = new Animation(0.07f, frames);
        frames.clear();
        for (int i = 0; i < 13; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 1024, breite, hoehe));
        }
        UpBow = new Animation(0.05f, frames);
        frames.clear();
        for (int i = 0; i < 13; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 1152, breite, hoehe));
        }
        DownBow = new Animation(0.05f, frames);
        frames.clear();
        for (int i = 0; i < 13; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 1216, breite, hoehe));
        }
        RightBow = new Animation(0.05f, frames);
        frames.clear();
        for (int i = 0; i < 13; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 1088, breite, hoehe));
        }
        LeftBow = new Animation(0.05f, frames);

        frames.clear();
        for (int i = 1; i < 5; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 1280,breite,hoehe));
        }
        Dying=new Animation(0.2f,frames);
        frames.clear();
        Died=new TextureRegion(spriteQuelle,320,1280,breite,hoehe);
        standingDownSprite = new TextureRegion(spriteQuelle, 0, 640, breite, hoehe);
        standingUpSprite = new TextureRegion(spriteQuelle, 0, 512, breite, hoehe);
        standingLeftSprite = new TextureRegion(spriteQuelle, 0, 576, breite, hoehe);
        standingRightSprite = new TextureRegion(spriteQuelle, 0, 704, breite, hoehe);
        setRegion(standingDownSprite);
    }
    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        // System.out.println(getState().toString()+ stateTimer);
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        switch (currentState) {
            case DYING:
                if(!destroyed){
                    destroyBody();destroyed=true;}
                region=Dying.getKeyFrame(stateTimer,false);
                if(Dying.isAnimationFinished(stateTimer)){
                    dead=true;runDying=false;}
                break;
            case DEAD:
                if(!dead)
                    dead=true;
                region=Died;
                break;
            case STANDING:
                switch (currentRichtung) {
                    case Links:
                        region = standingLeftSprite;
                        break;
                    case Rechts:
                        region = standingRightSprite;
                        break;
                    case Oben:
                        region = standingUpSprite;
                        break;
                    case Unten:
                        region = standingDownSprite;
                        break;
                    default:
                        region = standingDownSprite;
                        break;
                }
                break;
            case WALKING:
                switch (currentRichtung) {
                    case Links:
                        region = LeftWalk.getKeyFrame(stateTimer, true);
                        break;
                    case Rechts:
                        region = RightWalk.getKeyFrame(stateTimer, true);
                        break;
                    case Oben:
                        region = UpWalk.getKeyFrame(stateTimer, true);
                        break;
                    case Unten:
                        region = DownWalk.getKeyFrame(stateTimer, true);
                        break;
                    default:
                        region = standingDownSprite;
                        break;
                }
                break;
            case MELEE:
                switch (currentRichtung) {
                    case Links:
                        region = LeftMelee.getKeyFrame(stateTimer, true);
                        if(LeftMelee.isAnimationFinished(stateTimer))
                            runMeleeAnimation=false;
                        break;
                    case Rechts:
                        region = RightMelee.getKeyFrame(stateTimer, true);
                        if(RightMelee.isAnimationFinished(stateTimer))
                            runMeleeAnimation=false;
                        break;
                    case Oben:
                        region = UpMelee.getKeyFrame(stateTimer, true);
                        if(UpMelee.isAnimationFinished(stateTimer))
                            runMeleeAnimation=false;
                        break;
                    case Unten:
                        region = DownMelee.getKeyFrame(stateTimer, true);
                        if(DownMelee.isAnimationFinished(stateTimer))
                            runMeleeAnimation=false;
                        break;
                    default:
                        region = standingDownSprite;
                        break;
                }
                break;
            case ARCHERY:
                switch (currentRichtung) {
                    case Links:
                        region = LeftBow.getKeyFrame(stateTimer, true);
                        if(LeftBow.isAnimationFinished(stateTimer))
                            runArchery=false;
                        break;
                    case Rechts:
                        region = RightBow.getKeyFrame(stateTimer, true);
                        if(RightBow.isAnimationFinished(stateTimer))
                            runArchery=false;
                        break;
                    case Oben:
                        region = UpBow.getKeyFrame(stateTimer, true);
                        if(UpBow.isAnimationFinished(stateTimer))
                            runArchery=false;
                        break;
                    case Unten:
                        region = DownBow.getKeyFrame(stateTimer, true);
                        if(DownBow.isAnimationFinished(stateTimer))
                            runArchery=false;
                        break;
                    default:
                        region = standingDownSprite;
                        break;
                }
                break;
            default:
                region = standingDownSprite;
                break;
        }
        return region;
    }

    public State getState() {
        Vector2 velocity = b2body.getLinearVelocity();
        if(runDying){
            return State.DYING;}
        else if(dead){
            return State.DEAD;}
        else if(runArchery)
            return State.ARCHERY;
        else if(runMeleeAnimation)
            return State.MELEE;
        else if(velocity.x == 0 && velocity.y == 0)
            return State.STANDING;
        else
            return State.WALKING;
    }

    public void destroyBody(){
        world.destroyBody(b2body);
    }
    public void readyToDie(){runDying=true;}

    public void update(float dt){

        if(getCurrentRichtung()!=getPreviousRichtung() && !destroyed)
            sensorAnpassen();
    }
    public void setCurrentRichtung(int richtung) {
        previousRichtung=currentRichtung;
        switch (richtung) {
            case 0:
                currentRichtung = Richtung.Links;
                break;
            case 1:
                currentRichtung = Richtung.Rechts;
                break;
            case 2:
                currentRichtung = Richtung.Oben;
                break;
            case 3:
                currentRichtung = Richtung.Unten;
                break;
            default:
                break;
        }
    }

    public void createSensor(Boolean istHeldtemp){
        sensorCircleShape = new CircleShape();
        this.istHeld=istHeldtemp;
        sensorCircleShape.setRadius(4 / AnimaRPG.PPM);
        Vector2 sensorRichtungsVector;
        switch (getCurrentRichtung()) {
            case Rechts:
                sensorRichtungsVector = new Vector2(5 / AnimaRPG.PPM, -8 / AnimaRPG.PPM);
                break;
            case Links:
                sensorRichtungsVector = new Vector2(-5 / AnimaRPG.PPM, -8 / AnimaRPG.PPM);
                break;
            case Oben:
                sensorRichtungsVector = new Vector2(0, 5 / AnimaRPG.PPM);
                break;
            case Unten:
                sensorRichtungsVector = new Vector2(0, -5 / AnimaRPG.PPM);
                break;
            default:
                sensorRichtungsVector = new Vector2(0, 0);
                break;

        }
        sensorCircleShape.setPosition(sensorRichtungsVector);
        FixtureDef fdefSensor = new FixtureDef();
        if(istHeld) {
            fdefSensor.filter.categoryBits = AnimaRPG.HERO_SENSOR;
            fdefSensor.filter.maskBits = AnimaRPG.OBJECT_BIT;
        }
        else {
              fdefSensor.filter.categoryBits = AnimaRPG.ENEMY_SENSOR;
              fdefSensor.filter.maskBits = AnimaRPG.HERO_BIT;
        }
        fdefSensor.shape = sensorCircleShape;
        fdefSensor.isSensor = true;
        sensorFixture= b2body.createFixture(fdefSensor);
        sensorFixture.setUserData(this);
    }
    public void sensorAnpassen(){
        b2body.destroyFixture(sensorFixture);
        Vector2 richtungsVector;
        switch (getCurrentRichtung()) {
            case Rechts:
                richtungsVector = new Vector2(10 / AnimaRPG.PPM, -8 / AnimaRPG.PPM);
                break;
            case Links:
                richtungsVector = new Vector2(-10 / AnimaRPG.PPM, -8 / AnimaRPG.PPM);
                break;
            case Oben:
                richtungsVector = new Vector2(0, 7 / AnimaRPG.PPM);
                break;
            case Unten:
                richtungsVector = new Vector2(0, -20 / AnimaRPG.PPM);
                break;
            default:
                richtungsVector = new Vector2(0, 0);
                break;
        }
        sensorCircleShape.setPosition(richtungsVector);
        FixtureDef fdefSensor = new FixtureDef();
        if(istHeld) {
            fdefSensor.filter.categoryBits = AnimaRPG.HERO_SENSOR;
            fdefSensor.filter.maskBits = AnimaRPG.OBJECT_BIT;
        }
        else {
            fdefSensor.filter.categoryBits = AnimaRPG.ENEMY_SENSOR;
            fdefSensor.filter.maskBits = AnimaRPG.HERO_BIT;
        }
        fdefSensor.shape = sensorCircleShape;
        fdefSensor.isSensor = true;
        sensorFixture= b2body.createFixture(fdefSensor);
        sensorFixture.setUserData(this);
    }
    public Richtung getCurrentRichtung(){
        return currentRichtung;
    }
    public Richtung getPreviousRichtung(){ return previousRichtung; }

}