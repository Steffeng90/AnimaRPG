package com.mygdx.anima.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

        //define a table used to organize our hud's labels
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

        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(AnzeigeHPLabel).expandX().padTop(0);
        table.add(hitpointLabel).expandX().padTop(0);

        table.add(AnzeigeMPLabel).expandX().padTop(0);
        table.add(manaLabel).expandX().padTop(0);
        table.add().expandX().padTop(2);
        table.add().expandX().padTop(2);
        table.add().expandX().padTop(2);
        table.add().expandX().padTop(2);
        //add a second row to our table
        table.row();
        //add our table to the stage
        stage.addActor(table);
    }
    public void update(float dt,Held held){
        this.currentHitpoints=held.getCurrentHitpoints();
        this.maxHitpoints=held.getMaxHitpoints();
        this.currentMana=held.getCurrentMana();
        this.maxMana=held.getMaxMana();
        this.hitpointLabel.setText(String.format("%d / %d",this.currentHitpoints,this.maxHitpoints));
        this.manaLabel.setText(String.format("%d / %d",this.currentMana,this.maxMana));

        }
    public void draw() {
        stage.draw();
    }
    @Override
    public void dispose()
        {
        stage.dispose();
        this.dispose();}
    public void resize(int width,int height){
          viewport.update(width,height);
        }
}