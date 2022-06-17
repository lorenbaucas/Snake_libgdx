package com.mygdx.game.controllers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.controllers.Direction;
import com.mygdx.game.actor.Snake;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.actor.TrozoCuerpo;

//Este es el controlador del comportamiento del cuerpo de la serpiente
public class CuerpoController extends TrozoCuerpo {
    private Direction direction;
    private Direction nextDirection;

    //Estos dos metodos cada uno sirve para que dependiendo de por donde la cabeza se coma la manzana el trozo de cuerpo se añada al cuerpo de una direccion
    //u otra para que se acople bien a la serpiente y se puedan mover y girar al unisono
    public CuerpoController(float x, float y, TextureAtlas atlas, Direction direction, Direction nextDirection, TiledMap map, float x1, float y1, float width, float height) {
        super(atlas, Snake.BODY_TEXTURE_PATH, map, x1, y1, width, height);
        this.direction = direction;
        this.nextDirection = nextDirection;
        setBounds(x,y, SNAKE_MOVEMENT, SNAKE_MOVEMENT);
    }

    public CuerpoController(float x, float y, TextureAtlas atlas, String pathToTexture, TiledMap map, float x1, float y1, float width, float height) {
        super(atlas, pathToTexture, map, x1, y1, width, height);
        this.direction = Direction.RIGHT;
        this.nextDirection = Direction.RIGHT;
        setBounds(x,y, SNAKE_MOVEMENT, SNAKE_MOVEMENT);
    }

    //Bueno estos metodos hablan por si solos
    public Direction getDirection() {
        return direction;
    }

    public void setNextDirection(Direction nextDirection) {
        this.nextDirection = nextDirection;
    }

    public void actualizarPosicion() {
        //Con esto me aseguro que la serpiente no pueda girar 180 grados ni que si por ejemplo esta yendo direccion abajo pues no pueda ir hacia
        //arriba de golpe sino que solo se le permite girar 90 grados a los lados y luego se repite el proceso, se puede girar otra vez 90 grados
        //y de esta manera ya va hacia arriba
        switch (nextDirection) {
            case UP:
                if(!direction.equals(Direction.UP) && direction.equals(Direction.RIGHT)) {
                    rotate90(false);
                }
                else if (!direction.equals(Direction.UP) && direction.equals(Direction.LEFT)) {
                    rotate90(true);
                }
                setPosition(getX(), getY() + SNAKE_MOVEMENT);

                if(getY() >= GameScreen.WORLD_HEIGHT) {
                    setPosition(getX(), 0);
                }
                break;
            case DOWN:
                if(!direction.equals(Direction.DOWN) && direction.equals(Direction.RIGHT)) {
                    rotate90(true);
                }
                else if (!direction.equals(Direction.DOWN) && direction.equals(Direction.LEFT)) {
                    rotate90(false);
                }
                setPosition(getX(), getY() - SNAKE_MOVEMENT);

                if(getY() < 0) {
                    setPosition(getX(), GameScreen.WORLD_HEIGHT - SNAKE_MOVEMENT);
                }
                break;

            case LEFT:
                if(!direction.equals(Direction.LEFT) && direction.equals(Direction.DOWN)) {
                    rotate90(true);
                }
                else if (!direction.equals(Direction.LEFT) && direction.equals(Direction.UP)) {
                    rotate90(false);
                }
                setPosition(getX() - SNAKE_MOVEMENT, getY());

                if(getX() < 0) {
                    setPosition(GameScreen.WORLD_WIDTH - SNAKE_MOVEMENT, getY());
                }
                break;

            case RIGHT:
                if(!direction.equals(Direction.RIGHT) && direction.equals(Direction.DOWN)) {
                    rotate90(false);
                }
                else if (!direction.equals(Direction.RIGHT) && direction.equals(Direction.UP)) {
                    rotate90(true);
                }
                setPosition(getX() + SNAKE_MOVEMENT, getY());

                if(getX() >= GameScreen.WORLD_WIDTH) {
                    setPosition(0, getY());
                }
                break;
        }
        direction = nextDirection;
    }

    public boolean checkPosition(int x, int y) {
        return x == getX() && y == getY();
    }

    public void draw(Batch batch){
        super.draw(batch);
    }
}
