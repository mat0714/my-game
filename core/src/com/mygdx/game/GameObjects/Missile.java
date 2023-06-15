package com.mygdx.game.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Missile extends Rectangle {

    public Texture texture;
    public final Sound explosionSound;

    public Missile() {
        this.width = 30;
        this.height = 85;
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion-sound.mp3"));
    }

    public float getRandomX() {
        return MathUtils.random(0f, (float) (Gdx.graphics.getWidth() - getWidth()));
    }
}

