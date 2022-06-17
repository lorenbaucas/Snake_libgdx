package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SnakeGame;

//Esta es la pantalla inicial del juego donde le das para empezar nueva partida
public class GameStart implements Screen {

    private Viewport viewport;
    private Stage stage;
    public static Skin skin;

    private SnakeGame game;

    public GameStart(SnakeGame game) {
        this.game = game;
        //Volvemos a poner un par de labels y los posicionamos en el centro al igual que en los labels de puntuacion
        viewport = new StretchViewport(GameScreen.WORLD_WIDTH, GameScreen.WORLD_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        personalizar();

        //Titulo del juego que personalizamos con otro color etc
        Label snakeLabel = new Label("Snake", skin);

        //Metemos un boton que al pulsarlo comience el juego y cargue el mapa del mundo
        TextButton newGameButton = new TextButton("Nueva partida", skin);
        newGameButton.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               dispose();
               game.setScreen(new GameScreen(game, "Mundo.tmx"));;
           }
        });

        //Metemos tanto el label del titulo como el boton y se redimensiona
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        table.add(snakeLabel).height(GameScreen.WORLD_HEIGHT/16).expandX();
        table.row();
        //Ponemos que este 20 de distancia de la parte superior y asi queda mejor
        table.add(newGameButton).height(GameScreen.WORLD_HEIGHT/5).expandX().padTop(20);
        table.row();

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        //Si le das para atras estando en esta pantalla pues te sales del juego
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            dispose();
            System.exit(0);
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
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
    }

    private void personalizar() {
        //Personalizamos tanto el boton como el label del titulo poniendole de fuente la predeterminada por libgdx (arial 15 si mal no recuerdo)
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        //El fondo trasero y tal lo ponemos blanco entero
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));

        //El fondo del boton en naranjita y le decimos que la letra sea de la fuente de libgdx
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.ORANGE);
        textButtonStyle.down = skin.newDrawable("background", Color.ORANGE);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        //El label del titulo tambien en naranja mismo
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        labelStyle.fontColor = Color.ORANGE;
        skin.add("default", labelStyle);
    }
}
