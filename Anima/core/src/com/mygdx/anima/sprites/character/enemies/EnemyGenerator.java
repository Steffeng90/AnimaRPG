package com.mygdx.anima.sprites.character.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.enemies.raider.Raider;
import com.mygdx.anima.sprites.character.enemies.raider.RaiderArcher;
import com.mygdx.anima.sprites.character.enemies.raider.RaiderBoss;
import com.mygdx.anima.sprites.character.enemies.raider.RaiderHealer;
import com.mygdx.anima.sprites.character.enemies.ungeheuer.Bat;
import com.mygdx.anima.sprites.character.enemies.ungeheuer.Eyeball;
import com.mygdx.anima.sprites.character.interaktiveObjekte.FriendlyNPC;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Steffen on 01.12.2016.
 */

public class EnemyGenerator {

    private static String itemName="Default", id;
    private static Vector2 vector;
    //Kategorie 1: Nahkampf, 2: Fernkampf 3: Brust, 4: Benutzbar
    //Benutzbarkateogie 1: Lebenspunkte
    private static int kategorie,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,ruestung,schadenbigAttack;
    private static float castSpeed,bowSpeed,meleeSpeed,thrustSpeed,boundsX,boundsY;
    //  private static kategorie itemKategorie=waffe;
    private static JsonArray itemArray;
    public static void generateEnemy(Playscreen screen, float x, float y, String typ) {
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
                    schadenbigAttack=item.get("schadenbigAttack").getAsInt();
                    ruestung=item.get("ruestung").getAsInt();
                    boundsX=item.get("boundsX").getAsFloat();
                    boundsY=item.get("boundsY").getAsFloat();
                    castSpeed=item.get("castSpeed").getAsFloat();
                    bowSpeed=item.get("bowSpeed").getAsFloat();
                    meleeSpeed=item.get("meleeSpeed").getAsFloat();
                    thrustSpeed=item.get("thrustSpeed").getAsFloat();
                break;}
            }
        //EnemyHumanoid enemy=Playscreen.enemyPool.obtain();
        switch (kategorie){
            default:
            case 1:
                Raider raider=NPCPool.getRaiderPool().obtain();
                raider.init(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,schadenbigAttack,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
                Playscreen.activeRaider.add(raider);break;
            case 2:
                RaiderArcher raiderArcher=NPCPool.getRaiderArcherPool().obtain();
                raiderArcher.init(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,schadenbigAttack,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
                Playscreen.activeRaiderArcher.add(raiderArcher);break;
            case 3:
                RaiderHealer raiderHealer=NPCPool.getRaiderHealerPool().obtain();
                raiderHealer.init(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,schadenbigAttack,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
                Playscreen.activeRaiderHealer.add(raiderHealer);break;
            case 4:
                RaiderBoss raiderBoss=NPCPool.getRaiderBossPool().obtain();
                raiderBoss.init(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,schadenbigAttack,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
                Playscreen.activeRaiderBoss.add(raiderBoss);break;
            case 101:
                Bat bat=NPCPool.getBatPool().obtain();
                bat.init(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
                Playscreen.activeBat.add(bat);break;
            case 102:
                Eyeball eyeball=NPCPool.getEyeballPool().obtain();
                eyeball.init(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
                Playscreen.activeEyeball.add(eyeball);break;
           /* case 103:
                Ghost ghost=NPCPool.getEyeballPool().obtain();
                ghost.init(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
                Playscreen.activeGhost.add(ghost);break;
            case 104:
                Pumpkin pumpkin=NPCPool.getEyeballPool().obtain();
                pumpkin.init(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
                Playscreen.activePumpkin.add(pumpkin);break;
            case 105:
                Plant plant=NPCPool.getEyeballPool().obtain();
                plant.init(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
                Playscreen.activePlant.add(plant);break;
            case 106:
                Slime slime=NPCPool.getEyeballPool().obtain();
                slime.init(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
                Playscreen.activeSlime.add(slime);break;
            case 107:
                AngryBee angryBee=NPCPool.getEyeballPool().obtain();
                angryBee.init(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
                Playscreen.activeAngryBee.add(angryBee);break;
            case 108:
                Snake snake=NPCPool.getEyeballPool().obtain();
                snake.init(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
                Playscreen.activeSnake.add(snake);break;
            case 109:
                WormSmall wormSmall=NPCPool.getEyeballPool().obtain();
                wormSmall.init(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
                Playscreen.activeWormSmall.add(wormSmall);break;
            case 110:
                WormBig wormBig=NPCPool.getEyeballPool().obtain();
                wormBig.init(screen,x,y,id,maxhp,maxmana,regMana,ep,speed,schadenNah,schadenfern,schadenzauber,ruestung,boundsX,boundsY,castSpeed,bowSpeed,meleeSpeed,thrustSpeed);
                Playscreen.activeWormBig.add(wormBig);break;*/
        }
        }
    public static void generateNPC(Playscreen screen, Rectangle rect, String typ,String dialogID) {
        FriendlyNPC friendlyNPC=NPCPool.getFriendlyNPCPool().obtain();
        friendlyNPC.init(screen,rect,typ,dialogID);
        Playscreen.activeNPC.add(friendlyNPC);
    }
    }




