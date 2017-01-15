package com.mygdx.anima.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.scenes.AnzeigenDisplay;
import com.mygdx.anima.scenes.ItemFundInfo;
import com.mygdx.anima.scenes.LevelUpInfo;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.HumanoideSprites;
import com.mygdx.anima.sprites.character.SchadenLabel;
import com.mygdx.anima.sprites.character.enemies.Enemy;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Arrow;
import com.mygdx.anima.sprites.character.zauber.fixtures.Nova;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Schatztruhe;
import com.mygdx.anima.sprites.character.zauber.fixtures.ZauberFixture;
import com.mygdx.anima.sprites.character.items.ItemSprite;
import com.mygdx.anima.sprites.character.items.WaffeNah;
import com.mygdx.anima.sprites.character.zauber.ZauberGenerator;
import com.mygdx.anima.tools.B2WorldCreator;
import com.mygdx.anima.tools.Controller;
import com.mygdx.anima.tools.KartenManager;
import com.mygdx.anima.tools.listener.WorldContactListener;

/**
 * Created by Steffen on 06.11.2016.
 */

public class Playscreen implements Screen{
    private AnimaRPG game;
    public enum GameState {RUN, PAUSE}
    private GameState currentGameState;
     OrthographicCamera gamecam;
    private static Viewport gameViewPort;
    Controller controller;
    ItemFundInfo itemWindow;
    private LevelUpInfo levelUpWindow;
    BitmapFont bf;


    AnzeigenDisplay anzeige;
    //Objekte um TileMap einzubinden

    private OrthogonalTiledMapRenderer renderer;
    private static boolean mapWechsel;
    private static int mapID;
    // 1=süd,2=west,3=nord,4=ost;
    private static int mapEinstieg;

    // Box2D-Einbindung
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;
    private Held spieler;
    private Vector2 spielerPosition;
    private ItemSprite currentItemsprite;
    private static KartenManager kartenManager;

    //Camera-Variablen

    public Playscreen(AnimaRPG game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gameViewPort = new FitViewport(AnimaRPG.W_WIDTH / AnimaRPG.PPM, AnimaRPG.W_Height / AnimaRPG.PPM, gamecam);

        kartenManager =new KartenManager();
        renderer = kartenManager.karteErstellen(1,gameViewPort);

        gamecam.position.set(gameViewPort.getWorldWidth() / 2, gameViewPort.getWorldHeight() / 2, 0);
        setCurrentGameState(GameState.RUN);

        // Musik
        Music music = AnimaRPG.assetManager.get("audio/music/little town - orchestral.ogg", Music.class);
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();

        world = new World(new Vector2(0, 0), false);
        world.setContactListener(new WorldContactListener());
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);
        spieler = new Held(this,spielerPosition);
        getSpieler().getHeldenInventar().add(new WaffeNah("Test2", "nahkampf", new Vector2(1,5),1,4));

        controller = new Controller(game);
        anzeige = new AnzeigenDisplay(game.batch, spieler);
        bf=new BitmapFont(Gdx.files.internal("ui-skin/default.fnt"));
        bf.setColor(Color.BLUE);
        bf.setUseIntegerPositions(false);
        bf.getData().setScale(1f/AnimaRPG.W_WIDTH,1f/AnimaRPG.W_Height);

        //TestZauber erzeugen
        ZauberGenerator.generateZauber("staerkung1");
        ZauberGenerator.generateZauber("schaden2");
        ZauberGenerator.generateZauber("schaden1");


        //Testitems erzeugen:
/*
        getSpieler().getHeldenInventar().add(new Armor("Ruessi1","armor",new Vector2(5,13),15,20));
        getSpieler().getHeldenInventar().add(new Armor("Ruessi2","armor",new Vector2(6,13),15,16));
        getSpieler().getHeldenInventar().add(new Armor("Ruessi3","armor",new Vector2(7,13),1125,13));
        getSpieler().getHeldenInventar().add(new Armor("Ruessi4","armor",new Vector2(1,13),125,123));
        getSpieler().getHeldenInventar().add(new Armor("Ruessi5","armor",new Vector2(2,13),135,23));
        getSpieler().getHeldenInventar().add(new Armor("Ruessi6","armor",new Vector2(3,13),5,46));
        getSpieler().getHeldenInventar().add(new Armor("Ruessi7","armor",new Vector2(4,13),1,75));
        getSpieler().getHeldenInventar().add(new Armor("Ruessi8","armor",new Vector2(5,13),13,213));
        getSpieler().getHeldenInventar().add(new Armor("Ruessi9","armor",new Vector2(6,13),14,21));
        getSpieler().getHeldenInventar().add(new Armor("Ruessi10","armor",new Vector2(7,13),29,222));
        getSpieler().getHeldenInventar().add(new Armor("Ruessi11","armor",new Vector2(8,13),15,512));
        getSpieler().getHeldenInventar().add(new Armor("Ruessi12","armor",new Vector2(9,13),15,13));
        getSpieler().getHeldenInventar().add(new Armor("Ruessi3","armor",new Vector2(5,13),15,123));
        getSpieler().getHeldenInventar().add(new Armor("Ruessi14","armor",new Vector2(5,13),15,132));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test1", "nahkampf", new Vector2(0,5),15,2));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test3", "nahkampf", new Vector2(2,5),12,5));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test4", "nahkampf", new Vector2(3,5),32,5));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test5", "nahkampf", new Vector2(4,5),23,182));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test6", "nahkampf", new Vector2(5,5),56,18546));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test3", "nahkampf", new Vector2(2,5),123,14568));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test4", "nahkampf", new Vector2(3,5),54,1458));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test5", "nahkampf", new Vector2(4,5),11231,158));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test6", "nahkampf", new Vector2(5,5),1,178));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test1", "nahkampf", new Vector2(0,5),64,178));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test2", "nahkampf", new Vector2(1,5),75,188));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test3", "nahkampf", new Vector2(2,5),44,1118));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test4", "nahkampf", new Vector2(3,5),876,118));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test5", "nahkampf", new Vector2(4,5),1567,128));
        getSpieler().getHeldenInventar().add(new WaffeNah("Test6", "nahkampf", new Vector2(5,5),1546,32));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(5,11),112, 345));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(6,11),142, 78));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(7,11),312, 86));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(8,11),1512, 2456));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(9,11),242, 12));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(4,11),1, 2224));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(5,11),2, 4));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(6,11),8, 2));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(7,11),5, 6));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(8,11),151, 23));
        getSpieler().getHeldenInventar().add(new WaffeFern("Bogen1", "fernkampf", new Vector2(9,11),534, 52));*/
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        if(isMapWechsel()){
            // Die Karte wird gewechselt, dadurch aufruf am ende der If-Abfrage. vorher werden vorhandene Bodies gelöscht
            setMapWechsel(false);

            renderer.dispose();
            //TODO destroy alle bodies in WOrld (google)
            Array<Body> bodyArray=new Array<Body>();
            world.getBodies(bodyArray);
            world.clearForces();
            for(Body b:bodyArray){
                for(Fixture fix:b.getFixtureList()){
                    b.destroyFixture(fix);
                }
                // world.destroyBody(b);
            }
            renderer=kartenManager.karteErstellen(mapID,gameViewPort);
            creator=new B2WorldCreator(this);

            Gdx.app.log("KArte erstellt","");
        }
        switch (getCurrentGameState()) {
            case RUN:
                update(delta);
                break;
            case PAUSE:
                break;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //karte rendern
        renderer.render();
        // Render-Linien
        b2dr.render(world, gamecam.combined);

        //Zeigt den Controller nur bei Android an:
        //if(Gdx.app.getType()== Application.ApplicationType.Android){controller.draw();}
        game.batch.setProjectionMatrix(gamecam.combined);

        game.batch.begin();

        //Enemies malen
       for (Enemy enemy : creator.getAllEnemies()) {
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
        if (ZauberFixture.getAllZauber().size > 0) {

            for (ZauberFixture zauberFixture : Nova.getAllZauber()) {
                if (!zauberFixture.destroyed) {
                    zauberFixture.draw(game.batch);
                }
            }
        }
        /*for (Gebietswechsel gebietswechsel : creator.getAllAusgang()) {
            gebietswechsel.draw(game.batch);
        }*/
        spieler.draw(game.batch);
        if (SchadenLabel.getSchadensLabelArray().size > 0) {
            for (int i = 0; i < SchadenLabel.getSchadensLabelArray().size; i++) {
                    SchadenLabel sl = SchadenLabel.getSchadenLabel(i);
                    if (sl.getTimer() < 0.5) {
                        sl.draw(game.batch);
                        sl.addTime(delta);
                    } else {sl.removeSchadenLabel(i);}
            }}
            if (getCurrentItemsprite() != null || getLevelUpWindow()!=null) {
                setCurrentGameState(GameState.PAUSE);
            }

            game.batch.end();
            switch (getCurrentGameState()) {
                case RUN:
                    controller.draw();
                    Gdx.input.setInputProcessor(controller.getStage());
                    break;
                case PAUSE:
                    game.batch.begin();
                    if(getCurrentItemsprite()!=null)
                        getCurrentItemsprite().draw(game.batch);
                    game.batch.end();

                    controller.draw();
                    if(itemWindow!=null){itemWindow.draw();
                        getCurrentItemsprite().update(delta);
                        if (itemWindow.isGeklickt()) {
                            setCurrentItemsprite(null);
                            Gdx.input.setInputProcessor(controller.getStage());
                            itemWindow.dispose();
                            itemWindow=null;

                            setCurrentGameState(GameState.RUN);
                        }}
                    if(levelUpWindow!=null){
                        levelUpWindow.draw();
                        if (levelUpWindow.isGeklickt()) {
                            levelUpWindow.dispose();
                            levelUpWindow=null;
                            Gdx.input.setInputProcessor(controller.getStage());
                            setCurrentGameState(GameState.RUN);
                        }
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

        } else if (controller.isCast1Pressed()) {
            spieler.castAttack(1);spieler.b2body.setLinearVelocity(0, 0);
        }else if (controller.isCast2Pressed()) {
            spieler.castAttack(2);spieler.b2body.setLinearVelocity(0, 0);
        }else if (controller.isCast3Pressed()) {
            spieler.castAttack(3);spieler.b2body.setLinearVelocity(0, 0);
        }else if (controller.isCast4Pressed()) {
            spieler.castAttack(4);spieler.b2body.setLinearVelocity(0, 0);
        }
        else if (spieler.actionInProgress()) {
            float xAchse=controller.getTouchpad().getKnobPercentX()*spieler.getGeschwindigkeitLaufen();
            float yAchse=controller.getTouchpad().getKnobPercentY()*spieler.getGeschwindigkeitLaufen();
            spieler.b2body.setLinearVelocity(xAchse/10,yAchse/10);
            // nach unten schauen
            if(yAchse<0 && Math.abs(yAchse)>Math.abs(xAchse)){ spieler.setCurrentRichtung(3);}
            else if(yAchse>0 && Math.abs(yAchse)>Math.abs(xAchse)){ spieler.setCurrentRichtung(2);}
            else if(xAchse>0 && Math.abs(xAchse)>Math.abs(yAchse)){ spieler.setCurrentRichtung(1);}
            else if(xAchse<0 && Math.abs(xAchse)>Math.abs(yAchse)){ spieler.setCurrentRichtung(0);}

            else if (Gdx.input.isKeyPressed(Input.Keys.D) | controller.isRightPressed()) {
                spieler.b2body.setLinearVelocity(spieler.getGeschwindigkeitLaufen()/10, 0);
                spieler.setCurrentRichtung(1);
            } else if (Gdx.input.isKeyPressed(Input.Keys.A) | controller.isLeftPressed()) {
                spieler.b2body.setLinearVelocity(-spieler.getGeschwindigkeitLaufen()/10, 0);
                spieler.setCurrentRichtung(0);
            } else if (Gdx.input.isKeyPressed(Input.Keys.W) | controller.isUpPressed()) {
                spieler.b2body.setLinearVelocity(0, spieler.getGeschwindigkeitLaufen()/10);
                spieler.setCurrentRichtung(2);
            } else if (Gdx.input.isKeyPressed(Input.Keys.S) | controller.isDownPressed()) {
                spieler.b2body.setLinearVelocity(0, -spieler.getGeschwindigkeitLaufen()/10);
                spieler.setCurrentRichtung(3);
            } else {
                /*if (spieler.b2body != null)
                    spieler.b2body.setLinearVelocity(0, 0);*/
            }
        }
    }
    public void update(float dt) {
                handleInput(dt);
                world.step(1 / 60f, 6, 2);
                anzeige.update(dt, spieler);
                gamecam.update();
                renderer.setView(gamecam);
                if (spieler.getCurrentHitpoints() > 0) {
                    gamecam.position.set(spieler.b2body.getPosition(), 0);
                    kartenManager.justiereCam(gamecam);
                }
                for (Enemy enemy : creator.getAllEnemies()) {
                    if (!enemy.destroyed) {
                        kartenManager.isEnemyinRange(enemy);
                        enemy.update(spieler, dt);
                    } else {
                        creator.removeEnemy(enemy);
                    }
                }
                for (Schatztruhe truhe : creator.getAllSchatztruhen()) {
                    truhe.update(dt);
                }
                if (getCurrentItemsprite() != null) {
                    itemWindow=new ItemFundInfo(this,game.batch,getCurrentItemsprite().name);
                }
                for (Arrow arrow : Arrow.getAllArrows()) {
                    if (!arrow.destroyed) {
                        arrow.update(dt);
                    } else {
                        arrow.remove();
                    }
                }

                for (ZauberFixture zauberFixture : Nova.getAllZauber()) {
                    if (!zauberFixture.destroyed) {
                        zauberFixture.update(dt);
                    } else {
                        zauberFixture.remove();
                    }
                }
                if (!spieler.destroyed)
                    spieler.update(dt);
                controller.update();
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
        kartenManager.getMap().dispose();
        //itemWindow.dispose();
        //levelUpWindow.dispose();
        anzeige.dispose();
        b2dr.dispose();
        controller.dispose();
    }
    //Getter und Setter, selbstgeschrieben
    public World getWorld(){ return world;}
    public TiledMap getMap(){ return kartenManager.getMap();}

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

    public static Viewport getGameViewPort() {
        return gameViewPort;
    }
    public static void setGameViewPort(Viewport tempgameViewPort) {
        gameViewPort = tempgameViewPort;
    }
    public static KartenManager getKartenManager() {
        return kartenManager;
    }
    public static void setKartenManager(KartenManager kartenManager) {
        Playscreen.kartenManager = kartenManager;
    }

    public static boolean isMapWechsel() {
        return mapWechsel;
    }

    public static void setMapWechsel(boolean tempmapWechsel) {
        mapWechsel = tempmapWechsel;
    }

    public static int getMapId() {
        return mapID;}
    public static void setMapId(int tempMapID) {
        mapID = tempMapID;
    }

    public Vector2 getSpielerPosition() {
        return spielerPosition;
    }

    public void setSpielerPosition(Vector2 spielerPosition) {
        this.spielerPosition = spielerPosition;
    }

    public static int getMapEinstieg() {
        return mapEinstieg;
    }

    public static void setMapEinstieg(int tempMapEinstieg) {
        Playscreen.mapEinstieg = tempMapEinstieg;
    }

    public LevelUpInfo getLevelUpWindow() {
        return levelUpWindow;
    }

    public void setLevelUpWindow(LevelUpInfo levelUpWindow) {
        this.levelUpWindow = levelUpWindow;
    }
}
