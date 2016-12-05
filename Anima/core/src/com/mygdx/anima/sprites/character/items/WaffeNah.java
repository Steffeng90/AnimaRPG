package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Steffen on 01.12.2016.
 */

public class WaffeNah extends Ausruestung{
    private int schaden;

    public WaffeNah(String name, String itemKategorie, Vector2 grafikposi, int goldWert, int schaden){
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