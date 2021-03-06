package com.mygdx.anima;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.anima.screens.LoadingScreen;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.screens.StartScreen;
import com.mygdx.anima.sprites.character.Held;
import com.mygdx.anima.tools.AdsController;
import com.mygdx.anima.tools.MyAssetManager;

public class AnimaRPG extends Game {

	// Implementierung von Werbung
	private AdsController adsController;

	public AdsController getAdsController() {
		return adsController;
	}

	public AnimaRPG(AdsController adsControllerPara){
		this.adsController = adsControllerPara;
	}
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
	public static final short NOTHING_BIT=0;
	public static final short BARRIERE_BIT=1;
	public static final short HERO_BIT =2;
	public static final short ENEMY_BIT=4;
	public static final short HERO_WEAPON_BIT=8;
	public static final short ARROW_BIT =16;
	public static final short OBJECT_BIT=32;
	public static final short HERO_SENSOR=64;
	public static final short HERO_CAST_BIT=128;
	public static final short ENEMY_SENSOR=256;
	public static final short ENEMY_ATTACK=512;

	public static final short UNGEHEUER_BIT=1024;
	public static final short UNGEHEUER_SENSOR_BIT=2048;
	public static final short UNGEHEUER_ATTACK_BIT=4096;

	public static final short ENEMY_HEAL_SENSOR=8192;
	public static final short ENEMY_CAST_HEAL=16384;
	public static final short ENEMY_SEARCH_HEALER=17; //wird noch optimiert




	public static SpriteBatch batch;
	public static Screen currentScreen, previousScreen,loadingScreen;
	public Playscreen currentPlayScreen;
	private static Held held;
	private MyAssetManager assetManager;

	@Override
	public void create() {
		//if(adsController.isWifiConnected()) {adsController.showBannerAd();}
        assetManager = new MyAssetManager();
		//assetManager.finishLoading();

		batch = new SpriteBatch();
		previousScreen=new StartScreen(this);
		loadingScreen=new LoadingScreen(this);
		setScreen(loadingScreen);
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
		getAssetManager().dispose();
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

	public MyAssetManager getAssetManager() {
		return this.assetManager;
	}
}
