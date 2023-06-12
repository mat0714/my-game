package com.mygdx.game.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class MainCharacter extends Rectangle {

    public Texture texture;
    public TextureRegion textureRegion;
    public Sound jumpSound;
    public float jumpVelocity;
    private boolean canJump;

    public MainCharacter() {
        this.texture = new Texture(Gdx.files.internal("character.png"));
        this.textureRegion = new TextureRegion(texture, 1200, 1200);
        this.width = 100;
        this.height = 100;
        this.jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump-sound.mp3"));
        this.canJump = true;
    }

    public void jump() {
        if(canJump) {
            jumpVelocity += 800;
            jumpSound.play();
        }
    }
}
