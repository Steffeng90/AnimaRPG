package com.mygdx.anima.sprites.character.zauber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
import static com.mygdx.anima.sprites.character.items.ItemGenerator.ruestung;
import static com.mygdx.anima.sprites.character.items.ItemGenerator.setItemID;
import static com.mygdx.anima.sprites.character.items.ItemGenerator.setItemID;
import static com.mygdx.anima.sprites.character.items.ItemGenerator.setItemID;
import static com.mygdx.anima.sprites.character.items.ItemGenerator.setItemID;
import static com.mygdx.anima.sprites.character.items.ItemGenerator.setItemID;
import static com.mygdx.anima.sprites.character.items.ItemGenerator.setItemID;
import static com.mygdx.anima.sprites.character.items.ItemGenerator.setItemID;*/
import static com.mygdx.anima.AnimaRPG.getHeld;


/**
 * Created by Steffen on 01.12.2016.
 */

public class ZauberGenerator {
    private static String zauberName="Default",beschreibung="defaul";
    private static Vector2 vector;
    //Kategorie 1: Staerkung, 2: Schaden
    private static int kategorie,effektivitaet, manakosten;
    private static float zauberZeit, zauberDauer,zauberFixtureTimer;

    public static void generateZauber(String typ) {
        Gson gson = new Gson();
        try {
            FileHandle file =Gdx.files.internal("zauberdb.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.read()));
            JsonObject json = gson.fromJson(reader, JsonObject.class);

            JsonArray zauberArray = json. getAsJsonArray("zauber");
            for (int i = 0; i < zauberArray.size(); i++) {
                if(typ.equals(zauberArray.get(i).getAsJsonObject().get("id").getAsString())){
                JsonObject zauber= zauberArray.get(i).getAsJsonObject();
                    zauberName = zauber.get("name").getAsString();
                    vector = new Vector2(zauber.get("vectorX").getAsFloat(), zauber.get("vectorY").getAsFloat());
                    effektivitaet=zauber.get("effektivitaet").getAsInt();
                    manakosten=zauber.get("manakosten").getAsInt();
                    zauberZeit=zauber.get("zauberZeit").getAsFloat();
                    zauberDauer=zauber.get("zauberDauer").getAsFloat();
                    kategorie = zauber.get("kategorie").getAsInt();
                    beschreibung=zauber.get("beschreibung").getAsString();
                    zauberFixtureTimer=zauber.get("zauberFixtureTimer").getAsFloat();

               switch (kategorie){
                    case 1:
                        getHeld().getZauberList().addZauber(new ZauberEntity(zauberName,"staerkung", vector, effektivitaet, manakosten,zauberFixtureTimer, zauberZeit, zauberDauer,beschreibung));
                        break;
                    case 2:
                        getHeld().getZauberList().addZauber(new ZauberEntity(zauberName,"schaden", vector, effektivitaet, manakosten,zauberFixtureTimer, zauberZeit, zauberDauer,beschreibung));
                        break;
                }
                break;}
            }
        }
        catch (Exception e){
            System.out.print("Fehler beim ZauberFixture auslesen:");
            e.printStackTrace();
        }
    }


    }

