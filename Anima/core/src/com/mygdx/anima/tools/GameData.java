package com.mygdx.anima.tools;

import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.interaktiveObjekte.SchatztruhenSpeicherObjekt;
import com.mygdx.anima.sprites.character.items.Amulett;
import com.mygdx.anima.sprites.character.items.Brust;
import com.mygdx.anima.sprites.character.items.Handschuhe;
import com.mygdx.anima.sprites.character.items.Helm;
import com.mygdx.anima.sprites.character.items.Schuhe;
import com.mygdx.anima.sprites.character.items.WaffeFern;
import com.mygdx.anima.sprites.character.items.WaffeNah;

import java.io.Serializable;

import static com.mygdx.anima.AnimaRPG.getHeld;
import static com.mygdx.anima.tools.KartenManager.aktuelleKartenId;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

/**
 * Created by Steffen on 25.01.2017.
 */

public class GameData implements Serializable{
    public int hitpoints,maxHitpoints,maxMana,regMana,currentLevel,currentErfahrung,basicAngrGeschw,basicLaufgeschw,staerke,geschick,
            zauberkraft,schadenNah,schadenFern,schadenZauber,ruestung,zauberwiderstand,currentMapId,tempEingang;
    public float spielzeit;
    public int angelegtWaffenNahIndex,angelegtWaffenFernIndex,angelegtBrustIndex,angelegtHelmIndex,
            angelegtAmuleettIndex, angelegtHandschuheIndx, angelegtSchuheIndex;
    public String zauberslot1,zauberslot2,zauberslot3,zauberslot4;
    public String[] waffenNah,waffenFern,brust,helm,handschuhe,schuhe,amulett,zauber;
    public int[] geoeffneteTruhenMaps,geoeffneteTruhenId;

    public GameData()
    {
        hitpoints = getHeld().getMaxHitpoints();
        maxHitpoints = getHeld().getCurrentHitpoints();
        maxMana = getHeld().getMaxMana();
        regMana = getHeld().getRegMana();
        currentLevel = getHeld().getCurrentLevel();
        currentErfahrung = getHeld().getCurrentErfahrung();
        // int =getHeld().getNextLevelUp();
        basicAngrGeschw = getHeld().getBasicAngrGeschw();
        basicLaufgeschw = getHeld().getBasicLaufgeschw();
        staerke = getHeld().getStaerke();
        geschick = getHeld().getGeschick();
        zauberkraft = getHeld().getZauberkraft();
        schadenNah = getHeld().getSchadenNah();
        schadenFern = getHeld().getSchadenFern();
        schadenZauber = getHeld().getSchadenZauber();
        ruestung = getHeld().getRuestung();
        zauberwiderstand = getHeld().getZauberwiderstand();

        currentMapId=aktuelleKartenId;
        tempEingang=Playscreen.getMapEinstieg();
        spielzeit=getHeld().getSpielzeit();
        int size=getHeld().getZauberList().size();
        zauber=new String[size];
        for(int i=0;i<size;i++)
        {
            zauber[i]=getHeld().getZauberList().getZauberList().get(i).getId();
        }
        zauberslot1=getHeld().getZauberList().getZauberslot(1).getId();
        zauberslot2=getHeld().getZauberList().getZauberslot(2).getId();
        zauberslot3=getHeld().getZauberList().getZauberslot(3).getId();
        zauberslot4=getHeld().getZauberList().getZauberslot(4).getId();
        size=getHeld().getGeoeffneteTruhen().size;
        geoeffneteTruhenMaps=new int[size];
        geoeffneteTruhenId=new int[size];
        for(int i=0;i<size;i++)
        {
            SchatztruhenSpeicherObjekt speicher=getHeld().getGeoeffneteTruhen().get(i);
            geoeffneteTruhenMaps[i]=speicher.getMapID();
            geoeffneteTruhenId[i]=speicher.getTruhenID();
        }
       //
        size=getHeld().getHeldenInventar().getWaffenNahList().size();
        waffenNah =new String[size];
        for(int i=0;i<size;i++)
        {
            WaffeNah waffeNah=getHeld().getHeldenInventar().getWaffenNahList().get(i);
            waffenNah[i]=waffeNah.getId();
            if(waffeNah.isAngelegt()){
                angelegtWaffenNahIndex=i;
            }
        }
        size=getHeld().getHeldenInventar().getWaffenFernList().size();
        waffenFern =new String[size];
        for(int i=0;i<size;i++)
        {
            WaffeFern waffeFern=getHeld().getHeldenInventar().getWaffenFernList().get(i);
            waffenFern[i]=waffeFern.getId();
            if(waffeFern.isAngelegt()){
                angelegtWaffenFernIndex=i;
            }
        }
        size=getHeld().getHeldenInventar().getRuestungsList().size();
        brust =new String[size];
        for(int i=0;i<size;i++)
        {
            Brust angbrust=getHeld().getHeldenInventar().getRuestungsList().get(i);
            brust[i]=angbrust.getId();
            if(angbrust.isAngelegt()){
                angelegtBrustIndex=i;
            }
        }
        size=getHeld().getHeldenInventar().getHelmList().size();
        helm =new String[size];
        for(int i=0;i<size;i++)
        {
            Helm angHelm=getHeld().getHeldenInventar().getHelmList().get(i);
            helm[i]=angHelm.getId();
            if(angHelm.isAngelegt()){
                angelegtHelmIndex=i;
            }
        }
        size=getHeld().getHeldenInventar().getHandschuheList().size();
        handschuhe =new String[size];
        for(int i=0;i<size;i++)
        {
            Handschuhe angHandschuhe=getHeld().getHeldenInventar().getHandschuheList().get(i);
            handschuhe[i]=angHandschuhe.getId();
            if(angHandschuhe.isAngelegt()){
                angelegtHandschuheIndx=i;
            }
        }
        size=getHeld().getHeldenInventar().getSchuheList().size();
        schuhe =new String[size];
        for(int i=0;i<size;i++)
        {
            Schuhe angSchuhe=getHeld().getHeldenInventar().getSchuheList().get(i);
            schuhe[i]=angSchuhe.getId();
            if(angSchuhe.isAngelegt()){
                angelegtSchuheIndex=i;
            }
        }
        size=getHeld().getHeldenInventar().getAmulettList().size();
        amulett =new String[size];
        for(int i=0;i<size;i++)
        {
            Amulett angAmulett=getHeld().getHeldenInventar().getAmulettList().get(i);
            amulett[i]=angAmulett.getId();
            if(angAmulett.isAngelegt()){
                angelegtAmuleettIndex=i;
            }
        }
    }
   // regenerationTimer=0;
}
