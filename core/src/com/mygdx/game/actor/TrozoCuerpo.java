package com.mygdx.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class TrozoCuerpo extends Sprite {

    private int x_coord;
    private int y_coord;
    private TextureRegion texture;
    private TiledMap map;
    private Random randomGenerator;

    //Este numero es el tamaño que ocupara en el mapa respecto a su tamaño, si se aumenta el valor pues aumentan las manzanas posibles
    //y se adapta a la multitud de coordenadas que se genera, por ejemplo si ponemos 1000 entonces el mapa se adapta de manera que le puedan
    //llegar a caber 1000 manzanas, pero 16 es el numero mas cercano que he encontrado al valor maximo que se le pueda dar para que
    //concuerde con las dimensiones de la imagen y de colision para la serpiente, si ponemos 1 la imagen no cambia pero la serpiente no llega
    //a comersela porque claro ocupa 1 y la serpiente debe chocar con ella y si no tiene las dimensiones bien definidas no llega a comersela
    public static final int SIZE = 16;
    public static final int POSICIONES_POSIBLE_MANZANA = 23;
    public static final int SNAKE_MOVEMENT = 16;


    //El trozo de cuerpo se crea en X coordenadas, con la imagen del cuerpo y se genera en el mapa osea el mundo
    public TrozoCuerpo(TextureAtlas atlas, String pathToTexture, TiledMap map, float x1, float y1, float width, float height) {
        super(atlas.findRegion(pathToTexture));
        this.map = map;
        this.texture = new TextureRegion(getTexture(), x1, y1, width, height);
        this.x_coord = 0;
        this.y_coord = 0;
        this.randomGenerator = new Random();
        //Aqui se indican los limites definidos por los puntos por donde la serpiente se pueda mover, es decir se puede mover por todo el mapa
        //por lo tanto cada uno de los trozos es capaz de moverse a traves de todas las coordenadas en el mundo
        setBounds(x_coord, y_coord, SNAKE_MOVEMENT, SNAKE_MOVEMENT);
        setRegion(atlas.findRegion(pathToTexture));
    }

    public TrozoCuerpo(float x, float y) {
        setBounds(x, y, SNAKE_MOVEMENT, SNAKE_MOVEMENT);
    }

    public void setX_coord(int x_coord) {
        this.x_coord = x_coord;
    }

    public void setY_coord(int y_coord) {
        this.y_coord = y_coord;
    }


    //Como la manzana se convierte en un trozo de cuerpo este metodo hace que se genere una manzana en una coordenada aleatoria en el mapa
    public Random getRandomGenerator() {
        return randomGenerator;
    }
}
