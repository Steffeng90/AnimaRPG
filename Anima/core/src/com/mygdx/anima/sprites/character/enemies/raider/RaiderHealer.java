package com.mygdx.anima.sprites.character.enemies.raider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.enemies.EnemyHumanoid;
import com.mygdx.anima.sprites.character.zauber.fixtures.HeilzauberAOE;

/**
 * Created by Steffen on 13.11.2016.
 */

public class RaiderHealer extends EnemyHumanoid {
    Array<EnemyHumanoid> enemiesInRange;
    Boolean zaubereHeilung;

    public RaiderHealer() {
        super();
        enemiesInRange = new Array<EnemyHumanoid>();
        zaubereHeilung=false;
    }
    public void init(Playscreen screen, float x, float y, String id, int maxhp, int maxmana, int regMana, int ep, int speed, int schadenNah, int schadenfern, int schadenzauber,int schadenbigAttack, int ruestung, int boundsX, int boundsY, float castSpeed, float bowSpeed, float meleeSpeed, float thrustSpeed){
        super.init(screen, x, y, id, maxhp, maxmana, regMana, ep, speed, schadenNah, schadenfern, schadenzauber, schadenbigAttack, ruestung, boundsX, boundsY, castSpeed, bowSpeed, meleeSpeed, thrustSpeed);
    }
    @Override
    public void update(Held hero, float dt) {
        stateTimer += dt;
        for(EnemyHumanoid e:enemiesInRange){
            if((e.getCurrentHitpointsPercent()<0.5) && !runCasting){zaubereHeilung=true;}
        }
        super.update(hero, dt);

        if (vonFeedbackbetroffen) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            vonFeedbackbetroffen = false;}
        if(!runCasting && castExists){
            castExists=false;}
        else if(zaubereHeilung && !runCasting && !castExists){
                zaubereHeilung=false;
                heilen();
            }
        else if (!dead && !runDying && !runMeleeAnimation &&!runCasting && b2body.isActive()) {
            coordinateWalking(hero, dt);
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        } else if (b2body != null) {
            b2body.setLinearVelocity(new Vector2(0, 0));
        }
    }
    public void coordinateWalking(Held hero, float dt) {
        System.out.println("dst:" + (new Vector2(hero.getX(), hero.getY())).dst(new Vector2(this.getX(), this.getY())));
        if ((new Vector2(hero.getX(), hero.getY())).dst(new Vector2(this.getX(), this.getY())) <= 0.8f) {
            //System.out.println("dst:"+ distanz);
            if (hero.getX() > getX()) {
                // Runter
                if (hero.getY() > getY()) {
                    walkingVelo(hero, new Vector2(-getGeschwindigkeitLaufen(), -getGeschwindigkeitLaufen()));
                    if ((Math.abs(hero.getX() - getX()) <= Math.abs(hero.getY() - getY()))) {
                        setCurrentRichtung(3);
                    } else {
                        setCurrentRichtung(0);
                    }
                }
                // Hoch
                else {
                    walkingVelo(hero, new Vector2(-getGeschwindigkeitLaufen(), getGeschwindigkeitLaufen()));
                    if ((Math.abs(hero.getX() - getX()) <= Math.abs(hero.getY() - getY()))) {
                        setCurrentRichtung(2);
                    } else {
                        setCurrentRichtung(0);
                    }
                }
            }
            // Rechts
            else {
                // Runter
                if (hero.getY() > getY()) {
                    walkingVelo(hero, new Vector2(getGeschwindigkeitLaufen(), -getGeschwindigkeitLaufen()));
                    if ((Math.abs(hero.getX() - getX()) <= Math.abs(hero.getY() - getY()))) {
                        setCurrentRichtung(3);
                    } else {
                        setCurrentRichtung(1);
                    }
                }
                // Hoch
                else {
                    walkingVelo(hero, new Vector2(getGeschwindigkeitLaufen(), getGeschwindigkeitLaufen()));
                    if ((Math.abs(hero.getX() - getX()) <= Math.abs(hero.getY() - getY()))) {
                        setCurrentRichtung(2);
                    } else {
                        setCurrentRichtung(1);
                    }
                }
            }
        } else {
            velocity.x = 0;
            velocity.y = 0;
            if (hero.getX() > getX()) {
                if (hero.getY() > getY()) {
                    if ((Math.abs(hero.getX() - getX()) <= Math.abs(hero.getY() - getY()))) {
                        setCurrentRichtung(2);
                    } else {
                        setCurrentRichtung(1);
                    }
                }
                else {
                    if ((Math.abs(hero.getX() - getX()) <= Math.abs(hero.getY() - getY()))) {
                        setCurrentRichtung(3);
                    } else {
                        setCurrentRichtung(1);
                    }
                }
            }
            else {
                if (hero.getY() > getY()) {
                    if ((Math.abs(hero.getX() - getX()) <= Math.abs(hero.getY() - getY()))) {
                        setCurrentRichtung(2);
                    } else {
                        setCurrentRichtung(0);
                    }
                }
                else {
                    if ((Math.abs(hero.getX() - getX()) <= Math.abs(hero.getY() - getY()))) {
                        setCurrentRichtung(3);
                    } else {
                        setCurrentRichtung(0);
                    }
                }
            }
        }
    }
    public void walkingVelo(Held hero, Vector2 v2) {
        v2.x = v2.x / 10;
        v2.y = v2.y / 10;
        if ((getX() - hero.getX()) < 0.05f && (getX() - hero.getX()) > -0.05f) {
            velocity.x = 0;
        } else if ((velocity.x > -0.3f && velocity.x < 0.3f) || (velocity.x >= 0.3f && v2.x < 0) || (velocity.x <= -0.3f && v2.x > 0))
            velocity.x += v2.x;
        if ((getY() - hero.getY()) < 0.05f && (getY() - hero.getY()) > -0.05f) {
            velocity.y = 0;
        } else if ((velocity.y > -0.3f && velocity.y < 0.3f) || (velocity.y >= 0.3f && v2.y < 0) || (velocity.y <= -0.3f && v2.y > 0))
            velocity.y += v2.y;

    }
    @Override
    public void createSensor() {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(40 / AnimaRPG.PPM);

        FixtureDef fdefSensor = new FixtureDef();
        fdefSensor.filter.categoryBits = AnimaRPG.ENEMY_HEAL_SENSOR;
        fdefSensor.filter.maskBits = AnimaRPG.HERO_BIT | AnimaRPG.ENEMY_BIT | AnimaRPG.ENEMY_SEARCH_HEALER;
        fdefSensor.shape = circleShape;
        fdefSensor.isSensor = true;
        sensorFixture = b2body.createFixture(fdefSensor);
        sensorFixture.setUserData(this);
    }
    @Override
    public void sensorAnpassen(){
    }
    @Override
    public void heilen() {
        {

            if ((currentState == State.STANDING | currentState == State.WALKING) && getCurrentMana() >= 5 && stateTimer>1f) {
                setCurrentMana(getCurrentMana() - 5);
                new HeilzauberAOE(this, screen,world);
                runCasting=true;
            }
        }
    }

    public void removeEnemieInRange(EnemyHumanoid e) {
        enemiesInRange.removeValue(e,true);
    }
    public void addEnemieInRange(EnemyHumanoid e) {
        enemiesInRange.add(e);
    }
}
