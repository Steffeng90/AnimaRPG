package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * Created by Steffen on 01.12.2016.
 */

public class Item{
    private enum State{nichts,ausgewaehlt,angelegt}
    private State state;
    public static enum kategorie{nahkampf,fernkampf,nutzbar,armor}
    private kategorie itemKategorie;
    private static Texture spriteQuelle_weiss=new Texture("objekte/icons_for_rpg.png")
            ,spriteQuelle_blau=new Texture("objekte/icons_for_rpg_auswahl.png"),
            spriteQuelle_gruen=new Texture("objekte/icons_for_rpg_angelegt.png");
    private TextureRegion grafikNichts,grafikAusgewaehlt,grafikAngelegt;
    private String name;
    private int goldWert;
    private boolean angelegt,ausgewaehlt;
    private int grafikPosX,grafikPosY;
    public Image icon;

    public Item(String name, String kategorieString, Vector2 grafikposi,int goldWert) {
        setName(name);

        grafikPosX = (int) grafikposi.x;
        grafikPosY = (int) grafikposi.y;
        ausgewaehlt=false;
        angelegt=false;
        setGrafiken(new TextureRegion(spriteQuelle_weiss, grafikPosX * 34, grafikPosY * 34, 34, 34),
                new TextureRegion(spriteQuelle_gruen,grafikPosX*34,grafikPosY*34,34,34),
                new TextureRegion(spriteQuelle_blau,grafikPosX*34,grafikPosY*34,34,34));
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
        if(itemKategorie==kategorie.armor)
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
}
