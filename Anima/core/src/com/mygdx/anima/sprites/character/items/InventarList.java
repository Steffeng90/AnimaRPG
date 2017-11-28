package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.utils.Array;

import java.io.Serializable;
import java.util.Iterator;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 01.12.2016.
 */

public class InventarList implements Serializable {
    private static Array<Brust> ruestungsList;
    private static Array<Handschuhe> handschuheList;
    private static Array<Schuhe> schuheList;
    private static Array<Helm> helmList;
    private static Array<Amulett> amulettList;

    private static Array<WaffeNah> waffenNahList;
    private static Array<WaffeFern> waffenFernList;
    private static Array<Benutzbar> benutzbarList;

    private WaffeNah angelegtWaffeNah;
    private WaffeFern angelegtWaffeFern;
    private Brust angelegtRuestung;
    private Handschuhe angelegtHandschuhe;
    private Schuhe angelegtSchuhe;
    private Helm angelegtHelm;
    private Amulett angelegtAmulett;

    public InventarList(){
        ruestungsList=new Array<Brust>();
        handschuheList=new Array<Handschuhe>();
        schuheList=new Array<Schuhe>();
        helmList=new Array<Helm>();
        amulettList=new Array<Amulett>();

        waffenNahList =new Array<WaffeNah>();
        waffenFernList =new Array<WaffeFern>();
        benutzbarList=new Array<Benutzbar>();

    }
    public Array<Benutzbar> getBenutzbarList() {
        return benutzbarList;
    }

    public Array<WaffeNah> getWaffenNahList() {
        return waffenNahList;
    }
    public Array<WaffeFern> getWaffenFernList() {
        return waffenFernList;
    }
    public Array<Brust> getRuestungsList() {
        return ruestungsList;
    }
    public Array<Handschuhe> getHandschuheList() {return handschuheList;}
    public Array<Schuhe> getSchuheList() {return schuheList;}
    public Array<Helm> getHelmList() {return helmList;}
    public Array<Amulett> getAmulettList() {return amulettList;}

    public int size() {
        return ruestungsList.size;
    }

    public void add(int i, Brust brust) {
        ruestungsList.add(brust);
    }

    public void add(Brust brust) {ruestungsList.add(brust);}
    public void add(Schuhe schuhe) { schuheList.add(schuhe);}
    public void add(Handschuhe handschuhe) { handschuheList.add(handschuhe);}
    public void add(Helm helm) { helmList.add(helm);}
    public void add(Amulett amulett) { amulettList.add(amulett);}


    public void add(Benutzbar benutzbar) { benutzbarList.add(benutzbar);}
    public void add(WaffeNah waffeNah) {
         waffenNahList.add(waffeNah);
    }
    public void add(WaffeFern waffeFern) {
         waffenFernList.add(waffeFern);
    }

    public void remove(Benutzbar benutzbar) {
         benutzbarList.removeValue(benutzbar,true);
    }

    public Iterator<Brust> iterator() {
        return ruestungsList.iterator();
    }

    /*public void remove(Object o) {
         ruestungsList.removeValue(o,true);
    }*/

    public WaffeNah get(int i) {
         return waffenNahList.get(i);
    }

    public void set(int i, WaffeNah waffeNah) {
        waffenNahList.set(i, waffeNah);
    }

    public void clear() {
        ruestungsList.clear();
    }

    /*public boolean containsAll(Collection<?> collection) {
        return ruestungsList.containsAll(collection);
    }*/
    public synchronized void resetAuswahl(){
        int temp=waffenNahList.size;
        for(int i=0;i<temp;i++){
            waffenNahList.get(i).setAusgewaehlt(false);
        }
        temp=waffenFernList.size;
        for(int i=0;i<temp;i++){
            waffenFernList.get(i).setAusgewaehlt(false);
        }
        temp=ruestungsList.size;
        for(int i=0;i<temp;i++){
            ruestungsList.get(i).setAusgewaehlt(false);
        }
        temp=handschuheList.size;
        for(int i=0;i<temp;i++){
            handschuheList.get(i).setAusgewaehlt(false);
        }
        temp=schuheList.size;
        for(int i=0;i<temp;i++){
            schuheList.get(i).setAusgewaehlt(false);
        }
        temp=helmList.size;
        for(int i=0;i<temp;i++){
            helmList.get(i).setAusgewaehlt(false);
        }
        temp=amulettList.size;
        for(int i=0;i<temp;i++){
            amulettList.get(i).setAusgewaehlt(false);
        }
        temp=benutzbarList.size;
        for(int i=0;i<temp;i++){
            benutzbarList.get(i).setAusgewaehlt(false);
        }
    }
    // Getter und Setter für angelegte Variablen
    public synchronized WaffeNah getAngelegtWaffeNah() {return angelegtWaffeNah;}
    public synchronized void setAngelegtWaffeNah(WaffeNah angelegtWaffeNah) {
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

    public synchronized Schuhe getAngelegtSchuhe() {return angelegtSchuhe;}
    public synchronized void setAngelegtSchuhe(Schuhe angelegtSchuhe) {
        if (this.angelegtSchuhe != null)
            this.angelegtSchuhe.setAngelegt(false);
        if (angelegtSchuhe != null) {
            this.angelegtSchuhe = angelegtSchuhe;
            angelegtSchuhe.setAngelegt(true);
        } else {
            this.angelegtSchuhe = null;
        }
    }
//TODO update von Werten.
public synchronized Handschuhe getAngelegtHandschuhe() {return angelegtHandschuhe;}
    public synchronized void setAngelegtHandschuhe(Handschuhe angelegtHandschuhe) {
        if (this.angelegtHandschuhe != null)
            this.angelegtHandschuhe.setAngelegt(false);
        if (angelegtHandschuhe != null) {
            this.angelegtHandschuhe = angelegtHandschuhe;
            angelegtHandschuhe.setAngelegt(true);
        } else {
            this.angelegtHandschuhe = null;
        }
    }
    public synchronized Helm getAngelegtHelm() {return angelegtHelm;}
    public synchronized void setAngelegtHelm(Helm angelegtHelm) {
        if (this.angelegtHelm != null)
            this.angelegtHelm.setAngelegt(false);
        if (angelegtHelm != null) {
            this.angelegtHelm = angelegtHelm;
            angelegtHelm.setAngelegt(true);
        } else {
            this.angelegtHelm = null;
        }
    }
    public synchronized Amulett getAngelegtAmulett() {return angelegtAmulett;}
    public synchronized void setAngelegtAmulett(Amulett angelegtAmulett) {
        if (this.angelegtAmulett != null)
            this.angelegtAmulett.setAngelegt(false);
        if (angelegtAmulett != null) {
            this.angelegtAmulett = angelegtAmulett;
            angelegtAmulett.setAngelegt(true);
        } else {
            this.angelegtAmulett = null;
        }
    }
    public synchronized Brust getAngelegtRuestung() {return angelegtRuestung;}
    public synchronized void setAngelegtRuestung(Brust angelegtRuestung) {
        if(this.angelegtRuestung!=null)
            this.angelegtRuestung.setAngelegt(false);
        if(angelegtRuestung!=null){
            this.angelegtRuestung = angelegtRuestung;angelegtRuestung.setAngelegt(true);}
        else{this.angelegtRuestung=null;}
        getHeld().updateRuestung();}
}
