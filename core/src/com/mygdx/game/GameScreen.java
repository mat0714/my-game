package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.GameObjects.Bullet;
import com.mygdx.game.GameObjects.MainCharacter;
import com.mygdx.game.GameObjects.Platform;

public class GameScreen implements Screen {

    private final MyGdxGame game;
    private final Timer bulletsTimer;
    private final Timer difficultyTimer;
    private final PlatformCounter platformCounter;

    private final OrthographicCamera camera;
    private final MainCharacter character;
    private final Texture platformTexture;
    private final Array<Platform> platforms;
    private final Texture bulletTexture;
    private final Array<Bullet> bullets;

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

        bulletTexture = new Texture(Gdx.files.internal("bullet.png"));
        bullets = new Array<>();

        bulletsTimer = new Timer();
        bulletsTimer.scheduleTask(new Timer.Task() {
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

        for(Bullet bullet : bullets) {
            bullet.y -= 200 * Gdx.graphics.getDeltaTime();

            if(bullet.overlaps(character)) {
                game.setScreen(new EndGameScreen(game, score));
                dispose();
            }
        }

        game.batch.begin();
        game.font.draw(game.batch, "Platform: " + score, camera.position.x + 250, camera.position.y + 350);
        game.batch.draw(character.textureRegion, character.x, character.y, character.width, character.height);

        for(Platform platform : platforms) {
            game.batch.draw(platformTexture, platform.x, platform.y, platform.width, platform.height);
        }

        for(Bullet bullet : bullets) {
            game.batch.draw(bulletTexture, bullet.x, bullet.y, bullet.width, bullet.height);
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
        Bullet bullet = new Bullet();
        bullet.x = bullet.getRandomX();
        bullet.y = camera.position.y + 1000;
        bullets.add(bullet);
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
        bulletTexture.dispose();
    }
}
