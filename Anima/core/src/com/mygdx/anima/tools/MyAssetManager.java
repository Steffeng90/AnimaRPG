package com.mygdx.anima.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import static com.badlogic.gdx.Gdx.app;

/**
 * Created by Steffen on 30.11.2017.
 */

public class MyAssetManager extends AssetManager {
    String quelleHeldAlt,lastDialogIcon;
    int quelleMapAlt;
    private TextureRegion schatztruheOpenBraun,schatztruheCloseBraun,schatztruheOpenGold,schatztruheCloseGold,
            schatztruheCloseBlau,schatztruheOpenBlau,
            pfeilLinks,pfeilRechts,pfeilUp,pfeilDown,
            emptySpellIcon,steinblock,fassblock;

    Animation truheBraunAnimation,truheGoldAnimation,truheBlauAnimation;
    public Animation getTruheBraunAnimation() {
        return truheBraunAnimation;
    }
    public Animation getTruheGoldAnimation() {
        return truheGoldAnimation;
    }
    public Animation getTruheBlauAnimation() {
        return truheBlauAnimation;
    }

    public TextureRegion getSchatztruheOpenBraun() {
        return schatztruheOpenBraun;
    }
    public TextureRegion getSchatztruheCloseBraun() {
        return schatztruheCloseBraun;
    }
    public TextureRegion getSchatztruheOpenGold() {
        return schatztruheOpenGold;
    }
    public TextureRegion getSchatztruheCloseGold() {
        return schatztruheCloseGold;
    }
    public TextureRegion getSchatztruheCloseBlau() {
        return schatztruheCloseBlau;
    }
    public TextureRegion getSchatztruheOpenBlau() {
        return schatztruheOpenBlau;
    }
    public Skin mySkin,damageSkin;
    //FreeTypeFontGenerator generator;
    //FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public TextureRegion getSteinblock() {
        return steinblock;
    }
    public TextureRegion getFassblock() {
        return fassblock;
    }
    public MyAssetManager() {
        loadAllAssets();
    }
    public void loadAllAssets(){
        // Versucht einen TFF (Freetype FOnt) einzubauen.
        /*FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/myfont.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!*/
        // AUDIOS
        load("audio/music/little town - orchestral.ogg", Music.class);
        load("audio/sounds/anziehen.wav", Sound.class);
        load("audio/sounds/ausziehen.wav", Sound.class);
        load("audio/sounds/reiter_wechsel.ogg", Sound.class);
        load("audio/sounds/walk.ogg", Sound.class);
        load("audio/sounds/laufen.mp3", Sound.class);
        load("audio/sounds/sword_swing.mp3", Sound.class);
        load("audio/sounds/bow_attack.mp3", Sound.class);
        load("audio/sounds/nova.wav", Sound.class);
        load("audio/sounds/levelUp.wav", Sound.class);
        load("audio/sounds/itemFund.wav", Sound.class);
        load("audio/sounds/electricity.wav", Sound.class);
        load("audio/sounds/turn_page.wav", Sound.class);

        // Controller
        load("ui-skin/button_vorlage.png",Texture.class);
        load("ui-skin/button_vorlage_aktiv.png",Texture.class);
        load("touchBackground.png",Texture.class);
        load("ui-skin/inventar.png",Texture.class);

        load("touchBackground.png",Texture.class);
        load("touchBackground.png",Texture.class);
        load("touchBackground.png",Texture.class);

        // Steinblock
        load("objekte/block-fass.png",Texture.class);
        finishLoadingAsset("objekte/block-fass.png");
        fassblock=new TextureRegion(get("objekte/block-fass.png",Texture.class), 0, 0, 64, 112);
        load("objekte/block.png",Texture.class);
        finishLoadingAsset("objekte/block.png");
        steinblock=new TextureRegion(get("objekte/block.png",Texture.class), 0, 0, 32, 32);
        //Schatztruhe
        load("objekte/schatztruhe.png",Texture.class);
        finishLoadingAsset("objekte/schatztruhe.png");
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(get("objekte/schatztruhe.png",Texture.class), 0, i*32+128, 32, 32));
        }
        truheGoldAnimation=new Animation(0.1f, frames);
        frames.clear();
        schatztruheOpenGold= new TextureRegion(get("objekte/schatztruhe.png",Texture.class),0,224, 32,32);
        schatztruheCloseGold= new TextureRegion(get("objekte/schatztruhe.png",Texture.class),0,128, 32,32);
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(get("objekte/schatztruhe.png",Texture.class), 192, i*32+128, 32, 32));
        }
        truheBraunAnimation=new Animation(0.1f, frames);
        frames.clear();
        schatztruheOpenBraun= new TextureRegion(get("objekte/schatztruhe.png",Texture.class),192,224, 32,32);
        schatztruheCloseBraun= new TextureRegion(get("objekte/schatztruhe.png",Texture.class),192,128, 32,32);
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(get("objekte/schatztruhe.png",Texture.class), 288, i*32+0, 32, 32));
        }
        truheBlauAnimation=new Animation(0.2f, frames);
        frames.clear();
        schatztruheOpenBlau= new TextureRegion(get("objekte/schatztruhe.png",Texture.class),288,96, 32,32);
        schatztruheCloseBlau= new TextureRegion(get("objekte/schatztruhe.png",Texture.class),288,0, 32,32);


        // Zauberentity
        load("objekte/icons_for_rpg.png",Texture.class);
        load("objekte/icons_for_rpg_auswahl.png",Texture.class);
        load("objekte/icons_for_rpg_angelegt.png",Texture.class);
        load("objekte/blitz.png", Texture.class);
        load("objekte/energieNova.png",Texture.class);
        //Maps
        setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        //Textures
        load("objekte/heilung_versuch2.png",Texture.class);
        load("objekte/arrow.png", Texture.class);
        load("objekte/energieNova.png", Texture.class);
        load("ungeheuer/ungeheuer.atlas",TextureAtlas.class);

        finishLoadingAsset("objekte/arrow.png");
        pfeilRechts=new TextureRegion(get("objekte/arrow.png",Texture.class),5,10,53,9);
        pfeilLinks=new TextureRegion(get("objekte/arrow.png",Texture.class),5,0,53,9);
        pfeilUp=new TextureRegion(get("objekte/arrow.png",Texture.class),15,20,9,53);
        pfeilDown=new TextureRegion(get("objekte/arrow.png",Texture.class),5,20,9,53);
        this.finishLoading();
    }
        public TextureAtlas loadHeroTextureAtlas(String quelle){
            load(quelle+".pack", TextureAtlas.class);
            try{
                unload(quelleHeldAlt+".pack");
            }catch (Exception e){
                app.log("Karte","Vorgaengerkarte kann nicht entladen werden");
            }
            quelleHeldAlt=quelle;
            finishLoading();
            return get(quelle+".pack");
    }
        public TiledMap loadTiledMap(int quelle){
            load("level/level"+quelle+".tmx", TiledMap.class);
            try{
                unload("level/level"+quelleMapAlt+".tmx");
            }catch (Exception e){
                app.log("Karte","Vorgaengerkarte kann nicht entladen werden");
            }
            quelleMapAlt=quelle;
            finishLoading();
            return get("level/level"+quelle+".tmx");
    }
        public TextureAtlas returnEnemyHumanoidPack(String id){
            if(!isLoaded(id.substring(0,id.length()-2)+".pack",TextureAtlas.class)){
                load(id.substring(0,id.length()-2)+".pack", TextureAtlas.class);
                finishLoading();
            }
            return get(id.substring(0,id.length()-2)+".pack",TextureAtlas.class);
        }
        public TextureRegion loadDialogIcon(String typ){
            lastDialogIcon=typ;
            load("NPC/"+typ+".png",Texture.class);
            finishLoadingAsset("NPC/"+typ+".png");
            return new TextureRegion(get("NPC/"+typ+".png",Texture.class), 20, 136, 24, 32);
        }
        public void unloadDialogIcon(){
            unload("NPC/"+lastDialogIcon+".png");
        }
    public TextureRegion getPfeilLinks() {
        return pfeilLinks;
    }
    public TextureRegion getPfeilRechts() {
        return pfeilRechts;
    }
    public TextureRegion getPfeilUp() {
        return pfeilUp;
    }
    public TextureRegion getPfeilDown() {
        return pfeilDown;
    }

    public TextureRegion loadEmptySpellIcon() {
            if(emptySpellIcon==null){
             emptySpellIcon=new TextureRegion(get("objekte/icons_for_rpg.png",Texture.class),374,612,34,34);
            }
            return emptySpellIcon;
    }
    public Skin getSkin(){
            if(mySkin==null){
                load("ui-skin/uiskin.json",Skin.class);
                finishLoadingAsset("ui-skin/uiskin.json");
                mySkin=get("ui-skin/uiskin.json");
            }
        return mySkin;
    }
    public Skin getDamageSkin(){
        if(damageSkin==null){
            load("ui-skin/damageLabel.fnt",Skin.class);
            finishLoadingAsset("ui-skin/damageLabel.fnt");
           // damageSkin=get();
        }
        return mySkin;
    }
    @Override
    public synchronized void dispose() {
        super.dispose();
        mySkin.dispose();
    }
    public void reloadAsset(){
        this.clear();
        loadAllAssets();
    }
}

