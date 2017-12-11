package com.mygdx.anima.sprites.character.enemies;

import com.badlogic.gdx.utils.Pool;
import com.mygdx.anima.sprites.character.enemies.raider.Raider;
import com.mygdx.anima.sprites.character.enemies.raider.RaiderArcher;
import com.mygdx.anima.sprites.character.enemies.raider.RaiderBoss;
import com.mygdx.anima.sprites.character.enemies.raider.RaiderHealer;
import com.mygdx.anima.sprites.character.enemies.ungeheuer.Bat;
import com.mygdx.anima.sprites.character.enemies.ungeheuer.Eyeball;
import com.mygdx.anima.sprites.character.interaktiveObjekte.FriendlyNPC;

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
    private static final Pool<RaiderBoss> raiderBossPool = new Pool<RaiderBoss>() {
        @Override
        protected RaiderBoss newObject() {
            return new RaiderBoss();
        }
    };
    private static final Pool<FriendlyNPC> friendlyNPCPool = new Pool<FriendlyNPC>() {
        @Override
        protected FriendlyNPC newObject() {
            return new FriendlyNPC();
        }
    };
    private static final Pool<RaiderHealer> raiderHealerPool = new Pool<RaiderHealer>() {
        @Override
        protected RaiderHealer newObject() {
            return new RaiderHealer();
        }
    };
    private static final Pool<Bat> batPool = new Pool<Bat>() {
        @Override
        protected Bat newObject() {
            return new Bat();
        }
    };
    private static final Pool<Eyeball> eyeballPool = new Pool<Eyeball>() {
        @Override
        protected Eyeball newObject() {
            return new Eyeball();
        }
    };
    public static EnemyHumanoid createEnemy(MonsterType monsterType){
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
    public static void destroyMonster(EnemyHumanoid enemyHumanoid){
        if(enemyHumanoid instanceof Raider){
            raiderPool.free((Raider) enemyHumanoid);
        }
        else if(enemyHumanoid instanceof RaiderArcher){
            raiderArcherPool.free((RaiderArcher) enemyHumanoid);
        }
        else if(enemyHumanoid instanceof RaiderHealer){
            raiderHealerPool.free((RaiderHealer) enemyHumanoid);
        }
        else if(enemyHumanoid instanceof RaiderBoss){
            raiderBossPool.free((RaiderBoss) enemyHumanoid);
        }
    }
    public static void destroyUngeheuer(EnemyUngeheuer enemyUngeheuer){
        if(enemyUngeheuer instanceof Bat){
            batPool.free((Bat) enemyUngeheuer);
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
    public static Pool<RaiderBoss> getRaiderBossPool() {
        return raiderBossPool;
    }

    public static Pool<FriendlyNPC> getFriendlyNPCPool(){return friendlyNPCPool;}

    public static Pool<Bat> getBatPool(){return batPool;}
    public static Pool<Eyeball> getEyeballPool(){return eyeballPool;}
}
