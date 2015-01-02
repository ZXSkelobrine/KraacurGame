package com.github.vitineth.game.items.tiles.derivatives;

import com.github.vitineth.game.graphics.Renderer;
import com.github.vitineth.game.graphics.images.Sprite;
import com.github.vitineth.game.items.tiles.Tile;
import com.github.vitineth.game.stores.SpriteStore;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class GlassTile extends Tile {

    public GlassTile(Sprite sprite){
        super(16, 16, 0, 0, sprite);

    }

    @Override
    public void render(Renderer renderer, int x, int y) {
        super.render(renderer, x, y);
    }
}
