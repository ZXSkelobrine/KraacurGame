package com.github.vitineth.game.levels;

import com.github.vitineth.game.graphics.Renderer;
import com.github.vitineth.game.items.creatures.Entity;
import com.github.vitineth.game.items.creatures.players.Player;
import com.github.vitineth.game.items.maps.Map;
import com.github.vitineth.game.items.maps.MapLoader;
import com.github.vitineth.game.misc.Coordinate;
import com.github.vitineth.game.misc.utils.EntityLoader;

import java.awt.image.BufferedImage;
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
    private BufferedImage background;
    private Map levelMap;
    private Coordinate spawnLocation;
    private Player player;

    private List<Entity> entities;

    public Level(String name, BufferedImage map, BufferedImage entityOverlay, BufferedImage background, Coordinate spawnLocation, Player player) {
        this.name = name;
        this.map = map;
        this.entityOverlay = entityOverlay;
        this.spawnLocation = spawnLocation;
        this.background = background;
        this.player = player;
        loadLevel();
    }

    public Level(String name, BufferedImage map, BufferedImage entityOverlay, BufferedImage background, Player player) {
        this.name = name;
        this.map = map;
        this.entityOverlay = entityOverlay;
        this.background = background;
        this.spawnLocation = new Coordinate();
        this.player = player;
        loadLevel();
    }

    public void loadLevel(){
        levelMap = MapLoader.loadMap(map);
        entities = EntityLoader.loadEntities(entityOverlay);
        player.setX(spawnLocation.getScaledX(16));
        player.setY(spawnLocation.getScaledY(16));
    }

    public void renderMap(Renderer renderer){
        for (int i = 0; i < entities.size(); i++) {
            Entity current = entities.get(i);
            current.render(0, 0, renderer);
        }
        levelMap.renderMap(renderer);
    }

    public Map getMap(){
        return levelMap;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getEntityOverlay() {
        return entityOverlay;
    }

    public Map getLevelMap() {
        return levelMap;
    }

    public Coordinate getSpawnLocation() {
        return spawnLocation;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public BufferedImage getBackground() {
        return background;
    }
}
