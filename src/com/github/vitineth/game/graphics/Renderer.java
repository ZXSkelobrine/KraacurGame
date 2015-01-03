package com.github.vitineth.game.graphics;

import com.github.vitineth.game.Game;
import com.github.vitineth.game.graphics.images.Sprite;
import com.github.vitineth.game.items.creatures.Entity;
import com.github.vitineth.game.items.creatures.players.Player;
import com.github.vitineth.game.items.tiles.Tile;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class Renderer {

    public int[] pixels;
    public int xOffset, yOffset;
    int xtime = 0, ytime = 0;
    int counter = 0;
    private int width, height;

    public Renderer(int width, int height) {
        this.width = width;
        this.height = height;

        pixels = new int[width * height];
    }

    public void clear() {
        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public void render() {
        counter++;
        if(counter % 10 == 0) xtime++;
        if(counter % 80 == 0) ytime++;

        for(int y = 0; y < height; y++) {
            if(ytime >= height) break;
            for(int x = 0; x < width; x++) {
                if(xtime >= width) break;
                pixels[xtime + ytime * width] = 0xFF00FF;
            }
        }
    }

    public void update(Player player){
        if (player.getX() >= ((Game.getScreenWidth() - 10) + xOffset)) {
            xOffset+=2;
        }
        if (player.getY() >= ((Game.getScreenHeight() - 10) + yOffset)) {
            yOffset+=2;
        }
        if (player.getX() <= (xOffset + 40)) {
            xOffset-=2;
        }
        if (player.getY() <= (yOffset + 40)) {
            yOffset-=2;
        }
    }

    public void setOffset(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public int[] getOffset(){
        return new int[]{xOffset, yOffset};
    }

    public void drawTile(Tile tile, int offsetX, int offsetY){
        for(int x = 0; x < tile.getWidth(); x++){
            int xOff = x + offsetX;
            if(xOff < 0 || xOff >= width)break;
            for(int y = 0; y < tile.getHeight(); y++){
                int yOff = y + offsetY;
                if(yOff < 0 || yOff >= height)break;
                pixels[x+y*width] = tile.getSprite().getPixels()[x+y*tile.getWidth()];
            }
        }
    }

    public void renderTile(int xp, int yp, Tile tile) {
        xp -= xOffset;
        yp -= yOffset;
        for(int y = 0; y < tile.getSprite().getSize(); y++) {
            int ya = y + yp;
            for(int x = 0; x < tile.getSprite().getSize(); x++) {
                int xa = x + xp;
                if(xa < 0  || xa >= width || ya < 0 || ya >= height) continue;
                int colour = tile.getSprite().getPixels()[x + y * tile.getSprite().getSize()];
                if(colour != 0xFFFF00FF)pixels[xa + ya * width] = colour;
            }
        }
    }

    public void renderSprite(int xloc, int yloc, Sprite sprite){
        xloc -= xOffset;
        yloc -= yOffset;
        for(int y = 0; y < sprite.getSize(); y++) {
            int ya = y + yloc;
            for(int x = 0; x < sprite.getSize(); x++) {
                int xa = x + xloc;
                if(xa < 0  || xa >= width || ya < 0 || ya >= height) continue;
                int colour = sprite.getPixels()[x + y * sprite.getSize()];
                if(colour != 0xFFFF00FF)pixels[xa + ya * width] = colour;
            }
        }
    }

    public void renderEntity(int xloc, int yloc, Entity entity) {
        xloc -= xOffset;
        yloc -= yOffset;
        for (int y = 0; y < entity.getSprite().getSize(); y++) {
            int ya = y + yloc;
            for (int x = 0; x < entity.getSprite().getSize(); x++) {
                int xa = x + xloc;
                if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
                int colour = entity.getSprite().getPixels()[x + y * entity.getSprite().getSize()];
                if(colour != 0xFFFF00FF)pixels[xa + ya * width] = colour;
            }
        }
    }

}
