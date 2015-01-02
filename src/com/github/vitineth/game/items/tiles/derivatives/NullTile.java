package com.github.vitineth.game.items.tiles.derivatives;

import com.github.vitineth.game.graphics.Renderer;
import com.github.vitineth.game.graphics.images.Sprite;
import com.github.vitineth.game.items.tiles.Tile;
import com.github.vitineth.game.stores.SpriteStore;

import java.awt.*;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class NullTile extends Tile {

    public NullTile(){
        super(16, 16, 0, 0, SpriteStore.nullT);
    }

}
