package com.github.vitineth.game.items.tiles.derivatives;

import com.github.vitineth.game.items.tiles.Tile;
import com.github.vitineth.game.stores.SpriteStore;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class StoneTile extends Tile{

    public StoneTile(){
        super(16, 16, 0, 0, SpriteStore.fullStone);
        setSolid(true);
    }

}
