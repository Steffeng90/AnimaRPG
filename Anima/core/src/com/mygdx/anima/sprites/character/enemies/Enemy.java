package com.mygdx.anima.sprites.character.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.HumanoideSprites;
import com.mygdx.anima.sprites.character.zauber.fixtures.Blitz;
import com.mygdx.anima.sprites.character.zauber.fixtures.HeilzauberAOE;
import com.mygdx.anima.sprites.character.zauber.fixtures.Nova;
import com.mygdx.anima.sprites.character.zauber.fixtures.ZauberFixture;
import com.mygdx.anima.tools.SchadenBerechner;

import static com.mygdx.anima.AnimaRPG.ARROW_BIT;
import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 13.11.2016.
 */

public abstract class Enemy extends HumanoideSprites implements Pool.Poolable{

    public boolean
            // enemyInReichweite, wird true, wenn Held den ENEMY_ATTACK_SENSOR betritt
            enemyInReichweite,vonFeedbackbetroffen,
    // enemyNear, wird true, wenn Held den ENEMY_HEAL_SENSOR betritt
            enemyNear;
    public int erfahrung;
    public String quelle;
    public Enemy()
    {
    super();
    }
    public void init(Playscreen screen,float x, float y,String id,int maxhp,int maxmana,int regMana,int ep,int speed,int schadenNah,int schadenfern,int schadenzauber,int ruestung,float boundsX,float boundsY,float castSpeed,float bowSpeed,float meleeSpeed,float thrustSpeed){
        super.init(screen,maxhp,maxmana,regMana,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
        setPosition(x,y);
        setErfahrung(ep);
        quelle=id;
        create();
        velocity=new Vector2(0.2f,0.2f);
        //b2body.setActive(false);
        enemyInReichweite=false;
        vonFeedbackbetroffen=false;
        Gdx.app.log("","Text: "+id.substring(0,id.length()-2));
        atlas = new TextureAtlas(id.substring(0,id.length()-2)+".pack");
        animationenErstellen(getSchadenNah(),getSchadenFern(),getSchadenZauber(),false);
        setRegion(standingDownSprite);
    }
    public void reset(){
        super.reset();
    }
    public void create(){
        BodyDef bdef=new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type =BodyDef.BodyType.DynamicBody;
        b2body =world.createBody(bdef);
        createSensor();
        b2body.setActive(false);

        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(7f/AnimaRPG.PPM);
        shape.setPosition(new Vector2(0,-12/AnimaRPG.PPM));
        fdef.filter.categoryBits=AnimaRPG.ENEMY_BIT;
        fdef.filter.maskBits= AnimaRPG.GEBIETSWECHSEL_BIT | AnimaRPG.BARRIERE_BIT | AnimaRPG.HERO_BIT | AnimaRPG.HERO_WEAPON_BIT | AnimaRPG.OBJECT_BIT | AnimaRPG.ENEMY_BIT | AnimaRPG.HERO_CAST_BIT
        | AnimaRPG.ARROW_BIT | AnimaRPG.ENEMY_HEAL_SENSOR | AnimaRPG.ENEMY_CAST_HEAL;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        shape.setPosition(new Vector2(0,4.5f/AnimaRPG.PPM));
        fdef.filter.categoryBits=AnimaRPG.ENEMY_OBERKOERPER;
        fdef.filter.maskBits=AnimaRPG.HERO_WEAPON_BIT | ARROW_BIT;
        fdef.isSensor=true;
        fdef.shape=shape;
        b2body.createFixture(fdef).setUserData(this);;
    }

    public void update(Held hero, float dt){
        super.update(dt);
        /*if(!runMeleeAnimation && meleeExists){
            b2body.destroyFixture(meleeFixture);
            meleeExists=false;
        }
        else */
        if(enemyInReichweite && !runMeleeAnimation &&!destroyed){
            attack();
        }
        setRegion(getFrame(dt));
    }
    public void readyToDie(){super.readyToDie();
        giveErfahrung();
    }
    public void animationenErstellen(int melee,int bow,int cast,boolean thrust){
        // Die ersten Animationen werden erstellen, unabhängig der Übergebenen Booleans
        //TextureRegion spriteQuelle = new TextureRegion(atlas.findRegion(quelle+"-walk"), 0, 0, 16, 16);;
        TextureRegion walkQuelle=atlas.findRegion(quelle+"-walk"),
                dieQuelle=atlas.findRegion(quelle+"-die");

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < framesWalk; i++) {
            frames.add(new TextureRegion(walkQuelle, i*breite,0, breite, hoehe));
        }
        UpWalk = new Animation(0.1f, frames);
        frames.clear();
        for (int i = 0; i < framesWalk; i++) {
            frames.add(new TextureRegion(walkQuelle,i *breite, 128, breite, hoehe));
        }
        DownWalk = new Animation(0.1f, frames);
        frames.clear();
        for (int i = 0; i < framesWalk; i++) {
            frames.add(new TextureRegion(walkQuelle, i *breite, 64, breite, hoehe));
        }
        LeftWalk = new Animation(0.1f, frames);
        frames.clear();
        for (int i = 0; i < framesWalk; i++) {
            frames.add(new TextureRegion(walkQuelle, i *breite, 192, breite, hoehe));
        }
        RightWalk = new Animation(0.1f, frames);

        frames.clear();
        for (int i = 0; i < framesDie; i++) {
            frames.add(new TextureRegion(dieQuelle, i *breite, 0,breite,hoehe));
        }
        Dying=new Animation(0.2f,frames);
        frames.clear();

        // Hier folgt die Spezifikation anhand der Boolean, ob Melee oder nicht..
        if(melee>0) {
            TextureRegion meleeQuelle=atlas.findRegion(quelle+"-melee");
            for (int i = 0; i < framesSchwert; i++) {
                frames.add(new TextureRegion(meleeQuelle, i * breite, 0, breite, hoehe));
            }
            UpMelee1 = new Animation(meleeSpeed/framesSchwert, frames);
            frames.clear();
            for (int i = 0; i < framesSchwert; i++) {
                frames.add(new TextureRegion(meleeQuelle, i * breite, 128, breite, hoehe));
            }
            DownMelee1 = new Animation(meleeSpeed/framesSchwert, frames);
            frames.clear();
            for (int i = 0; i < framesSchwert; i++) {
                frames.add(new TextureRegion(meleeQuelle, i * breite, 64, breite, hoehe));
            }
            LeftMelee1 = new Animation(meleeSpeed/framesSchwert, frames);
            frames.clear();
            for (int i = 0; i < framesSchwert; i++) {
                frames.add(new TextureRegion(meleeQuelle, i * breite, 192, breite, hoehe));
            }
            RightMelee1 = new Animation(meleeSpeed/framesSchwert, frames);
            frames.clear();
        }
        if(bow>0) {
            TextureRegion bowQuelle=atlas.findRegion(quelle+"-bow");
            for (int i = 0; i < frameArcher; i++) {
                frames.add(new TextureRegion(bowQuelle, i * breite, 0, breite, hoehe));
            }
            UpBow1 = new Animation(bowSpeed/frameArcher, frames);
            frames.clear();
            for (int i = 0; i < frameArcher; i++) {
                frames.add(new TextureRegion(bowQuelle, i * breite, 128, breite, hoehe));
            }
            DownBow1 = new Animation(bowSpeed/frameArcher, frames);
            frames.clear();
            for (int i = 0; i < frameArcher; i++) {
                frames.add(new TextureRegion(bowQuelle, i * breite, 192, breite, hoehe));
            }
            RightBow1 = new Animation(bowSpeed/frameArcher, frames);
            frames.clear();
            for (int i = 0; i < frameArcher; i++) {
                frames.add(new TextureRegion(bowQuelle, i * breite, 64, breite, hoehe));
            }
            LeftBow1 = new Animation(bowSpeed/frameArcher, frames);
            frames.clear();
        }
        if(cast>0) {
            TextureRegion castQuelle=atlas.findRegion(quelle+"-cast");
            for (int i = 0; i < framesCast; i++) {
                frames.add(new TextureRegion(castQuelle, i * breite, 0, breite, hoehe));
            }
            UpCast1 = new Animation(castSpeed/framesCast, frames);
            frames.clear();
            for (int i = 0; i < framesCast; i++) {
                frames.add(new TextureRegion(castQuelle, i * breite, 128, breite, hoehe));
            }
            DownCast1 = new Animation(castSpeed/framesCast, frames);
            frames.clear();
            for (int i = 0; i < framesCast; i++) {
                frames.add(new TextureRegion(castQuelle, i * breite, 192, breite, hoehe));
            }
            RightCast1 = new Animation(castSpeed/framesCast, frames);
            frames.clear();
            for (int i = 0; i < framesCast; i++) {
                frames.add(new TextureRegion(castQuelle, i * breite, 64, breite, hoehe));
            }
            LeftCast1 = new Animation(castSpeed/framesCast, frames);
        }
        Died=new TextureRegion(dieQuelle,320,0,breite,hoehe);
        standingDownSprite = new TextureRegion(walkQuelle, 0, 128, breite, hoehe);
        standingUpSprite = new TextureRegion(walkQuelle, 0,0, breite, hoehe);
        standingLeftSprite = new TextureRegion(walkQuelle, 0, 64, breite, hoehe);
        standingRightSprite = new TextureRegion(walkQuelle, 0, 192, breite, hoehe);
        setRegion(standingDownSprite);

    }
    public void attack()
    {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(5 / AnimaRPG.PPM);
        Vector2 richtungsVector;

        switch (getCurrentRichtung()) {
            //Hier sind bei Y immer schon mind. -5 Abzug, weil man es ein bisschen nach unten ziehen muss, um die Mitte der Bodentexture und nicht
            // die der Grafikmitte zu treffen
            case Rechts:
                richtungsVector = new Vector2(15 / AnimaRPG.PPM, -8 / AnimaRPG.PPM);
                break;
            case Links:
                richtungsVector = new Vector2(-15 / AnimaRPG.PPM, -8 / AnimaRPG.PPM);
                break;
            case Oben:
                richtungsVector = new Vector2(0, 7 / AnimaRPG.PPM);
                break;
            case Unten:
                richtungsVector = new Vector2(0, -23 / AnimaRPG.PPM);
                break;
            default:
                richtungsVector = new Vector2(0, 0);
                break;

        }
        meleeFixtureDefinieren(richtungsVector);
/*        circleShape.setPosition(richtungsVector);
        FixtureDef fdefAttack = new FixtureDef();
        fdefAttack.filter.categoryBits = AnimaRPG.ENEMY_ATTACK;
        fdefAttack.filter.maskBits = AnimaRPG.HERO_BIT;
        fdefAttack.shape = circleShape;
        fdefAttack.isSensor = true;
        meleeFixture = this.b2body.createFixture(fdefAttack);
        meleeFixture.setUserData(this);
        runMeleeAnimation = true;
        meleeExists = true;*/
    }
    public void changeVelocity(Vector2 v2){

        velocity.x+=v2.x;
        velocity.y+=v2.y;
        /*if(velocity.x>=getGeschwindigkeitLaufen()/10){velocity.x=getGeschwindigkeitLaufen()/10;}
        else if(velocity.x<=-getGeschwindigkeitLaufen()/10){velocity.x=-getGeschwindigkeitLaufen()/10;}
        if(velocity.y>=getGeschwindigkeitLaufen()/10){velocity.y=getGeschwindigkeitLaufen()/10;}
        else if(velocity.y<=-getGeschwindigkeitLaufen()/10){velocity.y=-getGeschwindigkeitLaufen()/10;}*/
    }

    public void getsHitbySpell(ZauberFixture z) {
        if (z instanceof Nova) {
            vonFeedbackbetroffen = true;
            // setFeedbackDauer=z.getFeedbackDauer();
            hitByFeedback = true;
            Held tempHeld =(Held) z.zaubernder;
            getsDamaged(3);
            if (tempHeld.getX() < getX()) {
                // Runter
                if (tempHeld.getY() < getY()) {changeVelocity(new Vector2(z.rueckstoss, z.rueckstoss));}
                // Hoch
                else {changeVelocity(new Vector2(z.rueckstoss, -z.rueckstoss));}
            }
            // Rechts
            else {// Runter
                if (tempHeld.getY() < getY()) {changeVelocity(new Vector2(-z.rueckstoss, z.rueckstoss));}
                // Hoch
                else {changeVelocity(new Vector2(-z.rueckstoss, -z.rueckstoss));}
            }
        } else if (z instanceof HeilzauberAOE){
            getsHealed(z);        }
        else if(z instanceof Blitz){
            vonFeedbackbetroffen = true;
            // setFeedbackDauer=z.getFeedbackDauer();
            hitByFeedback = true;
            Held tempHeld =(Held) z.zaubernder;
            getsDamaged(3);
            if (tempHeld.getX() < getX()) {
                // Runter
                if (tempHeld.getY() < getY()) {changeVelocity(new Vector2(z.rueckstoss, z.rueckstoss));}
                // Hoch
                else {changeVelocity(new Vector2(z.rueckstoss, -z.rueckstoss));}
            }
        }
    }
    public void getsHitbyBow(){
        getsDamaged(2);
    }
    public void getsHitbySword(Held hero){
        vonFeedbackbetroffen = true;
        hitByFeedback = true;

        getsDamaged(1);
        //Links
        float feedback = 3f;
        if (hero.getX() < getX()) {
            // Runter
            if (hero.getY() < getY()) {
                changeVelocity(new Vector2(feedback, feedback));
            }
            // Hoch
            else {
                changeVelocity(new Vector2(feedback, -feedback));
            }
        }
        // Rechts
        else {
            // Runter
            if (hero.getY() < getY()) {
                changeVelocity(new Vector2(-feedback, feedback));
            }
            // Hoch
            else {
                changeVelocity(new Vector2(-feedback, -feedback));
            }
        }
    }

    public void getsHitbyThrust(Held hero){
        //nochnicht definiert
    }

    public void getsDamaged(int schadensTyp){
        SchadenBerechner.berechneSchaden(schadensTyp,this,getHeld());
    }
    public void getsHealed(ZauberFixture z){
        SchadenBerechner.berechneSchaden(4,this,z.zaubernder);
    }

    public int getErfahrung() {
        return erfahrung;
    }

    public void setErfahrung(int erfahrung) {
        this.erfahrung = erfahrung;
    }


    public void giveErfahrung(){
        getHeld().setCurrentErfahrung(getHeld().getCurrentErfahrung()+this.getErfahrung());
    }
    public void heilen(){}

    public void draw(Batch batch) {
        if (!dead || stateTimer < 3)
            super.draw(batch);
    }
}


