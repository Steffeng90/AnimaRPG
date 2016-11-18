package com.mygdx.anima.sprites.character.interaktiveObjekte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.HumanoideSprites;

/**
 * Created by Steffen on 18.11.2016.
 */

public class Arrow extends Sprite {
    private static Array<Arrow> allArrows=new Array<Arrow>();
    protected Playscreen screen;
    protected World world;
    public Body b2body;
    public Vector2 startVector,flugVector;
    public HumanoideSprites.Richtung richtung;
    Fixture arrowFixture;
    public boolean setToDestroy,destroyed;


    public Arrow(World world, Playscreen screen, HumanoideSprites.Richtung richtung,Vector2 startVector,Vector2 flugVector) {
        this.world=world;
        this.screen=screen;
        this.richtung = richtung;
        this.startVector=startVector;
        this.flugVector=flugVector;
        setToDestroy=false;
        destroyed=false;
        BodyDef bdef=new BodyDef();
        bdef.position.set(startVector);
        bdef.type =BodyDef.BodyType.DynamicBody;
        b2body =world.createBody(bdef);
        b2body.setActive(true);
        b2body.setLinearVelocity(flugVector);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8 / AnimaRPG.PPM, 8 / AnimaRPG.PPM, new Vector2(0,0), 0);

        FixtureDef fdefAttack = new FixtureDef();
        fdefAttack.filter.categoryBits = AnimaRPG.ARROW_BIT;
        fdefAttack.filter.maskBits = AnimaRPG.ENEMY_BIT | AnimaRPG.HERO_BIT | AnimaRPG.OBJECT_BIT | AnimaRPG.BARRIERE_BIT;
        fdefAttack.shape = shape;
        arrowFixture = b2body.createFixture(fdefAttack);
        arrowFixture.setUserData(this);
        allArrows.add(this);
    }
    public void update(float dt)
    {  if(!destroyed)
    {   if(setToDestroy){
            destroyArrow();}
        else{
        b2body.setLinearVelocity(flugVector);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        }}
    }
    public static Array<Arrow> getAllArrows(){
        return allArrows;
    }
    public void destroyArrow (){
        world.destroyBody(this.b2body);
        b2body.setUserData(null);
        b2body=null;
      //  Gdx.app.log("Kapuut","");
        setToDestroy=false;
        destroyed=true;
        allArrows.removeValue(this,true);
    }
    public void remove() {
        allArrows.removeValue(this, true);
    }
}
