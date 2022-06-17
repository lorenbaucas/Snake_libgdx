package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.GameStart;

public class SnakeGame extends Game {
    public SpriteBatch batch;

    @Override
    public void create () {
        this.batch = new SpriteBatch();
        setScreen(new GameStart(this));
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose () {
        super.dispose();
        batch.dispose();
    }
}