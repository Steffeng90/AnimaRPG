package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 01.12.2016.
 */

public class Item{
    private enum State{nichts,ausgewaehlt,angelegt}
    public static enum kategorie{nahkampf,fernkampf,nutzbar,brust,handschuhe,schuhe,helm,amulett}
    private kategorie itemKategorie;
    private TextureRegion grafikNichts,grafikAusgewaehlt,grafikAngelegt;
    private String id,name;
    private int goldWert;
    private boolean angelegt,ausgewaehlt;
    private int grafikPosX,grafikPosY;

    public Item(String typ,String name, String kategorieString, Vector2 grafikposi,int goldWert) {
        setName(name);
        id=typ;
        grafikPosX = (int) grafikposi.x;
        grafikPosY = (int) grafikposi.y;
        ausgewaehlt=false;
        angelegt=false;
        setGrafiken(new TextureRegion(getHeld().screen.getGame().getAssetManager().get("objekte/icons_for_rpg.png",Texture.class), grafikPosX * 34, grafikPosY * 34, 34, 34),
                new TextureRegion(getHeld().screen.getGame().getAssetManager().get("objekte/icons_for_rpg_angelegt.png",Texture.class),grafikPosX*34,grafikPosY*34,34,34),
                new TextureRegion(getHeld().screen.getGame().getAssetManager().get("objekte/icons_for_rpg_auswahl.png",Texture.class),grafikPosX*34,grafikPosY*34,34,34));
        setGoldWert(goldWert);
        setItemKategorie(kategorieString);

    }

    public boolean isAngelegt() {
        return angelegt;
    }

    public void setAngelegt(boolean angelegt) {
        this.angelegt = angelegt;
    }

    public boolean isAusgewaehlt() {
        return ausgewaehlt;
    }

    public void setAusgewaehlt(boolean ausgewaehlt) {
        this.ausgewaehlt = ausgewaehlt;
    }

    public void setGrafiken(TextureRegion grafikNichts,TextureRegion grafikAngelegt,TextureRegion grafikAusgewaehlt) {
        this.grafikNichts = grafikNichts;
        this.grafikAngelegt=grafikAngelegt;
        this.grafikAusgewaehlt=grafikAusgewaehlt;
    }
    public TextureRegion getGrafik(){
        switch (getState()){
            case angelegt: return grafikAngelegt;
            case ausgewaehlt: return  grafikAusgewaehlt;
            case nichts:
            default: return  grafikNichts;
        }
    }
    public TextureRegion getGrafikButton(){
        return  grafikNichts;
    }

    public String getItemKategorie() {return itemKategorie.toString();}
    public boolean isWeaponNah(){
        if(itemKategorie==kategorie.nahkampf)
            return true;
        else { return false;}
    }
    public boolean isWeaponFern(){
        if(itemKategorie==kategorie.fernkampf)
                return true;
            else { return false;}
    }
    public boolean isRuestung(){
        if(itemKategorie==kategorie.brust)
                return true;
            else { return false;}
    }
    public boolean isNutzbar(){
        if(itemKategorie==kategorie.nutzbar)
            return true;
        else { return false;}
    }
    public void setItemKategorie(kategorie itemKategorie) {
        this.itemKategorie = itemKategorie;
    }public void setItemKategorie(String itemKategorie) {
        this.itemKategorie = kategorie.valueOf(itemKategorie);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGoldWert() {
        return goldWert;
    }

    public void setGoldWert(int goldWert) {
        this.goldWert = goldWert;
    }

    private State getState() {
        if(angelegt){return State.angelegt;}
        else if(ausgewaehlt){return  State.ausgewaehlt;}
        else {return State.nichts;}
    }
    //private void setState(State state) {this.state = state;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
