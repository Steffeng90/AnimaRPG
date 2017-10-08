package com.mygdx.anima.screens.menuReiter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.mygdx.anima.screens.Menu;
import com.mygdx.anima.sprites.character.items.Amulett;
import com.mygdx.anima.sprites.character.items.Brust;
import com.mygdx.anima.sprites.character.items.Handschuhe;
import com.mygdx.anima.sprites.character.items.Helm;
import com.mygdx.anima.sprites.character.items.Item;
import com.mygdx.anima.sprites.character.items.Schuhe;
import com.mygdx.anima.sprites.character.items.WaffeFern;
import com.mygdx.anima.sprites.character.items.WaffeNah;
import com.mygdx.anima.tools.listener.InventarListener;
import com.mygdx.anima.tools.listener.anlegeButtonListener;

import java.util.ArrayList;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 12.12.2016.
 */

public class InventarReiter extends Group {
    Item angelegtWaffeNah, angelegtWaffeFern, angelegtRuestung;
    private float scrollbarposition;
    TextButton angelegtButton,ausziehenButton,wegwerfButton;
    public ScrollPane pane;
    public Table inventarLinks,inventarRechts;
    private float width, height,invLinksWidth,invRechtsWidth,reiterWidth;
    public Item auswahlItem;
    public Menu menu;
    int size;

    public InventarReiter(Menu menu){
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

    public void auswahlAnzeige(){
        inventarLinks = new Table(menu.getSkin());
        inventarLinks.setWidth(invLinksWidth);
        inventarLinks.setPosition(reiterWidth, height);
        inventarLinks.align(Align.left | Align.top);
        Label aktWaffe = new Label("Angelegt:", menu.getSkin());
        Label ausgwWaffe = new Label("Auswahl:", menu.getSkin());
        if(auswahlItem!=null) {
            String name=" ",eigenschaft1=" ",wert1auswahl=" ",wert1angelegt=" ",
                    eigenschaft2=" ",wert2auswahl=" ",wert2angelegt=" ",eigenschaft3,wert3auswahl=" ",wert3angelegt=" ",nameAngelegt=" ";
            name=auswahlItem.getName();
            eigenschaft3="Wert";


            switch (Item.kategorie.valueOf(auswahlItem.getItemKategorie())) {
                case nahkampf:
                    eigenschaft1="Schaden";
                    wert1auswahl=" "+((WaffeNah)auswahlItem).getSchaden();
                    wert3auswahl=" "+auswahlItem.getGoldWert();
                    angelegtWaffeNah=getHeld().getHeldenInventar().getAngelegtWaffeNah();
                    if(angelegtWaffeNah!=null){
                        nameAngelegt=angelegtWaffeNah.getName();
                        wert1angelegt=" "+((WaffeNah)angelegtWaffeNah).getSchaden();
                        wert3angelegt=" "+angelegtWaffeNah.getGoldWert();}
                    else{
                        nameAngelegt="Keine Nahwaffe angelegt";
                        wert1angelegt=" ";
                        wert3angelegt=" ";}
                    break;
                case fernkampf:
                    eigenschaft1="Fern-Schaden";
                    wert1auswahl=" "+((WaffeFern)auswahlItem).getSchaden();
                    wert3auswahl=" "+auswahlItem.getGoldWert();
                    angelegtWaffeFern=getHeld().getHeldenInventar().getAngelegtWaffeFern();
                    if(angelegtWaffeFern!=null){
                        nameAngelegt=angelegtWaffeFern.getName();
                        wert1angelegt=" "+((WaffeFern)angelegtWaffeFern).getSchaden();
                        wert3angelegt=" "+angelegtWaffeFern.getGoldWert();}
                    else{
                        nameAngelegt="Keine Fernwaffe angelegt";
                        wert1angelegt=" ";
                        wert3angelegt=" ";}
                    break;
                case brust:
                    eigenschaft1="Schutz:";
                    eigenschaft2="Leben-Regeneration:";

                    wert1auswahl=" "+((Brust)auswahlItem).getRuestung();
                    wert2auswahl=" "+((Brust)auswahlItem).getLpReg();
                    wert3auswahl=" "+auswahlItem.getGoldWert();
                    angelegtRuestung=getHeld().getHeldenInventar().getAngelegtRuestung();
                    if(angelegtRuestung!=null){
                        nameAngelegt=angelegtRuestung.getName();
                        wert1angelegt=" "+((Brust)angelegtRuestung).getRuestung();
                        wert2angelegt=" "+((Brust)angelegtRuestung).getLpReg();
                        wert3angelegt=" "+angelegtRuestung.getGoldWert();}
                    else{
                        nameAngelegt="Kein Brustschutz angelegt";
                        wert1angelegt=" ";
                        wert2angelegt=" ";
                        wert3angelegt=" ";}
                    break;
                case schuhe:
                    eigenschaft1="Schutz:";
                    eigenschaft2="Laufgeschwindigkeit:";

                    wert1auswahl=" "+((Schuhe)auswahlItem).getRuestung();
                    wert2auswahl=" "+((Schuhe)auswahlItem).getLaufgeschwindigkeit();
                    wert3auswahl=" "+auswahlItem.getGoldWert();
                    angelegtRuestung=getHeld().getHeldenInventar().getAngelegtSchuhe();
                    if(angelegtRuestung!=null){
                        nameAngelegt=angelegtRuestung.getName();
                        wert1angelegt=" "+((Schuhe)angelegtRuestung).getRuestung();
                        wert2angelegt=" "+((Schuhe)angelegtRuestung).getLaufgeschwindigkeit();
                        wert3angelegt=" "+angelegtRuestung.getGoldWert();}
                    else{
                        nameAngelegt="Keine Schuhe angelegt";
                        wert1angelegt=" ";
                        wert2angelegt=" ";
                        wert3angelegt=" ";}
                    break;
                case handschuhe:
                    eigenschaft1="Schutz:";
                    eigenschaft2="Angriffsgeschw.:";

                    wert1auswahl=" "+((Handschuhe)auswahlItem).getRuestung();
                    wert2auswahl=" "+((Handschuhe)auswahlItem).getAngriffgeschwindigkeit();
                    wert3auswahl=" "+auswahlItem.getGoldWert();
                    angelegtRuestung=getHeld().getHeldenInventar().getAngelegtHandschuhe();
                    if(angelegtRuestung!=null){
                        nameAngelegt=angelegtRuestung.getName();
                        wert1angelegt=" "+((Handschuhe)angelegtRuestung).getRuestung();
                        wert2angelegt=" "+((Handschuhe)angelegtRuestung).getAngriffgeschwindigkeit();
                        wert3angelegt=" "+angelegtRuestung.getGoldWert();}
                    else{
                        nameAngelegt="Keine Handschuhe angelegt";
                        wert1angelegt=" ";
                        wert2angelegt=" ";
                        wert3angelegt=" ";}
                    break;
                case helm:
                    eigenschaft1="Lebenspunkte:";
                    eigenschaft2="Zauberkraft:";

                    wert1auswahl=" "+((Helm)auswahlItem).getLebenspunkte();
                    wert2auswahl=" "+((Helm)auswahlItem).getZauberkraft();
                    wert3auswahl=" "+auswahlItem.getGoldWert();
                    angelegtRuestung=getHeld().getHeldenInventar().getAngelegtHelm();
                    if(angelegtRuestung!=null){
                        nameAngelegt=angelegtRuestung.getName();
                        wert1angelegt=" "+((Helm)angelegtRuestung).getLebenspunkte();
                        wert2angelegt=" "+((Helm)angelegtRuestung).getZauberkraft();
                        wert3angelegt=" "+angelegtRuestung.getGoldWert();}
                    else{
                        nameAngelegt="Kein Helm angelegt";
                        wert1angelegt=" ";
                        wert2angelegt=" ";
                        wert3angelegt=" ";}
                    break;
                case amulett:
                    eigenschaft1="Zauberwiderstand:";
                    eigenschaft2="Mana-Regeneration:";

                    wert1auswahl=" "+((Amulett)auswahlItem).getZauberwiderstand();
                    wert2auswahl=" "+((Amulett)auswahlItem).getManareg();
                    wert3auswahl=" "+auswahlItem.getGoldWert();
                    angelegtRuestung=getHeld().getHeldenInventar().getAngelegtAmulett();
                    if(angelegtRuestung!=null){
                        nameAngelegt=angelegtRuestung.getName();
                        wert1angelegt=" "+((Amulett)angelegtRuestung).getZauberwiderstand();
                        wert2angelegt=" "+((Amulett)angelegtRuestung).getManareg();
                        wert3angelegt=" "+angelegtRuestung.getGoldWert();}
                    else{
                        nameAngelegt="Kein Amulett angelegt";
                        wert1angelegt=" ";
                        wert2angelegt=" ";
                        wert3angelegt=" ";}
                    break;
                default:
                    break;
            }
            inventarLinks.add(aktWaffe).colspan(2);
            inventarLinks.row();
            inventarLinks.add(new Label(nameAngelegt, menu.getSkin())).size(invLinksWidth, height / 12f).colspan(2);
            inventarLinks.row();
            inventarLinks.add(new Label(eigenschaft1, menu.getSkin())).size(invLinksWidth*3/4, height / 12f);
            inventarLinks.add(new Label(wert1angelegt, menu.getSkin())).size(invLinksWidth*1/4, height / 12f);
            inventarLinks.row();
            inventarLinks.add(new Label(eigenschaft2, menu.getSkin())).size(invLinksWidth*3/4, height / 12f);
            inventarLinks.add(new Label(wert2angelegt, menu.getSkin())).size(invLinksWidth*1/4, height / 12f);
            inventarLinks.row();
            inventarLinks.add(new Label(eigenschaft3, menu.getSkin())).size(invLinksWidth*3/4, height / 12f);
            inventarLinks.add(new Label(wert3angelegt, menu.getSkin())).size(invLinksWidth*1/4, height / 12f);
            inventarLinks.row();
            inventarLinks.add(ausgwWaffe).colspan(2);
            inventarLinks.row();
            inventarLinks.add(new Label(name, menu.getSkin())).size(invLinksWidth, height / 12f).colspan(2);
            inventarLinks.row();
            inventarLinks.add(new Label(eigenschaft1, menu.getSkin())).size(invLinksWidth*3/4, height / 12f);
            inventarLinks.add(new Label(wert1auswahl,menu.getSkin())).size(invLinksWidth*1/4, height / 12f);
            inventarLinks.row();
            inventarLinks.add(new Label(eigenschaft2, menu.getSkin())).size(invLinksWidth*3/4, height / 12f);
            inventarLinks.add(new Label(wert2auswahl, menu.getSkin())).size(invLinksWidth*1/4, height / 12f);
            inventarLinks.row();
            inventarLinks.add(new Label(eigenschaft3, menu.getSkin())).size(invLinksWidth*3/4, height / 12f);
            inventarLinks.add(new Label(wert3auswahl, menu.getSkin())).size(invLinksWidth*1/4, height / 12f);
            inventarLinks.row();
            if(auswahlItem.isAngelegt()) {
                angelegtButton = new TextButton("Ablegen", menu.getSkin());
                angelegtButton.addListener(new anlegeButtonListener(menu.game, auswahlItem, this));
                inventarLinks.add(angelegtButton).size(invLinksWidth, height / 6f).colspan(2);
            }
            else{
                angelegtButton = new TextButton("Anlegen", menu.getSkin());
                angelegtButton.addListener(new anlegeButtonListener(menu.game, auswahlItem, this));
                inventarLinks.add(angelegtButton).size(invLinksWidth, height / 6f).colspan(2);
            }

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
        if(getHeld().getHeldenInventar().getWaffenNahList().size()==0 &&
                getHeld().getHeldenInventar().getWaffenFernList().size()==0 &&
                getHeld().getHeldenInventar().getRuestungsList().size()==0 &&
                getHeld().getHeldenInventar().getHelmList().size()==0 &&
                getHeld().getHeldenInventar().getHandschuheList().size()==0 &&
                getHeld().getHeldenInventar().getSchuheList().size()==0 &&
                getHeld().getHeldenInventar().getAmulettList().size()==0){
            inventarRechts.add(new Label("Du besitzt keine\n Ausruestungsgegenstaende.", menu.getSkin())).colspan(3);
        }
        if(getHeld().getHeldenInventar().getWaffenNahList().size()>0) {

            inventarRechts.add(new Label("Nahkampf-Waffen", menu.getSkin())).colspan(3);
            inventarRechts.row();
            ArrayList<WaffeNah> liste = getHeld().getHeldenInventar().getWaffenNahList();
            size = liste.size();
            for (int i = 0; i < size; i++) {
                if ((i) % 5 == 0) {
                    inventarRechts.row();
                }
                Image beispiel4 = new Image(liste.get(i).getGrafik());
                beispiel4.addListener(new InventarListener(this, liste.get(i), beispiel4));
                inventarRechts.add(beispiel4).size(width / 10f, width / 10f);
            }
        }
        inventarRechts.row();
        if(getHeld().getHeldenInventar().getWaffenFernList().size()>0) {
            inventarRechts.add(new Label("Fernkampf-Waffen", menu.getSkin())).colspan(3);
            inventarRechts.row();
            ArrayList<WaffeFern> listenf = getHeld().getHeldenInventar().getWaffenFernList();
            size = listenf.size();
            for (int i = 0; i < size; i++) {
                if ((i) % 5 == 0) {
                    inventarRechts.row();
                }
                Image beispiel4 = new Image(listenf.get(i).getGrafik());
                beispiel4.addListener(new InventarListener(this, listenf.get(i), beispiel4));
                inventarRechts.add(beispiel4).size(width / 10f, width / 10f);
            }
        }
        inventarRechts.row();
        if(getHeld().getHeldenInventar().getRuestungsList().size()>0){
        inventarRechts.add(new Label("Ruestungen", menu.getSkin())).colspan(3);
        inventarRechts.row();
        ArrayList<Brust> listeRues= getHeld().getHeldenInventar().getRuestungsList();
        size = listeRues.size();
        for (int i = 0; i < size; i++) {
            if ((i) % 5 == 0) {
                inventarRechts.row();}
            Image beispiel4=new Image(listeRues.get(i).getGrafik());
            beispiel4.addListener(new InventarListener(this, listeRues.get(i),beispiel4));
            inventarRechts.add(beispiel4).size(width / 10f, width / 10f);
            }
        }
        inventarRechts.row();
        if(getHeld().getHeldenInventar().getSchuheList().size()>0) {
            inventarRechts.add(new Label("Schuhe", menu.getSkin())).colspan(3);
            inventarRechts.row();
            ArrayList<Schuhe> listenf = getHeld().getHeldenInventar().getSchuheList();
            size = listenf.size();
            for (int i = 0; i < size; i++) {
                if ((i) % 5 == 0) {
                    inventarRechts.row();
                }
                Image beispiel4 = new Image(listenf.get(i).getGrafik());
                beispiel4.addListener(new InventarListener(this, listenf.get(i), beispiel4));
                inventarRechts.add(beispiel4).size(width / 10f, width / 10f);
            }
        }
        inventarRechts.row();
        if(getHeld().getHeldenInventar().getHandschuheList().size()>0) {
            inventarRechts.add(new Label("Handschuhe", menu.getSkin())).colspan(3);
            inventarRechts.row();
            ArrayList<Handschuhe> listenf = getHeld().getHeldenInventar().getHandschuheList();
            size = listenf.size();
            for (int i = 0; i < size; i++) {
                if ((i) % 5 == 0) {
                    inventarRechts.row();
                }
                Image beispiel4 = new Image(listenf.get(i).getGrafik());
                beispiel4.addListener(new InventarListener(this, listenf.get(i), beispiel4));
                inventarRechts.add(beispiel4).size(width / 10f, width / 10f);
            }
        }
        inventarRechts.row();
        if(getHeld().getHeldenInventar().getHelmList().size()>0) {
            inventarRechts.add(new Label("Helm", menu.getSkin())).colspan(3);
            inventarRechts.row();
            ArrayList<Helm> listenf = getHeld().getHeldenInventar().getHelmList();
            size = listenf.size();
            for (int i = 0; i < size; i++) {
                if ((i) % 5 == 0) {
                    inventarRechts.row();
                }
                Image beispiel4 = new Image(listenf.get(i).getGrafik());
                beispiel4.addListener(new InventarListener(this, listenf.get(i), beispiel4));
                inventarRechts.add(beispiel4).size(width / 10f, width / 10f);
            }
        }
        inventarRechts.row();
        if(getHeld().getHeldenInventar().getAmulettList().size()>0) {
            inventarRechts.add(new Label("Amulett", menu.getSkin())).colspan(3);
            inventarRechts.row();
            ArrayList<Amulett> listenf = getHeld().getHeldenInventar().getAmulettList();
            size = listenf.size();
            for (int i = 0; i < size; i++) {
                if ((i) % 5 == 0) {
                    inventarRechts.row();
                }
                Image beispiel4 = new Image(listenf.get(i).getGrafik());
                beispiel4.addListener(new InventarListener(this, listenf.get(i), beispiel4));
                inventarRechts.add(beispiel4).size(width / 10f, width / 10f);
            }
        }
        inventarRechts.row();

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
