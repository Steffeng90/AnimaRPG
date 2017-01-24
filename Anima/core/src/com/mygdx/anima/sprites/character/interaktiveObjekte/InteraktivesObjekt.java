package com.mygdx.anima.sprites.character.interaktiveObjekte;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;

/**
 * Created by Steffen on 13.11.2016.
 */

public abstract class InteraktivesObjekt extends Sprite {
    protected Playscreen screen;
    protected World world;

    public InteraktivesObjekt()
    {

    }
    public void init(Playscreen screen, float x, float y){
        this.screen=screen;
        this.world=screen.getWorld();

        setPosition(x, y);
        setBounds(getX(),getY(),16/ AnimaRPG.PPM,16/ AnimaRPG.PPM);

    }
    public abstract void defineItem();
    public abstract void use(Held hero);

}
