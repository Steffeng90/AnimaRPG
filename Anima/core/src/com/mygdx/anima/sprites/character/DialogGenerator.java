package com.mygdx.anima.sprites.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mygdx.anima.scenes.DialogFenster;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.items.Amulett;
import com.mygdx.anima.sprites.character.items.Benutzbar;
import com.mygdx.anima.sprites.character.items.Brust;
import com.mygdx.anima.sprites.character.items.Handschuhe;
import com.mygdx.anima.sprites.character.items.Helm;
import com.mygdx.anima.sprites.character.items.ItemSprite;
import com.mygdx.anima.sprites.character.items.Schuhe;
import com.mygdx.anima.sprites.character.items.WaffeFern;
import com.mygdx.anima.sprites.character.items.WaffeNah;

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

public class DialogGenerator {
    private static String id="",sprecher,inhalt,nachfolger;
    private static Vector2 vector;
    public static void generateDialog(Playscreen screen, SpriteBatch sb,String id ) {
        Gson gson = new Gson();
        try {
            FileHandle file = Gdx.files.internal("dialog.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.read()));
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            Gdx.app.log("Im generator", "");

            JsonArray itemArray = json.getAsJsonArray("dialoge");
            for (int i = 0; i < itemArray.size(); i++) {
                Gdx.app.log("Inf for Schleife", "");

                if (id.equals(itemArray.get(i).getAsJsonObject().get("id").getAsString())) {
                    Gdx.app.log("Wenn iff erkannt", "");

                    JsonObject item = itemArray.get(i).getAsJsonObject();
                    nachfolger = item.get("nachfolger").getAsString();
                    sprecher = item.get("sprecher").getAsString();
                    inhalt = item.get("inhalt").getAsString();

                screen.setActiveDialog(new DialogFenster(screen,sb,nachfolger,sprecher,inhalt));
                }
            }
        }
        catch (Exception e){
            System.out.print("Fehler beim Dialog auslesen:");
            e.printStackTrace();
        }
    }


    }

