package com.mygdx.anima.sprites.character.zauber;

import com.badlogic.gdx.utils.Array;

import java.io.Serializable;
/**
 * Created by Steffen on 01.12.2016.
 */

public class ZauberList implements Serializable {
    private static Array<ZauberEntity> zauberListe;

    private ZauberEntity zauberslot1,zauberslot2,zauberslot3,zauberslot4, zauberAuswahl;
    public ZauberList(){
        zauberListe=new Array<ZauberEntity>();
    }
    public int size() {
        return zauberListe.size;
    }

    public void addZauber(ZauberEntity zauber) {
        zauberListe.add(zauber);
    }

    public boolean remove(ZauberEntity zauber) {
        return zauberListe.removeValue(zauber,true);
    }
    public synchronized void resetAuswahl(){
        int temp=zauberListe.size;
        for(int i=0;i<temp;i++){
            zauberListe.get(i).setAusgewaehlt(false);
        }
    }
    public Array<ZauberEntity> getZauberList() {
        return zauberListe;
    }

    // Getter und Setter für angelegte Variablen
    /*public synchronized void setAngelegtWaffeNah(WaffeNah angelegtWaffeNah) {
        if(this.angelegtWaffeNah!=null)
            this.angelegtWaffeNah.setAngelegt(false);
        if(angelegtWaffeNah!=null){
        this.angelegtWaffeNah = angelegtWaffeNah;angelegtWaffeNah.setAngelegt(true);}
    else{this.angelegtWaffeNah=null;}
        getHeld().setSchadenNah();}
    public synchronized WaffeFern getAngelegtWaffeFern() {return angelegtWaffeFern;}
    public synchronized void setAngelegtWaffeFern(WaffeFern angelegtWaffeFern) {
        if(this.angelegtWaffeFern!=null)
            this.angelegtWaffeFern.setAngelegt(false);
        if(angelegtWaffeFern!=null){
        this.angelegtWaffeFern = angelegtWaffeFern;angelegtWaffeFern.setAngelegt(true);}
        else{this.angelegtWaffeFern=null;}
        getHeld().setSchadenFern();}
*/
    public void setZauberslot(int slotNummer,ZauberEntity zauber){
        switch(slotNummer){
            case 1: zauberslot1=zauber;break;
            case 2: zauberslot2=zauber;break;
            case 3: zauberslot3=zauber;break;
            case 4: zauberslot4=zauber;break;
        }
    }
    public ZauberEntity getZauberslot(int slotNummer){
        switch(slotNummer){
            case 1: return zauberslot1;
            case 2: return zauberslot2;
            case 3: return zauberslot3;
            case 4: return zauberslot4;
            default: return zauberslot1;
        }
    }
}
