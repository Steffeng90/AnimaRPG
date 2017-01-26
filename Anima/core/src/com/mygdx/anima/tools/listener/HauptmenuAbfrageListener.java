package com.mygdx.anima.tools.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.StartScreen;
import com.mygdx.anima.tools.HandleGameData;

/**
 * Created by Steffen on 05.12.2016.
 */

public class HauptmenuAbfrageListener extends InputListener {
    Dialog dialog;
    Skin skin;
    AnimaRPG game;
    // Typ1 ist Accept, 2  ist Decline;
    int typ;
    public HauptmenuAbfrageListener(Dialog dialog, Skin skin, AnimaRPG game,int typ){
    this.skin=skin;
        this.dialog=dialog;
        this.game=game;
        this.typ=typ;
    }
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        switch(typ){
            case 1: HandleGameData.speichern();game.changeScreen(new StartScreen(game));break;
            case 2: dialog.hide(); break;
        }



        //menu.stage.addActor(dialog);

        return true;
    }
}
