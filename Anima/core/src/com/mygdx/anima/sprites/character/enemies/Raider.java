package com.mygdx.anima.sprites.character.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Zauber;
import com.mygdx.anima.tools.B2WorldCreator;

/**
 * Created by Steffen on 13.11.2016.
 */

public class Raider extends Enemy
{
    public Raider(Playscreen screen,float x, float y){
        super(screen,x,y,"character/raider.png");
        create();
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
        coordinateWalking(hero, dt);
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        }
        else{
            b2body.setLinearVelocity(new Vector2(0,0));
        }
        setRegion(getFrame(dt));
    }
    public void create(){
        super.create();
    }
    public void setCurrentRichtung(int richtung)
    {
        super.setCurrentRichtung(richtung);
    }
    public void changeVelocity(Vector2 v2){
        velocity.x+=v2.x;
        velocity.y+=v2.y;
    }
    public void coordinateWalking(Held hero, float dt){
        if(hero.getX()<getX() ){
            // Runter
            if(hero.getY()<getY()){

                walkingVelo(hero,new Vector2(-0.1f,-0.1f));
                if((Math.abs(hero.getX()-getX())<=Math.abs(hero.getY()-getY()))){ setCurrentRichtung(3);}
                else{ setCurrentRichtung(0);}
            }
            // Hoch
            else{
                walkingVelo(hero,new Vector2(-0.1f,0.1f));
                if((Math.abs(hero.getX()-getX())<=Math.abs(hero.getY()-getY()))){ setCurrentRichtung(2);}
                else{setCurrentRichtung(0);}
            }}
        // Rechts
        else {
            // Runter
            if (hero.getY() < getY()) {
                walkingVelo(hero,new Vector2(0.1f,-0.1f));
                if((Math.abs(hero.getX()-getX())<=Math.abs(hero.getY()-getY()))){ setCurrentRichtung(3);}
                else{setCurrentRichtung(1);}
            }
            // Hoch
            else{
                walkingVelo(hero,new Vector2(0.1f,0.1f));
                if((Math.abs(hero.getX()-getX())<=Math.abs(hero.getY()-getY()))){ setCurrentRichtung(2);}
                else{setCurrentRichtung(1);}
            }
        }
    }
    public void walkingVelo(Held hero,Vector2 v2){
        if((getX()-hero.getX())<0.05f && (getX()-hero.getX())>-0.05f){
            velocity.x=0;
        }else if((velocity.x>-0.3f && velocity.x<0.3f) || (velocity.x>=0.3f && v2.x<0) || (velocity.x<=-0.3f && v2.x>0))
            velocity.x+=v2.x;
        if((getY()-hero.getY())<0.05f && (getY()-hero.getY())>-0.05f){
            velocity.y=0;
        }else if((velocity.y>-0.3f &&velocity.y<0.3f) || (velocity.y>=0.3f && v2.y<0) || (velocity.y<=-0.3f && v2.y>0))
            velocity.y+=v2.y;

    }
    public void getsHit(){ getsDamaged(2);};
    public void getsHitbySpell(Zauber z){
        vonFeedbackbetroffen=true;
        hitByFeedback=true;
        Held tempHeld=z.zaubernder;
        getsDamaged(3);
    if(tempHeld.getX()<getX() ){
    // Runter
    if(tempHeld.getY()<getY()){
        changeVelocity(new Vector2(z.rueckstoss,z.rueckstoss));
    }
    // Hoch
    else{
        changeVelocity(new Vector2(z.rueckstoss,-z.rueckstoss));
    }}
    // Rechts
    else {
    // Runter
    if (tempHeld.getY() < getY()) {
        changeVelocity(new Vector2(-z.rueckstoss,z.rueckstoss));
    }
    // Hoch
    else{
        changeVelocity(new Vector2(-z.rueckstoss,-z.rueckstoss));
    }
}}
    public void getsHit(Held hero){
        vonFeedbackbetroffen=true;
        getsDamaged(1);
        //Links
        float feedback=1.5f;
        if(hero.getX()<getX() ){
            // Runter
            if(hero.getY()<getY()){
                changeVelocity(new Vector2(feedback,feedback));
            }
            // Hoch
            else{
                changeVelocity(new Vector2(feedback,-feedback));
            }}
        // Rechts
        else {
            // Runter
            if (hero.getY() < getY()) {
                changeVelocity(new Vector2(-feedback,feedback));
            }
            // Hoch
            else{
                changeVelocity(new Vector2(-feedback,-feedback));
            }
        }
    }
    public void readyToDie(){
        super.readyToDie();
    }
    /* public void draw(Batch batch){
        if(!dead || stateTimer< 3)
            super.draw(batch);
    }*/
}
