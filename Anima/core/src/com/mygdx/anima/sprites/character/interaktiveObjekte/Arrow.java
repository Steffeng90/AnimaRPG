package com.mygdx.anima.sprites.character.interaktiveObjekte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    TextureRegion pfeilLinks,pfeilRechts,pfeilUp,pfeilDown;
    Texture pfeilQuelle;
    public float laenge,breite;
    //TextureRegion pfeilTexture=new TextureRegion(new Texture("objekte/icons_for_rpg.png"),5,6,53,14);



    public Arrow(World world, Playscreen screen, HumanoideSprites.Richtung richtung,Vector2 startVector,Vector2 flugVector) {
        super();
        this.world=world;
        this.screen=screen;
        this.richtung = richtung;
        this.startVector=startVector;
        this.flugVector=flugVector;
        setToDestroy=false;
        destroyed=false;
        laenge=8;breite=2;
        BodyDef bdef=new BodyDef();
        bdef.position.set(startVector);
        bdef.type =BodyDef.BodyType.DynamicBody;
        b2body =world.createBody(bdef);
        b2body.setActive(true);
        b2body.setLinearVelocity(flugVector);

        pfeilQuelle=new Texture("objekte/arrow.png");
        pfeilRechts=new TextureRegion(pfeilQuelle,5,10,53,9);
        pfeilLinks=new TextureRegion(pfeilQuelle,5,0,53,9);

        pfeilUp=new TextureRegion(pfeilQuelle,15,20,9,53);
        pfeilDown=new TextureRegion(pfeilQuelle,5,20,9,53);
        setRegion(getFrame());
        PolygonShape shape = new PolygonShape();
        if(this.richtung== (HumanoideSprites.Richtung.Oben) ||(this.richtung==  HumanoideSprites.Richtung.Unten))
            shape.setAsBox(breite/ AnimaRPG.PPM, laenge / AnimaRPG.PPM, new Vector2(0,0), 0);
        else{
            shape.setAsBox(laenge / AnimaRPG.PPM, breite / AnimaRPG.PPM, new Vector2(0,0), 0);

        }
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
        //setRegion(getFrame(dt));
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
      //  allArrows.removeValue(this,true);
    }
    public void remove() {
        allArrows.removeValue(this, true);
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
