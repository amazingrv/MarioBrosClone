package com.mygdx.mariobrosclone.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mariobrosclone.MarioBrosClone;

public class WonScreen implements Screen {

	Viewport viewport;
	Stage stage;
	MarioBrosClone game;

	public WonScreen(MarioBrosClone game) {

		this.game = game;
		viewport = new FitViewport(MarioBrosClone.V_WIDTH, MarioBrosClone.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, game.batch);

		Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

		Table table = new Table();
		table.center();
		table.setFillParent(true);

		Label gameOverLabel = new Label("YOU WIN!!", font);
		Label playAgainLabel = new Label("Press Enter to Play Again", font);

		table.add(gameOverLabel).expandX();
		table.row();
		table.add(playAgainLabel).expandX().padTop(10);

		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}

	void update(float dt) {
		if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			game.setScreen(new PlayScreen(game));
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
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
		stage.dispose();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
}
