package com.mygdx.anima.sprites.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.enemies.Enemy;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Arrow;
import com.mygdx.anima.sprites.character.interaktiveObjekte.InteraktivesObjekt;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Zauber;
import com.mygdx.anima.sprites.character.items.InventarList;
import com.mygdx.anima.tools.SchadenBerechner;

/**
 * Created by Steffen on 09.11.2016.
 */

public class Held extends HumanoideSprites{
    public boolean objectInReichweite;
    public InteraktivesObjekt object;
    public Playscreen screen;
    public boolean waffe=false;
    public boolean heldErstellt=false;
    public static String spriteNude="character/female_stock.png",
    spriteStock="character/female_stock.png",
    spriteSword="character/female_sword.png",
    spriteBogen="character/female_bogen.png";
    public boolean isHit;
    public Enemy treffenderEnemy;
    private InventarList heldenInventar;

    //Statistik-Werte

    private float spielzeit,geschwindigkeitLaufen;

    private int schadenNah,schadenFern,schadenZauber,ruestung,zauberwiderstand,staerke,
    geschick,zauberkraft,currentErfahrung,currentLevel,nextLevelUp;

    float characterTimer;

    public Held(Playscreen screen)
    {
        super(screen,spriteBogen,true);
        this.screen=screen;
        if(!heldErstellt){
        createHero();
        heldErstellt=true;}
        objectInReichweite=false;

        heldenInventar=new InventarList();

        // Gameplay-Variablen
        setMaxHitpoints(125);
        setCurrentHitpoints(getMaxHitpoints());
        setMaxMana(15);
        setCurrentMana(getMaxMana());
        setRegMana(5);
        setCurrentLevel(1);
        setCurrentErfahrung(0);
        setNextLevelUp(50);
        setGeschwindigkeitLaufen(15);
        setStaerke(10);
        setGeschick(12);
        setZauberkraft(8);
        setSchadenNah();
        setSchadenFern();
        setSchadenZauber(15);
        setRuestung();
        setZauberwiderstand(11);
        characterTimer=0;
}
    public TextureRegion getFrame(float dt) {
        return super.getFrame(dt);
    }
    public void createHero(){
        BodyDef bdef=new BodyDef();
        bdef.position.set((AnimaRPG.W_WIDTH/2) / AnimaRPG.PPM,30/AnimaRPG.PPM);
        bdef.type=BodyDef.BodyType.DynamicBody;
        b2body=world.createBody(bdef);
        createSensor(true);
        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(8/AnimaRPG.PPM);
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
        characterTimer+=dt;
        setSpielzeit(dt);
        if(characterTimer>=1 && getCurrentMana()<getMaxMana()){
            setCurrentMana(getCurrentMana()+getRegMana());
            characterTimer=0;}
        super.update(dt);
        setRegion(getFrame(dt));

        if(isHit){
            getsHit(treffenderEnemy);
            isHit=false;}
        if(!runMeleeAnimation && meleeExists){
            b2body.destroyFixture(meleeFixture);
            meleeExists=false;}
        if(!runCasting && castExists){
            castExists=false;}
        if(!destroyed)
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
    {   if(!meleeExists && !runMeleeAnimation && !runArchery && !runCasting) {
       // updateTextures(spriteSword);
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
        meleeExists= true;
    }}
    public void castAttack()
    {   if((currentState==State.STANDING |currentState==State.WALKING) && getCurrentMana()>=5){
        setCurrentMana(getCurrentMana()-5);
        new Zauber(this);}
    }
    public void bowAttack()
    {   if(currentState==State.STANDING |currentState==State.WALKING)
        {
        runArchery= true;

        Vector2 startVector, flugVector;
        Vector2 koerper=b2body.getPosition();
        switch (getCurrentRichtung()) {
            //Hier sind bei Y immer schon mind. -5 Abzug, weil man es ein bisschen nach unten ziehen muss, um die Mitte der Bodentexture und nicht
            // die der Grafikmitte zu treffen
            case Rechts:
                startVector = new Vector2(koerper.x+20 / AnimaRPG.PPM,koerper.y -8 / AnimaRPG.PPM);
                flugVector = new Vector2(200 / AnimaRPG.PPM, 0);break;
            case Links:
                startVector = new Vector2(koerper.x-20 / AnimaRPG.PPM,koerper.y-8 / AnimaRPG.PPM);
                flugVector = new Vector2(-200 / AnimaRPG.PPM, 0);break;
            case Oben:
                startVector = new Vector2(koerper.x,koerper.y +17 / AnimaRPG.PPM);
                flugVector = new Vector2(0, 200 / AnimaRPG.PPM);break;
            case Unten:
                startVector = new Vector2(koerper.x,koerper.y -33 / AnimaRPG.PPM);
                flugVector = new Vector2(0, -200 / AnimaRPG.PPM);break;
            default:
                startVector = new Vector2(0, 0);
                flugVector = new Vector2(10, 10);break;
        }
        new Arrow(world,screen,currentRichtung,startVector,flugVector);
    }}
    public void setObject(boolean inReichweite,InteraktivesObjekt io){
        objectInReichweite=inReichweite;
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
        getsDamaged(1,enemy);
    }
    public void getsDamaged(int schadensTyp,Enemy enemy){
        int verursachterSchaden;
        SchadenBerechner.berechneSchaden(schadensTyp,this,enemy);
    }
    //die Methode prüft, ob gerade eine Aktion oder Animation ausgeführt wird und gibt Boolean zurück
    public boolean actionInProgress(){
        if(!runDying && !runArchery && !runCasting && !runMeleeAnimation && !destroyed)
            return true;
        return false;
    }

    public InventarList getHeldenInventar() {
        return heldenInventar;
    }


    //Getter und Setter für alle Attribute
    public void setHeldenInventar(InventarList heldenInventar) {
        this.heldenInventar = heldenInventar;
    }
    public TextureRegion getProfilbild(){
        return standingDownSprite;
    }



    public int getCurrentErfahrung() {
        return currentErfahrung;
    }

    public void setCurrentErfahrung(int currentErfahrung) {
        this.currentErfahrung = currentErfahrung;
    }

    public int getNextLevelUp() {
        return nextLevelUp;
    }

    public void setNextLevelUp(int nextLevelUp) {
        this.nextLevelUp = nextLevelUp;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getSpielzeit() {
        int stunde =(int) spielzeit/3600,minute=(int) (spielzeit%3600)/60,sekunden=(int)(spielzeit%3600)%60;

        return ""+String.format("%02d",stunde)+":"+String.format("%02d",minute)+":"+String.format("%02d",sekunden);
    }

    public void setSpielzeit(float spielzeit) {
        this.spielzeit += spielzeit;
    }

    public float getGeschwindigkeitLaufen() {
        return geschwindigkeitLaufen;
    }

    public void setGeschwindigkeitLaufen(float geschwindigkeitLaufen) {
        this.geschwindigkeitLaufen = geschwindigkeitLaufen;
    }

    public int getSchadenNah() {
        return schadenNah;
    }

    public void setSchadenNah() {
        if(getHeldenInventar().getAngelegtWaffeNah()!=null)
        {this.schadenNah=getHeldenInventar().getAngelegtWaffeNah().getSchaden()*(100+staerke)/100;
        }else{
            this.schadenNah = 0;
        }
    }

    public int getSchadenFern() {
        return schadenFern;
    }

    public void setSchadenFern() {
        if(getHeldenInventar().getAngelegtWaffeFern()!=null)
        {this.schadenFern=getHeldenInventar().getAngelegtWaffeFern().getSchaden()*(100+geschick)/100;
        }else{this.schadenFern = 0;}
    }
    public int getSchadenZauber() {
        return schadenZauber;
    }

    public void setSchadenZauber(int schadenZauber) {
        //TODO Zauberübergabe einstellen
        this.schadenZauber = schadenZauber;
    }

    public int getRuestung() {
        return ruestung;
    }

    public void setRuestung() {
        if(getHeldenInventar().getAngelegtRuestung()!=null)
        {this.ruestung=getHeldenInventar().getAngelegtRuestung().getRuestung();
        }else{this.ruestung = 0;}
    }

    public int getStaerke() {
        return staerke;
    }

    public void setStaerke(int staerke) {
        this.staerke = staerke;
    }

    public int getGeschick() {
        return geschick;
    }

    public void setGeschick(int geschick) {
        this.geschick = geschick;
    }

    public int getZauberkraft() {
        return zauberkraft;
    }

    public void setZauberkraft(int zauberkraft) {
        this.zauberkraft = zauberkraft;
    }

    public int getZauberwiderstand() {
        return zauberwiderstand;
    }

    public void setZauberwiderstand(int zauberwiderstand) {
        this.zauberwiderstand = zauberwiderstand;
    }
}

