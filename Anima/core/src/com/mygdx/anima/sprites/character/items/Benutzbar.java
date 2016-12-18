package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.math.Vector2;

import static com.mygdx.anima.AnimaRPG.held;

/**
 * Created by Steffen on 01.12.2016.
 */

public class Benutzbar extends Item {
    private String wertKategorie, beschreibung;
    private int wertUpgradeZahl,kategoriezahl;

    public Benutzbar(String name, String itemKategorie, Vector2 grafikposi,int kategorie,int wertZahl, int goldWert) {
        super(name, itemKategorie, grafikposi, goldWert);
        switch (kategorie)
        {
            case 1: this.wertKategorie="Lebenspunkte";break;
            case 2: this.wertKategorie="maximalen \nLebenspunkte";break;
            case 3: this.wertKategorie="Magiepunkte";break;
            case 4: this.wertKategorie="maximalen \nMagiepunkte";break;
            case 5: this.wertKategorie="Kraft";break;
            case 6: this.wertKategorie="Geschicklichkeit";break;
            case 7: this.wertKategorie="Zauberkraft";break;
            case 8: this.wertKategorie="Zauberabwehr";break;
                // Fehlen noch: regMana,regHitpoints
        }
        kategoriezahl=kategorie;
        this.wertUpgradeZahl=wertZahl;
        beschreibung="Steigert die \n"+
                wertKategorie+" beim \nBenutzen um "+wertUpgradeZahl+".";
    }
    public void benutzen(){
        switch(kategoriezahl) {
            case 1: held.setCurrentHitpoints(held.getCurrentHitpoints() + wertUpgradeZahl);break;
            case 2: held.setMaxHitpoints(held.getMaxHitpoints() + wertUpgradeZahl);break;
            case 3: held.setCurrentMana(held.getCurrentMana() + wertUpgradeZahl);break;
            case 4: held.setMaxMana(held.getMaxMana() + wertUpgradeZahl);break;
            case 5: held.setStaerke(held.getStaerke() + wertUpgradeZahl);break;
            case 6: held.setGeschick(held.getGeschick() + wertUpgradeZahl);break;
            case 7: held.setZauberkraft(held.getZauberkraft() + wertUpgradeZahl);break;
            case 8: held.setZauberwiderstand(held.getZauberwiderstand() + wertUpgradeZahl);break;
        }
        held.getHeldenInventar().getBenutzbarList().remove(this);
    }

    public String getWertKategorie() {
        return wertKategorie;
    }

    public void setWertKategorie(String wertKategorie) {
        this.wertKategorie = wertKategorie;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public int getWertUpgradeZahl() {
        return wertUpgradeZahl;
    }

    public void setWertUpgradeZahl(int wertUpgradeZahl) {
        this.wertUpgradeZahl = wertUpgradeZahl;
    }

    public int getKategoriezahl() {
        return kategoriezahl;
    }

    public void setKategoriezahl(int kategoriezahl) {
        this.kategoriezahl = kategoriezahl;
    }
}
