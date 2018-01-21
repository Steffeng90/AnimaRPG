package com.mygdx.anima.screens.menuReiter;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.screens.MenuScreen;
import com.mygdx.anima.sprites.character.Quest;
import com.mygdx.anima.sprites.character.QuestLog;
import com.mygdx.anima.tools.listener.questLogButtonListener;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 12.12.2016.
 */

public class QuestReiter extends Group {
    private float scrollbarposition;
    TextButton benutzenButton;
    public ScrollPane pane;
    public Table questLogDetails, questLogRechts;
    private float width, height, questLinksWidth, questRechtsWidth,reiterWidth;
    public Quest auswahlQuest;
    public MenuScreen menu;
    public float zeilenhoehe;
    private String eigenschaft, aktuell="",verbesserung=" ";

    public QuestReiter(MenuScreen menu){
        this.menu=menu;
        this.width=menu.getWidth();
        this.height=menu.getHeight();
        auswahlQuest = null;
        menu.questReiter=this;
        scrollbarposition=0f;
        reiterWidth=width*(2f/10f);
        questLinksWidth =width*(5f/10f);
        questRechtsWidth =width*(3f/10f);
        zeilenhoehe=height/100f;
        auswahlAnzeige();
        this.addActor(questLogDetails);
        pane = zeigeQuestButtons();
        this.addActor(pane);
    }
    public void auswahlAnzeige(){
        questLogDetails = new Table(menu.getSkin());
        questLogDetails.setWidth(questLinksWidth);
        questLogDetails.setPosition(reiterWidth, height);
        questLogDetails.align(Align.left | Align.top);
        Label label = new Label("Aktueller Quest:", menu.getSkin());
        if(auswahlQuest !=null) {
            System.out.println("Quest ausgewählt und angezeigt");
            Label beschreibung= new Label (auswahlQuest.getAktuellenQuestpart().getInhalt(),menu.getSkin());
            beschreibung.setWrap(true);
            Label name= new Label (auswahlQuest.getName(),menu.getSkin());
            name.setWrap(true);
            questLogDetails.add(label).width(questLinksWidth);
            questLogDetails.row();
            questLogDetails.add(name).width(questLinksWidth);
            questLogDetails.row();
            questLogDetails.add(beschreibung).width(questLinksWidth);
}
        this.addActor(questLogDetails);
    }
    public ScrollPane zeigeQuestButtons() {
        questLogRechts = new Table();
        //questLogRechts.setWidth(questRechtsWidth);
        // questLogRechts.setPosition(0, height);
        questLogRechts.align(Align.left | Align.top);
        questLogRechts.setSize(questRechtsWidth*2,height);
        questLogRechts.add(new Label("QuestListe", menu.getSkin()));
        questLogRechts.row();

        QuestLog questLog = getHeld().getQuestlog();
        int size = questLog.getQuestlog().size;
        for (int i = 0; i < size; i++) {
            benutzenButton = new TextButton(questLog.getQuestlog().get(i).getName(), menu.getSkin());
            benutzenButton.addListener(new questLogButtonListener(questLog.getQuestlog().get(i),this));
            questLogRechts.add(benutzenButton).size(questRechtsWidth,zeilenhoehe*15);
            questLogRechts.row();
        }

        ScrollPane scrollPane = new ScrollPane(questLogRechts, menu.getSkin());
        scrollPane.setBounds(0, 0, questRechtsWidth, height);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setPosition((reiterWidth+questLinksWidth), 0);
        scrollPane.setFadeScrollBars(true);
        scrollPane.setScrollbarsOnTop(false);
        scrollPane.setupFadeScrollBars(0, 0);
        scrollPane.layout();
        scrollPane.setScrollPercentY(getScrollbarposition());
        scrollPane.layout();
        this.addActor(scrollPane);
        pane=scrollPane;
//        menu.zurueckButtonErzeugen();
        return scrollPane;

    }
    public synchronized float getScrollbarposition() {return scrollbarposition;}
    public synchronized void setScrollbarposition(float scrollbarposition) {this.scrollbarposition = scrollbarposition;}
}
