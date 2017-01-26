package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Steffen on 01.12.2016.
 */

public class Brust extends Ausruestung{
    private int ruestung,lpReg,optikStufe;

    public Brust(String id,String name, String itemKategorie, Vector2 grafikposi, int ruestung,int lpReg, int goldWert,int optikStufe){
        super(id,name, itemKategorie, grafikposi, goldWert);
        this.ruestung=ruestung;
        this.lpReg=lpReg;
        this.optikStufe=optikStufe;
    }

    public int getRuestung() {
        return ruestung;
    }

    public int getLpReg() {
        return lpReg;
    }

    public int getOptikStufe() {
        return optikStufe;
    }
}