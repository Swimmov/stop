package com.myfkd.stopit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Stopee extends Game {
    SpriteBatch batch;
    Texture texture;
    BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        texture = new Texture(Gdx.files.internal("main_fon.png"));
        this.setScreen(new MainMenuScreen(this));
    }
    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();

    }
}
