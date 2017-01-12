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
import com.mygdx.anima.sprites.character.zauber.ZauberEntity;
import com.mygdx.anima.tools.listener.ZauberListener;
import com.mygdx.anima.tools.listener.benutzenButtonListener;
import com.mygdx.anima.tools.listener.zauberslotButtonListener;

import java.util.ArrayList;

import static com.badlogic.gdx.utils.Scaling.fill;
import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 12.12.2016.
 */

public class ZauberReiter extends Group {
    private float scrollbarposition;
    TextButton zauberslot1Button,zauberslot2Button,zauberslot3Button,zauberslot4Button;
    public ScrollPane pane;
    public Table inventarLinks,inventarRechts;
    private float width, height,invLinksWidth,invRechtsWidth,reiterWidth,zeilenhoehe;
    public ZauberEntity auswahlZauber;
    public Menu menu;
    private String eigenschaft, aktuell="",verbesserung=" ";

    public ZauberReiter(Menu menu){
        this.menu=menu;
        this.width=menu.getWidth();
        this.height=menu.getHeight();
        auswahlZauber = null;
        scrollbarposition=0f;
        reiterWidth=width*2/10;
        invLinksWidth=width*3/10;
        invRechtsWidth=width*5/10;
        zeilenhoehe=height/20f;
        auswahlAnzeige();
        this.addActor(inventarLinks);
        pane = zeigeZauber();
        this.addActor(pane);
    }
    public void auswahlAnzeige(){
        inventarLinks = new Table(menu.getSkin());
        inventarLinks.setWidth(invLinksWidth);
        inventarLinks.setPosition(reiterWidth, height);
        inventarLinks.align(Align.left | Align.top);
        Table slotTable=new Table();
        Label zauberslot = new Label("Zauberslots:", menu.getSkin());
        slotTable.setWidth(invLinksWidth);
        slotTable.setPosition(reiterWidth, height);
        slotTable.add(zauberslot).size(invLinksWidth/4,invLinksWidth/4).colspan(4).size(invLinksWidth, height/16);
        slotTable.row();
        if(getHeld().getZauberList().getZauberslot1()==null && getHeld().getZauberList().getZauberslot2()==null && getHeld().getZauberList().getZauberslot3()==null && getHeld().getZauberList().getZauberslot4()==null)
        {
            slotTable.add(new Label("", menu.getSkin())).size(invLinksWidth/4,invLinksWidth/4).colspan(2); slotTable.row();
        }else{
        if(getHeld().getZauberList().getZauberslot1()!=null){Image img1=new Image(getHeld().getZauberList().getZauberslot1().getSlotGrafik());slotTable.add(img1).size(invLinksWidth/4,invLinksWidth/4);}
        if(getHeld().getZauberList().getZauberslot2()!=null){Image img2=new Image(getHeld().getZauberList().getZauberslot2().getSlotGrafik());slotTable.add(img2).size(invLinksWidth/4,invLinksWidth/4);}
        if(getHeld().getZauberList().getZauberslot3()!=null){Image img3=new Image(getHeld().getZauberList().getZauberslot3().getSlotGrafik());slotTable.add(img3).size(invLinksWidth/4,invLinksWidth/4);}
        if(getHeld().getZauberList().getZauberslot4()!=null){Image img4=new Image(getHeld().getZauberList().getZauberslot4().getSlotGrafik());slotTable.add(img4).size(invLinksWidth/4,invLinksWidth/4);}
        }
        inventarLinks.add(slotTable).colspan(2);
        inventarLinks.row();

        if(auswahlZauber !=null) {
            Label label1 = new Label(auswahlZauber.getName(), menu.getSkin());
            Label label2 = new Label(auswahlZauber.getBeschreibung(), menu.getSkin());
            Label label3 = new Label("Wirkungskraft", menu.getSkin());
            Label label3b = new Label(""+auswahlZauber.getEffektivitaet(), menu.getSkin());
            Label label4 = new Label("Manakosten", menu.getSkin());
            Label label4b = new Label(""+auswahlZauber.getManakosten(), menu.getSkin());
            Label label5 = new Label("Zauberzeit", menu.getSkin());
            Label label5b = new Label(""+auswahlZauber.getZauberZeit(), menu.getSkin());
            Label label6 = new Label("Zauberdauer", menu.getSkin());
            Label label6b ;
            if (auswahlZauber.getZauberDauer()!=0){label6b = new Label(""+auswahlZauber.getZauberDauer(), menu.getSkin());}
            else{label6b = new Label("keine", menu.getSkin());}
            Label fil11= new Label("", menu.getSkin());
            Label fil12= new Label("", menu.getSkin());
            Label fil13= new Label("", menu.getSkin());


            //eigenschaft
            inventarLinks.add(label1).colspan(2);
            inventarLinks.row();
            inventarLinks.add(label2).size(invLinksWidth,zeilenhoehe*5).colspan(2);
            inventarLinks.row();
            inventarLinks.add(fil12).size(invLinksWidth*1/4, zeilenhoehe);
            inventarLinks.row();

            inventarLinks.add(label3).size(invLinksWidth*3/4, zeilenhoehe);
            inventarLinks.add(label3b).size(invLinksWidth*1/4, zeilenhoehe);
            inventarLinks.row();
            inventarLinks.add(label4).size(invLinksWidth*3/4, zeilenhoehe);
            inventarLinks.add(label4b).size(invLinksWidth*1/4, zeilenhoehe);
            inventarLinks.row();
            inventarLinks.add(label5).size(invLinksWidth*3/4, zeilenhoehe);
            inventarLinks.add(label5b).size(invLinksWidth*1/4, zeilenhoehe);
            inventarLinks.row();
            inventarLinks.add(label6).size(invLinksWidth*3/4, zeilenhoehe);
            inventarLinks.add(label6b).size(invLinksWidth*1/4, zeilenhoehe);

            inventarLinks.row();inventarLinks.add(fil13).size(invLinksWidth*1/4, zeilenhoehe);
            inventarLinks.row();

            // Table für Buttons erstellen.
            Table buttons=new Table();
            zauberslot1Button = new TextButton("In Feld 1", menu.getSkin());
            zauberslot2Button = new TextButton("In Feld 2", menu.getSkin());
            zauberslot3Button = new TextButton("In Feld 3", menu.getSkin());
            zauberslot4Button = new TextButton("In Feld 4", menu.getSkin());
                zauberslot1Button.addListener(new zauberslotButtonListener(auswahlZauber,this,1));
                zauberslot2Button.addListener(new zauberslotButtonListener(auswahlZauber,this,2));
                zauberslot3Button.addListener(new zauberslotButtonListener(auswahlZauber,this,3));
                zauberslot4Button.addListener(new zauberslotButtonListener(auswahlZauber,this,4));
            buttons.add(zauberslot1Button).size(invLinksWidth/2, height / 12f);
            buttons.add(zauberslot2Button).size(invLinksWidth/2, height / 12f);
            buttons.row();

            buttons.add(zauberslot3Button).size(invLinksWidth/2, height / 12f);
            buttons.add(zauberslot4Button).size(invLinksWidth/2, height / 12f);
            buttons.row();
            inventarLinks.add(buttons).colspan(2);

        }
        this.addActor(inventarLinks);
    }
    public ScrollPane zeigeZauber() {
        inventarRechts = new Table();
        inventarRechts.setWidth(invRechtsWidth);
        inventarRechts.setPosition(invRechtsWidth, height);
        inventarRechts.align(Align.left | Align.top);
        inventarRechts.add(new Label("ZauberFixture", menu.getSkin())).colspan(3);
        inventarRechts.row();

        ArrayList<ZauberEntity> liste = getHeld().getZauberList().getZauberList();
        int size = liste.size();
        for (int i = 0; i < size; i++) {
            if ((i) % 5 == 0) {inventarRechts.row();}
            Image beispiel4 = new Image(liste.get(i).getGrafik());
            beispiel4.addListener(new ZauberListener(this, liste.get(i),beispiel4));
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
