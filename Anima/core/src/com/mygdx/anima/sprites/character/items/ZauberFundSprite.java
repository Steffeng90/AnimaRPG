package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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



public class ZauberFundSprite extends Sprite {
    protected Playscreen screen;
    protected World world;
    Body body;
    public TextureRegion texture;
    public Texture spriteQuelle;
    public int spriteBreite, spriteHoehe;
    public String name;

    public ZauberFundSprite(Playscreen screen, float x, float y, Vector2 textureArea, String name,String menuIconSource){
        this.screen=screen;
        this.world=screen.getWorld();

        setPosition(x, y);
        setBounds(getX(),getY(),16/ AnimaRPG.PPM,16/ AnimaRPG.PPM);
        if(menuIconSource.equals("standard")){
            spriteQuelle=screen.getGame().getAssetManager().get("objekte/icons_for_rpg.png");
            this.name=name;
            spriteBreite=34;
            spriteHoehe =34;
            texture=new TextureRegion(spriteQuelle,((int)textureArea.x)*spriteBreite,((int)textureArea.y)*spriteHoehe,spriteBreite,spriteHoehe);
        }
        else{
            TextureAtlas atlas=screen.getGame().getAssetManager().get("objekte/casts/castingIcons.atlas");
            this.name=name;
            spriteBreite=128;
            spriteHoehe =128;
            texture=new TextureRegion(atlas.findRegion(menuIconSource),((int)textureArea.x)*spriteBreite,((int)textureArea.y)*spriteHoehe,spriteBreite,spriteHoehe);

        }

        setRegion(texture);
        defineItem();
    }
    public void defineItem() {
        BodyDef bdef= new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        body =world.createBody(bdef);
        body.setActive(true);
        FixtureDef fdef= new FixtureDef();
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(8/ AnimaRPG.PPM,8/AnimaRPG.PPM,new Vector2(getWidth()/2, getHeight()/2),0);
        fdef.filter.categoryBits=AnimaRPG.NOTHING_BIT;

        fdef.shape=shape;
    }
    public void update(float dt){
    }
    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
    public void itemDispose(){
       // body.destroyFixture(fdef);
    }
}