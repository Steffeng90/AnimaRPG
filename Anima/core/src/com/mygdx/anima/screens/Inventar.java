package com.mygdx.anima.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.actors.ImageActor;
import com.mygdx.anima.screens.actors.MyActor;

/**
 * Created by Steffen on 24.11.2016.
 */

public class Inventar implements Screen {
    Stage stage;
    MyActor myActor;
    ImageActor imageActor;
    Skin skin=new Skin(Gdx.files.internal("ui-skin/uiskin.json"));
 //   skin.addRegions("ui-skin/uiskin.atlas");

    public Inventar(AnimaRPG game) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
//        myActor=new MyActor();
        imageActor=new ImageActor(new Texture(Gdx.files.internal("badlogic.jpg")));
 //       stage.addActor(myActor);
        stage.addActor(imageActor);
        // stage.setKeyboardFocus(myActor);
//        stage.setKeyboardFocus(imageActor);

        /*Group reiterGroup=new Group();
        reiterGroup.addActor(new TextButton("StatReiter",skin));
        reiterGroup.addActor(new TextButton("HeldReiter",skin));
        reiterGroup.addActor(new TextButton("InventarReiter",skin));
*/
        Table reiterTable=new Table();
        reiterTable.setWidth(stage.getWidth()/4);
        reiterTable.align(Align.left |Align.top);
        reiterTable.setPosition(0,Gdx.graphics.getHeight());
        reiterTable.add(new TextButton("StatReiter",skin,"default")).size(Gdx.graphics.getWidth()/5f,(Gdx.graphics.getHeight()/3f));

        reiterTable.row();
        reiterTable.add(new TextButton("HeldReiter",skin,"default")).size(Gdx.graphics.getWidth()/5,(Gdx.graphics.getHeight()/3));
        reiterTable.row();
        reiterTable.add(new TextButton("InventarReiter",skin,"default")).size(Gdx.graphics.getWidth()/5,(Gdx.graphics.getHeight()/3));
        stage.addActor(reiterTable);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
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
