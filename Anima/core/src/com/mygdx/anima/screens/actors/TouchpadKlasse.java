package com.mygdx.anima.screens.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

/**
 * Created by Steffen on 14.12.2016.
 */

public class TouchpadKlasse {
        Stage stage;
        Touchpad touchpad;

        public void create () {
            stage = new Stage();
            Gdx.input.setInputProcessor(stage);

            Skin skin = new Skin(Gdx.files.internal("ui-skin/uiskin.json"));

            touchpad = new Touchpad(20f, skin);
            touchpad.setBounds(15, 15, 100, 100);
            stage.addActor(touchpad);
        }

        public void render () {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
        public void resize (int width, int height) {
            stage.getViewport().update(width, height, true);
        }
        public void dispose () {
            stage.dispose();
        }
    }