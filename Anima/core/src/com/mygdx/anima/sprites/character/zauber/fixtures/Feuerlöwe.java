package com.mygdx.anima.sprites.character.zauber.fixtures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.sprites.character.HumanoideSprites;

import static com.mygdx.anima.AnimaRPG.getHeld;
import static com.mygdx.anima.sprites.character.SpriteVorlage.Richtung.Links;
import static com.mygdx.anima.sprites.character.SpriteVorlage.Richtung.Oben;
import static com.mygdx.anima.sprites.character.SpriteVorlage.Richtung.Rechts;

/**
 * Created by Steffen on 19.11.2016.
 */

public class Feuerlöwe extends ZauberFixture {
    int reichweite;

    public Feuerlöwe(HumanoideSprites.Richtung richtung, float zauberFixture) {

        super(zauberFixture);
        System.out.println("Feuerlöwe wird gezaubert");
        this.zaubernder = getHeld();
        this.world = zaubernder.world;
        //zaubernder.anima.getAssetManager().get("audio/sounds/electricity.wav", Sound.class).play();
        this.screen = zaubernder.screen;
        richtungBestimmen(getHeld());
        bdef = new BodyDef();
        bdef.position.set(zauberStartVector);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        b2body.setActive(true);
        laenge = 64;
        breite = 64;
        radius = 60;
        reichweite = 1200;
        stateTimer = 0;
        rueckstoss = 1;
        switch(richtung)
        {
            case Rechts:
                zauberQuelle = screen.getGame().getAssetManager().get("objekte/casts/firelion_right.png", Texture.class);
                break;
            case Links:
                zauberQuelle = screen.getGame().getAssetManager().get("objekte/casts/firelion_left.png", Texture.class);
                break;
            case Oben:
                zauberQuelle = screen.getGame().getAssetManager().get("objekte/casts/firelion_up.png", Texture.class);
                break;
            case Unten:
                zauberQuelle = screen.getGame().getAssetManager().get("objekte/casts/firelion_down.png", Texture.class);
                break;
            default:
                zauberQuelle = screen.getGame().getAssetManager().get("objekte/casts/firelion_right.png", Texture.class);
                break;

        }
        initialTexture = new TextureRegion(zauberQuelle, 0, 0, laenge, breite);
        setRegion(initialTexture);
        frames = new Array<TextureRegion>();
        setBounds(0, 0, breite / AnimaRPG.PPM, laenge/ AnimaRPG.PPM);
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                frames.add(new TextureRegion(zauberQuelle, x * 128, y * 128, laenge*2, breite*2));
            }
        }

        zauber = new Animation(.1f, frames);
        frames.clear();
        zaubernder.runCasting = true;
        zaubernder.castExists = true;

        PolygonShape shape = new PolygonShape();

            shape.setAsBox(0.5f * breite / AnimaRPG.PPM, 0.5f * laenge / AnimaRPG.PPM, new Vector2(0, 0), 0);
            System.out.println("HX"+(0.5f * breite / AnimaRPG.PPM));
        fdefAttack = new FixtureDef();
        fdefAttack.filter.categoryBits = AnimaRPG.HERO_CAST_BIT;
        fdefAttack.filter.maskBits = AnimaRPG.ENEMY_BIT | AnimaRPG.OBJECT_BIT | AnimaRPG.BARRIERE_BIT | AnimaRPG.UNGEHEUER_BIT;

        fdefAttack.shape = shape;
        fdefAttack.isSensor = true;
        setToDestroy = false;
        destroyed = false;
        allZauber.add(this);
    }

    public void update(float dt) {
        super.update(dt);
        if (b2body != null) {
            // b2body.setLinearVelocity(zauberFlugVector);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            //setRegion(getFrame(dt));
        }
    }

    @Override
    public void richtungBestimmen(Held held) {
        Vector2 koerper = held.b2body.getPosition();
        switch (held.getCurrentRichtung()) {
            //Hier sind bei Y immer schon mind. -5 Abzug, weil man es ein bisschen nach unten ziehen muss, um die Mitte der Bodentexture und nicht
            // die der Grafikmitte zu treffen
            case Rechts:
                zauberStartVector = new Vector2(koerper.x + (40 + reichweite) / AnimaRPG.PPM, koerper.y - 8 / AnimaRPG.PPM);
                zauberFlugVector = new Vector2(50 / AnimaRPG.PPM, 0);
                break;
            case Links:
                zauberStartVector = new Vector2(koerper.x - (40 - reichweite) / AnimaRPG.PPM, koerper.y - 8 / AnimaRPG.PPM);
                zauberFlugVector = new Vector2(-50 / AnimaRPG.PPM, 0);
                break;
            case Oben:
                zauberStartVector = new Vector2(koerper.x, koerper.y + (30 + reichweite) / AnimaRPG.PPM);
                zauberFlugVector = new Vector2(0, 50 / AnimaRPG.PPM);
                break;
            case Unten:
                zauberStartVector = new Vector2(koerper.x, koerper.y - (30 - reichweite) / AnimaRPG.PPM);
                zauberFlugVector = new Vector2(0, -50 / AnimaRPG.PPM);
                break;
            default:
                //zauberStartVector = koerper;
                zauberFlugVector = new Vector2(0, 0);
                break;
        }
    }
}