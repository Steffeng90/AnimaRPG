package com.mygdx.anima.sprites.character;

import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.scenes.QuestInfo;
import com.mygdx.anima.screens.Playscreen;

import java.io.Serializable;

/**
 * Created by Steffen on 01.12.2016.
 */

public class QuestLog implements Serializable {
    private static Array<Quest> questArray;

    public QuestLog() {
        questArray = new Array<Quest>();
    }
    public void setQuestArray(Array<Quest> arr){
        questArray=arr;
    }
    public Array<Quest> getQuestArray(){
        return questArray;
    }

    public int size() {
        return questArray.size;
    }

    public void addQuest(Quest quest) {
        questArray.add(quest);
    }

    public boolean remove(Quest quest) {
        return questArray.removeValue(quest, true);
    }

    public synchronized void resetAuswahl() {
        System.out.println("Questarray:+ "+ questArray);
        int temp = questArray.size;
        for (int i = 0; i < temp; i++) {
            questArray.get(i).setAusgewaehltImLog(false);
        }
    }

    public Array<Quest> getQuestlog() {
        return questArray;
    }

    public void checkEvents(int i, Playscreen screen) {
        QuestGenerator.generateQuest(screen,screen.getGame().batch,i,null);
        for (int y = 0; y < questArray.size; y++) {
            System.out.println("Compare" + i + "mit" + questArray.get(y).getAktuellenQuestpart().getPartAbschlussEvent());
            if (i == questArray.get(y).getAktuellenQuestpart().getPartAbschlussEvent()) {
                if (questArray.get(y).questFortschritt()) {
                    screen.setQuestInfo(new QuestInfo(screen, screen.getGame().batch, questArray.get(y).getName(), "Quest abgeschlossen"));
                } else {
                    screen.setQuestInfo(new QuestInfo(screen, screen.getGame().batch, questArray.get(y).getName(), "Quest fortgeschritten"));
                }
            }
        }
    }
}

