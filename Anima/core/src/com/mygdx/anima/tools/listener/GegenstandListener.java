package com.mygdx.anima.tools.listener;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.anima.screens.menuReiter.GegenstandReiter;
import com.mygdx.anima.screens.menuReiter.InventarReiter;
import com.mygdx.anima.sprites.character.items.Benutzbar;
import com.mygdx.anima.sprites.character.items.Item;

/**
 * Created by Steffen on 04.12.2016.
 */

public class GegenstandListener extends InputListener {
    Benutzbar tempItem;
    GegenstandReiter inv;
    Image img;
    Vector2 posi;

        public GegenstandListener(GegenstandReiter inv, Benutzbar paraItem, Image img ){
            this.inv=inv;
            this.tempItem=paraItem;
            this.img=img;
        }
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            // Funktion wird nur ausgeführt, wenn es kein Swipe war
            if(posi.x-x<10 && posi.x-x>-10 && posi.y-y<10 && posi.y-y>-10){
            inv.menu.game.held.getHeldenInventar().resetAuswahl();
            // Vorrige Auswahl wird auf ausgewählt false gesetzt
            if(inv.auswahlItem!=null)
                inv.auswahlItem.setAusgewaehlt(false);
            inv.auswahlItem=tempItem;
            if(tempItem.isAusgewaehlt())
            {   tempItem.setAusgewaehlt(false);}
            else{
                tempItem.setAusgewaehlt(true);
                img=new Image(tempItem.getGrafik());
            }
                inv.inventarRechts.clear();
                inv.zeigeItems();
                inv.inventarLinks.clear();
                inv.auswahlAnzeige();}
        }
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                posi=new Vector2(x,y);
           // Gdx.app.log("eingang bei:"+ menu.pane.getVisualScrollPercentY());
                inv.setScrollbarposition(inv.pane.getScrollPercentY());
                return true;

        }
}
