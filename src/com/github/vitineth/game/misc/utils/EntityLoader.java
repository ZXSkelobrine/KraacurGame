package com.github.vitineth.game.misc.utils;

import com.github.vitineth.game.items.creatures.Entity;
import com.github.vitineth.game.items.creatures.mobs.builders.BanishedKnightBuilder;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class EntityLoader {


    public static List<Entity> loadEntities(BufferedImage overlay){
        List<Entity> entities = new ArrayList<Entity>();
        System.out.printf("%-13s   %-9s   %-6s   %-11s   %-6s%n", "A", "B", "C", "D", "Tile");
        for (int x = 0; x < overlay.getWidth(); x++) {
            for (int y = 0; y < overlay.getHeight(); y++) {
                int colour = overlay.getRGB(x, y);
                switch (colour) {
                    case 0xFFFFFF00:
                        entities.add(BanishedKnightBuilder.create().setX(x).setY(y).finalise());
                        break;
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
