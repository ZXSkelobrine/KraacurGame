package com.github.vitineth.game.graphics.images;

import java.awt.image.BufferedImage;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class Sprite {

    private int[] pixels;
    private int size;

    public Sprite(BufferedImage bi){
        pixels = new int[bi.getWidth() * bi.getHeight()];
        size = bi.getHeight();
        bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), pixels, 0, bi.getWidth());
    }

    public Sprite(int[] pixels, int size){
        this.pixels = pixels;
        this.size = size;
    }

    public Sprite(SpriteSheet sheet, int x, int y, int w){
        this.pixels = sheet.getSprite(x, y, w).pixels;
        this.size = w;
    }

    public Sprite(int colour, int w){
        this.pixels = new int[w * w];
        this.size = w;
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = colour;
        }
    }

    public Sprite(Sprite top, Sprite bottom, int... removeColours){
        this.pixels = new int[(top.getSize() * top.getSize())+1];
        int[] topPixels = top.getPixels();
        int[] bottomPixels = bottom.getPixels();
        for (int i = 0; i < topPixels.length; i++) {
            pixels[i] = topPixels[i];
                if(topPixels[i] == 0xFFFF00FF){
                    pixels[i] = bottomPixels[i];
                    System.out.println("Removed");
                }else{
                    pixels[i] = topPixels[i];
                }
            System.out.println("i = " + i);
            System.out.println("pixels[i] = " + pixels[i]);
            System.out.println();
        }
    }

    public int[] getPixels() {
        return pixels;
    }

    public int getSize() {
        return size;
    }
}
