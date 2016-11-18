package com.mygdx.anima;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.anima.screens.Playscreen;

public class AnimaRPG extends Game {
	// Verhätltnis Pixel per Meter, da Box2D pro 100 Pixel einen Meter rechnet und das ein bisschen viel ist
	public static final float PPM = 100;
	public static final int W_WIDTH =320;
	public static final int W_Height = 220;

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

	public SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		setScreen(new Playscreen(this));
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
}