package com.mygdx.anima.tools.listener;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.enemies.EnemyHumanoid;
import com.mygdx.anima.sprites.character.enemies.EnemyUngeheuer;
import com.mygdx.anima.sprites.character.enemies.raider.Raider;
import com.mygdx.anima.sprites.character.enemies.raider.RaiderHealer;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Arrow;
import com.mygdx.anima.sprites.character.interaktiveObjekte.DialogArea;
import com.mygdx.anima.sprites.character.interaktiveObjekte.FriendlyNPC;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Gebietswechsel;
import com.mygdx.anima.sprites.character.interaktiveObjekte.InteraktivesObjekt;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Schatztruhe;
import com.mygdx.anima.sprites.character.zauber.fixtures.ZauberFixture;

/**
 * Created by Steffen on 13.11.2016.
 */

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA=contact.getFixtureA();
        Fixture fixB=contact.getFixtureB();
        try {
            int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
            switch (cDef) {

                case AnimaRPG.HERO_BIT | AnimaRPG.ENEMY_BIT:
                    break;
                case AnimaRPG.HERO_WEAPON_BIT | AnimaRPG.ENEMY_BIT:
                    if (fixA.getFilterData().categoryBits == AnimaRPG.HERO_WEAPON_BIT)
                        ((EnemyHumanoid) fixB.getUserData()).getsHitbySword((Held) fixA.getUserData());
                    else {
                        ((EnemyHumanoid) fixA.getUserData()).getsHitbySword((Held) fixB.getUserData());
                    }
                    break;
                case AnimaRPG.HERO_WEAPON_BIT | AnimaRPG.UNGEHEUER_BIT:
                    if (fixA.getFilterData().categoryBits == AnimaRPG.HERO_WEAPON_BIT)
                        ((EnemyUngeheuer) fixB.getUserData()).getsHitbySword((Held) fixA.getUserData());
                    else
                        ((EnemyUngeheuer) fixA.getUserData()).getsHitbySword((Held) fixB.getUserData());
                    break;
                case AnimaRPG.HERO_SENSOR | AnimaRPG.OBJECT_BIT:
                    if (fixB.getUserData() instanceof Schatztruhe) {
                        if (((Schatztruhe) fixB.getUserData()).closed == false) {
                        } else {
                            ((Held) fixA.getUserData()).setObject(true, ((InteraktivesObjekt) fixB.getUserData()));
                        }
                    } else if (fixA.getUserData() instanceof Schatztruhe) {
                        if (((Schatztruhe) fixA.getUserData()).closed == false) {
                        } else {
                            ((Held) fixB.getUserData()).setObject(true, ((InteraktivesObjekt) fixA.getUserData()));
                        }
                    } else if (fixB.getUserData() instanceof FriendlyNPC) {
                        ((Held) fixA.getUserData()).setNPC(true, ((FriendlyNPC) fixB.getUserData()));
                    } else if (fixA.getUserData() instanceof FriendlyNPC) {
                        ((Held) fixB.getUserData()).setNPC(true, ((FriendlyNPC) fixA.getUserData()));
                    }
                    break;
                case AnimaRPG.HERO_BIT | AnimaRPG.OBJECT_BIT:
                    if (fixA.getUserData() instanceof DialogArea) {
                        ((DialogArea) fixA.getUserData()).checkForEvents();
                    } else if (fixB.getUserData() instanceof DialogArea) {
                        ((DialogArea) fixB.getUserData()).checkForEvents();
                    } else if (fixA.getUserData() instanceof Gebietswechsel) {
                        Playscreen.setMapId(((Gebietswechsel) fixA.getUserData()).getNextMapId());
                        Playscreen.setMapEinstieg(((Gebietswechsel) fixA.getUserData()).getAusgangsrichtung());
                        ((Held) fixB.getUserData()).screen.setMapWechsel(true);
                    } else if (fixB.getUserData() instanceof Gebietswechsel) {
                        Playscreen.setMapId(((Gebietswechsel) fixB.getUserData()).getNextMapId());
                        Playscreen.setMapEinstieg(((Gebietswechsel) fixB.getUserData()).getAusgangsrichtung());
                        ((Held) fixA.getUserData()).screen.setMapWechsel(true);
                    }
                    break;
                case AnimaRPG.ENEMY_SENSOR | AnimaRPG.HERO_BIT:
                    if (fixA.getFilterData().categoryBits == AnimaRPG.ENEMY_SENSOR) {
                        ((EnemyHumanoid) fixA.getUserData()).enemyInReichweite = true;
                    } else {
                        ((EnemyHumanoid) fixB.getUserData()).enemyInReichweite = true;
                    }
                    break;
                case AnimaRPG.UNGEHEUER_SENSOR_BIT | AnimaRPG.HERO_BIT:
                    if (fixA.getFilterData().categoryBits == AnimaRPG.UNGEHEUER_SENSOR_BIT) {
                        ((EnemyUngeheuer) fixA.getUserData()).enemyInReichweite = true;
                    } else {
                        ((EnemyUngeheuer) fixB.getUserData()).enemyInReichweite = true;
                    }
                    break;
                case AnimaRPG.ENEMY_ATTACK | AnimaRPG.HERO_BIT:
                    if (fixA.getFilterData().categoryBits == AnimaRPG.ENEMY_ATTACK) {
                        ((Held) fixB.getUserData()).isHitbyMelee = true;
                        ((Held) fixB.getUserData()).treffenderEnemy = (EnemyHumanoid) fixA.getUserData();
                    } else {
                        ((Held) fixA.getUserData()).isHitbyMelee = true;
                        ((Held) fixA.getUserData()).treffenderEnemy = (EnemyHumanoid) fixB.getUserData();
                    }
                    break;
                case AnimaRPG.UNGEHEUER_ATTACK_BIT | AnimaRPG.HERO_BIT:
                    if (fixA.getFilterData().categoryBits == AnimaRPG.UNGEHEUER_ATTACK_BIT) {
                        ((Held) fixB.getUserData()).isHitbyMelee = true;
                        ((Held) fixB.getUserData()).treffenderEnemy = (EnemyUngeheuer) fixA.getUserData();
                    } else {
                        ((Held) fixA.getUserData()).isHitbyMelee = true;
                        ((Held) fixA.getUserData()).treffenderEnemy = (EnemyUngeheuer) fixB.getUserData();
                    }
                    break;
                case AnimaRPG.ARROW_BIT | AnimaRPG.HERO_BIT:
                    if (fixA.getFilterData().categoryBits == AnimaRPG.ARROW_BIT) {
                        ((Held) fixB.getUserData()).treffenderEnemy = (EnemyHumanoid) ((Arrow) fixA.getUserData()).erzeuger;
                        ((Held) fixB.getUserData()).isHitbyBow = true;
                        ((Arrow) fixA.getUserData()).setToDestroy = true;
                    } else {
                        ((Held) fixA.getUserData()).treffenderEnemy = (EnemyHumanoid) ((Arrow) fixB.getUserData()).erzeuger;
                        ((Held) fixA.getUserData()).isHitbyBow = true;
                        ((Arrow) fixB.getUserData()).setToDestroy = true;
                    }
                    break;
                case AnimaRPG.ARROW_BIT | AnimaRPG.BARRIERE_BIT:
                case AnimaRPG.ARROW_BIT | AnimaRPG.OBJECT_BIT:
                    if (fixA.getFilterData().categoryBits == (AnimaRPG.ARROW_BIT)) {
                        ((Arrow) fixA.getUserData()).setToDestroy = true;
                    } else {
                        ((Arrow) fixB.getUserData()).setToDestroy = true;
                    }
                    break;
                case AnimaRPG.ARROW_BIT | AnimaRPG.ARROW_BIT:
                    if (fixB.getUserData() instanceof Arrow) {
                        ((Arrow) fixB.getUserData()).setToDestroy = true;
                    }
                    if (fixA.getUserData() instanceof Arrow) {
                        ((Arrow) fixA.getUserData()).setToDestroy = true;
                    }
                    break;
                case AnimaRPG.ARROW_BIT | AnimaRPG.ENEMY_BIT:
                    if (fixA.getFilterData().categoryBits == AnimaRPG.ARROW_BIT) {
                        ((EnemyHumanoid) fixB.getUserData()).getsHitbyBow();
                        ((Arrow) fixA.getUserData()).setToDestroy = true;
                    } else {
                        ((EnemyHumanoid) fixA.getUserData()).getsHitbyBow();
                        ((Arrow) fixB.getUserData()).setToDestroy = true;
                    }
                    break;
                case AnimaRPG.ARROW_BIT | AnimaRPG.UNGEHEUER_BIT:
                    if (fixA.getFilterData().categoryBits == AnimaRPG.ARROW_BIT) {
                        ((EnemyUngeheuer) fixB.getUserData()).getsHitbyBow();
                        ((Arrow) fixA.getUserData()).setToDestroy = true;
                    } else {
                        ((EnemyUngeheuer) fixA.getUserData()).getsHitbyBow();
                        ((Arrow) fixB.getUserData()).setToDestroy = true;
                    }
                    break;
                case AnimaRPG.HERO_CAST_BIT | AnimaRPG.ENEMY_BIT:
                    if (fixA.getFilterData().categoryBits == AnimaRPG.HERO_CAST_BIT)
                        ((EnemyHumanoid) fixB.getUserData()).getsHitbySpell((ZauberFixture) fixA.getUserData());
                    else
                        ((EnemyHumanoid) fixA.getUserData()).getsHitbySpell((ZauberFixture) fixB.getUserData());
                    break;
                case AnimaRPG.HERO_CAST_BIT | AnimaRPG.UNGEHEUER_BIT:
                    if (fixA.getFilterData().categoryBits == AnimaRPG.HERO_CAST_BIT)
                        ((EnemyUngeheuer) fixB.getUserData()).getsHitbySpell((ZauberFixture) fixA.getUserData());
                    else
                        ((EnemyUngeheuer) fixA.getUserData()).getsHitbySpell((ZauberFixture) fixB.getUserData());
                    break;

                case AnimaRPG.ENEMY_HEAL_SENSOR | AnimaRPG.ENEMY_BIT:
                    if (fixA.getFilterData().categoryBits == AnimaRPG.ENEMY_HEAL_SENSOR) {
                        {
                            ((RaiderHealer) fixA.getUserData()).addEnemieInRange((EnemyHumanoid) fixB.getUserData());
                        }
                    } else {
                        {
                            ((RaiderHealer) fixB.getUserData()).addEnemieInRange((EnemyHumanoid) fixA.getUserData());
                        }
                    }
                    break;
                case AnimaRPG.ENEMY_CAST_HEAL | AnimaRPG.ENEMY_BIT:
                    if (fixA.getFilterData().categoryBits == AnimaRPG.ENEMY_CAST_HEAL)
                        ((EnemyHumanoid) fixB.getUserData()).getsHitbySpell((ZauberFixture) fixA.getUserData());
                    else
                        ((EnemyHumanoid) fixA.getUserData()).getsHitbySpell((ZauberFixture) fixB.getUserData());
                    break;
                case AnimaRPG.ENEMY_HEAL_SENSOR | AnimaRPG.HERO_BIT:
                    if (fixA.getFilterData().categoryBits == AnimaRPG.ENEMY_HEAL_SENSOR) {
                        ((EnemyHumanoid) fixA.getUserData()).enemyNear = true;
                    } else {
                        ((EnemyHumanoid) fixB.getUserData()).enemyNear = true;
                    }
                    break;
                case AnimaRPG.ENEMY_HEAL_SENSOR | AnimaRPG.ENEMY_SEARCH_HEALER:
                    if (fixA.getFilterData().categoryBits == AnimaRPG.ENEMY_SEARCH_HEALER) {
                        if (((RaiderHealer) fixB.getUserData()).getCurrentMana() >= 4) {
                            ((Raider) fixA.getUserData()).healer = (RaiderHealer) fixB.getUserData();
                        }
                    } else {
                        if (((RaiderHealer) fixA.getUserData()).getCurrentMana() >= 4) {
                            ((Raider) fixB.getUserData()).healer = (RaiderHealer) fixA.getUserData();
                        }
                    }
                    break;
                default:
                    break;
            }
        }catch(Exception e){

        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA=contact.getFixtureA();
        Fixture fixB=contact.getFixtureB();
        int cDef=fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        switch(cDef) {
            case AnimaRPG.HERO_SENSOR | AnimaRPG.OBJECT_BIT:
                if (fixB.getUserData() instanceof Schatztruhe){
                    ((Held) fixA.getUserData()).setObject(false,null);}
                else if(fixA.getUserData() instanceof Schatztruhe){
                    ((Held) fixB.getUserData()).setObject(false,null);}
                else if (fixB.getUserData() instanceof FriendlyNPC){
                    ((Held) fixA.getUserData()).setNPC(false,null);}
                else if(fixA.getUserData() instanceof FriendlyNPC){
                    ((Held) fixB.getUserData()).setNPC(false,null);}
                break;
            case AnimaRPG.ENEMY_SENSOR | AnimaRPG.HERO_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.ENEMY_SENSOR){
                    ((EnemyHumanoid) fixA.getUserData()).enemyInReichweite=false;}
                else {
                    ((EnemyHumanoid) fixB.getUserData()).enemyInReichweite=false;}
                break;
            case AnimaRPG.UNGEHEUER_SENSOR_BIT | AnimaRPG.HERO_BIT:
                if(fixB.getFilterData().categoryBits==AnimaRPG.UNGEHEUER_SENSOR_BIT){
                    ((EnemyUngeheuer) fixB.getUserData()).enemyInReichweite=false;}
                else {
                    ((EnemyUngeheuer) fixA.getUserData()).enemyInReichweite=false;}
                break;
            case AnimaRPG.ENEMY_ATTACK | AnimaRPG.HERO_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.ENEMY_ATTACK)
                    {((Held)fixB.getUserData()).treffenderEnemy =null;}
                else{((Held)fixA.getUserData()).treffenderEnemy =null;}
                break;
            case AnimaRPG.UNGEHEUER_ATTACK_BIT | AnimaRPG.HERO_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.UNGEHEUER_ATTACK_BIT)
                {((Held)fixB.getUserData()).treffenderEnemy =null;}
                else{((Held)fixA.getUserData()).treffenderEnemy =null;}
                break;
            case AnimaRPG.ENEMY_HEAL_SENSOR | AnimaRPG.HERO_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.ENEMY_HEAL_SENSOR)
                {((EnemyHumanoid)fixA.getUserData()).enemyNear=false;}
                else{((EnemyHumanoid)fixB.getUserData()).enemyNear=false;}
                break;
            case AnimaRPG.ENEMY_HEAL_SENSOR | AnimaRPG.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits==AnimaRPG.ENEMY_HEAL_SENSOR)
                {{((RaiderHealer)fixA.getUserData()).removeEnemieInRange((EnemyHumanoid)fixB.getUserData());}}
                else
                {{((RaiderHealer)fixB.getUserData()).removeEnemieInRange((EnemyHumanoid)fixA.getUserData());}}
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
