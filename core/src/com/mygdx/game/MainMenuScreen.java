package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    public final MyGdxGame game;
    public final OrthographicCamera camera;
    private final Texture welcomeText, pressKeyText;

    public MainMenuScreen(final MyGdxGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        welcomeText = new Texture(Gdx.files.internal("welcome-text.png"));
        pressKeyText = new Texture(Gdx.files.internal("press-key-text.png"));

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 0.8f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(welcomeText,
                Gdx.graphics.getWidth() / 2f - welcomeText.getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f - welcomeText.getHeight() / 2f + 40);

        game.batch.draw(pressKeyText, 90, 70);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
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
        welcomeText.dispose();
        pressKeyText.dispose();
    }
}
