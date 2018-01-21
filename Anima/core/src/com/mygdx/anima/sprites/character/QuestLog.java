package com.mygdx.anima.sprites.character;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.scenes.QuestInfo;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.zauber.ZauberEntity;

import java.io.Serializable;

/**
 * Created by Steffen on 01.12.2016.
 */

public class QuestLog implements Serializable {
    private static Array<Quest> questlog;

    public QuestLog() {
        questlog = new Array<Quest>();
    }

    public int size() {
        return questlog.size;
    }

    public void addQuest(Quest quest) {
        questlog.add(quest);
    }

    public boolean remove(Quest quest) {
        return questlog.removeValue(quest, true);
    }

    public synchronized void resetAuswahl() {
        int temp = questlog.size;
        for (int i = 0; i < temp; i++) {
            questlog.get(i).setAusgewaehltImLog(false);
        }
    }

    public Array<Quest> getQuestlog() {
        return questlog;
    }

    public void checkEvents(int i, Playscreen screen) {
        QuestGenerator.generateQuest(screen,screen.getGame().batch,i);
        for (int y = 0; y < questlog.size; y++) {
            System.out.println("Compare" + i + "mit" + questlog.get(y).getAktuellenQuestpart().getPartAbschlussEvent());
            if (i == questlog.get(y).getAktuellenQuestpart().getPartAbschlussEvent()) {
                if (questlog.get(y).questFortschritt()) {
                    screen.setQuestInfo(new QuestInfo(screen, screen.getGame().batch, "Erster Quest", "Quest abgeschlossen"));
                } else {
                    screen.setQuestInfo(new QuestInfo(screen, screen.getGame().batch, "Erster Quest", "Quest fortgeschritten"));
                }
            }
        }
    }
}

