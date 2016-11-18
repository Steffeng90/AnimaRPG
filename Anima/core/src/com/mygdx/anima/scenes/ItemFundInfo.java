package com.mygdx.anima.scenes;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;

/**
 * Created by Steffen on 17.11.2016.
 */

public class ItemFundInfo implements Disposable {
    public Stage stage;
    public Viewport viewport;

    private Label itemtext;

    public ItemFundInfo(SpriteBatch sb){
        viewport = new FitViewport(AnimaRPG.W_WIDTH, AnimaRPG.W_Height, new OrthographicCamera());
        stage =new Stage(viewport,sb);

        Table table = new Table();
        table.bottom();
        Label itemFound= new Label("Gegenstand gefunden:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(itemFound);
        stage.addActor(table);
    }
    @Override
    public void dispose() {

    }
}
