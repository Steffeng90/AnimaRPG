package com.mygdx.anima.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.screens.actors.MyDialog;
import com.mygdx.anima.sprites.character.items.ItemSprite;

/**
 * Created by Steffen on 18.11.2016.
 */

public class ItemFundInfo implements Disposable {

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    public Playscreen screen;
    private Viewport viewport;
    private boolean geklickt;
    private Skin skin;
    public float windowTimer;
    //Scene2D widgets
    private float infoWidth,infoHeight;

    public ItemFundInfo(final Playscreen screen, SpriteBatch sb,ItemSprite item) {
        this.screen = screen;
        this.screen.getGame().getAssetManager().get("audio/sounds/itemFund.wav", Sound.class).play(0.5f);
        infoWidth = (float) (AnimaRPG.W_WIDTH * 2);
        infoHeight = (float) (AnimaRPG.W_Height * 2);
        viewport = new FitViewport(infoWidth, infoHeight, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("ui-skin/uiskin.json"));

        windowTimer = 0;
        geklickt = false;
        MyDialog dialog = new MyDialog("Gegenstand gefunden", skin, "dialog") {
            public void result(Object obj) {
            }
        };

        Table dialogTable=new Table();
        Label l1 = new Label(item.name , skin);
        Label l2 = new Label(item.name , skin);
        Label l3 = new Label(item.name , skin);
        Image img=new Image(item.texture);
        img.setSize(80f,80f);

        dialogTable.add(l1);
        dialogTable.add(img).size(80f,80f).padTop(5f);
        //dialog.add(img).size(80f,80f).padTop(5f);
        dialog.add(dialogTable);
        dialog.show(stage);
        stage.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //geklickt = true;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (windowTimer >= 0.4f) {
                    geklickt = true;
                }
                return true;
            }
        });
    }
    public void update(float dt){
        windowTimer+=dt;
        }

    public void draw() {
        stage.draw();
    }
    @Override
    public void dispose()
    {   stage.clear();
        stage.dispose();;
    }

    public void resize(int width,int height){
        viewport.update(width,height);
    }
    public boolean isGeklickt() {
        return geklickt;
    }
}