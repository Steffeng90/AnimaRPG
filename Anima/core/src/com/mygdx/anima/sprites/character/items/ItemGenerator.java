package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mygdx.anima.screens.Playscreen;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.mygdx.anima.AnimaRPG.getHeld;

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
    //Kategorie 1: Nahkampf, 2: Fernkampf 3: Brust, 4: Benutzbar
    //Benutzbarkateogie 1: Lebenspunkte
    private static int kategorie,schaden,goldWert, benutzbarKategorie, benutzbarWert,wert1, wert2,animationsStufe,optikStufe;
 //  private static kategorie itemKategorie=waffe;

    public static ItemSprite generateItem(Playscreen screen, float x, float y, String id) {
        Gson gson = new Gson();
        try {
            FileHandle file =Gdx.files.internal("itemdb.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.read()));
            JsonObject json = gson.fromJson(reader, JsonObject.class);

            JsonArray itemArray = json. getAsJsonArray("item");
            for (int i = 0; i < itemArray.size(); i++) {
                if(id.equals(itemArray.get(i).getAsJsonObject().get("id").getAsString())){
                JsonObject item = itemArray.get(i).getAsJsonObject();
                itemName = item.get("name").getAsString();
                vector = new Vector2(item.get("vectorX").getAsFloat(), item.get("vectorY").getAsFloat());
                goldWert = item.get("goldWert").getAsInt();
                kategorie = item.get("kategorie").getAsInt();
                switch (kategorie){
                    case 1:
                        schaden = item.get("schaden").getAsInt();
                        animationsStufe = item.get("animationsStufe").getAsInt();
                        getHeld().getHeldenInventar().add(new WaffeNah(id,itemName, "nahkampf", vector,schaden, goldWert,animationsStufe));
                        break;
                    case 2:
                        schaden = item.get("schaden").getAsInt();
                        animationsStufe = item.get("animationsStufe").getAsInt();
                        getHeld().getHeldenInventar().add(new WaffeFern(id,itemName, "fernkampf", vector,schaden, goldWert,animationsStufe));
                        break;
                    case 3:
                        wert1 = item.get("ruestung").getAsInt();
                        wert2 = item.get("LPReg").getAsInt();
                        optikStufe =item.get("optikStufe").getAsInt();
                        getHeld().getHeldenInventar().add(new Brust(id,itemName, "brust", vector,wert1,wert2, goldWert,optikStufe));
                        break;
                    case 4:
                        wert1 = item.get("ruestung").getAsInt();
                        wert2 = item.get("laufgeschwindigkeit").getAsInt();
                        optikStufe =item.get("optikStufe").getAsInt();
                        getHeld().getHeldenInventar().add(new Schuhe(id,itemName, "schuhe", vector,wert1,wert2,goldWert,optikStufe));
                        break;
                    case 5:
                        wert1 = item.get("ruestung").getAsInt();
                        wert2 = item.get("angriffgeschwindigkeit").getAsInt();
                        getHeld().getHeldenInventar().add(new Handschuhe(id,itemName, "handschuhe", vector,wert1,wert2,goldWert));
                        break;
                    case 6:
                        wert1 = item.get("lebenspunkte").getAsInt();
                        wert2 = item.get("zauberkraft").getAsInt();
                        getHeld().getHeldenInventar().add(new Helm(id,itemName, "helm", vector,wert1,wert2,goldWert));
                        break;
                    case 7:
                        wert1 = item.get("manareg").getAsInt();
                        wert2 = item.get("zauberwiderstand").getAsInt();
                        getHeld().getHeldenInventar().add(new Amulett(id,itemName, "amulett", vector,wert1,wert2,goldWert));
                        break;
                    case 8:
                        benutzbarKategorie=item.get("benutzbarKategorie").getAsInt();
                        benutzbarWert=item.get("benutzbarWert").getAsInt();
                        getHeld().getHeldenInventar().add(new Benutzbar(id,itemName, "nutzbar", vector,benutzbarKategorie, benutzbarWert, goldWert));
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


    }

