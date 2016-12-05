package com.mygdx.anima.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.actors.ImageActor;
import com.mygdx.anima.screens.actors.MyActor;
import com.mygdx.anima.sprites.character.items.Armor;
import com.mygdx.anima.sprites.character.items.Item;
import com.mygdx.anima.sprites.character.items.WaffeFern;
import com.mygdx.anima.sprites.character.items.WaffeNah;
import com.mygdx.anima.tools.InventarListener;
import com.mygdx.anima.tools.anlegeButtonListener;

import java.util.ArrayList;

/**
 * Created by Steffen on 24.11.2016.
 */

public class Inventar implements Screen {
    public AnimaRPG game;
    private Viewport viewport;
    private boolean zuSchliessen;
    Stage stage;
    MyActor myActor;
    ImageActor imageActor;
    Skin skin = new Skin(Gdx.files.internal("ui-skin/uiskin.json"));
    private float width, height;
    Item angelegtWaffeNah, angelegtWaffeFern, angelegtRuestung;
    public Item auswahlItem;
    private float scrollbarposition;
    public ScrollPane pane;
    public Table inventarLinks,inventarRechts;
    TextButton angelegtButton,ausziehenButton,wegwerfButton;
    public Inventar(final AnimaRPG game) {
        this.game = game;
        width = game.W_WIDTH * 2;
        height = game.W_Height * 2;
        this.viewport = new FitViewport(width, height, new OrthographicCamera());
        Gdx.app.log("BRreite und hoehe:", width + " " + height);
        //  BitmapFont bf=new BitmapFont(Gdx.files.internal("default.fnt"),true);
        stage = new Stage(viewport, game.batch);
        zuSchliessen = false;
        auswahlItem = null;
        scrollbarposition=0f;

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
        auswahlAnzeige();
//        stage.addActor(inventarLinks);

        pane = zeigeItems();

        //stage.addActor(pane);
        Gdx.input.setInputProcessor(stage);
    }

    public void zurueckButtonErzeugen(){
        Image inventarImg = new Image(new Texture("ui-skin/inventar.png"));
        inventarImg.setSize(50, 50);
        inventarImg.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {game.held.getHeldenInventar().resetAuswahl();game.closeScreen();dispose();}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}});
        inventarImg.setPosition(width - 50, height - 50);
        stage.addActor(inventarImg);
    }
    public void auswahlAnzeige(){
        inventarLinks = new Table(skin);

        inventarLinks.setWidth(stage.getWidth() / 5);
        inventarLinks.setPosition(width / 5, height);
        inventarLinks.align(Align.left | Align.top);
        Label aktWaffe = new Label("Angelegt:", skin);
        Label ausgwWaffe = new Label("Auswahl:", skin);
        if(auswahlItem!=null) {
        String name=" ",eigenschaft1=" ",wert1auswahl=" ",wert1angelegt=" ",eigenschaft2,wert2auswahl=" ",wert2angelegt=" ",nameAngelegt=" ";
        name=auswahlItem.getName();
        eigenschaft2="Wert";


        switch (Item.kategorie.valueOf(auswahlItem.getItemKategorie())) {
            case nahkampf:
                eigenschaft1="Schaden";
                wert1auswahl=" "+((WaffeNah)auswahlItem).getSchaden();
                wert2auswahl=" "+auswahlItem.getGoldWert();
                angelegtWaffeNah=game.held.getHeldenInventar().getAngelegtWaffeNah();
                if(angelegtWaffeNah!=null){
                nameAngelegt=angelegtWaffeNah.getName();
                wert1angelegt=" "+((WaffeNah)angelegtWaffeNah).getSchaden();
                wert2angelegt=" "+angelegtWaffeNah.getGoldWert();}
                break;
            case fernkampf:
                eigenschaft1="Fern-Schaden";
                wert1auswahl=" "+((WaffeFern)auswahlItem).getSchaden();
                wert2auswahl=" "+auswahlItem.getGoldWert();
                angelegtWaffeFern=game.held.getHeldenInventar().getAngelegtWaffeFern();
                if(angelegtWaffeFern!=null){
                nameAngelegt=angelegtWaffeFern.getName();
                wert1angelegt=" "+((WaffeFern)angelegtWaffeFern).getSchaden();
                wert2angelegt=" "+angelegtWaffeFern.getGoldWert();}
                break;
            case armor:
                eigenschaft1="Ruestungswert";
                wert1auswahl=" "+((Armor)auswahlItem).getRuestung();
                wert2auswahl=" "+auswahlItem.getGoldWert();
                angelegtRuestung=game.held.getHeldenInventar().getAngelegtRuestung();
                if(angelegtWaffeFern!=null){
                    nameAngelegt=angelegtWaffeFern.getName();
                    wert1angelegt=" "+((Armor)auswahlItem).getRuestung();
                wert2angelegt=" "+auswahlItem.getGoldWert();}
                break;
            default:
                break;
        }
        inventarLinks.add(aktWaffe).colspan(2);
        inventarLinks.row();
        inventarLinks.add(new Label(nameAngelegt, skin)).size(width * 3 / 20, height / 12f);
        inventarLinks.row();
        inventarLinks.add(new Label(eigenschaft1, skin)).size(width * 3 / 20, height / 12f);
        inventarLinks.add(new Label(wert1angelegt, skin)).size(width * 1 / 20, height / 12f);
        inventarLinks.row();
        inventarLinks.add(new Label(eigenschaft2, skin)).size(width * 3 / 20, height / 12f);
        inventarLinks.add(new Label(wert2angelegt, skin)).size(width * 1 / 20, height / 12f);
        inventarLinks.row();
        inventarLinks.add(ausgwWaffe).colspan(2);
        inventarLinks.row();
        inventarLinks.add(new Label(name, skin)).size(width * 3 / 20, height / 12f);
        inventarLinks.row();
        inventarLinks.add(new Label(eigenschaft1, skin)).size(width * 3 / 20, height / 12f);
        inventarLinks.add(new Label(wert1auswahl,skin)).size(width * 1 / 20, height / 12f);
        inventarLinks.row();
        inventarLinks.add(new Label(eigenschaft2, skin)).size(width * 3 / 20, height / 12f);
        inventarLinks.add(new Label(wert2auswahl, skin)).size(width * 1 / 20, height / 12f);
        inventarLinks.row();
        angelegtButton =new TextButton("Anlegen", skin);
        angelegtButton.addListener(new anlegeButtonListener(game,auswahlItem,this));
        inventarLinks.add(angelegtButton).size(width / 5, height / 6f).colspan(2);
        inventarLinks.row();
        inventarLinks.add(new TextButton("Wegwerfen", skin)).size(width / 5, height / 6f).colspan(2);
        inventarLinks.row();
        stage.addActor(inventarLinks);
        }}
    public ScrollPane zeigeItems() {
        inventarRechts = new Table();
        inventarRechts.setPosition(width * 2 / 5, height);
        inventarRechts.align(Align.left | Align.top);
        inventarRechts.add(new Label("Nahkampf-Waffen", skin)).colspan(3);
        inventarRechts.row();

        ArrayList<WaffeNah> liste = game.held.getHeldenInventar().getWaffenNahList();
        int size = liste.size();
        //Gdx.app.log("Size:"+size,"");
        for (int i = 0; i < size; i++) {
            if ((i) % 4 == 0) {inventarRechts.row();}
            Image beispiel4 = new Image(liste.get(i).getGrafik());
            beispiel4.addListener(new InventarListener(this, liste.get(i),beispiel4));
            inventarRechts.add(beispiel4).size(width / 10f, width / 10f);
        }
        inventarRechts.row();
        inventarRechts.add(new Label("Fernkampf-Waffen", skin)).colspan(3);
        inventarRechts.row();
        ArrayList<WaffeFern> listenf = game.held.getHeldenInventar().getWaffenFernList();
        size = listenf.size();
        for (int i = 0; i < size; i++) {
            if ((i) % 4 == 0) {
                inventarRechts.row();}
            Image beispiel4=new Image(listenf.get(i).getGrafik());
            beispiel4.addListener(new InventarListener(this, listenf.get(i),beispiel4));
            inventarRechts.add(beispiel4).size(width / 10f, width / 10f);
        }
        ScrollPane scrollPane = new ScrollPane(inventarRechts, skin);
        scrollPane.setBounds(0, 0, width / 2, height);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setPosition(width / 2, 0);
        scrollPane.setFadeScrollBars(true);
        scrollPane.setScrollbarsOnTop(false);
        scrollPane.setupFadeScrollBars(0, 0);
        scrollPane.layout();
        scrollPane.setScrollPercentY(getScrollbarposition());
        Gdx.app.log("Scroller erneuert","");
        scrollPane.layout();
        stage.addActor(scrollPane);
        pane=scrollPane;
        zurueckButtonErzeugen();

        return scrollPane;

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
    @Override
    public void resize(int width, int height) {viewport.update(width, height);}
    @Override
    public void pause() {    }
    @Override
    public void resume() {    }
    @Override
    public void hide() {    }
    @Override
    public void dispose() {    }
    @Override
    public void show() {    }

    public synchronized float getScrollbarposition() {
        return scrollbarposition;
    }

    public synchronized void setScrollbarposition(float scrollbarposition) {
        this.scrollbarposition = scrollbarposition;
    }
}