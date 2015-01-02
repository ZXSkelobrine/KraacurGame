package com.github.vitineth.game.stores;

import com.github.vitineth.game.graphics.images.Sprite;
import com.github.vitineth.game.items.tiles.derivatives.*;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class TileStore {

    public static GrassTile grassTile = new GrassTile();
    public static DirtTile dirtTile = new DirtTile();
    public static NullTile nullTile = new NullTile();

    public static WaterTile waterTile = new WaterTile();
    public static WoodTile woodFloorTile = new WoodTile();
    public static StoneTile stoneTile = new StoneTile();

    public static GlassTile glassTile(Sprite beneath){
        return new GlassTile(beneath);
    }


}
