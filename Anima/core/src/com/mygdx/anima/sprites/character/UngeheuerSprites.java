package com.mygdx.anima.sprites.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Arrow;

/**
 * Created by Steffen on 13.11.2016.
 */

public class UngeheuerSprites extends SpriteVorlage{

    public enum State {STANDING, WALKING, MELEE, DYING, DEAD,FEEDBACK};
    public State currentState, previousState;

    public Animation UpWalk, DownWalk, LeftWalk, RightWalk;
    public Animation UpMelee1, DownMelee1, LeftMelee1, RightMelee1;

    public Animation Dying;
    public TextureRegion Died;
    public TextureRegion standingDownSprite, standingUpSprite,
            standingLeftSprite, standingRightSprite;
    public Vector2 velocity,basicPosition; //BasicPosition ist ein Zwischenspeicher für Angriffe mit großen Waffen. Bei folgenden Animationen kehrt die Figur in die BasicPosition zurück
    //Texture Atlas und QuellenTextureRegion
    public TextureAtlas atlas;
    TextureRegion WalkRegion,AttackRegion, DieRegion;
    //Fixturen und ihre Trigger
    //(Der FixtureTrigger wird genutzt,damit eine Fixture nur einmal erzeugt wird, wenn die Animation mehrals 80% durch ist
    public Fixture meleeFixture,sensorFixture;
    public boolean meleeFixtureErzeugen,bowFixtureErzeugen, triggerFixture, uebergroeßeAktiv;
    CircleShape sensorCircleShape;
    FixtureDef fdefAttack;

    public boolean meleeExists, castExists;
    public boolean runMeleeAnimation,istHeld,hitByFeedback;

    public float hitdauer,wertHeld=0.07f;

    //Einstellungen

    //BreiteEinstellungen, da man mit verschiedenen Waffen verschieden breit ist.
    public int breite;
    public int hoehe;
    public float framesCast=7,framesStich=8,framesSchwert=6,framesWalk,framesDie=6,frameArcher=13;
    public float castSpeed,bowSpeed,meleeSpeed,thrustSpeed;
    float regenerationTimer;
    //Konstruktor für Enemies
    public UngeheuerSprites(){
    }
    public void init(AnimaRPG anima, Playscreen screen,int maxhp,int maxmana,int regMana,int speed,int schadenNah,int schadenfern,int schadenzauber,int ruestung,float boundsX,float boundsY,float castSpeed,float bowSpeed,float meleeSpeed,float thrustSpeed){
        this.world = screen.getWorld();
        this.screen=screen;
        setMaxHitpoints(maxhp);
        setMaxMana(maxmana);
        setRegMana(regMana);
        setGeschwindigkeitLaufen(speed);
        this.setSchadenNah(schadenNah);
        this.setSchadenFern(schadenfern);
        this.setSchadenZauber(schadenzauber);
        this.setRuestung(ruestung);
        this.bowSpeed=bowSpeed;
        this.meleeSpeed=meleeSpeed;
        this.castSpeed=castSpeed;
        this.thrustSpeed=thrustSpeed;

        istHeld=false;
        currentState = State.STANDING;
        previousState = State.STANDING;
        currentRichtung = Richtung.Unten;
        stateTimer = 0;
        runMeleeAnimation=false;
        runDying=false;
        meleeExists=false;
        destroyed=false;
        uebergroeßeAktiv=false;
        triggerFixture=true;
        setBounds(0, 0, boundsX / AnimaRPG.PPM, boundsY/ AnimaRPG.PPM);
    }
    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        switch (currentState) {
            case DYING:
                // Baustelle
                region=standingDownSprite;
                if(stateTimer>0.3f){
                    dead=true;}
                break;
            case DEAD:
                if(runDying && stateTimer >0.7f ){
                    destroyBody();
                }
                region=standingDownSprite;
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
                            region = LeftMelee1.getKeyFrame(stateTimer, true);
                            if (triggerFixture && stateTimer >= LeftMelee1.getAnimationDuration() * 0.3) {
                                meleeFixtureErzeugen = true;
                                triggerFixture = false;
                            }
                            if (LeftMelee1.isAnimationFinished(stateTimer)) {
                                stateTimer = 0;
                                runMeleeAnimation = false;
                            }
                        break;
                    case Rechts:
                            region = RightMelee1.getKeyFrame(stateTimer, true);
                            if (triggerFixture && stateTimer >= RightMelee1.getAnimationDuration() * 0.3) {
                                meleeFixtureErzeugen = true;
                                triggerFixture = false;
                            }
                            if (RightMelee1.isAnimationFinished(stateTimer)) {
                                stateTimer = 0;
                                runMeleeAnimation = false;
                            }
                        break;
                    case Oben:
                            region = UpMelee1.getKeyFrame(stateTimer, true);
                            if (triggerFixture && stateTimer >= UpMelee1.getAnimationDuration() * 0.3) {
                                meleeFixtureErzeugen = true;
                                triggerFixture = false;
                            }
                            if (UpMelee1.isAnimationFinished(stateTimer)) {
                                stateTimer = 0;
                                runMeleeAnimation = false;
                            }
                        break;
                    case Unten:
                            region = DownMelee1.getKeyFrame(stateTimer, true);
                            if (triggerFixture && stateTimer >= DownMelee1.getAnimationDuration() * 0.3) {
                                meleeFixtureErzeugen = true;
                                triggerFixture = false;
                            }
                            if (DownMelee1.isAnimationFinished(stateTimer)) {
                                stateTimer = 0;
                                runMeleeAnimation = false;
                            }
                        break;
                    default:
                        region = standingDownSprite;
                        break;
                }
                break;
            case FEEDBACK:
                switch (currentRichtung) {
                    case Links:
                        region=standingLeftSprite;
                        break;
                    case Rechts:
                        region=standingRightSprite;
                        break;
                    case Oben:
                        region = standingUpSprite;
                        break;
                    case Unten:
                        region =standingDownSprite;
                        break;
                    default:
                        region = standingDownSprite;
                        break;
                }
                if(stateTimer>=0.5){hitByFeedback=false;}
                break;
            default:
                region = standingDownSprite;
                break;
        }
        return region;
    }

    public State getState() {
        Vector2 velocity = b2body.getLinearVelocity();
        if(dead){
            return State.DEAD;}
        else if(hitByFeedback){
            runMeleeAnimation=false;
            return State.FEEDBACK;}
        else if(runDying){
            return State.DYING;}
        else if(runMeleeAnimation)
            return State.MELEE;
        else if(velocity.x == 0 && velocity.y == 0)
            {return State.STANDING;}
        else
            return State.WALKING;
    }
    public void createSensor(){
        sensorCircleShape = new CircleShape();
        sensorCircleShape.setRadius(5 / AnimaRPG.PPM);
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

            fdefSensor.filter.categoryBits = AnimaRPG.UNGEHEUER_SENSOR_BIT;
            fdefSensor.filter.maskBits = AnimaRPG.HERO_BIT | AnimaRPG.HERO_OBERKOERPER;

        fdefSensor.shape = sensorCircleShape;
        fdefSensor.isSensor = true;
        sensorFixture= b2body.createFixture(fdefSensor);
        sensorFixture.setUserData(this);
    }
    public void update(float dt){
        //  b2body beweggt sich nur, wenn kein nahkampfangriff ausgeführt wird
        if(b2body!=null && !meleeExists){
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);}
        if(getCurrentRichtung()!=getPreviousRichtung() && !destroyed) {
            sensorAnpassen();
        }
        if(meleeFixtureErzeugen){meleeFixtureErzeugen=false;meleeFixtureErzeugen();}
        if(!runMeleeAnimation && meleeExists && b2body!=null){
            b2body.destroyFixture(meleeFixture);
            meleeExists=false;triggerFixture=true;}
       //waffenNah else(bowFixtureErzeugen){
        regenerationTimer+=dt;
        if(regenerationTimer>=3 && getCurrentMana()<getMaxMana()){
            setCurrentMana(getCurrentMana()+getRegMana());
            regenerationTimer=0;}
        }
    public void meleeFixtureDefinieren(Vector2 richtungsVector){
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(15 / AnimaRPG.PPM);
        circleShape.setPosition(richtungsVector);
        fdefAttack = new FixtureDef();
        fdefAttack.filter.categoryBits = AnimaRPG.UNGEHEUER_ATTACK_BIT;
            fdefAttack.filter.maskBits = AnimaRPG.HERO_BIT | AnimaRPG.HERO_OBERKOERPER;
        fdefAttack.shape = circleShape;
        fdefAttack.isSensor = true;
        runMeleeAnimation = true;
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
        fdefSensor.filter.categoryBits = AnimaRPG.UNGEHEUER_SENSOR_BIT;
        fdefSensor.filter.maskBits = AnimaRPG.HERO_BIT | AnimaRPG.HERO_OBERKOERPER;

        fdefSensor.shape = sensorCircleShape;
        fdefSensor.isSensor = true;
        sensorFixture= b2body.createFixture(fdefSensor);
        sensorFixture.setUserData(this);
    }
    public void meleeFixtureErzeugen(){
        meleeFixture = b2body.createFixture(fdefAttack);
        meleeFixture.setUserData(this);
        meleeExists= true;
    }
    public void changeGrafiken(String quelle){
        atlas = new TextureAtlas(quelle+".pack");
        DieRegion =atlas.findRegion("die");
    }
    public void reset(){
        super.reset();
        meleeExists=false; castExists=false;
        runMeleeAnimation=false;
        istHeld=false;
        hitByFeedback=false;
    }
}