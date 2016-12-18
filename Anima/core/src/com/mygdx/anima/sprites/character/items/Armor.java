package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Steffen on 01.12.2016.
 */

public class Armor extends Ausruestung{
    private int ruestung;
    public Armor(String name, String itemKategorie, Vector2 grafikposi, int ruestung, int goldWert){
        super(name, itemKategorie, grafikposi, goldWert);
        this.ruestung=ruestung;
    }

    public int getRuestung() {
        return ruestung;
    }

    public void setRuestung(int ruestung) {
        this.ruestung = ruestung;
    }
}