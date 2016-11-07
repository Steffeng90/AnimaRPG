package com.mygdx.anima.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;

/**
 * Created by Steffen on 06.11.2016.
 */

public class Playscreen implements Screen {

    AnimaRPG game;
    OrthographicCamera gamecam;
    private Viewport gameViewPort;
    //Objekte um TileMap einzubinden
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public Playscreen(AnimaRPG game){
        this.game=game;
        gamecam=new OrthographicCamera();
        gameViewPort=new FitViewport(AnimaRPG.W_WIDTH /AnimaRPG.PPM, AnimaRPG.W_Height/AnimaRPG.PPM);

        mapLoader=new TmxMapLoader();
        map=mapLoader.load("level/anfangsgebiet.tmx");
        renderer= new OrthogonalTiledMapRenderer(map,1/AnimaRPG.PPM);
        gamecam.position.set(gameViewPort.getWorldWidth()/2,gameViewPort.getWorldHeight()/2,0);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //karte rendern
        renderer.render();
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gameViewPort.update(width,height);
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
        map.dispose();
        renderer.dispose();


    }
}
