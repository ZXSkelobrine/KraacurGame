package com.github.vitineth.game.items.tiles.derivatives;

import com.github.vitineth.game.graphics.Renderer;
import com.github.vitineth.game.stores.SpriteStore;
import com.github.vitineth.game.items.tiles.Tile;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class DirtTile extends Tile {

    public DirtTile(){
        super(16, 16, 0, 0, SpriteStore.fullDirt);
    }

}
