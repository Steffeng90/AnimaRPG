package com.mygdx.anima.sprites.character.interaktiveObjekte;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.HumanoideSprites;


/**
 * Created by Steffen on 18.11.2016.
 */

public class Arrow extends Sprite implements Pool.Poolable {
    protected Playscreen screen;
    protected World world;
    public Body b2body;
    public Vector2 startVector,flugVector;
    public HumanoideSprites.Richtung richtung;
    public HumanoideSprites erzeuger;
    Fixture arrowFixture;
    public boolean setToDestroy,destroyed;
    TextureRegion pfeilLinks,pfeilRechts,pfeilUp,pfeilDown;
    Texture pfeilQuelle;
    public float laenge,breite;

    public Arrow() {
        super();
    }
    public void init(World world, Playscreen screen, HumanoideSprites.Richtung richtung,Vector2 startVector,Vector2 flugVector,HumanoideSprites erzeuger){
        this.world=world;
        this.screen=screen;
        this.richtung = richtung;
        this.startVector=startVector;
        this.flugVector=flugVector;
        this.erzeuger=erzeuger;
        setToDestroy=false;
        destroyed=false;
        laenge=8;breite=2;
        BodyDef bdef=new BodyDef();
        bdef.position.set(startVector);
        bdef.type =BodyDef.BodyType.DynamicBody;
        b2body =world.createBody(bdef);
        b2body.setActive(true);
        b2body.setLinearVelocity(flugVector);

        pfeilRechts=screen.getGame().getAssetManager().getPfeilRechts();
        pfeilLinks=screen.getGame().getAssetManager().getPfeilLinks();

        pfeilUp=screen.getGame().getAssetManager().getPfeilUp();
        pfeilDown=screen.getGame().getAssetManager().getPfeilDown();
        setRegion(getFrame());
        PolygonShape shape = new PolygonShape();
        if(this.richtung== (HumanoideSprites.Richtung.Oben) ||(this.richtung==  HumanoideSprites.Richtung.Unten))
            shape.setAsBox(breite/ AnimaRPG.PPM, laenge / AnimaRPG.PPM, new Vector2(0,0), 0);
        else{
            shape.setAsBox(laenge / AnimaRPG.PPM, breite / AnimaRPG.PPM, new Vector2(0,0), 0);
        }
        FixtureDef fdefAttack = new FixtureDef();

        if(erzeuger instanceof Held) {
            fdefAttack.filter.categoryBits = AnimaRPG.ARROW_BIT;
            fdefAttack.filter.maskBits = AnimaRPG.ENEMY_BIT | AnimaRPG.OBJECT_BIT | AnimaRPG.BARRIERE_BIT | AnimaRPG.UNGEHEUER_BIT | AnimaRPG.ARROW_BIT;
        }
        else{
            fdefAttack.filter.categoryBits = AnimaRPG.ARROW_BIT;
            fdefAttack.filter.maskBits = AnimaRPG.HERO_BIT | AnimaRPG.OBJECT_BIT | AnimaRPG.BARRIERE_BIT | AnimaRPG.UNGEHEUER_BIT | AnimaRPG.ARROW_BIT;

        }
        fdefAttack.shape = shape;
        arrowFixture = b2body.createFixture(fdefAttack);
        arrowFixture.setUserData(this);

       // allArrows.add(this);
    }
    public void reset(){
        this.world=null;
        this.screen=null;
        this.richtung = null;
        this.startVector=null;
        this.flugVector=null;
        this.erzeuger=null;
        setToDestroy=false;
        destroyed=false;
        laenge=0;breite=0;
        //b2body.setActive(false);
        //b2body.setLinearVelocity(0,0);

        pfeilQuelle=null;
        pfeilRechts=null;
        pfeilLinks=null;

        pfeilUp=null;
        pfeilDown=null;
    }
    public void update(float dt)
    {  if(!destroyed)
    {   if(setToDestroy){
            destroyArrow();}
        else{
        b2body.setLinearVelocity(flugVector);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        //setRegion(getFrame(dt));
    }}
    }
    public void destroyArrow (){
        world.destroyBody(this.b2body);
        b2body.setUserData(null);
        b2body=null;
        setToDestroy=false;
        destroyed=true;
      //  allArrows.removeValue(this,true);
    }
    public TextureRegion getFrame(){
        switch(richtung){
            case Links:setBounds(0, 0, 20 / AnimaRPG.PPM, 5/ AnimaRPG.PPM);return pfeilLinks;
            case Rechts:setBounds(0, 0, 20 / AnimaRPG.PPM, 5/ AnimaRPG.PPM);return pfeilRechts;
            case Oben:setBounds(0, 0, 5 / AnimaRPG.PPM, 20/ AnimaRPG.PPM);return pfeilUp;
            case Unten:setBounds(0, 0, 5 / AnimaRPG.PPM, 20/ AnimaRPG.PPM);return pfeilDown;
            default: return pfeilRechts;
        }
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
}
