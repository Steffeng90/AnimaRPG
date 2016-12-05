package com.mygdx.anima.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.scenes.AnzeigenDisplay;
import com.mygdx.anima.scenes.ItemFundInfo;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.HumanoideSprites;
import com.mygdx.anima.sprites.character.enemies.Enemy;
import com.mygdx.anima.sprites.character.enemies.Raider;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Arrow;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Schatztruhe;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Zauber;
import com.mygdx.anima.sprites.character.items.ItemSprite;
import com.mygdx.anima.sprites.character.items.WaffeFern;
import com.mygdx.anima.sprites.character.items.WaffeNah;
import com.mygdx.anima.tools.B2WorldCreator;
import com.mygdx.anima.tools.Controller;
import com.mygdx.anima.tools.WorldContactListener;

/**
 * Created by Steffen on 06.11.2016.
 */

public class Playscreen implements Screen{


    private AnimaRPG game;

    public enum GameState {RUN, PAUSE}
    private GameState currentGameState;
    OrthographicCamera gamecam;
    private Viewport gameViewPort;
    Controller controller;
    ItemFundInfo itemWindow;

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
    private ItemSprite currentItemsprite;
    //Camera-Variablen
    float mapLeft, mapRight, mapTop, mapBottom;
    float cameraHalfWidth, cameraHalfHeight;
    float cameraLeft, cameraRight, cameraTop, cameraBottom;
    MapProperties properties;
    float mapWidth, mapHeight, tilePixelWidth, tilePixelHeight, mapPixelWidth, mapPixelHeight;


    public Playscreen(AnimaRPG game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gameViewPort = new FitViewport(AnimaRPG.W_WIDTH / AnimaRPG.PPM, AnimaRPG.W_Height / AnimaRPG.PPM, gamecam);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level/start.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / AnimaRPG.PPM);

        gamecam.position.set(gameViewPort.getWorldWidth() / 2, gameViewPort.getWorldHeight() / 2, 0);
        setCurrentGameState(GameState.RUN);

        world = new World(new Vector2(0, 0), false);
        world.setContactListener(new WorldContactListener());
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);
        spieler = new Held(this);
        controller = new Controller(game);
        anzeige = new AnzeigenDisplay(game.batch, spieler);
        this.
        //Map-Camera-Initialisierung

        properties = map.getProperties();
        mapWidth = properties.get("width", Integer.class);
        mapHeight = properties.get("height", Integer.class);
        tilePixelWidth = properties.get("tilewidth", Integer.class);
        tilePixelHeight = properties.get("tileheight", Integer.class);
        mapPixelWidth = mapWidth * tilePixelWidth / AnimaRPG.PPM;
        mapPixelHeight = mapHeight * tilePixelHeight / AnimaRPG.PPM;

        mapLeft = 0;
        mapRight = 0 + mapPixelWidth;
        mapBottom = 0;
        mapTop = 0 + mapPixelHeight;
        cameraHalfWidth = gameViewPort.getWorldWidth() * .5f;
        cameraHalfHeight = gameViewPort.getWorldHeight() * .5f;

        //Testitems erzeugen:
        getSpieler().getHeldenInventar().add(new WaffeNah("Test1", "nahkampf", new Vector2(0,5),15,18));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test2", "nahkampf", new Vector2(1,5),15,18));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test3", "nahkampf", new Vector2(2,5),15,18));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test4", "nahkampf", new Vector2(3,5),15,18));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test5", "nahkampf", new Vector2(4,5),15,18));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test6", "nahkampf", new Vector2(5,5),15,18));
    /*    getSpieler().getHeldenInventar().add(new WaffeNah("Test1", "nahkampf", new Vector2(0,5),15,18));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test2", "nahkampf", new Vector2(1,5),15,18));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test3", "nahkampf", new Vector2(2,5),15,18));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test4", "nahkampf", new Vector2(3,5),15,18));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test5", "nahkampf", new Vector2(4,5),15,18));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test6", "nahkampf", new Vector2(5,5),15,18));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test1", "nahkampf", new Vector2(0,5),15,18));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test2", "nahkampf", new Vector2(1,5),15,18));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test3", "nahkampf", new Vector2(2,5),15,18));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test4", "nahkampf", new Vector2(3,5),15,18));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test5", "nahkampf", new Vector2(4,5),15,18));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test6", "nahkampf", new Vector2(5,5),15,18));
     */   getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(4,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(5,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(6,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(7,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(8,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(9,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(4,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(5,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(6,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(7,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(8,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(9,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(5,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(6,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(7,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(8,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(9,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(4,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(5,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(6,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(7,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(8,13),15, 24));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(6,13),15, 24));
    }

    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        switch (getCurrentGameState())
        {
            case RUN:
                update(delta);
                break;
            case PAUSE:
                break;
        }
                Gdx.gl.glClearColor(1, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                //karte rendern
                renderer.render();
                // Render-Linien
                b2dr.render(world, gamecam.combined);

                //Zeigt den Controller nur bei Android an:
                //if(Gdx.app.getType()== Application.ApplicationType.Android){controller.draw();}
                game.batch.setProjectionMatrix(gamecam.combined);
                game.batch.begin();

                //Raider erzeugen
                for (Enemy enemy : creator.getAllRaider()) {
                    enemy.draw(game.batch);
                }
                for (Schatztruhe schatztruhe : creator.getAllSchatztruhen()) {
                    schatztruhe.draw(game.batch);
                }
                if (Arrow.getAllArrows().size > 0) {
                    for (Arrow arrow : Arrow.getAllArrows()) {
                        arrow.draw(game.batch);
                    }
                }
                if (Zauber.getAllZauber().size > 0) {

                    for (Zauber zauber : Zauber.getAllZauber()) {
                        if (!zauber.destroyed) {
                            zauber.draw(game.batch);
                        }
                    }
                }
                if (getCurrentItemsprite() != null) {
                    setCurrentGameState(GameState.PAUSE);}

                spieler.draw(game.batch);

                game.batch.end();
        switch (getCurrentGameState())
        {
            case RUN:
                controller.draw();
                Gdx.input.setInputProcessor(controller.getStage());

                break;
            case PAUSE:
                game.batch.begin();
                getCurrentItemsprite().draw(game.batch);
                game.batch.end();

                controller.draw();
                itemWindow.draw();
                getCurrentItemsprite().update(delta);
                if(itemWindow.isGeklickt()){
                    setCurrentItemsprite(null);
                    itemWindow.dispose();
                    Gdx.input.setInputProcessor(controller.getStage());

                    setCurrentGameState(GameState.RUN);
                }
                break;
            }

                anzeige.draw();

                if (gameOver()) {
                    game.setScreen(new GameOverScreen(game));
                    dispose();
                }

        }


    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) | controller.isMeleePressed()) {
            spieler.meleeAttack();spieler.b2body.setLinearVelocity(0, 0);

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.B) | controller.isUsePressed()) {
            spieler.useObject();spieler.b2body.setLinearVelocity(0, 0);

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.V)) {
            spieler.spriteWechsel();spieler.b2body.setLinearVelocity(0, 0);

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            spieler.spriteBogen();spieler.b2body.setLinearVelocity(0, 0);

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.N) | controller.isBowPressed()) {
            spieler.bowAttack();spieler.b2body.setLinearVelocity(0, 0);

        } else if (controller.isCastPressed()) {
            spieler.castAttack();spieler.b2body.setLinearVelocity(0, 0);
        }
        else if (spieler.actionInProgress()) {
            if (Gdx.input.isKeyPressed(Input.Keys.D) | controller.isRightPressed()) {
                spieler.b2body.setLinearVelocity(1, 0);
                spieler.setCurrentRichtung(1);
            } else if (Gdx.input.isKeyPressed(Input.Keys.A) | controller.isLeftPressed()) {
                spieler.b2body.setLinearVelocity(-1, 0);
                spieler.setCurrentRichtung(0);
            } else if (Gdx.input.isKeyPressed(Input.Keys.W) | controller.isUpPressed()) {
                spieler.b2body.setLinearVelocity(0, 1);
                spieler.setCurrentRichtung(2);
            } else if (Gdx.input.isKeyPressed(Input.Keys.S) | controller.isDownPressed()) {
                spieler.b2body.setLinearVelocity(0, -1);
                spieler.setCurrentRichtung(3);
            } else {
                if (spieler.b2body != null)
                    spieler.b2body.setLinearVelocity(0, 0);
            }
        }
    }

    public void update(float dt) {

                handleInput(dt);
                world.step(1 / 60f, 6, 2);
                anzeige.update(dt, spieler);
                gamecam.update();
                renderer.setView(gamecam);
                if (spieler.currentHitpoints > 0) {
                    gamecam.position.set(spieler.b2body.getPosition(), 0);
                }
                for (Enemy enemy : creator.getAllRaider()) {
       /* if(!enemy.destroyed){
        if(enemy.getX() < spieler.getX() + 250 / AnimaRPG.PPM && enemy.getX() >spieler.getX() - 250 / AnimaRPG.PPM
                && enemy.getY() < spieler.getY() + 250/ AnimaRPG.PPM && enemy.getY() >spieler.getY() - 250 / AnimaRPG.PPM)
            {enemy.b2body.setActive(true);}
        enemy.update(spieler,dt);        }
        else{            creator.removeRaider((Raider)enemy);        }*/
                    if (!enemy.destroyed) {
                        if (enemy.getX() < cameraRight && enemy.getX() > cameraLeft
                                && enemy.getY() < cameraTop && enemy.getY() > cameraBottom) {
                            if (enemy.b2body.isActive() == false)
                                enemy.b2body.setActive(true);
                        }
                        enemy.update(spieler, dt);
                    } else {
                        creator.removeRaider((Raider) enemy);
                    }
                }
                for (Schatztruhe truhe : creator.getAllSchatztruhen()) {
                    truhe.update(dt);
                }
                if (getCurrentItemsprite() != null) {
                    Gdx.app.log("nichtnull","");
                    itemWindow=new ItemFundInfo(this,game.batch,getCurrentItemsprite().name);
                }

                for (Arrow arrow : Arrow.getAllArrows()) {
                    if (!arrow.destroyed) {
                        arrow.update(dt);
                    } else {
                        arrow.remove();
                    }
                }
                for (Zauber zauber : Zauber.getAllZauber()) {
                    if (!zauber.destroyed) {
                        zauber.update(dt);
                    } else {
                        zauber.remove();
                    }
                }
                if (!spieler.destroyed)
                    spieler.update(dt);


                justiereCam();


    }


    @Override
    public void resize(int width, int height) {
        gameViewPort.update(width,height);
        controller.resize(width,height);
        anzeige.resize(width,height);
    }

    @Override
    public void pause(    ) {}
    @Override
    public void resume() {    }
    @Override
    public void hide() {    }
    @Override
    public void dispose() {
        world.dispose();
        renderer.dispose();
        map.dispose();
        anzeige.dispose();
        b2dr.dispose();
        controller.dispose();
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
        {gamecam.position.x = mapRight / 2;}
        else if(cameraLeft <= mapLeft)
        {gamecam.position.x = mapLeft + cameraHalfWidth;}
        else if(cameraRight >= mapRight)
        {gamecam.position.x = mapRight - cameraHalfWidth;}
// Vertical axis
        if(mapPixelHeight<  gamecam.viewportWidth)
        {gamecam.position.y = mapTop / 2;}
        else if(cameraBottom <= mapBottom)
        {gamecam.position.y = mapBottom + cameraHalfHeight;}
        else if(cameraTop >= mapTop)
        {gamecam.position.y = mapTop - cameraHalfHeight;}
    }
    public boolean gameOver(){
        if(spieler.currentState == HumanoideSprites.State.DEAD && spieler.stateTimer>3){
            return true;
        }
        return false;
    }

    public ItemSprite getCurrentItemsprite() {
        return currentItemsprite;
    }

    public void setCurrentItemsprite(ItemSprite currentItemsprite) {
        this.currentItemsprite = currentItemsprite;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }
    public void runCurrentGameState() {
        this.currentGameState = GameState.RUN;
    }

    public Held getSpieler() {
        return spieler;
    }

    public void setSpieler(Held spieler) {
        this.spieler = spieler;
    }
}
