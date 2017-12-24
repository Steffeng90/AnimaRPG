package com.mygdx.anima.sprites.character.enemies.raider;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.HumanoideSprites;
import com.mygdx.anima.sprites.character.enemies.EnemyHumanoid;
import com.mygdx.anima.sprites.character.zauber.fixtures.ZauberFixture;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 13.11.2016.
 */

public class RaiderBoss extends EnemyHumanoid
{
    public RaiderHealer healer;
    float healSensorTimer=0;
    boolean healSensorActive;
    Fixture healSensorFixture;
    int nachbed;
    public RaiderBoss(){
        super();
    }
    public void init(Playscreen screen,float x, float y,String id,int maxhp,int maxmana,int regMana,int ep,int speed,int schadenNah,int schadenfern,int schadenzauber,int schadenbigAttack,int ruestung,float boundsX,float boundsY,float castSpeed,float bowSpeed,float meleeSpeed,float thrustSpeed,int aktivierungsEvent,int nachbed){
        super.init(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,schadenbigAttack,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed, aktivierungsEvent);
        this.nachbed=nachbed;
    }
    @Override
    public void update(Held hero,float dt) {

        stateTimer +=dt;
        super.update(hero,dt);
        if(hitByFeedback){
            if(vonFeedbackbetroffen){b2body.setLinearVelocity(velocity);
            //setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            vonFeedbackbetroffen=false;}
        }
        else if(!dead && !runDying && !runMeleeAnimation){
            if(healer!=null){
                coordinateWalking(healer, dt);}
            else{coordinateWalking(hero, dt);}
            b2body.setLinearVelocity(velocity);
        }
        else if(b2body!=null){ b2body.setLinearVelocity(new Vector2(0,0));}
        if(healSensorActive==false && getCurrentHitpointsPercent()<0.5 && !dead){
            createHealerSensor();
             //b2body.setLinearVelocity(new Vector2(0,0));
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
    public void coordinateWalking(HumanoideSprites zielSprite, float dt){
        Vector2 einheitsvector=new Vector2(zielSprite.getb2bodyX()-getb2bodyX(),zielSprite.getb2bodyY()-getb2bodyY());
        float einheitsZahl=1/(float)Math.sqrt(Math.pow(einheitsvector.x,2)+Math.pow(einheitsvector.y,2));
        einheitsvector.x=einheitsZahl*einheitsvector.x;
        einheitsvector.y=einheitsZahl*einheitsvector.y;
        walkingVelo(zielSprite,new Vector2(einheitsvector.x*(float)getGeschwindigkeitLaufen(),einheitsvector.y*(float)getGeschwindigkeitLaufen()));
        if(einheitsvector.y>0 && einheitsvector.y> Math.abs(einheitsvector.x)){ setCurrentRichtung(2);}
        else if(einheitsvector.x>0 && einheitsvector.x> Math.abs(einheitsvector.y)){ setCurrentRichtung(1);}
        else if(einheitsvector.y<0 && Math.abs(einheitsvector.y)> Math.abs(einheitsvector.x)){ setCurrentRichtung(3);}
        else { setCurrentRichtung(0);}
    }
    public void walkingVelo(HumanoideSprites hero,Vector2 v2){
        v2.x=v2.x/10;
        v2.y=v2.y/10;

        if((getb2bodyX()-hero.getb2bodyX())<0.1f && (getb2bodyX()-hero.getb2bodyX())>-0.1f){
            velocity.x=0;
        }else
        if((velocity.x>-(float)getGeschwindigkeitLaufen()/10 && velocity.x<(float)getGeschwindigkeitLaufen()/10) || (velocity.x>=getGeschwindigkeitLaufen()/10 && v2.x<0) || (velocity.x<=-getGeschwindigkeitLaufen()/10 && v2.x>0))
            velocity.x=v2.x;
        if((getb2bodyY()-hero.getb2bodyY())<0.15f && (getb2bodyY()-hero.getb2bodyY())>-0.15f){
            velocity.y=0;
        }else if((velocity.y>-(float)getGeschwindigkeitLaufen()/10 &&velocity.y<(float)getGeschwindigkeitLaufen()/10) || (velocity.y>=getGeschwindigkeitLaufen()/10 && v2.y<0) || (velocity.y<=-getGeschwindigkeitLaufen()/10 && v2.y>0))
            velocity.y=v2.y;
    }
    @Override
    public void getsHealed(ZauberFixture z){
        super.getsHealed(z);
        healer=null;
    }

    @Override
    public void destroyBody() {
        super.destroyBody();
        getHeld().getEventList()[nachbed]=true;
    }

    public void createHealerSensor() {
        if(!dead){
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(200 / AnimaRPG.PPM);

        FixtureDef fdefSensor = new FixtureDef();
        fdefSensor.filter.categoryBits = AnimaRPG.ENEMY_SEARCH_HEALER;
        fdefSensor.filter.maskBits = AnimaRPG.ENEMY_HEAL_SENSOR;
        fdefSensor.shape = circleShape;
        fdefSensor.isSensor = true;
        healSensorFixture = b2body.createFixture(fdefSensor);
        healSensorFixture.setUserData(this);
        healSensorActive=true;}
    }
}
