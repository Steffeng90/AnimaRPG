package com.mygdx.anima.sprites.character.enemies.raider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.enemies.Enemy;
import com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Nova;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Zauber;

import java.util.ArrayList;

/**
 * Created by Steffen on 13.11.2016.
 */

public class RaiderHealer extends Enemy {
    ArrayList<Enemy> enemiesInRange;
    Playscreen screen;

    public RaiderHealer(Playscreen screen, float x, float y, String id, int maxhp, int maxmana, int regMana, int ep, int speed, int schadenNah, int schadenfern, int schadenzauber, int ruestung, int boundsX, int boundsY, float castSpeed, float bowSpeed, float meleeSpeed, float thrustSpeed) {
        super(screen, x, y, id, maxhp, maxmana, regMana, ep, speed, schadenNah, schadenfern, schadenzauber, ruestung, boundsX, boundsY, castSpeed, bowSpeed, meleeSpeed, thrustSpeed);
        enemiesInRange = new ArrayList<Enemy>();
        this.screen = screen;
        zaubereHeilung=false;
    }

    @Override
    public void update(Held hero, float dt) {
        stateTimer += dt;

        super.update(hero, dt);

        if (vonFeedbackbetroffen) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            vonFeedbackbetroffen = false;}
            else if(zaubereHeilung)
            {heilen();Gdx.app.log("Heilendurchführen","unknown");
                zaubereHeilung=false;
            }
            else if (!dead && !runDying && !runMeleeAnimation &&!runCasting) {
            coordinateWalking(hero, dt);
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        } else if (b2body != null) {
            b2body.setLinearVelocity(new Vector2(0, 0));
        }
    }

    public void create() {
        super.create();
    }

    public void setCurrentRichtung(int richtung) {
        super.setCurrentRichtung(richtung);
    }

    public void changeVelocity(Vector2 v2) {
        velocity.x += v2.x;
        velocity.y += v2.y;
    }

    public void coordinateWalking(Held hero, float dt) {
        if (hero.getX() < getX()) {
            // Runter
            if (hero.getY() < getY()) {

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
            if (hero.getY() < getY()) {
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

    public void getsHit() {
        getsDamaged(2);
    }
    public void getsHit(Held hero) {
        vonFeedbackbetroffen = true;
        getsDamaged(1);
        //Links
        float feedback = 1.5f;
        if (hero.getX() < getX()) {
            // Runter
            if (hero.getY() < getY()) {
                changeVelocity(new Vector2(feedback, feedback));
            }
            // Hoch
            else {
                changeVelocity(new Vector2(feedback, -feedback));
            }
        }
        // Rechts
        else {
            // Runter
            if (hero.getY() < getY()) {
                changeVelocity(new Vector2(-feedback, feedback));
            }
            // Hoch
            else {
                changeVelocity(new Vector2(-feedback, -feedback));
            }
        }
    }
    public void readyToDie() {
        super.readyToDie();
    }

    public void draw(Batch batch) {
        if (!dead || stateTimer < 3)
            super.draw(batch);
    }

    @Override
    public void createSensor() {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(40 / AnimaRPG.PPM);

        FixtureDef fdefSensor = new FixtureDef();
        fdefSensor.filter.categoryBits = AnimaRPG.ENEMY_HEAL_SENSOR;
        fdefSensor.filter.maskBits = AnimaRPG.HERO_BIT | AnimaRPG.ENEMY_BIT;
        fdefSensor.shape = circleShape;
        fdefSensor.isSensor = true;
        sensorFixture = b2body.createFixture(fdefSensor);
        sensorFixture.setUserData(this);
        Gdx.app.log("Shaperstellen", "");
    }
    @Override
    public void sensorAnpassen(){

    }
    @Override
    public void heilen() {
        {
            Gdx.app.log("MinusMAna", ""+getCurrentMana());

            if ((currentState == State.STANDING | currentState == State.WALKING) && getCurrentMana() >= 5) {
                Gdx.app.log("MinusMAna", "");
                setCurrentMana(getCurrentMana() - 5);
                new HeilzauberAOE(this, screen);
                runCasting=true;
            }
        }
    }
}