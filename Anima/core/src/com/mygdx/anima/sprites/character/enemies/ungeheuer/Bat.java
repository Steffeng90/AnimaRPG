package com.mygdx.anima.sprites.character.enemies.ungeheuer;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.HumanoideSprites;
import com.mygdx.anima.sprites.character.enemies.EnemyUngeheuer;

/**
 * Created by Steffen on 13.11.2016.
 */

public class Bat extends EnemyUngeheuer
{
    float t=0;
    double startingRadius = 150;
    double rotations = 5;
    public Bat(){
        super();
    }
    public void init(Playscreen screen,float x, float y,String id,int maxhp,int maxmana,int regMana,int ep,int speed,int schadenNah,int schadenfern,int schadenzauber,int ruestung,float boundsX,float boundsY,float castSpeed,float bowSpeed,float meleeSpeed,float thrustSpeed){
        // Am Ende des Init true übergeben (weil isFlying)
        super.init(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed,true);
        quelle=id;
        //(is);
        velocity=new Vector2(0.2f,0.2f);
        //b2body.setActive(false);
        enemyInReichweite=false;
        vonFeedbackbetroffen=false;
        framesWalk=3;
        breite=32;
        hoehe=32;

        String texureRegion="bat";
        animationenErstellen(texureRegion);
        setRegion(standingDownSprite);
    }
    @Override
    public void update(Held hero,float dt) {
        stateTimer +=dt;
        super.update(hero,dt);

        if(hitByFeedback){
            if(vonFeedbackbetroffen){b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            vonFeedbackbetroffen=false;}
        }
        else if(!dead && !runDying && !runMeleeAnimation){
            coordinateWalking(hero, dt);
            b2body.setLinearVelocity(velocity);
        }
        else if(b2body!=null){ b2body.setLinearVelocity(new Vector2(0,0));}
    }
    public void coordinateWalking(HumanoideSprites zielSprite, float dt){

        Vector2 einheitsvector=new Vector2(zielSprite.getb2bodyX()-getb2bodyX(),zielSprite.getb2bodyY()-getb2bodyY());
        float einheitsZahl=1/(float)Math.sqrt(Math.pow(einheitsvector.x,2)+Math.pow(einheitsvector.y,2));
        einheitsvector.x=einheitsZahl*einheitsvector.x;
        einheitsvector.y=einheitsZahl*einheitsvector.y;
        walkingVelo(zielSprite,new Vector2(einheitsvector.x*(float)getGeschwindigkeitLaufen(),einheitsvector.y*(float)getGeschwindigkeitLaufen()),dt);
        if(einheitsvector.y>0 && einheitsvector.y> Math.abs(einheitsvector.x)){ setCurrentRichtung(2);}
        else if(einheitsvector.x>0 && einheitsvector.x> Math.abs(einheitsvector.y)){ setCurrentRichtung(1);}
        else if(einheitsvector.y<0 && Math.abs(einheitsvector.y)> Math.abs(einheitsvector.x)){ setCurrentRichtung(3);}
        else { setCurrentRichtung(0);}
    }
    public void walkingVelo(HumanoideSprites hero,Vector2 v2,float dt){
        if((startingRadius * (1-t))>50)
        {
            t+= (dt / 16);
            if (t > 1)
                t = 1;

            float xDist=this.getb2bodyX()-hero.getb2bodyX()*AnimaRPG.PPM;
            float yDist=this.getb2bodyY()-hero.getb2bodyY()*AnimaRPG.PPM;
            double startingRadius = Math.sqrt(Math.pow(xDist/2,2)+Math.pow(yDist/2,2));


            double x = getArmX(startingRadius * (1-t), t * rotations * Math.PI * 2);
            double y = getArmY(startingRadius * (1-t), t * rotations * Math.PI * 2);

            velocity.x=(float)x/100;
            velocity.y=(float)y/100;
        }
        else{
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
    }
    public void reset(){
        super.reset();
        t=0;
    }
    public static double getArmX(double length, double angle) {
        return Math.cos(angle) * length;
    }

    public static double getArmY(double length, double angle) {
        return Math.sin(angle) * length;
    }
}
