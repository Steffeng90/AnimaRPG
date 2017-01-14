package com.mygdx.anima.sprites.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Arrow;

/**
 * Created by Steffen on 13.11.2016.
 */

public class HumanoideSprites extends Sprite {
    public Playscreen screen;
    public World world;
    public Body b2body;
    public float stateTimer, feedbackDauer;
    // TODO Feedback-Dauer einfügen


    public enum State {STANDING, WALKING, MELEE, DYING, DEAD, ARCHERY, CASTING,FEEDBACK};
    public enum Richtung {Links, Rechts, Oben, Unten};
    public State currentState, previousState;
    public Richtung previousRichtung, currentRichtung;
    public Animation UpWalk, DownWalk, LeftWalk, RightWalk;
    public Animation UpMelee, DownMelee, LeftMelee, RightMelee;
    public Animation UpBow, DownBow, LeftBow, RightBow;
    public Animation UpCast,DownCast, LeftCast,RightCast;

    public Animation Dying;
    public TextureRegion Died;
    public TextureRegion standingDownSprite, standingUpSprite,
            standingLeftSprite, standingRightSprite;
    public Texture spriteQuelle;
    public Vector2 velocity;

    //Fixturen und ihre Trigger
    //(Der FixtureTrigger wird genutzt,damit eine Fixture nur einmal erzeugt wird, wenn die Animation mehrals 80% durch ist
    public Fixture meleeFixture,castFixture,bowFixture,thrustfixtureErzeugen,sensorFixture;
    public boolean meleeFixtureErzeugen,castFixtureErzeugen,bowFixtureErzeugen,thrustFixtureErzeugen, triggerFixture;
    FixtureDef fdefAttack;
    CircleShape sensorCircleShape;
    public Vector2 arrowStartVector,arrowFlugVector;

    public boolean meleeExists, castExists;
    public boolean runMeleeAnimation,istHeld,runArchery,runCasting,hitByFeedback;
    public boolean runDying,dead,destroyed;
    public float hitdauer,wertHeld=0.07f;

    //Einstellungen
    private int currentHitpoints,maxHitpoints,currentMana,maxMana,regMana,regHitpoints,
            schadenNah,schadenFern,schadenZauber,ruestung,zauberwiderstand;
    private float geschwindigkeitLaufen;
    //BreiteEinstellungen, da man mit verschiedenen Waffen verschieden breit ist.
    public int breite;
    public int hoehe;
    public static int framesCast=7,framesStich=8,framesSchwert=6,framesWalk=9,framesDie=6,frameArcher=13;
    public float castSpeed,bowSpeed,meleeSpeed,thrustSpeed;
    float regenerationTimer;
    //Konstruktor für Enemies
    public HumanoideSprites(Playscreen screen,int maxhp,int maxmana,int regMana,int speed,int schadenNah,int schadenfern,int schadenzauber,int ruestung,int boundsX,int boundsY,float castSpeed,float bowSpeed,float meleeSpeed,float thrustSpeed){
        this.world = screen.getWorld();
        this.screen=screen;
        setMaxHitpoints(maxhp);
        setMaxMana(maxmana);
        setRegMana(regMana);
        geschwindigkeitLaufen=speed;
        this.schadenNah=schadenNah;
        this.schadenFern=schadenfern;
        this.schadenZauber=schadenzauber;
        this.ruestung=ruestung;
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
        breite=64;
        hoehe=64;
        triggerFixture=true;
        setBounds(0, 0, boundsX / AnimaRPG.PPM, boundsY/ AnimaRPG.PPM);
        //setRegion(standingDownSprite);
    }

    //Konstruktor für Held
    public HumanoideSprites(Playscreen screen, String quelle,Boolean istHeld) {
        this.world = screen.getWorld();
        this.istHeld=istHeld;
        this.bowSpeed=1f;
        this.meleeSpeed=0.5f;
        this.castSpeed=2f;
        this.thrustSpeed=2;

        currentState = State.STANDING;
        previousState = State.STANDING;
        currentRichtung = Richtung.Unten;
        stateTimer = 0;
        runMeleeAnimation=false;
        triggerFixture=true;
        runDying=false;
        meleeExists=false;
        breite=64;
        hoehe=64;
            //hitdauer=wertHeld;
        hitdauer=1;

        //Animationen erstellen
            updateTextures(quelle);
            setBounds(0, 0, 42 / AnimaRPG.PPM, 42/ AnimaRPG.PPM);
            setRegion(standingDownSprite);
            }
        //Spritebreite


    public void updateTextures(String quelle){
        spriteQuelle = new Texture(quelle);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < framesWalk; i++) {
            frames.add(new TextureRegion(spriteQuelle, i*breite, 512, breite, hoehe));
        }
        UpWalk = new Animation(0.1f, frames);
        frames.clear();
        for (int i = 0; i < framesWalk; i++) {
            frames.add(new TextureRegion(spriteQuelle,i *breite, 640, breite, hoehe));
        }
        DownWalk = new Animation(0.1f, frames);
        frames.clear();
        for (int i = 0; i < framesWalk; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 577, breite, hoehe));
        }
        LeftWalk = new Animation(0.1f, frames);
        frames.clear();
        for (int i = 0; i < framesWalk; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 704, breite, hoehe));
        }
        RightWalk = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < framesSchwert; i++) {
            frames.add(new TextureRegion(spriteQuelle,i* breite, 768, breite, hoehe));
        }
        UpMelee = new Animation(meleeSpeed/framesSchwert, frames);
        frames.clear();
        for (int i = 0; i < framesSchwert; i++) {
            frames.add(new TextureRegion(spriteQuelle, i*breite,896, breite, hoehe));
        }
        DownMelee = new Animation(meleeSpeed/framesSchwert, frames);
        frames.clear();
        for (int i = 0; i < framesSchwert; i++) {
            frames.add(new TextureRegion(spriteQuelle, i * breite, 832, breite, hoehe));
        }
        LeftMelee= new Animation(meleeSpeed/framesSchwert, frames);
        frames.clear();
        for (int i = 0; i < framesSchwert; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 960, breite, hoehe));
        }
        RightMelee = new Animation(meleeSpeed/framesSchwert, frames);
        frames.clear();
        for (int i = 0; i < frameArcher; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 1024, breite, hoehe));
        }
        UpBow = new Animation(bowSpeed/frameArcher, frames);
        frames.clear();
        for (int i = 0; i < frameArcher; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 1152, breite, hoehe));
        }
        DownBow = new Animation(bowSpeed/frameArcher, frames);
        frames.clear();
        for (int i = 0; i < frameArcher; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 1216, breite, hoehe));
        }
        RightBow = new Animation(bowSpeed/frameArcher, frames);
        frames.clear();
        for (int i = 0; i < frameArcher; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 1088, breite, hoehe));
        }
        LeftBow = new Animation(bowSpeed/frameArcher, frames);
        frames.clear();
        for (int i = 0; i < framesCast; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 0, breite, hoehe));
        }
        UpCast = new Animation(castSpeed/framesCast, frames);
        frames.clear();
        for (int i = 0; i < framesCast; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 128, breite, hoehe));
        }
        DownCast= new Animation(castSpeed/framesCast, frames);
        frames.clear();
        for (int i = 0; i < framesCast; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 192, breite, hoehe));
        }
        RightCast = new Animation(castSpeed/framesCast, frames);
        frames.clear();
        for (int i = 0; i < framesCast; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 64, breite, hoehe));
        }
        LeftCast = new Animation(castSpeed/framesCast, frames);

        frames.clear();
        for (int i = 0; i < framesDie; i++) {
            frames.add(new TextureRegion(spriteQuelle, i *breite, 1280,breite,hoehe));
        }
        Dying=new Animation(0.2f,frames);
        frames.clear();
        Died=new TextureRegion(spriteQuelle,320,1280,breite,hoehe);
        standingDownSprite = new TextureRegion(spriteQuelle, 0, 640, breite, hoehe);
        standingUpSprite = new TextureRegion(spriteQuelle, 0,512, breite, hoehe);
        standingLeftSprite = new TextureRegion(spriteQuelle, 0, 577, breite, hoehe);
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
                region=Dying.getKeyFrame(stateTimer,false);
                if(Dying.isAnimationFinished(stateTimer)){
                    dead=true;}
                break;
            case DEAD:
                if(runDying && stateTimer >3 ){
                    destroyBody();
                }
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
                        if(triggerFixture && stateTimer>=LeftMelee.getAnimationDuration()*0.3)
                        {meleeFixtureErzeugen=true;triggerFixture =false;}
                        if(LeftMelee.isAnimationFinished(stateTimer))
                        {stateTimer=0;runMeleeAnimation=false;}
                        break;
                    case Rechts:
                        region = RightMelee.getKeyFrame(stateTimer, true);
                        if(triggerFixture && stateTimer>=RightMelee.getAnimationDuration()*0.3)
                        {meleeFixtureErzeugen=true;triggerFixture =false;}
                        if(RightMelee.isAnimationFinished(stateTimer))
                        {stateTimer=0;runMeleeAnimation=false;}
                        break;
                    case Oben:
                        region = UpMelee.getKeyFrame(stateTimer, true);
                        if(triggerFixture && stateTimer>=UpMelee.getAnimationDuration()*0.3)
                        {meleeFixtureErzeugen=true;triggerFixture =false;}
                        if(UpMelee.isAnimationFinished(stateTimer))
                        {;stateTimer=0;runMeleeAnimation=false;}
                        break;
                    case Unten:
                        region = DownMelee.getKeyFrame(stateTimer, true);
                        if(triggerFixture && stateTimer>=DownMelee.getAnimationDuration()*0.3)
                        {meleeFixtureErzeugen=true;triggerFixture =false;}
                        if(DownMelee.isAnimationFinished(stateTimer))
                        {stateTimer=0;runMeleeAnimation=false;}
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
                        if(triggerFixture && stateTimer>=LeftBow.getAnimationDuration()*0.8)
                            {bowFixtureErzeugen=true;triggerFixture =false;}
                        if(LeftBow.isAnimationFinished(stateTimer))
                            {runArchery=false;triggerFixture=true;}
                        break;
                    case Rechts:
                        region = RightBow.getKeyFrame(stateTimer, true);
                        if(triggerFixture && stateTimer>=RightBow.getAnimationDuration()*0.8)
                            {bowFixtureErzeugen=true;triggerFixture =false;}
                        if(RightBow.isAnimationFinished(stateTimer))
                            {runArchery=false;triggerFixture=true;}
                        break;
                    case Oben:
                        region = UpBow.getKeyFrame(stateTimer, true);
                        if(triggerFixture && stateTimer>=UpBow.getAnimationDuration()*0.8)
                            {bowFixtureErzeugen=true;triggerFixture =false;}
                        if(UpBow.isAnimationFinished(stateTimer))
                            {runArchery=false;triggerFixture=true;}
                        break;
                    case Unten:
                        region = DownBow.getKeyFrame(stateTimer, true);
                        if(triggerFixture && stateTimer>=DownBow.getAnimationDuration()*0.8)
                            {bowFixtureErzeugen=true;triggerFixture =false;}
                        if(DownBow.isAnimationFinished(stateTimer))
                            {runArchery=false;triggerFixture=true;}
                        break;
                    default:
                        region = standingDownSprite;
                        break;
                }
                break;
            case CASTING:
                switch (currentRichtung) {
                    case Links:
                        region = LeftCast.getKeyFrame(stateTimer, true);
                        if(LeftCast.isAnimationFinished(stateTimer)){
                            runCasting=false;}
                        break;
                    case Rechts:
                        region = RightCast.getKeyFrame(stateTimer, true);
                        if(RightCast.isAnimationFinished(stateTimer)){
                            runCasting=false;}
                        break;
                    case Oben:
                        region = UpCast.getKeyFrame(stateTimer, true);
                        if(UpCast.isAnimationFinished(stateTimer)){
                            runCasting=false;}
                        break;
                    case Unten:
                        region = DownCast.getKeyFrame(stateTimer, true);
                        if(DownCast.isAnimationFinished(stateTimer)){
                            runCasting=false;}
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
            runArchery=false;
            runMeleeAnimation=false;
            runCasting=false;
            return State.FEEDBACK;}
        else if(runDying){
            return State.DYING;}
        else if(runArchery)
            return State.ARCHERY;
        else if(runCasting)
            return State.CASTING;
        else if(runMeleeAnimation)
            return State.MELEE;
        else if(velocity.x == 0 && velocity.y == 0)
            return State.STANDING;
        else
            return State.WALKING;
    }

    public void destroyBody(){
        world.destroyBody(b2body);
        b2body.setUserData(null);
        b2body=null;
        runDying=false;
        destroyed=true;
    }
    public void readyToDie(){
        for(Fixture fix:b2body.getFixtureList()){
            Filter filter=fix.getFilterData();
            filter.categoryBits=AnimaRPG.NOTHING_BIT;
            fix.setFilterData(filter);}
        runDying=true;
    }


    public void update(float dt){
        if(b2body!=null){
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);}
        if(getCurrentRichtung()!=getPreviousRichtung() && !destroyed) {
            sensorAnpassen();
        }
        if(meleeFixtureErzeugen){meleeFixtureErzeugen=false;meleeFixtureErzeugen();}
        else if(bowFixtureErzeugen){bowFixtureErzeugen=false;
            new Arrow(world,screen,currentRichtung,arrowStartVector,arrowFlugVector,this);}
        if(!runMeleeAnimation && meleeExists){
            b2body.destroyFixture(meleeFixture);
            meleeExists=false;triggerFixture=true;}
       //s else(bowFixtureErzeugen){
        regenerationTimer+=dt;
        if(regenerationTimer>=3 && getCurrentMana()<getMaxMana()){
            setCurrentMana(getCurrentMana()+getRegMana());
            regenerationTimer=0;}
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
        if(this.istHeld) {
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
    public void meleeFixtureDefinieren(Vector2 richtungsVector){
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(15 / AnimaRPG.PPM);
        circleShape.setPosition(richtungsVector);
        fdefAttack = new FixtureDef();
        if(istHeld){fdefAttack.filter.categoryBits = AnimaRPG.HERO_WEAPON_BIT;
                    fdefAttack.filter.maskBits = AnimaRPG.ENEMY_BIT;}
        else{       fdefAttack.filter.categoryBits = AnimaRPG.ENEMY_ATTACK;
                    fdefAttack.filter.maskBits = AnimaRPG.HERO_BIT;}
        fdefAttack.shape = circleShape;
        fdefAttack.isSensor = true;
        runMeleeAnimation = true;

    }
    public void meleeFixtureErzeugen(){
        meleeFixture = b2body.createFixture(fdefAttack);
        meleeFixture.setUserData(this);
        meleeExists= true;
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
        if(this.istHeld) {
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
    public int getCurrentHitpoints() {
        return currentHitpoints;
    }
    public float getCurrentHitpointsPercent(){ return ((float)currentHitpoints/(float)maxHitpoints);}
    public void setCurrentHitpoints(int currentHitpoints) {
        if(currentHitpoints<=0){
            readyToDie();
            this.currentHitpoints=0;
        }else if(currentHitpoints>maxHitpoints){
            this.currentHitpoints=maxHitpoints;
        }
        else{
            this.currentHitpoints = currentHitpoints;
        }
    }
    public int getMaxHitpoints() {
        return maxHitpoints;
    }

    public void setMaxHitpoints(int maxHitpoints) {
        int temp=this.maxHitpoints;
        this.maxHitpoints = maxHitpoints;
        setCurrentHitpoints(getCurrentHitpoints()+(maxHitpoints-temp));
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int currentMana) {

        if (currentMana <= 0) {
            this.currentMana = 0;
        } else if (currentMana > maxMana) {
            this.currentMana = maxMana;
        } else {
            this.currentMana = currentMana;
        }
    }
    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
        setCurrentMana(maxMana);

    }

    public int getRegMana() {
        return regMana;
    }

    public void setRegMana(int regMana) {
        this.regMana = regMana;
    }

    public int getSchadenNah() {
        return schadenNah;
    }

    public void setSchadenNah(int schadenNah) {
        this.schadenNah = schadenNah;
    }

    public int getSchadenFern() {
        return schadenFern;
    }

    public void setSchadenFern(int schadenFern) {
        this.schadenFern = schadenFern;
    }

    public int getSchadenZauber() {
        return schadenZauber;
    }

    public void setSchadenZauber(int schadenZauber) {
        this.schadenZauber = schadenZauber;
    }

    public int getRuestung() {
        return ruestung;
    }

    public void setRuestung(int ruestung) {
        this.ruestung = ruestung;
    }

    public float getGeschwindigkeitLaufen() {
        return geschwindigkeitLaufen;
    }

    public void setGeschwindigkeitLaufen(float geschwindigkeitLaufen) {
        this.geschwindigkeitLaufen = geschwindigkeitLaufen;
    }
    public int getZauberwiderstand() {
        return zauberwiderstand;
    }

    public void setZauberwiderstand(int zauberwiderstand) {
        this.zauberwiderstand = zauberwiderstand;
    }
    public int getRegHitpoints() {
        return regHitpoints;
    }

    public void setRegHitpoints(int regHitpoints) {
        this.regHitpoints = regHitpoints;
    }
    public Richtung getCurrentRichtung(){
        return currentRichtung;
    }
    public Richtung getPreviousRichtung(){ return previousRichtung; }

    public float getFeedbackDauer() {
        return feedbackDauer;
    }

    public void setFeedbackDauer(float feedbackDauer) {
        this.feedbackDauer = feedbackDauer;
    }
}