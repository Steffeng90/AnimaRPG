package com.mygdx.anima.sprites.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Arrow;

/**
 * Created by Steffen on 13.11.2016.
 */

public class SpriteVorlage extends Sprite{
    public Playscreen screen;
    public World world;
    public Body b2body;
    public AnimaRPG anima;
    public float stateTimer, feedbackDauer;
    public enum Richtung {Links, Rechts, Oben, Unten};
    public Richtung previousRichtung, currentRichtung;
    //Einstellungen
    private int currentHitpoints,maxHitpoints,currentMana,maxMana,regMana,regHitpoints,
            schadenNah,schadenFern,schadenZauber,ruestung,zauberwiderstand,geschwindigkeitLaufen,angriffgeschwindigkeit;
    public boolean runDying,dead,destroyed,resetAktiv;

    public SpriteVorlage(){
    }
    public void destroyBody(){
        world.destroyBody(b2body);
        b2body.setUserData(null);
        //b2body=null;
        runDying=false;
        destroyed=true;
    }
    public void readyToDie(){
        //if(!this.istHeld){
            for(Fixture fix:b2body.getFixtureList()){
                Filter filter=fix.getFilterData();
                filter.categoryBits=AnimaRPG.NOTHING_BIT;
                fix.setFilterData(filter);}
            b2body.setLinearVelocity(new Vector2(0,0));
        //}
        runDying=true;
        //this.setBounds(0,0,42 / AnimaRPG.PPM, 42/ AnimaRPG.PPM);
            // Die Größe wird hier bewusst angepasst, da wenn eine Nahkampf (mit großer Waffe) unterbrochen wird die Größenanpassung
            // evtl. übersprungen werden. Hier wird sichergestellt, dass der Char in normale Größe stirbt
        //this.setSize(42 / AnimaRPG.PPM, 42/ AnimaRPG.PPM);
    }
    // Diese Methode zerstört die B2bodys und alle Fixtures, ohne EP zu geben.
    // Grund dafür ist der Kartenwechsel
    public void bodyZerstoeren() {
    }

    public void setCurrentRichtung(int richtung) {
        previousRichtung=currentRichtung;
        switch (richtung) {
            case 0:
                currentRichtung = Richtung.Links;
                break;
            case 1:
                currentRichtung = Richtung.Rechts;
                break;
            case 2:
                currentRichtung = Richtung.Oben;
                break;
            case 3:
                currentRichtung = Richtung.Unten;
                break;
            default:
                break;
        }
    }



    public int getCurrentHitpoints() {
        return currentHitpoints;
    }
    public float getCurrentHitpointsPercent(){ return ((float)currentHitpoints/(float)maxHitpoints);}
    public void setCurrentHitpoints(int currentHitpoints) {
        if(currentHitpoints<=0 && !dead ){
            readyToDie();
            this.currentHitpoints=0;
        }else if(currentHitpoints>maxHitpoints){
            this.currentHitpoints=maxHitpoints;
        }
        else{
            this.currentHitpoints = currentHitpoints;
        }
    }
    public int getMaxHitpoints() {
        return maxHitpoints;
    }

    public void setMaxHitpoints(int maxHitpoints) {
        int temp=this.maxHitpoints;
        this.maxHitpoints = maxHitpoints;
        setCurrentHitpoints(getCurrentHitpoints()+(maxHitpoints-temp));
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int currentMana) {

        if (currentMana <= 0) {
            this.currentMana = 0;
        } else if (currentMana > maxMana) {
            this.currentMana = maxMana;
        } else {
            this.currentMana = currentMana;
        }
    }
    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
        setCurrentMana(maxMana);

    }

    public int getRegMana() {
        return regMana;
    }

    public void setRegMana(int regMana) {
        this.regMana = regMana;
    }

    public int getSchadenNah() {
        return schadenNah;
    }

    public void setSchadenNah(int schadenNah) {
        this.schadenNah = schadenNah;
    }

    public int getSchadenFern() {
        return schadenFern;
    }

    public void setSchadenFern(int schadenFern) {
        this.schadenFern = schadenFern;
    }

    public int getSchadenZauber() {
        return schadenZauber;
    }

    public void setSchadenZauber(int schadenZauber) {
        this.schadenZauber = schadenZauber;
    }

    public int getRuestung() {
        return ruestung;
    }

    public void setRuestung(int ruestung) {
        this.ruestung = ruestung;
    }

    public int getGeschwindigkeitLaufen() {
        return geschwindigkeitLaufen;
    }

    public void setGeschwindigkeitLaufen(int geschwindigkeitLaufen) {
        this.geschwindigkeitLaufen = geschwindigkeitLaufen;
    }
    public int getZauberwiderstand() {
        return zauberwiderstand;
    }

    public void setZauberwiderstand(int zauberwiderstand) {
        this.zauberwiderstand = zauberwiderstand;
    }

    public int getAngriffgeschwindigkeit() {
        return angriffgeschwindigkeit;
    }

    public void setAngriffgeschwindigkeit(int angriffgeschwindigkeit) {
        this.angriffgeschwindigkeit = angriffgeschwindigkeit;
    }

    public int getRegHitpoints() {
        return regHitpoints;
    }

    public void setRegHitpoints(int regHitpoints) {
        this.regHitpoints = regHitpoints;
    }
    public Richtung getCurrentRichtung(){
        return currentRichtung;
    }
    public Richtung getPreviousRichtung(){ return previousRichtung; }

    public float getFeedbackDauer() {
        return feedbackDauer;
    }

    public void setFeedbackDauer(float feedbackDauer) {
        this.feedbackDauer = feedbackDauer;
    }
    public void reset(){
        resetAktiv=true;
        if(b2body!=null){for(Fixture fix:b2body.getFixtureList()){
            Filter filter=fix.getFilterData();
            filter.categoryBits=AnimaRPG.NOTHING_BIT;
            fix.setFilterData(filter);}}
        //world.destroyBody(b2body);
        //b2body.setUserData(null);
        b2body=null;
        runDying=false;
        destroyed=false;

        currentHitpoints=0;
        maxHitpoints=0;
        currentMana=0;
        maxMana=0;
        regMana=0;
        regHitpoints=0;
        schadenNah=0;
        schadenFern=0;
        schadenZauber=0;
        ruestung=0;
        zauberwiderstand=0;
        geschwindigkeitLaufen=0;
        angriffgeschwindigkeit=0;

        runDying=false;
        dead=false;
        destroyed=false;
    }
    // beide Methoden ermitteln die Position des Bodys des Helds, damit Figuren sich nicht an seinem Sprite-Grafik orientieren.
    public float getb2bodyY(){
        return this.b2body.getPosition().y;
    }
    public float getb2bodyX(){
        return this.b2body.getPosition().x;
    }

}