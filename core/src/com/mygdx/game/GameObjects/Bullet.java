package com.mygdx.game.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Bullet extends Rectangle {

    public Texture texture;

    public Bullet() {
        this.texture = new Texture(Gdx.files.internal("bullet.png"));
        this.width = 25;
        this.height = 79;
    }

    public float getRandomX() {
        return MathUtils.random(0f, (float) (Gdx.graphics.getWidth() - getWidth()));
    }
}

