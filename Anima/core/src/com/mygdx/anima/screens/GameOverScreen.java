package com.mygdx.anima.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.tools.HandleGameData;

/**
 * Created by Steffen on 20.11.2016.
 */
    public class GameOverScreen implements Screen {
        private Viewport viewport;
        private Stage stage;

        private AnimaRPG game;
        public GameOverScreen(AnimaRPG game)
        {
            this.game=game;
            this.viewport=new FitViewport(AnimaRPG.W_WIDTH,AnimaRPG.W_Height,new OrthographicCamera());
            stage =new Stage(viewport,((AnimaRPG) game).batch);

            Label.LabelStyle font=new Label.LabelStyle(new BitmapFont(), Color.WHITE);
            Table table=new Table();
            table.center();
            table.setFillParent(true);

            Label gameOverLabel=new Label("Game Over",font);
            Label playAgainLabel=new Label("Click to Play Again",font);
            table.add(gameOverLabel).expandX();
            table.row();
            table.add(playAgainLabel).expandX().padTop(10f);

            stage.addActor(table);
        }
        @Override
        public void show() {
        }
        @Override
        public void render(float delta) {
            if(Gdx.input.justTouched())
            {
                //game.changeScreen(HandleGameData.laden(game));
                game.changeScreen(new StartScreen(game));
                //game.setScreen(new Playscreen(game));
                dispose();
            }
            Gdx.gl.glClearColor(0,0,0,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.draw();
        }
        @Override
        public void resize(int width, int height) {
        }
        @Override
        public void pause() {
        }
        @Override
        public void resume() {
        }
        @Override
        public void hide() {
        }
        @Override
        public void dispose() {
            stage.dispose();
        }
    }

