package com.mygdx.game.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.controllers.Direction;
import com.mygdx.game.actor.Snake;
import com.mygdx.game.SnakeGame;
import com.mygdx.game.controllers.SnakeController;
import com.mygdx.game.actor.Manzana;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.util.concurrent.TimeUnit;

//Este es el mundo en general
public class GameScreen implements Screen {

    private SnakeGame game;
    private OrthographicCamera gameCamera;
    private Viewport gameViewport;
    private String mundo;

    private Music music;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private ExtraPuntuacion puntuacion;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private TextureAtlas atlas;

    private Snake snake;
    private Manzana manzana;

    private boolean gameOver = false;

    //Me vas a odiar por esto, ya se que por pixeles esta mal pero es la unica manera de hacerlo con la serpientita que de la otra manera imposible
    //porque necesita cierto tamaño prefijado para que sea exacta y funcione bien por eso genere un mundo vacio con el Tiled de libgdx
    public static final int WORLD_WIDTH = 500;
    public static final int WORLD_HEIGHT = 400;
    //Esto es la velocidad a la que va que luego mas abajo te lo explico
    public static int FPS_SLEEP = 20;
    //Y esta la puntuacion que se añade al marcador, por cada manzana comida aumenta en 10 de puntuacion
    private static final int PUNTUACION = 10;


    public GameScreen(SnakeGame game, String mundo) {
        this.mundo = mundo;
        this.game = game;

        //Le meto la musiquita para que juegues relajado
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();

        gameCamera = new OrthographicCamera();
        gameViewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, gameCamera);

        //Aqui cargo el mapa
        mapLoader = new TmxMapLoader();
        map = mapLoader.load(mundo);
        renderer = new OrthogonalTiledMapRenderer(map);

        //Lo pongo asi para que se vea bien la pantalla
        gameCamera.position.set(gameViewport.getWorldWidth() / 2, gameViewport.getWorldHeight() / 2, 0);

        //Se hace el mundo y se renderiza
        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();

        //Las imagenes del atlas
        atlas = new TextureAtlas("Atlas.atlas");

        //Meto la puntuacion
        puntuacion = new ExtraPuntuacion(game.batch);

        //Meto la serpiente, la manzana, el metodo para meter mas manzanas etc
        snake = new Snake(map, atlas);
        manzana = new Manzana(Manzana.TEXTURE_PATH, map, atlas);
        manzana.generarManzana(manzana, snake, manzana.getRandomGenerator());

        Gdx.input.setInputProcessor(new GestureDetector(new SnakeController(snake)));
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
    }

    private void update() {
        if(!gameOver) {
            //Si sigue viva la serpiente va actualizandose la posicion, comprobando si choca o no y la direccion a la que
            //deslizas el dedo para que la serpiente siga haciendo giros de 90 grados
            dedoDesliza();

            world.step(1 / 60f, 1, 2);

            gameCamera.update();

            checkAllColissions();

            snake.actualizarPosicion();

            checkAllColissions();

            renderer.setView(gameCamera);
        }
    }

    private void checkAllColissions() {
        //Si se choca con la manzana aumenta el tamaño de su cuerpo y la puntuacion y se genera otra manzana
        if(manzana.checkSnakeCollision(manzana, snake)) {
            snake.eat();
            puntuacion.addScore(PUNTUACION);
            manzana.generarManzana(manzana, snake, manzana.getRandomGenerator());
        }

        //Si se choca consigo misma muere
        if (snake.checkSnakeCollision()) {
            gameOver = true;
        }
    }

    //Dependiendo de hacia donde deslices el dedo cambiaras la direccion a la que la serpiente ira la proxima vez que se mueva
    //pero no deja hacer giros de 180 grados osea que si vas para arriba no puedes ir para abajo directamente
    private void dedoDesliza() {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && snake.getSnakeHead().getDirection() != Direction.DOWN) {
                snake.setDirection(Direction.UP);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && snake.getSnakeHead().getDirection() != Direction.UP) {
                snake.setDirection(Direction.DOWN);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && snake.getSnakeHead().getDirection() != Direction.RIGHT) {
                snake.setDirection(Direction.LEFT);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && snake.getSnakeHead().getDirection() != Direction.LEFT) {
                snake.setDirection(Direction.RIGHT);
            }
    }

    //Esto aumenta o disminuye la velocidad de movimiento de la serpiente, realmente no es la serpiente sino el juego en si
    //porque lo va "durmiendo" y cuanto mas aumentas este valor...(lee siguiente comentario)
    private void sleep(int fps) {
        long diff, start = System.currentTimeMillis();
        update();
        if(fps>0){
            diff = System.currentTimeMillis() - start;
            //Cuanto mas aumentas este valor mas lenta ira porque "duerme" mas tiempo, no se si me entiendes
            long targetDelay = 3000/fps;
            if (diff < targetDelay) {
                try{
                    TimeUnit.MILLISECONDS.sleep(targetDelay - diff);
                } catch (InterruptedException e) {
                    System.out.println(e.toString());
                }
            }
        }
    }

    @Override
    public void render(float delta) {

        //Renderiza todos los recursos
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sleep(FPS_SLEEP);

        renderer.render();

        debugRenderer.render(world, gameCamera.combined);

        game.batch.setProjectionMatrix(gameCamera.combined);
        game.batch.begin();
        snake.draw(game.batch);

        manzana.draw(game.batch);

        game.batch.end();

        game.batch.setProjectionMatrix(puntuacion.stage.getCamera().combined);
        puntuacion.stage.draw();

        //Si se mata aparece la pantalla de gameover
        if(gameOver) {
            game.setScreen(new GameOver(game, mundo));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        debugRenderer.dispose();
        puntuacion.dispose();
        music.dispose();
    }
}
