package com.mygdx.anima.sprites.character.interaktiveObjekte;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;

/**
 * Created by Steffen on 19.11.2016.
 */

public class Nova extends Zauber {

public Nova(Held held) {
    this.zaubernder=held;
    this.world=held.world;
    this.screen=held.screen;
    bdef=new BodyDef();
    bdef.position.set(held.b2body.getPosition().x,held.b2body.getPosition().y);
    bdef.type = BodyDef.BodyType.DynamicBody;
    b2body =world.createBody(bdef);
    b2body.setActive(true);
    laenge = 20;
    breite = 20;
    radius=38;
    stateTimer=0;
    rueckstoss=3;
    zauberQuelle = new Texture("objekte/energieNova.png");
    initialTexture=new TextureRegion(zauberQuelle,13, 463, laenge, breite);
    setRegion(initialTexture);

    setBounds(b2body.getPosition().x-radius/AnimaRPG.PPM,b2body.getPosition().y-radius/AnimaRPG.PPM,75 / AnimaRPG.PPM,75/ AnimaRPG.PPM);
    frames = new Array<TextureRegion>();

    frames.add(new TextureRegion(zauberQuelle, 5, 11, 16, 16));
    frames.add(new TextureRegion(zauberQuelle,24,10, breite, laenge));
    frames.add(new TextureRegion(zauberQuelle,47 , 7, 26, 24));
    frames.add(new TextureRegion(zauberQuelle, 77,13, 13, 13));
    frames.add(new TextureRegion(zauberQuelle, 95, 7, 25, 24));
    frames.add(new TextureRegion(zauberQuelle, 125, 7, 25, 24));
    frames.add(new TextureRegion(zauberQuelle, 154, 7, 25, 24));
    frames.add(new TextureRegion(zauberQuelle, 188, 3, 31, 30));
    frames.add(new TextureRegion(zauberQuelle, 229, 3, 33, 31));
    frames.add(new TextureRegion(zauberQuelle, 272, 2, 36, 33));
    zauber = new Animation(.1f, frames);
    frames.clear();
    held.runCasting = true;
    held.castExists = true;

    CircleShape circleShape = new CircleShape();
    circleShape.setRadius(radius / AnimaRPG.PPM);
    circleShape.setPosition(new Vector2(0,0));
     fdefAttack = new FixtureDef();
    fdefAttack.filter.categoryBits = AnimaRPG.HERO_CAST_BIT;
    fdefAttack.filter.maskBits = AnimaRPG.ENEMY_BIT;
    fdefAttack.shape = circleShape;
    fdefAttack.isSensor = true;

    setToDestroy=false;
    destroyed=false;
    Zauber.allZauber.add(this);
}
}
