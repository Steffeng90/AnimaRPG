package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Steffen on 01.12.2016.
 */

public class Item{

    public static enum kategorie{nahkampf,fernkampf,nutzbar,armor}
    private kategorie itemKategorie;
    private static Texture spriteQuelle_weiss=new Texture("objekte/icons_for_rpg.png")
            ,spriteQuelle_blau=new Texture("objekte/icons_for_rpg_auswahl.png"),
            spriteQuelle_gruen=new Texture("objekte/icons_for_rpg_angelegt.png");
    private TextureRegion grafik;
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
        setGrafik(new TextureRegion(spriteQuelle_weiss, grafikPosX * 34, grafikPosY * 34, 34, 34));
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

    public void setGrafik(TextureRegion grafik) {
        this.grafik = grafik;
    }
    public void changeGrafik(){
        if(angelegt)
        {
            setGrafik(new TextureRegion(spriteQuelle_gruen,grafikPosX*34,grafikPosY*34,34,34));
        }else if(ausgewaehlt){
            setGrafik(new TextureRegion(spriteQuelle_blau,grafikPosX*34,grafikPosY*34,34,34));
        }else{
            setGrafik(new TextureRegion(spriteQuelle_weiss,grafikPosX*34,grafikPosY*34,34,34));
        }
       // Gdx.app.log("Wechsel","");
    }

    public TextureRegion getGrafik(){ return grafik;}

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
    /*public TextureRegion getTextureRegion() {
        return grafik;
    }
    public void setTextureRegion(TextureRegion grafik) {
        this.grafik = grafik;
    }
*/
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


}
