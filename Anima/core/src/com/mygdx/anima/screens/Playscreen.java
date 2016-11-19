package com.mygdx.anima.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapProperties;
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
import com.mygdx.anima.scenes.AnzeigenDisplay;
import com.mygdx.anima.scenes.ItemFundInfo;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.enemies.Enemy;
import com.mygdx.anima.sprites.character.enemies.Raider;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Arrow;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Schatztruhe;
import com.mygdx.anima.tools.B2WorldCreator;
import com.mygdx.anima.tools.Controller;
import com.mygdx.anima.tools.WorldContactListener;

/**
 * Created by Steffen on 06.11.2016.
 */

public class Playscreen implements Screen {

    private AnimaRPG game;

    OrthographicCamera gamecam;
    private Viewport gameViewPort;
    Controller controller;
    AnzeigenDisplay anzeige;
    //Objekte um TileMap einzubinden
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2D-Einbindung
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;
    private Held spieler;

    //Camera-Variablen
    float mapLeft,mapRight,mapTop,mapBottom;
    float cameraHalfWidth,cameraHalfHeight;
    float cameraLeft,cameraRight,cameraTop,cameraBottom;
    MapProperties properties;
    float mapWidth, mapHeight, tilePixelWidth, tilePixelHeight,mapPixelWidth, mapPixelHeight;

    public Playscreen(AnimaRPG game){
        this.game=game;
        gamecam=new OrthographicCamera();
        gameViewPort=new FitViewport(AnimaRPG.W_WIDTH /AnimaRPG.PPM, AnimaRPG.W_Height/AnimaRPG.PPM, gamecam);

        mapLoader=new TmxMapLoader();
        map=mapLoader.load("level/start.tmx");
        renderer= new OrthogonalTiledMapRenderer(map,1/AnimaRPG.PPM);

        gamecam.position.set(gameViewPort.getWorldWidth()/2,gameViewPort.getWorldHeight()/2,0);


        world=new World(new Vector2(0,0),false);
        world.setContactListener(new WorldContactListener());
        b2dr=new Box2DDebugRenderer();
        creator= new B2WorldCreator(this);
        spieler=new Held(this);
        controller=new Controller(game.batch);
        anzeige=new AnzeigenDisplay(game.batch,spieler);

        //Map-Camera-Initialisierung

        properties=map.getProperties();
        mapWidth =properties.get("width", Integer.class);
        mapHeight=properties.get("height", Integer.class);
        tilePixelWidth=properties.get("tilewidth", Integer.class);
        tilePixelHeight=properties.get("tileheight", Integer.class);
        mapPixelWidth =mapWidth*tilePixelWidth/AnimaRPG.PPM;
        mapPixelHeight=mapHeight*tilePixelHeight/AnimaRPG.PPM;

        mapLeft=0;
        mapRight=0+mapPixelWidth;
        mapBottom=0;
        mapTop=0+mapPixelHeight;
        cameraHalfWidth = gameViewPort.getWorldWidth() * .5f;
        cameraHalfHeight =gameViewPort.getWorldHeight() * .5f;


        Gdx.app.log(""+mapPixelWidth,"");
        Gdx.app.log(""+mapPixelHeight,"");
        Gdx.app.log("Gamecam WIDTH"+gamecam.viewportWidth,"");
        Gdx.app.log("Gamecam HEIGHT"+gamecam.viewportHeight,"");
        Gdx.app.log(""+cameraLeft+" "+cameraRight,"");
        Gdx.app.log(""+cameraTop+" "+cameraBottom,"");
        Gdx.app.log("GamecamX"+gamecam.position.x+"GamecamY"+gamecam.position.y,"");
        Gdx.app.log("viewPortHeight"+gameViewPort.getWorldHeight()+" "+gameViewPort.getCamera().viewportHeight,"");
        Gdx.app.log("viewPortWidth"+gameViewPort.getWorldWidth()+" "+gameViewPort.getCamera().viewportWidth,"");
        Gdx.app.log("halfhei"+cameraHalfHeight,"");
        Gdx.app.log("halfwid"+cameraHalfWidth,"");


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

        //Zeigt den Controller nur bei Android an:
        //if(Gdx.app.getType()== Application.ApplicationType.Android){controller.draw();}
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        //Raider erzeugen
        for(Enemy enemy: creator.getAllRaider()){
            enemy.draw(game.batch);
        }
        for(Schatztruhe schatztruhe: creator.getAllSchatztruhen()){
            schatztruhe.draw(game.batch);
        }
        spieler.draw(game.batch);
        game.batch.end();
        controller.draw();
        anzeige.draw();

}
    public void handleInput(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) | controller.isRightPressed()) {spieler.b2body.setLinearVelocity(1,0); spieler.setCurrentRichtung(1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)| controller.isLeftPressed()) {spieler.b2body.setLinearVelocity(-1,0); spieler.setCurrentRichtung(0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP) | controller.isUpPressed()) {spieler.b2body.setLinearVelocity(0,1);spieler.setCurrentRichtung(2);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) |controller.isDownPressed()) {spieler.b2body.setLinearVelocity(0,-1);spieler.setCurrentRichtung(3);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) | controller.isMeleePressed()){ spieler.meleeAttack();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.B) | controller.isUsePressed()){ spieler.useObject();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.V) ){ spieler.spriteWechsel();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.C) ){ spieler.spriteBogen();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.N) | controller.isBowPressed()){ spieler.bowAttack();
        } else if (controller.isCastPressed()){spieler.castAttack();}


        else        {
            spieler.b2body.setLinearVelocity(0,0);
        }
    }
public void update(float dt)
{
    handleInput(dt);
    world.step(1 / 60f, 6, 2);
    anzeige.update(dt,spieler);
    gamecam.update();
    renderer.setView(gamecam);
    spieler.update(dt);
    for(Enemy enemy: creator.getAllRaider()){
        if(!enemy.destroyed){
        if(enemy.getX() < spieler.getX() + 250 / AnimaRPG.PPM && enemy.getX() >spieler.getX() - 250 / AnimaRPG.PPM
                && enemy.getY() < spieler.getY() + 250/ AnimaRPG.PPM && enemy.getY() >spieler.getY() - 250 / AnimaRPG.PPM)
            {enemy.b2body.setActive(true);}
        enemy.update(spieler,dt);
        }
        else{
            creator.removeRaider((Raider)enemy);
        }
    }
    for(Schatztruhe truhe: creator.getAllSchatztruhen()) {
        truhe.update(dt);
    }
    for(Arrow arrow :Arrow.getAllArrows()){
        if(!arrow.destroyed)
            arrow.update(dt);
        else{ arrow.remove();}
    }
    gamecam.position.set(spieler.b2body.getPosition(),0);

    justiereCam();

    //if(!spieler.destroyed)
    //gamecam.position.set(gameViewPort.getWorldWidth()/2,spieler.b2body.getPosition().y,0);
//    gamecam.position.set(spieler.b2body.getPosition(),0);
    //Testen von Cameraeinstellung: Wenn man sich dem Rand nähert dann geht die Camera dieser Achse nichtmehr nacht

    //Gdx.app.log("Welt: "+renderer.getViewBounds().toString(),"");
    //Gdx.app.log("Charakter: "+spieler.b2body.getPosition().toString(),"");
    Gdx.app.log(mapPixelWidth+" "+gamecam.viewportWidth,"");
    Gdx.app.log(mapPixelHeight+" "+gamecam.viewportHeight,"");
}
    @Override
    public void resize(int width, int height) {
        gameViewPort.update(width,height);
        controller.resize(width,height);
        anzeige.resize(width,height);
    }

    @Override
    public void pause() {}

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
        this.dispose();
        anzeige.dispose();
    }
    //Getter und Setter, selbstgeschrieben
    public World getWorld(){ return world;}
    public TiledMap getMap(){ return map;}
    public void justiereCam(){

        cameraLeft= gamecam.position.x - cameraHalfWidth;
        cameraRight=gamecam.position.x + cameraHalfWidth;
        cameraTop=gamecam.position.y +cameraHalfHeight;
        cameraBottom=gamecam.position.y-cameraHalfHeight;

        if(mapPixelWidth< gamecam.viewportWidth)
        {
            gamecam.position.x = mapRight / 2;
            Gdx.app.log("Fall1","");

        }
        else if(cameraLeft <= mapLeft)
        {
            gamecam.position.x = mapLeft + cameraHalfWidth;
            Gdx.app.log("Fall2","");
        }
        else if(cameraRight >= mapRight)
        {
            gamecam.position.x = mapRight - cameraHalfWidth;
            Gdx.app.log("Fall3","");

        }

// Vertical axis
        if(mapPixelHeight<  gamecam.viewportWidth)
        {
            gamecam.position.y = mapTop / 2;
            Gdx.app.log("Fall4","");
        }
        else if(cameraBottom <= mapBottom)
        {
            gamecam.position.y = mapBottom + cameraHalfHeight;
            Gdx.app.log("Fall5","");

        }
        else if(cameraTop >= mapTop)
        {
            gamecam.position.y = mapTop - cameraHalfHeight;
            Gdx.app.log("Fall6","");
        }
    }
}
