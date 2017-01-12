package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 01.12.2016.
 */

public class InventarList {
    private static ArrayList<Armor> ruestungsList;
    private static ArrayList<WaffeNah> waffenNahList;
    private static ArrayList<WaffeFern> waffenFernList;
    private static ArrayList<Benutzbar> benutzbarList;

    private WaffeNah angelegtWaffeNah;
    private WaffeFern angelegtWaffeFern;
    private Armor angelegtRuestung;
    public InventarList(){
        ruestungsList=new ArrayList<Armor>();
        waffenNahList =new ArrayList<WaffeNah>();
        waffenFernList =new ArrayList<WaffeFern>();
        benutzbarList=new ArrayList<Benutzbar>();
    }
    public ArrayList<Benutzbar> getBenutzbarList() {
        return benutzbarList;
    }

    public void setBenutzbarList(ArrayList<Benutzbar> benutzbarList) {
        this.benutzbarList = benutzbarList;
    }

    public ArrayList<WaffeNah> getWaffenNahList() {
        return waffenNahList;
    }
    public ArrayList<WaffeFern> getWaffenFernList() {
        return waffenFernList;
    }

    public void setWaffenList(ArrayList<WaffeNah> waffenList) {
        this.waffenNahList = waffenList;
    }

    public ArrayList<Armor> getRuestungsList() {
        return ruestungsList;
    }
    public void setRuestungsList(ArrayList<Armor> ruestungsList) {
        this.ruestungsList = ruestungsList;
    }

    public int size() {
        return ruestungsList.size();
    }

    public void add(int i, Armor armor) {
        ruestungsList.add(i, armor);
    }

    public boolean contains(Object o) {
        return ruestungsList.contains(o);
    }

    public boolean add(Armor armor) {return ruestungsList.add(armor);}
    public boolean add(Benutzbar benutzbar) {return benutzbarList.add(benutzbar);}
    public boolean remove(Benutzbar benutzbar) {
        return benutzbarList.remove(benutzbar);
    }

    public Object[] toArray() {
        return ruestungsList.toArray();
    }

    public Iterator<Armor> iterator() {
        return ruestungsList.iterator();
    }

    public boolean isEmpty() {
        return ruestungsList.isEmpty();
    }

    public boolean add(WaffeNah waffeNah) {
        return waffenNahList.add(waffeNah);
    }
    public boolean add(WaffeFern waffeFern) {
        return waffenFernList.add(waffeFern);
    }


    public <T> T[] toArray(T[] ts) {
        return ruestungsList.toArray(ts);
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
        temp=benutzbarList.size();
        for(int i=0;i<temp;i++){
            benutzbarList.get(i).setAusgewaehlt(false);
        }
//        Gdx.app.log("Resetdurchgeführt","");
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

    public synchronized Armor getAngelegtRuestung() {return angelegtRuestung;}
    public synchronized void setAngelegtRuestung(Armor angelegtRuestung) {
        if(this.angelegtRuestung!=null)
            this.angelegtRuestung.setAngelegt(false);
        if(angelegtRuestung!=null){
            this.angelegtRuestung = angelegtRuestung;angelegtRuestung.setAngelegt(true);}
        else{this.angelegtRuestung=null;}
        getHeld().setRuestung();}
}
