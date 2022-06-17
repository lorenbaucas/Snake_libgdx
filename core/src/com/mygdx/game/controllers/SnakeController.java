package com.mygdx.game.controllers;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.controllers.Direction;
import com.mygdx.game.actor.Snake;

public class SnakeController implements GestureDetector.GestureListener {

    private Snake snake;

    public SnakeController(Snake snake) {
        this.snake = snake;
    }

    //Con esto hacemos avanzar a la serpiente haciendo que por ejemplo si la serpiente actualmente va a una velocidad
    //que no sea 0 por el eje X osea horizontalmente por ejemplo en el punto 0.0 osea el borde inferior izquierdo
    //y se mueve al punto 10.0 entonces avanza horizontalmente y si la cabeza no va al contrario osea de 10.0 a 0.0
    //entonces su direccion es la derecha entiendes? Me explico fatal pero yo me entiendo. Bueno esto ocurre con las 4
    //posibles direcciones arriba abajo(eje y) derecha o izquierda(eje x) cualquier duda me preguntas
    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if(Math.abs(velocityX)>Math.abs(velocityY)){
            if(velocityX > 0 && snake.getSnakeHead().getDirection() != Direction.LEFT){
                snake.setDirection(Direction.RIGHT);
            }else if (velocityX < 0 && snake.getSnakeHead().getDirection() != Direction.RIGHT){
                snake.setDirection(Direction.LEFT);
            }
        }else{
            if(velocityY > 0 && snake.getSnakeHead().getDirection() != Direction.UP){
                snake.setDirection(Direction.DOWN);
            } else if (velocityY < 0 && snake.getSnakeHead().getDirection() != Direction.DOWN) {
                snake.setDirection(Direction.UP);
            }
        }
        return true;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {return false; }

    @Override
    public void pinchStop() {}
}
