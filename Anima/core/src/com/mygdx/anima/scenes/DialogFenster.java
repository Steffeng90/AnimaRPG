package com.mygdx.anima.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.screens.actors.MyDialog;
import com.mygdx.anima.sprites.character.items.ItemSprite;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 18.11.2016.
 */

public class DialogFenster implements Disposable {

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    public Playscreen screen;
    private Viewport viewport;
    private boolean geklickt;
    private Skin skin;
    public float windowTimer;
    //Scene2D widgets
    private float infoWidth,infoHeight;
    private String nachfolger;
    public TextureRegion profileImage;
    public DialogFenster(final Playscreen screen, SpriteBatch sb, String nachfolger, String sprecher, String inhalt,String icon) {
        this.screen = screen;
        this.nachfolger=nachfolger;
        if(icon!=null)
        {
            profileImage=screen.getGame().getAssetManager().loadDialogIcon(icon);
        }
        // AnimaRPG.assetManager.get("audio/sounds/itemFund.wav", Sound.class).play();
        infoWidth = (float) (AnimaRPG.W_WIDTH * 2);
        infoHeight = (float) (AnimaRPG.W_Height * 2);
        viewport = new FitViewport(infoWidth, infoHeight, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        Gdx.input.setInputProcessor(stage);
        skin = screen.getGame().getAssetManager().getSkin();

        windowTimer = 0;
        geklickt = false;
        MyDialog dialog = new MyDialog(sprecher, skin, "dialog") {
            public void result(Object obj) {
                System.out.println("result " + obj);
            }
        };
        Label l1 = new Label(inhalt , skin);
        l1.setWrap(true);
        l1.setAlignment(Align.topLeft);
        if(profileImage!=null){
            Image img=new Image(profileImage); //new Image(getHeld().getDialogbild());
            img.setSize(48f,62f);
            dialog.getContentTable().add(img).size(48f,62f);
        }

        dialog.getContentTable().add(l1).size((infoWidth/2)-45f,infoHeight/5);
        dialog.setSize((infoWidth/1.8f)-20f,infoHeight/4);
        dialog.setPosition(infoWidth/5,0);
        stage.addActor(dialog);
        stage.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (windowTimer >= 0.4f) {
                    geklickt = true;
                    screen.getGame().getAssetManager().unloadDialogIcon();
                }
                return true;
            }});}

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

    public String getNachfolger() {
        return nachfolger;
    }

    public void setNachfolger(String nachfolger) {
        this.nachfolger = nachfolger;
    }
}