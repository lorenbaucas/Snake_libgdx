package com.mygdx.game.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.controllers.ManzanaController;

import java.util.Random;

//Esta es la manzana
public class Manzana extends TrozoCuerpo implements ManzanaController {

    //Aqui indico la ruta de la textura osea de la imagen sprite
    public static final String TEXTURE_PATH = "apple";

    //Se crea la manzana a partir de los datos recogidos, la imagen, el tile map que es el mundo, etc
    public Manzana(String pathToTexture, TiledMap map, TextureAtlas atlas) {
        super(atlas, pathToTexture, map, 0, 0, 0, 0);
    }

    public void draw(Batch batch){
        super.draw(batch);
    }
}
