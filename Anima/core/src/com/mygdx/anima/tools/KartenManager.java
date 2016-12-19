package com.mygdx.anima.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.sprites.character.enemies.Enemy;

import static java.lang.System.getProperties;

/**
 * Created by Steffen on 18.12.2016.
 */

public class KartenManager {
    float mapLeft, mapRight, mapTop, mapBottom;
    float cameraHalfWidth, cameraHalfHeight;
    float cameraLeft, cameraRight, cameraTop, cameraBottom;
    MapProperties properties;
    float mapWidth, mapHeight, tilePixelWidth, tilePixelHeight, mapPixelWidth, mapPixelHeight;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    public OrthogonalTiledMapRenderer karteErstellen(int kartenNummer,Viewport gameViewPort){
        if(map!=null){map.dispose();}
        mapLoader = new TmxMapLoader();
        // Gdx.app.log(""+kartenNummer,""+"level/level"+kartenNummer+".tmx");

        map=mapLoader.load("level/level"+kartenNummer+".tmx");

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
        if(mapPixelHeight<  gamecam.viewportWidth)
        {gamecam.position.y = mapTop / 2;}
        else if(cameraBottom <= mapBottom)
        {gamecam.position.y = mapBottom + cameraHalfHeight;}
        else if(cameraTop >= mapTop)
        {gamecam.position.y = mapTop - cameraHalfHeight;}
    }
    public void update(Enemy enemy) {
        if (enemy.getX() < cameraRight && enemy.getX() > cameraLeft
                && enemy.getY() < cameraTop && enemy.getY() > cameraBottom) {
            if (enemy.b2body.isActive() == false)
                enemy.b2body.setActive(true);
        }
    }
    public void isEnemyinRange(Enemy enemy){
        if (enemy.getX() < cameraRight && enemy.getX() > cameraLeft
                && enemy.getY() < cameraTop && enemy.getY() > cameraBottom) {
            if (enemy.b2body.isActive() == false)
                enemy.b2body.setActive(true);
        }
    }
}
