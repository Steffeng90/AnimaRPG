package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;

/**
 * Created by Steffen on 22.11.2016.
 */



public class ItemSprite extends Sprite {
    protected Playscreen screen;
    protected World world;

    public enum Itemtyp{Brot,Pilz,Käse,Fisch,Schwert,Bogen};
    public Itemtyp type;
    Body body;
    public TextureRegion texture;
    public Texture spriteQuelle;
    public int spriteBreite, spriteHoehe;

    public ItemSprite(Playscreen screen, float x, float y,String type){
        this.screen=screen;
        this.world=screen.getWorld();

        setPosition(x, y);
        setBounds(getX(),getY(),16/ AnimaRPG.PPM,16/ AnimaRPG.PPM);
        spriteQuelle=new Texture("objekte/icons_for_rpg.png");
        spriteBreite=34;
        spriteHoehe =34;
        this.type=Itemtyp.valueOf(type);
        defineItem();
    }
    public void defineItem() {
        BodyDef bdef= new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        body =world.createBody(bdef);
        body.setActive(true);
        setRegion(getFrame());
        FixtureDef fdef= new FixtureDef();
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(8/ AnimaRPG.PPM,8/AnimaRPG.PPM,new Vector2(getWidth()/2, getHeight()/2),0);
        fdef.filter.categoryBits=AnimaRPG.ITEM_SPRITE_BIT;
        // fdef.filter.maskBits=AnimaRPG.HERO_BIT | AnimaRPG.ENEMY_BIT | AnimaRPG.HERO_SENSOR | AnimaRPG.ARROW_BIT;
        fdef.shape=shape;

        //body.createFixture(fdef).setUserData(this);
    }
    public void update(float dt){
        Gdx.app.log("uüdatea","");

        setRegion(getFrame());
    }
    public TextureRegion getFrame(){
        TextureRegion region;
        switch(type){
            case Brot:
                region=new TextureRegion(spriteQuelle,7*spriteBreite,1* spriteHoehe,spriteBreite,spriteHoehe);
                break;
            case Pilz:
                region=new TextureRegion(spriteQuelle,5 *spriteBreite,1* spriteHoehe,spriteBreite,spriteHoehe);
                break;
            case Käse:
                region=new TextureRegion(spriteQuelle,9*spriteBreite,1* spriteHoehe,spriteBreite,spriteHoehe);
                break;
            case Fisch:
                region=new TextureRegion(spriteQuelle,10*spriteBreite,1* spriteHoehe,spriteBreite,spriteHoehe);
                break;
            case Schwert:
                region=new TextureRegion(spriteQuelle,0*spriteBreite,5* spriteHoehe,spriteBreite,spriteHoehe);
                break;
            case Bogen:
                region=new TextureRegion(spriteQuelle,0*spriteBreite,11* spriteHoehe,spriteBreite,spriteHoehe);
                break;
            default:
                //Default_traube
                region=new TextureRegion(spriteQuelle,0*spriteBreite,0* spriteHoehe,spriteBreite,spriteHoehe);
                break;
        }
        return region;
    }

    @Override
    public void draw(Batch batch) {
        Gdx.app.log("drwa","");
        super.draw(batch);
    }
    public void itemDispose(){
       // body.destroyFixture(fdef);
    }

}