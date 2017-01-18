package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Steffen on 01.12.2016.
 */

public class Handschuhe extends Ausruestung{
    private int ruestung,angriffgeschwindigkeit;
    public Handschuhe(String name, String itemKategorie, Vector2 grafikposi, int ruestung,int angriffgeschwindigkeit, int goldWert){
        super(name, itemKategorie, grafikposi, goldWert);
        this.ruestung=ruestung;
        this.angriffgeschwindigkeit=angriffgeschwindigkeit;
    }

    public int getRuestung() {
        return ruestung;
    }

    public void setRuestung(int ruestung) {
        this.ruestung = ruestung;
    }

    public int getAngriffgeschwindigkeit() {
        return angriffgeschwindigkeit;
    }

    public void setAngriffgeschwindigkeit(int angriffgeschwindigkeit) {
        this.angriffgeschwindigkeit = angriffgeschwindigkeit;
    }
}