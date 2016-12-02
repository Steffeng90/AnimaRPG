package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Steffen on 01.12.2016.
 */

public class Item extends Sprite{
    // K
    private enum kategorie{nahkampf,fernkampf,nutzbar,armor}
    private kategorie itemKategorie;
    private Texture spriteQuelle;
    private TextureRegion grafik;
    private String name;
    private int goldWert;


    public Item(String name, String kategorieString, Vector2 grafikposi,int goldWert){
        setName(name);
        spriteQuelle=new Texture("objekte/icons_for_rpg.png");
        setGrafik(new TextureRegion(spriteQuelle,grafikposi.x*34,grafikposi.y*34,34,34));
        setGoldWert(goldWert);
        setItemKategorie(kategorieString);
    }
    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
    public void setGrafik(TextureRegion grafik) {
        this.grafik = grafik;
    }


    public kategorie getItemKategorie() {
        return itemKategorie;
    }

    public void setItemKategorie(kategorie itemKategorie) {
        this.itemKategorie = itemKategorie;
    }public void setItemKategorie(String itemKategorie) {
        this.itemKategorie = kategorie.valueOf(itemKategorie);
    }public TextureRegion getTextureRegion() {
        return grafik;
    }
    public void setTextureRegion(TextureRegion grafik) {
        this.grafik = grafik;
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


}
