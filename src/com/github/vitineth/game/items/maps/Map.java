package com.github.vitineth.game.items.maps;

import com.github.vitineth.game.graphics.Renderer;
import com.github.vitineth.game.items.tiles.Tile;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class Map {

    private Tile[][] map = new Tile[16][16];

    public void setMap(Tile[][] map) {
        this.map = map;
    }

    public void renderMap(Renderer renderer){
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                map[x][y].render(renderer, x*16, y*16);
            }
        }
    }

    public Tile[][] getTiles(){
        return map;
    }

}
