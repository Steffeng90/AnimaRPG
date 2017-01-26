package com.mygdx.anima.sprites.character.interaktiveObjekte;

import java.io.Serializable;

/**
 * Created by Steffen on 24.01.2017.
 */

public class SchatztruhenSpeicherObjekt implements Serializable{
    int mapID, truhenID;
    public SchatztruhenSpeicherObjekt(int mapID, int truhenID){
        this.mapID=mapID;
        this.truhenID=truhenID;
    }
    public int getTruhenID() {
        return truhenID;
    }

    public void setTruhenID(int truhenID) {
        this.truhenID = truhenID;
    }

    public int getMapID() {
        return mapID;
    }

    public void setMapID(int mapID) {
        this.mapID = mapID;
    }
}
