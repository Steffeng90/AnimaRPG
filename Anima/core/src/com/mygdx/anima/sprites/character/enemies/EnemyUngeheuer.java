package com.mygdx.anima.sprites.character.enemies;

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
import com.mygdx.anima.sprites.character.UngeheuerSprites;
import com.mygdx.anima.sprites.character.zauber.fixtures.Blitz;
import com.mygdx.anima.sprites.character.zauber.fixtures.HeilzauberAOE;
import com.mygdx.anima.sprites.character.zauber.fixtures.Nova;
import com.mygdx.anima.sprites.character.zauber.fixtures.ZauberFixture;
import com.mygdx.anima.tools.SchadenBerechner;

import static com.mygdx.anima.AnimaRPG.HERO_ARROW;
import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 13.11.2016.
 */

public abstract class EnemyUngeheuer extends UngeheuerSprites implements Pool.Poolable{

    public boolean
            // enemyInReichweite, wird true, wenn Held den ENEMY_ATTACK_SENSOR betritt
            enemyInReichweite,vonFeedbackbetroffen;

    public int erfahrung;
    public String quelle;
    public EnemyUngeheuer()
    {
    super();
    }
    public void init(Playscreen screen,float x, float y,String id,int maxhp,int maxmana,int regMana,int ep,int speed,int schadenNah,int schadenfern,int schadenzauber,int ruestung,float boundsX,float boundsY,float castSpeed,float bowSpeed,float meleeSpeed,float thrustSpeed,boolean isFlying){
        super.init(screen.getGame(),screen,maxhp,maxmana,regMana,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
        setPosition(x,y);
        setErfahrung(ep);
        quelle=id;
        create(isFlying);
        velocity=new Vector2(0.2f,0.2f);
        //b2body.setActive(false);
        enemyInReichweite=false;
        vonFeedbackbetroffen=false;

        // animationenErstellen(getSchadenNah());

    }
    public void reset(){
        super.reset();
    }
    public void create(boolean isFlying){
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
        if(isFlying==true){
            fdef.filter.categoryBits=AnimaRPG.FLYING_UNGEHEUER_BIT;
            fdef.filter.maskBits= AnimaRPG.HERO_BIT | AnimaRPG.HERO_WEAPON_BIT | AnimaRPG.ENEMY_BIT | AnimaRPG.HERO_CAST_BIT
                    | AnimaRPG.HERO_ARROW;
        }
        else{
            fdef.filter.categoryBits=AnimaRPG.UNGEHEUER_BIT;
            fdef.filter.maskBits= AnimaRPG.GEBIETSWECHSEL_BIT | AnimaRPG.BARRIERE_BIT | AnimaRPG.HERO_BIT | AnimaRPG.HERO_WEAPON_BIT | AnimaRPG.OBJECT_BIT | AnimaRPG.ENEMY_BIT | AnimaRPG.HERO_CAST_BIT
                    | AnimaRPG.HERO_ARROW;
        }
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(Held hero, float dt){
        super.update(dt);
        if(enemyInReichweite && !runMeleeAnimation &&!destroyed){
            attack();

        }
        if(!runMeleeAnimation && meleeExists && b2body!=null){
            b2body.destroyFixture(meleeFixture);
            meleeExists=false;triggerFixture=true;}
        setRegion(getFrame(dt));
    }
    public void readyToDie(){super.readyToDie();
        giveErfahrung();
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
        circleShape.setPosition(richtungsVector);
        FixtureDef fdefAttack = new FixtureDef();
        fdefAttack.filter.categoryBits = AnimaRPG.UNGEHEUER_ATTACK_BIT;
        fdefAttack.filter.maskBits = AnimaRPG.HERO_BIT;
        fdefAttack.shape = circleShape;
        fdefAttack.isSensor = true;
        meleeFixture = this.b2body.createFixture(fdefAttack);
        meleeFixture.setUserData(this);
        runMeleeAnimation = true;
        meleeExists = true;
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
            if (tempHeld.getb2bodyX() < getb2bodyX()) {
                // Runter
                if (tempHeld.getb2bodyY() < getb2bodyY()) {changeVelocity(new Vector2(z.rueckstoss, z.rueckstoss));}
                // Hoch
                else {changeVelocity(new Vector2(z.rueckstoss, -z.rueckstoss));}
            }
            // Rechts
            else {// Runter
                if (tempHeld.getb2bodyY() < getb2bodyY()) {changeVelocity(new Vector2(-z.rueckstoss, z.rueckstoss));}
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
            if (tempHeld.getb2bodyX() < getb2bodyX()) {
                // Runter
                if (tempHeld.getb2bodyY() < getb2bodyY()) {changeVelocity(new Vector2(z.rueckstoss, z.rueckstoss));}
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
        if (hero.getb2bodyX() < getb2bodyX()) {
            // Runter
            if (hero.getb2bodyY() < getb2bodyY()) {
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
            if (hero.getb2bodyY() < getb2bodyY()) {
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


