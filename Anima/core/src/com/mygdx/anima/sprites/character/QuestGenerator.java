package com.mygdx.anima.sprites.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mygdx.anima.scenes.DialogFenster;
import com.mygdx.anima.scenes.QuestInfo;
import com.mygdx.anima.screens.Playscreen;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.badlogic.gdx.Gdx.app;

/**
 * Created by Steffen on 01.12.2016.
 */

public class QuestGenerator {
    private static String name,belohnung;
    private static int questAktivierungsEvent,id;
    private static QuestPart[] questParts;

    public static void generateQuest(Playscreen screen, SpriteBatch sb, int event) {
        Gson gson = new Gson();
        try {
            FileHandle file = Gdx.files.internal("quest.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.read()));
            JsonObject json = gson.fromJson(reader, JsonObject.class);

            JsonArray questArray = json.getAsJsonArray("quests");
            for (int i = 0; i < questArray.size(); i++) {

                if (event==(questArray.get(i).getAsJsonObject().get("questAktivierungsEvent").getAsInt())) {
                    System.out.println("QuestID "+event);
                    JsonObject quest = questArray.get(i).getAsJsonObject();
                    id= quest.get("id").getAsInt();
                    name = quest.get("name").getAsString();
                    belohnung = quest.get("belohnung").getAsString();
                    JsonArray temp=quest.get("questParts").getAsJsonArray();
                    int size=temp.size();
                    System.out.println("Size"+size);
                    questParts=new QuestPart[size];
                    for(int y=0;y<size;y++){
                        questParts[y]=new QuestPart(temp.get(y).getAsJsonObject().get("abschlussEvent").getAsInt(),temp.get(y).getAsJsonObject().get("inhalt").getAsString());
                        }
                    screen.getSpieler().getQuestlog().addQuest(new Quest(id,name,belohnung,questParts));
                    screen.setQuestInfo(new QuestInfo(screen, screen.getGame().batch, name, "Neuer Quest"));
                }
            }
        }
        catch (Exception e){
            System.out.print("Fehler beim Quest auslesen:");
            e.printStackTrace();
        }
    }


    }

