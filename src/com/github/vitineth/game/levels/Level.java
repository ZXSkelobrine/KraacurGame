package com.github.vitineth.game.levels;

import com.github.vitineth.game.graphics.Renderer;
import com.github.vitineth.game.items.creatures.Entity;
import com.github.vitineth.game.items.maps.Map;
import com.github.vitineth.game.items.maps.MapLoader;
import com.github.vitineth.game.misc.Coordinate;
import com.github.vitineth.game.misc.utils.EntityLoader;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class Level {

    private String name;
    private BufferedImage map;
    private BufferedImage entityOverlay;
    private Map levelMap;
    private Coordinate spawnLocation;

    private List<Entity> entities;

    public Level(String name, BufferedImage map, BufferedImage entityOverlay, Coordinate spawnLocation){
        this.name = name;
        this.map = map;
        this.entityOverlay = entityOverlay;
        this.spawnLocation = spawnLocation;
        loadLevel();
    }

    public Level(String name, BufferedImage map, BufferedImage entityOverlay){
        this.name = name;
        this.map = map;
        this.entityOverlay = entityOverlay;
        this.spawnLocation = new Coordinate();
        loadLevel();
    }

    public void loadLevel(){
        levelMap = MapLoader.loadMap(map);
        entities = EntityLoader.loadEntities(entityOverlay);
    }

    public void renderMap(Renderer renderer){
        levelMap.renderMap(renderer);
    }

    public Map getMap(){
        return levelMap;
    }


}