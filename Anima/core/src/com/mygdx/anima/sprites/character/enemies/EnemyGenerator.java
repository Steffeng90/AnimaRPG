package com.mygdx.anima.sprites.character.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mygdx.anima.screens.Playscreen;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Steffen on 01.12.2016.
 */

public class EnemyGenerator {

    private static String itemName="Default", id;
    private static Vector2 vector;
    //Kategorie 1: Nahkampf, 2: Fernkampf 3: Armor, 4: Benutzbar
    //Benutzbarkateogie 1: Lebenspunkte
    private static int kategorie,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY;
    private static float castSpeed,bowSpeed,meleeSpeed,thrustSpeed;
    //  private static kategorie itemKategorie=waffe;
    private static JsonArray itemArray;
    public static Enemy generateEnemy(Playscreen screen, float x, float y, String typ) {
        Gson gson = new Gson();
        try {
            FileHandle file =Gdx.files.internal("enemydb.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.read()));
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            itemArray = json. getAsJsonArray("enemy");}
            catch (Exception e) {
                System.out.print("Fehler beim Itemauslesen:");
                e.printStackTrace();
            }
            for (int i = 0; i < itemArray.size(); i++) {
                if(typ.equals(itemArray.get(i).getAsJsonObject().get("id").getAsString())){
                JsonObject item = itemArray.get(i).getAsJsonObject();
                    id = item.get("id").getAsString();
                kategorie=item.get("kategorie").getAsInt();
                    maxhp=item.get("maxhp").getAsInt();
                    maxmana=item.get("maxmana").getAsInt();
                            regMana=item.get("regMana").getAsInt();
                    ep=item.get("ep").getAsInt();
                    speed=item.get("speed").getAsInt();
                    schadenNah=item.get("schadenNah").getAsInt();
                    schadenfern=item.get("schadenfern").getAsInt();
                    schadenzauber=item.get("schadenzauber").getAsInt();
                    ruestung=item.get("ruestung").getAsInt();
                    boundsX=item.get("boundsX").getAsInt();
                    boundsY=item.get("boundsY").getAsInt();
                    castSpeed=item.get("castSpeed").getAsFloat();
                    bowSpeed=item.get("bowSpeed").getAsFloat();
                    meleeSpeed=item.get("meleeSpeed").getAsFloat();
                    thrustSpeed=item.get("thrustSpeed").getAsFloat();
                break;}
            }
        switch (kategorie){
            case 1:
                return new com.mygdx.anima.sprites.character.enemies.raider.Raider(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
            case 2:
                return new com.mygdx.anima.sprites.character.enemies.raider.RaiderArcher(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
            case 3:
                return new com.mygdx.anima.sprites.character.enemies.raider.RaiderHealer(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
            default:
                return new com.mygdx.anima.sprites.character.enemies.raider.Raider(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);

        }
        }


    }




