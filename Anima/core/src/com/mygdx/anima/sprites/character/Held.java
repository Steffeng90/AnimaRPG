package com.mygdx.anima.sprites.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.enemies.Enemy;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Arrow;
import com.mygdx.anima.sprites.character.interaktiveObjekte.InteraktivesObjekt;

/**
 * Created by Steffen on 09.11.2016.
 */

public class Held extends HumanoideSprites{
    public boolean objectInReichweite;
    public InteraktivesObjekt object;
    public Playscreen screen;
    public boolean waffe=false;
    public static boolean heldErstellt=false;
    public static String spriteNude="character/female_stock.png",
    spriteStock="character/female_stock.png",
    spriteSword="character/female_sword.png",
    spriteBogen="character/female_bogen.png";
    public boolean isHit;
    public Enemy treffenderEnemy;
    public int hitpoints;

    public Held(Playscreen screen)
    {
        super(screen,spriteStock,true);
        this.screen=screen;
        if(!heldErstellt){
        createHero();
        heldErstellt=true;}
        objectInReichweite=false;
        hitpoints=10;
}
    public TextureRegion getFrame(float dt) {
        return super.getFrame(dt);
    }
    public void createHero(){
        BodyDef bdef=new BodyDef();
        bdef.position.set(30/ AnimaRPG.PPM,30/AnimaRPG.PPM);
        bdef.type=BodyDef.BodyType.DynamicBody;
        b2body=world.createBody(bdef);
        createSensor(true);
        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(10/AnimaRPG.PPM);
        shape.setPosition(new Vector2(0,-12/AnimaRPG.PPM));
        //PolygonShape shape =new PolygonShape();
        //shape.setAsBox(8/ AnimaRPG.PPM,8/AnimaRPG.PPM,new Vector2(0,-10/AnimaRPG.PPM),0);
        fdef.filter.categoryBits=AnimaRPG.HERO_BIT;
        fdef.filter.maskBits=AnimaRPG.BARRIERE_BIT | AnimaRPG.ENEMY_BIT | AnimaRPG.OBJECT_BIT | AnimaRPG.ENEMY_SENSOR | AnimaRPG.ENEMY_ATTACK
        | AnimaRPG.ARROW_BIT;
        fdef.shape=shape;
        b2body.createFixture(fdef).setUserData(this);
    }
    public void update(float dt)
    {
        super.update(dt);
        setRegion(getFrame(dt));
        if(isHit){
            Gdx.app.log("Held wird inUpdate getroffen","");
            getsHit(treffenderEnemy);
            isHit=false;}
        if(!runMeleeAnimation && meleeExists){
            b2body.destroyFixture(meleeFixture);
            meleeExists=false;
        }
        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
    }
    public Richtung getCurrentRichtung(){
        return super.getCurrentRichtung();
    }
    public void setCurrentRichtung(int richtung)
    {
        super.setCurrentRichtung(richtung);
    }
    public void meleeAttack()
    {   if(!meleeExists && !runMeleeAnimation && !runArchery) {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(15 / AnimaRPG.PPM);
        Vector2 richtungsVector;

        switch (getCurrentRichtung()) {
            //Hier sind bei Y immer schon mind. -5 Abzug, weil man es ein bisschen nach unten ziehen muss, um die Mitte der Bodentexture und nicht
            // die der Grafikmitte zu treffen
            case Rechts:
                richtungsVector = new Vector2(15 / AnimaRPG.PPM, -8 / AnimaRPG.PPM);
                break;
            case Links:
                richtungsVector = new Vector2(-15 / AnimaRPG.PPM, -8 / AnimaRPG.PPM);
                break;
            case Oben:
                richtungsVector = new Vector2(0, 7 / AnimaRPG.PPM);
                break;
            case Unten:
                richtungsVector = new Vector2(0, -23 / AnimaRPG.PPM);
                break;
            default:
                richtungsVector = new Vector2(0, 0);
                break;

        }
        circleShape.setPosition(richtungsVector);
        FixtureDef fdefAttack = new FixtureDef();
        fdefAttack.filter.categoryBits = AnimaRPG.HERO_WEAPON_BIT;
        fdefAttack.filter.maskBits = AnimaRPG.ENEMY_BIT;
        fdefAttack.shape = circleShape;
        fdefAttack.isSensor = true;
        meleeFixture = b2body.createFixture(fdefAttack);
        meleeFixture.setUserData(this);
        runMeleeAnimation = true;
        meleeExists = true;
    }}
    public void bowAttack()
    {   if(!meleeExists && !runMeleeAnimation && !runArchery) {
        Vector2 startVector, flugVector;
        Vector2 koerper=b2body.getPosition();
        switch (getCurrentRichtung()) {
            //Hier sind bei Y immer schon mind. -5 Abzug, weil man es ein bisschen nach unten ziehen muss, um die Mitte der Bodentexture und nicht
            // die der Grafikmitte zu treffen
            case Rechts:
                startVector = new Vector2(koerper.x+20 / AnimaRPG.PPM,koerper.y -8 / AnimaRPG.PPM);
                flugVector = new Vector2(150 / AnimaRPG.PPM, 0);
                break;
            case Links:
                startVector = new Vector2(koerper.x-20 / AnimaRPG.PPM,koerper.y-8 / AnimaRPG.PPM);
                flugVector = new Vector2(-150 / AnimaRPG.PPM, 0);
                break;
            case Oben:
                startVector = new Vector2(koerper.x,koerper.y +17 / AnimaRPG.PPM);
                flugVector = new Vector2(0, 150 / AnimaRPG.PPM);
                break;
            case Unten:
                startVector = new Vector2(koerper.x,koerper.y -33 / AnimaRPG.PPM);
                flugVector = new Vector2(0, -150 / AnimaRPG.PPM);
                break;
            default:
                startVector = new Vector2(0, 0);
                flugVector = new Vector2(10, 10);
                break;

        }
        Arrow arrow=new Arrow(world,screen,currentRichtung,startVector,flugVector);
        runArchery= true;
    }}
    public void setObject(InteraktivesObjekt io){
        object=io;
    }
    public void useObject(){
        if(object!=null)
            object.use(this);
    }
    public void spriteWechsel(){
        updateTextures(spriteSword);
            }
    public void spriteBogen(){
        updateTextures(spriteBogen);
    }
    public void getsHit(Enemy enemy){
        getsDamaged();
        }
    public void getsDamaged(){
        hitpoints= hitpoints-2;
        if(hitpoints<=0){
            readyToDie();
        }
    }
    }

