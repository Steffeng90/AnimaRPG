package com.mygdx.anima.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.enemies.EnemyGenerator;
import com.mygdx.anima.sprites.character.interaktiveObjekte.DialogArea;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Gebietswechsel;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Schatztruhe;
import com.mygdx.anima.sprites.character.interaktiveObjekte.SchatztruhenSpeicherObjekt;
import com.mygdx.anima.sprites.character.interaktiveObjekte.SteinBlock;

import static com.mygdx.anima.AnimaRPG.BARRIERE_BIT;
import static com.mygdx.anima.AnimaRPG.ENEMY_BIT;
import static com.mygdx.anima.AnimaRPG.ARROW_BIT;
import static com.mygdx.anima.AnimaRPG.HERO_BIT;
import static com.mygdx.anima.AnimaRPG.NOTHING_BIT;
import static com.mygdx.anima.AnimaRPG.OBJECT_BIT;
import static com.mygdx.anima.AnimaRPG.UNGEHEUER_BIT;
import static com.mygdx.anima.AnimaRPG.getHeld;
import static com.mygdx.anima.screens.Playscreen.activeSteinblock;
import static com.mygdx.anima.screens.Playscreen.activeTruhen;
import static com.mygdx.anima.screens.Playscreen.getMapEinstieg;
import static com.mygdx.anima.screens.Playscreen.steinblockPool;
import static com.mygdx.anima.screens.Playscreen.truhenPool;
import static com.mygdx.anima.tools.KartenManager.aktuelleKartenId;

/**
 * Created by Steffen on 09.11.2016.
 */

public class B2WorldCreator {
    AnimaRPG game;
    World world;
    TiledMap map;
    Body body;
    BodyDef bdef;
    FixtureDef fdef;
    PolygonShape pshape;
    CircleShape cshape;
    public Array<Gebietswechsel> allAusgang;

    public B2WorldCreator(Playscreen screen) {
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.game=screen.getGame();
        bdef = new BodyDef();
        fdef = new FixtureDef();
        pshape = new PolygonShape();
        cshape=new CircleShape();

        try {
            if (map.getLayers().get("barrieren") != null) {
                for (MapObject object : map.getLayers().get("barrieren").getObjects().getByType(RectangleMapObject.class)) {

                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    bdef.type = BodyDef.BodyType.StaticBody;
                    bdef.position.set((rect.getX() + rect.getWidth() / 2) / AnimaRPG.PPM, (rect.getY() + rect.getHeight() / 2) / AnimaRPG.PPM);
                    body = world.createBody(bdef);

                    pshape.setAsBox((rect.getWidth() / 2) / AnimaRPG.PPM, (rect.getHeight() / 2) / AnimaRPG.PPM);
                    fdef.shape = pshape;
                    fdef.filter.categoryBits = BARRIERE_BIT;
                    fdef.filter.maskBits = HERO_BIT | ENEMY_BIT | OBJECT_BIT | ARROW_BIT |UNGEHEUER_BIT | NOTHING_BIT;
                    body.createFixture(fdef);
                }
                for (MapObject object : map.getLayers().get("barrieren").getObjects().getByType(EllipseMapObject.class)) {
                    Ellipse elli = ((EllipseMapObject) object).getEllipse();
                    bdef.type = BodyDef.BodyType.StaticBody;
                    bdef.position.set((elli.x + elli.width / 2) / AnimaRPG.PPM, (elli.y + elli.width / 2) / AnimaRPG.PPM);
                    body = world.createBody(bdef);
                    cshape.setRadius((elli.width / 2) / AnimaRPG.PPM);

                    fdef.shape = cshape;
                    fdef.filter.categoryBits = BARRIERE_BIT;
                    fdef.filter.maskBits = HERO_BIT | ENEMY_BIT | OBJECT_BIT | ARROW_BIT | UNGEHEUER_BIT | NOTHING_BIT;
                    body.createFixture(fdef);
                }
                for (MapObject object : map.getLayers().get("barrieren").getObjects().getByType(PolylineMapObject.class)) {
                    float[] vertices = ((PolylineMapObject) object).getPolyline().getTransformedVertices();

                    Vector2[] worldVertices = new Vector2[vertices.length / 2];
                    body = world.createBody(bdef);
                    for (int i = 0; i < vertices.length / 2; ++i) {
                        worldVertices[i] = new Vector2();
                        worldVertices[i].x = vertices[i * 2] / AnimaRPG.PPM;
                        worldVertices[i].y = vertices[i * 2 + 1] / AnimaRPG.PPM;
                    }
                    bdef.type = BodyDef.BodyType.StaticBody;
                    bdef.position.set((((PolylineMapObject) object).getPolyline().getOriginX()) / AnimaRPG.PPM, (((PolylineMapObject) object).getPolyline().getOriginX()) / AnimaRPG.PPM);
                    body = world.createBody(bdef);
                    ChainShape chain = new ChainShape();
                    chain.createChain(worldVertices);

                    fdef.shape = chain;
                    fdef.filter.categoryBits = BARRIERE_BIT;
                    fdef.filter.maskBits = HERO_BIT | ENEMY_BIT | OBJECT_BIT | ARROW_BIT | UNGEHEUER_BIT | NOTHING_BIT;
                    body.createFixture(fdef);
                }
                // Keine PolygonShapes verwenden.
            }
        }
            catch(Exception e){
                Gdx.app.log("Error:",e.getStackTrace().toString());

            }
            // Erzeugen von Ein und Ausgängen
            allAusgang = new Array<Gebietswechsel>();
        for (MapObject object : map.getLayers().get("areaborder").getObjects().getByType(PolylineMapObject.class)) {
            int vorbed=0,nachbedfalse=0,nachbedtrue=0;
            float[] vertices = ((PolylineMapObject) object).getPolyline().getTransformedVertices();

            Vector2[] worldVertices = new Vector2[vertices.length / 2];
            body = world.createBody(bdef);
            for (int i = 0; i < vertices.length / 2; ++i) {
                worldVertices[i] = new Vector2();
                worldVertices[i].x = vertices[i * 2] / AnimaRPG.PPM;
                worldVertices[i].y = vertices[i * 2 + 1] / AnimaRPG.PPM;
            }
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((((PolylineMapObject) object).getPolyline().getOriginX()) / AnimaRPG.PPM, (((PolylineMapObject) object).getPolyline().getOriginX()) / AnimaRPG.PPM);
            body = world.createBody(bdef);
            ChainShape chain = new ChainShape();
            chain.createChain(worldVertices);

            /*fdef.shape = chain;
            fdef.filter.maskBits = HERO_BIT | ENEMY_BIT | OBJECT_BIT
            body.createFixture(fdef);*/
            if(object.getProperties().containsKey("nextMap")){
                int nextMap = Integer.valueOf(object.getProperties().get("nextMap").toString());
                int richtungInt = getAreaInt(object.getProperties().get("ausgang").toString());
                // Rectangle rect = ((RectangleMapObject) object).getRectangle();
                allAusgang.add(new Gebietswechsel(screen,worldVertices, nextMap, richtungInt));
            }
            else if (object.getProperties().containsKey("dialog")){
                String dialogID=object.getProperties().get("dialog").toString();

                if(object.getProperties().containsKey("vorbed")){
                    vorbed=Integer.parseInt(object.getProperties().get("vorbed").toString());
                }
                if(object.getProperties().containsKey("nachbedfalse")){
                    nachbedfalse=Integer.parseInt(object.getProperties().get("nachbedfalse").toString());
                }
                if(object.getProperties().containsKey("nachbedtrue")){
                    nachbedtrue=Integer.parseInt(object.getProperties().get("nachbedtrue").toString());
                }
                new DialogArea(screen,worldVertices,dialogID,vorbed,nachbedfalse,nachbedtrue);
            }
        }
            // Erzeugen von Gegner
            if (map.getLayers().get("enemyHumanoid") != null) {
                for (MapObject object : map.getLayers().get("enemyHumanoid").getObjects().getByType(RectangleMapObject.class)) {
                    int nachbedfalse=0,nachbedtrue=0,vorbedfalse=0,vorbedtrue=0;
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();

                    // LOGIK FÜR NCPS
                    if(object.getProperties().get("typ").toString().contains("npc")){
                        if(object.getProperties().containsKey("vorbedfalse") && object.getProperties().containsKey("vorbedtrue"))
                        {
                            vorbedfalse= Integer.parseInt(object.getProperties().get("vorbedfalse").toString());
                            vorbedtrue= Integer.parseInt(object.getProperties().get("vorbedtrue").toString());
                            if(getHeld().getEventListEntryValue(vorbedfalse)==false && getHeld().getEventListEntryValue(vorbedtrue)==true){
                                EnemyGenerator.generateNPC(screen, rect,object.getProperties().get("typ").toString(),
                                        object.getProperties().get("dialog").toString());
                            }
                        }
                        else if(object.getProperties().containsKey("vorbedfalse"))
                        {
                            vorbedfalse= Integer.parseInt(object.getProperties().get("vorbedfalse").toString());
                            if(getHeld().getEventListEntryValue(vorbedfalse)==false){
                                EnemyGenerator.generateNPC(screen, rect,object.getProperties().get("typ").toString(),
                                        object.getProperties().get("dialog").toString());
                            }
                        }else if(object.getProperties().containsKey("vorbedtrue")){
                            vorbedtrue= Integer.parseInt(object.getProperties().get("vorbedtrue").toString());
                            if(getHeld().getEventListEntryValue(vorbedtrue)==true){
                                EnemyGenerator.generateNPC(screen, rect,object.getProperties().get("typ").toString(),
                                        object.getProperties().get("dialog").toString());
                            }
                        }
                        else{
                            EnemyGenerator.generateNPC(screen, rect,object.getProperties().get("typ").toString(),
                                    object.getProperties().get("dialog").toString());
                        }
                    }
                    // LOGIK FÜR ENEMYS
                    else if(object.getProperties().containsKey("vorbedfalse") && object.getProperties().containsKey("vorbedtrue") && object.getProperties().containsKey("nachbed") && object.getProperties().containsKey("aktivEvent")) {
                        vorbedfalse=Integer.parseInt(object.getProperties().get("vorbedfalse").toString());
                        vorbedtrue=Integer.parseInt(object.getProperties().get("vorbedtrue").toString());
                        if(getHeld().getEventListEntryValue(vorbedtrue)==true && getHeld().getEventListEntryValue(vorbedfalse)==true){
                            EnemyGenerator.generateEnemy(screen,(rect.getX()+(rect.getWidth()/2))/ AnimaRPG.PPM, (rect.getY()+(rect.getHeight()/2)) / AnimaRPG.PPM,object.getProperties().get("typ").toString(),Integer.parseInt(object.getProperties().get("nachbed").toString()),Integer.parseInt(object.getProperties().get("aktivEvent").toString()));
                        }}
                    else if(object.getProperties().containsKey("vorbedfalse") && object.getProperties().containsKey("vorbedtrue") && object.getProperties().containsKey("nachbed")) {
                        vorbedfalse=Integer.parseInt(object.getProperties().get("vorbedfalse").toString());
                        vorbedtrue=Integer.parseInt(object.getProperties().get("vorbedtrue").toString());
                        if(getHeld().getEventListEntryValue(vorbedtrue)==true && getHeld().getEventListEntryValue(vorbedfalse)==true){
                            EnemyGenerator.generateEnemy(screen,(rect.getX()+(rect.getWidth()/2))/ AnimaRPG.PPM, (rect.getY()+(rect.getHeight()/2)) / AnimaRPG.PPM,object.getProperties().get("typ").toString(),Integer.parseInt(object.getProperties().get("nachbed").toString()),0);
                        }}
                    else if(object.getProperties().containsKey("vorbedfalse") && object.getProperties().containsKey("vorbedtrue") && object.getProperties().containsKey("aktivEvent")) {
                        vorbedfalse=Integer.parseInt(object.getProperties().get("vorbedfalse").toString());
                        vorbedtrue=Integer.parseInt(object.getProperties().get("vorbedtrue").toString());
                        if(getHeld().getEventListEntryValue(vorbedtrue)==true && getHeld().getEventListEntryValue(vorbedfalse)==true){
                            EnemyGenerator.generateEnemy(screen, (rect.getX()+(rect.getWidth()/2))/ AnimaRPG.PPM, (rect.getY()+(rect.getHeight()/2)) / AnimaRPG.PPM,object.getProperties().get("typ").toString(),0,Integer.parseInt(object.getProperties().get("aktivEvent").toString()));
                        }}
                    else if(object.getProperties().containsKey("vorbedfalse") && object.getProperties().containsKey("vorbedtrue")) {
                        vorbedfalse=Integer.parseInt(object.getProperties().get("vorbedfalse").toString());
                        vorbedtrue=Integer.parseInt(object.getProperties().get("vorbedtrue").toString());
                        if(getHeld().getEventListEntryValue(vorbedtrue)==true && getHeld().getEventListEntryValue(vorbedfalse)==true){
                            EnemyGenerator.generateEnemy(screen, (rect.getX()+(rect.getWidth()/2))/ AnimaRPG.PPM, (rect.getY()+(rect.getHeight()/2)) / AnimaRPG.PPM,object.getProperties().get("typ").toString(),0,0);
                        }}
                    else if(object.getProperties().containsKey("vorbedfalse") && !object.getProperties().containsKey("vorbedtrue") && object.getProperties().containsKey("nachbed") && object.getProperties().containsKey("aktivEvent")) {
                        vorbedfalse=Integer.parseInt(object.getProperties().get("vorbedfalse").toString());
                        if(getHeld().getEventListEntryValue(vorbedfalse)==false){
                            EnemyGenerator.generateEnemy(screen,(rect.getX()+(rect.getWidth()/2))/ AnimaRPG.PPM, (rect.getY()+(rect.getHeight()/2)) / AnimaRPG.PPM,object.getProperties().get("typ").toString(),Integer.parseInt(object.getProperties().get("nachbed").toString()),Integer.parseInt(object.getProperties().get("aktivEvent").toString()));
                        }}
                    else if(object.getProperties().containsKey("vorbedfalse") && !object.getProperties().containsKey("vorbedtrue") && object.getProperties().containsKey("nachbed")) {
                        vorbedfalse=Integer.parseInt(object.getProperties().get("vorbedfalse").toString());
                        if(getHeld().getEventListEntryValue(vorbedfalse)==false){
                            EnemyGenerator.generateEnemy(screen,(rect.getX()+(rect.getWidth()/2))/ AnimaRPG.PPM, (rect.getY()+(rect.getHeight()/2)) / AnimaRPG.PPM,object.getProperties().get("typ").toString(),Integer.parseInt(object.getProperties().get("nachbed").toString()),0);
                        }}
                    else if(object.getProperties().containsKey("vorbedfalse") && !object.getProperties().containsKey("vorbedtrue") && object.getProperties().containsKey("aktivEvent")) {
                        vorbedfalse=Integer.parseInt(object.getProperties().get("vorbedfalse").toString());
                        if(getHeld().getEventListEntryValue(vorbedfalse)==false){
                            EnemyGenerator.generateEnemy(screen, (rect.getX()+(rect.getWidth()/2))/ AnimaRPG.PPM, (rect.getY()+(rect.getHeight()/2)) / AnimaRPG.PPM,object.getProperties().get("typ").toString(),0,Integer.parseInt(object.getProperties().get("aktivEvent").toString()));
                        }}
                    else if(object.getProperties().containsKey("vorbedfalse") &&!object.getProperties().containsKey("vorbedtrue")) {
                        vorbedfalse=Integer.parseInt(object.getProperties().get("vorbedfalse").toString());
                        if(getHeld().getEventListEntryValue(vorbedfalse)==false){
                            EnemyGenerator.generateEnemy(screen, (rect.getX()+(rect.getWidth()/2))/ AnimaRPG.PPM, (rect.getY()+(rect.getHeight()/2)) / AnimaRPG.PPM,object.getProperties().get("typ").toString(),0,0);
                        }}
                    else if(object.getProperties().containsKey("vorbedtrue") && object.getProperties().containsKey("nachbed") && object.getProperties().containsKey("aktivEvent")) {
                        vorbedtrue=Integer.parseInt(object.getProperties().get("vorbedtrue").toString());
                        if(getHeld().getEventListEntryValue(vorbedtrue)==true){
                            EnemyGenerator.generateEnemy(screen,(rect.getX()+(rect.getWidth()/2))/ AnimaRPG.PPM, (rect.getY()+(rect.getHeight()/2)) / AnimaRPG.PPM,object.getProperties().get("typ").toString(),Integer.parseInt(object.getProperties().get("nachbed").toString()),Integer.parseInt(object.getProperties().get("aktivEvent").toString()));
                        }}
                    else if(object.getProperties().containsKey("vorbedtrue") && object.getProperties().containsKey("nachbed")) {
                        vorbedtrue=Integer.parseInt(object.getProperties().get("vorbedtrue").toString());
                        if(getHeld().getEventListEntryValue(vorbedtrue)==true){
                            EnemyGenerator.generateEnemy(screen,(rect.getX()+(rect.getWidth()/2))/ AnimaRPG.PPM, (rect.getY()+(rect.getHeight()/2)) / AnimaRPG.PPM,object.getProperties().get("typ").toString(),Integer.parseInt(object.getProperties().get("nachbed").toString()),0);
                        }}
                    else if(object.getProperties().containsKey("vorbedtrue") && object.getProperties().containsKey("aktivEvent")) {
                        vorbedtrue=Integer.parseInt(object.getProperties().get("vorbedtrue").toString());
                        if(getHeld().getEventListEntryValue(vorbedtrue)==true){
                            EnemyGenerator.generateEnemy(screen, (rect.getX()+(rect.getWidth()/2))/ AnimaRPG.PPM, (rect.getY()+(rect.getHeight()/2)) / AnimaRPG.PPM,object.getProperties().get("typ").toString(),0,Integer.parseInt(object.getProperties().get("aktivEvent").toString()));
                        }}
                    else if(object.getProperties().containsKey("vorbedtrue")) {
                        vorbedtrue=Integer.parseInt(object.getProperties().get("vorbedtrue").toString());
                        if(getHeld().getEventListEntryValue(vorbedtrue)==true){
                            EnemyGenerator.generateEnemy(screen, (rect.getX()+(rect.getWidth()/2))/ AnimaRPG.PPM, (rect.getY()+(rect.getHeight()/2)) / AnimaRPG.PPM,object.getProperties().get("typ").toString(),0,0);
                        }}
                    else {
                        EnemyGenerator.generateEnemy(screen, (rect.getX()+(rect.getWidth()/2))/ AnimaRPG.PPM, (rect.getY()+(rect.getHeight()/2)) / AnimaRPG.PPM,object.getProperties().get("typ").toString(),0,0);
                    }
                }

            }
            // Erzeugen von Schatztruhen
            if (map.getLayers().get("schatztruhen") != null) {
                for (MapObject object : map.getLayers().get("schatztruhen").getObjects().getByType(RectangleMapObject.class)) {
                    if (object.getProperties().containsKey("Inhalt")) {
                        int truhenId = (Integer) object.getProperties().get("id");
                        boolean isClosed = true;
                        if (getHeld() != null) {
                            for (SchatztruhenSpeicherObjekt objekt : getHeld().getGeoeffneteTruhen()) {
                                if (aktuelleKartenId == objekt.getMapID() && truhenId == objekt.getTruhenID()) {
                                    isClosed = false;
                                }
                            }
                        }
                        int vorbed = 0, nachbedfalse = 0, nachbedtrue = 0;
                        if (object.getProperties().containsKey("vorbed")) {
                            vorbed = Integer.parseInt((String) object.getProperties().get("vorbed"));
                        }
                        if (object.getProperties().containsKey("nachbedtrue")) {
                            nachbedtrue = Integer.parseInt((String) object.getProperties().get("nachbedtrue"));
                        }
                        if (object.getProperties().containsKey("nachbedfalse")) {
                            nachbedfalse = Integer.parseInt((String) object.getProperties().get("nachbedfalse"));
                        }
                        String inhalt = (String) object.getProperties().get("Inhalt");
                        System.out.println("SchatztruhenObjekt: erzeugt"+(String) object.getProperties().get("typ"));
                        int typ = Integer.valueOf((String) object.getProperties().get("typ"));
                        Rectangle rect = ((RectangleMapObject) object).getRectangle();
                        Schatztruhe truhe = truhenPool.obtain();
                        if (vorbed == 0 || (vorbed != 0 && getHeld().getEventListEntryValue(vorbed) == true)) {
                            truhe.init(screen, rect.getX() / AnimaRPG.PPM, rect.getY() / AnimaRPG.PPM, typ, truhenId, inhalt, isClosed, nachbedfalse, nachbedtrue);
                            activeTruhen.add(truhe);
                        }
                    } else {
                        if (object.getProperties().containsKey("vorbed")) {
                            int vorbed = Integer.parseInt(object.getProperties().get("vorbed").toString());
                            if (getHeld().getEventListEntryValue(vorbed) == false) {
                                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                                SteinBlock block= steinblockPool.obtain();
                                if (object.getProperties().containsKey("typ")) {
                                    String typ =object.getProperties().get("typ").toString();
                                    block.init(screen,rect.getX() / AnimaRPG.PPM, rect.getY() / AnimaRPG.PPM,typ);}
                                else{
                                    block.init(screen,rect.getX() / AnimaRPG.PPM, rect.getY() / AnimaRPG.PPM);
                                }

                                activeSteinblock.add(block);
                                System.out.println("Steinblock erzeugt");
                            }
                        }
                    }
                }
            }
            if (screen.getSpieler() != null) {
                for (MapObject object : map.getLayers().get("startpunkt").getObjects().getByType(RectangleMapObject.class)) {
                    int richtungInt = getAreaInt(object.getProperties().get("start").toString());
                    if (richtungInt == getMapEinstieg()) {
                        Rectangle rect = ((RectangleMapObject) object).getRectangle();
                        screen.getSpieler().createHeroBody(new Vector2((rect.getX() + rect.getWidth() / 2) / AnimaRPG.PPM, (rect.getY() + rect.getHeight()) / AnimaRPG.PPM));
                        break;
                    }
                }
            }
        }
    public int getAreaInt(String richtung){
        // Wenn hier Häuser hinzugefügt werden, dann muss auch getAusgangsrichtung in Gebietswechsel angepasst werden
        // Jeder return Wert muss eindeutig sein, das hat 0 mit den Map Nummern zu tun. Nimm einfach eine eindeutige Nummer..
        if (richtung.equals("sued")) {return 1;
        } else if (richtung.equals("west")) {return 2;
        } else if (richtung.equals("nord")) {return 3;
        } else if (richtung.equals("ost")) {return 4;
        } else if (richtung.equals("haus1")) {return 9001;
        } else if (richtung.equals("haus2")) {return 9002;
        } else if (richtung.equals("haus3")) {return 9003;
        } else if (richtung.equals("haus4")) {return 9004;
        } else if (richtung.equals("haus4AB")) {return 90041;
        } else if (richtung.equals("haus5")) {return 9005;
        } else if (richtung.equals("haus6")) {return 9006;
        } else if (richtung.equals("dungeon1A")) {return 9007;
        } else if (richtung.equals("dungeon1AB")) {return 9008;
        } else if (richtung.equals("dungeon1BA")) {return 90081;

        } else if (richtung.equals("dungeon1CB")) {return 9009;
        } else if (richtung.equals("dungeon1BC")) {return 90091;

        } else{ return 2;}
    }
}
