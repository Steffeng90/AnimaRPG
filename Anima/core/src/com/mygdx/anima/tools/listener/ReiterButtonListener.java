package com.mygdx.anima.tools.listener;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.anima.screens.MenuScreen;
import com.mygdx.anima.screens.menuReiter.CharakterReiter;
import com.mygdx.anima.screens.menuReiter.GegenstandReiter;
import com.mygdx.anima.screens.menuReiter.InventarReiter;
import com.mygdx.anima.screens.menuReiter.QuestReiter;
import com.mygdx.anima.screens.menuReiter.ZauberReiter;

/**
 * Created by Steffen on 05.12.2016.
 */

public class ReiterButtonListener extends InputListener {
    MenuScreen menu;
    int auswahl;
    public ReiterButtonListener(MenuScreen menu, int auswahl){
        this.menu=menu;
        this.auswahl=auswahl;
    }
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        menu.game.getAssetManager().get("audio/sounds/reiter_wechsel.ogg", Sound.class).play(0.5f);
        menu.negiereAuswahlAllerReiter();
        menu.setAktiverReiter(auswahl);
        switch (auswahl){
            //Charakterreiter
            case 1:        menu.setAnzeigeGroup(new CharakterReiter(menu));break;
            case 2:        menu.setAnzeigeGroup(new ZauberReiter(menu));break;
            case 3:        menu.setAnzeigeGroup(new InventarReiter(menu));break;
            case 4:        menu.setAnzeigeGroup(new GegenstandReiter(menu));break;
            case 5:        menu.setAnzeigeGroup(new QuestReiter(menu));break;
        }
        menu.setChangeReiter(true);
        return true;
    }
}
