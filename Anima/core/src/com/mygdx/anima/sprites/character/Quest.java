package com.mygdx.anima.sprites.character;

import com.mygdx.anima.sprites.character.items.Item;

/**
 * Created by Steffen on 10.01.2018.
 */

public class Quest {
    private int id,aktuellerQuestPart;
    private String name;
    private QuestPart[] questParts;
    private boolean angenommen,abgeschlossen,questAktivierungsEvent,ausgewaehltImLog;
    private Item belohnung;

    public Quest(int id,String name,String belohnung,QuestPart[] questParts){
        this.id=id;
        this.name=name;
        aktuellerQuestPart=0;
        angenommen=true;
        abgeschlossen=false;
        setQuestParts(questParts);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QuestPart[] getQuestParts() {
        return questParts;
    }

    public void setQuestParts(QuestPart[] questParts) {
        this.questParts = questParts;
    }

    public boolean isAngenommen() {
        return angenommen;
    }

    public void setAngenommen(boolean angenommen) {
        this.angenommen = angenommen;
    }

    public boolean isAbgeschlossen() {
        return abgeschlossen;
    }

    public void setAbgeschlossen(boolean abgeschlossen) {
        this.abgeschlossen = abgeschlossen;
    }

    public boolean isQuestAktivierungsEvent() {
        return questAktivierungsEvent;
    }

    public void setQuestAktivierungsEvent(boolean questAktivierungsEvent) {
        this.questAktivierungsEvent = questAktivierungsEvent;
    }

    public Item getBelohnung() {
        return belohnung;
    }

    public void setBelohnung(Item belohnung) {
        this.belohnung = belohnung;
    }
    public boolean questFortschritt(){
        // Gibt true zurück, wenn das Quest abgeschlossen ist, sonst false
        System.out.print("aktQP"+aktuellerQuestPart+" length"+questParts.length);
        if(aktuellerQuestPart<(questParts.length-1)){
            aktuellerQuestPart++;
            return false;
        }else{
            setAbgeschlossen(true);
            return true;
        }
    }
    public int getId(){
        return id;
    }
    public boolean isAusgewaehltImLog() {
        return ausgewaehltImLog;
    }

    public void setAusgewaehltImLog(boolean ausgewaehltImLog) {
        this.ausgewaehltImLog = ausgewaehltImLog;
    }

    public QuestPart getAktuellenQuestpart(){
        return questParts[aktuellerQuestPart];
    }
    public int getIDAktuellerQuestPart(){
        return aktuellerQuestPart;
    }
    public void setIDAktuellerQuestPart(int part){
        aktuellerQuestPart=part;
    }
}

