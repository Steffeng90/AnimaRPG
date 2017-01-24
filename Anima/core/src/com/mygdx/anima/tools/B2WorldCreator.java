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
import com.mygdx.anima.sprites.character.enemies.Enemy;
import com.mygdx.anima.sprites.character.enemies.EnemyGenerator;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Gebietswechsel;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Schatztruhe;

import static com.mygdx.anima.AnimaRPG.ARROW_BIT;
import static com.mygdx.anima.AnimaRPG.BARRIERE_BIT;
import static com.mygdx.anima.AnimaRPG.ENEMY_BIT;
import static com.mygdx.anima.AnimaRPG.HERO_BIT;
import static com.mygdx.anima.AnimaRPG.OBJECT_BIT;
import static com.mygdx.anima.screens.Playscreen.activeTruhen;
import static com.mygdx.anima.screens.Playscreen.getMapEinstieg;
import static com.mygdx.anima.screens.Playscreen.truhenPool;

/**
 * Created by Steffen on 09.11.2016.
 */

public class B2WorldCreator {
    World world;
    TiledMap map;
    Body body;
    BodyDef bdef;
    FixtureDef fdef;
    PolygonShape pshape;
    CircleShape cshape;
    public Array<Enemy> allEnemy;
    public Array<Schatztruhe> allSchatztruhen;
    public Array<Gebietswechsel> allAusgang;

    public B2WorldCreator(Playscreen screen) {
        this.world = screen.getWorld();
        this.map = screen.getMap();
        bdef = new BodyDef();
        fdef = new FixtureDef();
        pshape = new PolygonShape();
        cshape=new CircleShape();

        try {
            if (map.getLayers().get("barrieren") != null) {
                //TODO PoliLine Probieren
                /*for (MapObject object : map.getLayers().get("barrieren").getObjects()) {
                     Gdx.app.log("RectFound", "" + object.getClass());
                }*/
                for (MapObject object : map.getLayers().get("barrieren").getObjects().getByType(RectangleMapObject.class)) {

                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    bdef.type = BodyDef.BodyType.StaticBody;
                    bdef.position.set((rect.getX() + rect.getWidth() / 2) / AnimaRPG.PPM, (rect.getY() + rect.getHeight() / 2) / AnimaRPG.PPM);
                    body = world.createBody(bdef);

                    pshape.setAsBox((rect.getWidth() / 2) / AnimaRPG.PPM, (rect.getHeight() / 2) / AnimaRPG.PPM);
                    fdef.shape = pshape;
                    fdef.filter.categoryBits = BARRIERE_BIT;
                    fdef.filter.maskBits = HERO_BIT | ENEMY_BIT | OBJECT_BIT | ARROW_BIT;
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
                    fdef.filter.maskBits = HERO_BIT | ENEMY_BIT | OBJECT_BIT | ARROW_BIT;
                    body.createFixture(fdef);
                }
                for (MapObject object : map.getLayers().get("barrieren").getObjects().getByType(PolylineMapObject.class)) {
                    Gdx.app.log("Trigger bei:", "Chain");
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
                    fdef.filter.maskBits = HERO_BIT | ENEMY_BIT | OBJECT_BIT | ARROW_BIT;
                    body.createFixture(fdef);
                }
                // Keine PolygonShapes verwenden.
            }
        }
            catch(Exception e){
                Gdx.app.log("Error:",e.getMessage());
                Gdx.app.log("Error:",e.getStackTrace().toString());

            }
            // Erzeugen von Ein und Ausgängen
            allAusgang = new Array<Gebietswechsel>();
        /*for (MapObject object : map.getLayers().get("areaborder").getObjects().getByType(RectangleMapObject.class)) {
                int nextMap = Integer.valueOf(object.getProperties().get("nextMap").toString());
                int richtungInt = getAreaInt(object.getProperties().get("ausgang").toString());
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                allAusgang.add(new Gebietswechsel(screen, rect.getX() / AnimaRPG.PPM, rect.getY() / AnimaRPG.PPM, nextMap, richtungInt, rect));
                // Gdx.app.log("Wechsel erzeugt"," ");
            }*/
        for (MapObject object : map.getLayers().get("areaborder").getObjects().getByType(PolylineMapObject.class)) {
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
            fdef.filter.maskBits = HERO_BIT | ENEMY_BIT | OBJECT_BIT | ARROW_BIT;
            body.createFixture(fdef);*/
            int nextMap = Integer.valueOf(object.getProperties().get("nextMap").toString());
            int richtungInt = getAreaInt(object.getProperties().get("ausgang").toString());
            // Rectangle rect = ((RectangleMapObject) object).getRectangle();
            allAusgang.add(new Gebietswechsel(screen,worldVertices, nextMap, richtungInt));

        }
            // Erzeugen von Gegner
            if (map.getLayers().get("enemy") != null) {
                for (MapObject object : map.getLayers().get("enemy").getObjects().getByType(RectangleMapObject.class)) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    EnemyGenerator.generateEnemy(screen, rect.getX() / AnimaRPG.PPM, rect.getY() / AnimaRPG.PPM,object.getProperties().get("typ").toString());
                    Gdx.app.log("Raider erzeugt", "");
                }
            }
            // Erzeugen von Schatztruhen
            //allSchatztruhen = new Array<Schatztruhe>();
            if (map.getLayers().get("schatztruhen") != null) {
                for (MapObject object : map.getLayers().get("schatztruhen").getObjects().getByType(RectangleMapObject.class)) {
                    String inhalt = (String) object.getProperties().get("Inhalt");
                    int typ=Integer.valueOf((String) object.getProperties().get("typ"));
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    Schatztruhe truhe=truhenPool.obtain();
                    truhe.init(screen, rect.getX() / AnimaRPG.PPM, rect.getY() / AnimaRPG.PPM,typ, inhalt);
                    activeTruhen.add(truhe);
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


    public Array<Enemy> getAllEnemies(){ return allEnemy;}
    // public Array<Schatztruhe> getAllSchatztruhen(){ return allSchatztruhen;}
    public void removeEnemy(Enemy enemy){
        allEnemy.removeValue(enemy,true);
    }
    public Array<Gebietswechsel> getAllAusgang() {
        return allAusgang;
    }
    public void setAllAusgang(Array<Gebietswechsel> allAusgang) {
        this.allAusgang = allAusgang;
    }
    public int getAreaInt(String richtung){
        // Wenn hier Häuser hinzugefügt werden, dann muss auch getAusgangsrichtung in Gebietswechsel angepasst werden
        if (richtung.equals("sued")) {return 1;
        } else if (richtung.equals("west")) {return 2;
        } else if (richtung.equals("nord")) {return 3;
        } else if (richtung.equals("ost")) {return 4;
        } else if (richtung.equals("haus1")) {return 9001;
        } else if (richtung.equals("haus2")) {return 9002;
        } else if (richtung.equals("haus3")) {return 9003;
        } else if (richtung.equals("haus4")) {return 9004;
        } else if (richtung.equals("haus5")) {return 9005;
        } else if (richtung.equals("haus6")) {return 9006;
        } else if (richtung.equals("haus7")) {return 9007;
        } else if (richtung.equals("haus8")) {return 9008;
        } else if (richtung.equals("haus9")) {return 9009;
        } else if (richtung.equals("haus10")) {return 9010;

        } else{ return 2;}
    }
}
