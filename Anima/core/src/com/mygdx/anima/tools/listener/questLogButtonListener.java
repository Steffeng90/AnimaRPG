package com.mygdx.anima.tools.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.anima.screens.menuReiter.QuestReiter;
import com.mygdx.anima.sprites.character.Quest;

/**
 * Created by Steffen on 05.12.2016.
 */

public class questLogButtonListener extends InputListener {
    Quest quest;
    QuestReiter reiter;

    public questLogButtonListener(Quest paraquest, QuestReiter reiter){
        quest =paraquest;

        this.reiter=reiter;

    }
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
        reiter.questLogRechts.clear();
        reiter.questLogDetails.clear();
        reiter.auswahlQuest=quest;
        reiter.zeigeQuestButtons();
        reiter.auswahlAnzeige();
    }
}
