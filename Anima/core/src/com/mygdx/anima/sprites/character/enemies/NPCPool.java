package com.mygdx.anima.sprites.character.enemies;

import com.badlogic.gdx.utils.Pool;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.enemies.raider.Raider;
import com.mygdx.anima.sprites.character.enemies.raider.RaiderArcher;
import com.mygdx.anima.sprites.character.enemies.raider.RaiderBoss;
import com.mygdx.anima.sprites.character.enemies.raider.RaiderHealer;
import com.mygdx.anima.sprites.character.enemies.ungeheuer.AngryBee;
import com.mygdx.anima.sprites.character.enemies.ungeheuer.Bat;
import com.mygdx.anima.sprites.character.enemies.ungeheuer.Eyeball;
import com.mygdx.anima.sprites.character.enemies.ungeheuer.Ghost;
import com.mygdx.anima.sprites.character.enemies.ungeheuer.Plant;
import com.mygdx.anima.sprites.character.enemies.ungeheuer.Pumpkin;
import com.mygdx.anima.sprites.character.enemies.ungeheuer.Slime;
import com.mygdx.anima.sprites.character.enemies.ungeheuer.Snake;
import com.mygdx.anima.sprites.character.enemies.ungeheuer.WormBig;
import com.mygdx.anima.sprites.character.enemies.ungeheuer.WormSmall;
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
    private static final Pool<Ghost> ghostPool = new Pool<Ghost>() {
        @Override
        protected Ghost newObject() {
            return new Ghost();
        }
    };
    private static final Pool<Pumpkin> pumpkinPool = new Pool<Pumpkin>() {
        @Override
        protected Pumpkin newObject() {
            return new Pumpkin();
        }
    };
    private static final Pool<Plant> plantPool = new Pool<Plant>() {
        @Override
        protected Plant newObject() {
            return new Plant();
        }
    };
    private static final Pool<AngryBee> angryBeePool = new Pool<AngryBee>() {
        @Override
        protected AngryBee newObject() {
            return new AngryBee();
        }
    };
    private static final Pool<Snake> snakePool = new Pool<Snake>() {
        @Override
        protected Snake newObject() {
            return new Snake();
        }
    };
    private static final Pool<Slime> slimePool = new Pool<Slime>() {
        @Override
        protected Slime newObject() {
            return new Slime();
        }
    };
    private static final Pool<WormBig> wormBigPool = new Pool<WormBig>() {
        @Override
        protected WormBig newObject() {
            return new WormBig();
        }
    };
    private static final Pool<WormSmall> wormSmallPool = new Pool<WormSmall>() {
        @Override
        protected WormSmall newObject() {
            return new WormSmall();
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

    public static Pool<Ghost> getGhostPool() {
        return ghostPool;
    }

    public static Pool<Pumpkin> getPumpkinPool() {
        return pumpkinPool;
    }

    public static Pool<Plant> getPlantPool() {
        return plantPool;
    }

    public static Pool<AngryBee> getAngryBeePool() {
        return angryBeePool;
    }

    public static Pool<Snake> getSnakePool() {
        return snakePool;
    }

    public static Pool<Slime> getSlimePool() {
        return slimePool;
    }

    public static Pool<WormBig> getWormBigPool() {
        return wormBigPool;
    }

    public static Pool<WormSmall> getWormSmallPool() {
        return wormSmallPool;
    }
    public static void aktiveNPCsEntfernen(Playscreen screen){
        int size;
        size= screen.activeRaider.size;
        Raider raider;
        if(size>0){ for (int i = size; --i >= 0;) {
            raider = screen.activeRaider.get(i);
            screen.activeRaider.removeIndex(i);
            getRaiderPool().free(raider);
        }
        }
        size= screen.activeBat.size;
        Bat bat;
        if(size>0){ for (int i = size; --i >= 0;) {
            bat = screen.activeBat.get(i);
            screen.activeBat.removeIndex(i);
            getBatPool().free(bat);
        }
        }
        size= screen.activeEyeball.size;
        Eyeball eyeball;
        if(size>0){ for (int i = size; --i >= 0;) {
            eyeball = screen.activeEyeball.get(i);
            screen.activeEyeball.removeIndex(i);
            getEyeballPool().free(eyeball);
        }
        }
        size= screen.activeGhost.size;
        Ghost ghost;
        if(size>0){ for (int i = size; --i >= 0;) {
            ghost = screen.activeGhost.get(i);
            screen.activeGhost.removeIndex(i);
            getGhostPool().free(ghost);
        }
        }
        size= screen.activePumpkin.size;
        Pumpkin pumpkin;
        if(size>0){ for (int i = size; --i >= 0;) {
            pumpkin = screen.activePumpkin.get(i);
            screen.activePumpkin.removeIndex(i);
            getPumpkinPool().free(pumpkin);
        }
        }
        size= screen.activeSlime.size;
        Slime slime;
        if(size>0){ for (int i = size; --i >= 0;) {
            slime = screen.activeSlime.get(i);
            screen.activeSlime.removeIndex(i);
            getSlimePool().free(slime);
        }
        }
        size= screen.activeAngryBee.size;
        AngryBee angryBee;
        if(size>0){ for (int i = size; --i >= 0;) {
            angryBee= screen.activeAngryBee.get(i);
            screen.activeAngryBee.removeIndex(i);
            getAngryBeePool().free(angryBee);
        }
        }
        size= screen.activeSnake.size;
        Snake snake;
        if(size>0){ for (int i = size; --i >= 0;) {
            snake= screen.activeSnake.get(i);
            screen.activeSnake.removeIndex(i);
            getSnakePool().free(snake);
        }
        }
        size= screen.activePlant.size;
        Plant plant;
        if(size>0){ for (int i = size; --i >= 0;) {
            plant= screen.activePlant.get(i);
            screen.activePlant.removeIndex(i);
            getPlantPool().free(plant);
        }
        }
        size= screen.activeWormBig.size;
        WormBig wormBig;
        if(size>0){ for (int i = size; --i >= 0;) {
            wormBig= screen.activeWormBig.get(i);
            screen.activeWormBig.removeIndex(i);
            getWormBigPool().free(wormBig);
        }
        }
        size= screen.activeWormSmall.size;
        WormSmall wormSmall;
        if(size>0){ for (int i = size; --i >= 0;) {
            wormSmall= screen.activeWormSmall.get(i);
            screen.activeWormSmall.removeIndex(i);
            getWormSmallPool().free(wormSmall);
        }
        }
        size= screen.activeNPC.size;
        FriendlyNPC npc;
        if(size>0){ for (int i = size; --i >= 0;) {
            npc = screen.activeNPC.get(i);
            screen.activeNPC.removeIndex(i);
            getFriendlyNPCPool().free(npc);
        }
        }
        size= screen.activeRaiderArcher.size;
        RaiderArcher raiderArcher;
        if(size>0){ for (int i = size; --i >= 0;) {
            raiderArcher = screen.activeRaiderArcher.get(i);
            screen.activeRaiderArcher.removeIndex(i);
            NPCPool.getRaiderArcherPool().free(raiderArcher);
        }
        }
        size= screen.activeRaiderHealer.size;
        RaiderHealer raiderHealer;
        if(size>0){ for (int i = size; --i >= 0;) {
            raiderHealer = screen.activeRaiderHealer.get(i);
            screen.activeRaiderHealer.removeIndex(i);
            NPCPool.getRaiderHealerPool().free(raiderHealer);
        }
        }
        size= screen.activeRaiderBoss.size;
        RaiderBoss raiderBoss;
        if(size>0){ for (int i = size; --i >= 0;) {
            raiderBoss = screen.activeRaiderBoss.get(i);
            screen.activeRaiderBoss.removeIndex(i);
            NPCPool.getRaiderBossPool().free(raiderBoss);
        }
        }
    }
}
