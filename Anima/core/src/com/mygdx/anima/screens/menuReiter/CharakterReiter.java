package com.mygdx.anima.screens.menuReiter;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.anima.screens.MenuScreen;
import com.mygdx.anima.sprites.character.Held;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 12.12.2016.
 */

public class CharakterReiter extends Group {
    private float scrollbarposition;
    public ScrollPane pane;
    public Table charLinks, charRechts;
    private float width, height, linksWidth, rechtsWidth,reiterWidth,zeilenHeight,zahlenwertWidth,attributWidth;
    public MenuScreen menu;
    private Held held;
    public CharakterReiter(MenuScreen menu){
        this.menu=menu;
        this.held=getHeld();
        this.width=menu.getWidth();
        this.height=menu.getHeight();
        scrollbarposition=0f;
        reiterWidth=width*2/10;
        linksWidth =width*3/10;
        rechtsWidth =width*5/10;
        zeilenHeight=height/12f;
        zahlenwertWidth=rechtsWidth*1/3;
        attributWidth=rechtsWidth*2/3;
        auswahlAnzeige();
        this.addActor(charLinks);
        pane = zeigeWerte();
        this.addActor(pane);
    }

    public void auswahlAnzeige(){
        charLinks = new Table(menu.getSkin());

        charLinks.setWidth(linksWidth);
        charLinks.setPosition(reiterWidth, height);
        charLinks.align(Align.left | Align.top);
        Label nameLabel= new Label("Name:", menu.getSkin());
        Label nameString= new Label("Alana", menu.getSkin());
        Label spielzeitLabel= new Label("Spielzeit:", menu.getSkin());
        Label spielzeitString= new Label(held.getSpielzeitString(), menu.getSkin());
        Image charBild=new Image (getHeld().getProfilbild());
        charLinks.add(charBild).size(linksWidth,zeilenHeight*8).colspan(2);
        charLinks.row();
        charLinks.add(nameLabel);charLinks.add(nameString);
        charLinks.row();
        charLinks.add(spielzeitLabel);charLinks.add(spielzeitString);
            this.addActor(charLinks);
        }
    public ScrollPane zeigeWerte() {
        charRechts = new Table();
        charRechts.setWidth(rechtsWidth);
        charRechts.setPosition(rechtsWidth, height);
        charRechts.align(Align.left | Align.top);
        charRechts.add(new Label("Charakter-Werte", menu.getSkin())).size(attributWidth,50).colspan(2);
        charRechts.row();
        charRechts.add(new Label("Stufe:",menu.getSkin())).size(attributWidth,zeilenHeight);
        charRechts.add(createAttributLabel(held.getCurrentLevel())).size(zahlenwertWidth,zeilenHeight);
        charRechts.row();
        charRechts.add(new Label("Erfahrung:",menu.getSkin())).size(attributWidth,zeilenHeight);
        charRechts.add(createAttributLabel(held.getCurrentErfahrung(),held.getNextLevelUp())).size(zahlenwertWidth,zeilenHeight);
        charRechts.row();
        charRechts.add(new Label("Lebenspunkte:",menu.getSkin())).size(attributWidth,zeilenHeight);
        charRechts.add(createAttributLabel(held.getCurrentHitpoints(),held.getMaxHitpoints())).size(zahlenwertWidth,zeilenHeight);
        charRechts.row();
        charRechts.add(new Label("Lebenspunkte-Regeneration:",menu.getSkin())).size(attributWidth,zeilenHeight);
        charRechts.add(createAttributLabel(held.getGesamtLPReg())).size(zahlenwertWidth,zeilenHeight);
        charRechts.row();
        charRechts.add(new Label("Manapunkte:",menu.getSkin())).size(attributWidth,zeilenHeight);
        charRechts.add(createAttributLabel(held.getCurrentMana(),held.getMaxMana())).size(zahlenwertWidth,zeilenHeight);
        charRechts.row();
        charRechts.add(new Label("Mana-Regeneration:",menu.getSkin())).size(attributWidth,zeilenHeight);
        charRechts.add(createAttributLabel(held.getGesamtManaReg())).size(zahlenwertWidth,zeilenHeight);
        charRechts.row();
        charRechts.add(new Label("Kraft:",menu.getSkin())).size(attributWidth,zeilenHeight);
        charRechts.add(createAttributLabel(held.getStaerke())).size(zahlenwertWidth,zeilenHeight);
        charRechts.row();
        charRechts.add(new Label("Geschick:",menu.getSkin())).size(attributWidth,zeilenHeight);
        charRechts.add(createAttributLabel(held.getGeschick())).size(zahlenwertWidth,zeilenHeight);
        charRechts.row();
        charRechts.add(new Label("Zauberkraft:",menu.getSkin())).size(attributWidth,zeilenHeight);
        charRechts.add(createAttributLabel(held.getGesamtZauberkraft())).size(zahlenwertWidth,zeilenHeight);
        charRechts.row();
        charRechts.add(new Label("Laufgeschwindigkeit:",menu.getSkin())).size(attributWidth,zeilenHeight);
        charRechts.add(createAttributLabel((int)held.getGeschwindigkeitLaufen())).size(zahlenwertWidth,zeilenHeight);
        charRechts.row();

        charRechts.add(new Label("Kampf-Werte", menu.getSkin())).size(attributWidth,50).colspan(2);
        charRechts.row();
        charRechts.add(new Label("Nahkampfschaden:",menu.getSkin())).size(attributWidth,zeilenHeight);
        charRechts.add(createAttributLabel(held.getSchadenNah())).size(zahlenwertWidth,zeilenHeight);
        charRechts.row();
        charRechts.add(new Label("Fernkampfschaden:",menu.getSkin())).size(attributWidth,zeilenHeight);
        charRechts.add(createAttributLabel(held.getSchadenFern())).size(zahlenwertWidth,zeilenHeight);
        charRechts.row();

        charRechts.add(new Label("Zauberschaden:",menu.getSkin())).size(attributWidth,zeilenHeight);
        charRechts.add(createAttributLabel(held.getSchadenZauber())).size(zahlenwertWidth,zeilenHeight);
        charRechts.row();
        charRechts.add(new Label("Ruestung:",menu.getSkin())).size(attributWidth,zeilenHeight);
        charRechts.add(createAttributLabel(held.getRuestung())).size(zahlenwertWidth,zeilenHeight);
        charRechts.row();
        charRechts.add(new Label("Zauberwiderstand:",menu.getSkin())).size(attributWidth,zeilenHeight);
        charRechts.add(createAttributLabel(held.getGesamtZauberwiderstand())).size(zahlenwertWidth,zeilenHeight);
        charRechts.row();
        charRechts.add(new Label("Angriffsgeschwindigkeit:",menu.getSkin())).size(attributWidth,zeilenHeight);
        charRechts.add(createAttributLabel((int)held.getAngriffgeschwindigkeit())).size(zahlenwertWidth,zeilenHeight);
        charRechts.row();

        ScrollPane scrollPane = new ScrollPane(charRechts, menu.getSkin());
        scrollPane.setBounds(0, 0, width / 2, height);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setPosition(width / 2, 0);
        scrollPane.setFadeScrollBars(true);
        scrollPane.setScrollbarsOnTop(false);
        scrollPane.setupFadeScrollBars(0, 0);
        scrollPane.layout();
        //scrollPane.setScrollPercentY(getScrollbarposition());
        //scrollPane.layout();
        this.addActor(scrollPane);
        pane=scrollPane;
//        menu.zurueckButtonErzeugen();
        return scrollPane;

    }
    public synchronized float getScrollbarposition() {return scrollbarposition;}
    public synchronized void setScrollbarposition(float scrollbarposition) {this.scrollbarposition = scrollbarposition;}

    public Label createAttributLabel(int wert1, int wert2){
        Label lpz=new Label(wert1+" / "+wert2+" ", menu.getSkin());
        lpz.setAlignment(Align.right);
        return lpz;
    }
    public Label createAttributLabel(int wert1){
        Label lpz=new Label(wert1+" ", menu.getSkin());
        lpz.setAlignment(Align.right);
        return lpz;
    }
}
