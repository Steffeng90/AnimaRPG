package com.mygdx.anima.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.mygdx.anima.screens.MenuScreen;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 18.11.2016.
 */

public class Controller {
    AnimaRPG game;
    Viewport viewport;
    private Stage stage;
    public static boolean updateDurchfuehren,useUpdate,talkUpdate;
    public boolean upPressed,downPressed,leftPressed,rightPressed,
    meleePressed,bowPressed,cast1Pressed,cast2Pressed,cast3Pressed,cast4Pressed, usePressed,talkPressed ;
    ImageButton meleeButton,useButton,talkButton,bowButton,cast1Button,cast2Button,cast3Button,cast4Button;
    ImageButton.ImageButtonStyle style;
    Table tableRechts;

    OrthographicCamera cam;
    private Touchpad touchpad;
    public int buttonSize;
    Skin skin;
    SpriteDrawable background,background_aktiv;


    public Controller(final AnimaRPG game){
        cam=new OrthographicCamera();
        this.game=game;
        skin=game.getAssetManager().getSkin();
        viewport=new FitViewport(AnimaRPG.W_WIDTH,AnimaRPG.W_Height,cam);
        stage=new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
        buttonSize=25;
        updateDurchfuehren=false;

        background= new SpriteDrawable(new Sprite(game.getAssetManager().get("ui-skin/button_vorlage.png",Texture.class)));
        background_aktiv=new SpriteDrawable(new Sprite(game.getAssetManager().get("ui-skin/button_vorlage_aktiv.png",Texture.class)));
        touchpad = new Touchpad(10, skin);
        skin.add("touchBackground",game.getAssetManager().get("touchBackground.png"));
        touchpad.getStyle().background=skin.getDrawable("touchBackground");

        touchpad.setBounds(0, 0,60, 60);
        stage.addActor(touchpad);
        rechteTabelleDefinieren();
        tableRechts=rechteTabelleZeichnen();
        stage.addActor((tableRechts));

        Image inventarImg=new Image(game.getAssetManager().get("ui-skin/inventar.png",Texture.class));
        inventarImg.setSize(buttonSize,buttonSize);
        inventarImg.addListener(new InputListener(){
            @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.getAssetManager().get("audio/sounds/turn_page.wav", Sound.class).play();

                game.getScreen().pause();
                game.changeScreen(new MenuScreen(game));}
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;}});
        inventarImg.setPosition(AnimaRPG.W_WIDTH-buttonSize,AnimaRPG.W_Height-buttonSize);
        stage.addActor(inventarImg);
    }
    public void rechteTabelleDefinieren() {
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
            style.imageUp = new SpriteDrawable(new Sprite((new TextureRegion(game.getAssetManager().get("objekte/icons_for_rpg.png",Texture.class), 8 * 34, 29 * 34, 34, 34))));
            style.imageDown = new SpriteDrawable(new Sprite((new TextureRegion(game.getAssetManager().get("objekte/icons_for_rpg.png",Texture.class), 8 * 34, 29 * 34, 34, 34))));
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
        } else if (getHeld().npcInReichweite){
            tableRight.add(talkButton).size(buttonSize,buttonSize);
        }
        else {          tableRight.add().size(buttonSize,buttonSize);}
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
        else if(talkUpdate){
            talkUpdate=false;
            updateTalkButton();
            tableRechts.clear();
            tableRechts=rechteTabelleZeichnen();
            stage.addActor((tableRechts));
        }

    }
    //talkButton ist ausgelagert, weil sich Icon während dem game ständig verändern kann.
    public void updateTalkButton(){
        if(getHeld().npc!=null){
            style = new ImageButton.ImageButtonStyle();
            style.up = background;
            style.down = background_aktiv;style.imageUp = new SpriteDrawable(new Sprite((new TextureRegion(getHeld().npc.getProfilbild()))));
            style.imageDown = new SpriteDrawable(new Sprite((new TextureRegion(getHeld().npc.getProfilbild()))));
            talkButton = new ImageButton(style);
            talkButton.setSize(buttonSize, buttonSize);
            talkButton.addListener(new InputListener() {
                @Override public void touchUp(InputEvent event, float x, float y, int pointer, int button) {talkPressed = false;}
                @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {talkPressed = true;return true;}
            });
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
    public boolean isTalkPressed() {
        return talkPressed;
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
    public void reset(){
    getTouchpad().clearActions();
    }
}
