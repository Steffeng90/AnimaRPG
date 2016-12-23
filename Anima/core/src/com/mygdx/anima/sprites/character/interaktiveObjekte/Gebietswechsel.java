package com.mygdx.anima.sprites.character.interaktiveObjekte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
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

public class Gebietswechsel {
    Body body;
    private int nextMapId;
    // 1=süd,2=west,3=nord,4=ost; Wert wird in Getter-Methoden umgedreht
    private int ausgangsrichtung;

    public Gebietswechsel(Playscreen screen, float x, float y, int nextMap,int richtung, Rectangle rect) {
        this.nextMapId = nextMap;
        ausgangsrichtung=richtung;
        BodyDef bdef = new BodyDef();
        bdef.position.set((rect.getX()+rect.getWidth()/2)/ AnimaRPG.PPM, (rect.getY()+rect.getHeight()/2)/AnimaRPG.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        body = screen.getWorld().createBody(bdef);
        body.setActive(true);

        FixtureDef fdef = new FixtureDef();
        PolygonShape pshape = new PolygonShape();
        pshape.setAsBox((rect.getWidth() / 2) / AnimaRPG.PPM, (rect.getHeight() / 2) / AnimaRPG.PPM);
        fdef.filter.categoryBits = AnimaRPG.GEBIETSWECHSEL_BIT;
        fdef.filter.maskBits = AnimaRPG.HERO_BIT | AnimaRPG.ENEMY_BIT | AnimaRPG.ARROW_BIT;
        fdef.shape = pshape;
        body.createFixture(fdef).setUserData(this);
    }
    public int getNextMapId() {return nextMapId;}
    public void setNextMapId(int inhalt) {this.nextMapId = nextMapId;}
    public int getAusgangsrichtung() {
        //Hier wird der int wert umgedreht: Aus Nord wird Süd
        switch(ausgangsrichtung){
            case 1:return 3;
            case 2:return 4;
            case 3:return 1;
            case 4:return 2;
            case 10: return 10;
            default: Gdx.app.log("Fehler bei",""+this);return 1;
        }
        }
    public void setAusgangsrichtung(int ausgangsrichtung) {this.ausgangsrichtung = ausgangsrichtung;}
}