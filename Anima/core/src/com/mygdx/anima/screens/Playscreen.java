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
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.scenes.AnzeigenDisplay;
import com.mygdx.anima.scenes.DialogFenster;
import com.mygdx.anima.scenes.ItemFundInfo;
import com.mygdx.anima.scenes.LevelUpInfo;
import com.mygdx.anima.sprites.character.DialogGenerator;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.HumanoideSprites;
import com.mygdx.anima.sprites.character.SchadenLabel;
import com.mygdx.anima.sprites.character.enemies.Enemy;
import com.mygdx.anima.sprites.character.enemies.NPCPool;
import com.mygdx.anima.sprites.character.enemies.raider.Raider;
import com.mygdx.anima.sprites.character.enemies.raider.RaiderArcher;
import com.mygdx.anima.sprites.character.enemies.raider.RaiderHealer;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Arrow;
import com.mygdx.anima.sprites.character.interaktiveObjekte.FriendlyNPC;
import com.mygdx.anima.sprites.character.items.ItemGenerator;
import com.mygdx.anima.sprites.character.zauber.fixtures.Nova;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Schatztruhe;
import com.mygdx.anima.sprites.character.zauber.fixtures.ZauberFixture;
import com.mygdx.anima.sprites.character.items.ItemSprite;
import com.mygdx.anima.sprites.character.items.WaffeNah;
import com.mygdx.anima.sprites.character.zauber.ZauberGenerator;
import com.mygdx.anima.tools.B2WorldCreator;
import com.mygdx.anima.tools.Controller;
import com.mygdx.anima.tools.GameData;
import com.mygdx.anima.tools.KartenManager;
import com.mygdx.anima.tools.listener.WorldContactListener;

import static com.badlogic.gdx.Input.Keys.R;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

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
    DialogFenster activeDialog;
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

    //Poolable - Arrays
    public static Array<Arrow> activeArrows = new Array<Arrow>();
    public static Pool<Arrow> arrowPool= new Pool<Arrow>() {
        @Override
        protected Arrow newObject() {
            return new Arrow();
        }
    };
    public static Array<Schatztruhe> activeTruhen = new Array<Schatztruhe>();
    public static Pool<Schatztruhe> truhenPool= new Pool<Schatztruhe>() {
        @Override
        protected Schatztruhe newObject() {
            return new Schatztruhe();
        }
    };
    public static Array<SchadenLabel> activeSchadenlabel = new Array<SchadenLabel>();
    public static Pool<SchadenLabel> schadenlabelPool= new Pool<SchadenLabel>() {
        @Override
        protected SchadenLabel newObject() {
            return new SchadenLabel();
        }
    };
    public static Array<Raider> activeRaider= new Array<Raider>();
    public static Array<RaiderArcher> activeRaiderArcher= new Array<RaiderArcher>();
    public static Array<RaiderHealer> activeRaiderHealer= new Array<RaiderHealer>();
    public static Array<FriendlyNPC> activeNPC= new Array<FriendlyNPC>();

    public Playscreen(AnimaRPG game,GameData gameData){
        this.game = game;
        defineScreen(game,gameData.currentMapId);
        spieler=new Held(this,gameData);
        controller = new Controller(game);
        anzeige = new AnzeigenDisplay(game.batch, spieler);
        setMapEinstieg(gameData.tempEingang);
        creator = new B2WorldCreator(this);

    }
    public Playscreen(AnimaRPG game) {
        this.game = game;
        defineScreen(game,1);
        spieler = new Held(this,spielerPosition);
        controller = new Controller(game);
        anzeige = new AnzeigenDisplay(game.batch, spieler);
        creator = new B2WorldCreator(this);

        //TestZauber erzeugen
        ZauberGenerator.generateZauber("staerkung1");
        ZauberGenerator.generateZauber("schaden2");
        ZauberGenerator.generateZauber("schaden1");
        ItemGenerator.generateItem(this,0,0,"Schwert1");
        ItemGenerator.generateItem(this,0,0,"Schwert3");
        ItemGenerator.generateItem(this,0,0,"Schwert6");
        spieler.getHeldenInventar().setAngelegtWaffeNah(spieler.getHeldenInventar().getWaffenNahList().get(2));
        ItemGenerator.generateItem(this,0,0,"Bogen1");
        ItemGenerator.generateItem(this,0,0,"Bogen3");
        ItemGenerator.generateItem(this,0,0,"Bogen5");


        ItemGenerator.generateItem(this,0,0,"schuhe1");
        ItemGenerator.generateItem(this,0,0,"schuhe2");
        ItemGenerator.generateItem(this,0,0,"schuhe3");
        ItemGenerator.generateItem(this,0,0,"schuhe4");
        ItemGenerator.generateItem(this,0,0,"schuhe5");
        ItemGenerator.generateItem(this,0,0,"schuhe6");
        ItemGenerator.generateItem(this,0,0,"schuhe7");

        ItemGenerator.generateItem(this,0,0,"handschuhe1");
        ItemGenerator.generateItem(this,0,0,"handschuhe2");
        ItemGenerator.generateItem(this,0,0,"handschuhe3");
        ItemGenerator.generateItem(this,0,0,"handschuhe4");
        ItemGenerator.generateItem(this,0,0,"handschuhe5");

        ItemGenerator.generateItem(this,0,0,"hals1");
        ItemGenerator.generateItem(this,0,0,"hals2");
        ItemGenerator.generateItem(this,0,0,"hals3");
        ItemGenerator.generateItem(this,0,0,"hals4");

        ItemGenerator.generateItem(this,0,0,"kopf1");
        ItemGenerator.generateItem(this,0,0,"kopf2");
        ItemGenerator.generateItem(this,0,0,"kopf3");
        ItemGenerator.generateItem(this,0,0,"kopf4");
        ItemGenerator.generateItem(this,0,0,"kopf5");
    }
    // Hier sind die Gemeinsamkeiten der beiden Konstruktoren ausgelagert
    public void defineScreen(AnimaRPG game,int kartenID){
        this.game = game;

        setCurrentGameState(GameState.RUN);
        gamecam = new OrthographicCamera();

        gameViewPort = new FitViewport(AnimaRPG.W_WIDTH / AnimaRPG.PPM, AnimaRPG.W_Height / AnimaRPG.PPM, gamecam);
        gamecam.position.set(gameViewPort.getWorldWidth() / 2, gameViewPort.getWorldHeight() / 2, 0);

        kartenManager =new KartenManager();
        renderer = kartenManager.karteErstellen(kartenID,gameViewPort);

        // Musik
        Music music = AnimaRPG.assetManager.get("audio/music/little town - orchestral.ogg", Music.class);
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();

        world = new World(new Vector2(0, 0), false);
        world.setContactListener(new WorldContactListener());
        b2dr = new Box2DDebugRenderer();

        bf=new BitmapFont(Gdx.files.internal("ui-skin/default.fnt"));
        bf.setColor(Color.BLUE);
        bf.setUseIntegerPositions(false);
        bf.getData().setScale(1f/AnimaRPG.W_WIDTH,1f/AnimaRPG.W_Height);

    }
    @Override
    public void show() {
    }
    @Override
    public void render(float delta) {
        if(isMapWechsel()){
            // Die Karte wird gewechselt, dadurch aufruf am ende der If-Abfrage. vorher werden vorhandene Bodies gelöscht
            setMapWechsel(false);
            aktiveNPCsEntfernen();
            renderer.dispose();
            //TODO destroy alle bodies in WOrld (google)


            renderer=kartenManager.karteErstellen(mapID,gameViewPort);
            creator=new B2WorldCreator(this);

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
        // b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);

        game.batch.begin();

        //Enemies malen
       /*for (Enemy enemy : creator.getAllEnemies()) {
            enemy.draw(game.batch);
        }*/
        /*for (Schatztruhe schatztruhe : creator.getAllSchatztruhen()) {
            schatztruhe.draw(game.batch);
        }*/
        /*if (Arrow.getAllArrows().size > 0) {
            for (Arrow arrow : Arrow.getAllArrows()) {
                arrow.draw(game.batch);
            }
        }*/
        for(Arrow arrow :activeArrows){
            arrow.draw(game.batch);
        }
        for(Schatztruhe truhe:activeTruhen){
            truhe.draw(game.batch);
        }
        for (Raider raider:activeRaider){
            raider.draw(game.batch);
        }

        for (FriendlyNPC npc:activeNPC){
            npc.draw(game.batch);
        }
        for(RaiderHealer raiderHealer:activeRaiderHealer){
            raiderHealer.draw(game.batch);
        }
        for(RaiderArcher raiderArcher:activeRaiderArcher){
            raiderArcher.draw(game.batch);
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
        if (activeSchadenlabel.size > 0) {
            for (int i = activeSchadenlabel.size; --i >= 0;) {
                    SchadenLabel sl = activeSchadenlabel.get(i);
                    if (sl.getTimer() < 0.5) {
                        sl.draw(game.batch);
                        sl.addTime(delta);
                    } else {
                        activeSchadenlabel.removeIndex(i);
                        schadenlabelPool.free(sl);
                    }
            }}
            if (getCurrentItemsprite() != null || getLevelUpWindow()!=null ||getActiveDialog()!=null) {
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
                        itemWindow.update(delta);

                        if (itemWindow.isGeklickt()) {
                            setCurrentItemsprite(null);
                            Gdx.input.setInputProcessor(controller.getStage());
                            itemWindow.dispose();
                            itemWindow=null;
                            setCurrentGameState(GameState.RUN);
                        }}
                    if(levelUpWindow!=null){

                        levelUpWindow.draw();
                        levelUpWindow.update(delta);
                        if (levelUpWindow.isGeklickt()) {
                            levelUpWindow.dispose();
                            levelUpWindow=null;
                            Gdx.input.setInputProcessor(controller.getStage());
                            setCurrentGameState(GameState.RUN);
                        }
                    }
                    if(activeDialog!=null){

                        activeDialog.draw();
                        activeDialog.update(delta);
                        if (activeDialog.isGeklickt()) {
                            if((Integer.parseInt(activeDialog.getNachfolger()))!=0){
                                DialogGenerator.generateDialog(this,game.batch,activeDialog.getNachfolger(),null);
                            }
                            else {
                                activeDialog.dispose();
                                activeDialog=null;
                                Gdx.input.setInputProcessor(controller.getStage());
                                setCurrentGameState(GameState.RUN);
                            }
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
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.B) | controller.isTalkPressed()) {
           spieler.useNPC();controller.talkPressed=false; spieler.b2body.setLinearVelocity(0, 0);
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
            // Zu beachten: die XundY-Achse werden von -15 bis +15, deswegen sind WASD und Touchpad unterschiedlich schnell
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
        Schatztruhe truhe;
        int len = activeTruhen.size;
        for (int i = len; --i >= 0;) {
            truhe= activeTruhen.get(i);
            truhe.update(dt);
        }
        if (getCurrentItemsprite() != null) {
            itemWindow=new ItemFundInfo(this,game.batch,getCurrentItemsprite());
        }
        // ArrowPool durchlaufen
        Arrow arrow;
        len = activeArrows.size;
        for (int i = len; --i >= 0;) {
            arrow = activeArrows.get(i);
            if (arrow.destroyed == true) {
                activeArrows.removeIndex(i);
                arrowPool.free(arrow);
            }
            else{
                arrow.update(dt);
            }
        }
        //Raider-pool
        Raider raider;
        len = activeRaider.size;
        for (int i = len; --i >= 0;) {
            raider = activeRaider.get(i);
            if (raider.destroyed == true) {
                activeRaider.removeIndex(i);
                NPCPool.getRaiderPool().free(raider);
            }
            else{
                kartenManager.isEnemyinRange(raider);
                raider.update(spieler,dt);
            }
        }
        //FriendlyNPC-Pool
        FriendlyNPC npc;
        len = activeNPC.size;
        for (int i = len; --i >= 0;) {
            npc = activeNPC.get(i);
            npc.update();

        }
        // RaiderArcher
        RaiderArcher raiderArcher;
        len = activeRaiderArcher.size;
        for (int i = len; --i >= 0;) {
            raiderArcher = activeRaiderArcher.get(i);
            if (raiderArcher.destroyed == true) {
                activeRaiderArcher.removeIndex(i);
                NPCPool.getRaiderArcherPool().free(raiderArcher);
            }
            else{
                kartenManager.isEnemyinRange(raiderArcher);
                raiderArcher.update(spieler,dt);
            }
        }
        // RaiderHealer
        RaiderHealer raiderHealer;
        len = activeRaiderHealer.size;

        for (int i = len; --i >= 0;) {
            raiderHealer = activeRaiderHealer.get(i);
            if (raiderHealer.destroyed == true) {
                activeRaiderHealer.removeIndex(i);
                NPCPool.getRaiderHealerPool().free(raiderHealer);
            }
            else{
                kartenManager.isEnemyinRange(raiderHealer);
                raiderHealer.update(spieler,dt);
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

    public AnimaRPG getGame() {
        return game;
    }

    public void setGame(AnimaRPG game) {
        this.game = game;
    }

    public LevelUpInfo getLevelUpWindow() {
        return levelUpWindow;
    }

    public void setLevelUpWindow(LevelUpInfo levelUpWindow) {
        this.levelUpWindow = levelUpWindow;
    }

    public DialogFenster getActiveDialog() {
        return activeDialog;
    }

    public void setActiveDialog(DialogFenster activeDialog) {
        this.activeDialog = activeDialog;
    }

    public void aktiveNPCsEntfernen(){
        int size= activeArrows.size;
        Arrow arrow;
        if(size>0){ for (int i = size; --i >= 0;) {
            arrow = activeArrows.get(i);
            activeArrows.removeIndex(i);
            arrowPool.free(arrow);
        }
        }
        size= activeSchadenlabel.size;
        SchadenLabel schadenLabel;
        if(size>0){ for (int i = size; --i >= 0;) {
            schadenLabel= activeSchadenlabel.get(i);
            activeSchadenlabel.removeIndex(i);
            schadenlabelPool.free(schadenLabel);
        }
        }
        size= activeTruhen.size;
        Schatztruhe truhe;
        if(size>0){ for (int i = size; --i >= 0;) {
            truhe= activeTruhen.get(i);
            activeTruhen.removeIndex(i);
            truhenPool.free(truhe);
        }
        }
        size= activeRaider.size;
        Raider raider;
        if(size>0){ for (int i = size; --i >= 0;) {
            raider = activeRaider.get(i);
            activeRaider.removeIndex(i);
            NPCPool.getRaiderPool().free(raider);
            }
        }
        size= activeNPC.size;
        FriendlyNPC npc;
        if(size>0){ for (int i = size; --i >= 0;) {
            npc = activeNPC.get(i);
            activeNPC.removeIndex(i);
            NPCPool.getFriendlyNPCPool().free(npc);
        }
        }
        size= activeRaiderArcher.size;
        RaiderArcher raiderArcher;
        if(size>0){ for (int i = size; --i >= 0;) {
            raiderArcher = activeRaiderArcher.get(i);
            activeRaiderArcher.removeIndex(i);
            NPCPool.getRaiderArcherPool().free(raiderArcher);
        }
        }
        size= activeRaiderHealer.size;
        RaiderHealer raiderHealer;
        if(size>0){ for (int i = size; --i >= 0;) {
            raiderHealer = activeRaiderHealer.get(i);
            activeRaiderHealer.removeIndex(i);
            NPCPool.getRaiderHealerPool().free(raiderHealer);
        }
        }
        Array<Body> bodyArray=new Array<Body>();
        world.getBodies(bodyArray);
        //world.clearForces();
        for(Body b:bodyArray){
            if(b.getFixtureList()!=null){for(Fixture fix:b.getFixtureList()){
                b.destroyFixture(fix);
            }}
            world.destroyBody(b);
        }
}
}
