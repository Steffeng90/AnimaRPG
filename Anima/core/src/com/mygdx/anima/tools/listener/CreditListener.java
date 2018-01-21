package com.mygdx.anima.tools.listener;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.CreditScreen;
import com.mygdx.anima.screens.StartScreen;

/**
 * Created by Steffen on 05.12.2016.
 */

public class CreditListener extends InputListener {
    StartScreen startScreen;
    Skin skin;
    AnimaRPG game;
    public CreditListener(StartScreen startScreen, Skin skin, AnimaRPG game){
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
        super.touchUp(event, x, y, pointer, button);
        game.getAssetManager().get("audio/sounds/reiter_wechsel.ogg", Sound.class).play();
        game.changeScreen(new CreditScreen(game));
    }
}
