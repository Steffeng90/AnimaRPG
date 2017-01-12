package com.mygdx.anima.screens.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class MyButton extends ImageButton
{

    public MyButton(Texture texture_up, Texture texture_down,Texture background)
    {
        super(new SpriteDrawable(new Sprite(texture_up)),
                new SpriteDrawable(new Sprite(texture_down)));

        //this.setBackground(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ui-skin/pfeil_unten.png")))));
        this.setBackground(new SpriteDrawable(new Sprite(texture_down)));
    }
}