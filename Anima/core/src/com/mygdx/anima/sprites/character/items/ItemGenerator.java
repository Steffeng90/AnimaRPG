package com.mygdx.anima.sprites.character.items;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.anima.screens.Playscreen;


/**
 * Created by Steffen on 01.12.2016.
 */

public class ItemGenerator {
    public enum Itemtyp {
        Schwert1, Schwert2, Schwert3, Schwert4, Schwert5, Schwert6, Schwert7, Schwert8,
        ruestung1,ruestung2,ruestung3,ruestung4,ruestung5,ruestung6,ruestung7,
        Bogen1,Bogen2,Bogen3,Bogen4,Bogen5,Bogen6,
        Brot, Pilz, Kaese, Fisch, Schwert, Bogen
    }
    private static String itemName="Default";
    private static Vector2 vector;
    //Kategorie 1: Nahkampf, 2: Fernkampf 3: Armor, 4: Benutzbar
    private static int kategorie,schaden,goldWert,ruestung;
 //  private static kategorie itemKategorie=waffe;

    public static ItemSprite generateItem(Playscreen screen, float x, float y, String typ) {
        Itemtyp itemID = setItemID(typ);
        //return new ItemSprite(screen, x, y, getFrame(itemID), itemName);

        switch (itemID) {
            case Schwert1:
                itemName="Eisenschwert";
                kategorie=1;
                vector=new Vector2(0,6);
                schaden=10;
                goldWert=15;
                break;

            case Schwert2:
                itemName="Stahlschwert";
                kategorie=1;
                vector=new Vector2(1,6);
                schaden=15;
                goldWert=15;
                break;

            case Schwert3:
                itemName="Mithrilschwert";
                kategorie=1;
                vector=new Vector2(2,6);
                schaden=20;
                goldWert=15;
                break;
            case Schwert4:
                itemName="Langschwert";
                kategorie=1;
                vector=new Vector2(3,6);
                schaden=23;
                goldWert=45;
                break;
            case Schwert5:
                itemName="Großschwert";
                kategorie=1;
                vector=new Vector2(4,6);
                schaden=30;
                goldWert=75;
                break;
            case Schwert6:
                itemName="Schwert der Läuterung";
                kategorie=1;
                vector=new Vector2(5,6);
                schaden=45;
                goldWert=135;
                break;
            case Schwert7:
                x = 6;
                y = 6;
                break;
            //Fernkampfewaffen
            case Bogen1:
                itemName="Kurzbogen";
                kategorie=2;
                vector=new Vector2(0,11);
                schaden=45;
                goldWert=135;
                break;
            case Bogen2:
                itemName="Gehärteter Bogen";
                kategorie=2;
                vector=new Vector2(1,11);
                schaden=45;
                goldWert=135;
                break;
            case Bogen3:
                itemName="Langbogen";
                kategorie=2;
                vector=new Vector2(2,11);
                schaden=45;
                goldWert=135;
                break;
            case Bogen4:
                itemName="Kompositbogen";
                kategorie=2;
                vector=new Vector2(3,11);
                schaden=45;
                goldWert=135;
                break;
            case Bogen5:
                itemName="Eschenbogen";
                kategorie=2;
                vector=new Vector2(4,11);
                schaden=45;
                goldWert=135;
                break;
            case Bogen6:
                itemName="Alptraum";
                kategorie=2;
                vector=new Vector2(5,11);
                schaden=45;
                goldWert=135;
                break;
            //Ruestung / Armor
            case ruestung1:
                itemName="Stoffhemd";
                kategorie=3;
                vector=new Vector2(0,13);
                ruestung=10;
                goldWert=15;
                break;
            case ruestung2:
                itemName="Schutzweste";
                kategorie=3;
                vector=new Vector2(1,13);
                ruestung=20;
                goldWert=15;
                break;
            case ruestung3:
                itemName="Lederwarms";
                kategorie=3;
                vector=new Vector2(2,13);
                ruestung=30;
                goldWert=15;
                break;
            case ruestung4:
                itemName="Brustpanzer";
                kategorie=3;
                vector=new Vector2(3,13);
                ruestung=40;
                goldWert=15;
                break;
            case ruestung5:
                itemName="Plattenpanzer";
                kategorie=3;
                vector=new Vector2(4,13);
                ruestung=50;
                goldWert=15;
                break;
            case ruestung6:
                itemName="Warms der Heilung";
                kategorie=3;
                vector=new Vector2(5,13);
                ruestung=60;
                goldWert=15;
                break;
            case ruestung7:
                itemName="Vollstrecker-Gewand";
                kategorie=3;
                vector=new Vector2(6,13);
                ruestung=70;
                goldWert=15;
                break;
            case Brot:
                x = 7;
                y = 1;
                break;
            case Pilz:
                x = 5;
                y = 1;
                break;
            case Kaese:
                x = 9;
                y = 1;
                break;
            case Fisch:
                x = 10;
                y = 1;
                break;
            case Schwert:
                x = 0;
                y = 5;
                break;
            case Bogen:
                x = 0;
                y = 11;
                break;
            default:
                //Default_traube
                x = 3;
                y = 3;
                break;
        }
        switch(kategorie){
            case 1:
                screen.getSpieler().getHeldenInventar().add(new WaffeNah(itemName, "nahkampf", vector,schaden, goldWert));
                break;
            case 2:
                screen.getSpieler().getHeldenInventar().add(new WaffeFern(itemName, "fernkampf", vector,schaden, goldWert));
                break;
            case 3:
                screen.getSpieler().getHeldenInventar().add(new Armor(itemName, "armor", vector,ruestung, goldWert));
                break;
            case 4:
                // screen.getSpieler().getHeldenInventar().add(new Benutzbar(itemName, "benutzbar", vector,schaden, goldWert));
        }

       // Gdx.app.log("WaffeNah definiert","");
        return new ItemSprite(screen, x, y, vector, itemName);
    }
    private static Itemtyp setItemID(String type) {
        return Itemtyp.valueOf(type);

    }

   /* public static Vector2 getFrame(Itemtyp type) {
        int x, y;

        switch(type){
            case Brot:
                region=new TextureRegion(spriteQuelle,7*spriteBreite,1* spriteHoehe,spriteBreite,spriteHoehe);
                break;
            case Pilz:
                region=new TextureRegion(spriteQuelle,5 *spriteBreite,1* spriteHoehe,spriteBreite,spriteHoehe);
                break;
            case Käse:
                region=new TextureRegion(spriteQuelle,9*spriteBreite,1* spriteHoehe,spriteBreite,spriteHoehe);
                break;
            case Fisch:
                region=new TextureRegion(spriteQuelle,10*spriteBreite,1* spriteHoehe,spriteBreite,spriteHoehe);
                break;
            case Schwert:
                region=new TextureRegion(spriteQuelle,0*spriteBreite,5* spriteHoehe,spriteBreite,spriteHoehe);
                break;
            case Bogen:
                region=new TextureRegion(spriteQuelle,0*spriteBreite,11* spriteHoehe,spriteBreite,spriteHoehe);
                break;
            default:
                //Default_traube
                region=new TextureRegion(spriteQuelle,0*spriteBreite,0* spriteHoehe,spriteBreite,spriteHoehe);
                break;
        }

        }
        return new Vector2(x, y);
    }*/
}
