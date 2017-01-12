package com.mygdx.anima.sprites.character.interaktiveObjekte;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.HumanoideSprites;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 19.11.2016.
 */

public class Blitz extends ZauberFixture {

public Blitz(HumanoideSprites.Richtung richtung) {
    this.zaubernder=getHeld();
    this.world=zaubernder.world;
    this.screen=zaubernder.screen;
    bdef=new BodyDef();
    bdef.position.set(zaubernder.b2body.getPosition().x,zaubernder.b2body.getPosition().y);
    bdef.type = BodyDef.BodyType.DynamicBody;
    b2body =world.createBody(bdef);
    b2body.setActive(true);
    laenge = 20;
    breite = 20;
    radius=60;
    stateTimer=0;
    rueckstoss=3;
    zauberQuelle = new Texture("objekte/blitz.png");
    richtungBestimmen(getHeld());
    initialTexture=new TextureRegion(zauberQuelle,0, 0, 20, 17);
    setRegion(initialTexture);
    setBounds(b2body.getPosition().x-radius/AnimaRPG.PPM,b2body.getPosition().y-radius/AnimaRPG.PPM,(radius*2) / AnimaRPG.PPM,(radius*2)/ AnimaRPG.PPM);

    // setBounds(b2body.getPosition().x-radius/AnimaRPG.PPM,b2body.getPosition().y-radius/AnimaRPG.PPM,75 / AnimaRPG.PPM,75/ AnimaRPG.PPM);
    frames = new Array<TextureRegion>();
    frames.add(new TextureRegion(zauberQuelle, 0, 0, 20, 17));
    frames.add(new TextureRegion(zauberQuelle, 20, 0, 25, 17));
    frames.add(new TextureRegion(zauberQuelle, 44, 0, 37, 17));
    frames.add(new TextureRegion(zauberQuelle, 81, 0, 59, 17));
    frames.add(new TextureRegion(zauberQuelle, 130, 0, 46, 17));
    frames.add(new TextureRegion(zauberQuelle, 176, 0, 52, 17));

    zauber = new Animation(.1f, frames);
    frames.clear();
    zaubernder.runCasting = true;
    zaubernder.castExists = true;

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
    ZauberFixture.allZauber.add(this);
}
}
