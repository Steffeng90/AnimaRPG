package com.mygdx.anima.sprites.character.zauber;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.anima.sprites.character.HumanoideSprites;

import java.io.Serializable;

/**
 * Created by Steffen on 01.12.2016.
 */

public class ZauberEntity implements Serializable {
    private enum State{nichts,ausgewaehlt,angelegt}
    private State state;
    public static enum kategorie{staerkung,schaden};
    private kategorie itemKategorie;
    private static Texture spriteQuelle_weiss=new Texture("objekte/icons_for_rpg.png")
            ,spriteQuelle_blau=new Texture("objekte/icons_for_rpg_auswahl.png"),
            spriteQuelle_gruen=new Texture("objekte/icons_for_rpg_angelegt.png");
    private TextureRegion grafikNichts,grafikAusgewaehlt,grafikAngelegt;
    private String id,name,beschreibung;
    private int effektivitaet, manakosten;
    // zauberFixtureTimer beschreibt die Prozent zu denen die Cast-Animation abgelaufen sein muss, also bei 0.8 wird der zauber erzeugt
    private float zauberZeit, zauberDauer, feedbackDauer ,zauberFixtureTimer;
    private boolean angelegt,ausgewaehlt;
    private int grafikPosX,grafikPosY;
    private com.mygdx.anima.sprites.character.zauber.fixtures.ZauberFixture zauberFixture;

    public ZauberEntity(String id,String name, String kategorieString, Vector2 grafikposi, int effektivitaet, int manakosten,float zauberFixtureTimer,float zauberZeit,float zauberDauer,String beschreibung) {
        this.id=id;
        setName(name);
        grafikPosX = (int) grafikposi.x;
        grafikPosY = (int) grafikposi.y;
        this.zauberFixtureTimer=zauberFixtureTimer;
        ausgewaehlt=false;
        angelegt=false;
        setGrafiken(new TextureRegion(spriteQuelle_weiss, grafikPosX * 34, grafikPosY * 34, 34, 34),
                new TextureRegion(spriteQuelle_gruen,grafikPosX*34,grafikPosY*34,34,34),
                new TextureRegion(spriteQuelle_blau,grafikPosX*34,grafikPosY*34,34,34));
        setItemKategorie(kategorieString);
        setEffektivitaet(effektivitaet);
        setManakosten(manakosten);
        setZauberZeit(zauberZeit);
        setZauberDauer(zauberDauer);
        this.beschreibung=beschreibung;
    }

    public boolean isAngelegt() {
        return angelegt;
    }

    public void setAngelegt(boolean angelegt) {
        this.angelegt = angelegt;
    }

    public boolean isAusgewaehlt() {
        return ausgewaehlt;
    }

    public void setAusgewaehlt(boolean ausgewaehlt) {
        this.ausgewaehlt = ausgewaehlt;
    }

    public void setGrafiken(TextureRegion grafikNichts,TextureRegion grafikAngelegt,TextureRegion grafikAusgewaehlt) {
        this.grafikNichts = grafikNichts;
        this.grafikAngelegt=grafikAngelegt;
        this.grafikAusgewaehlt=grafikAusgewaehlt;
    }
    public TextureRegion getGrafik(){
        switch (getState()){
            case angelegt: return grafikAngelegt;
            case ausgewaehlt: return  grafikAusgewaehlt;
            case nichts:
            default: return grafikNichts;
        }
    }
    public void fixtureErzeugen(HumanoideSprites.Richtung richtung){
        if(name.equals("Energie-Nova")){
            new com.mygdx.anima.sprites.character.zauber.fixtures.Nova(zauberFixtureTimer);
        }
        else if(name.equals("Blitz")){
            new com.mygdx.anima.sprites.character.zauber.fixtures.Blitz(richtung,zauberFixtureTimer);
        }
    }
    public TextureRegion getSlotGrafik(){return grafikNichts;}
    public boolean isStaerkung(){
        if(itemKategorie== kategorie.staerkung)
            return true;
        else { return false;}
    }
    public boolean isSchaden(){
        if(itemKategorie== kategorie.schaden)
                return true;
            else { return false;}
    }
    public void setItemKategorie(String itemKategorie) {
        this.itemKategorie = kategorie.valueOf(itemKategorie);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private State getState() {
        if(angelegt){return State.angelegt;}
        else if(ausgewaehlt){return  State.ausgewaehlt;}
        else {return State.nichts;}
    }

    public int getEffektivitaet() {
        return effektivitaet;
    }

    public void setEffektivitaet(int effektivitaet) {
        this.effektivitaet = effektivitaet;
    }

    public int getManakosten() {
        return manakosten;
    }

    public void setManakosten(int manakosten) {
        this.manakosten = manakosten;
    }

    public float getZauberZeit() {
        return zauberZeit;
    }

    public void setZauberZeit(float zauberZeit) {
        this.zauberZeit = zauberZeit;
    }

    public float getZauberDauer() {
        return zauberDauer;
    }

    public void setZauberDauer(float zauberDauer) {
        this.zauberDauer = zauberDauer;
    }
    //private void setState(State state) {this.state = state;}

    public String getBeschreibung() {return beschreibung;}
    public void setBeschreibung(String beschreibung) {this.beschreibung = beschreibung;}

    public com.mygdx.anima.sprites.character.zauber.fixtures.ZauberFixture getZauberFixture() {return zauberFixture;}

    public void setZauberFixture(com.mygdx.anima.sprites.character.zauber.fixtures.ZauberFixture zauberFixture) {this.zauberFixture = zauberFixture;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
