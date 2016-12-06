package com.mygdx.anima.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.anima.screens.Inventar;
import com.mygdx.anima.sprites.character.items.Item;

/**
 * Created by Steffen on 04.12.2016.
 */

public class InventarListener extends InputListener {
    Item tempItem;
    Inventar inventar;
    Vector2 posi;
    Image img;
        public InventarListener(Inventar inv,Item paraItem, Image img){
            inventar=inv;
                    tempItem=paraItem;
        }
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            // Funktion wird nur ausgeführt, wenn es kein Swipe war
            if(posi.x-x<10 && posi.x-x>-10 && posi.y-y<10 && posi.y-y>-10){
            inventar.game.held.getHeldenInventar().resetAuswahl();
            // Vorrige Auswahl wird auf ausgewählt false gesetzt
            if(inventar.auswahlItem!=null)
                inventar.auswahlItem.setAusgewaehlt(false);
            inventar.auswahlItem=tempItem;

            if(tempItem.isAngelegt())
            {
                inventar.inventarRechts.clear();
                inventar.zeigeItems();
            }
            else if(tempItem.isAusgewaehlt())
            {   tempItem.setAusgewaehlt(false);
                inventar.inventarRechts.clear();
                inventar.zeigeItems();
            }
            else{
                tempItem.setAusgewaehlt(true);
                img=new Image(tempItem.getGrafik());
                inventar.inventarRechts.clear();
                inventar.zeigeItems();
            }
                inventar.inventarLinks.clear();
                inventar.auswahlAnzeige();}
        }
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                posi=new Vector2(x,y);
           // Gdx.app.log("eingang bei:"+ inventar.pane.getVisualScrollPercentY());
                inventar.setScrollbarposition(inventar.pane.getScrollPercentY());
                return true;

        }
}
