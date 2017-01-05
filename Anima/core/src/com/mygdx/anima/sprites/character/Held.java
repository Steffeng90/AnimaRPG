package com.mygdx.anima.sprites.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.scenes.LevelUpInfo;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.enemies.Enemy;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Arrow;
import com.mygdx.anima.sprites.character.interaktiveObjekte.InteraktivesObjekt;
import com.mygdx.anima.sprites.character.interaktiveObjekte.Nova;
import com.mygdx.anima.sprites.character.items.InventarList;
import com.mygdx.anima.tools.SchadenBerechner;

import static com.mygdx.anima.AnimaRPG.setHeld;

/**
 * Created by Steffen on 09.11.2016.
 */

public class Held extends HumanoideSprites{
    public boolean objectInReichweite, genugEP;
    private int[] erfahrungsstufen;
    public InteraktivesObjekt object;
    public Playscreen screen;
    public boolean waffe=false;
    public boolean heldErstellt=false;
    public static String spriteNude="character/female_stock.png",
    spriteStock="character/female_stock.png",
    spriteSword="character/female_sword.png",
    spriteBogen="character/female_bogen.png";
    public boolean isHitbyMelee,isHitbyBow,isHitbyCast,isHitbyThrust;
    public Enemy treffenderEnemy;
    private InventarList heldenInventar;
    //Dieser Wert wird über Methoden übergeben, um den Held neu zu positionieren, bei Kartenwechsel
    private Vector2 heldPosition;
    //Statistik-Werte

    private float spielzeit,geschwindigkeitLaufen;

    private int schadenNah,schadenFern,schadenZauber,ruestung,zauberwiderstand,staerke,
    geschick,zauberkraft,currentErfahrung,currentLevel,nextLevelUp;



    public Held(Playscreen screen,Vector2 spielerPosition)
    {
        super(screen,spriteBogen,true);
        this.screen=screen;
        heldPosition=spielerPosition;

        if(!heldErstellt){
        createHeroBody(new Vector2(150f/AnimaRPG.PPM,50f/AnimaRPG.PPM));
        heldErstellt=true;}
        objectInReichweite=false;

        heldenInventar=new InventarList();

        // Gameplay-Variablen
        int Startwert=100;
        int lastWert=Startwert;
        erfahrungsstufen=new int[50];
        for(int i=0;i<50;i++){
            lastWert=(int) (lastWert*1.2);
            lastWert-=(lastWert%10);
            erfahrungsstufen[i]=lastWert;
            // Gdx.app.log("Stufe "+i+" EPWert:",""+erfahrungsstufen[i]);
        }

        setMaxHitpoints(1000);
        setCurrentHitpoints(getMaxHitpoints());
        setMaxMana(15);
        setCurrentMana(getMaxMana());
        setRegMana(1);
        setCurrentLevel(1);
        setCurrentErfahrung(0);
        setNextLevelUp(1);
        setGeschwindigkeitLaufen(20); // 10 ist guter Startwert
        setStaerke(10);
        setGeschick(12);
        setZauberkraft(8);
        setSchadenNah(10);
        setSchadenFern(10);
        setSchadenZauber(12);
        setRuestung(3);
        setZauberwiderstand(11);
        regenerationTimer=0;
        setHeld(this);
}
   /* public TextureRegion getFrame(float dt) {
        return super.getFrame(dt);
    }*/
    public void createHeroBody(Vector2 heldPosition){
        BodyDef bdef=new BodyDef();
        bdef.position.set(heldPosition);
        bdef.type=BodyDef.BodyType.DynamicBody;
        b2body=world.createBody(bdef);
        createSensor();
        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(7/AnimaRPG.PPM);
        shape.setPosition(new Vector2(0,-12/AnimaRPG.PPM));
        fdef.filter.categoryBits=AnimaRPG.HERO_BIT;
        fdef.filter.maskBits=AnimaRPG.GEBIETSWECHSEL_BIT | AnimaRPG.BARRIERE_BIT | AnimaRPG.ENEMY_BIT | AnimaRPG.OBJECT_BIT | AnimaRPG.ENEMY_SENSOR | AnimaRPG.ENEMY_ATTACK
                | AnimaRPG.ARROW_BIT | AnimaRPG.ENEMY_HEAL_SENSOR;
        fdef.shape=shape;
        b2body.createFixture(fdef).setUserData(this);
    }
    public void update(float dt)
    {
        setSpielzeit(dt);
        super.update(dt);
        setRegion(getFrame(dt));
        if(genugEP){
            genugEP=false;
            stufenAufstieg();
        }
        if(isHitbyMelee){getsHitbyMelee(treffenderEnemy);isHitbyMelee=false;}
        if(isHitbyBow){getsHitbyBow(treffenderEnemy);isHitbyBow=false;}
        if(isHitbyCast){getsHitbyCast(treffenderEnemy);isHitbyCast=false;}
        if(isHitbyThrust){getsHitbyThrust(treffenderEnemy);isHitbyThrust=false;}
        if(!runCasting && castExists){
            castExists=false;}
        else if(!destroyed)
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
       // CircleShape circleShape = new CircleShape();
        //circleShape.setRadius(15 / AnimaRPG.PPM);
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
        meleeFixtureDefinieren(richtungsVector);
        // TODO ALle FixturesErzeugungenn (Melee,Bow, Cast und Thrust, in HumanoideSprites als Methode auslagern.
        /*circleShape.setPosition(richtungsVector);
        FixtureDef fdefAttack = new FixtureDef();
        fdefAttack.filter.categoryBits = AnimaRPG.HERO_WEAPON_BIT;
        fdefAttack.filter.maskBits = AnimaRPG.ENEMY_BIT;
        fdefAttack.shape = circleShape;
        fdefAttack.isSensor = true;
        meleeFixture = b2body.createFixture(fdefAttack);
        meleeFixture.setUserData(this);
        runMeleeAnimation = true;
        meleeExists= true;*/
    }}
    public void castAttack()
    {   if((currentState==State.STANDING |currentState==State.WALKING) && getCurrentMana()>=5){
        Gdx.app.log("MinusMAna","");
        setCurrentMana(getCurrentMana()-5);
        new Nova(this);}
    }
    public void bowAttack()
    {   if(currentState==State.STANDING |currentState==State.WALKING)
        {
        runArchery= true;

        // Vector2 startVector, flugVector;
        Vector2 koerper=b2body.getPosition();
        switch (getCurrentRichtung()) {
            //Hier sind bei Y immer schon mind. -5 Abzug, weil man es ein bisschen nach unten ziehen muss, um die Mitte der Bodentexture und nicht
            // die der Grafikmitte zu treffen
            case Rechts:
                arrowStartVector = new Vector2(koerper.x+20 / AnimaRPG.PPM,koerper.y -8 / AnimaRPG.PPM);
                arrowFlugVector = new Vector2(200 / AnimaRPG.PPM, 0);break;
            case Links:
                arrowStartVector = new Vector2(koerper.x-20 / AnimaRPG.PPM,koerper.y-8 / AnimaRPG.PPM);
                arrowFlugVector = new Vector2(-200 / AnimaRPG.PPM, 0);break;
            case Oben:
                arrowStartVector = new Vector2(koerper.x,koerper.y +17 / AnimaRPG.PPM);
                arrowFlugVector = new Vector2(0, 200 / AnimaRPG.PPM);break;
            case Unten:
                arrowStartVector = new Vector2(koerper.x,koerper.y -33 / AnimaRPG.PPM);
                arrowFlugVector = new Vector2(0, -200 / AnimaRPG.PPM);break;
            default:
                arrowStartVector = new Vector2(0, 0);
                arrowFlugVector = new Vector2(10, 10);break;
        }
        // new Arrow(world,screen,currentRichtung,startVector,flugVector,this);
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
    public void getsHitbyMelee(Enemy enemy){
        if(enemy!=null){getsDamaged(1,enemy);}
    }
    public void getsHitbyBow(Enemy enemy){getsDamaged(2,enemy);}
    public void getsHitbyCast(Enemy enemy){
        getsDamaged(3,enemy);
    }
    public void getsHitbyThrust(Enemy enemy){
        getsDamaged(4,enemy);
    }
    public void getsDamaged(int schadensTyp,Enemy enemy){
        if(enemy==null){Gdx.app.log("Gegner ist null","");}
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

    public synchronized int getCurrentErfahrung() {
        return currentErfahrung;
    }

    public synchronized void setCurrentErfahrung(int currentErfahrung) {
        this.currentErfahrung = currentErfahrung;
        if(currentErfahrung>nextLevelUp){
            this.currentErfahrung=currentErfahrung-nextLevelUp;
            genugEP=true;}
    }
    public int getNextLevelUp() {
        return nextLevelUp;
    }

    public void setNextLevelUp(int level) {
        this.nextLevelUp = erfahrungsstufen[level-1];
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
        setNextLevelUp(currentLevel);
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

    public Vector2 getHeldPosition() {
        return heldPosition;
    }

    public void setHeldPosition(Vector2 heldPosition) {
        //this.heldPosition = heldPosition;
    }
    public synchronized void stufenAufstieg(){
        //TODO Idee: Werte 2,2,1 aufteilen und LP + 5-12 und MP + 2-3
        Gdx.app.log("Stufenaufstieg","");
        setCurrentLevel(getCurrentLevel()+1);

        // Zufällige verteilung der Werte auf Stärke, Geschick, und Zauberkraft
        int i=(int)(3*Math.random()),stark,gesch,zaub,hp=0,mana=0;
        switch(i){
            case 0: stark=1 ;gesch=2 ;zaub=2 ;break;
            case 1: stark=2 ;gesch=1 ;zaub=2 ;break;
            case 2: stark=2 ;gesch=2 ;zaub=1 ;break;
            case 3: stark=3 ;gesch=2 ;zaub=2 ;break;
            case 4: stark=2 ;gesch=3 ;zaub=2 ;break;
            case 5: stark=2 ;gesch=2 ;zaub=3 ;break;
            case 6: stark=1 ;gesch=2 ;zaub=1 ;break;
            case 7: stark=2 ;gesch=1 ;zaub=1 ;break;
            case 8: stark=1 ;gesch=1 ;zaub=2 ;break;
                default:stark=2 ;gesch=2 ;zaub=2 ;
        }
        setStaerke(getStaerke()+stark);setGeschick(getGeschick()+gesch);setZauberkraft(getZauberkraft()+zaub);
        //Lebenspunkte 5-8
        i=(int)(4*Math.random());
        switch(i){
            case 0:hp=5;break;
            case 1:hp=6;break;
            case 2:hp=7;break;
            case 3:hp=8;break;
        }
        setMaxHitpoints(getMaxHitpoints()+hp);
        i=(int)(2*Math.random());
        switch(i) {
            case 0: mana=2;break;
            case 1: mana=3;break;
        }
        setMaxMana(getMaxMana() + 2);
        screen.setLevelUpWindow(new LevelUpInfo(screen,AnimaRPG.batch,currentLevel,stark,gesch,zaub,hp,mana));
    }
}