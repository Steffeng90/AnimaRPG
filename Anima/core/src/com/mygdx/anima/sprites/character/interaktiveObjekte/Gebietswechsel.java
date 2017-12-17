package com.mygdx.anima.sprites.character.interaktiveObjekte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;

/**
 * Created by Steffen on 13.11.2016.
 */

public class Gebietswechsel {
    Body body;
    private int nextMapId;
    // 1=süd,2=west,3=nord,4=ost; Wert wird in Getter-Methoden umgedreht
    private int ausgangsrichtung;

    public Gebietswechsel(Playscreen screen,Vector2[] worldVertices, int nextMap,int richtung) {
        this.nextMapId = nextMap;
        ausgangsrichtung=richtung;
        BodyDef bdef = new BodyDef();
//        bdef.type = BodyDef.BodyType.StaticBody;
        body = screen.getWorld().createBody(bdef);
        body.setActive(true);

        FixtureDef fdef = new FixtureDef();
        //PolygonShape pshape = new PolygonShape();
        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);

        //pshape.setAsBox((v2.x-v1.x)/ AnimaRPG.PPM, (v2.y/v1.y) / AnimaRPG.PPM);
        fdef.filter.categoryBits = AnimaRPG.OBJECT_BIT;
        fdef.filter.maskBits = AnimaRPG.HERO_BIT | AnimaRPG.ENEMY_BIT | AnimaRPG.ARROW_BIT;
        fdef.shape = chain;
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
            case 9001: return 9001;
            case 9002: return 9002;
            case 9003: return 9003;
            case 9004: return 9004;
            case 9005: return 9005;
            case 9006: return 9006;
            case 9007: return 9007;
            case 9008: return 9008;
            case 9009: return 9009;
            case 9010: return 9010;
            default: Gdx.app.log("Fehler bei",""+this);return 1;
        }
        }
    public void setAusgangsrichtung(int ausgangsrichtung) {this.ausgangsrichtung = ausgangsrichtung;}
}