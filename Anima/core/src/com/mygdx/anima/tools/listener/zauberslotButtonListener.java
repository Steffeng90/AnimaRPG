package com.mygdx.anima.tools.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.anima.screens.menuReiter.GegenstandReiter;
import com.mygdx.anima.screens.menuReiter.ZauberReiter;
import com.mygdx.anima.sprites.character.items.Benutzbar;
import com.mygdx.anima.sprites.character.zauber.ZauberEntity;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 05.12.2016.
 */

public class zauberslotButtonListener extends InputListener {
    ZauberEntity zauber;
    ZauberReiter reiter;
    int slotNummer;

    public zauberslotButtonListener(ZauberEntity paraZauber, ZauberReiter reiter, int slotNummer){
        zauber=paraZauber;
        this.reiter=reiter;
        this.slotNummer=slotNummer;
    }
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        getHeld().getZauberList().setZauberslot(slotNummer,zauber);
        reiter.inventarRechts.clear();
        reiter.inventarLinks.clear();
        reiter.zeigeZauber();
        reiter.auswahlAnzeige();
        getHeld().updateAlleWerte();
        return true;
    }
}
