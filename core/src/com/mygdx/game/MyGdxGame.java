package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
    public Music music;

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
        music = Gdx.audio.newMusic(Gdx.files.internal("background-music.mp3"));
        music.setLooping(true);
        music.play();
		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
        music.dispose();
	}

}
