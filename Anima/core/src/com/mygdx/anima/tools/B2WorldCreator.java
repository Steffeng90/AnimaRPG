package com.mygdx.anima.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.enemies.Raider;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Gebietswechsel;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Schatztruhe;

import static com.badlogic.gdx.Input.Keys.M;
import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.all;
import static com.badlogic.gdx.utils.JsonValue.ValueType.array;
import static com.mygdx.anima.AnimaRPG.ARROW_BIT;
import static com.mygdx.anima.AnimaRPG.BARRIERE_BIT;
import static com.mygdx.anima.AnimaRPG.ENEMY_BIT;
import static com.mygdx.anima.AnimaRPG.HERO_BIT;
import static com.mygdx.anima.AnimaRPG.OBJECT_BIT;
import static com.mygdx.anima.AnimaRPG.held;
import static com.mygdx.anima.screens.Playscreen.getMapEinstieg;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
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
    public Array<Raider> allRaider;
    public Array<Schatztruhe> allSchatztruhen;
    public Array<Gebietswechsel> allAusgang;

    public B2WorldCreator(Playscreen screen)
    {
        this.world=screen.getWorld();
        this.map=screen.getMap();
        bdef=new BodyDef();
        fdef=new FixtureDef();
        pshape=new PolygonShape();


        for(MapObject object:map.getLayers().get("barrieren").getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect=((RectangleMapObject) object).getRectangle();
            bdef.type=BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ AnimaRPG.PPM, (rect.getY()+rect.getHeight()/2)/AnimaRPG.PPM);
            body=world.createBody(bdef);

            pshape.setAsBox((rect.getWidth()/2)/AnimaRPG.PPM, (rect.getHeight()/2)/AnimaRPG.PPM);
            fdef.shape=pshape;
            fdef.filter.categoryBits=BARRIERE_BIT;
            fdef.filter.maskBits=HERO_BIT | ENEMY_BIT | OBJECT_BIT | ARROW_BIT;
            body.createFixture(fdef);
        }
        if(screen.getSpieler()!=null ) {
            for (MapObject object : map.getLayers().get("startpunkt").getObjects().getByType(RectangleMapObject.class)) {
                int richtungInt;
                String richtung = object.getProperties().get("start").toString();
                if (richtung.equals("sued")) {richtungInt = 1;
                } else if (richtung.equals("west")) {richtungInt = 2;
                } else if (richtung.equals("nord")) {richtungInt = 3;
                } else {richtungInt = 4;}
                if (richtungInt == getMapEinstieg()) {
                    Gdx.app.log("Trigger bei:",""+richtungInt);
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    screen.getSpieler().createHeroBody(new Vector2((rect.getX() + rect.getWidth() / 2) / AnimaRPG.PPM, (rect.getY() + rect.getHeight()) / AnimaRPG.PPM));
                    break;
                }
            }
        }
        // Erzeugen von Ein und Ausgängen
        allAusgang=new Array<Gebietswechsel>();
        for(MapObject object:map.getLayers().get("areaborder").getObjects().getByType(RectangleMapObject.class)) {
            int nextMap=Integer.valueOf(object.getProperties().get("nextMap").toString());
            int richtungInt;
            String richtung = object.getProperties().get("ausgang").toString();
            if (richtung.equals("sued")) {richtungInt = 1;
            } else if (richtung.equals("west")) {richtungInt = 2;
            } else if (richtung.equals("nord")) {richtungInt = 3;
            } else {richtungInt = 4;}
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            allAusgang.add(new Gebietswechsel(screen, rect.getX() / AnimaRPG.PPM, rect.getY() / AnimaRPG.PPM,nextMap,richtungInt,rect));
            Gdx.app.log("Wechsel erzeugt"," ");
        }
        // Erzeugen von Raider
        allRaider=new Array<Raider>();
        for(MapObject object:map.getLayers().get("raider").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            allRaider.add(new Raider(screen, rect.getX() / AnimaRPG.PPM, rect.getY() / AnimaRPG.PPM));
            Gdx.app.log("Radider erzeugt","");
        }
        // Erzeugen von Schatztruhen
        allSchatztruhen=new Array<Schatztruhe>();
        for(MapObject object:map.getLayers().get("schatztruhen").getObjects().getByType(RectangleMapObject.class)) {
            String inhalt=(String)object.getProperties().get("Inhalt");
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            allSchatztruhen.add(new Schatztruhe(screen, rect.getX() / AnimaRPG.PPM, rect.getY() / AnimaRPG.PPM, inhalt));
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
}
