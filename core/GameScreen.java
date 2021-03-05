package com.myfkd.stopit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import com.badlogic.gdx.math.Rectangle;
import java.util.Iterator;

public class GameScreen implements Screen {

	final Stopee game;
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture tarakanImg;
	Texture tapokImg;
	Sound tarakanSound;
	Sound tarakanSound2;
	Music peremenMusic;
	Rectangle tapok;
	Vector3 touchPos;
	Array<Rectangle> runs;
	long lastRunTime;
	static int tarCatchered;
	static int tarEscaped;

	int timeNano;
	int count;
	int tarakanSpeed;

	public GameScreen (final Stopee gam) {

		timeNano = 800000000;
		count = 0;
		tarakanSpeed = 200;

		this.game = gam;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800,480);
		batch = new SpriteBatch();

		touchPos = new Vector3();

		tarakanImg = new Texture("tarakan_85.png");
		tapokImg = new Texture("newTapok.png");

		tarakanSound = Gdx.audio.newSound(Gdx.files.internal("batc.wav"));
		tarakanSound2 = Gdx.audio.newSound(Gdx.files.internal("gagstch.wav"));
		peremenMusic = Gdx.audio.newMusic(Gdx.files.internal("peremen.mp3"));

		peremenMusic.setLooping(true);
		peremenMusic.play();

		tapok = new Rectangle();
		tapok.x = 800 / 2 - 64 / 2; // center
		tapok.y = 20;
		tapok.width = 64;
		tapok.height = 64; //149?

		runs = new Array<>();
		spawnRun();

	}

	private void spawnRun() {
		Rectangle run = new Rectangle();
		run.x = MathUtils.random(0, 800 - 64);
		run.y = 0;
		run.width = 64;
		run.height = 64;
		runs.add(run);
		lastRunTime = TimeUtils.nanoTime();
	}


	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 1f, .5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.font.draw(game.batch, "Bags Slapped :" + tarCatchered, 10, 450);
		game.font.draw(game.batch, "Escaped :" + tarEscaped, 10, 420);
		game.batch.draw(tapokImg, tapok.x, tapok.y);
		for (Rectangle run: runs) {
			game.batch.draw(tarakanImg, run.x, run.y);
		}
		game.batch.end();

		if(Gdx.input.isTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			tapok.x = (int) (touchPos.x - 64 / 2);
			tapok.y = (int) (touchPos.y - 64 / 2);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) tapok.x -=200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) tapok.x +=200 * Gdx.graphics.getDeltaTime();

		if (tapok.x < 0 ) tapok.x = 0;
		if (tapok.x > 800-64 ) tapok.x = 800 - 64;

		if (tapok.y < 0 ) tapok.y = 0;
		if (tapok.y > 480-149 ) tapok.y = 480 - 150;

		if (TimeUtils.nanoTime() - lastRunTime > timeNano) {
			spawnRun();
			count ++;
			if (count > 20 && timeNano <= 100000000) {
				timeNano = 810000000;
				count = 0;
				tarakanSpeed = 200;
			}

			if(count > 20 && timeNano > 100000000) {
				timeNano -= 100000000;
				count = 0;
				tarakanSpeed += 50;
			}
		}

		Iterator<Rectangle> iter = runs.iterator();
		while(iter.hasNext()) {
			Rectangle run = iter.next();
			run.y += tarakanSpeed * Gdx.graphics.getDeltaTime();
			if (run.y + 64 > 480) {
				tarakanSound2.play();
				iter.remove();
				tarEscaped ++;
				if(tarEscaped==20){
					game.setScreen(new SecondMenuScreen(game));
					dispose();
				}
			}
			if(run.overlaps(tapok)) {
				tarCatchered ++;
				tarakanSound.play();
				iter.remove();

			}
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
	public void dispose () {
		batch.dispose();
		tapokImg.dispose();
		tarakanImg.dispose();
		tarakanSound.dispose();
		peremenMusic.dispose();
	}

	@Override
	public void show() {
		peremenMusic.play();
	}
}
