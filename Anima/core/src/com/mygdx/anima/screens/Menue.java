package com.mygdx.anima.screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.actors.ImageActor;
import com.mygdx.anima.screens.actors.MyActor;

/**
 * Created by Steffen on 23.11.2016.
 */

public class Menue implements Screen {
//    public class Menue extends ApplicationAdapter {
    Stage stage;
    MyActor myActor;
    ImageActor imageActor;

    public Menue(AnimaRPG game) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        myActor=new MyActor();
        imageActor=new ImageActor(new Texture(Gdx.files.internal("badlogic.jpg")));
        stage.addActor(myActor);
        stage.addActor(imageActor);
        // stage.setKeyboardFocus(myActor);
        stage.setKeyboardFocus(imageActor);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}