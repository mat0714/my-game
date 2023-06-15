package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class EndGameScreen implements Screen {

    private final MyGdxGame game;
    private final OrthographicCamera camera;
    private final Texture welcomeText, pressKeyText, characterTexture;
    private final TextureRegion characterTextureRegion;
    private final BitmapFont font;
    private final int score;

    public EndGameScreen(final MyGdxGame game, int score) {
        this.game = game;
        this.score = score;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        welcomeText = new Texture(Gdx.files.internal("try-more.png"));
        pressKeyText = new Texture(Gdx.files.internal("press-key-text.png"));
        characterTexture = new Texture(Gdx.files.internal("character.png"));
        characterTextureRegion = new TextureRegion(characterTexture, 1000, 1000);
        font = new BitmapFont();
        font.setColor(0, 0, 0, 1);
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
        game.batch.draw(characterTextureRegion, 580, 50, 200, 200);
        font.draw(game.batch, "Your score: " + score, 350, 240);
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
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
        font.dispose();
    }
}