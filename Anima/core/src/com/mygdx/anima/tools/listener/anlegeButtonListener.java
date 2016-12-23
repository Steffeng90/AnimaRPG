package com.mygdx.anima.tools.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Menu;
import com.mygdx.anima.screens.menuReiter.InventarReiter;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.items.Armor;
import com.mygdx.anima.sprites.character.items.Item;
import com.mygdx.anima.sprites.character.items.WaffeFern;
import com.mygdx.anima.sprites.character.items.WaffeNah;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 05.12.2016.
 */

public class anlegeButtonListener extends InputListener {
    Item.kategorie temp;
    Held held;
    Item item;
    InventarReiter inv;

    public anlegeButtonListener(AnimaRPG game, Item item,InventarReiter inv){
        temp =Item.kategorie.valueOf(item.getItemKategorie());
        this.held=getHeld();
        this.item=item;
        this.inv=inv;
    }
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (item.isAngelegt()) {
            item.setAngelegt(false);
            switch(temp)
            {
                case nahkampf: held.getHeldenInventar().setAngelegtWaffeNah(null);break;
                case fernkampf: held.getHeldenInventar().setAngelegtWaffeFern(null);break;
                case armor: held.getHeldenInventar().setAngelegtRuestung(null);break;
                default: break;
            }}

            else{
        switch(temp)
        {
            case nahkampf: held.getHeldenInventar().setAngelegtWaffeNah((WaffeNah)item);break;
            case fernkampf: held.getHeldenInventar().setAngelegtWaffeFern((WaffeFern)item);break;
            case armor: held.getHeldenInventar().setAngelegtRuestung((Armor)item);break;
            default: break;
        }}
        inv.inventarRechts.clear();
        inv.zeigeItems();
        inv.inventarLinks.clear();
        inv.auswahlAnzeige();
        return super.touchDown(event, x, y, pointer, button);
    }
}
