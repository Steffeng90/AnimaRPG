package com.mygdx.anima.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Menu;
import com.mygdx.anima.screens.actors.MyButton;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 18.11.2016.
 */

public class Controller {
    AnimaRPG game;
    Viewport viewport;
    private Stage stage;
    public static boolean updateDurchfuehren;
    boolean upPressed,downPressed,leftPressed,rightPressed,
    meleePressed,bowPressed,castPressed, usePressed ;
    ImageButton weaponButton;
    ImageButton.ImageButtonStyle style;
    Table tableRechts;


    OrthographicCamera cam;
    private Touchpad touchpad;
    SpriteBatch batch;
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

        Table tableLeft=new Table();
        tableLeft.left().bottom();

        Image upImg=new Image(new Texture("ui-skin/pfeil_oben.png"));
        upImg.setSize(buttonSize,buttonSize);
        upImg.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed=false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed=true;
                return true;
            }
        });


        Image downImg=new Image(new Texture("ui-skin/pfeil_unten.png"));
        downImg.setSize(buttonSize,buttonSize);
        downImg.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed=false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed=true;
                return true;
            }
        });
        Image leftImg=new Image(new Texture("ui-skin/pfeil_links.png"));
        leftImg.setSize(buttonSize,buttonSize);
        leftImg.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed=false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed=true;
                return true;
            }
        });
        Image rightImg=new Image(new Texture("ui-skin/pfeil_rechts.png"));
        rightImg.setSize(buttonSize,buttonSize);
        rightImg.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed=false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed=true;
                return true;
            }
        });
        tableLeft.add();
        tableLeft.add(upImg).size(upImg.getWidth(),upImg.getHeight());
        tableLeft.add();
        tableLeft.row().pad(0,0,0,0);
        tableLeft.add(leftImg).size(leftImg.getWidth(),leftImg.getHeight());
        tableLeft.add();
        tableLeft.add(rightImg).size(rightImg.getWidth(),rightImg.getHeight());
        tableLeft.row().padBottom(0);
        tableLeft.add();
        tableLeft.add(downImg).size(downImg.getWidth(),downImg.getHeight());
        //tableLeft.add();
       // stage.addActor(tableLeft);

        /*Touchpad touchpad;
        Skin skin = new Skin(Gdx.files.internal("ui-skin/uiskin.json"));
        touchpad = new Touchpad(20f, skin);
        touchpad.setBounds(15, 15, 100, 100);
        tableLeft.add(touchpad);*/

        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
        //touchpadStyle.knob=skin.getDrawable("knob");
        //touchpadStyle.background=skin.getDrawable("background");

        //        touchpad = new Touchpad(10, touchpadStyle);

        touchpad = new Touchpad(10, skin);
        skin.add("touchBackground",new Texture("touchBackground.png"));
        touchpad.getStyle().background=skin.getDrawable("touchBackground");

        touchpad.setBounds(0, 0,60, 60);
       // touchpad.
        stage.addActor(touchpad);

        tableRechts=rechteTabelleZeichnen();
        stage.addActor((tableRechts));

        Image inventarImg=new Image(new Texture("ui-skin/inventar.png"));
        inventarImg.setSize(buttonSize,buttonSize);
        inventarImg.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.getScreen().pause();
                game.changeScreen(new Menu(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        inventarImg.setPosition(AnimaRPG.W_WIDTH-buttonSize,AnimaRPG.W_Height-buttonSize);
        stage.addActor(inventarImg);
    }
    public Table rechteTabelleZeichnen(){
        Table tableRight=new Table();

        Image meleeImg=new Image(new Texture("ui-skin/action_melee.png"));
        meleeImg.setSize(buttonSize,buttonSize);
        meleeImg.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                meleePressed=false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                meleePressed=true;
                return true;
            }
        });

        SpriteDrawable background = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ui-skin/button_vorlage.png"))));
        SpriteDrawable icon_up = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ui-skin/button_sword.png"))));
        SpriteDrawable icon_down = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ui-skin/action_use.png"))));

        // Test für Dynamische Buttons#
        if(getHeld().getHeldenInventar().getAngelegtWaffeNah()!=null) {
            style = new ImageButton.ImageButtonStyle();
            style.up = background;
            style.down = background;
            style.imageUp = new SpriteDrawable(new Sprite(getHeld().getHeldenInventar().getAngelegtWaffeNah().getGrafikButton()));
            style.imageDown = new SpriteDrawable(new Sprite(getHeld().getHeldenInventar().getAngelegtWaffeNah().getGrafikButton()));
            // style.unpressedOffsetY = -20; // to "not" center the icon
            // style.unpressedOffsetX = -30; // to "not" center the icon

            weaponButton = new ImageButton(style);
        }
        meleeImg.setSize(buttonSize,buttonSize);
        meleeImg.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                meleePressed=false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                meleePressed=true;
                return true;
            }
        });
        Image bowImg=new Image(new Texture("ui-skin/action_bow.png"));
        bowImg.setSize(buttonSize,buttonSize);
        bowImg.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bowPressed=false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bowPressed=true;
                return true;
            }
        });
        Image castImg=new Image(new Texture("ui-skin/action_cast.png"));
        castImg.setSize(buttonSize,buttonSize);
        castImg.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                castPressed=false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                castPressed=true;
                Gdx.app.log("Cast","");

                return true;

            }
        });
        Image useImg=new Image(new Texture("ui-skin/action_use.png"));
        useImg.setSize(buttonSize,buttonSize);
        useImg.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                usePressed=false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                usePressed=true;
                return true;
            }
        });
        //Pad bei 320 Breite
        //tableRight.padLeft(240);
        //Pad bei 360 Breite
        tableRight.padLeft(280);
        tableRight.add();
        tableRight.add(meleeImg).size(meleeImg.getWidth(),meleeImg.getHeight());
        if(getHeld().getHeldenInventar().getAngelegtWaffeNah()!=null){tableRight.add(weaponButton).size(meleeImg.getWidth(),meleeImg.getHeight());}
        else{tableRight.add();}
        tableRight.row().pad(0,0,0,0);
        tableRight.add(bowImg).size(bowImg.getWidth(),bowImg.getHeight());
        tableRight.add();
        tableRight.add(castImg).size(castImg.getWidth(),castImg.getHeight());
        tableRight.row().padBottom(0);
        tableRight.add();
        tableRight.add(useImg).size(useImg.getWidth(),useImg.getHeight());
        tableRight.add();
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

    public boolean isCastPressed() {
        return castPressed;
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
