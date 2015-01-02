package com.github.vitineth.game.graphics.images;

import com.github.vitineth.game.misc.Coordinate;

import java.awt.image.BufferedImage;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class SpriteSheet {

    private int[] pixels;
    private int size;
    private int spriteSize;

    public SpriteSheet(BufferedImage bi, int spriteSize){
        pixels = new int[bi.getWidth() * bi.getHeight()];
        this.spriteSize = spriteSize;
        size = bi.getWidth();
        bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), pixels, 0, bi.getWidth());
    }

    public Sprite getSprite(int ox, int oy, int w){
        int[] spritePixels = new int[w*w];
        for(int y = 0; y < w; y++) {
            for(int x = 0; x < w; x++) {
                spritePixels[x + y * w] = pixels[(x + ox) + (y + oy) * size];
            }
        }
        return new Sprite(spritePixels, w);
    }

    public Coordinate getSpriteCoordinate(int basicX, int basicY){
        return new Coordinate(basicX * spriteSize, basicY * spriteSize);
    }

}
