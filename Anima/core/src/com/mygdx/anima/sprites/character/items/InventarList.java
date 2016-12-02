package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Steffen on 01.12.2016.
 */

public class InventarList {
    private ArrayList<Armor> ruestungsList;
    private ArrayList<Waffe> waffenList;
    private ArrayList<Benutzbar> benutzbarList;

    public InventarList(){
        ruestungsList=new ArrayList<Armor>();
        waffenList=new ArrayList<Waffe>();
        benutzbarList=new ArrayList<Benutzbar>();
    }
    public List<Benutzbar> getBenutzbarList() {
        return benutzbarList;
    }

    public void setBenutzbarList(ArrayList<Benutzbar> benutzbarList) {
        this.benutzbarList = benutzbarList;
    }

    public List<Waffe> getWaffenList() {
        return waffenList;
    }

    public void setWaffenList(ArrayList<Waffe> waffenList) {
        this.waffenList = waffenList;
    }

    public List<Armor> getRuestungsList() {
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

    public boolean add(Armor armor) {
        return ruestungsList.add(armor);
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

    public boolean add(Waffe waffe) {
        Gdx.app.log("Waffe hinzugefügt","");
        return waffenList.add(waffe);
    }

    public <T> T[] toArray(T[] ts) {
        return ruestungsList.toArray(ts);
    }

    public boolean remove(Object o) {
        return ruestungsList.remove(o);
    }

    public Waffe get(int i) {
        return waffenList.get(i);
    }

    public Waffe set(int i, Waffe waffe) {
        return waffenList.set(i, waffe);
    }

    public void clear() {
        ruestungsList.clear();
    }

    public boolean containsAll(Collection<?> collection) {
        return ruestungsList.containsAll(collection);
    }
}
