package com.mygdx.anima.screens.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

/**
 * Created by Steffen on 23.11.2016.
 */

public class MyActor extends Actor {
    Sprite sprite=new Sprite(new Texture(Gdx.files.internal("badlogic.jpg")));

    public MyActor(){
        setBounds(sprite.getX(),sprite.getY(),sprite.getWidth(),sprite.getHeight());
        setTouchable(Touchable.enabled);
        addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.RIGHT){
                    MoveByAction mba=new MoveByAction();
                    mba.setAmount(100f,0f);
                    mba.setDuration(5f);

                    MyActor.this.addAction(mba);
                }
                return true;
            }
        });
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);    }

    @Override
    protected void positionChanged() {
        sprite.setPosition(getX(),getY());
        super.positionChanged();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
