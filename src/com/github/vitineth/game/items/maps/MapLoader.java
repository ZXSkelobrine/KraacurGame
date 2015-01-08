package com.github.vitineth.game.items.maps;

import com.github.vitineth.game.items.tiles.Tile;
import com.github.vitineth.game.items.tiles.derivatives.DirtTile;
import com.github.vitineth.game.items.tiles.derivatives.GrassTile;
import com.github.vitineth.game.stores.SpriteStore;
import com.github.vitineth.game.stores.TileStore;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class MapLoader {

    private static HashMap<Integer, Tile> tileHashMap = new HashMap<Integer, Tile>();

    static {
        tileHashMap.put(0xFFFFFFFF, new GrassTile());
        tileHashMap.put(0xFFFF0000, new DirtTile());

        tileHashMap.put(0xFF00FF00, TileStore.waterTile);
        tileHashMap.put(0xFFFFFF00, TileStore.woodFloorTile);
        tileHashMap.put(0xFF1E7172, TileStore.glassTile(SpriteStore.glassStone));
        tileHashMap.put(0xFF9f5353, TileStore.stoneTile);
        tileHashMap.put(0xFFABCDEF, TileStore.blankTile);
    }

    public static Map loadMap(BufferedImage image){
        Tile[][] map = new Tile[image.getWidth()][image.getHeight()];
        System.out.printf("%-13s   %-9s   %-6s   %-11s   %-6s%n", "A", "B", "C", "D", "Tile");
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int colour = image.getRGB(x, y);
//                System.out.println("A(" + image.getRGB(x, y) + ")\tB(" + 0xFFFF0000 + ")\tC(" + 0xFFFFFFFF + ")\tD(" + 0xFF000000 + ")");
                System.out.printf("%-12s   %-7s   %-6s   %-6s   %6s%n", "A(" + image.getRGB(x, y) + ")", "B(" + 0xFFFF0000 + ")", "C(" + 0xFFFFFFFF + ")", "D(" + 0xFF000000 + ")", (tileHashMap.containsKey(colour) ? tileHashMap.get(colour).getClass().getSimpleName() : TileStore.nullTile.getClass().getSimpleName()));
                if(tileHashMap.containsKey(colour)){
                    map[y][x] = tileHashMap.get(colour);
                }else{
                    map[y][x] = TileStore.nullTile;
                }
            }
            System.out.println();
        }

        Map finalMap = new Map();
        finalMap.setMap(map);
        return finalMap;
    }

}
