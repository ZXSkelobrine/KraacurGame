package com.github.vitineth.game.levels.derivatives;

import com.github.vitineth.game.items.creatures.players.Player;
import com.github.vitineth.game.levels.Level;
import com.github.vitineth.game.misc.Coordinate;
import com.github.vitineth.game.misc.utils.ImageLoader;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class SpawnLevel extends Level{

    public SpawnLevel(Player player) {
        super("Spawn", ImageLoader.loadRelativeImage("/textures/maps/spawn/map.png"), ImageLoader.loadRelativeImage("/textures/maps/spawn/entity_overlay.png"), new Coordinate(1, 1), player);
    }

}
