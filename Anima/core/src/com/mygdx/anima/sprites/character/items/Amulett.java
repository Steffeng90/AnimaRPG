package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Steffen on 01.12.2016.
 */

public class Amulett extends Ausruestung{
    private int manareg,zauberwiderstand;
    public Amulett(String name, String itemKategorie, Vector2 grafikposi, int manareg, int zauberwiderstand, int goldWert){
        super(name, itemKategorie, grafikposi, goldWert);
        this.manareg=manareg;
        this.zauberwiderstand=zauberwiderstand;
    }

    public int getManareg() {
        return manareg;
    }

    public void setManareg(int manareg) {
        this.manareg = manareg;
    }

    public int getZauberwiderstand() {
        return zauberwiderstand;
    }

    public void setZauberwiderstand(int zauberwiderstand) {
        this.zauberwiderstand = zauberwiderstand;
    }
}