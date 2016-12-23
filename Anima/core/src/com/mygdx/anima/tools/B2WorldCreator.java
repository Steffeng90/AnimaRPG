package com.mygdx.anima.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
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
import com.mygdx.anima.sprites.character.enemies.Raider;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Gebietswechsel;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Schatztruhe;

import java.awt.geom.Ellipse2D;
import java.security.spec.EllipticCurve;

import static com.badlogic.gdx.graphics.g2d.ParticleEmitter.SpawnShape.ellipse;
import static com.badlogic.gdx.math.Interpolation.circle;
import static com.badlogic.gdx.utils.JsonValue.ValueType.object;
import static com.mygdx.anima.AnimaRPG.ARROW_BIT;
import static com.mygdx.anima.AnimaRPG.BARRIERE_BIT;
import static com.mygdx.anima.AnimaRPG.ENEMY_BIT;
import static com.mygdx.anima.AnimaRPG.HERO_BIT;
import static com.mygdx.anima.AnimaRPG.OBJECT_BIT;
import static com.mygdx.anima.AnimaRPG.currentScreen;
import static com.mygdx.anima.screens.Playscreen.getMapEinstieg;
import static java.lang.System.getProperties;

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
    public Array<Raider> allRaider;
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
                for (MapObject object : map.getLayers().get("barrieren").getObjects()) {
                    // Gdx.app.log("RectFound", "" + object.getClass());
                }
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
            // Erzeugen von Ein und Ausgängen
            allAusgang = new Array<Gebietswechsel>();
            for (MapObject object : map.getLayers().get("areaborder").getObjects().getByType(RectangleMapObject.class)) {
                int nextMap = Integer.valueOf(object.getProperties().get("nextMap").toString());
                int richtungInt = getAreaInt(object.getProperties().get("ausgang").toString());
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                allAusgang.add(new Gebietswechsel(screen, rect.getX() / AnimaRPG.PPM, rect.getY() / AnimaRPG.PPM, nextMap, richtungInt, rect));
                // Gdx.app.log("Wechsel erzeugt"," ");
            }
            // Erzeugen von Raider
            allRaider = new Array<Raider>();
            if (map.getLayers().get("raider") != null) {
                for (MapObject object : map.getLayers().get("raider").getObjects().getByType(RectangleMapObject.class)) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    allRaider.add(new Raider(screen, rect.getX() / AnimaRPG.PPM, rect.getY() / AnimaRPG.PPM));
                    //Gdx.app.log("Radider erzeugt", "");
                }
            }
            // Erzeugen von Schatztruhen
            allSchatztruhen = new Array<Schatztruhe>();
            if (map.getLayers().get("schatztruhen") != null) {
                for (MapObject object : map.getLayers().get("schatztruhen").getObjects().getByType(RectangleMapObject.class)) {
                    String inhalt = (String) object.getProperties().get("Inhalt");
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    allSchatztruhen.add(new Schatztruhe(screen, rect.getX() / AnimaRPG.PPM, rect.getY() / AnimaRPG.PPM, inhalt));
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
        catch(Exception e){
            Gdx.app.log("Error:",e.getMessage());
        }
    }
    public Array<Raider> getAllRaider(){ return allRaider;}
    public Array<Schatztruhe> getAllSchatztruhen(){ return allSchatztruhen;}
    public void removeRaider(Raider raider){
        allRaider.removeValue(raider,true);
    }
    public Array<Gebietswechsel> getAllAusgang() {
        return allAusgang;
    }
    public void setAllAusgang(Array<Gebietswechsel> allAusgang) {
        this.allAusgang = allAusgang;
    }
    public int getAreaInt(String richtung){
        if (richtung.equals("sued")) {return 1;
        } else if (richtung.equals("west")) {return 2;
        } else if (richtung.equals("nord")) {return 3;
        } else if (richtung.equals("ost")) {return 4;
        } else if (richtung.equals("haus1")) {return 10;
        } else{ return 2;}
    }
}
