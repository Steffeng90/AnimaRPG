package com.mygdx.anima.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.screens.actors.MyDialog;

/**
 * Created by Steffen on 18.11.2016.
 */

public class QuestInfo implements Disposable {

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    public Playscreen screen;
    private Viewport viewport;
    private Skin skin;
    public float windowTimer,positionX,positionY;
    //Scene2D widgets
    private float infoWidth,infoHeight;
    private String nachfolger;
    public boolean readyForDispose;
    MyDialog dialog;

    public QuestInfo(final Playscreen screen, SpriteBatch sb, String questName, String questStatus) {
        this.screen = screen;
        // AnimaRPG.assetManager.get("audio/sounds/itemFund.wav", Sound.class).play();
        infoWidth = (float) (AnimaRPG.W_WIDTH * 2);
        infoHeight = (float) (AnimaRPG.W_Height * 2);
        positionY=-infoHeight / 6;
        positionX=infoWidth/3;
        viewport = new FitViewport(infoWidth, infoHeight, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        skin = screen.getGame().getAssetManager().getSkin();

        windowTimer = 0;
        dialog = new MyDialog(questStatus, skin, "default") {
            public void result(Object obj) {
                System.out.println("result " + obj);
            }
        };
        Label l1 = new Label(questName, skin);
        l1.setWrap(true);
//        l1.setAlignment(Align.topLeft);
        l1.setAlignment(Align.center);
        dialog.getContentTable().add(l1);//.size((infoWidth / 2) - 45f, infoHeight / 5);
        dialog.setSize(infoWidth/3f, infoHeight / 6);
        dialog.setPosition(positionX, positionY);

        //Mit dieser Methode kann das Fesnter auch aus der Stage fliegen.
        dialog.setKeepWithinStage(false);
        stage.addActor(dialog);
    }
    public void update(float dt){
        if(positionY<0 / 6 && windowTimer<=2){
            positionY+=2f;
        }else if( windowTimer<=2){
            windowTimer+=dt;
        }
        else{
            positionY-=2f;
        }
        dialog.setPosition(positionX,positionY);
        if(windowTimer>2 && positionY<-infoHeight / 6){
            this.readyForDispose=true;
        }
        }

    public void draw() {
        stage.act();
        stage.draw();
    }
    @Override
    public void dispose()
    {   stage.clear();
        stage.dispose();
    }

    public void resize(int width,int height){
        viewport.update(width,height);
    }

}