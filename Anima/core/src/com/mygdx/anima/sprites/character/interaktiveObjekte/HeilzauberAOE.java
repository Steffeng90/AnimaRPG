package com.mygdx.anima.sprites.character.interaktiveObjekte;

import com.badlogic.gdx.Screen;
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
import com.mygdx.anima.sprites.character.enemies.Enemy;

import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;
import static com.mygdx.anima.sprites.character.interaktiveObjekte.HeilzauberAOE.allZauber;

/**
 * Created by Steffen on 19.11.2016.
 */

public class HeilzauberAOE extends Zauber {

public HeilzauberAOE(Enemy enemy,Playscreen screen) {
    this.zaubernder=enemy;
    this.world=screen.getWorld();
    this.screen=screen;
    bdef=new BodyDef();
    bdef.position.set(zaubernder.b2body.getPosition().x,zaubernder.b2body.getPosition().y);
    bdef.type = BodyDef.BodyType.DynamicBody;
    b2body =world.createBody(bdef);
    b2body.setActive(true);

    radius=38;
    stateTimer=0;
    zauberQuelle = new Texture("objekte/heilung.png");

    setBounds(b2body.getPosition().x-radius/AnimaRPG.PPM,b2body.getPosition().y-radius/AnimaRPG.PPM,75 / AnimaRPG.PPM,75/ AnimaRPG.PPM);
    frames = new Array<TextureRegion>();

    frames.add(new TextureRegion(zauberQuelle, 0,0,32, 34));
    frames.add(new TextureRegion(zauberQuelle,33,0, 36, 34));
    frames.add(new TextureRegion(zauberQuelle,69 ,0, 26,49));
    frames.add(new TextureRegion(zauberQuelle, 112,0, 13, 39));
    frames.add(new TextureRegion(zauberQuelle, 151, 0, 25, 40));
    frames.add(new TextureRegion(zauberQuelle, 191, 0, 25, 41));

    zauber = new Animation(.18f, frames);
    frames.clear();
    zaubernder.runCasting = true;
    zaubernder.castExists = true;

    CircleShape circleShape = new CircleShape();
    circleShape.setRadius(radius / AnimaRPG.PPM);
    // circleShape.setPosition(new Vector2(0,0));
     fdefAttack = new FixtureDef();
    fdefAttack.filter.categoryBits = AnimaRPG.ENEMY_CAST_HEAL;
    fdefAttack.filter.maskBits = AnimaRPG.ENEMY_BIT;
    fdefAttack.shape = circleShape;
    fdefAttack.isSensor = true;

    setToDestroy=false;
    destroyed=false;
    Zauber.allZauber.add(this);
}

}
