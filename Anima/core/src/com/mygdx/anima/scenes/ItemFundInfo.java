package com.mygdx.anima.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.screens.actors.SteffensDialog;
import com.mygdx.anima.screens.actors.UITest;
import com.mygdx.anima.sprites.character.Held;

import static com.badlogic.gdx.Input.Keys.M;

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
    //Scene2D widgets
    private float infoWidth,infoHeight;

    public ItemFundInfo(final Playscreen screen, SpriteBatch sb,String itemName){
       this.screen=screen;
        infoWidth=(float)(AnimaRPG.W_WIDTH*2);
                infoHeight=(float)(AnimaRPG.W_Height*2);
        viewport = new FitViewport(infoWidth,infoHeight, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        Gdx.input.setInputProcessor(stage);
        skin=new Skin(Gdx.files.internal("ui-skin/uiskin.json"));

        geklickt=false;

        Dialog dialog = new Dialog("", skin, "dialog") {
            public void result(Object obj) {
                System.out.println("result "+obj);
            }
        };
        Label l1=new Label(itemName+"\n gefunden!",skin);
        l1.setAlignment(Align.center);
        dialog.text(l1);
        dialog.key(Input.Keys.ENTER, true); //sends "true" when the ENTER key is pressed
        dialog.setSize(infoWidth/3,infoHeight/3);
        dialog.setPosition(infoWidth/3,0);
        dialog.align(Align.center);

        stage.addActor(dialog);
        stage.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                geklickt=true;}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Klicker erkannt","");
                geklickt=true;
                return true;}
        });
    }
   // public void update(float dt){
        /*if(geklickt==true){
            geklickt=false;
            dispose();
        }*/
   // }
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
    public void setGeklickt(boolean geklickt) {
        this.geklickt = geklickt;
    }
}