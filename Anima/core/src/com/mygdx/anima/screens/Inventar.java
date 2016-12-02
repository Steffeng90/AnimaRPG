package com.mygdx.anima.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.actors.ImageActor;
import com.mygdx.anima.screens.actors.MyActor;

/**
 * Created by Steffen on 24.11.2016.
 */

public class Inventar implements Screen {
    private Viewport viewport;
    Stage stage;
    MyActor myActor;
    ImageActor imageActor;
    Skin skin = new Skin(Gdx.files.internal("ui-skin/uiskin.json"));
    private float width, height;

    public Inventar(AnimaRPG game) {
        width = game.W_WIDTH*2;
        height = game.W_Height*2;
        this.viewport = new FitViewport(width, height, new OrthographicCamera());
        Gdx.app.log("BRreite und hoehe:", width + " " + height);
      //  BitmapFont bf=new BitmapFont(Gdx.files.internal("default.fnt"),true);
        stage = new Stage(viewport, game.batch);

        Gdx.input.setInputProcessor(stage);
//        myActor=new MyActor();
        //imageActor=new ImageActor(new Texture(Gdx.files.internal("badlogic.jpg")));
        //       stage.addActor(myActor);
        // stage.addActor(imageActor);
        // stage.setKeyboardFocus(myActor);
//        stage.setKeyboardFocus(imageActor);

        /*Group reiterGroup=new Group();
        reiterGroup.addActor(new TextButton("StatReiter",skin));
        reiterGroup.addActor(new TextButton("HeldReiter",skin));
        reiterGroup.addActor(new TextButton("InventarReiter",skin));
*/

        Table reiterTable = new Table(skin);
        reiterTable.setWidth(width / 5);
        reiterTable.align(Align.left | Align.top);
        reiterTable.setPosition(0, height);
        reiterTable.add(new TextButton("StatReiter", skin)).size(width / 5f, height / 3f);
        reiterTable.row();
        reiterTable.add(new TextButton("HeldReiter", skin)).size(width / 5f, height / 3f);
        reiterTable.row();
        reiterTable.add(new TextButton("InventarReiter", skin)).size(width / 5f, height / 3f);
        stage.addActor(reiterTable);

        Group inventarGroup = new Group();
        Table inventarLinks = new Table(skin);

        // inventarLinks.setWidth(stage.getWidth()/5);
        inventarLinks.setPosition(width / 5, height);
        inventarLinks.align(Align.left | Align.top);
        Label aktWaffe = new Label("Aktuelle Waffe", skin);

        Label dieseWaffe = new Label("Diese Waffe", skin);
        Table mitte = new Table(skin);
        mitte.add(aktWaffe).width(width / 5);
        mitte.add(new Label("Schwert", skin));
        mitte.row();
        mitte.add(new Label("Schaden", skin)).uniform();
        mitte.add(new Label("10", skin)).uniform();
        mitte.row();
        mitte.add(new Label("Wert", skin));
        mitte.add(new Label("15 Gold", skin));
        mitte.setSize(width / 5, height / 3f);
        inventarLinks.add(aktWaffe).colspan(2);
        inventarLinks.row();
        //inventarLinks.add(oben).width(width / 5);
        inventarLinks.row();
        inventarLinks.add(dieseWaffe).colspan(2);
        inventarLinks.row();
        inventarLinks.add(mitte).width(width / 5);
        inventarLinks.row();
        inventarLinks.add(new TextButton("Benutzen", skin)).size(width / 5, height / 6f);
        inventarLinks.row();
        inventarLinks.add(new TextButton("Wegwerfen", skin)).size(width / 5, height / 6f);

        //inventarGroup.addActor(inventarLinks);
        stage.addActor(inventarLinks);
/*
        Table inventarRechts=new Table(skin);
        inventarRechts.setWidth((stage.getWidth()/5)*2);
        inventarRechts.setPosition((stage.getWidth()/5)*3,Gdx.graphics.getHeight());
        inventarRechts.align(Align.left |Align.top);
        inventarRechts.add(new Label("Nahkampf-Waffen",skin,"default"));
        inventarRechts.row();
        inventarRechts.add(new Label("Fernkampf-Waffen",skin,"default"));
        inventarRechts.row();

        inventarRechts.add(new Label("Rüstung",skin,"default"));
        stage.addActor(inventarRechts);
*/
        /*
        Table root = new Table();

        root.setFillParent(true);

        root.debug().defaults().space(6).size(110);
        stage.addActor(root);

        root.add(new Container(new Label("center",skin,"default")));
        root.add(new Container(new Label("top",skin,"default")).top());
        root.add(new Container(new Label("right",skin,"default")).right());
        root.add(new Container(new Label("bottom",skin,"default")).bottom());
        root.add(new Container(new Label("left",skin,"default")).left());
        root.row();
        root.add(new Container(new Label("fill",skin,"default")).fill());
        root.add(new Container(new Label("fillX",skin,"default")).fillX());
        root.add(new Container(new Label("fillY",skin,"default")).fillY());
        root.add(new Container(new Label("fill 75%",skin,"default")).fill(0.75f, 0.75f));
        root.add(new Container(new Label("fill 75% br",skin,"default")).fill(0.75f, 0.75f).bottom().right());
        root.row();
        root.add(new Container(new Label("padTop 5\ntop",skin,"default")).padTop(5).top());
        root.add(new Container(new Label("padBottom 5\nbottom",skin,"default")).padBottom(5).bottom());
        root.add(new Container(new Label("padLeft 15",skin,"default")).padLeft(15));
        root.add(new Container(new Label("pad 10 fill",skin,"default")).pad(10).fill());
        root.add(new Container(new Label("pad 10 tl",skin,"default")).pad(10).top().left());
        root.row();
        //root.add(new Container(new Label("bg",skin,"default")).background(logo));
       // root.add(new Container(new Label("bg height 50",skin,"default")).background(logo).height(50));

        Container transformBG = new Container(new Label("bg transform",skin,"default"));
        transformBG.setTransform(true);
        transformBG.setOrigin(55, 55);
        transformBG.rotateBy(90);
        root.add(transformBG);

        Container transform = new Container(new Label("transform",skin,"default"));
        transform.setTransform(true);
        transform.setOrigin(55, 55);
        transform.rotateBy(90);
        root.add(transform);

        Container clip = new Container(new Label("clip1clip2clip3clip4",skin,"default"));
        clip.setClip(true);
        root.add(clip);
        */
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

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

