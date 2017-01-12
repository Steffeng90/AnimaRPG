package com.mygdx.anima.tools.listener;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.anima.screens.menuReiter.ZauberReiter;
import com.mygdx.anima.sprites.character.zauber.ZauberEntity;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 04.12.2016.
 */

public class ZauberListener extends InputListener {
    ZauberEntity tempZauber;
    ZauberReiter zauberReiter;
    Image img;
    Vector2 posi;

        public ZauberListener(ZauberReiter  zR, ZauberEntity paraZauber, Image img ){
            this.zauberReiter=zR;
            this.tempZauber=paraZauber;
            this.img=img;
        }
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            // Funktion wird nur ausgeführt, wenn es kein Swipe war
            if(posi.x-x<10 && posi.x-x>-10 && posi.y-y<10 && posi.y-y>-10){
                getHeld().getHeldenInventar().resetAuswahl();
            // Vorrige Auswahl wird auf ausgewählt false gesetzt
            if(zauberReiter.auswahlZauber !=null)
                zauberReiter.auswahlZauber.setAusgewaehlt(false);
            zauberReiter.auswahlZauber=tempZauber;
            if(tempZauber.isAusgewaehlt())
            {   tempZauber.setAusgewaehlt(false);}
            else{
                tempZauber.setAusgewaehlt(true);
                img=new Image(tempZauber.getGrafik());
            }
                zauberReiter.inventarRechts.clear();
                zauberReiter.zeigeZauber();
                zauberReiter.inventarLinks.clear();
                zauberReiter.auswahlAnzeige();}
        }
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                posi=new Vector2(x,y);
           // Gdx.app.log("eingang bei:"+ menu.pane.getVisualScrollPercentY());
                zauberReiter.setScrollbarposition(zauberReiter.pane.getScrollPercentY());
                return true;

        }
}
