package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

//Esto es la pantallita que sale de Puntuacion
public class ExtraPuntuacion implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private static int puntuacion;

    private static Label scoreLabel;

    public ExtraPuntuacion(SpriteBatch batch) {
        //Ponemos la puntuacion a 0 inicialmente
        puntuacion = 0;
        //Le decimos que la queremos en el centro de la pantalla
        viewport = new FitViewport(GameScreen.WORLD_WIDTH, GameScreen.WORLD_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        //Añadimos dos partes, primero el label donde pone Puntuacion y luego el de la puntuacion en si osea el numero
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //Le ponemos el estilo y como se vera al principio sin haber comido una manzana aun
        scoreLabel = new Label("Puntuacion: 0", new Label.LabelStyle(new BitmapFont(), Color.WHITE ));

        //Le damos de espacio 10 de distancia con la parte de arriba osea el eje x de manera horizontal
        table.add(scoreLabel).expandX().padTop(10);

        stage.addActor(table);
    }

    //Añadimos la puntuacion consiguida a la que teniamos anteriormente
    public static void addScore(int value) {
        puntuacion += value;
        scoreLabel.setText("Puntuación: " + puntuacion);
    }

    @Override
    public void dispose() {
        puntuacion = 0;
        stage.dispose();
    }
}
