package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameObjects.MainCharacter;
import com.mygdx.game.GameObjects.Platform;

public class GameScreen implements Screen {

    final MyGdxGame game;

    OrthographicCamera camera;
    MainCharacter character;
    Platform platform;
    Array<Platform> platforms;
    float gravity = -20;

    public GameScreen(final MyGdxGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        character = new MainCharacter();
        character.x = Gdx.graphics.getWidth() / 2f;
        character.y = 20;

        platform = new Platform();
        platforms = new Array<>();

        for (int i = 1; i < 20; i++) {
            Platform platform = new Platform();
            platform.x = platform.getRandomX();
            platform.y = i * 200;
            platforms.add(platform);
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 0.8f, 1);
        camera.update();
        camera.position.set(character.x + character.width / 2, character.y + 180, 0);

        game.batch.setProjectionMatrix(camera.combined);

        character.y += character.jumpVelocity * Gdx.graphics.getDeltaTime();
        character.jumpVelocity += gravity;

        if(character.y < 20) {
            character.jumpVelocity = 0;
        }

        for(Platform platform : platforms) {
            if (isCharacterOnPlatform(platform)) {
                character.jumpVelocity = 0;
            }
        }

        game.batch.begin();
        game.font.draw(game.batch, "Awesome game", 10, Gdx.graphics.getHeight() - 20);
        game.batch.draw(character.textureRegion, character.x, character.y, character.width, character.height);

        for(Platform platform : platforms) {
            game.batch.draw(platform.texture, platform.x, platform.y, platform.width, platform.height);
        }

        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            character.x -= 200 * Gdx.graphics.getDeltaTime();
            character.textureRegion.setRegion(0, 0, 1200, 1200);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            character.x += 200 * Gdx.graphics.getDeltaTime();
            character.textureRegion.setRegion(1200, 0, 1200, 1200);
        }

        if(character.x < 0) {
            character.x = 0;
        }
        if(character.x > Gdx.graphics.getWidth() - character.width) {
            character.x = Gdx.graphics.getWidth() - character.width;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            character.jump();
        }
    }

    private boolean isCharacterOnPlatform(Platform platform) {
        if(character.jumpVelocity <= 0 && character.overlaps(platform) && !(character.y < platform.y)) {
            return true;
        }
        return false;
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
        platform.texture.dispose();
    }
}
