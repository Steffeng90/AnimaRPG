package com.mygdx.anima.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;

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
            body.createFixture(fdef);
        }
    }

}
