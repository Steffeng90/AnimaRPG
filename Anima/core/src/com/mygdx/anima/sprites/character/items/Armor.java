package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Steffen on 01.12.2016.
 */

public class Armor extends Ausruestung{
    public int ruestung;
    public Armor(String name, String itemKategorie, Vector2 grafikposi, int goldWert, int ruestung){
        super(name, itemKategorie, grafikposi, goldWert);
        this.ruestung=ruestung;
    }
    }