package com.mygdx.anima.tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.sprites.character.enemies.EnemyHumanoid;
import com.mygdx.anima.sprites.character.enemies.EnemyUngeheuer;

import static com.badlogic.gdx.Gdx.app;

/**
 * Created by Steffen on 18.12.2016.
 */

public class KartenManager {
    float mapLeft, mapRight, mapTop, mapBottom;
    float cameraHalfWidth, cameraHalfHeight,enemyActivePuffer;
    float cameraLeft, cameraRight, cameraTop, cameraBottom;
    MapProperties properties;
    float mapWidth, mapHeight, tilePixelWidth, tilePixelHeight, mapPixelWidth, mapPixelHeight;
    public static int aktuelleKartenId;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    public OrthogonalTiledMapRenderer karteErstellen(AnimaRPG game,int kartenNummer,Viewport gameViewPort){
        if(map!=null){map.dispose();}
        mapLoader = new TmxMapLoader();
        aktuelleKartenId=kartenNummer;
        //map=mapLoader.load("level/level"+kartenNummer+".tmx");
        map=game.getAssetManager().loadTiledMap(kartenNummer);
        //Map-Camera-Initialisierung
        properties = map.getProperties();
        mapWidth = properties.get("width", Integer.class);
        mapHeight = properties.get("height", Integer.class);
        tilePixelWidth = properties.get("tilewidth", Integer.class);
        tilePixelHeight = properties.get("tileheight", Integer.class);
        mapPixelWidth = mapWidth * tilePixelWidth / AnimaRPG.PPM;
        mapPixelHeight = mapHeight * tilePixelHeight / AnimaRPG.PPM;

        mapLeft = 0;
        mapRight = 0 + mapPixelWidth;
        mapBottom = 0;
        mapTop = 0 + mapPixelHeight;
        cameraHalfWidth = gameViewPort.getWorldWidth() * .5f;
        cameraHalfHeight = gameViewPort.getWorldHeight() * .5f;
        enemyActivePuffer=tilePixelWidth/AnimaRPG.PPM;
        HandleGameData.speichern();
        return new OrthogonalTiledMapRenderer(map, 1 /AnimaRPG.PPM);
        }
    public TmxMapLoader getMapLoader() {
        return mapLoader;
    }
    public void setMapLoader(TmxMapLoader mapLoader) {
        this.mapLoader = mapLoader;
    }
    public TiledMap getMap() {
        return map;
    }
    public void setMap(TiledMap map) {
        this.map = map;
    }
    public void justiereCam(OrthographicCamera gamecam){
        cameraLeft= gamecam.position.x - cameraHalfWidth;
        cameraRight=gamecam.position.x + cameraHalfWidth;
        cameraTop=gamecam.position.y +cameraHalfHeight;
        cameraBottom=gamecam.position.y-cameraHalfHeight;

        if(mapPixelWidth< gamecam.viewportWidth)
        {gamecam.position.x = mapRight / 2;}
        else if(cameraLeft <= mapLeft)
        {gamecam.position.x = mapLeft + cameraHalfWidth;}
        else if(cameraRight >= mapRight)
        {gamecam.position.x = mapRight - cameraHalfWidth;}

// Vertical axis
        if(mapPixelHeight<  gamecam.viewportHeight)
        {gamecam.position.y = mapTop / 2;}
        else if(cameraBottom <= mapBottom)
        {gamecam.position.y = mapBottom + cameraHalfHeight;}
        else if(cameraTop >= mapTop)
        {gamecam.position.y = mapTop - cameraHalfHeight;}
    }

    public void isEnemyinRange(EnemyHumanoid enemyHumanoid) {
        if (enemyHumanoid.getX() < cameraRight + enemyActivePuffer && enemyHumanoid.getX() > cameraLeft - enemyActivePuffer
                && enemyHumanoid.getY() < cameraTop + enemyActivePuffer && enemyHumanoid.getY() > cameraBottom - enemyActivePuffer) {
            if (enemyHumanoid.b2body.isActive() == false)
                enemyHumanoid.b2body.setActive(true);
        }
    }
    public void isEnemyinRangeUngeheuer(EnemyUngeheuer enemyUngeheuer){
        if (enemyUngeheuer.getX() < cameraRight+enemyActivePuffer && enemyUngeheuer.getX() > cameraLeft-enemyActivePuffer
                && enemyUngeheuer.getY() < cameraTop+enemyActivePuffer && enemyUngeheuer.getY() > cameraBottom-enemyActivePuffer) {
            if (enemyUngeheuer.b2body.isActive() == false)
                enemyUngeheuer.b2body.setActive(true);
        }
    }
}
