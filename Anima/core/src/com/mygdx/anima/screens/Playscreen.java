package com.mygdx.anima.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.tools.B2WorldCreator;

/**
 * Created by Steffen on 06.11.2016.
 */

public class Playscreen implements Screen {

    private AnimaRPG game;

    OrthographicCamera gamecam;
    private Viewport gameViewPort;
    //Objekte um TileMap einzubinden
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2D-Einbindung
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;
    private Held spieler;


    public Playscreen(AnimaRPG game){
        this.game=game;
        gamecam=new OrthographicCamera();
        gameViewPort=new FitViewport(AnimaRPG.W_WIDTH /AnimaRPG.PPM, AnimaRPG.W_Height/AnimaRPG.PPM, gamecam);

        mapLoader=new TmxMapLoader();
        map=mapLoader.load("level/start.tmx");
        renderer= new OrthogonalTiledMapRenderer(map,1/AnimaRPG.PPM);
        gamecam.position.set(gameViewPort.getWorldWidth()/2,gameViewPort.getWorldHeight()/2,0);


        world=new World(new Vector2(0,0),false);
        b2dr=new Box2DDebugRenderer();
        creator= new B2WorldCreator(this);
        spieler=new Held(this);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //karte rendern
        renderer.render();

        // Render-Linien
        b2dr.render(world,gamecam.combined);


        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        spieler.draw(game.batch);
        game.batch.end();
    }
    public void handleInput(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            spieler.b2body.setLinearVelocity(1,0); spieler.setCurrentRichtung(1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {spieler.b2body.setLinearVelocity(-1,0); spieler.setCurrentRichtung(0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {spieler.b2body.setLinearVelocity(0,1);spieler.setCurrentRichtung(2);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {spieler.b2body.setLinearVelocity(0,-1);spieler.setCurrentRichtung(3);
        }else
        {
            spieler.b2body.setLinearVelocity(0,0);
        }
    }
public void update(float dt)
{
    handleInput(dt);
    world.step(1 / 60f, 6, 2);

    gamecam.update();
    renderer.setView(gamecam);
    spieler.update(dt);
    gamecam.position.set(spieler.b2body.getPosition(),0);
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
    //Getter und Setter, selbstgeschrieben
    public World getWorld(){ return world;}
    public TiledMap getMap(){ return map;}

}
