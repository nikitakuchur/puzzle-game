package com.puzzlegame.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.puzzlegame.ui.GameUI;
import com.puzzlegame.game.Level;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;

public class GameScreen extends ScreenAdapter {

    private Stage stage = new Stage(new ScreenViewport());

    private Level level = new Level();
    private GameUI gameUI = new GameUI(this);

    /**
     * Creates a new game screen
     */
    public GameScreen() {
        stage.getCamera().position.set(Vector3.Zero);
        level.fillScreen(true);
        stage.addActor(level);
        stage.addActor(gameUI);
    }

    /**
     * @return the level
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Sets the level
     *
     * @param level the level
     */
    public void setLevel(Level level) {
        int index = stage.getActors().indexOf(this.level, true);
        stage.getActors().set(index, level);
        level.fillScreen(true);
        this.level.dispose();
        this.level = level;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        stage.dispose();
        level.dispose();
        gameUI.dispose();
    }
}