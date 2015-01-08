package com.github.vitineth.game.levels.derivatives;

import com.github.vitineth.game.graphics.Renderer;
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
public class CastleLevel extends Level {

    private boolean isFirstRender = true;

    public CastleLevel(Player player) {
        super("Kuraac Castle", ImageLoader.loadRelativeImage("/textures/maps/castle/map.png"), ImageLoader.loadRelativeImage("/textures/maps/castle/entity_overlay.png"), ImageLoader.loadRelativeImage("/textures/maps/castle/background.png"), new Coordinate(40, 40), player);
    }

    @Override
    public void renderMap(Renderer renderer) {
        if (isFirstRender) {
            renderer.setOffset((getSpawnLocation().getScaledX(16) / 2) - 60, (getSpawnLocation().getScaledY(16) / 2) + 30);
            isFirstRender = false;
        }
        super.renderMap(renderer);
    }
}
