package com.mygdx.anima.tools.listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.enemies.Enemy;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Arrow;
import com.mygdx.anima.sprites.character.interaktiveObjekte.InteraktivesObjekt;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Zauber;

/**
 * Created by Steffen on 13.11.2016.
 */

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA=contact.getFixtureA();
        Fixture fixB=contact.getFixtureB();
        int cDef=fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        switch(cDef){

            case AnimaRPG.HERO_BIT | AnimaRPG.ENEMY_BIT:
                break;
            case AnimaRPG.HERO_WEAPON_BIT | AnimaRPG.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.HERO_WEAPON_BIT)
                    ((Enemy)fixB.getUserData()).getsHit((Held)fixA.getUserData());
                else
                    ((Enemy)fixA.getUserData()).getsHit((Held)fixB.getUserData());
                break;
            case AnimaRPG.HERO_SENSOR | AnimaRPG.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.HERO_SENSOR){
                    ((Held) fixA.getUserData()).setObject(true,((InteraktivesObjekt) fixB.getUserData()));}
                else{
                    ((Held) fixB.getUserData()).setObject(true,((InteraktivesObjekt) fixA.getUserData()));}
                break;
            case AnimaRPG.ENEMY_SENSOR | AnimaRPG.HERO_BIT:
                Gdx.app.log("Held im Sensor","");
                if(fixA.getFilterData().categoryBits==AnimaRPG.ENEMY_SENSOR){
                    ((Enemy) fixA.getUserData()).enemyInReichweite=true;}
                else {
                    ((Enemy) fixB.getUserData()).enemyInReichweite=true;}
                    break;
            case AnimaRPG.ENEMY_ATTACK | AnimaRPG.HERO_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.ENEMY_ATTACK)
                {   ((Held)fixB.getUserData()).isHit=true;
                    ((Held)fixB.getUserData()).treffenderEnemy=(Enemy)fixB.getUserData();}

                else
                {   ((Held)fixA.getUserData()).isHit=true;
                    ((Held)fixA.getUserData()).treffenderEnemy=(Enemy)fixB.getUserData();}
                break;
            case AnimaRPG.ARROW_BIT | AnimaRPG.HERO_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.ARROW_BIT)
                {   ((Held)fixB.getUserData()).isHit=true;
                    ((Arrow)fixA.getUserData()).setToDestroy=true;}
                else
                {   ((Held)fixA.getUserData()).isHit=true;
                ((Arrow)fixB.getUserData()).setToDestroy=true;}
                break;
            case AnimaRPG.ARROW_BIT | AnimaRPG.BARRIERE_BIT:
            case AnimaRPG.ARROW_BIT | AnimaRPG.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.ARROW_BIT){
                    ((Arrow)fixA.getUserData()).setToDestroy=true;}
                else{
                    ((Arrow)fixB.getUserData()).setToDestroy=true;}
                break;

            case AnimaRPG.ARROW_BIT | AnimaRPG.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.ARROW_BIT)
                {   ((Enemy)fixB.getUserData()).getsHit();
                    ((Arrow)fixA.getUserData()).setToDestroy=true;}
                else
                {   ((Enemy)fixA.getUserData()).getsHit();
                    ((Arrow)fixB.getUserData()).setToDestroy=true;}
                break;
            case AnimaRPG.HERO_CAST_BIT | AnimaRPG.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.HERO_CAST_BIT)
                    ((Enemy)fixB.getUserData()).getsHitbySpell((Zauber)fixA.getUserData());
                else
                    ((Enemy)fixA.getUserData()).getsHitbySpell((Zauber)fixB.getUserData());
                break;
            default:
                Gdx.app.log("undefined","unknown");break;

        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA=contact.getFixtureA();
        Fixture fixB=contact.getFixtureB();
        int cDef=fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        switch(cDef) {
            case AnimaRPG.HERO_SENSOR | AnimaRPG.OBJECT_BIT:
             //   Gdx.app.log("Object verlässt Reichweite","unknown");
                if (fixA.getFilterData().categoryBits == AnimaRPG.HERO_SENSOR){
                    ((Held) fixA.getUserData()).setObject(false,null);}
                else{
                    ((Held) fixB.getUserData()).setObject(false,null);}
                break;
            case AnimaRPG.ENEMY_SENSOR | AnimaRPG.HERO_BIT:
                Gdx.app.log("Held nicht im Sensor","");
                if(fixA.getFilterData().categoryBits==AnimaRPG.ENEMY_SENSOR){
                    ((Enemy) fixA.getUserData()).enemyInReichweite=false;}
                else {
                    ((Enemy) fixB.getUserData()).enemyInReichweite=false;}
                break;
            case AnimaRPG.ENEMY_ATTACK | AnimaRPG.HERO_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.ENEMY_ATTACK)
                {   ((Held)fixB.getUserData()).isHit=false;
                    ((Held)fixB.getUserData()).treffenderEnemy=null;}

                else
                {   ((Held)fixA.getUserData()).isHit=false;
                    ((Held)fixA.getUserData()).treffenderEnemy=null;}
                //((Held)fixA.getUserData()).getsHit((Enemy)fixB.getUserData());
                break;
        }
    }
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
