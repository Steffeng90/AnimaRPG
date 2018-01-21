package com.mygdx.anima.sprites.character;

import com.mygdx.anima.sprites.character.items.Item;

/**
 * Created by Steffen on 10.01.2018.
 */

public class QuestPart {
    private String inhalt;
    private int PartAbschlussEvent;

    public QuestPart(int abschlussEvent,String inhalt){
        this.PartAbschlussEvent=abschlussEvent;
        this.inhalt=inhalt;
    }
    public String getInhalt() {
        return inhalt;
    }

    public void setInhalt(String inhalt) {
        this.inhalt = inhalt;
    }

    public int getPartAbschlussEvent() {
        return PartAbschlussEvent;
    }

    public void setPartAbschlussEvent(int partAbschlussEvent) {
        PartAbschlussEvent = partAbschlussEvent;
    }
}
