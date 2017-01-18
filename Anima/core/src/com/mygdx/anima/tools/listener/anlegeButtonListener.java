package com.mygdx.anima.tools.listener;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.menuReiter.InventarReiter;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.items.Amulett;
import com.mygdx.anima.sprites.character.items.Brust;
import com.mygdx.anima.sprites.character.items.Handschuhe;
import com.mygdx.anima.sprites.character.items.Helm;
import com.mygdx.anima.sprites.character.items.Item;
import com.mygdx.anima.sprites.character.items.Schuhe;
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
            AnimaRPG.assetManager.get("audio/sounds/ausziehen.wav", Sound.class).play();

            item.setAngelegt(false);
            switch(temp)
            {
                case nahkampf: held.getHeldenInventar().setAngelegtWaffeNah(null);break;
                case fernkampf: held.getHeldenInventar().setAngelegtWaffeFern(null);break;
                case brust: held.getHeldenInventar().setAngelegtRuestung(null);break;
                case schuhe: held.getHeldenInventar().setAngelegtSchuhe(null);break;
                case handschuhe: held.getHeldenInventar().setAngelegtHandschuhe(null);break;
                case helm: held.getHeldenInventar().setAngelegtHelm(null);break;
                case amulett: held.getHeldenInventar().setAngelegtAmulett(null);break;
                default: break;
            }}

            else{
            AnimaRPG.assetManager.get("audio/sounds/anziehen.wav", Sound.class).play();

            switch(temp)
        {
            case nahkampf: held.getHeldenInventar().setAngelegtWaffeNah((WaffeNah)item);break;
            case fernkampf: held.getHeldenInventar().setAngelegtWaffeFern((WaffeFern)item);break;
            case brust: held.getHeldenInventar().setAngelegtRuestung((Brust)item);getHeld();break;
            case schuhe: held.getHeldenInventar().setAngelegtSchuhe((Schuhe)item);break;
            case handschuhe: held.getHeldenInventar().setAngelegtHandschuhe((Handschuhe)item);break;
            case helm: held.getHeldenInventar().setAngelegtHelm((Helm)item);break;
            case amulett: held.getHeldenInventar().setAngelegtAmulett((Amulett)item);break;
            default: break;
        }}
        getHeld().updateAlleWerte();
        inv.inventarRechts.clear();
        inv.zeigeItems();
        inv.inventarLinks.clear();
        inv.auswahlAnzeige();
        return super.touchDown(event, x, y, pointer, button);
    }
}
