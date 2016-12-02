package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Steffen on 01.12.2016.
 */

public class Waffe extends Ausruestung{
    public int schaden;

    public Waffe(String name, String itemKategorie, Vector2 grafikposi,int goldWert,int schaden){
        super(name, itemKategorie, grafikposi, goldWert);
        this.schaden=schaden;
    }
}
