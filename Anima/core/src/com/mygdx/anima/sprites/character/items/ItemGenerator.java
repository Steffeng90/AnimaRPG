package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mygdx.anima.screens.Playscreen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import jdk.nashorn.internal.runtime.Context;

/*
import static com.mygdx.anima.sprites.character.items.ItemGenerator.ruestung;
import static com.mygdx.anima.sprites.character.items.ItemGenerator.setItemID;
import static com.mygdx.anima.sprites.character.items.ItemGenerator.setItemID;
import static com.mygdx.anima.sprites.character.items.ItemGenerator.setItemID;
import static com.mygdx.anima.sprites.character.items.ItemGenerator.setItemID;
import static com.mygdx.anima.sprites.character.items.ItemGenerator.setItemID;
import static com.mygdx.anima.sprites.character.items.ItemGenerator.setItemID;
import static com.mygdx.anima.sprites.character.items.ItemGenerator.setItemID;*/


/**
 * Created by Steffen on 01.12.2016.
 */

public class ItemGenerator {
    public enum Itemtyp {
        Schwert1, Schwert2, Schwert3, Schwert4, Schwert5, Schwert6, Schwert7, Schwert8,
        ruestung1,ruestung2,ruestung3,ruestung4,ruestung5,ruestung6,ruestung7,
        Bogen1,Bogen2,Bogen3,Bogen4,Bogen5,Bogen6,
        Brot, Pilz, Kaese, Fisch, Schwert, Bogen
    }
    private static String itemName="Default";
    private static Vector2 vector;
    //Kategorie 1: Nahkampf, 2: Fernkampf 3: Armor, 4: Benutzbar
    //Benutzbarkateogie 1: Lebenspunkte
    private static int kategorie,schaden,goldWert, benutzbarKategorie, benutzbarWert,ruestung;
 //  private static kategorie itemKategorie=waffe;

    public static ItemSprite generateItem(Playscreen screen, float x, float y, String typ) {
        Gson gson = new Gson();
        try {
            FileHandle file =Gdx.files.internal("itemdb.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.read()));
            JsonObject json = gson.fromJson(reader, JsonObject.class);

            JsonArray itemArray = json. getAsJsonArray("item");
            for (int i = 0; i < itemArray.size(); i++) {
                if(typ.equals(itemArray.get(i).getAsJsonObject().get("id").getAsString())){
                JsonObject item = itemArray.get(i).getAsJsonObject();
                itemName = item.get("name").getAsString();
                vector = new Vector2(item.get("vectorX").getAsFloat(), item.get("vectorY").getAsFloat());
                goldWert = item.get("goldWert").getAsInt();
                kategorie = item.get("kategorie").getAsInt();

                switch (kategorie){
                    case 1:
                        schaden = item.get("schaden").getAsInt();
                        screen.getSpieler().getHeldenInventar().add(new WaffeNah(itemName, "nahkampf", vector,schaden, goldWert));
                        break;
                    case 2:
                        schaden = item.get("schaden").getAsInt();
                        screen.getSpieler().getHeldenInventar().add(new WaffeFern(itemName, "fernkampf", vector,schaden, goldWert));
                        break;
                    case 3:
                        ruestung = item.get("ruestung").getAsInt();
                        screen.getSpieler().getHeldenInventar().add(new Armor(itemName, "armor", vector,ruestung, goldWert));
                        break;
                    case 4:
                        benutzbarKategorie=item.get("benutzbarKategorie").getAsInt();
                        benutzbarWert=item.get("benutzbarWert").getAsInt();
                        screen.getSpieler().getHeldenInventar().add(new Benutzbar(itemName, "nutzbar", vector,benutzbarKategorie, benutzbarWert, goldWert));
                        break;
                }
                break;}
            }
        }
        catch (Exception e){
            System.out.print("Fehler beim Itemauslesen:");
            e.printStackTrace();
        }
        return new ItemSprite(screen, x, y, vector, itemName);
    }
    private static Itemtyp setItemID(String type) {
        return Itemtyp.valueOf(type);

    }


    }

