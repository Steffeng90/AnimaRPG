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
	public static final int W_WIDTH = 320;
	public static final int W_Height = 320;

	public SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		setScreen(new Playscreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}

	@Override
	public void render() {
		super.render();
	}
}