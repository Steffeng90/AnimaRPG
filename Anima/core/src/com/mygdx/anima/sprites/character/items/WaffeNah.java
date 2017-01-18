package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Steffen on 01.12.2016.
 */

public class WaffeNah extends Ausruestung{
    private int schaden;
    private int animationsStufe;


    public WaffeNah(String name, String itemKategorie, Vector2 grafikposi, int schaden, int goldWert,int animationsStufe){
        super(name, itemKategorie, grafikposi, goldWert);
        this.schaden=schaden;
        this.animationsStufe=animationsStufe;

    }

    public int getSchaden() {
        return schaden;
    }

    public void setSchaden(int schaden) {
        this.schaden = schaden;
    }

    public int getAnimationsStufe() {
        return animationsStufe;
    }
}
