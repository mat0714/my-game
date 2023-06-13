package com.mygdx.game.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Platform extends Rectangle {

    public Platform() {
        this.width = 300;
        this.height = 40;
    }

    public float getRandomX() {
        return MathUtils.random(0f, (float) Gdx.graphics.getWidth() - getWidth());
    }
}