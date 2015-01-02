package com.github.vitineth.game.misc.utils;

import com.github.vitineth.game.items.creatures.Entity;
import com.github.vitineth.game.items.maps.Map;
import com.github.vitineth.game.items.tiles.Tile;
import com.github.vitineth.game.stores.TileStore;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class EntityLoader {

    private static HashMap<Integer, Entity> tileHashMap = new HashMap<Integer, Entity>();

    static {
        tileHashMap.put(0xFFFFFFFF, new Entity());
        tileHashMap.put(0xFFFF0000, new Entity());
    }

    public static List<Entity> loadEntities(BufferedImage overlay){
        List<Entity> entities = new ArrayList<Entity>();
        System.out.printf("%-13s   %-9s   %-6s   %-11s   %-6s%n", "A", "B", "C", "D", "Tile");
        for (int x = 0; x < overlay.getWidth(); x++) {
            for (int y = 0; y < overlay.getHeight(); y++) {
                int colour = overlay.getRGB(x, y);
                System.out.printf("%-12s   %-7s   %-6s   %-6s   %6s%n", "A(" + overlay.getRGB(x, y) + ")", "B(" + 0xFFFF0000 + ")", "C(" + 0xFFFFFFFF + ")", "D(" + 0xFF000000 + ")", (tileHashMap.containsKey(colour) ? tileHashMap.get(colour).getClass().getSimpleName() : TileStore.nullTile.getClass().getSimpleName()));
                if(tileHashMap.containsKey(colour)){
                    entities.add(tileHashMap.get(colour));
                }
            }
            System.out.println();
        }
/*
        Entity[] entityArray = new Entity[entities.size()];
        entityArray = entities.toArray(entityArray);

        return entityArray;*/
        return entities;
    }

}
