package com.mygdx.anima.screens.menuReiter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mygdx.anima.screens.Menu;
import com.mygdx.anima.sprites.character.items.Benutzbar;
import com.mygdx.anima.tools.listener.GegenstandListener;
import com.mygdx.anima.tools.listener.benutzenButtonListener;


import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 12.12.2016.
 */

public class GegenstandReiter extends Group {
    private float scrollbarposition;
    TextButton benutzenButton;
    public ScrollPane pane;
    public Table inventarLinks,inventarRechts;
    private float width, height,invLinksWidth,invRechtsWidth,reiterWidth;
    public Benutzbar auswahlItem;
    public Menu menu;
    private String eigenschaft, aktuell="",verbesserung=" ";

    public GegenstandReiter(Menu menu){
        this.menu=menu;
        this.width=menu.getWidth();
        this.height=menu.getHeight();
        auswahlItem = null;
        scrollbarposition=0f;
        reiterWidth=width*2/10;
        invLinksWidth=width*3/10;
        invRechtsWidth=width*5/10;
        auswahlAnzeige();
        this.addActor(inventarLinks);
        pane = zeigeItems();
        this.addActor(pane);
    }
    public void benutztAnzeige(){
        inventarLinks = new Table(menu.getSkin());
        inventarLinks.setWidth(invLinksWidth);
        inventarLinks.setPosition(reiterWidth, height);
        inventarLinks.align(Align.left | Align.top);
        Label label1 = new Label("Gegenstand benutzt!", menu.getSkin());
        Label label2;
        switch(auswahlItem.getKategoriezahl()){
            case 1:
            case 2:
            case 3:
            case 4: label2 = new Label("Du konntest deine \n"+eigenschaft+" von\n" + aktuell+" " +
                    "auf " + verbesserung +"\n steigern!", menu.getSkin());break;
            case 5:
            case 6:
            case 7:
            default: label2 = new Label("Du konntest deine \n"+eigenschaft+" von " + aktuell+" " +
                    "\nauf " + verbesserung +" steigern!", menu.getSkin());break;
        }

        inventarLinks.add(label1).size(invLinksWidth, height / 12f);
        inventarLinks.row();
        inventarLinks.add(label2).size(invLinksWidth, height / 4f);
        this.addActor(inventarLinks);

    }
    public void auswahlAnzeige(){
        inventarLinks = new Table(menu.getSkin());
        inventarLinks.setWidth(invLinksWidth);
        inventarLinks.setPosition(reiterWidth, height);
        inventarLinks.align(Align.left | Align.top);
        Label label = new Label("Gegenstand:", menu.getSkin());
        if(auswahlItem!=null) {
            String name=" ", beschreibung=auswahlItem.getBeschreibung();
            name=auswahlItem.getName();

            switch(auswahlItem.getKategoriezahl())
            {
                case 1: eigenschaft="Lebenspunkte";
                aktuell=""+getHeld().getCurrentHitpoints()+"/"+getHeld().getMaxHitpoints();
                if(auswahlItem.getWertUpgradeZahl()<(getHeld().getMaxHitpoints()-getHeld().getCurrentHitpoints())) {
                    verbesserung = "" + (getHeld().getCurrentHitpoints() + auswahlItem.getWertUpgradeZahl()) + "/" +getHeld().getMaxHitpoints();
                }else{
                    verbesserung=""+(getHeld().getMaxHitpoints()+"/"+getHeld().getMaxHitpoints());
                }
                break;
                case 2: eigenschaft="Maximale Lebenspunkte";
                    aktuell=""+(getHeld().getCurrentHitpoints()+"/"+getHeld().getMaxHitpoints());
                    verbesserung = "" + (getHeld().getCurrentHitpoints() + auswahlItem.getWertUpgradeZahl()) + "/" +(getHeld().getMaxHitpoints()  + auswahlItem.getWertUpgradeZahl());
                    break;
                case 3: eigenschaft="Magiepunkte";
                    aktuell=""+getHeld().getCurrentMana()+"/"+getHeld().getMaxMana();
                    if(auswahlItem.getWertUpgradeZahl()<(getHeld().getMaxMana()-getHeld().getCurrentMana())) {
                        verbesserung = "" + (getHeld().getCurrentMana() + auswahlItem.getWertUpgradeZahl()) + "/" +getHeld().getMaxMana();
                    }else{
                        verbesserung=""+(getHeld().getMaxMana()+"/"+getHeld().getMaxMana());
                    }
                    break;
                case 4: eigenschaft="Maximale Magiepunkte";
                    aktuell=""+getHeld().getCurrentMana()+"/"+getHeld().getMaxMana();
                    verbesserung = "" + (getHeld().getCurrentMana() + auswahlItem.getWertUpgradeZahl()) + "/" +(getHeld().getMaxMana()  + auswahlItem.getWertUpgradeZahl());
                    break;
                case 5: eigenschaft="Kraft";
                    aktuell=""+getHeld().getStaerke();
                    verbesserung = "" + (getHeld().getStaerke() + auswahlItem.getWertUpgradeZahl());
                    break;
                case 6: eigenschaft="Geschicklichkeit";
                    aktuell=""+getHeld().getGeschick();
                    verbesserung = "" + (getHeld().getGeschick() + auswahlItem.getWertUpgradeZahl());
                    break;
                case 7: eigenschaft="Zauberkraft";
                    aktuell=""+getHeld().getZauberkraft();
                    verbesserung = "" + (getHeld().getZauberkraft() + auswahlItem.getWertUpgradeZahl());
                    break;
                case 8: eigenschaft="Zauberabwehr";
                    aktuell=""+getHeld().getZauberwiderstand();
                    verbesserung = "" + (getHeld().getZauberwiderstand() + auswahlItem.getWertUpgradeZahl());
                    break;
                default:
                    eigenschaft=aktuell=verbesserung="undefined";

            }
            //eigenschaft
            inventarLinks.add(label).colspan(2);
            inventarLinks.row();
            inventarLinks.add(new Label(name, menu.getSkin())).size(invLinksWidth, height / 12f).colspan(2);
            inventarLinks.row();
            inventarLinks.add(new Label(beschreibung, menu.getSkin())).size(invLinksWidth*3/4, height / 4f).colspan(2).align(Align.left);
            inventarLinks.row();
            //inventarLinks.add(new Label(eigenschaft, menu.getSkin())).size(invLinksWidth*3/4, height / 12f);
            inventarLinks.row();

            inventarLinks.add(new Label(aktuell+" wird zu "+verbesserung, menu.getSkin())).size(invLinksWidth*1/4, height / 12f).align(Align.left);
            inventarLinks.row();
            inventarLinks.row();

                benutzenButton = new TextButton("Benutzen", menu.getSkin());
            benutzenButton.addListener(new benutzenButtonListener(auswahlItem,this));
                inventarLinks.add(benutzenButton).size(invLinksWidth, height / 3f).colspan(2);


            inventarLinks.row();
            inventarLinks.add(new TextButton("Wegwerfen", menu.getSkin())).size(invLinksWidth, height / 6f).colspan(2);
            inventarLinks.row();

        }
        this.addActor(inventarLinks);
    }
    public ScrollPane zeigeItems() {
        inventarRechts = new Table();
        inventarRechts.setWidth(invRechtsWidth);
        inventarRechts.setPosition(invRechtsWidth, height);
        inventarRechts.align(Align.left | Align.top);
        inventarRechts.add(new Label("Gegenstaende", menu.getSkin())).colspan(3);
        inventarRechts.row();

        Array<Benutzbar> liste = getHeld().getHeldenInventar().getBenutzbarList();
        int size = liste.size;
        for (int i = 0; i < size; i++) {
            if ((i) % 5 == 0) {inventarRechts.row();}
            Image beispiel4 = new Image(liste.get(i).getGrafik());
            beispiel4.addListener(new GegenstandListener(this, liste.get(i),beispiel4));
            inventarRechts.add(beispiel4).size(width / 10f, width / 10f);
        }

        ScrollPane scrollPane = new ScrollPane(inventarRechts, menu.getSkin());
        scrollPane.setBounds(0, 0, width / 2, height);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setPosition(width / 2, 0);
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
