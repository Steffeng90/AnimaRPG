package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Steffen on 01.12.2016.
 */

public class Schuhe extends Ausruestung{
    private int ruestung,laufgeschwindigkeit,optikStufe;
    public Schuhe(String id,String name, String itemKategorie, Vector2 grafikposi, int ruestung, int laufgeschwindigkeit, int goldWert,int optikStufe){
        super(id,name, itemKategorie, grafikposi, goldWert);
        this.ruestung=ruestung;
        this.laufgeschwindigkeit=laufgeschwindigkeit;
        this.optikStufe=optikStufe;
    }

    public int getRuestung() {
        return ruestung;
    }

    public void setRuestung(int ruestung) {
        this.ruestung = ruestung;
    }

    public int getLaufgeschwindigkeit() {
        return laufgeschwindigkeit;
    }

    public int getOptikStufe() {
        return optikStufe;
    }
}