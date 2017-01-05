package com.mygdx.anima.tools.listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.enemies.Enemy;
import com.mygdx.anima.sprites.character.enemies.raider.Raider;
import com.mygdx.anima.sprites.character.enemies.raider.RaiderHealer;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Arrow;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Gebietswechsel;
import com.mygdx.anima.sprites.character.interaktiveObjekte.InteraktivesObjekt;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Nova;
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
                    ((Enemy)fixB.getUserData()).getsHitbySword((Held)fixA.getUserData());
                else
                    ((Enemy)fixA.getUserData()).getsHitbySword((Held)fixB.getUserData());
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
                {   ((Held)fixB.getUserData()).isHitbyMelee=true;
                    ((Held)fixB.getUserData()).treffenderEnemy=(Enemy)fixA.getUserData();}
                else
                {   ((Held)fixA.getUserData()).isHitbyMelee=true;
                    ((Held)fixA.getUserData()).treffenderEnemy=(Enemy)fixB.getUserData();}
                break;
            case AnimaRPG.ARROW_BIT | AnimaRPG.HERO_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.ARROW_BIT)
                {
                    ((Held)fixB.getUserData()).treffenderEnemy=(Enemy)((Arrow)fixA.getUserData()).erzeuger;
                    ((Held)fixB.getUserData()).isHitbyBow=true;
                    ((Arrow)fixA.getUserData()).setToDestroy=true;}
                else
                {   ((Held)fixA.getUserData()).treffenderEnemy=(Enemy)((Arrow)fixB.getUserData()).erzeuger;
                    ((Held)fixA.getUserData()).isHitbyBow=true;
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
                {   ((Enemy)fixB.getUserData()).getsHitbyBow();
                    ((Arrow)fixA.getUserData()).setToDestroy=true;}
                else
                {   ((Enemy)fixA.getUserData()).getsHitbyBow();
                    ((Arrow)fixB.getUserData()).setToDestroy=true;}
                break;
            case AnimaRPG.HERO_CAST_BIT | AnimaRPG.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.HERO_CAST_BIT)
                    ((Enemy)fixB.getUserData()).getsHitbySpell((Zauber)fixA.getUserData());
                else
                    ((Enemy)fixA.getUserData()).getsHitbySpell((Zauber)fixB.getUserData());
                break;
            case AnimaRPG.HERO_BIT | AnimaRPG.GEBIETSWECHSEL_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.GEBIETSWECHSEL_BIT){
                    Playscreen.setMapId(((Gebietswechsel)fixA.getUserData()).getNextMapId());
                    Playscreen.setMapEinstieg(((Gebietswechsel)fixA.getUserData()).getAusgangsrichtung());}
                else{
                    Playscreen.setMapId(((Gebietswechsel)fixB.getUserData()).getNextMapId());
                    Playscreen.setMapEinstieg(((Gebietswechsel)fixB.getUserData()).getAusgangsrichtung());}
                Playscreen.setMapWechsel(true);
                break;
            case AnimaRPG.ENEMY_HEAL_SENSOR | AnimaRPG.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.ENEMY_HEAL_SENSOR)
                {{((RaiderHealer)fixA.getUserData()).addEnemieInRange((Enemy)fixB.getUserData());}}
                    else
                {{((RaiderHealer)fixB.getUserData()).addEnemieInRange((Enemy)fixA.getUserData());}}
                break;
            case AnimaRPG.ENEMY_CAST_HEAL | AnimaRPG.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.ENEMY_CAST_HEAL)
                    ((Enemy)fixB.getUserData()).getsHitbySpell((Zauber)fixA.getUserData());
                else
                    ((Enemy)fixA.getUserData()).getsHitbySpell((Zauber)fixB.getUserData());
                break;
            case AnimaRPG.ENEMY_HEAL_SENSOR | AnimaRPG.HERO_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.ENEMY_HEAL_SENSOR)
                {((Enemy)fixA.getUserData()).enemyNear=true;}
                else{((Enemy)fixB.getUserData()).enemyNear=true;}
                break;
            case AnimaRPG.ENEMY_HEAL_SENSOR | AnimaRPG.ENEMY_SEARCH_HEALER:
                if(fixA.getFilterData().categoryBits==AnimaRPG.ENEMY_SEARCH_HEALER)
                {if(((RaiderHealer) fixB.getUserData()).getCurrentMana()>=4){((Raider)fixA.getUserData()).healer=(RaiderHealer) fixB.getUserData();}}
                else{
                if(((RaiderHealer) fixA.getUserData()).getCurrentMana()>=4){((Raider)fixB.getUserData()).healer=(RaiderHealer) fixA.getUserData();}}
                break;
            default:
                //Gdx.app.log("undefined","unknown");
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA=contact.getFixtureA();
        Fixture fixB=contact.getFixtureB();
        int cDef=fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        switch(cDef) {
            case AnimaRPG.HERO_SENSOR | AnimaRPG.OBJECT_BIT:
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
                    {((Held)fixB.getUserData()).treffenderEnemy=null;}
                else{((Held)fixA.getUserData()).treffenderEnemy=null;}
                break;
            case AnimaRPG.ENEMY_HEAL_SENSOR | AnimaRPG.HERO_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.ENEMY_HEAL_SENSOR)
                {((Enemy)fixA.getUserData()).enemyNear=false;}
                else{((Enemy)fixB.getUserData()).enemyNear=false;}
                break;
            case AnimaRPG.ENEMY_HEAL_SENSOR | AnimaRPG.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.ENEMY_HEAL_SENSOR)
                {{((RaiderHealer)fixA.getUserData()).removeEnemieInRange((Enemy)fixB.getUserData());}}
                else
                {{((RaiderHealer)fixB.getUserData()).removeEnemieInRange((Enemy)fixA.getUserData());}}
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
