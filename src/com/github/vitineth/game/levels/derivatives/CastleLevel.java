package com.github.vitineth.game.levels.derivatives;

import com.github.vitineth.game.levels.Level;
import com.github.vitineth.game.misc.Coordinate;
import com.github.vitineth.game.misc.utils.ImageLoader;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class CastleLevel extends Level {

    public CastleLevel(){
        super("Kuraac Castle", ImageLoader.loadRelativeImage("/textures/maps/castle/map.png"), ImageLoader.loadRelativeImage("/textures/maps/castle/entity_overlay.png"), new Coordinate(34, 34));
    }

}
