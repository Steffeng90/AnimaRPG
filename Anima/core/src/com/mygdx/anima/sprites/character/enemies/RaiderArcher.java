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

/**
 * Created by Steffen on 13.11.2016.
 */

public class RaiderArcher extends Enemy
{
    public RaiderArcher(Playscreen screen,float x, float y){
        super(screen,x,y,"character/raider.png");
        create();
    }
    @Override
    public void update(Held hero,float dt) {
        stateTimer +=dt;
        super.update(hero,dt);
        coordinateWalking(hero, dt);
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
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
        if(hero.getX()<getX()  ){
            // Runter
            if(hero.getY()<getY()){

                walkingVelo(new Vector2(-0.1f,-0.1f));
                if((Math.abs(hero.getX()-getX())<=Math.abs(hero.getY()-getY()))){ setCurrentRichtung(3);}
                else{ setCurrentRichtung(0);}
            }
            // Hoch
            else{
                walkingVelo(new Vector2(-0.1f,0.1f));
                if((Math.abs(hero.getX()-getX())<=Math.abs(hero.getY()-getY()))){ setCurrentRichtung(2);}
                else{setCurrentRichtung(0);}
            }}
        // Rechts
        else {
            // Runter
            if (hero.getY() < getY()) {
                walkingVelo(new Vector2(0.1f,-0.1f));
                if((Math.abs(hero.getX()-getX())<=Math.abs(hero.getY()-getY()))){ setCurrentRichtung(3);}
                else{setCurrentRichtung(1);}
            }
            // Hoch
            else{
                walkingVelo(new Vector2(0.1f,0.1f));
                if((Math.abs(hero.getX()-getX())<=Math.abs(hero.getY()-getY()))){ setCurrentRichtung(2);}
                else{setCurrentRichtung(1);}
            }
        }
    }
    public void walkingVelo(Vector2 v2){
        if((velocity.x>-0.3f && velocity.x<0.3f) || (velocity.x>=0.3f && v2.x<0) || (velocity.x<=-0.3f && v2.x>0))
            velocity.x+=v2.x;
        if((velocity.y>-0.3f &&velocity.y<0.3f) || (velocity.y>=0.3f && v2.y<0) || (velocity.y<=-0.3f && v2.y>0))
            velocity.y+=v2.y;
    }
    public void getsHit(Held hero){
        getsDamaged();
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
    public void getsDamaged(){
        hitCounter++;

        if(hitCounter>=3 && !runDying){
            readyToDie();
        }
    }
    public void readyToDie(){
        super.readyToDie();
    }
    public void draw(Batch batch){
        if(!dead || stateTimer< 3)
            super.draw(batch);
    }

    @Override
    public void getsHit() {

    }
}
