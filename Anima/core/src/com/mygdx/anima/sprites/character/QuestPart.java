package com.mygdx.anima.sprites.character;

import com.mygdx.anima.sprites.character.items.Item;

/**
 * Created by Steffen on 10.01.2018.
 */

public class QuestPart {
    private String inhalt;
    private int PartAbschlussEvent[];

    public QuestPart(int abschlussEvent,int abschlussEventB,int abschlussEventC, String inhalt){
        this.PartAbschlussEvent=new int[]{abschlussEvent,abschlussEventB,abschlussEventC};
        this.inhalt=inhalt;
    }
    public QuestPart(int abschlussEvent, int abschlussEventB,String inhalt){
        this.PartAbschlussEvent=new int[]{abschlussEvent,abschlussEventB};
        this.inhalt=inhalt;
    }
    public QuestPart(int abschlussEvent,String inhalt){
        this.PartAbschlussEvent=new int[]{abschlussEvent};
        this.inhalt=inhalt;
    }
    public QuestPart(int[] abschlussEvent,String inhalt){
        this.PartAbschlussEvent=new int[abschlussEvent.length];
        for(int y=0;y<abschlussEvent.length;y++){
            this.PartAbschlussEvent[y]=abschlussEvent[y];
            this.inhalt=inhalt;
        }

    }
    public String getInhalt() {
        return inhalt;
    }

    public void setInhalt(String inhalt) {
        this.inhalt = inhalt;
    }

    public int getPartAbschlussEvent(int i) {
        return PartAbschlussEvent[i];
    }
    public int[] getPartAbschlussEvent() {
        return PartAbschlussEvent;
    }
    public void setPartAbschlussEvent(int[] partAbschlussEvent) {
        PartAbschlussEvent = partAbschlussEvent;
    }
}
