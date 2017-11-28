package com.mygdx.anima.tools.listener;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.anima.screens.Menu;
import com.mygdx.anima.screens.menuReiter.InventarReiter;
import com.mygdx.anima.sprites.character.items.Item;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 04.12.2016.
 */

public class InventarListener extends InputListener {
    Item tempItem;
    InventarReiter inv;
    Vector2 posi;
    Image img;
        public InventarListener(InventarReiter inv, Item paraItem, Image img){
            this.inv=inv;
                    tempItem=paraItem;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            // Funktion wird nur ausgeführt, wenn es kein Swipe war
            if(posi.x-x<10 && posi.x-x>-10 && posi.y-y<10 && posi.y-y>-10){
                getHeld().getHeldenInventar().resetAuswahl();
            // Vorrige Auswahl wird auf ausgewählt false gesetzt
            if(inv.auswahlItem!=null){
                inv.auswahlItem.setAusgewaehlt(false);}
            inv.auswahlItem=tempItem;
            if(tempItem.isAngelegt()){
                inv.inventarRechts.clear();
                inv.zeigeItems();
            }
            else if(tempItem.isAusgewaehlt())
            {   tempItem.setAusgewaehlt(false);
                inv.inventarRechts.clear();
                inv.zeigeItems();
            }
            else{
                tempItem.setAusgewaehlt(true);
                img=new Image(tempItem.getGrafik());
                inv.inventarRechts.clear();
                inv.zeigeItems();
            }
                inv.inventarLinks.clear();
                inv.auswahlAnzeige();}
        }
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                posi=new Vector2(x,y);
                inv.setScrollbarposition(inv.pane.getScrollPercentY());
                return true;

        }
}
