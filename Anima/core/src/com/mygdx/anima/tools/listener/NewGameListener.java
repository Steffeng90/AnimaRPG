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
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.screens.StartScreen;
import com.mygdx.anima.tools.HandleGameData;

/**
 * Created by Steffen on 05.12.2016.
 */

public class NewGameListener extends InputListener {
    StartScreen startScreen;
    Skin skin;
    AnimaRPG game;
    public NewGameListener(StartScreen startScreen, Skin skin, AnimaRPG game){
    this.skin=skin;
        this.startScreen=startScreen;
        this.game=game;
    }
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        game.getAssetManager().get("audio/sounds/reiter_wechsel.ogg", Sound.class).play();
        if(!HandleGameData.pruefeObSpielStandVorhanden()){
            game.changeScreen(new Playscreen(game));
        }
        else{
            game.getAssetManager().get("audio/sounds/reiter_wechsel.ogg", Sound.class).play(0.5f);
            Dialog dialog = new Dialog("Neues Spiel", skin, "dialog") {
                public void result(Object obj) {
                    System.out.println("result " + obj);
                }
            };
            Label label=new Label("Der alte Spielstand wird überschrieben.",skin);
            TextButton decline=new TextButton("Abbrechen",skin);
            TextButton accept=new TextButton("       Okay       ",skin);
            accept.setSize(decline.getWidth(),decline.getHeight());
            accept.addListener(new NewGameAbfrageListener(dialog,skin,game,1));
            decline.addListener(new NewGameAbfrageListener(dialog,skin,game,2));

            Table table=new Table();
            table.add(label).colspan(2);
            table.row();
            table.add(accept);
            table.add(decline);
            dialog.add(table);
            dialog.show(startScreen.stage);

        }
        super.touchUp(event, x, y, pointer, button);
    }
}
