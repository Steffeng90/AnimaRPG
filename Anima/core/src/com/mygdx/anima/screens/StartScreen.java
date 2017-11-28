package com.mygdx.anima.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.tools.HandleGameData;

/**
 * Created by Steffen on 24.11.2016.
 */

public class StartScreen implements Screen {
    public AnimaRPG game;
    private Viewport viewport;
    Stage stage;
    Label titel;
    Button newGameButton, continueButton,creditsButton;
    private final Skin skin = new Skin(Gdx.files.internal("ui-skin/uiskin.json"));
    private float width, height,reiterWidth;
    public StartScreen(final AnimaRPG game) {
        this.game = game;
        Image img=new Image(new Texture(Gdx.files.internal("objekte/startbildschirm.png")));
        img.setFillParent(true);
        width = game.W_WIDTH * 2;
        height = game.W_Height * 2;
        continueButton=new TextButton("Spiel fortsetzen",skin);
        continueButton.setSize(width/4,height/6);
        continueButton.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.changeScreen(HandleGameData.laden(game));

            }
        });
        newGameButton=new TextButton("Neues Spiel",skin);
        newGameButton.setSize(width/4,height/6);
        newGameButton.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.changeScreen(new Playscreen(game));

            }
            });
        creditsButton=new TextButton("Credits",skin);
        creditsButton.setSize(width/4,height/6);
        this.viewport = new FitViewport(width, height, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        Table table=new Table();
        table.center();

        table.setFillParent(true);
        table.add(continueButton).pad(10).size(width/4,height/6);
        table.row();
        table.add(newGameButton).pad(10).size(width/4,height/6);
        table.row();
        table.add(creditsButton).pad(10).size(width/4,height/6);
        stage.addActor(img);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }
    @Override public void resize(int width, int height) {viewport.update(width, height);}
    @Override public void pause() {    }
    @Override public void resume() {    }
    @Override public void hide() {    }
    @Override public void dispose() {
        //TODO richtiges Dispose einbauen
    }
    @Override public void show() {    }
    }