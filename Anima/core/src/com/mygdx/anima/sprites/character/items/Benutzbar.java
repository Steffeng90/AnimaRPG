package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.math.Vector2;

import static com.mygdx.anima.AnimaRPG.getHeld;

/**
 * Created by Steffen on 01.12.2016.
 */

public class Benutzbar extends Item {
    private String wertKategorie, beschreibung;
    private int wertUpgradeZahl,kategoriezahl;

    public Benutzbar(String id,String name, String itemKategorie, Vector2 grafikposi,int kategorie,int wertZahl, int goldWert) {
        super(id,name, itemKategorie, grafikposi, goldWert);
        switch (kategorie)
        {
            case 1: this.wertKategorie="Lebenspunkte";break;
            case 2: this.wertKategorie="maximalen Lebenspunkte";break;
            case 3: this.wertKategorie="Magiepunkte";break;
            case 4: this.wertKategorie="maximalen Magiepunkte";break;
            case 5: this.wertKategorie="Kraft";break;
            case 6: this.wertKategorie="Geschicklichkeit";break;
            case 7: this.wertKategorie="Zauberkraft";break;
            case 8: this.wertKategorie="Zauberabwehr";break;
                // Fehlen noch: regMana,regHitpoints
        }
        kategoriezahl=kategorie;
        this.wertUpgradeZahl=wertZahl;
        beschreibung="Steigert die "+
                wertKategorie+" beim Benutzen um "+wertUpgradeZahl+".";
    }
    public void benutzen(){
        switch(kategoriezahl) {
            case 1: getHeld().setCurrentHitpoints(getHeld().getCurrentHitpoints() + wertUpgradeZahl);break;
            case 2: getHeld().setMaxHitpoints(getHeld().getMaxHitpoints() + wertUpgradeZahl);break;
            case 3: getHeld().setCurrentMana(getHeld().getCurrentMana() + wertUpgradeZahl);break;
            case 4: getHeld().setMaxMana(getHeld().getMaxMana() + wertUpgradeZahl);break;
            case 5: getHeld().setStaerke(getHeld().getStaerke() + wertUpgradeZahl);break;
            case 6: getHeld().setGeschick(getHeld().getGeschick() + wertUpgradeZahl);break;
            case 7: getHeld().setZauberkraft(getHeld().getZauberkraft() + wertUpgradeZahl);break;
            case 8: getHeld().setZauberwiderstand(getHeld().getZauberwiderstand() + wertUpgradeZahl);break;
        }
        getHeld().getHeldenInventar().getBenutzbarList().removeValue(this,true);
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
