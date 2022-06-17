package com.mygdx.game.controllers;

import com.mygdx.game.actor.Snake;
import com.mygdx.game.actor.TrozoCuerpo;
import com.mygdx.game.controllers.CuerpoController;


import java.util.Random;

public interface ManzanaController {

    //Genero una manzana que si se come luego se convierte en trozo de cuerpo y se une a la serpiente, se genera
    //en una coordenada aleatoria que viene dada por un numero aleatorio
    default void generarManzana(TrozoCuerpo manzana, Snake snake, Random random) {
        int x;
        int y;

        do{
            //La coordenada aleatoria viene dada por las posibles coordenadas existentes y los posibles puntos de generacion de manzanas
            x = (random.nextInt(TrozoCuerpo.POSICIONES_POSIBLE_MANZANA)) * TrozoCuerpo.SIZE;
            y = (random.nextInt(TrozoCuerpo.POSICIONES_POSIBLE_MANZANA)) * TrozoCuerpo.SIZE;
        } while (!checkManzanaPosition(x, y, snake));

        manzana.setX(x);
        manzana.setY(y);

        manzana.setX_coord(x);
        manzana.setY_coord(y);

        //Los limites para la generacion de la manzana son los mismos por donde la serpiente pueda desplazarse, es decir el mapa entero
        manzana.setBounds(x,y, TrozoCuerpo.SNAKE_MOVEMENT, TrozoCuerpo.SNAKE_MOVEMENT);
    }

    //Mientras haya una manzana no se generara una nueva
    default boolean checkManzanaPosition(int x, int y, Snake snake) {
        if(!snake.getSnakeHead().checkPosition(x, y)) {
            for(CuerpoController snakeCell : snake.getSnakeBody()) {
                if(snakeCell.checkPosition(x, y)) {
                    return false;
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    //Comprobamos que no choquen entre si, no se da el caso pero antes si para ir comprobando poco a poco cual es lo ideal para que vayan
    //juntos pero sin llegar a tocarse
    default boolean checkSnakeCollision(TrozoCuerpo trozo, Snake snake) {
        return snake.checkSnakeHeadCollision(trozo);
    }
}
