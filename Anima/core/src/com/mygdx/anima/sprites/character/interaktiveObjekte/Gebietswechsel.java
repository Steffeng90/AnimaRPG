package com.mygdx.anima.sprites.character.interaktiveObjekte;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.items.ItemGenerator;

import static com.mygdx.anima.AnimaRPG.GEBIETSWECHSEL_BIT;

/**
 * Created by Steffen on 13.11.2016.
 */

public class Gebietswechsel extends InteraktivesObjekt {

    Texture spriteQuelle;
    Body body;
    Animation openTruhe;

    public String inhalt;

    public Gebietswechsel(Playscreen screen, float x, float y, String inhalt){
        super(screen,x,y);
        this.inhalt=inhalt;
    }
    @Override
    public void defineItem() {
        BodyDef bdef= new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        body =world.createBody(bdef);
        body.setActive(true);

        FixtureDef fdef= new FixtureDef();
        PolygonShape shape=new PolygonShape();
        shape.setAsBox(8/ AnimaRPG.PPM,8/AnimaRPG.PPM,new Vector2(getWidth()/2, getHeight()/2),0);
        fdef.filter.categoryBits=AnimaRPG.GEBIETSWECHSEL_BIT;
        fdef.filter.maskBits=AnimaRPG.HERO_BIT | AnimaRPG.ENEMY_BIT | AnimaRPG.ARROW_BIT;
        fdef.shape=shape;

        body.createFixture(fdef).setUserData(this);
        }
    public void update(float dt){}
    @Override
    public void use(Held hero) {}
}
