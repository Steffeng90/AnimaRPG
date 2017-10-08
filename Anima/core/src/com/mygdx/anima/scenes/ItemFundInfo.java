package com.mygdx.anima.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.mygdx.anima.sprites.character.items.Item;
import com.mygdx.anima.sprites.character.items.ItemSprite;

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
        Dialog dialog = new Dialog("Gegenstand gefunden", skin, "dialog") {
            public void result(Object obj) {
                System.out.println("result " + obj);
            }
        };

        //img.setPosition(infoWidth *(3f/8f), (1f/3f)*infoHeight);
        //img.setSize(infoWidth / 3, 5*(infoHeight / 6));
       /* l1.setAlignment(Align.center);
        //dialogTable.align(Align.center);
        //dialogTable.setSize(infoWidth / 4f, infoHeight/3);
        //dialogTable.setPosition(infoWidth *(3f/8f), 0);
        /*dialogTable.setFillParent(true);

        dialogTable.add(img).size(infoWidth / 9f,infoWidth / 9f);//.align(Align.center);
        dialogTable.row();
        dialogTable.add(l1).size(infoWidth / 5f,25f);//.align(Align.center);
        dialogTable.center().bottom();

       // dialog.key(Input.Keys.ENTER, true); //sends "true" when the ENTER key is pressed
        //dialog.setSize(infoWidth / 4f, infoHeight/3);
       // dialog.setPosition(infoWidth *(3f/8f), 0);
        //dialog.align(Align.center);
*/
        Table dialogTable=new Table();
        Label l1 = new Label(item.name , skin);
        Image img=new Image(item.texture);
        img.setSize(80f,80f);

        dialog.add(img).size(80f,80f);
        //dialog.row();
        dialog.add(l1);
        //stage.addActor(img);
        dialog.show(stage);
        //stage.addActor(dialog);
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