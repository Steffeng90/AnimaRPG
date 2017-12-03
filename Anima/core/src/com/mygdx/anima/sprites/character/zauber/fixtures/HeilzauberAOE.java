package com.mygdx.anima.sprites.character.zauber.fixtures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.enemies.EnemyHumanoid;

/**
 * Created by Steffen on 19.11.2016.
 */

public class HeilzauberAOE extends ZauberFixture {

public HeilzauberAOE(EnemyHumanoid enemyHumanoid, Playscreen screen, World world) {
    super(0.8f);
    this.zaubernder= enemyHumanoid;
    this.world=world;
    this.screen=screen;
    bdef=new BodyDef();
    bdef.position.set(zaubernder.b2body.getPosition().x,zaubernder.b2body.getPosition().y+7/AnimaRPG.PPM);
    bdef.type = BodyDef.BodyType.DynamicBody;
    b2body =world.createBody(bdef);
    b2body.setActive(true);
    radius=38;
    stateTimer=0;
    zauberQuelle = screen.getGame().getAssetManager().get("objekte/heilung_versuch2.png",Texture.class);

    setBounds(b2body.getPosition().x-radius/AnimaRPG.PPM-0.17f,b2body.getPosition().y-radius/AnimaRPG.PPM,100 / AnimaRPG.PPM,100/ AnimaRPG.PPM);
    frames = new Array<TextureRegion>();

    frames.add(new TextureRegion(zauberQuelle, 0,0,35, 45));
    frames.add(new TextureRegion(zauberQuelle,35,0, 36, 45));
    frames.add(new TextureRegion(zauberQuelle,71 ,0, 43,45));
    frames.add(new TextureRegion(zauberQuelle, 114,0, 39, 45));
    frames.add(new TextureRegion(zauberQuelle, 153, 0, 40, 45));
    frames.add(new TextureRegion(zauberQuelle, 193, 0, 42, 45));
    frames.add(new TextureRegion(zauberQuelle, 235, 0, 47, 45));


    zauber = new Animation(0.1f, frames);
    frames.clear();
    zaubernder.runCasting = true;
    zaubernder.castExists = true;

    CircleShape circleShape = new CircleShape();
    circleShape.setRadius(radius / AnimaRPG.PPM);
    // circleShape.setPosition(new Vector2(0,100/AnimaRPG.PPM));
    fdefAttack = new FixtureDef();
    fdefAttack.filter.categoryBits = AnimaRPG.ENEMY_CAST_HEAL;
    fdefAttack.filter.maskBits = AnimaRPG.ENEMY_BIT;
    fdefAttack.shape = circleShape;
    fdefAttack.isSensor = true;

    setToDestroy=false;
    destroyed=false;
    com.mygdx.anima.sprites.character.zauber.fixtures.ZauberFixture.allZauber.add(this);
}

}
