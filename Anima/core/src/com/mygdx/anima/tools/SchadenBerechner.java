package com.mygdx.anima.tools;

import com.badlogic.gdx.Gdx;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.HumanoideSprites;
import com.mygdx.anima.sprites.character.SchadenLabel;

import static com.mygdx.anima.AnimaRPG.getHeld;
import static com.mygdx.anima.screens.Playscreen.activeSchadenlabel;
import static com.mygdx.anima.screens.Playscreen.schadenlabelPool;

/**
 * Created by Steffen on 14.12.2016.
 */

public class SchadenBerechner {
    public static void  berechneSchaden(int schadensTyp, HumanoideSprites erleidender, HumanoideSprites verursacher){
        int verursachterSchaden;
        try {
            switch (schadensTyp) {
                // TODO wennn viele auf Held schlagen wird oft eine NPE erzeugen -> Treffen werden in Array eingearbeitet und ausgelesen / abgearbeitet
                case 1: //Nahkampf
                    verursachterSchaden = verursacher.getSchadenNah() - (int) (erleidender.getRuestung() / 2);
                    if (verursachterSchaden < 2) {
                        verursachterSchaden = 2;
                    }
                    break;
                case 2: //Fernkampf
                    verursachterSchaden = verursacher.getSchadenFern() - (int) (erleidender.getRuestung() / 3);
                    if (verursachterSchaden < 2) {
                        verursachterSchaden = 2;
                    }
                    break;
                case 3: //Zauberschaden
                    verursachterSchaden = verursacher.getSchadenZauber() - (int) (erleidender.getZauberwiderstand() / 2);
                    if (verursachterSchaden < 2) {
                        verursachterSchaden = 2;
                    }
                    break;
                case 4: //Heilung
                    verursachterSchaden = -(int) (verursacher.getSchadenZauber() * 1.5);
                    break;
                default:
                    Gdx.app.log("Defaul Scahden vergeben", "");
                    verursachterSchaden = 2;
                    break;
            }
//        Gdx.app.log("Wert",""+verursachterSchaden+" "+ erleidender.toString()+"body:"+ held.b2body.getPosition());
            //new SchadenLabel(verursachterSchaden, erleidender, getHeld().b2body.getPosition());
            SchadenLabel schadenLabel=schadenlabelPool.obtain();
            schadenLabel.init(verursachterSchaden, erleidender, getHeld().b2body.getPosition());
            activeSchadenlabel.add(schadenLabel);
            erleidender.setCurrentHitpoints(erleidender.getCurrentHitpoints() - verursachterSchaden);
        }
        catch(NullPointerException npe){
            Gdx.app.log("Fehler in Schadenberechner:",npe.getMessage());
        }
    }
}
