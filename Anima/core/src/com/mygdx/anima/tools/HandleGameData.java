package com.mygdx.anima.tools;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

import jdk.nashorn.internal.runtime.Context;

import static com.badlogic.gdx.Gdx.files;
import static com.badlogic.gdx.Input.Keys.M;
import static com.mygdx.anima.AnimaRPG.getHeld;
import static java.security.AccessController.getContext;
import static javax.script.ScriptEngine.FILENAME;

/**
 * Created by Steffen on 25.01.2017.
 */

public class HandleGameData {
        public static void speichern(){
            try {
                    GameData data=new GameData();
                    FileHandle file = files.local("save.txt");
                    byte[] dataBytes=data.serialize();
                    file.writeBytes(dataBytes,false);
                System.out.print("Spiel gespeichert");
            }
            catch(Exception e) {
                System.out.print("Fehler beim Heldspeichern:"+e.getMessage());
            }
        }
        public static Playscreen laden(AnimaRPG game) {
            try{
                FileHandle file = files.local("save.txt");
                byte[] dataBytes=file.readBytes();
                GameData gameData=(GameData) GameData.deserialize(dataBytes);
                System.out.print("Spiel geladen");
                return new Playscreen(game,gameData);
            }
            catch(Exception e){
                System.out.print(e.getStackTrace()+"Fehler beim Heldladen:"+e.getMessage());
            }

            return new Playscreen(game);
        }
}
