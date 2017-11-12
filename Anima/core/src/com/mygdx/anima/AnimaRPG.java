package com.mygdx.anima;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.google.gson.Gson;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.screens.StartScreen;
import com.mygdx.anima.sprites.character.Held;

import javax.naming.Context;
import javax.xml.soap.Text;

import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static com.mygdx.anima.AnimaRPG.ENEMY_SEARCH_HEALER;
import static java.security.AccessController.getContext;

public class AnimaRPG extends Game {
	// Verhätltnis Pixel per Meter, da Box2D pro 100 Pixel einen Meter rechnet und das ein bisschen viel ist
	//public static final float  MAP_SCALE=0.5625;
	public static final float PPM = 100;
	//public static final int W_WIDTH =320;
	//public static final int W_Height = 180;

	//public static final float PPM = 100;
	public static final int W_WIDTH =360;
	public static final int W_Height = 200;

	//B2D Collision Bits
	//Es wurden anfangs quadratische Bit genommen, damit die Summe nie das gleiche sein kann (Duplicate Label in WorldContactListener)
	// Man muss ein bisschen rumprobieren, weil sich manche Summen überschneiden.
	// im World Contact Listener sind mal ^ | und & eingebaut.
	// Aktueller Stand: 2^X und dann auch mit Negativem Vorzeichen
	public static final short NOTHING_BIT=6619;
	public static final short BARRIERE_BIT=6737;
	public static final short HERO_BIT =6863;
	public static final short ENEMY_BIT=6977;
	public static final short HERO_WEAPON_BIT=7109;
	public static final short NPC_BIT=7237;
	public static final short OBJECT_BIT=7523;
	public static final short HERO_SENSOR=128;
	public static final short ENEMY_SENSOR=256;
	public static final short ENEMY_ATTACK=512;
	public static final short ENEMY_ARROW =1024;
	public static final short HERO_ARROW =2048;
	public static final short HERO_CAST_BIT=4096;
	public static final short ITEM_SPRITE_BIT=8192;
	public static final short GEBIETSWECHSEL_BIT=109;
	public static final short ENEMY_HEAL_SENSOR=191;
	public static final short ENEMY_CAST_HEAL=269;
	public static final short ENEMY_SEARCH_HEALER=353;
	public static final short ENEMY_OBERKOERPER=617;
	public static final short HERO_OBERKOERPER=709;
	public static final short EVENT_AREA_BIT=1201;

	public static final short UNGEHEUER_BIT=811;
	public static final short FLYING_UNGEHEUER_BIT=907;
	public static final short UNGEHEUER_SENSOR_BIT=1009;
	public static final short UNGEHEUER_ATTACK_BIT=1093;


	public static SpriteBatch batch;
	public static Screen currentScreen, previousScreen, currentPlayScreen;
	private static Held held;
	private AssetManager assetManager;

	@Override
	public void create() {
		// AUDIOS
		assetManager = new AssetManager();
		assetManager.load("audio/music/little town - orchestral.ogg", Music.class);
		assetManager.load("audio/sounds/anziehen.wav", Sound.class);
		assetManager.load("audio/sounds/ausziehen.wav", Sound.class);
		assetManager.load("audio/sounds/reiter_wechsel.ogg", Sound.class);
		assetManager.load("audio/sounds/walk.ogg", Sound.class);
		assetManager.load("audio/sounds/laufen.mp3", Sound.class);
		assetManager.load("audio/sounds/sword_swing.mp3", Sound.class);
		assetManager.load("audio/sounds/bow_attack.mp3", Sound.class);
		assetManager.load("audio/sounds/nova.wav", Sound.class);
		assetManager.load("audio/sounds/levelUp.wav", Sound.class);
		assetManager.load("audio/sounds/itemFund.wav", Sound.class);
		assetManager.load("audio/sounds/electricity.wav", Sound.class);
		assetManager.load("audio/sounds/turn_page.wav", Sound.class);
		assetManager.finishLoading();

		batch = new SpriteBatch();
		currentScreen=new StartScreen(this);
		setScreen(currentScreen);
	}

	public void changeScreen(Screen screen){
		previousScreen=currentScreen;
		currentScreen=screen;
		setScreen(currentScreen);
	}
	public void closeScreen(){
		currentScreen=previousScreen;
		setScreen(currentScreen);
	}
	@Override
	public void dispose() {
		batch.dispose();
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	public static Held getHeld() {
		return held;
	}

	public static void setHeld(Held held) {
		AnimaRPG.held = held;
	}

	public AssetManager getAssetManager() {
		return this.assetManager;

	}
}