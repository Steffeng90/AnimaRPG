package com.mygdx.anima.sprites.character.enemies.raider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.HumanoideSprites;
import com.mygdx.anima.sprites.character.enemies.Enemy;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Nova;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Zauber;

/**
 * Created by Steffen on 13.11.2016.
 */

public class Raider extends Enemy
{
    public RaiderHealer healer;
    float healSensorTimer=0;
    boolean healSensorActive;
    Fixture healSensorFixture;
    public Raider(Playscreen screen,float x, float y,String id,int maxhp,int maxmana,int regMana,int ep,int speed,int schadenNah,int schadenfern,int schadenzauber,int ruestung,int boundsX,int boundsY,float castSpeed,float bowSpeed,float meleeSpeed,float thrustSpeed){
        super(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
    }
    @Override
    public void update(Held hero,float dt) {
        stateTimer +=dt;
        super.update(hero,dt);

        if(vonFeedbackbetroffen){
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            vonFeedbackbetroffen=false;
        }
        else if(!dead && !runDying && !runMeleeAnimation){
            if(healer!=null){coordinateWalking(healer, dt);}
            else{coordinateWalking(hero, dt);}
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        }
        else if(b2body!=null){ b2body.setLinearVelocity(new Vector2(0,0));}
        if(healSensorActive==false && getCurrentHitpointsPercent()<0.5){
            createHealerSensor();
             b2body.setLinearVelocity(new Vector2(0,0));
        }
        else if(healSensorActive && b2body!=null){
            healSensorTimer+=dt;
            if(healSensorTimer>1){
                b2body.destroyFixture(healSensorFixture);
                healSensorTimer=0;
                healSensorActive=false;
            }
        }
    }

    public void coordinateWalking(HumanoideSprites hero, float dt){
        if(hero.getX()<getX() ){
            // Runter
            if(hero.getY()<getY()){

                walkingVelo(hero,new Vector2(-getGeschwindigkeitLaufen(),-getGeschwindigkeitLaufen()));
                if((Math.abs(hero.getX()-getX())<=Math.abs(hero.getY()-getY()))){ setCurrentRichtung(3);}
                else{ setCurrentRichtung(0);}
            }
            // Hoch
            else{
                walkingVelo(hero,new Vector2(-getGeschwindigkeitLaufen(),getGeschwindigkeitLaufen()));
                if((Math.abs(hero.getX()-getX())<=Math.abs(hero.getY()-getY()))){ setCurrentRichtung(2);}
                else{setCurrentRichtung(0);}
            }}
        // Rechts
        else {
            // Runter
            if (hero.getY() < getY()) {
                walkingVelo(hero,new Vector2(getGeschwindigkeitLaufen(),-getGeschwindigkeitLaufen()));
                if((Math.abs(hero.getX()-getX())<=Math.abs(hero.getY()-getY()))){ setCurrentRichtung(3);}
                else{setCurrentRichtung(1);}
            }
            // Hoch
            else{
                walkingVelo(hero,new Vector2(getGeschwindigkeitLaufen(),getGeschwindigkeitLaufen()));
                if((Math.abs(hero.getX()-getX())<=Math.abs(hero.getY()-getY()))){ setCurrentRichtung(2);}
                else{setCurrentRichtung(1);}
            }
        }
    }
    public void walkingVelo(HumanoideSprites hero,Vector2 v2){
        v2.x=v2.x/10;
        v2.y=v2.y/10;

        if((getX()-hero.getX())<0.05f && (getX()-hero.getX())>-0.05f){
            velocity.x=0;
        }else if((velocity.x>-0.3f && velocity.x<0.3f) || (velocity.x>=0.3f && v2.x<0) || (velocity.x<=-0.3f && v2.x>0))
            velocity.x+=v2.x;
        if((getY()-hero.getY())<0.05f && (getY()-hero.getY())>-0.05f){
            velocity.y=0;
        }else if((velocity.y>-0.3f &&velocity.y<0.3f) || (velocity.y>=0.3f && v2.y<0) || (velocity.y<=-0.3f && v2.y>0))
            velocity.y+=v2.y;
    }
    @Override
    public void getsHealed(Zauber z){
        super.getsHealed(z);
        healer=null;
    }
    public void createHealerSensor() {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(200 / AnimaRPG.PPM);

        FixtureDef fdefSensor = new FixtureDef();
        fdefSensor.filter.categoryBits = AnimaRPG.ENEMY_SEARCH_HEALER;
        fdefSensor.filter.maskBits = AnimaRPG.ENEMY_HEAL_SENSOR;
        fdefSensor.shape = circleShape;
        fdefSensor.isSensor = true;
        healSensorFixture = b2body.createFixture(fdefSensor);
        healSensorFixture.setUserData(this);
        healSensorActive=true;
    }
}
