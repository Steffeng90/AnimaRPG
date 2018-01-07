package com.mygdx.anima.screens.actors;

/**
 * Created by Steffen on 06.01.2018.
 */

public class CreditObject {
    public int id;
    public String inhalt;
    public String[] urheber;

    public CreditObject(int id,String inhalt,String[] urheber){
        this.id=id;
        this.inhalt=inhalt;
        this.urheber=urheber;
    }
    public String getUrheberString(){
        String temp="";
        for(int i=0;i<urheber.length;i++) {
            if((i%2)==0)
            {
                temp+=", "+urheber[i]+"\n";
            }
            else{
                temp+=""+urheber[i];
            }
        }
        return temp;
    }


}
