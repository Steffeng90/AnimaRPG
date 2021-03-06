package com.mygdx.anima.tools.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.anima.screens.menuReiter.GegenstandReiter;
import com.mygdx.anima.sprites.character.items.Benutzbar;

/**
 * Created by Steffen on 05.12.2016.
 */

public class benutzenButtonListener extends InputListener {
    Benutzbar benutzbar;
    GegenstandReiter reiter;

    public benutzenButtonListener(Benutzbar paraItem, GegenstandReiter reiter){
        benutzbar=paraItem;
        this.reiter=reiter;

    }
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
       benutzbar.benutzen();

        reiter.inventarRechts.clear();
        reiter.inventarLinks.clear();
        reiter.zeigeItems();
        reiter.benutztAnzeige();
        return true;
    }
}
