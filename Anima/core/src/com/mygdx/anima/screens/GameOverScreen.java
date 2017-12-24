package com.mygdx.anima.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
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
        BitmapFont bf;
        private AnimaRPG game;
        public GameOverScreen(AnimaRPG game)
        {
            this.game=game;
            this.viewport=new FitViewport(AnimaRPG.W_WIDTH,AnimaRPG.W_Height,new OrthographicCamera());
            stage =new Stage(viewport,((AnimaRPG) game).batch);
            bf= new BitmapFont();
            Label.LabelStyle font=new Label.LabelStyle(bf, Color.WHITE);
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
                // letzter Stand: game.changeScreen(new StartScreen(game));
                //game.setScreen(new Playscreen(game));
                this.dispose();
                game.getAdsController().loadRewardedVideoAd();
                game.getAdsController().showRewardedVideoAd();

                if(game.getAdsController().getRewardedVideoAdFinished()){
                    game.getHeld().wiederbeleben(1f);
                }
                else{
                    game.getHeld().wiederbeleben(0.7f);
                }
                game.getHeld().screen.setMapWechsel(true);
                game.changeScreen(game.currentPlayScreen);

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
            bf.dispose();
        }
    }

