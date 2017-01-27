package com.mygdx.anima.sprites.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.scenes.LevelUpInfo;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.enemies.Enemy;
import com.mygdx.anima.sprites.character.interaktiveObjekte.InteraktivesObjekt;
import com.mygdx.anima.sprites.character.interaktiveObjekte.SchatztruhenSpeicherObjekt;
import com.mygdx.anima.sprites.character.items.InventarList;
import com.mygdx.anima.sprites.character.items.ItemGenerator;
import com.mygdx.anima.sprites.character.zauber.ZauberGenerator;
import com.mygdx.anima.sprites.character.zauber.ZauberList;
import com.mygdx.anima.tools.Controller;
import com.mygdx.anima.tools.GameData;
import com.mygdx.anima.tools.SchadenBerechner;

import java.io.Serializable;

import static com.badlogic.gdx.Gdx.app;
import static com.mygdx.anima.AnimaRPG.ARROW_BIT;
import static com.mygdx.anima.AnimaRPG.setHeld;

/**
 * Created by Steffen on 09.11.2016.
 */

public class Held extends HumanoideSprites implements Serializable{
    public boolean objectInReichweite, genugEP;

    private Array<SchatztruhenSpeicherObjekt> geoeffneteTruhen;
    private int[] erfahrungsstufen;
    public InteraktivesObjekt object;
    public Playscreen screen;
    public boolean heldErstellt=false;
    public static String einseins="character/43";
    public boolean isHitbyMelee,isHitbyBow,isHitbyCast,isHitbyThrust;
    public Enemy treffenderEnemy;
    private InventarList heldenInventar;
    private ZauberList zauberList;
    private int aktuellerZauberInt;
    private TextureRegion dialogBild;
    private boolean[] eventList;
    //Dieser Wert wird über Methoden übergeben, um den Held neu zu positionieren, bei Kartenwechsel
    private Vector2 heldPosition;
    //Statistik-Werte

    private float spielzeit;

    private int schadenNah,schadenFern,schadenZauber,ruestung,zauberwiderstand,staerke,gesamtZauberwiderstand,gesamtZauberkraft,gesamtManaReg,gesamtLPReg,
    gesamtLaufgeschwindigkeit,geschick,zauberkraft,currentErfahrung,currentLevel,nextLevelUp,basicLaufgeschw,basicAngrGeschw;
    Sound walkingSound;
    Boolean soundLoopAktiv;

    // Erstmaliges Erstellen mit Konstruktor
    public Held(Playscreen screen,Vector2 spielerPosition)
    {
        super(screen,einseins,true);
        this.screen=screen;
        //heldPosition=spielerPosition;
        if(!heldErstellt){
        createHeroBody(new Vector2(150f/AnimaRPG.PPM,100f/AnimaRPG.PPM));
        heldErstellt=true;}
        objectInReichweite=false;
        geoeffneteTruhen= new Array<SchatztruhenSpeicherObjekt>();
        heldenInventar=new InventarList();
        zauberList=new ZauberList();
        setEventList();
        // Gameplay-Variablen
        setErfahrungsstufen();
        soundLoopAktiv=false;
        //walkingSound=AnimaRPG.assetManager.get("audio/sounds/walk.ogg", Sound.class);
        walkingSound=AnimaRPG.assetManager.get("audio/sounds/laufen.mp3", Sound.class);

        setMaxHitpoints(100);
        setCurrentHitpoints(getMaxHitpoints());
        setMaxMana(15);
        setCurrentMana(getMaxMana());
        setRegMana(15);
        setCurrentLevel(1);
        setCurrentErfahrung(0);
        setNextLevelUp(1);
        basicAngrGeschw=40;
        setBasicLaufgeschw(20); // 10 ist guter Startwert
        setStaerke(10);
        setGeschick(12);
        setZauberkraft(8);
        setSchadenNah(10);
        setSchadenFern(10);
        setSchadenZauber(12);
        setRuestung(3);
        setZauberwiderstand(11);
        updateAlleWerte();
        regenerationTimer=0;
        setHeld(this);
}
   /* public TextureRegion getFrame(float dt) {
        return super.getFrame(dt);
    }*/
    public Held(Playscreen screen,GameData gameData){
        super(screen,einseins,true);

        soundLoopAktiv=false;
        //walkingSound=AnimaRPG.assetManager.get("audio/sounds/walk.ogg", Sound.class);
        walkingSound=AnimaRPG.assetManager.get("audio/sounds/laufen.mp3", Sound.class);

        setErfahrungsstufen();
        setZauberwiderstand(gameData.zauberwiderstand);
        if(!heldErstellt){
            createHeroBody(new Vector2(150f/AnimaRPG.PPM,100f/AnimaRPG.PPM));
            Gdx.app.log("Spieler wurde neu navigiert","");

            heldErstellt=true;}
        objectInReichweite=false;
        geoeffneteTruhen= new Array<SchatztruhenSpeicherObjekt>();
        heldenInventar=new InventarList();
        zauberList=new ZauberList();

        // Nicht löschen: setSchadenZauber();
        // TODO RUESTUNG durch ITEMS definieren


        setCurrentHitpoints(gameData.hitpoints);
        setMaxHitpoints(gameData.maxHitpoints);
        setMaxMana(gameData.maxMana);
        setRegMana(gameData.regMana);
        setCurrentLevel(gameData.currentLevel);
        setCurrentErfahrung(gameData.currentErfahrung);
        setBasicAngrGeschw(gameData.basicAngrGeschw);
        setBasicLaufgeschw(gameData.basicLaufgeschw);

        setStaerke(gameData.staerke);
        setGeschick(gameData.geschick);
        setZauberkraft(gameData.zauberkraft);
        setSchadenNah();
        setSchadenFern();
        setSpielzeit(gameData.spielzeit);

        setNextLevelUp(getCurrentLevel());

        setHeld(this);
        // Zauber auslesen
        for(int i=0; i<gameData.zauber.length;i++){
            ZauberGenerator.generateZauber(gameData.zauber[i]);
            if(gameData.zauberslot1==zauberList.getZauberList().get(i).getId()){
                getZauberList().setZauberslot(1,getZauberList().getZauberList().get(i));
            }
            if(gameData.zauberslot2==zauberList.getZauberList().get(i).getId()){
                getZauberList().setZauberslot(2,getZauberList().getZauberList().get(i));
            }
            if(gameData.zauberslot3==zauberList.getZauberList().get(i).getId()){
                getZauberList().setZauberslot(3,getZauberList().getZauberList().get(i));
            }
            if(gameData.zauberslot4==zauberList.getZauberList().get(i).getId()){
                getZauberList().setZauberslot(4,getZauberList().getZauberList().get(i));
            }
        }
        // Geöffnete truhen auslesen
        for(int i = 0; i<gameData.geoeffneteTruhenMaps.length; i++)
        {   geoeffneteTruhen.add(new SchatztruhenSpeicherObjekt(gameData.geoeffneteTruhenMaps[i],gameData.geoeffneteTruhenId[i]));
            }
        // Gespeicherte Items auslesen und wieder anziehen
        for(int i = 0; i<gameData.waffenNah.length; i++)
        {   ItemGenerator.generateItem(screen,0f,0f,gameData.waffenNah[i]);
            if(gameData.angelegtWaffenNahIndex==i){
                heldenInventar.setAngelegtWaffeNah(heldenInventar.getWaffenNahList().get(i));
            }}
        for(int i = 0; i<gameData.waffenFern.length; i++)
        {   ItemGenerator.generateItem(screen,0f,0f,gameData.waffenFern[i]);
            if(gameData.angelegtWaffenFernIndex==i){
                heldenInventar.setAngelegtWaffeFern(heldenInventar.getWaffenFernList().get(i));
            }}
        for(int i = 0; i<gameData.brust.length; i++)
        {   ItemGenerator.generateItem(screen,0f,0f,gameData.brust[i]);
            if(gameData.angelegtBrustIndex==i){
                heldenInventar.setAngelegtRuestung(heldenInventar.getRuestungsList().get(i));
            }}
        for(int i = 0; i<gameData.helm.length; i++)
        {   ItemGenerator.generateItem(screen,0f,0f,gameData.helm[i]);
            if(gameData.angelegtHelmIndex==i){
                heldenInventar.setAngelegtHelm(heldenInventar.getHelmList().get(i));
            }}
        for(int i = 0; i<gameData.schuhe.length; i++)
        {   ItemGenerator.generateItem(screen,0f,0f,gameData.schuhe[i]);
            if(gameData.angelegtSchuheIndex==i){
                heldenInventar.setAngelegtSchuhe(heldenInventar.getSchuheList().get(i));
            }}
        for(int i = 0; i<gameData.handschuhe.length; i++)
        {   ItemGenerator.generateItem(screen,0f,0f,gameData.handschuhe[i]);
            if(gameData.angelegtHandschuheIndx==i){
                heldenInventar.setAngelegtHandschuhe(heldenInventar.getHandschuheList().get(i));
            }}
        for(int i = 0; i<gameData.amulett.length; i++)
        {   ItemGenerator.generateItem(screen,0f,0f,gameData.amulett[i]);
            if(gameData.angelegtAmuleettIndex==i){
                heldenInventar.setAngelegtAmulett(heldenInventar.getAmulettList().get(i));
            }}

        updateAlleWerte();
    }
    public void createHeroBody(Vector2 heldPosition){
        BodyDef bdef=new BodyDef();
        bdef.position.set(heldPosition);
        bdef.type=BodyDef.BodyType.DynamicBody;
        b2body=world.createBody(bdef);
        createSensor();
        FixtureDef fdef=new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(7f/AnimaRPG.PPM);
        shape.setPosition(new Vector2(0,-12/AnimaRPG.PPM));
        fdef.filter.categoryBits=AnimaRPG.HERO_BIT;
        fdef.filter.maskBits=AnimaRPG.GEBIETSWECHSEL_BIT | AnimaRPG.BARRIERE_BIT | AnimaRPG.ENEMY_BIT | AnimaRPG.OBJECT_BIT | AnimaRPG.ENEMY_SENSOR | AnimaRPG.ENEMY_ATTACK
                | ARROW_BIT | AnimaRPG.ENEMY_HEAL_SENSOR | AnimaRPG.EVENT_AREA_BIT;
        fdef.shape=shape;
        b2body.createFixture(fdef).setUserData(this);
        // Oberkörpershape
        shape.setPosition(new Vector2(0,4.5f/AnimaRPG.PPM));
        fdef.filter.categoryBits=AnimaRPG.HERO_OBERKOERPER;
        fdef.filter.maskBits=AnimaRPG.ENEMY_ATTACK | ARROW_BIT;
        fdef.isSensor=true;
        fdef.shape=shape;
        b2body.createFixture(fdef).setUserData(this);

    }
    public void update(float dt)
    {
        setSpielzeit(dt);
        super.update(dt);
        setRegion(getFrame(dt));

        if(currentState==State.WALKING && soundLoopAktiv==false){
            soundLoopAktiv=true;
         walkingSound.loop(0.2f);
        }
        else if(currentState!=State.WALKING && soundLoopAktiv==true){
            soundLoopAktiv=false;
            walkingSound.stop();
        }
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
        else if(!destroyed){
            //setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
        }
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
        AnimaRPG.assetManager.get("audio/sounds/sword_swing.mp3", Sound.class).play(0.5f);


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
    public void castAttack(int slotNr)
    {   if((currentState==State.STANDING |currentState==State.WALKING) && getCurrentMana()>=getZauberList().getZauberslot(slotNr).getManakosten()){
        setCurrentMana(getCurrentMana()-getZauberList().getZauberslot(slotNr).getManakosten());
        setAktuellerZauberInt(slotNr);
        getZauberList().getZauberslot(slotNr).fixtureErzeugen(currentRichtung);}
    }
   /*public void castBlitz()
    {   if((currentState==State.STANDING |currentState==State.WALKING) && getCurrentMana()>=5){
        Gdx.app.log("MinusMAna","");
        setCurrentMana(getCurrentMana()-5);
        new Nova();}
    }*/
    public void bowAttack()
    {   if(currentState==State.STANDING |currentState==State.WALKING)
        {
        runArchery= true;
        //    AnimaRPG.assetManager.get("audio/sounds/bow_attack.mp3", Sound.class).play(0.5f);

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
               // arrowStartVector = new Vector2(koerper.x,koerper.y +17 / AnimaRPG.PPM);
                arrowStartVector = new Vector2(koerper.x,koerper.y +23 / AnimaRPG.PPM);
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
        Controller.useUpdate=true;
    }
    public void useObject(){
        if(object!=null)
            object.use(this);
    }
  /*  public void spriteWechsel(){
        updateTextures(spriteSword);
            }
    public void spriteBogen(){
        updateTextures(spriteBogen);
    }*/
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
        if(enemy==null){
            app.log("Gegner ist null","");}
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
    public ZauberList getZauberList() { return zauberList;}

    //Getter und Setter für alle Attribute
    public void setHeldenInventar(InventarList heldenInventar) {
        this.heldenInventar = heldenInventar;
    }
    public TextureRegion getProfilbild(){
        return standingDownSprite;
    }
    public TextureRegion getDialogbild(){
        return dialogBild;
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

    public String getSpielzeitString() {
        int stunde =(int) spielzeit/3600,minute=(int) (spielzeit%3600)/60,sekunden=(int)(spielzeit%3600)%60;

        return ""+String.format("%02d",stunde)+":"+String.format("%02d",minute)+":"+String.format("%02d",sekunden);
    }
    public float getSpielzeit(){
        return spielzeit;
    }
    public void setSpielzeit(float spielzeit) {
        this.spielzeit += spielzeit;
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
        //TODO Zauberübergabe einstellen Hier sollte man untescheiden zwischen allen Zauberslot
        this.schadenZauber = schadenZauber;
    }

    public int getRuestung() {

        return ruestung;
    }

    public void updateRuestung() {
        int w1=0,w2=0,w3=0;
        if(getHeldenInventar().getAngelegtRuestung()!=null){ w1=getHeldenInventar().getAngelegtRuestung().getRuestung();}
        if(getHeldenInventar().getAngelegtSchuhe()!=null) {w2=getHeldenInventar().getAngelegtSchuhe().getRuestung();}
        if(getHeldenInventar().getAngelegtHandschuhe()!=null){w3=getHeldenInventar().getAngelegtHandschuhe().getRuestung();}

        ruestung=w1+w2+w3;
    }

    public int getGesamtManaReg() {
        return gesamtManaReg;
    }
    public void updateGesamtManaReg(){
        if(getHeldenInventar().getAngelegtAmulett()!=null){
            gesamtManaReg=getRegMana()+getHeldenInventar().getAngelegtAmulett().getManareg();
        }
        else {gesamtManaReg=getRegMana();}
    }
    public int getGesamtLPReg() {
        return gesamtLPReg;
    }
    public void updateGesamtLPReg(){
        if(getHeldenInventar().getAngelegtRuestung()!=null){
            gesamtLPReg=getRegHitpoints()+getHeldenInventar().getAngelegtRuestung().getLpReg();
        }
        else {gesamtLPReg=getRegHitpoints();}
    }
    public void updateAlleWerte(){
        updateGesamtLPReg();
        updateLaufgeschwindigkeit();
        updateGesamtManaReg();
        updateGesamtZauberkraft();
        updateGesamtZauberwiderstand();
        updateRuestung();
        updateAngriffsgeschw();
        int w1=1,w2=1;
        float zeit1=0,zeit2=0,zeit3=0,zeit4=0;
        if(getHeldenInventar().getAngelegtRuestung()!=null){
            w1=getHeldenInventar().getAngelegtRuestung().getOptikStufe();
        }
        if(getHeldenInventar().getAngelegtSchuhe()!=null){
            w2=getHeldenInventar().getAngelegtSchuhe().getOptikStufe();
        }
        if( getZauberList().getZauberslot(1)!=null){
            zeit1=getZauberList().getZauberslot(1).getZauberZeit();
        }
        if( getZauberList().getZauberslot(2)!=null){
            zeit2=getZauberList().getZauberslot(2).getZauberZeit();
        }
        if( getZauberList().getZauberslot(3)!=null){
            zeit3=getZauberList().getZauberslot(3).getZauberZeit();
        }
        if( getZauberList().getZauberslot(3)!=null){
            zeit4=getZauberList().getZauberslot(3).getZauberZeit();
        }
        updateTextures("character/"+w1+""+w2,getAngriffgeschwindigkeit(),getGeschwindigkeitLaufen(),zeit1,zeit2,zeit3,zeit4);
        dialogBild=new TextureRegion(standingDownSprite,20,8,24,32);

    };
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
    public int getGesamtZauberwiderstand() {
        return gesamtZauberwiderstand;
    }
    public void updateGesamtZauberwiderstand(){
        if(getHeldenInventar().getAngelegtAmulett()!=null){
            gesamtZauberwiderstand=zauberwiderstand+getHeldenInventar().getAngelegtAmulett().getZauberwiderstand();}
        else{
            gesamtZauberwiderstand=zauberwiderstand;
        }
    }
    public int getGesamtZauberkraft() {
        return gesamtZauberkraft;
    }
    public void updateGesamtZauberkraft(){
        if(getHeldenInventar().getAngelegtHelm()!=null){
            gesamtZauberkraft=zauberkraft+getHeldenInventar().getAngelegtHelm().getZauberkraft();}
        else { gesamtZauberkraft=zauberkraft;}
    }

    public void updateLaufgeschwindigkeit() {
        if(getHeldenInventar().getAngelegtSchuhe()!=null){
        this.setGeschwindigkeitLaufen(getBasicLaufgeschw()+getHeldenInventar().getAngelegtSchuhe().getLaufgeschwindigkeit());}
        else{
            this.setGeschwindigkeitLaufen(getBasicLaufgeschw());
        }
    }

    public Vector2 getHeldPosition() {
        return heldPosition;
    }

    public void setHeldPosition(Vector2 heldPosition) {
        //this.heldPosition = heldPosition;
    }
    public synchronized void stufenAufstieg(){
        //TODO Idee: Werte 2,2,1 aufteilen und LP + 5-12 und MP + 2-3
        app.log("Stufenaufstieg","");
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

    public void updateAngriffsgeschw() {
        if(getHeldenInventar().getAngelegtHandschuhe()!=null){
            setAngriffgeschwindigkeit(basicAngrGeschw+getHeldenInventar().getAngelegtHandschuhe().getAngriffgeschwindigkeit());
        }else {
            setAngriffgeschwindigkeit(basicAngrGeschw);
        }
    }

    public int getBasicLaufgeschw() {
        return basicLaufgeschw;
    }
    public int getBasicAngrGeschw() {
        return basicAngrGeschw;
    }
    public void setBasicAngrGeschw(int basicAngrGeschw) {
        this.basicAngrGeschw = basicAngrGeschw;
    }

    public void setBasicLaufgeschw(int basicLaufgeschw) {
        this.basicLaufgeschw = basicLaufgeschw;
    }

    public int getAktuellerZauberInt() {
        return aktuellerZauberInt;
    }

    public void setAktuellerZauberInt(int aktuellerZauber) {
        this.aktuellerZauberInt = aktuellerZauber;
    }

    public Array<SchatztruhenSpeicherObjekt> getGeoeffneteTruhen() {
        return geoeffneteTruhen;
    }

    public void setGeoeffneteTruhen(Array<SchatztruhenSpeicherObjekt> geoeffneteTruhen) {
        this.geoeffneteTruhen = geoeffneteTruhen;
    }
    public void setErfahrungsstufen(){
        int Startwert=100;
        int lastWert=Startwert;
        erfahrungsstufen=new int[50];
        for(int i=0;i<50;i++){
            lastWert=(int) (lastWert*1.2);
            lastWert-=(lastWert%10);
            erfahrungsstufen[i]=lastWert;
        }
    }
    public boolean[] getEventList() {
        return eventList;
    }
    public void setEventList(boolean[] eventList) {
        this.eventList = eventList;
    }
    public void setEventList() {
        this.eventList=new boolean[50];
       /* for(boolean event:eventList){
            event=true;
            Gdx.app.log("werte gesetzt","");
        }*/
        for(int i=0;i<50;i++){
            eventList[i]=false;
        }
    }
}