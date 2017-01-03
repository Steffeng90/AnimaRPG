package com.mygdx.anima;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.google.gson.Gson;
import com.mygdx.anima.screens.Playscreen;
import com.mygdx.anima.sprites.character.Held;

import javax.xml.soap.Text;

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
	public static final short NOTHING_BIT=0;
	public static final short BARRIERE_BIT=1;
	public static final short HERO_BIT =2;
	public static final short ENEMY_BIT=4;
	public static final short HERO_WEAPON_BIT=16;
	public static final short OBJECT_BIT=32;
	public static final short HERO_SENSOR=64;
	public static final short ENEMY_SENSOR=128;
	public static final short ENEMY_ATTACK=256;
	public static final short ARROW_BIT=512;
	public static final short HERO_CAST_BIT=1024;
	public static final short ITEM_SPRITE_BIT=2048;
	public static final short GEBIETSWECHSEL_BIT=4096;
	public static final short ENEMY_HEAL_SENSOR=8192;
	public static final short ENEMY_CAST_HEAL=16384;


	public static SpriteBatch batch;
	public static Screen currentScreen, previousScreen;
	private static Held held;
	public static AssetManager aManager;
	@Override
	public void create() {
		aManager=new AssetManager();
		Gdx.files.getLocalStoragePath();
		//aManager.load("objekte/itemdb.json);
//		aManager.load("objekte/itemdb.json", Gson.class);
		aManager.finishLoading();

		batch = new SpriteBatch();
		currentScreen=new Playscreen(this);
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
}