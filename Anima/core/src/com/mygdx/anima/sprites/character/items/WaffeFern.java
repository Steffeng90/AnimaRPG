package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Steffen on 01.12.2016.
 */

public class WaffeFern extends Ausruestung{
    private int schaden;

    public WaffeFern(String name, String itemKategorie, Vector2 grafikposi, int schaden, int goldWert){
        super(name, itemKategorie, grafikposi, goldWert);
        this.schaden=schaden;
    }

    public int getSchaden() {
        return schaden;
    }

    public void setSchaden(int schaden) {
        this.schaden = schaden;
    }
}
