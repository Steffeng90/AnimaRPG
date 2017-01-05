package com.mygdx.anima.sprites.character.enemies.raider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.enemies.Enemy;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Arrow;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Nova;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Zauber;

/**
 * Created by Steffen on 13.11.2016.
 */

public class RaiderArcher extends Enemy
{
    Playscreen screen;
    PolygonShape bowSensor;

    public RaiderArcher(Playscreen screen,float x, float y,String id,int maxhp,int maxmana,int regMana,int ep,int speed,int schadenNah,int schadenfern,int schadenzauber,int ruestung,int boundsX,int boundsY,float castSpeed,float bowSpeed,float meleeSpeed,float thrustSpeed){
        super(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
        this.screen=screen;
    }
    @Override
    public void update(Held hero,float dt) {
        stateTimer +=dt;
       // setRegion(getFrame(dt));

        if(vonFeedbackbetroffen){
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            vonFeedbackbetroffen=false;
        }
        else if(!dead && !runDying && !runMeleeAnimation && !runArchery){
            coordinateWalking(hero, dt);
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        }
        else if(b2body!=null){ b2body.setLinearVelocity(new Vector2(0,0));}
        super.update(hero,dt);

    }
    public void coordinateWalking(Held hero, float dt){
        if(((getX()-hero.getX())<0.05f && (getX()-hero.getX())>-0.05f)|| ((getY()-hero.getY())<0.05f && (getY()-hero.getY())>-0.05f)) {
            velocity.x = 0;
            velocity.y = 0;
            //
            if(hero.getX()<getX() ){
                if(hero.getY()<getY()){
                    if((Math.abs(hero.getX()-getX())<=Math.abs(hero.getY()-getY()))){ setCurrentRichtung(3);}
                    else{ setCurrentRichtung(0);}
                }else{
                    if((Math.abs(hero.getX()-getX())<=Math.abs(hero.getY()-getY()))){ setCurrentRichtung(2);}
                    else{setCurrentRichtung(0);}
                }}
            else {
                if (hero.getY() < getY()) {
                    if((Math.abs(hero.getX()-getX())<=Math.abs(hero.getY()-getY()))){ setCurrentRichtung(3);}
                    else{setCurrentRichtung(1);}
                }else{
                    if((Math.abs(hero.getX()-getX())<=Math.abs(hero.getY()-getY()))){ setCurrentRichtung(2);}
                    else{setCurrentRichtung(1);}
                }}}
        //Östlich von Held
        else if(getX()>hero.getX()){
            //Nord-Osten
            if(getY()>hero.getY()){
                if(Math.abs(getX()- hero.getX())>Math.abs(getY()-hero.getY())){
                    walkingVelo(hero,new Vector2(0,-getGeschwindigkeitLaufen()));
                    setCurrentRichtung(3);
                }else{
                    walkingVelo(hero,new Vector2(-getGeschwindigkeitLaufen(),0));
                    setCurrentRichtung(0);
                }}
            // Süd-Osten
            else{
                if(Math.abs(getX()- hero.getX())>Math.abs(getY()-hero.getY())){
                    walkingVelo(hero,new Vector2(0,getGeschwindigkeitLaufen()));
                    setCurrentRichtung(2);
                }else{
                    walkingVelo(hero,new Vector2(-getGeschwindigkeitLaufen(),0));
                    setCurrentRichtung(0);
                }}}
        // Westlich von Held
        else{
            //Nord-Westen
            if(getY()>hero.getY()){
                if(Math.abs(getX()- hero.getX())>Math.abs(getY()-hero.getY())){
                    walkingVelo(hero,new Vector2(0,-getGeschwindigkeitLaufen()));
                    setCurrentRichtung(3);
                }else{
                    walkingVelo(hero,new Vector2(getGeschwindigkeitLaufen(),0));
                    setCurrentRichtung(1);
                }}
            // Süd-Westen
            else{
                if(Math.abs(getX()- hero.getX())>Math.abs(getY()-hero.getY())){
                    walkingVelo(hero,new Vector2(0,getGeschwindigkeitLaufen()));
                    setCurrentRichtung(2);
                }else{
                    walkingVelo(hero,new Vector2(getGeschwindigkeitLaufen(),0));
                    setCurrentRichtung(1);
                }}
        }
    }
    public void walkingVelo(Held hero,Vector2 v2){
        velocity.x=v2.x/10;
        velocity.y=v2.y/10;
    }
    @Override
    public void attack()
        {   if(currentState==State.STANDING |currentState==State.WALKING)
        {runArchery= true;
           // Vector2 startVector, flugVector;
            Vector2 koerper=b2body.getPosition();
            switch (getCurrentRichtung()) {
                //Hier sind bei Y immer schon mind. -5 Abzug, weil man es ein bisschen nach unten ziehen muss, um die Mitte der Bodentexture und nicht
                // die der Grafikmitte zu treffen
                case Rechts:
                    arrowStartVector = new Vector2(koerper.x+20 / AnimaRPG.PPM,koerper.y -8 / AnimaRPG.PPM);
                    arrowFlugVector = new Vector2(200 / AnimaRPG.PPM, 0);break;
                case Links:
                    arrowStartVector = new Vector2(koerper.x-20 / AnimaRPG.PPM,koerper.y-8 / AnimaRPG.PPM);
                    arrowFlugVector = new Vector2(-200 / AnimaRPG.PPM, 0);break;
                case Oben:
                    arrowStartVector = new Vector2(koerper.x,koerper.y +17 / AnimaRPG.PPM);
                    arrowFlugVector = new Vector2(0, 200 / AnimaRPG.PPM);break;
                case Unten:
                    arrowStartVector = new Vector2(koerper.x,koerper.y -33 / AnimaRPG.PPM);
                    arrowFlugVector = new Vector2(0, -200 / AnimaRPG.PPM);break;
                default:
                    arrowStartVector = new Vector2(0, 0);
                    arrowFlugVector = new Vector2(10, 10);break;
            }
           // new Arrow(world,screen,currentRichtung,arrowStartVector,arrowFlugVector,this);
        }}

    @Override
    public void createSensor(){
        bowSensor= new PolygonShape();
        bowSensor.setAsBox(10/AnimaRPG.PPM,200/AnimaRPG.PPM);
        Vector2 sensorRichtungsVector;
        FixtureDef fdefSensor = new FixtureDef();
            fdefSensor.filter.categoryBits = AnimaRPG.ENEMY_SENSOR;
            fdefSensor.filter.maskBits = AnimaRPG.HERO_BIT;
        fdefSensor.shape = bowSensor;
        fdefSensor.isSensor = true;
        sensorFixture= b2body.createFixture(fdefSensor);
        sensorFixture.setUserData(this);
        Gdx.app.log("Shaperstellen","");
    }
    @Override
    public void sensorAnpassen(){
        Gdx.app.log("Sensoranpassen","");

        b2body.destroyFixture(sensorFixture);
        switch (getCurrentRichtung()) {
            case Rechts:
            case Links:bowSensor.setAsBox(200/AnimaRPG.PPM,10/AnimaRPG.PPM);
                break;
            case Oben:
            case Unten:bowSensor.setAsBox(10/AnimaRPG.PPM,200/AnimaRPG.PPM);
                break;
            default:
                break;
        }
        FixtureDef fdefSensor = new FixtureDef();
            fdefSensor.filter.categoryBits = AnimaRPG.ENEMY_SENSOR;
            fdefSensor.filter.maskBits = AnimaRPG.HERO_BIT;

        fdefSensor.shape = bowSensor;
        fdefSensor.isSensor = true;
        sensorFixture= b2body.createFixture(fdefSensor);
        sensorFixture.setUserData(this);
    }
}