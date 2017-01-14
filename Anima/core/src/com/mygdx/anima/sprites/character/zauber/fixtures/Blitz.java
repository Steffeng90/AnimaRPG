package com.mygdx.anima.sprites.character.zauber.fixtures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.HumanoideSprites;

import static com.mygdx.anima.AnimaRPG.getHeld;
import static com.mygdx.anima.sprites.character.HumanoideSprites.Richtung.Links;
import static com.mygdx.anima.sprites.character.HumanoideSprites.Richtung.Oben;
import static com.mygdx.anima.sprites.character.HumanoideSprites.Richtung.Rechts;
import static com.mygdx.anima.sprites.character.HumanoideSprites.Richtung.Unten;

/**
 * Created by Steffen on 19.11.2016.
 */

public class Blitz extends ZauberFixture {
    Vector2 StartVector,FlugVector;
public Blitz(HumanoideSprites.Richtung richtung,float zauberFixture) {
    super(zauberFixture);
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
    Vector2 koerper=zaubernder.b2body.getPosition();
    switch (richtung){
    case Rechts:
    StartVector = new Vector2(koerper.x+20 / AnimaRPG.PPM,koerper.y -8 / AnimaRPG.PPM);
    FlugVector = new Vector2(100 / AnimaRPG.PPM, 0);break;
    case Links:
    StartVector = new Vector2(koerper.x-20 / AnimaRPG.PPM,koerper.y-8 / AnimaRPG.PPM);
    FlugVector = new Vector2(-100 / AnimaRPG.PPM, 0);break;
    case Oben:
    StartVector = new Vector2(koerper.x,koerper.y +17 / AnimaRPG.PPM);
    FlugVector = new Vector2(0,100 / AnimaRPG.PPM);break;
    case Unten:
    StartVector = new Vector2(koerper.x,koerper.y -33 / AnimaRPG.PPM);
    FlugVector = new Vector2(0, -100 / AnimaRPG.PPM);break;
    default:
    StartVector = new Vector2(0, 0);
    FlugVector = new Vector2(10, 10);break;
    }
    // setBounds(b2body.getPosition().x-radius/AnimaRPG.PPM,b2body.getPosition().y-radius/AnimaRPG.PPM,75 / AnimaRPG.PPM,75/ AnimaRPG.PPM);
    TextureRegion frame1,frame2,frame3,frame4,frame5,frame6;

            frames = new Array<TextureRegion>();
            frame1=new TextureRegion(zauberQuelle, 0, 0, 20, 17);
            frame2=new TextureRegion(zauberQuelle, 20, 0, 25, 17);
            frame3=new TextureRegion(zauberQuelle, 44, 0, 37, 17);
            frame4=new TextureRegion(zauberQuelle, 81, 0, 49, 17);
            frame5=new TextureRegion(zauberQuelle, 130, 0, 46, 17);
            frame6=new TextureRegion(zauberQuelle, 176, 0, 52, 17);
    switch(richtung) {
        case Rechts:
            setBounds(0, 0, 80 / AnimaRPG.PPM, 20/ AnimaRPG.PPM);
            frame1=new TextureRegion(zauberQuelle, 0, 0, 20, 17);
            frame2=new TextureRegion(zauberQuelle, 20, 0, 25, 17);
            frame3=new TextureRegion(zauberQuelle, 44, 0, 37, 17);
            frame4=new TextureRegion(zauberQuelle, 81, 0, 49, 17);
            frame5=new TextureRegion(zauberQuelle, 130, 0, 46, 17);
            frame6=new TextureRegion(zauberQuelle, 176, 0, 52, 17);
            frame1.flip(true,false);
            frame2.flip(true,false);
            frame3.flip(true,false);
            frame4.flip(true,false);
            frame5.flip(true,false);
            frame6.flip(true,false);
            break;
        case Links:
            setBounds(0, 0, 80 / AnimaRPG.PPM, 20/ AnimaRPG.PPM);
            frame1=new TextureRegion(zauberQuelle, 0, 0, 20, 17);
            frame2=new TextureRegion(zauberQuelle, 20, 0, 25, 17);
            frame3=new TextureRegion(zauberQuelle, 44, 0, 37, 17);
            frame4=new TextureRegion(zauberQuelle, 81, 0, 49, 17);
            frame5=new TextureRegion(zauberQuelle, 130, 0, 46, 17);
            frame6=new TextureRegion(zauberQuelle, 176, 0, 52, 17);
            break;
        case Oben:
            setBounds(0, 0, 20 / AnimaRPG.PPM, 80/ AnimaRPG.PPM);
            frame1=new TextureRegion(zauberQuelle, 0, 225, 17, 20);
            frame2=new TextureRegion(zauberQuelle, 0, 201,17, 25);
            frame3=new TextureRegion(zauberQuelle, 0, 164, 17, 37);
            frame4=new TextureRegion(zauberQuelle, 0,116, 17, 49);
            frame5=new TextureRegion(zauberQuelle,0, 69, 17, 46);
            frame6=new TextureRegion(zauberQuelle, 0, 22, 17, 52);
            frame1.flip(true,false);
            frame2.flip(true,false);
            frame3.flip(true,false);
            frame4.flip(true,false);
            frame5.flip(true,false);
            frame6.flip(true,false);
            break;
        case Unten:
            setBounds(0, 0, 20/ AnimaRPG.PPM, 80/ AnimaRPG.PPM);
            frame1=new TextureRegion(zauberQuelle, 0, 225, 17, 20);
            frame2=new TextureRegion(zauberQuelle, 0, 201,17, 25);
            frame3=new TextureRegion(zauberQuelle, 0, 164, 17, 37);
            frame4=new TextureRegion(zauberQuelle, 0,116, 17, 49);
            frame5=new TextureRegion(zauberQuelle,0, 69, 17, 46);
            frame6=new TextureRegion(zauberQuelle, 0, 22, 17, 52);
            break;
    }
    frames = new Array<TextureRegion>();
    frames.add(frame1);
    frames.add(frame2);
    frames.add(frame3);
    frames.add(frame4);
    frames.add(frame5);
    frames.add(frame6);
    frames.add(frame5);
    frames.add(frame4);
    frames.add(frame3);
    frames.add(frame2);
    frames.add(frame1);

    zauber = new Animation(.1f, frames);
    frames.clear();
    zaubernder.runCasting = true;
    zaubernder.castExists = true;

    PolygonShape shape = new PolygonShape();
    if(richtung== (Oben) ||(richtung==  Unten))
        shape.setAsBox(breite/ AnimaRPG.PPM, laenge / AnimaRPG.PPM, new Vector2(0,0), 0);
    else{
        shape.setAsBox(laenge / AnimaRPG.PPM, breite / AnimaRPG.PPM, new Vector2(0,0), 0);
    }
    fdefAttack = new FixtureDef();
    fdefAttack.filter.categoryBits = AnimaRPG.HERO_CAST_BIT;
    //if(erzeuger instanceof Held) {
        fdefAttack.filter.maskBits = AnimaRPG.ENEMY_BIT | AnimaRPG.OBJECT_BIT | AnimaRPG.BARRIERE_BIT | AnimaRPG.GEBIETSWECHSEL_BIT;
    /*}
    else{
        fdefAttack.filter.maskBits = AnimaRPG.HERO_BIT | AnimaRPG.OBJECT_BIT | AnimaRPG.BARRIERE_BIT | AnimaRPG.GEBIETSWECHSEL_BIT;
    }*/
    fdefAttack.shape = shape;
    fdefAttack.isSensor = true;
    setToDestroy=false;
    destroyed=false;
    allZauber.add(this);
    }
    /*@Override
    public TextureRegion getFrame(float dt){
        switch(richtung){
            case Links:setBounds(0, 0, 20 / AnimaRPG.PPM, 5/ AnimaRPG.PPM);return pfeilLinks;
            case Rechts:setBounds(0, 0, 20 / AnimaRPG.PPM, 5/ AnimaRPG.PPM);return pfeilRechts;
            case Oben:setBounds(0, 0, 5 / AnimaRPG.PPM, 20/ AnimaRPG.PPM);return pfeilUp;
            case Unten:setBounds(0, 0, 5 / AnimaRPG.PPM, 20/ AnimaRPG.PPM);return pfeilDown;
            default: return pfeilRechts;
        }
    }*/
    public void update(float dt)
    { super.update(dt);
        if(b2body!=null) {
            b2body.setLinearVelocity(FlugVector);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            //setRegion(getFrame(dt));
        }
    }
}
