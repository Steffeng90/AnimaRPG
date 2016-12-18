package com.mygdx.anima.tools.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Menu;
import com.mygdx.anima.screens.menuReiter.CharakterReiter;
import com.mygdx.anima.screens.menuReiter.GegenstandReiter;
import com.mygdx.anima.screens.menuReiter.InventarReiter;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.items.Armor;
import com.mygdx.anima.sprites.character.items.Item;
import com.mygdx.anima.sprites.character.items.WaffeFern;
import com.mygdx.anima.sprites.character.items.WaffeNah;

/**
 * Created by Steffen on 05.12.2016.
 */

public class ReiterButtonListener extends InputListener {
    Menu menu;
    int auswahl;
    public ReiterButtonListener(Menu menu,int auswahl){
        this.menu=menu;
        this.auswahl=auswahl;
    }
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        switch (auswahl){
            //Charakterreiter
            case 1:        menu.setAnzeigeGroup(new CharakterReiter(menu));break;
            case 2:        menu.setAnzeigeGroup(null);break;
            case 3:        menu.setAnzeigeGroup(new InventarReiter(menu));break;
            case 4:        menu.setAnzeigeGroup(new GegenstandReiter(menu));break;
        }
        menu.setChangeReiter(true);
        return true;
    }
}
