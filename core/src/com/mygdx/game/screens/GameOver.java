package com.mygdx.game.screens;

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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SnakeGame;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class GameOver implements Screen {

    private Viewport viewport;
    private Stage stage;
    private String mundo;
    private Music music;

    private SnakeGame game;

    //Esta es la pantalla de cuando mueres
    public GameOver(SnakeGame game, String mundo) {
        this.mundo = mundo;
        this.game = game;

        //Le añadimos la musiquita de muerte del mario bros y que nos venga nintento y nos denuncie por derechos de copyright jajaja
        music = Gdx.audio.newMusic(Gdx.files.internal("die.mp3"));
        music.setVolume(0.1f);
        music.play();

        //Aqui generamos la pantalla y le añadimos un par de labels que pongan gameover y pulsa otra vez para empezar nueva partida
        viewport = new StretchViewport(GameScreen.WORLD_WIDTH, GameScreen.WORLD_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER", font);
        Label playAgainLabel = new Label("Pulsa para empezar de nuevo", font);
        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10);

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        //Si pulsa en la pantalla comienza nueva partida
        if(Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game, mundo));
            dispose();
        }
        //Y si le da para atras vuelve al menu inicial
        else if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            game.setScreen(new GameStart(game));
            dispose();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        music.dispose();
    }
}
