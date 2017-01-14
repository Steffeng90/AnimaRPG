package com.mygdx.anima.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Menu;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 18.11.2016.
 */

public class Controller {
    AnimaRPG game;
    Viewport viewport;
    private Stage stage;
    public static boolean updateDurchfuehren,useUpdate;
    boolean upPressed,downPressed,leftPressed,rightPressed,
    meleePressed,bowPressed,cast1Pressed,cast2Pressed,cast3Pressed,cast4Pressed, usePressed ;
    ImageButton meleeButton,useButton,bowButton,cast1Button,cast2Button,cast3Button,cast4Button;
    ImageButton.ImageButtonStyle style;
    Table tableRechts;

    OrthographicCamera cam;
    private Touchpad touchpad;
    public int buttonSize;
    Skin skin=new Skin(Gdx.files.internal("ui-skin/uiskin.json"));


    public Controller(final AnimaRPG game){
        cam=new OrthographicCamera();
        this.game=game;
        viewport=new FitViewport(AnimaRPG.W_WIDTH,AnimaRPG.W_Height,cam);
        stage=new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
        buttonSize=25;
        updateDurchfuehren=false;

        touchpad = new Touchpad(10, skin);
        skin.add("touchBackground",new Texture("touchBackground.png"));
        touchpad.getStyle().background=skin.getDrawable("touchBackground");

        touchpad.setBounds(0, 0,60, 60);
        stage.addActor(touchpad);
        rechteTabelleDefinieren();
        tableRechts=rechteTabelleZeichnen();
        stage.addActor((tableRechts));

        Image inventarImg=new Image(new Texture("ui-skin/inventar.png"));
        inventarImg.setSize(buttonSize,buttonSize);
        inventarImg.addListener(new InputListener(){
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.getScreen().pause();
                game.changeScreen(new Menu(game));}
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;}});
        inventarImg.setPosition(AnimaRPG.W_WIDTH-buttonSize,AnimaRPG.W_Height-buttonSize);
        stage.addActor(inventarImg);
    }
    public void rechteTabelleDefinieren() {
        SpriteDrawable background = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ui-skin/button_vorlage.png"))));
        SpriteDrawable background_aktiv = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ui-skin/button_vorlage_aktiv.png"))));
        // Test für Dynamische Buttons#
        if (getHeld().getHeldenInventar().getAngelegtWaffeNah() != null) {
            style = new ImageButton.ImageButtonStyle();
            style.up = background;
            style.down = background_aktiv;
            style.imageUp = new SpriteDrawable(new Sprite(getHeld().getHeldenInventar().getAngelegtWaffeNah().getGrafikButton()));
            style.imageDown = new SpriteDrawable(new Sprite(getHeld().getHeldenInventar().getAngelegtWaffeNah().getGrafikButton()));
            meleeButton = new ImageButton(style);
            meleeButton.setSize(buttonSize, buttonSize);
            meleeButton.addListener(new InputListener() {
                @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {meleePressed = false;}
                @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {meleePressed = true;return true;}
            });}
        if (getHeld().getHeldenInventar().getAngelegtWaffeFern() != null) {
            style = new ImageButton.ImageButtonStyle();
            style.up = background;
            style.down = background_aktiv;
            style.imageUp = new SpriteDrawable(new Sprite(getHeld().getHeldenInventar().getAngelegtWaffeFern().getGrafikButton()));
            style.imageDown = new SpriteDrawable(new Sprite(getHeld().getHeldenInventar().getAngelegtWaffeFern().getGrafikButton()));
            bowButton = new ImageButton(style);
            bowButton.setSize(buttonSize, buttonSize);
            bowButton.addListener(new InputListener() {
                @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {bowPressed = false;}
                @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {bowPressed = true;return true;}
            });}
        if (getHeld().getZauberList().getZauberslot(1) != null) {
            style = new ImageButton.ImageButtonStyle();
            style.up = background;
            style.down = background_aktiv;
            style.imageUp = new SpriteDrawable(new Sprite(getHeld().getZauberList().getZauberslot(1).getSlotGrafik()));
            style.imageDown = new SpriteDrawable(new Sprite(getHeld().getZauberList().getZauberslot(1).getSlotGrafik()));
            cast1Button = new ImageButton(style);
            cast1Button.setSize(buttonSize, buttonSize);
            cast1Button.addListener(new InputListener() {
                @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {cast1Pressed = false;}
                @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {cast1Pressed = true;return true;}
            });}
        if (getHeld().getZauberList().getZauberslot(2) != null) {
            style = new ImageButton.ImageButtonStyle();
            style.up = background;
            style.down = background_aktiv;
            style.imageUp = new SpriteDrawable(new Sprite(getHeld().getZauberList().getZauberslot(2).getSlotGrafik()));
            style.imageDown = new SpriteDrawable(new Sprite(getHeld().getZauberList().getZauberslot(2).getSlotGrafik()));
            cast2Button = new ImageButton(style);
            cast2Button.setSize(buttonSize, buttonSize);
            cast2Button.addListener(new InputListener() {
                @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {cast2Pressed = false;}
                @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {cast2Pressed = true;return true;}
            });}
        if (getHeld().getZauberList().getZauberslot(3) != null) {
            style = new ImageButton.ImageButtonStyle();
            style.up = background;
            style.down = background_aktiv;
            style.imageUp = new SpriteDrawable(new Sprite(getHeld().getZauberList().getZauberslot(3).getSlotGrafik()));
            style.imageDown = new SpriteDrawable(new Sprite(getHeld().getZauberList().getZauberslot(3).getSlotGrafik()));
            cast3Button = new ImageButton(style);
            cast3Button.setSize(buttonSize, buttonSize);
            cast3Button.addListener(new InputListener() {
                @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {cast3Pressed = false;}
                @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {cast3Pressed = true;return true;}
            });}
        if (getHeld().getZauberList().getZauberslot(4) != null) {
            style = new ImageButton.ImageButtonStyle();
            style.up = background;
            style.down = background_aktiv;
            style.imageUp = new SpriteDrawable(new Sprite(getHeld().getZauberList().getZauberslot(4).getSlotGrafik()));
            style.imageDown = new SpriteDrawable(new Sprite(getHeld().getZauberList().getZauberslot(4).getSlotGrafik()));
            cast4Button = new ImageButton(style);
            cast4Button.setSize(buttonSize, buttonSize);
            cast4Button.addListener(new InputListener() {
                @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {cast4Pressed = false;}
                @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {cast4Pressed = true;return true;}
            });}
            if(true){
            style = new ImageButton.ImageButtonStyle();
            style.up = background;
            style.down = background_aktiv;
            style.imageUp = new SpriteDrawable(new Sprite((new TextureRegion(new Texture("objekte/icons_for_rpg.png"), 8 * 34, 29 * 34, 34, 34))));
            style.imageDown = new SpriteDrawable(new Sprite((new TextureRegion(new Texture("objekte/icons_for_rpg.png"), 8 * 34, 29 * 34, 34, 34))));
            useButton = new ImageButton(style);
            useButton.setSize(buttonSize, buttonSize);
            useButton.addListener(new InputListener() {
                @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {usePressed = false;}
                @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {usePressed = true;return true;}
            });}
    }
    public Table rechteTabelleZeichnen(){
        Table tableRight = new Table();
        //Pad bei 320 Breite
        //tableRight.padLeft(240);
        //Pad bei 360 Breite
        tableRight.padLeft(270);
        tableRight.row().pad(5,0,5,5);
        tableRight.add().size(buttonSize,buttonSize);;
        tableRight.add().size(buttonSize,buttonSize);;

        if (getHeld().getZauberList().getZauberslot(4)!=null) {
            tableRight.add(cast4Button).size(buttonSize,buttonSize);
        } else {tableRight.add().size(buttonSize,buttonSize);}
        tableRight.row().pad(5,0,5,5);
        tableRight.add().size(buttonSize,buttonSize);;
        tableRight.add().size(buttonSize,buttonSize);;
        if (getHeld().getZauberList().getZauberslot(3)!=null) {
            tableRight.add(cast3Button).size(buttonSize,buttonSize);
        } else {tableRight.add().size(buttonSize,buttonSize);}
        tableRight.row().pad(5,0,5,5);
        tableRight.add().size(buttonSize,buttonSize);;
        tableRight.add().size(buttonSize,buttonSize);;

        if (getHeld().getZauberList().getZauberslot(2)!=null) {
            tableRight.add(cast2Button).size(buttonSize,buttonSize);
        } else {tableRight.add().size(buttonSize,buttonSize);}
        tableRight.row().pad(5,0,5,5);
        tableRight.add().size(buttonSize,buttonSize);;
        tableRight.add().size(buttonSize,buttonSize);;

        if (getHeld().getZauberList().getZauberslot(1)!=null) {
            tableRight.add(cast1Button).size(buttonSize,buttonSize);
        } else {        tableRight.add().size(buttonSize,buttonSize);;
        }
        // Platz für SpeerAttacke
        tableRight.row().pad(5,0,5,5);
        if (getHeld().objectInReichweite) {
            tableRight.add(useButton).size(buttonSize,buttonSize);
        } else {        tableRight.add().size(buttonSize,buttonSize);}
        if (getHeld().getHeldenInventar().getAngelegtWaffeFern() != null) {
            tableRight.add(bowButton).size(buttonSize,buttonSize);
        } else {        tableRight.add().size(buttonSize,buttonSize);}
        if (getHeld().getHeldenInventar().getAngelegtWaffeNah() != null) {
            tableRight.add(meleeButton).size(buttonSize,buttonSize);
        } else {        tableRight.add().size(buttonSize,buttonSize);}
        tableRight.left().bottom();
        return tableRight;
    }
    public void draw(){
        stage.draw();
    }
    public void update(){
        if(updateDurchfuehren){
            updateDurchfuehren=false;
            tableRechts.clear();
            rechteTabelleDefinieren();
            tableRechts=rechteTabelleZeichnen();
            stage.addActor((tableRechts));
        }
        else if(useUpdate){
            useUpdate=false;
            tableRechts.clear();
            tableRechts=rechteTabelleZeichnen();
            stage.addActor((tableRechts));
        }
    }
    public boolean isUpPressed() {
        return upPressed;
    }
    public boolean isDownPressed() {
        return downPressed;
    }
    public boolean isLeftPressed() {
        return leftPressed;
    }
    public boolean isRightPressed() {
        return rightPressed;
    }
    public void resize(int width,int height){
        viewport.update(width,height);
    }

    public boolean isMeleePressed() {
        return meleePressed;
    }

    public boolean isBowPressed() {
        return bowPressed;
    }

    public boolean isCast1Pressed() {
        return cast1Pressed;
    }
    public boolean isCast2Pressed() {
        return cast2Pressed;
    }
    public boolean isCast3Pressed() {
        return cast3Pressed;
    }
    public boolean isCast4Pressed() {
        return cast4Pressed;
    }
    public boolean isUsePressed() {
        return usePressed;
    }

    public void dispose(){
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public Touchpad getTouchpad() {
        return touchpad;
    }
    public void setTouchpad(Touchpad touchpad) {
        this.touchpad = touchpad;
    }
}
