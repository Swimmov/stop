package com.myfkd.stopit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class SecondMenuScreen implements Screen {
    final Stopee game;
    OrthographicCamera camera;
    GameScreen gameScreen;

    public SecondMenuScreen(Stopee gam) {
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, .25f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(game.texture, 0, 0);
        game.font.draw(game.batch, "YOUR RESULT: ",70, 270);
        game.font.draw(game.batch, "ESCAPED Total: " + GameScreen.tarEscaped, 70, 200);
        game.font.draw(game.batch, "FAILED TO ESCAPE Total: " + GameScreen.tarCatchered, 70, 130);
        game.font.draw(game.batch, "TOUCH THE SCREEN TO START NEW GAME!", 70, 60);
        game.batch.end();


        if (Gdx.input.justTouched()) {
            GameScreen.tarEscaped = 0;
            GameScreen.tarCatchered = 0;
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


    }
}
