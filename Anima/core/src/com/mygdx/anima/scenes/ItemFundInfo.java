package com.mygdx.anima.scenes;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;

/**
 * Created by Steffen on 18.11.2016.
 */

public class ItemFundInfo implements Disposable {

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    public Playscreen screen;
    private Viewport viewport;
    private boolean geklickt;
    //Scene2D widgets
    private Label AnzeigeHPLabel;
    private Label AnzeigeMPLabel;

    public ItemFundInfo(final Playscreen screen, SpriteBatch sb,String itemName){
       this.screen=screen;
        viewport = new FitViewport(AnimaRPG.W_WIDTH, AnimaRPG.W_Height, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        Gdx.input.setInputProcessor(stage);

        geklickt=false;

        //define a table used to organize our hud's labels
        Table table = new Table();
        table.setTouchable(Touchable.enabled);
        table.bottom();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our labels using the String, and a Label style consisting of a font and color
        AnzeigeHPLabel = new Label(itemName+" gefunden!", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("ui-skin/gamefont.fnt")), Color.WHITE));
        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add().expandX().padTop(2);
        table.add(AnzeigeHPLabel).expandX();
        table.add().expandX().padTop(2);

        //add our table to the stage
        stage.addActor(table);

        stage.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                geklickt=true;

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Klicker erkannt","");
                geklickt=true;
                return true;
            }
        });
    }
    public void update(float dt){
        Gdx.app.log("Update","");

        if(geklickt==true){
//            screen.setCurrentGameState(Playscreen.GameState.RUN);
            Gdx.app.log("KNOWN","");
            dispose();
        }
 }
    public void draw() {
        stage.draw();
    }
    @Override
    public void dispose()
    { stage.clear();
        stage.dispose();
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