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

import static com.badlogic.gdx.Input.Keys.M;
import static com.mygdx.anima.AnimaRPG.getHeld;
import static java.security.AccessController.getContext;
import static javax.script.ScriptEngine.FILENAME;

/**
 * Created by Steffen on 25.01.2017.
 */

public class HandleGameData {
        public static void speichern(){
            Gson gson = new Gson();
            try {
               /* FileHandle file = Gdx.files.local("saveGame.json");
                String FILENAME ="saveGame.json";
                String string = "hello world!";
                FileOutputStream fos = new FileOutputStream(new File(getFilesDir(), "tours"));
                javax.naming.Context.
                FileOutputStream fos = openFileOutput(FILENAME, getContext().MODE_PRIVATE);
                fos.write(string.getBytes());
                fos.close();*/
               FileOutputStream fileO;
                try {
                    GameData data=new GameData();
                    fileO = new FileOutputStream("saveGame.json");
                    ObjectOutputStream oas = new ObjectOutputStream(fileO);
                    oas.writeObject(data);
                    oas.close();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println(e.toString());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            catch (Exception e) {
                System.out.print("Fehler beim Heldspeichern:");
                e.printStackTrace();
            }

        }
        public static Playscreen laden(AnimaRPG game) {
            FileInputStream fileI;
            try {
                fileI = new FileInputStream("saveGame.json");
                ObjectInputStream ias = new ObjectInputStream(fileI);
                GameData gameData=(GameData)ias.readObject();
                ias.close();
                return new Playscreen(game,gameData);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println(e.toString());
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println(e.toString());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return new Playscreen(game);
        }
}
