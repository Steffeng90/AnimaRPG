package com.mygdx.anima.sprites.character.enemies;

import com.badlogic.gdx.utils.Pool;
import com.mygdx.anima.sprites.character.enemies.raider.Raider;
import com.mygdx.anima.sprites.character.enemies.raider.RaiderArcher;
import com.mygdx.anima.sprites.character.enemies.raider.RaiderHealer;

/**
 * Created by Steffen on 22.01.2017.
 */

public class NPCPool {
    public enum MonsterType{
        Raider, RaiderHealer, RaiderArcher};

    private static final Pool<Raider> raiderPool = new Pool<Raider>() {
        @Override
        protected Raider newObject() {
            return new Raider();
        }
    };
    private static final Pool<RaiderArcher> raiderArcherPool = new Pool<RaiderArcher>() {
        @Override
        protected RaiderArcher newObject() {
            return new RaiderArcher();
        }
    };
    private static final Pool<RaiderHealer> raiderHealerPool = new Pool<RaiderHealer>() {
        @Override
        protected RaiderHealer newObject() {
            return new RaiderHealer();
        }
    };
    public static Enemy createEnemy(MonsterType monsterType){
        switch(monsterType){
            default:
            case Raider:
                return raiderPool.obtain();
            case RaiderArcher:
                return raiderArcherPool.obtain();
            case RaiderHealer:
                return raiderHealerPool.obtain();
        }
    }
    //You must call destroyMonster whenever you are done with the monster or the pool will leak.
    public static void destroyMonster(Enemy enemy){
        if(enemy instanceof Raider){
            raiderPool.free((Raider) enemy);
        }
        else if(enemy instanceof RaiderArcher){
            raiderArcherPool.free((RaiderArcher) enemy);
        }
        else if(enemy instanceof RaiderHealer){
            raiderHealerPool.free((RaiderHealer) enemy);
        }
    }

    public static Pool<Raider> getRaiderPool() {
        return raiderPool;
    }

    public static Pool<RaiderArcher> getRaiderArcherPool() {
        return raiderArcherPool;
    }

    public static Pool<RaiderHealer> getRaiderHealerPool() {
        return raiderHealerPool;
    }
}
