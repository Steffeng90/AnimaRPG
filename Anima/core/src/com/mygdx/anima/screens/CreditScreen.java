package com.mygdx.anima.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.actors.CreditObject;
import com.mygdx.anima.tools.Controller;
import com.mygdx.anima.tools.listener.CreditListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 24.11.2016.
 */

public class CreditScreen implements Screen {
    public AnimaRPG game;
    private Viewport viewport;
    public Stage stage;
    private boolean changeReiter;
    Table CreditsTabelle;
    private Skin skin;
    private float width, height;
    public ScrollPane pane;
    //Credit JSON Werte
    private static JsonArray creditArray,urheberHilfsArray;
    private CreditObject[] grafikenArray,soundArray,testerArray;
    TextButton creditsButton;


    public CreditScreen(final AnimaRPG game) {
        this.game = game;
        skin =game.getAssetManager().getSkin();
        width = game.W_WIDTH * 2;
        height = game.W_Height * 2;

        this.viewport = new FitViewport(width, height, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        generateCredits();

        pane=zeigeCreditScrollPane();
        stage.addActor(zurueckButtonErzeugen());
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
    public TextButton zurueckButtonErzeugen(){
        creditsButton=new TextButton("CreditScreen",skin);
        creditsButton.setSize(width/4,height/6);
        creditsButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.getAssetManager().get("audio/sounds/turn_page.wav", Sound.class).play();
                //game.closeScreen();
                game.changeScreen(new StartScreen(game));
                dispose();}
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {return true;}});
        creditsButton.setPosition(width*(3f/8f),0);
        return creditsButton;
    }
    public ScrollPane zeigeCreditScrollPane(){
        CreditsTabelle = new Table();
        CreditsTabelle.setWidth(width);
        CreditsTabelle.setHeight(height*0.8f);
        CreditsTabelle.setPosition(0, height);
        CreditsTabelle.align(Align.left | Align.top);
        CreditsTabelle.add(new Label("Grafiken:",getSkin()));
        CreditsTabelle.row();
        for(int x=0;x<grafikenArray.length;x++){
            System.out.println("Array"+x);

        CreditsTabelle.add(new Label(grafikenArray[x].inhalt, getSkin()));
        CreditsTabelle.row();
        CreditsTabelle.add(new Label("Urheber"+grafikenArray[x].getUrheberString(), getSkin()));
        CreditsTabelle.row();
        }
        CreditsTabelle.add(new Label("Sound:",getSkin()));
        CreditsTabelle.row();
        for(int x=0;x<soundArray.length;x++){
            System.out.println("Array"+x);

            CreditsTabelle.add(new Label(soundArray[x].inhalt, getSkin()));
            CreditsTabelle.row();
            CreditsTabelle.add(new Label("Urheber"+soundArray[x].getUrheberString(), getSkin()));
            CreditsTabelle.row();
        }
        CreditsTabelle.add(new Label("Test & Support:",getSkin()));
        CreditsTabelle.row();
        for(int x=0;x<testerArray.length;x++){
            System.out.println("Array"+x);

            CreditsTabelle.add(new Label(testerArray[x].inhalt, getSkin()));
            CreditsTabelle.row();
            CreditsTabelle.add(new Label(testerArray[x].getUrheberString(), getSkin()));
            CreditsTabelle.row();
        }
            ScrollPane scrollPane = new ScrollPane(CreditsTabelle, getSkin());
            scrollPane.setBounds(0, 0, width, height*0.8f);
            scrollPane.setScrollingDisabled(true, false);
            scrollPane.setSmoothScrolling(false);
            scrollPane.setPosition(0, height*0.2f);
            scrollPane.setFadeScrollBars(true);
            scrollPane.setScrollbarsOnTop(false);
            scrollPane.setupFadeScrollBars(0, 0);
            scrollPane.layout();

            scrollPane.setSize(width, height*(5f/6f));
            stage.addActor(scrollPane);
            pane=scrollPane;
//        menu.zurueckButtonErzeugen();
            return scrollPane;

        }
    public void generateCredits(){
            Gson gson = new Gson();
         int id;
         String inhalt;
         String[] urheber;
        JsonObject json=null;
            try {
                FileHandle file =Gdx.files.internal("credits.json");
                BufferedReader reader = new BufferedReader(new InputStreamReader(file.read()));
                 json = gson.fromJson(reader, JsonObject.class);}
            catch (Exception e) {
                System.out.print("Fehler beim Credits auslesen:");
                e.printStackTrace();
            }

            creditArray = json. getAsJsonArray("grafiken");
            grafikenArray=new CreditObject[creditArray.size()];
            for (int i = 0; i < creditArray.size(); i++) {
                    JsonObject item = creditArray.get(i).getAsJsonObject();

                    id = item.get("id").getAsInt();
                    inhalt=item.get("inhalt").getAsString();
                    urheberHilfsArray=item.getAsJsonArray("urheber");
                    urheber= new String[item.getAsJsonArray("urheber").size()];
                            for(int y=0;y<urheber.length;y++){
                                urheber[y]=urheberHilfsArray.get(y).getAsString();
                            }
                    grafikenArray[i]=new CreditObject(id,inhalt,urheber);}
        creditArray = json. getAsJsonArray("sounds");
        soundArray=new CreditObject[creditArray.size()];

        for (int i = 0; i < creditArray.size(); i++) {
            JsonObject item = creditArray.get(i).getAsJsonObject();

            id = item.get("id").getAsInt();
            inhalt=item.get("inhalt").getAsString();
            urheberHilfsArray=item.getAsJsonArray("urheber");
            urheber= new String[item.getAsJsonArray("urheber").size()];
            for(int y=0;y<urheber.length;y++){
                urheber[y]=urheberHilfsArray.get(y).getAsString();
            }
            soundArray[i]=new CreditObject(id,inhalt,urheber);}

        creditArray = json. getAsJsonArray("tester");
        testerArray=new CreditObject[creditArray.size()];
        System.out.println("Größe"+grafikenArray.length);
        for (int i = 0; i < creditArray.size(); i++) {
            JsonObject item = creditArray.get(i).getAsJsonObject();

            id = item.get("id").getAsInt();
            inhalt=item.get("inhalt").getAsString();
            urheberHilfsArray=item.getAsJsonArray("urheber");
            urheber= new String[item.getAsJsonArray("urheber").size()];
            for(int y=0;y<urheber.length;y++){
                urheber[y]=urheberHilfsArray.get(y).getAsString();
            }
            testerArray[i]=new CreditObject(id,inhalt,urheber);
            }
            }


    public Skin getSkin() {return skin;}
    public float getWidth() {return width;}
    public void setWidth(float width) {this.width = width;}
    public float getHeight() {return height;}
    public void setHeight(float height) {this.height = height;}


    @Override public void resize(int width, int height) {viewport.update(width, height);}
    @Override public void pause() {    }
    @Override public void resume() {    }
    @Override public void hide() { }
    @Override public void dispose() {
        //TODO richtiges Dispose einbauen
        stage.dispose();
    }
    @Override public void show() {    }
    }
