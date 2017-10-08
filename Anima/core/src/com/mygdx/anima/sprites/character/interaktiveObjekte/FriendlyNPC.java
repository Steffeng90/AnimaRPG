package com.mygdx.anima.sprites.character.interaktiveObjekte;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.DialogGenerator;
import com.mygdx.anima.sprites.character.Held;

import static com.mygdx.anima.AnimaRPG.ARROW_BIT;
import static com.mygdx.anima.AnimaRPG.HERO_BIT;
import static com.mygdx.anima.AnimaRPG.NPC_BIT;
import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 02.02.2017.
 */


public class FriendlyNPC extends Sprite implements Pool.Poolable{
    public Playscreen screen;
    public World world;
    BodyDef bdef;
    public Body body;
    public TextureRegion standingDownSprite, standingUpSprite,
            standingLeftSprite, standingRightSprite,profilBild;
    public String dialogID;
    public FriendlyNPC() {}
    public void init(Playscreen screen, Rectangle rect, String typ,String dialogID){
        this.screen=screen;
        world=screen.getWorld();
        setPosition(rect.getX(),rect.getY());
        this.setBounds(0, 0, 42 / AnimaRPG.PPM, 42/ AnimaRPG.PPM);
        bdef= new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set( (rect.getX()+(rect.getWidth()/2))/ AnimaRPG.PPM, (rect.getY()+(rect.getHeight()/2))/AnimaRPG.PPM);
        body = world.createBody(bdef);
        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(7f/AnimaRPG.PPM);
        shape.setPosition(new Vector2(0,-12/AnimaRPG.PPM));
        fdef.filter.categoryBits=AnimaRPG.NPC_BIT;
        fdef.filter.maskBits= AnimaRPG.HERO_BIT |AnimaRPG.HERO_SENSOR;
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);


        shape.setPosition(new Vector2(0,4.5f/AnimaRPG.PPM));
        fdef.filter.categoryBits=AnimaRPG.NPC_BIT;
        fdef.filter.maskBits=AnimaRPG.HERO_BIT;
        fdef.shape=shape;
        body.createFixture(fdef).setUserData(this);;


        fdef.filter.categoryBits = NPC_BIT;
        fdef.filter.maskBits = HERO_BIT;
        body.createFixture(fdef);
        Texture quelle=new Texture("FriendlyNPC/"+typ+".png");
        int breite=64;
        setRegion(new TextureRegion(quelle,0,0,breite,breite));
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        standingDownSprite = new TextureRegion(quelle, 0, 128, breite, breite);
        standingUpSprite = new TextureRegion(quelle, 0,0, breite, breite);
        standingLeftSprite = new TextureRegion(quelle, 0, 64, breite, breite);
        standingRightSprite = new TextureRegion(quelle, 0, 192, breite, breite);
        setRegion(standingDownSprite);
        profilBild= new TextureRegion(standingDownSprite,20,8,24,32 );
        this.dialogID=dialogID;
    }
    @Override
    public void reset() {

    }
    public void update() {
        Held hero = getHeld();
        int richtung;
        if (hero.getX() < getX()) {
            if (hero.getY() < getY()) {
                if ((Math.abs(hero.getX() - getX()) <= Math.abs(hero.getY() - getY()))) {
                    richtung=3;
                } else {
                    richtung=0;
                }
            } else {
                if ((Math.abs(hero.getX() - getX()) <= Math.abs(hero.getY() - getY()))) {
                    richtung=2;
                } else {
                    richtung=0;
                }
            }
        } else {
            if (hero.getY() < getY()) {
                if ((Math.abs(hero.getX() - getX()) <= Math.abs(hero.getY() - getY()))) {
                    richtung=3;
                } else {
                    richtung=1;
                }
            } else {
                if ((Math.abs(hero.getX() - getX()) <= Math.abs(hero.getY() - getY()))) {
                    richtung=2;
                } else {
                    richtung=1;
                }
            }
        }
        switch (richtung) {
            default:
            case 0: setRegion(standingLeftSprite);break;
            case 1: setRegion(standingRightSprite);break;
            case 2: setRegion(standingUpSprite);break;
            case 3: setRegion(standingDownSprite);break;

        }

    }
    public TextureRegion getProfilbild(){
        return profilBild;
    }
    public void talktTo(){
        DialogGenerator.generateDialog(screen, screen.getGame().batch, dialogID,profilBild);

    }
}
