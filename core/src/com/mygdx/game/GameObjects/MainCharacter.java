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
        texture = new Texture(Gdx.files.internal("character.png"));
        textureRegion = new TextureRegion(texture, 1000, 1000);
        width = 80;
        height = 80;
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump-sound.mp3"));
        canJump = true;
    }

    public void jump(float deltaTime) {
        if(canJump) {
            jumpVelocity += 740;
            jumpSound.play();
            canJump = false;
        }
    }
}
