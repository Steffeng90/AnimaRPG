package com.mygdx.anima.sprites.character.zauber.fixtures;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.HumanoideSprites;

import static com.mygdx.anima.AnimaRPG.getHeld;
import static com.mygdx.anima.sprites.character.SpriteVorlage.Richtung.Oben;
import static com.mygdx.anima.sprites.character.SpriteVorlage.Richtung.Unten;

/**
 * Created by Steffen on 19.11.2016.
 */

public class Blitz extends ZauberFixture {
public Blitz(HumanoideSprites.Richtung richtung,float zauberFixture) {
    super(zauberFixture);


    this.zaubernder=getHeld();
    this.world=zaubernder.world;
    zaubernder.anima.getAssetManager().get("audio/sounds/electricity.wav", Sound.class).play();
    this.screen=zaubernder.screen;
    richtungBestimmen(getHeld());
    bdef=new BodyDef();
    bdef.position.set(zauberStartVector);
    bdef.type = BodyDef.BodyType.DynamicBody;
    b2body =world.createBody(bdef);
    b2body.setActive(true);
    laenge = 80;
    breite = 20;
    radius=60;
    stateTimer=0;
    rueckstoss=3;
    zauberQuelle =screen.getGame().getAssetManager().get("objekte/blitz.png",Texture.class);
    initialTexture=new TextureRegion(zauberQuelle,0, 0, 20, 17);
    setRegion(initialTexture);
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
            setBounds(0, 0, laenge / AnimaRPG.PPM, breite/ AnimaRPG.PPM);
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
            setBounds(0, 0, laenge / AnimaRPG.PPM, breite/ AnimaRPG.PPM);
            frame1=new TextureRegion(zauberQuelle, 0, 0, 20, 17);
            frame2=new TextureRegion(zauberQuelle, 20, 0, 25, 17);
            frame3=new TextureRegion(zauberQuelle, 44, 0, 37, 17);
            frame4=new TextureRegion(zauberQuelle, 81, 0, 49, 17);
            frame5=new TextureRegion(zauberQuelle, 130, 0, 46, 17);
            frame6=new TextureRegion(zauberQuelle, 176, 0, 52, 17);
            break;
        case Oben:
            setBounds(0, 0, breite / AnimaRPG.PPM, laenge/ AnimaRPG.PPM);
            frame1=new TextureRegion(zauberQuelle, 0, 225, 17, 20);
            frame2=new TextureRegion(zauberQuelle, 0, 201,17, 25);
            frame3=new TextureRegion(zauberQuelle, 0, 164, 17, 37);
            frame4=new TextureRegion(zauberQuelle, 0,116, 17, 49);
            frame5=new TextureRegion(zauberQuelle,0, 69, 17, 46);
            frame6=new TextureRegion(zauberQuelle, 0, 22, 17, 52);
            frame1.flip(false,true);
            frame2.flip(false,true);
            frame3.flip(false,true);
            frame4.flip(false,true);
            frame5.flip(false,true);
            frame6.flip(false,true);
            break;
        case Unten:
            setBounds(0, 0, breite/ AnimaRPG.PPM, laenge/ AnimaRPG.PPM);
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

    zauber = new Animation(.1f, frames);
    frames.clear();
    zaubernder.runCasting = true;
    zaubernder.castExists = true;

    PolygonShape shape = new PolygonShape();
    if(richtung== (Oben) ||(richtung==  Unten))
        shape.setAsBox(0.5f*breite/ AnimaRPG.PPM,0.5f* laenge / AnimaRPG.PPM, new Vector2(0,0), 0);
    else{
        shape.setAsBox(0.5f*laenge / AnimaRPG.PPM, 0.5f*breite / AnimaRPG.PPM, new Vector2(0,0), 0);
    }
    fdefAttack = new FixtureDef();
    fdefAttack.filter.categoryBits = AnimaRPG.HERO_CAST_BIT;
    fdefAttack.filter.maskBits = AnimaRPG.ENEMY_BIT | AnimaRPG.OBJECT_BIT | AnimaRPG.BARRIERE_BIT | AnimaRPG.UNGEHEUER_BIT;

    fdefAttack.shape = shape;
    fdefAttack.isSensor = true;
    setToDestroy=false;
    destroyed=false;
    allZauber.add(this);
    }
    public void update(float dt)
    { super.update(dt);
        if(b2body!=null) {
            b2body.setLinearVelocity(zauberFlugVector);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            //setRegion(getFrame(dt));
        }
    }
    @Override
    public void richtungBestimmen(Held held)
    {
        Vector2 koerper=held.b2body.getPosition();
        switch (held.getCurrentRichtung()) {
            //Hier sind bei Y immer schon mind. -5 Abzug, weil man es ein bisschen nach unten ziehen muss, um die Mitte der Bodentexture und nicht
            // die der Grafikmitte zu treffen
            case Rechts:
                zauberStartVector = new Vector2(koerper.x+40 / AnimaRPG.PPM,koerper.y -8 / AnimaRPG.PPM);
                zauberFlugVector = new Vector2(200 / AnimaRPG.PPM, 0);break;
            case Links:
                zauberStartVector = new Vector2(koerper.x-40 / AnimaRPG.PPM,koerper.y-8 / AnimaRPG.PPM);
                zauberFlugVector = new Vector2(-200 / AnimaRPG.PPM, 0);break;
            case Oben:
                zauberStartVector = new Vector2(koerper.x,koerper.y +30 / AnimaRPG.PPM);
                zauberFlugVector = new Vector2(0, 200 / AnimaRPG.PPM);break;
            case Unten:
                zauberStartVector = new Vector2(koerper.x,koerper.y -30 / AnimaRPG.PPM);
                zauberFlugVector= new Vector2(0, -200 / AnimaRPG.PPM);break;
            default:
                zauberStartVector = koerper;
                zauberFlugVector = new Vector2(10, 10);break;
        }
    }
}
