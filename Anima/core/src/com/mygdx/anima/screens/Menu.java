package com.mygdx.anima.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.menuReiter.CharakterReiter;
import com.mygdx.anima.screens.menuReiter.GegenstandReiter;
import com.mygdx.anima.screens.menuReiter.InventarReiter;
import com.mygdx.anima.screens.menuReiter.ZauberReiter;
import com.mygdx.anima.tools.Controller;
import com.mygdx.anima.tools.listener.HauptmenuListener;
import com.mygdx.anima.tools.listener.ReiterButtonListener;

import java.awt.Color;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 24.11.2016.
 */

public class Menu implements Screen {
    public AnimaRPG game;
    private Viewport viewport;
    public Stage stage;
    Group anzeigeGroup;
    private boolean changeReiter;
    private Skin skin;
    private float width, height,reiterWidth;
    public GegenstandReiter ggstReiter;
    public InventarReiter invReiter;
    public ZauberReiter zauberReiter;

    public int getAktiverReiter() {
        return aktiverReiter;
    }

    public void setAktiverReiter(int aktiverReiter) {
        if (aktiverReiter>1 && aktiverReiter<6) {
            this.aktiverReiter = aktiverReiter;

        }
        else {
            this.aktiverReiter=1;
        }
    }

    private int aktiverReiter;
    public Menu(final AnimaRPG game) {
        this.game = game;
        skin =game.getAssetManager().getSkin();
        width = game.W_WIDTH * 2;
        height = game.W_Height * 2;
        reiterWidth=width*2/10;
        changeReiter=false;
        aktiverReiter=1;
        negiereAuswahlAllerReiter();

        this.viewport = new FitViewport(width, height, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        stage.addActor(reiterButtonsErzeugen());
        stage.addActor(new CharakterReiter(this));
        stage.addActor(zurueckButtonErzeugen());
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void render(float delta) {
        getHeld().setSpielzeit(delta);
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(isChangeReiter() && anzeigeGroup!=null){
            stage.clear();
            stage.addActor(reiterButtonsErzeugen());
            stage.addActor(anzeigeGroup);setChangeReiter(false);
            stage.addActor(zurueckButtonErzeugen());
        }
        stage.act();
        stage.draw();
    }
    public Image zurueckButtonErzeugen(){
        Image inventarImg = new Image(game.getAssetManager().get("ui-skin/inventar.png",Texture.class));
        inventarImg.setSize(50, 50);
        inventarImg.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Controller.updateDurchfuehren=true;
                game.getAssetManager().get("audio/sounds/turn_page.wav", Sound.class).play();
                getHeld().getHeldenInventar().resetAuswahl();game.closeScreen();dispose();}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}});
        inventarImg.setPosition(width - 50, height - 50);
        return inventarImg;
    }
    public Table reiterButtonsErzeugen(){
        Table reiterTable = new Table(skin);
        reiterTable.setWidth(reiterWidth);
        reiterTable.align(Align.left | Align.top);
        reiterTable.setPosition(0, height);
        //Button erzeugen
        TextButton charakterReiterButton= new TextButton("Charakter", skin);
        TextButton skillReiterButton= new TextButton("Zauber", skin);
        TextButton inventarReiterButton= new TextButton("Ausruestung", skin);
        TextButton nutzbareItemsReiterButton= new TextButton("Verwendbares", skin);
        TextButton hautpmenueButton=new TextButton("Hauptmenue", skin);

        //Listener hinzufügen
        charakterReiterButton.addListener(new ReiterButtonListener(this,1));
        skillReiterButton.addListener(new ReiterButtonListener(this,2));
        inventarReiterButton.addListener(new ReiterButtonListener(this,3));
        nutzbareItemsReiterButton.addListener(new ReiterButtonListener(this,4));
        hautpmenueButton.addListener(new HauptmenuListener(this,skin,game));

        // Farblich markieren

        switch(getAktiverReiter()){
            case 1: charakterReiterButton.setColor(com.badlogic.gdx.graphics.Color.OLIVE);break;
            case 2: skillReiterButton.setColor(com.badlogic.gdx.graphics.Color.OLIVE);break;
            case 3: inventarReiterButton.setColor(com.badlogic.gdx.graphics.Color.OLIVE);break;
            case 4: nutzbareItemsReiterButton.setColor(com.badlogic.gdx.graphics.Color.OLIVE);break;
            default: break;
        }

        //Zur Tabelle hinzufügen
        reiterTable.add(charakterReiterButton).size(reiterWidth, height / 5f);
        reiterTable.row();
        reiterTable.add(skillReiterButton).size(reiterWidth, height / 5f);
        reiterTable.row();
        reiterTable.add(inventarReiterButton).size(reiterWidth, height / 5f);
        reiterTable.row();
        reiterTable.add(nutzbareItemsReiterButton).size(reiterWidth, height / 5f);
        reiterTable.row();
        reiterTable.add(hautpmenueButton).size(reiterWidth, height / 5f);
        return reiterTable;
    }
    public Skin getSkin() {return skin;}
    public float getWidth() {return width;}
    public void setWidth(float width) {this.width = width;}
    public float getHeight() {return height;}
    public void setHeight(float height) {this.height = height;}
    public Group getAnzeigeGroup() {return anzeigeGroup;}
    public void setAnzeigeGroup(Group anzeigeGroup) {this.anzeigeGroup = anzeigeGroup;}
    public boolean isChangeReiter() {return changeReiter;}
    public void setChangeReiter(boolean changeReiter) {this.changeReiter = changeReiter;}

    public void negiereAuswahlAllerReiter(){
        getHeld().getZauberList().resetAuswahl();
        getHeld().getHeldenInventar().resetAuswahl();
    }
    @Override public void resize(int width, int height) {viewport.update(width, height);}
    @Override public void pause() {    }
    @Override public void resume() {    }
    @Override public void hide() {    }
    @Override public void dispose() {
        //TODO richtiges Dispose einbauen
        stage.dispose();
    }
    @Override public void show() {    }
    }
