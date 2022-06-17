package com.mygdx.game.actor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.actor.TrozoCuerpo;
import com.mygdx.game.controllers.CuerpoController;
import com.mygdx.game.controllers.Direction;

//Esta es la serpiente
public class Snake {
    private CuerpoController snakeHead;
    private Array<CuerpoController> snakeBody;
    private TiledMap map;
    private TextureAtlas atlas;

    //Por defecto tiene de longitud 5 trozos de cuerpo
    private static final int INITIAL_SNAKE_LENGTH = 5;

    public static final String HEAD_TEXTURE_PATH = "head";
    public static final String BODY_TEXTURE_PATH = "body";


    //Esta serpiente se crea al inicio de la partida, se le dan las "coordenadas" iniciales y las texturas, osea los sprites
    public Snake(TiledMap map, TextureAtlas atlas) {
        this.map = map;
        this.atlas = atlas;
        //La serpiente empieza con la cabeza posicionada y con su correspondiente sprite
        this.snakeHead = new CuerpoController(INITIAL_SNAKE_LENGTH * CuerpoController.SNAKE_MOVEMENT, CuerpoController.SNAKE_MOVEMENT, atlas, HEAD_TEXTURE_PATH, map, 0, 16, 16, 16);
        //Con esto podremos girar la cabeza
        this.snakeHead.flip(true, false);
        createSnakeBody();
    }

    //La serpiente es la combinación de la cabeza y los trozos de cuerpo
    public Snake(CuerpoController snakeHead, Array<CuerpoController> snakeBody) {
        this.snakeBody = snakeBody;
        this.snakeHead = snakeHead;
    }

    //El cuerpo es la union de los trozos de cuerpo
    private void createSnakeBody() {
        Array<CuerpoController> snakeBody = new Array<>();

        //Al iniciarse el juego con 5 trozos de cuerpo pues genera una cadena de esos 5 trozos y los une creando asi el cuerpo de la serpiente y se van acoplando a la anterior
        //pero el primer trozo de cuerpo no se acopla a la cabeza sino que se posiciona detras de ella al inicio del juego
        for (int i = 1; i < INITIAL_SNAKE_LENGTH; i++) {
            CuerpoController trozocuerpo = new CuerpoController(i * CuerpoController.SNAKE_MOVEMENT, CuerpoController.SNAKE_MOVEMENT, atlas, BODY_TEXTURE_PATH, map, 16, 16, 16, 16);
            snakeBody.add(trozocuerpo);
        }

        this.snakeBody = snakeBody;
    }

    public Array<CuerpoController> getSnakeBody() {
        return snakeBody;
    }

    public CuerpoController getSnakeHead() {
        return snakeHead;
    }

    public void setDirection(Direction direction) {
        snakeHead.setNextDirection(direction);
    }

    //Metodo para que simule el movimiento de la serpiente, es decir el cuerpo formado tanto por los trozos como por la cabeza va tomando nuevas coordenadas en el mapa
    public void actualizarPosicion() {
        snakeHead.actualizarPosicion();
        snakeBody.get(snakeBody.size - 1).actualizarPosicion();
        snakeBody.get(snakeBody.size - 1).setNextDirection(snakeHead.getDirection());

        for (int i = snakeBody.size - 2; i >= 0; i--) {
            snakeBody.get(i).actualizarPosicion();
            snakeBody.get(i).setNextDirection(snakeBody.get(i + 1).getDirection());
        }
    }

    //Metodo que simula como se come la manzana
    public void eat() {
        //Del cuerpo de la serpiente coge la cabeza
        CuerpoController cuerpoAnterior = snakeBody.get(0);
        CuerpoController cuerpo;

        //Dependiendo de la direccion por la que ha comido la manzana entonces el nuevo trozo de cuerpo se añadira de manera que se adapte a la direccion de la serpiente
        switch (cuerpoAnterior.getDirection()) {
            case RIGHT:
                cuerpo = new CuerpoController(cuerpoAnterior.getX() - CuerpoController.SNAKE_MOVEMENT, cuerpoAnterior.getY(), atlas, Direction.RIGHT, cuerpoAnterior.getDirection(), map, 16, 16, 16, 16);
                snakeBody.insert(0, cuerpo);
                break;
            case LEFT:
                cuerpo = new CuerpoController(cuerpoAnterior.getX() + CuerpoController.SNAKE_MOVEMENT, cuerpoAnterior.getY(), atlas,  Direction.LEFT, cuerpoAnterior.getDirection(), map, 16, 16, 16, 16);
                snakeBody.insert(0, cuerpo);
                break;
            case DOWN:
                cuerpo = new CuerpoController(cuerpoAnterior.getX(), cuerpoAnterior.getY() + CuerpoController.SNAKE_MOVEMENT, atlas, Direction.DOWN, cuerpoAnterior.getDirection(), map, 16, 16, 16, 16);
                snakeBody.insert(0, cuerpo);
                break;
            case UP:
                cuerpo = new CuerpoController(cuerpoAnterior.getX(), cuerpoAnterior.getY() - CuerpoController.SNAKE_MOVEMENT, atlas, Direction.UP, cuerpoAnterior.getDirection(), map, 16, 16, 16, 16);
                snakeBody.insert(0, cuerpo);
                break;
        }
    }

    //Si la cabeza choca con el cuerpo se acaba el juego
    public boolean checkSnakeCollision() {
        for (CuerpoController trozoCuerpo : snakeBody) {
            if (checkSnakeHeadCollision(trozoCuerpo)) {
                return true;
            }
        }
        return false;
    }

    //De esta manera si el proximo paso de la cabeza coincide con alguno de los trozos de cuerpo entonces se da por hecho que se come a si misma
    public boolean checkSnakeHeadCollision(Sprite sprite) {
        return (snakeHead.getX() == sprite.getX()) && (snakeHead.getY() == sprite.getY());
    }

    public void draw(SpriteBatch batch) {
        snakeHead.draw(batch);

        for (CuerpoController trozoCuerpo : snakeBody) {
            trozoCuerpo.draw(batch);
        }
    }
}
