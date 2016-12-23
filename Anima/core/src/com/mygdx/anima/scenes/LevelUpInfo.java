package com.mygdx.anima.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

/**
 * Created by Steffen on 18.11.2016.
 */

public class LevelUpInfo implements Disposable {

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    public Playscreen screen;
    private Viewport viewport;
    private boolean geklickt;
    private Skin skin;
    //Scene2D widgets
    private float infoWidth,infoHeight;

    public LevelUpInfo(Playscreen screen, SpriteBatch sb,int level, int stark,int gesch,int zaub,int hp,int mana){
       this.screen=screen;
        infoWidth=(float)(AnimaRPG.W_WIDTH*2.5);
                infoHeight=(float)(AnimaRPG.W_Height*2.5);
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
        Label l1a=new Label("Stufe",skin);
        Label l1b=new Label("+"+level,skin);
        Label l1c=new Label("Staerke:",skin);
        Label l1d=new Label("+"+stark,skin);
        Label l2a=new Label("Lebenspunkte:",skin);
        Label l2b=new Label("+"+hp,skin);

        Label l2c=new Label("Geschicklichkeit:",skin);
        Label l2d=new Label("+"+gesch,skin);
        Label l3a=new Label("Mana:",skin);
        Label l3b=new Label("+"+mana,skin);
        Label l3c=new Label("Zauberkraft:",skin);
        Label l3d=new Label("+"+zaub,skin);

        Table table=new Table();
        table.add(l1a);table.add(l1b);table.add(l1c);table.add(l1d);
        table.row();
        table.add(l2a);table.add(l2b);table.add(l2c);table.add(l2d);
        table.row();
        table.add(l3a);table.add(l3b);table.add(l3c);table.add(l3d);
        dialog.add(table);
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
    public void update(float dt){
        if(geklickt==true){
            dispose();
        }}
    public void draw() {
        stage.draw();
    }
    @Override
    public void dispose()
    {   stage.clear();
        stage.dispose();}

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