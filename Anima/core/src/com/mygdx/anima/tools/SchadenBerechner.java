package com.mygdx.anima.tools;

import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.HumanoideSprites;
import com.mygdx.anima.sprites.character.SchadenLabel;

import static com.mygdx.anima.AnimaRPG.held;

/**
 * Created by Steffen on 14.12.2016.
 */

public class SchadenBerechner {
    public static void  berechneSchaden(int schadensTyp, HumanoideSprites erleidender, HumanoideSprites verursacher){
        int verursachterSchaden;

        switch(schadensTyp)
        {
            case 1: //Nahkampf
                verursachterSchaden=verursacher.getSchadenNah()-(erleidender.getRuestung()/2); break;
            case 2: //Fernkampf
                verursachterSchaden=verursacher.getSchadenFern()-(erleidender.getRuestung()/3); break;
            case 3: //Zauberschaden
                verursachterSchaden=verursacher.getSchadenZauber()-erleidender.getZauberwiderstand(); break;
            default: verursachterSchaden=2;break;
        }
        if(verursachterSchaden<2)
        {
            verursachterSchaden=2;
        }
        new SchadenLabel(verursachterSchaden,erleidender,held.b2body.getPosition());
        erleidender.setCurrentHitpoints(erleidender.getCurrentHitpoints()-verursachterSchaden);
    }
}
