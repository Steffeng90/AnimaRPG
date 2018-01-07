package com.mygdx.anima.tools.listener;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.MenuScreen;

/**
 * Created by Steffen on 05.12.2016.
 */

public class HauptmenuListener extends InputListener {
    MenuScreen menu;
    Skin skin;
    AnimaRPG game;
    public HauptmenuListener(MenuScreen menu, Skin skin, AnimaRPG game){
    this.skin=skin;
        this.menu=menu;
        this.game=game;
    }
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        game.getAssetManager().get("audio/sounds/reiter_wechsel.ogg", Sound.class).play(0.5f);
        Dialog dialog = new Dialog("Hauptmenu", skin, "dialog") {
            public void result(Object obj) {
                System.out.println("result " + obj);
            }
        };
        Label label=new Label("Zurueck zum Hauptmenue?",skin);
        TextButton decline=new TextButton("Abbrechen",skin);
        TextButton accept=new TextButton("       Ja       ",skin);
        accept.setSize(decline.getWidth(),decline.getHeight());
        accept.addListener(new HauptmenuAbfrageListener(dialog,skin,game,1));
        decline.addListener(new HauptmenuAbfrageListener(dialog,skin,game,2));

        Table table=new Table();
        table.add(label).colspan(2);
        table.row();
        table.add(accept);
        table.add(decline);
        dialog.add(table);
        dialog.show(menu.stage);

        return true;
    }
}
