package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Steffen on 01.12.2016.
 */

public class Helm extends Ausruestung{
    private int lebenspunkte,zauberkraft;
    public Helm(String id,String name, String itemKategorie, Vector2 grafikposi, int lebenspunkte, int zauberkraft, int goldWert){
        super(id,name, itemKategorie, grafikposi, goldWert);
        this.lebenspunkte=lebenspunkte;
        this.zauberkraft=zauberkraft;
    }

    public int getLebenspunkte() {
        return lebenspunkte;
    }

    public void setLebenspunkte(int lebenspunkte) {
        this.lebenspunkte = lebenspunkte;
    }

    public int getZauberkraft() {
        return zauberkraft;
    }

    public void setZauberkraft(int zauberkraft) {
        this.zauberkraft = zauberkraft;
    }
}