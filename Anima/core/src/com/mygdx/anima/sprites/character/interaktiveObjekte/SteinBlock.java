package com.mygdx.anima.sprites.character.interaktiveObjekte;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.items.ItemGenerator;
import com.mygdx.anima.sprites.character.zauber.ZauberGenerator;

import static com.mygdx.anima.AnimaRPG.getHeld;
import static com.mygdx.anima.tools.KartenManager.aktuelleKartenId;

/**
 * Created by Steffen on 13.11.2016.
 */

public class SteinBlock extends InteraktivesObjekt implements Pool.Poolable{

    Body body;
    TextureRegion darstellung;

    public SteinBlock(){
       super();
    }
    public void init(Playscreen screen, float x, float y){
        super.init(screen,x,y);
        defineItem(8,8);
        darstellung=screen.getGame().getAssetManager().getSteinblock();
    }
    public void init(Playscreen screen, float x, float y,String typ){


        if(typ.matches("block-fass")){
            super.init(screen,x,y,64,112);
            darstellung=screen.getGame().getAssetManager().getFassblock();
            defineItem(32,56);
        }else{
            super.init(screen,x,y);
            darstellung=screen.getGame().getAssetManager().getSteinblock();
            defineItem(8,8);
        }

    }
    @Override
    public void defineItem(float x,float y) {
        BodyDef bdef= new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        body =world.createBody(bdef);
        body.setActive(true);

        FixtureDef fdef= new FixtureDef();
        PolygonShape shape=new PolygonShape();
       shape.setAsBox(x/ AnimaRPG.PPM,y/AnimaRPG.PPM,new Vector2(getWidth()/2, getHeight()/2),0);
        fdef.filter.categoryBits=AnimaRPG.BARRIERE_BIT;
        fdef.filter.maskBits=AnimaRPG.HERO_BIT | AnimaRPG.ENEMY_BIT | AnimaRPG.OBJECT_BIT | AnimaRPG.ARROW_BIT |AnimaRPG.UNGEHEUER_BIT | AnimaRPG.NOTHING_BIT;
        fdef.shape=shape;

        body.createFixture(fdef).setUserData(this);
        }
    public void reset(){
        body=null;
    }
    public void update(float dt){
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        return darstellung;
}

    @Override
    public void use(Held hero) {

    }
}
