package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Steffen on 02.12.2016.
 */

public class Ausruestung extends Item {
    public boolean angelegt;
    public boolean ausgewaehlt;

    public Ausruestung(String name, String itemKategorie, Vector2 grafikposi, int goldWert) {
        super(name, itemKategorie, grafikposi, goldWert);
        this.angelegt = false;
        this.ausgewaehlt = false;
    }
}
