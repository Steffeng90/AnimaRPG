package com.mygdx.anima.sprites.character.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.HumanoideSprites;
import com.mygdx.anima.sprites.character.SchadenLabel;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Zauber;
import com.mygdx.anima.tools.SchadenBerechner;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 13.11.2016.
 */

public abstract class Enemy extends HumanoideSprites{

    public boolean enemyInReichweite,vonFeedbackbetroffen;
    public int erfahrung;

    public Enemy(Playscreen screen,float x, float y, String quelle)
    {
        super(screen,quelle,false);
        setPosition(x,y);
        create();
        velocity=new Vector2(0.2f,0.2f);
        b2body.setActive(false);
        enemyInReichweite=false;
        vonFeedbackbetroffen=false;

        setMaxHitpoints(5);
        setCurrentHitpoints(getMaxHitpoints());
        setMaxMana(15);
        setCurrentMana(getMaxMana());
        setRegMana(5);
        setErfahrung(100);
        setGeschwindigkeitLaufen(15);
        setSchadenNah(5);
        setSchadenFern(4);
        setSchadenZauber(15);
        setRuestung(4);

    }


    public void create(){
        BodyDef bdef=new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type =BodyDef.BodyType.DynamicBody;
        b2body =world.createBody(bdef);
        createSensor(false);
        b2body.setActive(false);

        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(7/AnimaRPG.PPM);
        shape.setPosition(new Vector2(0,-12/AnimaRPG.PPM));
        //PolygonShape shape=new PolygonShape();
        //shape.setAsBox(8/ AnimaRPG.PPM,8/AnimaRPG.PPM,new Vector2(0,-10/AnimaRPG.PPM),0);
        fdef.filter.categoryBits=AnimaRPG.ENEMY_BIT;
        fdef.filter.maskBits= AnimaRPG.GEBIETSWECHSEL_BIT | AnimaRPG.BARRIERE_BIT | AnimaRPG.HERO_BIT | AnimaRPG.HERO_WEAPON_BIT | AnimaRPG.OBJECT_BIT | AnimaRPG.ENEMY_BIT | AnimaRPG.HERO_CAST_BIT
        | AnimaRPG.ARROW_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(Held hero, float dt){
        if(!runMeleeAnimation && meleeExists){
            b2body.destroyFixture(meleeFixture);
            meleeExists=false;
        }
        else if(enemyInReichweite && !runMeleeAnimation &&!destroyed){
            attack();
        }
        super.update(dt);
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
        circleShape.setPosition(richtungsVector);
        FixtureDef fdefAttack = new FixtureDef();
        fdefAttack.filter.categoryBits = AnimaRPG.ENEMY_ATTACK;
        fdefAttack.filter.maskBits = AnimaRPG.HERO_BIT;
        fdefAttack.shape = circleShape;
        fdefAttack.isSensor = true;
        meleeFixture = this.b2body.createFixture(fdefAttack);
        meleeFixture.setUserData(this);
        runMeleeAnimation = true;
        meleeExists = true;
    }
    public abstract void getsHitbySpell(Zauber z);
    public abstract void getsHit();
    public abstract void getsHit(Held hero);

    public void getsDamaged(int schadensTyp){
        SchadenBerechner.berechneSchaden(schadensTyp,this,getHeld());
    }

    public int getErfahrung() {
        return erfahrung;
    }

    public void setErfahrung(int erfahrung) {
        this.erfahrung = erfahrung;
    }


    public void giveErfahrung(){
        Gdx.app.log("Erfahrung gegeben","");
        getHeld().setCurrentErfahrung(getHeld().getCurrentErfahrung()+this.getErfahrung());
    }

}


