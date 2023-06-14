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
    public boolean canJump;

    public MainCharacter() {
        this.texture = new Texture(Gdx.files.internal("character.png"));
        this.textureRegion = new TextureRegion(texture, 1000, 1000);
        this.width = 80;
        this.height = 80;
        this.jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump-sound.mp3"));
        this.canJump = true;
    }

    public void jump(float deltaTime) {
        if(canJump) {
            jumpVelocity += 740;
            jumpSound.play();
            canJump = false;
        }
    }
}
