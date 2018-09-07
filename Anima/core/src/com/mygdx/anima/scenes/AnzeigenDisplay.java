package com.mygdx.anima.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.sprites.character.Held;

/**
 * Created by Steffen on 18.11.2016.
 */

public class AnzeigenDisplay implements Disposable {

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    //Mario score/time Tracking Variables
    private int currentHitpoints,maxHitpoints, currentMana,maxMana;

    //Scene2D widgets
    private Label hitpointLabel;
    private Label manaLabel;
    private Label AnzeigeHPLabel;
    private Label AnzeigeMPLabel;

    private Texture anzeigeTexture;
    private float healthbar_default_width;

    public AnzeigenDisplay(SpriteBatch sb,Held held){
        //define our tracking variables
        this.currentHitpoints=held.getCurrentHitpoints();
        this.maxHitpoints=held.getMaxHitpoints();
        this.currentMana=held.getCurrentMana();
        this.maxMana=held.getMaxMana();

        //setup the HUD viewport using a new camera seperate from our gamecam
        //define our stage using that viewport and our games spritebatch
        viewport = new FitViewport(AnimaRPG.W_WIDTH, AnimaRPG.W_Height, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //define a table used to organize our hud'waffenNah labels
        Table table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our labels using the String, and a Label style consisting of a font and color
        hitpointLabel = new Label(String.format("%d / %d", currentHitpoints, maxHitpoints), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        manaLabel =new Label(String.format("%d / %d",currentMana, maxMana), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        AnzeigeHPLabel = new Label("HP ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        AnzeigeMPLabel = new Label("MP ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        // Icon als Health-Bar
        anzeigeTexture=AnimaRPG.getHeld().screen.getGame().getAssetManager().get("objekte/ui_anzeigeBild.png",Texture.class);
        Image I1=new Image(new TextureRegion(anzeigeTexture,16,16,200,64));
        Image I2=new Image(new TextureRegion(anzeigeTexture,240,24,102,8));
        Image I3=new Image(new TextureRegion(anzeigeTexture,240,64,102,8));
        Image I4=new Image(new TextureRegion(anzeigeTexture,240,44,102,8));

        //sollte so bei 400 sein;
        int sizeRegler=500;

        healthbar_default_width=I2.getWidth()*AnimaRPG.W_WIDTH/sizeRegler;
        I1.setSize(I1.getWidth()*AnimaRPG.W_WIDTH/sizeRegler,I1.getHeight()*AnimaRPG.W_Height/sizeRegler);
        I1.setPosition(0,AnimaRPG.W_Height-I1.getHeight());

        I2.setSize(healthbar_default_width,I2.getHeight()*AnimaRPG.W_Height/sizeRegler);
        I2.setPosition(I1.getWidth()*0.37f,AnimaRPG.W_Height-I1.getHeight()*0.250f);

        I3.setSize(healthbar_default_width,I3.getHeight()*AnimaRPG.W_Height/sizeRegler);
        I3.setPosition(I1.getWidth()*0.37f,AnimaRPG.W_Height-I1.getHeight()*0.5625f);

        I4.setSize(healthbar_default_width,I4.getHeight()*AnimaRPG.W_Height/sizeRegler);
        I4.setPosition(I1.getWidth()*0.37f,AnimaRPG.W_Height-I1.getHeight()*0.875f);

        stage.addActor(I1);
        stage.addActor(I2);
        stage.addActor(I3);
        stage.addActor(I4);
    }
    public void update(float dt,Held held){
        this.currentHitpoints=held.getCurrentHitpoints();
        this.maxHitpoints=held.getMaxHitpoints();
        this.currentMana=held.getCurrentMana();
        this.maxMana=held.getMaxMana();
        this.hitpointLabel.setText(String.format("%d / %d",this.currentHitpoints,this.maxHitpoints));
        this.manaLabel.setText(String.format("%d / %d",this.currentMana,this.maxMana));
        stage.getActors().get(1).setWidth(healthbar_default_width*(held.getCurrentHitpointsPercent()));
        stage.getActors().get(2).setWidth(healthbar_default_width*(held.getCurrentManaPercent()));
        stage.getActors().get(3).setWidth(healthbar_default_width*(held.getCurrentEXPpercent()));
        }
    public void draw() {
        stage.draw();
    }
    @Override
    public void dispose()
        {
        stage.dispose();}
    public void resize(int width,int height){
          viewport.update(width,height);
        }
}