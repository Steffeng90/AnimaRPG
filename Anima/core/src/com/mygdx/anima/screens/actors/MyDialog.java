package com.mygdx.anima.screens.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.anima.AnimaRPG;

/**
 * Created by Steffen on 10.10.2017.
 */

public class MyDialog extends Dialog{
    public MyDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
        this.padTop(10f);
        this.padLeft(5f);
        this.padRight(5f);
        this.padBottom(5f);
    }
}
