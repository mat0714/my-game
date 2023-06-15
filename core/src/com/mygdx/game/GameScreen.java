package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.GameObjects.Missile;
import com.mygdx.game.GameObjects.MainCharacter;
import com.mygdx.game.GameObjects.Platform;

public class GameScreen implements Screen {

    private final MyGdxGame game;
    private final Timer missilesTimer;
    private final Timer difficultyTimer;
    private final PlatformCounter platformCounter;

    private final OrthographicCamera camera;
    private final MainCharacter character;
    private final Texture platformTexture;
    private final Array<Platform> platforms;
    private final Texture missileTexture;
    private final Array<Missile> missiles;

    private final int numberOfPlatforms = 200;
    private final int distanceBetweenPlatforms = 230;
    private float initialBulletsCreationInterval = 0.45f;
    private final float difficultyIncreasingInterval = 20f;
    private final float gravity = -18;


    public GameScreen(final MyGdxGame game) {
        this.game = game;
        platformCounter = new PlatformCounter();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        character = new MainCharacter();
        character.x = Gdx.graphics.getWidth() / 2f;
        character.y = 20;


        platformTexture = new Texture(Gdx.files.internal("platform.jpg"));
        platforms = new Array<>();

        for (int i = 1; i <= numberOfPlatforms; i++) {
            Platform platform = new Platform();
            platform.x = platform.getRandomX();
            platform.y = i * distanceBetweenPlatforms;
            platforms.add(platform);
        }

        missileTexture = new Texture(Gdx.files.internal("missile.png"));
        missiles = new Array<>();

        missilesTimer = new Timer();
        missilesTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                spawnBullets();
            }
        },0, initialBulletsCreationInterval);

        difficultyTimer = new Timer();
        difficultyTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                initialBulletsCreationInterval += 5;
            }
        }, 0, difficultyIncreasingInterval);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 0.8f, 1);
        camera.update();
        camera.position.set(Gdx.graphics.getWidth() / 2f, character.y + 180, 0);
        game.batch.setProjectionMatrix(camera.combined);

        character.y += character.jumpVelocity * Gdx.graphics.getDeltaTime();
        character.jumpVelocity += gravity;
        int score = platformCounter.count(character.y, distanceBetweenPlatforms);

        if(character.y <= 20) {
            character.jumpVelocity = 0;
        }

        for(Platform platform : platforms) {
            if (isCharacterOnPlatform(platform)) {
                character.jumpVelocity = 0;
            }
        }

        for(Missile missile : missiles) {
            missile.y -= 200 * Gdx.graphics.getDeltaTime();

            if(missile.overlaps(character)) {
                missile.explosionSound.play();
                game.setScreen(new EndGameScreen(game, score));
                dispose();
            }
        }

        game.batch.begin();
        game.font.draw(game.batch, "Score: " + score, camera.position.x + 250, camera.position.y + 350);
        game.batch.draw(character.textureRegion, character.x, character.y, character.width, character.height);

        for(Platform platform : platforms) {
            game.batch.draw(platformTexture, platform.x, platform.y, platform.width, platform.height);
        }

        for(Missile missile : missiles) {
            game.batch.draw(missileTexture, missile.x, missile.y, missile.width, missile.height);
        }
        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            character.x -= 200 * Gdx.graphics.getDeltaTime();
            character.textureRegion.setRegion(0, 0, 1000, 1000);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            character.x += 200 * Gdx.graphics.getDeltaTime();
            character.textureRegion.setRegion(1000, 0, 1000, 1000);
        }

        if(character.x < 0) {
            character.x = 0;
        }
        if(character.x > Gdx.graphics.getWidth() - character.width) {
            character.x = Gdx.graphics.getWidth() - character.width;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            character.jump(Gdx.graphics.getDeltaTime());
        }

        if(character.jumpVelocity == 0) {
            character.canJump = true;
        }
    }

    private void spawnBullets() {
        Missile missile = new Missile();
        missile.x = missile.getRandomX();
        missile.y = camera.position.y + 1000;
        missiles.add(missile);
    }

    private boolean isCharacterOnPlatform(Platform platform) {
        return character.jumpVelocity <= 0 && character.overlaps(platform) && !(character.y < platform.y);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        character.texture.dispose();
        character.jumpSound.dispose();
        platformTexture.dispose();
        missileTexture.dispose();
    }
}
