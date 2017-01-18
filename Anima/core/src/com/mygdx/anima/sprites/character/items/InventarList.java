package com.mygdx.anima.sprites.character.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 01.12.2016.
 */

public class InventarList {
    private static ArrayList<Brust> ruestungsList;
    private static ArrayList<Handschuhe> handschuheList;
    private static ArrayList<Schuhe> schuheList;
    private static ArrayList<Helm> helmList;
    private static ArrayList<Amulett> amulettList;

    private static ArrayList<WaffeNah> waffenNahList;
    private static ArrayList<WaffeFern> waffenFernList;
    private static ArrayList<Benutzbar> benutzbarList;

    private WaffeNah angelegtWaffeNah;
    private WaffeFern angelegtWaffeFern;
    private Brust angelegtRuestung;
    private Handschuhe angelegtHandschuhe;
    private Schuhe angelegtSchuhe;
    private Helm angelegtHelm;
    private Amulett angelegtAmulett;

    public InventarList(){
        ruestungsList=new ArrayList<Brust>();
        handschuheList=new ArrayList<Handschuhe>();
        schuheList=new ArrayList<Schuhe>();
        helmList=new ArrayList<Helm>();
        amulettList=new ArrayList<Amulett>();

        waffenNahList =new ArrayList<WaffeNah>();
        waffenFernList =new ArrayList<WaffeFern>();
        benutzbarList=new ArrayList<Benutzbar>();

    }
    public ArrayList<Benutzbar> getBenutzbarList() {
        return benutzbarList;
    }

    public ArrayList<WaffeNah> getWaffenNahList() {
        return waffenNahList;
    }
    public ArrayList<WaffeFern> getWaffenFernList() {
        return waffenFernList;
    }
    public ArrayList<Brust> getRuestungsList() {
        return ruestungsList;
    }
    public ArrayList<Handschuhe> getHandschuheList() {return handschuheList;}
    public ArrayList<Schuhe> getSchuheList() {return schuheList;}
    public ArrayList<Helm> getHelmList() {return helmList;}
    public ArrayList<Amulett> getAmulettList() {return amulettList;}

    public int size() {
        return ruestungsList.size();
    }

    public void add(int i, Brust brust) {
        ruestungsList.add(i, brust);
    }

    public boolean add(Brust brust) {return ruestungsList.add(brust);}
    public boolean add(Schuhe schuhe) {return schuheList.add(schuhe);}
    public boolean add(Handschuhe handschuhe) {return handschuheList.add(handschuhe);}
    public boolean add(Helm helm) {return helmList.add(helm);}
    public boolean add(Amulett amulett) {return amulettList.add(amulett);}


    public boolean add(Benutzbar benutzbar) {return benutzbarList.add(benutzbar);}
    public boolean add(WaffeNah waffeNah) {
        return waffenNahList.add(waffeNah);
    }
    public boolean add(WaffeFern waffeFern) {
        return waffenFernList.add(waffeFern);
    }

    public boolean remove(Benutzbar benutzbar) {
        return benutzbarList.remove(benutzbar);
    }

    public Iterator<Brust> iterator() {
        return ruestungsList.iterator();
    }

    public boolean remove(Object o) {
        return ruestungsList.remove(o);
    }

    public WaffeNah get(int i) {
        return waffenNahList.get(i);
    }

    public WaffeNah set(int i, WaffeNah waffeNah) {
        return waffenNahList.set(i, waffeNah);
    }

    public void clear() {
        ruestungsList.clear();
    }

    public boolean containsAll(Collection<?> collection) {
        return ruestungsList.containsAll(collection);
    }
    public synchronized void resetAuswahl(){
        int temp=waffenNahList.size();
        for(int i=0;i<temp;i++){
            waffenNahList.get(i).setAusgewaehlt(false);
        }
        temp=waffenFernList.size();
        for(int i=0;i<temp;i++){
            waffenFernList.get(i).setAusgewaehlt(false);
        }
        temp=ruestungsList.size();
        for(int i=0;i<temp;i++){
            ruestungsList.get(i).setAusgewaehlt(false);
        }
        temp=handschuheList.size();
        for(int i=0;i<temp;i++){
            handschuheList.get(i).setAusgewaehlt(false);
        }
        temp=schuheList.size();
        for(int i=0;i<temp;i++){
            schuheList.get(i).setAusgewaehlt(false);
        }
        temp=helmList.size();
        for(int i=0;i<temp;i++){
            helmList.get(i).setAusgewaehlt(false);
        }
        temp=amulettList.size();
        for(int i=0;i<temp;i++){
            amulettList.get(i).setAusgewaehlt(false);
        }
        temp=benutzbarList.size();
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
