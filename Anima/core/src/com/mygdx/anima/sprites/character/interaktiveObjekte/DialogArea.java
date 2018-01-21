package com.mygdx.anima.sprites.character.interaktiveObjekte;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.DialogGenerator;

import static com.badlogic.gdx.Input.Keys.L;
import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 13.11.2016.
 */

public class DialogArea {
    Playscreen screen;
    Body body;
    private String id;
    private int vorbed,nachbedfalse,nachbedtrue;

    public DialogArea(Playscreen screen, Vector2[]worldVertices, String id, int vorbed,int nachbedfalse,int nachbedtrue) {
        this.id = id;
        this.vorbed=vorbed;
        this.nachbedfalse=nachbedfalse;
        this.nachbedtrue=nachbedtrue;
        this.screen=screen;
        BodyDef bdef = new BodyDef();
        body = screen.getWorld().createBody(bdef);
        body.setActive(true);

        FixtureDef fdef = new FixtureDef();
        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        fdef.filter.categoryBits = AnimaRPG.OBJECT_BIT;
        fdef.filter.maskBits = AnimaRPG.HERO_BIT;
        fdef.shape = chain;
        fdef.isSensor=true;
        body.createFixture(fdef).setUserData(this);
    }

    public String getId() {
        return id;
    }
    public int getVorbed() {
        return vorbed;
    }

    public void checkForEvents(){

        System.out.println("DialogAreaCheck"+vorbed+"ist auf"+getHeld().getEventListEntryValue(vorbed));
        if(getHeld().getEventListEntryValue(vorbed)){
            System.out.println("Diag1");
            createDialog();
            System.out.println("Diag2");
        }
    }
    public void createDialog(){
        DialogGenerator.generateDialog(screen, screen.getGame().batch, id);
        getHeld().changeEventListEntry(nachbedfalse,false,screen);
        getHeld().changeEventListEntry(nachbedtrue,true,screen);
        System.out.println("Dialog erzeugt und Event"+nachbedtrue+"auf true");

    }
}