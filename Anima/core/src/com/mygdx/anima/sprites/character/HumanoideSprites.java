package com.mygdx.anima.sprites.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Arrow;

/**
 * Created by Steffen on 13.11.2016.
 */

public class HumanoideSprites extends Sprite{
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
    public Animation UpMelee1, DownMelee1, LeftMelee1, RightMelee1,
            UpMelee2, DownMelee2, LeftMelee2, RightMelee2,
            UpMelee3, DownMelee3, LeftMelee3, RightMelee3;
    public Animation UpBow1, DownBow1, LeftBow1, RightBow1,
            UpBow2, DownBow2, LeftBow2, RightBow2;
    public Animation UpCast1, DownCast1, LeftCast1, RightCast1,
            UpCast2, DownCast2, LeftCast2, RightCast2,
            UpCast3, DownCast3, LeftCast3, RightCast3,
            UpCast4, DownCast4, LeftCast4, RightCast4;

    public Animation Dying;
    public TextureRegion Died;
    public TextureRegion standingDownSprite, standingUpSprite,
            standingLeftSprite, standingRightSprite;
    public Texture spriteQuelle;
    public Vector2 velocity;
    //Texture Atlas und QuellenTextureRegion
    public TextureAtlas atlas;
    TextureRegion heroWalkRegion,heroBow1Region,heroBow2Region,heroCastRegion,heroDieRegion,heroSword1Region,heroSword2Region,heroSword3Region;
    //Fixturen und ihre Trigger
    //(Der FixtureTrigger wird genutzt,damit eine Fixture nur einmal erzeugt wird, wenn die Animation mehrals 80% durch ist
    public Fixture meleeFixture,castFixture,bowFixture,thrustfixtureErzeugen,sensorFixture;
    public boolean meleeFixtureErzeugen,castFixtureErzeugen,bowFixtureErzeugen,thrustFixtureErzeugen, triggerFixture;
    FixtureDef fdefAttack;
    CircleShape sensorCircleShape;
    public Vector2 arrowStartVector,arrowFlugVector;

    public boolean meleeExists, castExists;
    public boolean runMeleeAnimation,istHeld,runArchery,runCasting,hitByFeedback;
    public boolean runDying,dead,destroyed,resetAktiv;
    public float hitdauer,wertHeld=0.07f;

    //Einstellungen
    private int currentHitpoints,maxHitpoints,currentMana,maxMana,regMana,regHitpoints,
            schadenNah,schadenFern,schadenZauber,ruestung,zauberwiderstand,geschwindigkeitLaufen,angriffgeschwindigkeit;

    //BreiteEinstellungen, da man mit verschiedenen Waffen verschieden breit ist.
    public int breite,breite_oversize;
    public int hoehe;
    public static float framesCast=7,framesStich=8,framesSchwert=6,framesWalk=9,framesDie=6,frameArcher=13;
    public float castSpeed,bowSpeed,meleeSpeed,thrustSpeed;
    float regenerationTimer;
    //Konstruktor für Enemies
    public HumanoideSprites(){
    }
    public void init(Playscreen screen,int maxhp,int maxmana,int regMana,int speed,int schadenNah,int schadenfern,int schadenzauber,int ruestung,float boundsX,float boundsY,float castSpeed,float bowSpeed,float meleeSpeed,float thrustSpeed){
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
        destroyed=false;
        breite=64;
        hoehe=64;
        triggerFixture=true;
        setBounds(0, 0, boundsX / AnimaRPG.PPM, boundsY/ AnimaRPG.PPM);
    }
    //Konstruktor für Held
    public HumanoideSprites(Playscreen screen, String quelle,Boolean istHeld) {
        this.world = screen.getWorld();
        this.istHeld=istHeld;
        this.bowSpeed=1f;
        this.meleeSpeed=0.5f;
        this.castSpeed=2f;
        this.thrustSpeed=2;
        angriffgeschwindigkeit=10;
        changeGrafiken(quelle);
        currentState = State.STANDING;
        previousState = State.STANDING;
        currentRichtung = Richtung.Unten;
        stateTimer = 0;
        runMeleeAnimation=false;
        triggerFixture=true;
        runDying=false;
        meleeExists=false;
        breite=64;breite_oversize=192;
        hoehe=64;
            //hitdauer=wertHeld;
        hitdauer=1;

        //Animationen erstellen
            updateTextures(quelle,angriffgeschwindigkeit,getGeschwindigkeitLaufen(),0,0,0,0);
            setBounds(0, 0, 42 / AnimaRPG.PPM, 42/ AnimaRPG.PPM);

        setRegion(standingDownSprite);
            }
        //Spritebreite


    public void updateTextures(String quelle,int attackSpeed,int laufSpeed,float castSpeed1,float castSpeed2, float castSpeed3,float castSpeed4){
        changeGrafiken(quelle);
        float speedAttack=(0.8f-(float)attackSpeed/100f);
        float speedLaufen=(1-(float)laufSpeed/100f);
        //spriteQuelle = new Texture(quelle);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < framesWalk; i++) {
            frames.add(new TextureRegion(heroWalkRegion, i*breite,0, breite, hoehe));
        }
        UpWalk = new Animation(speedLaufen/framesWalk, frames);
        frames.clear();
        for (int i = 0; i < framesWalk; i++) {
            frames.add(new TextureRegion(heroWalkRegion, i *breite, 64, breite, hoehe));
        }
        LeftWalk = new Animation(speedLaufen/framesWalk, frames);
        frames.clear();
        for (int i = 0; i < framesWalk; i++) {
            frames.add(new TextureRegion(heroWalkRegion,i *breite, 128, breite, hoehe));
        }
        DownWalk = new Animation(speedLaufen/framesWalk, frames);
        frames.clear();
        for (int i = 0; i < framesWalk; i++) {
            frames.add(new TextureRegion(heroWalkRegion, i *breite, 192, breite, hoehe));
        }
        RightWalk = new Animation(speedLaufen/framesWalk, frames);
        frames.clear();
        for (int i = 0; i < framesSchwert; i++) {
            frames.add(new TextureRegion(heroSword1Region,i *breite, 0, breite, hoehe));
        }
        UpMelee1 = new Animation(speedAttack/framesSchwert, frames);
        frames.clear();
        for (int i = 0; i < framesSchwert; i++) {
            frames.add(new TextureRegion(heroSword1Region,i *breite, 64, breite, hoehe));
        }
        LeftMelee1= new Animation(speedAttack/framesSchwert, frames);
        frames.clear();
        for (int i = 0; i < framesSchwert; i++) {
            frames.add(new TextureRegion(heroSword1Region,i *breite, 128, breite, hoehe));
        }
        DownMelee1 = new Animation(speedAttack/framesSchwert, frames);
        frames.clear();
        for (int i = 0; i < framesSchwert; i++) {
            frames.add(new TextureRegion(heroSword1Region, i *breite, 192, breite, hoehe));
        }
        RightMelee1 = new Animation(speedAttack/framesSchwert, frames);
        frames.clear();
        for (int i = 0; i < framesSchwert; i++) {
            frames.add(new TextureRegion(heroSword2Region,i *breite_oversize, 0, breite_oversize, breite_oversize));
        }
        UpMelee2 = new Animation(speedAttack/framesSchwert, frames);
        frames.clear();
        for (int i = 0; i < framesSchwert; i++) {
            frames.add(new TextureRegion(heroSword2Region,i *breite_oversize, breite_oversize, breite_oversize, breite_oversize));
        }
        LeftMelee2= new Animation(speedAttack/framesSchwert, frames);
        frames.clear();
        for (int i = 0; i < framesSchwert; i++) {
            frames.add(new TextureRegion(heroSword2Region,i *breite_oversize, breite_oversize*2, breite_oversize, breite_oversize));
        }
        DownMelee2 = new Animation(speedAttack/framesSchwert, frames);
        frames.clear();
        for (int i = 0; i < framesSchwert; i++) {
            frames.add(new TextureRegion(heroSword2Region, i *breite_oversize, breite_oversize*3, breite_oversize, breite_oversize));
        }
        RightMelee2 = new Animation(speedAttack/framesSchwert, frames);
        frames.clear();
        for (int i = 0; i < framesSchwert; i++) {
            frames.add(new TextureRegion(heroSword3Region,i *breite_oversize, 0, breite_oversize, breite_oversize));
        }
        UpMelee3 = new Animation(speedAttack/framesSchwert, frames);
        frames.clear();
        for (int i = 0; i < framesSchwert; i++) {
            frames.add(new TextureRegion(heroSword3Region,i *breite_oversize, breite_oversize, breite_oversize, breite_oversize));
        }
        LeftMelee3= new Animation(speedAttack/framesSchwert, frames);
        frames.clear();
        for (int i = 0; i < framesSchwert; i++) {
            frames.add(new TextureRegion(heroSword3Region,i *breite_oversize, breite_oversize*2, breite_oversize, breite_oversize));
        }
        DownMelee3 = new Animation(speedAttack/framesSchwert, frames);
        frames.clear();
        for (int i = 0; i < framesSchwert; i++) {
            frames.add(new TextureRegion(heroSword3Region, i *breite_oversize, breite_oversize*3, breite_oversize, breite_oversize));
        }
        RightMelee3 = new Animation(speedAttack/framesSchwert, frames);
        frames.clear();
        for (int i = 0; i < frameArcher; i++) {
            frames.add(new TextureRegion(heroBow1Region, i *breite, 0, breite, hoehe));
        }
        UpBow1 = new Animation(speedAttack/frameArcher, frames);
        frames.clear();
        for (int i = 0; i < frameArcher; i++) {
            frames.add(new TextureRegion(heroBow1Region, i *breite, 64, breite, hoehe));
        }
        LeftBow1 = new Animation(speedAttack/frameArcher, frames);
        frames.clear();
        for (int i = 0; i < frameArcher; i++) {
            frames.add(new TextureRegion(heroBow1Region, i *breite,128, breite, hoehe));
        }
        DownBow1 = new Animation(speedAttack/frameArcher, frames);
        frames.clear();
        for (int i = 0; i < frameArcher; i++) {
            frames.add(new TextureRegion(heroBow1Region, i *breite,192, breite, hoehe));
        }
        RightBow1 = new Animation(speedAttack/frameArcher, frames);
        frames.clear();
        for (int i = 0; i < frameArcher; i++) {
            frames.add(new TextureRegion(heroBow2Region, i *breite, 0, breite, hoehe));
        }
        UpBow2 = new Animation(speedAttack/frameArcher, frames);
        frames.clear();
        for (int i = 0; i < frameArcher; i++) {
            frames.add(new TextureRegion(heroBow2Region, i *breite, 64, breite, hoehe));
        }
        LeftBow2 = new Animation(speedAttack/frameArcher, frames);
        frames.clear();
        for (int i = 0; i < frameArcher; i++) {
            frames.add(new TextureRegion(heroBow2Region, i *breite,128, breite, hoehe));
        }
        DownBow2 = new Animation(speedAttack/frameArcher, frames);
        frames.clear();
        for (int i = 0; i < frameArcher; i++) {
            frames.add(new TextureRegion(heroBow2Region, i *breite,192, breite, hoehe));
        }
        RightBow2 = new Animation(speedAttack/frameArcher, frames);
        frames.clear();

        for (int i = 0; i < framesCast; i++) {
            frames.add(new TextureRegion(heroCastRegion, i *breite, 0, breite, hoehe));
        }
        UpCast1 = new Animation(castSpeed1/framesCast, frames);
        UpCast2 = new Animation(castSpeed2/framesCast, frames);
        UpCast3 = new Animation(castSpeed3/framesCast, frames);
        UpCast4 = new Animation(castSpeed4/framesCast, frames);
        frames.clear();
        for (int i = 0; i < framesCast; i++) {
            frames.add(new TextureRegion(heroCastRegion, i *breite,64, breite, hoehe));
        }
        LeftCast1 = new Animation(castSpeed1/framesCast, frames);
        LeftCast2 = new Animation(castSpeed2/framesCast, frames);
        LeftCast3 = new Animation(castSpeed3/framesCast, frames);
        LeftCast4 = new Animation(castSpeed4/framesCast, frames);
        frames.clear();
        for (int i = 0; i < framesCast; i++) {
            frames.add(new TextureRegion(heroCastRegion, i *breite, 128, breite, hoehe));
        }
        DownCast1 = new Animation(castSpeed1/framesCast, frames);
        DownCast2 = new Animation(castSpeed2/framesCast, frames);
        DownCast3 = new Animation(castSpeed3/framesCast, frames);
        DownCast4 = new Animation(castSpeed4/framesCast, frames);
        frames.clear();
        for (int i = 0; i < framesCast; i++) {
            frames.add(new TextureRegion(heroCastRegion, i *breite, 192, breite, hoehe));
        }
        RightCast1 = new Animation(castSpeed1/framesCast, frames);
        RightCast2 = new Animation(castSpeed2/framesCast, frames);
        RightCast3 = new Animation(castSpeed3/framesCast, frames);
        RightCast4 = new Animation(castSpeed4/framesCast, frames);
        frames.clear();

        for (int i = 0; i < framesDie; i++) {
            frames.add(new TextureRegion(heroDieRegion, i *breite, 0,breite,hoehe));
        }
        Dying=new Animation(0.2f,frames);
        frames.clear();
        Died=new TextureRegion(heroDieRegion,320,0,breite,hoehe);
        standingUpSprite = new TextureRegion(heroWalkRegion, 0, 0, breite, hoehe);
        standingLeftSprite = new TextureRegion(heroWalkRegion, 0, 64, breite, hoehe);
        standingDownSprite = new TextureRegion(heroWalkRegion, 0, 128, breite, hoehe);
        standingRightSprite = new TextureRegion(heroWalkRegion, 0,192, breite, hoehe);
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
                        if(this instanceof Held){
                            switch(((Held)this).getHeldenInventar().getAngelegtWaffeNah().getAnimationsStufe()){
                                default:
                                    Gdx.app.log("Defaulttrigger",""+((Held)this).getHeldenInventar().getAngelegtWaffeNah().getAnimationsStufe());
                                case 1:
                                    region = LeftMelee1.getKeyFrame(stateTimer, true);
                                    if(triggerFixture && stateTimer>=LeftMelee1.getAnimationDuration()*0.3)
                                    {meleeFixtureErzeugen=true;triggerFixture =false;}
                                    if(LeftMelee1.isAnimationFinished(stateTimer))
                                    {
                                        stateTimer=0;runMeleeAnimation=false;
                                    }
                                    break;
                                case 2:
                                    this.setBounds(this.b2body.getPosition().x-62/ AnimaRPG.PPM, this.b2body.getPosition().y-62/ AnimaRPG.PPM, 126 / AnimaRPG.PPM, 126/ AnimaRPG.PPM);
                                    region = LeftMelee2.getKeyFrame(stateTimer, true);
                                    if(triggerFixture && stateTimer>=LeftMelee2.getAnimationDuration()*0.3)
                                    {meleeFixtureErzeugen=true;triggerFixture =false;}
                                    if(LeftMelee2.isAnimationFinished(stateTimer))
                                    {this.setBounds(0, 0, 42 / AnimaRPG.PPM, 42/ AnimaRPG.PPM);
                                        stateTimer=0;runMeleeAnimation=false;

                                    }
                                    break;
                                case 3:
                                    this.setBounds(this.b2body.getPosition().x-62/ AnimaRPG.PPM, this.b2body.getPosition().y-62/ AnimaRPG.PPM, 126 / AnimaRPG.PPM, 126/ AnimaRPG.PPM);
                                    region = LeftMelee3.getKeyFrame(stateTimer, true);
                                    if(triggerFixture && stateTimer>=LeftMelee3.getAnimationDuration()*0.3)
                                    {meleeFixtureErzeugen=true;triggerFixture =false;}
                                    if(LeftMelee3.isAnimationFinished(stateTimer))
                                    { this.setBounds(0, 0, 42 / AnimaRPG.PPM, 42/ AnimaRPG.PPM);
                                        stateTimer=0;runMeleeAnimation=false;
                                    }
                                    break;
                            }

                        }
                        else {
                            region = LeftMelee1.getKeyFrame(stateTimer, true);
                            if (triggerFixture && stateTimer >= LeftMelee1.getAnimationDuration() * 0.3) {
                                meleeFixtureErzeugen = true;
                                triggerFixture = false;
                            }
                            if (LeftMelee1.isAnimationFinished(stateTimer)) {
                                stateTimer = 0;
                                runMeleeAnimation = false;
                            }
                        }
                        break;
                    case Rechts:
                        if(this instanceof Held){
                            switch(((Held)this).getHeldenInventar().getAngelegtWaffeNah().getAnimationsStufe()){
                                default:
                                    Gdx.app.log("Defaulttrigger",""+((Held)this).getHeldenInventar().getAngelegtWaffeNah().getAnimationsStufe());
                                case 1:
                                    region = RightMelee1.getKeyFrame(stateTimer, true);
                                    if(triggerFixture && stateTimer>=RightMelee1.getAnimationDuration()*0.3)
                                    {meleeFixtureErzeugen=true;triggerFixture =false;}
                                    if(RightMelee1.isAnimationFinished(stateTimer))
                                    {
                                        stateTimer=0;runMeleeAnimation=false;
                                    }
                                    break;
                                case 2:
                                    this.setBounds(this.b2body.getPosition().x-62/ AnimaRPG.PPM, this.b2body.getPosition().y-62/ AnimaRPG.PPM, 126 / AnimaRPG.PPM, 126/ AnimaRPG.PPM);
                                    region = RightMelee2.getKeyFrame(stateTimer, true);
                                    if(triggerFixture && stateTimer>=RightMelee2.getAnimationDuration()*0.3)
                                    {meleeFixtureErzeugen=true;triggerFixture =false;}
                                    if(RightMelee2.isAnimationFinished(stateTimer))
                                    {this.setBounds(0, 0, 42 / AnimaRPG.PPM, 42/ AnimaRPG.PPM);
                                        stateTimer=0;runMeleeAnimation=false;
                                    }
                                    break;
                                case 3:
                                    this.setBounds(this.b2body.getPosition().x-62/ AnimaRPG.PPM, this.b2body.getPosition().y-62/ AnimaRPG.PPM, 126 / AnimaRPG.PPM, 126/ AnimaRPG.PPM);
                                    region = RightMelee3.getKeyFrame(stateTimer, true);
                                    if(triggerFixture && stateTimer>=RightMelee3.getAnimationDuration()*0.3)
                                    {meleeFixtureErzeugen=true;triggerFixture =false;}
                                    if(RightMelee3.isAnimationFinished(stateTimer))
                                    {
                                        this.setBounds(0, 0, 42 / AnimaRPG.PPM, 42/ AnimaRPG.PPM);
                                        stateTimer=0;runMeleeAnimation=false;
                                    }
                                    break;
                            }
                        }
                        else {
                            region = RightMelee1.getKeyFrame(stateTimer, true);
                            if (triggerFixture && stateTimer >= RightMelee1.getAnimationDuration() * 0.3) {
                                meleeFixtureErzeugen = true;
                                triggerFixture = false;
                            }
                            if (RightMelee1.isAnimationFinished(stateTimer)) {
                                stateTimer = 0;
                                runMeleeAnimation = false;
                            }
                        }
                        break;
                    case Oben:
                        if(this instanceof Held){
                            switch(((Held)this).getHeldenInventar().getAngelegtWaffeNah().getAnimationsStufe()){
                                default:
                                    Gdx.app.log("Defaulttrigger",""+((Held)this).getHeldenInventar().getAngelegtWaffeNah().getAnimationsStufe());
                                case 1:
                                    region = UpMelee1.getKeyFrame(stateTimer, true);
                                    if(triggerFixture && stateTimer>=UpMelee1.getAnimationDuration()*0.3)
                                    {meleeFixtureErzeugen=true;triggerFixture =false;}
                                    if(UpMelee1.isAnimationFinished(stateTimer))
                                    {
                                        stateTimer=0;runMeleeAnimation=false;
                                    }
                                    break;
                                case 2:
                                    this.setBounds(this.b2body.getPosition().x-62/ AnimaRPG.PPM, this.b2body.getPosition().y-62/ AnimaRPG.PPM, 126 / AnimaRPG.PPM, 126/ AnimaRPG.PPM);
                                    region = UpMelee2.getKeyFrame(stateTimer, true);
                                    if(triggerFixture && stateTimer>=UpMelee2.getAnimationDuration()*0.3)
                                    {meleeFixtureErzeugen=true;triggerFixture =false;}
                                    if(UpMelee2.isAnimationFinished(stateTimer))
                                    {
                                        this.setBounds(0, 0, 42 / AnimaRPG.PPM, 42/ AnimaRPG.PPM);
                                        stateTimer=0;runMeleeAnimation=false;
                                    }
                                    break;
                                case 3:
                                    this.setBounds(this.b2body.getPosition().x-62/ AnimaRPG.PPM, this.b2body.getPosition().y-62/ AnimaRPG.PPM, 126 / AnimaRPG.PPM, 126/ AnimaRPG.PPM);
                                    region = UpMelee3.getKeyFrame(stateTimer, true);
                                    if(triggerFixture && stateTimer>=UpMelee3.getAnimationDuration()*0.3)
                                    {meleeFixtureErzeugen=true;triggerFixture =false;}
                                    if(UpMelee3.isAnimationFinished(stateTimer))
                                    {
                                        this.setBounds(0, 0, 42 / AnimaRPG.PPM, 42/ AnimaRPG.PPM);
                                        stateTimer=0;runMeleeAnimation=false;
                                    }
                                    break;
                            }

                        }
                        else {
                            region = UpMelee1.getKeyFrame(stateTimer, true);
                            if (triggerFixture && stateTimer >= UpMelee1.getAnimationDuration() * 0.3) {
                                meleeFixtureErzeugen = true;
                                triggerFixture = false;
                            }
                            if (UpMelee1.isAnimationFinished(stateTimer)) {
                                stateTimer = 0;
                                runMeleeAnimation = false;
                            }
                        }
                        break;
                    case Unten:
                        if(this instanceof Held){
                            switch(((Held)this).getHeldenInventar().getAngelegtWaffeNah().getAnimationsStufe()){
                                default:
                                    Gdx.app.log("Defaulttrigger",""+((Held)this).getHeldenInventar().getAngelegtWaffeNah().getAnimationsStufe());
                                case 1:
                                    region = DownMelee1.getKeyFrame(stateTimer, true);
                                    if(triggerFixture && stateTimer>=DownMelee1.getAnimationDuration()*0.3)
                                    {meleeFixtureErzeugen=true;triggerFixture =false;}
                                    if(DownMelee1.isAnimationFinished(stateTimer))
                                    {
                                        stateTimer=0;runMeleeAnimation=false;
                                    }
                                    break;
                                case 2:
                                    this.setBounds(this.b2body.getPosition().x-62/ AnimaRPG.PPM, this.b2body.getPosition().y-62/ AnimaRPG.PPM, 126 / AnimaRPG.PPM, 126/ AnimaRPG.PPM);
                                    region = DownMelee2.getKeyFrame(stateTimer, true);
                                    if(triggerFixture && stateTimer>=DownMelee2.getAnimationDuration()*0.3)
                                    {meleeFixtureErzeugen=true;triggerFixture =false;}
                                    if(DownMelee2.isAnimationFinished(stateTimer))
                                    {
                                        this.setBounds(0, 0, 42 / AnimaRPG.PPM, 42/ AnimaRPG.PPM);
                                        stateTimer=0;runMeleeAnimation=false;
                                    }
                                    break;
                                case 3:
                                    this.setBounds(this.b2body.getPosition().x-62/ AnimaRPG.PPM, this.b2body.getPosition().y-62/ AnimaRPG.PPM, 126 / AnimaRPG.PPM, 126/ AnimaRPG.PPM);
                                    region = DownMelee3.getKeyFrame(stateTimer, true);
                                    if(triggerFixture && stateTimer>=DownMelee3.getAnimationDuration()*0.3)
                                    {meleeFixtureErzeugen=true;triggerFixture =false;}
                                    if(DownMelee3.isAnimationFinished(stateTimer))
                                    {
                                        this.setBounds(0, 0, 42 / AnimaRPG.PPM, 42/ AnimaRPG.PPM);
                                        stateTimer=0;runMeleeAnimation=false;
                                    }
                                    break;
                            }
                        }
                        else {
                            region = DownMelee1.getKeyFrame(stateTimer, true);
                            if (triggerFixture && stateTimer >= DownMelee1.getAnimationDuration() * 0.3) {
                                meleeFixtureErzeugen = true;
                                triggerFixture = false;
                            }
                            if (DownMelee1.isAnimationFinished(stateTimer)) {
                                stateTimer = 0;
                                runMeleeAnimation = false;
                            }
                        }
                        break;
                    default:
                        region = standingDownSprite;
                        break;
                }
                break;
            case ARCHERY:
                switch (currentRichtung) {
                    case Links:
                        if(this instanceof Held){
                            if(((Held)this).getHeldenInventar().getAngelegtWaffeFern().getAnimationsStufe()==2){
                                region = LeftBow2.getKeyFrame(stateTimer, true);
                                if(triggerFixture && stateTimer>=LeftBow2.getAnimationDuration()*0.8)
                                {bowFixtureErzeugen=true;triggerFixture =false;            AnimaRPG.assetManager.get("audio/sounds/bow_attack.mp3", Sound.class).play(0.5f);
                                }
                                if(LeftBow2.isAnimationFinished(stateTimer))
                                {runArchery=false;triggerFixture=true;}
                            }
                            else{
                                region = LeftBow1.getKeyFrame(stateTimer, true);
                                if (triggerFixture && stateTimer >= LeftBow1.getAnimationDuration() * 0.8) {
                                    AnimaRPG.assetManager.get("audio/sounds/bow_attack.mp3", Sound.class).play(0.5f);
                                    bowFixtureErzeugen = true;
                                    triggerFixture = false;
                                }
                                if (LeftBow1.isAnimationFinished(stateTimer)) {
                                    runArchery = false;
                                    triggerFixture = true;
                                }
                            }
                        }
                        else {
                            region = LeftBow1.getKeyFrame(stateTimer, true);
                            if (triggerFixture && stateTimer >= LeftBow1.getAnimationDuration() * 0.8) {
                                AnimaRPG.assetManager.get("audio/sounds/bow_attack.mp3", Sound.class).play(0.5f);
                                bowFixtureErzeugen = true;
                                triggerFixture = false;
                            }
                            if (LeftBow1.isAnimationFinished(stateTimer)) {
                                runArchery = false;
                                triggerFixture = true;
                            }
                        }
                        break;
                    case Rechts:
                        if(this instanceof Held){
                            if(((Held)this).getHeldenInventar().getAngelegtWaffeFern().getAnimationsStufe()==2){
                                region = RightBow2.getKeyFrame(stateTimer, true);
                                if(triggerFixture && stateTimer>=RightBow2.getAnimationDuration()*0.8)
                                {            AnimaRPG.assetManager.get("audio/sounds/bow_attack.mp3", Sound.class).play(0.5f);

                                    bowFixtureErzeugen=true;triggerFixture =false;}
                                if(RightBow2.isAnimationFinished(stateTimer))
                                {runArchery=false;triggerFixture=true;}
                            }
                            else{
                                region = RightBow1.getKeyFrame(stateTimer, true);
                                if (triggerFixture && stateTimer >= RightBow1.getAnimationDuration() * 0.8) {
                                    AnimaRPG.assetManager.get("audio/sounds/bow_attack.mp3", Sound.class).play(0.5f);
                                    bowFixtureErzeugen = true;
                                    triggerFixture = false;
                                }
                                if (RightBow1.isAnimationFinished(stateTimer)) {
                                    runArchery = false;
                                    triggerFixture = true;
                                }
                            }
                        }
                        else {
                            region = RightBow1.getKeyFrame(stateTimer, true);
                            if (triggerFixture && stateTimer >= RightBow1.getAnimationDuration() * 0.8) {
                                AnimaRPG.assetManager.get("audio/sounds/bow_attack.mp3", Sound.class).play(0.5f);
                                bowFixtureErzeugen = true;
                                triggerFixture = false;
                            }
                            if (RightBow1.isAnimationFinished(stateTimer)) {
                                runArchery = false;
                                triggerFixture = true;
                            }
                        }
                        break;
                    case Oben:
                        if(this instanceof Held){
                            if(((Held)this).getHeldenInventar().getAngelegtWaffeFern().getAnimationsStufe()==2){
                                region = UpBow2.getKeyFrame(stateTimer, true);
                                if(triggerFixture && stateTimer>=UpBow2.getAnimationDuration()*0.8)
                                {            AnimaRPG.assetManager.get("audio/sounds/bow_attack.mp3", Sound.class).play(0.5f);
                                    bowFixtureErzeugen=true;triggerFixture =false;}
                                if(UpBow2.isAnimationFinished(stateTimer))
                                {runArchery=false;triggerFixture=true;}
                            }
                            else{
                                region = UpBow1.getKeyFrame(stateTimer, true);
                                if (triggerFixture && stateTimer >= UpBow1.getAnimationDuration() * 0.8) {
                                    AnimaRPG.assetManager.get("audio/sounds/bow_attack.mp3", Sound.class).play(0.5f);
                                    bowFixtureErzeugen = true;
                                    triggerFixture = false;
                                }
                                if (UpBow1.isAnimationFinished(stateTimer)) {
                                    runArchery = false;
                                    triggerFixture = true;
                                }
                            }
                        }
                        else {
                            region = UpBow1.getKeyFrame(stateTimer, true);
                            if (triggerFixture && stateTimer >= UpBow1.getAnimationDuration() * 0.8) {
                                AnimaRPG.assetManager.get("audio/sounds/bow_attack.mp3", Sound.class).play(0.5f);
                                bowFixtureErzeugen = true;
                                triggerFixture = false;
                            }
                            if (UpBow1.isAnimationFinished(stateTimer)) {
                                runArchery = false;
                                triggerFixture = true;
                            }
                        }
                        break;
                    case Unten:
                        if(this instanceof Held){
                            if(((Held)this).getHeldenInventar().getAngelegtWaffeFern().getAnimationsStufe()==2){
                                region = DownBow2.getKeyFrame(stateTimer, true);
                                if(triggerFixture && stateTimer>=DownBow2.getAnimationDuration()*0.8)
                                {            AnimaRPG.assetManager.get("audio/sounds/bow_attack.mp3", Sound.class).play(0.5f);
                                    bowFixtureErzeugen=true;triggerFixture =false;}
                                if(DownBow2.isAnimationFinished(stateTimer))
                                {runArchery=false;triggerFixture=true;}
                            }
                            else{
                                region = DownBow1.getKeyFrame(stateTimer, true);
                                if (triggerFixture && stateTimer >= DownBow1.getAnimationDuration() * 0.8) {
                                    AnimaRPG.assetManager.get("audio/sounds/bow_attack.mp3", Sound.class).play(0.5f);
                                    bowFixtureErzeugen = true;
                                    triggerFixture = false;
                                }
                                if (DownBow1.isAnimationFinished(stateTimer)) {
                                    runArchery = false;
                                    triggerFixture = true;
                                }
                            }
                        }
                        else {
                            region = DownBow1.getKeyFrame(stateTimer, true);
                            if (triggerFixture && stateTimer >= DownBow1.getAnimationDuration() * 0.8) {
                                AnimaRPG.assetManager.get("audio/sounds/bow_attack.mp3", Sound.class).play(0.5f);
                                bowFixtureErzeugen = true;
                                triggerFixture = false;
                            }
                            if (DownBow1.isAnimationFinished(stateTimer)) {
                                runArchery = false;
                                triggerFixture = true;
                            }
                        }
                        break;
                    default:
                        region = standingDownSprite;
                        break;
                }
                break;
            case CASTING:
                switch (currentRichtung) {
                    case Links:
                        if(this instanceof Held){
                            switch(((Held)this).getAktuellerZauberInt()){
                                default:
                                case 1: region = LeftCast1.getKeyFrame(stateTimer, true);
                                    if(LeftCast1.isAnimationFinished(stateTimer)){
                                        runCasting=false;}break;
                                case 2: region = LeftCast2.getKeyFrame(stateTimer, true);
                                    if(LeftCast2.isAnimationFinished(stateTimer)){
                                        runCasting=false;}break;
                                case 3: region = LeftCast3.getKeyFrame(stateTimer, true);
                                    if(LeftCast3.isAnimationFinished(stateTimer)){
                                        runCasting=false;}break;
                                case 4: region = LeftCast4.getKeyFrame(stateTimer, true);
                                    if(LeftCast4.isAnimationFinished(stateTimer)){
                                        runCasting=false;}break;
                            }
                        }else{
                            region = LeftCast1.getKeyFrame(stateTimer, true);
                            if(LeftCast1.isAnimationFinished(stateTimer)){
                                runCasting=false;}
                        }
                        break;
                    case Rechts:
                        if(this instanceof Held){
                            switch(((Held)this).getAktuellerZauberInt()){
                                default:
                                case 1: region = RightCast1.getKeyFrame(stateTimer, true);
                                    if(RightCast1.isAnimationFinished(stateTimer)){
                                        runCasting=false;}break;
                                case 2: region = RightCast2.getKeyFrame(stateTimer, true);
                                    if(RightCast2.isAnimationFinished(stateTimer)){
                                        runCasting=false;}break;
                                case 3: region = RightCast3.getKeyFrame(stateTimer, true);
                                    if(RightCast3.isAnimationFinished(stateTimer)){
                                        runCasting=false;}break;
                                case 4: region = RightCast4.getKeyFrame(stateTimer, true);
                                    if(RightCast4.isAnimationFinished(stateTimer)){
                                        runCasting=false;}break;
                            }
                        }else{
                            region = RightCast1.getKeyFrame(stateTimer, true);
                            if(RightCast1.isAnimationFinished(stateTimer)){
                                runCasting=false;}
                        }
                        break;
                    case Oben:
                        if(this instanceof Held){
                            switch(((Held)this).getAktuellerZauberInt()){
                                default:
                                case 1: region = UpCast1.getKeyFrame(stateTimer, true);
                                    if(UpCast1.isAnimationFinished(stateTimer)){
                                        runCasting=false;}break;
                                case 2: region = UpCast2.getKeyFrame(stateTimer, true);
                                    if(UpCast2.isAnimationFinished(stateTimer)){
                                        runCasting=false;}break;
                                case 3: region = UpCast3.getKeyFrame(stateTimer, true);
                                    if(UpCast3.isAnimationFinished(stateTimer)){
                                        runCasting=false;}break;
                                case 4: region = UpCast4.getKeyFrame(stateTimer, true);
                                    if(UpCast4.isAnimationFinished(stateTimer)){
                                        runCasting=false;}break;
                            }
                        }else{
                            region = UpCast1.getKeyFrame(stateTimer, true);
                            if(UpCast1.isAnimationFinished(stateTimer)){
                                runCasting=false;}
                        }
                        break;
                    case Unten:
                        if(this instanceof Held){
                            switch(((Held)this).getAktuellerZauberInt()){
                                default:
                                case 1: region = DownCast1.getKeyFrame(stateTimer, true);
                                    if(DownCast1.isAnimationFinished(stateTimer)){
                                        runCasting=false;}break;
                                case 2: region = DownCast2.getKeyFrame(stateTimer, true);
                                    if(DownCast2.isAnimationFinished(stateTimer)){
                                        runCasting=false;}break;
                                case 3: region = DownCast3.getKeyFrame(stateTimer, true);
                                    if(DownCast3.isAnimationFinished(stateTimer)){
                                        runCasting=false;}break;
                                case 4: region = DownCast4.getKeyFrame(stateTimer, true);
                                    if(DownCast4.isAnimationFinished(stateTimer)){
                                        runCasting=false;}break;
                            }
                        }else{
                            region = DownCast1.getKeyFrame(stateTimer, true);
                            if(DownCast1.isAnimationFinished(stateTimer)){
                                runCasting=false;}
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
            {return State.STANDING;}
        else
            return State.WALKING;
    }

    public void destroyBody(){
        world.destroyBody(b2body);
        b2body.setUserData(null);
        //b2body=null;
        runDying=false;
        destroyed=true;
    }
    public void readyToDie(){
        for(Fixture fix:b2body.getFixtureList()){
            Filter filter=fix.getFilterData();
            filter.categoryBits=AnimaRPG.NOTHING_BIT;
            fix.setFilterData(filter);}
        b2body.setLinearVelocity(new Vector2(0,0));
        runDying=true;
    }
    // Diese Methode zerstört die B2bodys und alle Fixtures, ohne EP zu geben.
    // Grund dafür ist der Kartenwechsel
    public void bodyZerstoeren() {
    }

    public void update(float dt){

        if(b2body!=null){
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);}
        if(getCurrentRichtung()!=getPreviousRichtung() && !destroyed) {
            sensorAnpassen();
        }
        if(meleeFixtureErzeugen){meleeFixtureErzeugen=false;meleeFixtureErzeugen();}
        else if(bowFixtureErzeugen){bowFixtureErzeugen=false;

            //new Arrow(world,screen,currentRichtung,arrowStartVector,arrowFlugVector,this);
           Arrow arrow=Playscreen.arrowPool.obtain();
            arrow.init(world,screen,currentRichtung,arrowStartVector,arrowFlugVector,this);
            Playscreen.activeArrows.add(arrow);
        }
        if(!runMeleeAnimation && meleeExists && b2body!=null){
            b2body.destroyFixture(meleeFixture);
            meleeExists=false;triggerFixture=true;}
       //waffenNah else(bowFixtureErzeugen){
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
                    fdefAttack.filter.maskBits = AnimaRPG.ENEMY_BIT | AnimaRPG.ENEMY_OBERKOERPER;}
        else{       fdefAttack.filter.categoryBits = AnimaRPG.ENEMY_ATTACK;
                    fdefAttack.filter.maskBits = AnimaRPG.HERO_BIT | AnimaRPG.HERO_OBERKOERPER;}
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
        if(currentHitpoints<=0 && !dead ){
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
    public void changeGrafiken(String quelle){
        atlas = new TextureAtlas(quelle+".pack");
        heroDieRegion=atlas.findRegion("die");
        heroWalkRegion=atlas.findRegion("walk");
        heroCastRegion=atlas.findRegion("cast");
        heroBow1Region=atlas.findRegion("bow1");
        heroBow2Region=atlas.findRegion("bow2");
        heroSword1Region=atlas.findRegion("sword1");
        heroSword2Region=atlas.findRegion("sword2");
        heroSword3Region=atlas.findRegion("sword3");

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

    public int getGeschwindigkeitLaufen() {
        return geschwindigkeitLaufen;
    }

    public void setGeschwindigkeitLaufen(int geschwindigkeitLaufen) {
        this.geschwindigkeitLaufen = geschwindigkeitLaufen;
    }
    public int getZauberwiderstand() {
        return zauberwiderstand;
    }

    public void setZauberwiderstand(int zauberwiderstand) {
        this.zauberwiderstand = zauberwiderstand;
    }

    public int getAngriffgeschwindigkeit() {
        return angriffgeschwindigkeit;
    }

    public void setAngriffgeschwindigkeit(int angriffgeschwindigkeit) {
        this.angriffgeschwindigkeit = angriffgeschwindigkeit;
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
    public void reset(){
        resetAktiv=true;
        if(b2body!=null){for(Fixture fix:b2body.getFixtureList()){
            Filter filter=fix.getFilterData();
            filter.categoryBits=AnimaRPG.NOTHING_BIT;
            fix.setFilterData(filter);}}
        //world.destroyBody(b2body);
        //b2body.setUserData(null);
        b2body=null;
        runDying=false;
        destroyed=false;

        currentHitpoints=0;
        maxHitpoints=0;
        currentMana=0;
        maxMana=0;
        regMana=0;
        regHitpoints=0;
        schadenNah=0;
        schadenFern=0;
        schadenZauber=0;
        ruestung=0;
        zauberwiderstand=0;
        geschwindigkeitLaufen=0;
        angriffgeschwindigkeit=0;
        meleeExists=false; castExists=false;
        runMeleeAnimation=false;
        istHeld=false;
        runArchery=false;
        runCasting=false;
        hitByFeedback=false;
        runDying=false;
        dead=false;
        destroyed=false;
    }
}