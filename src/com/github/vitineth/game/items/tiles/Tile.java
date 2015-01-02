package com.github.vitineth.game.items.tiles;

import com.github.vitineth.game.graphics.Renderer;
import com.github.vitineth.game.graphics.images.Sprite;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class Tile {

    private int width, height;
    private int x;
    private int y;
    private Sprite sprite;
    private boolean isSolid;

    public Tile(int w, int h, int x, int y, Sprite s){
        this.width = w;
        this.height = h;
        this.x = x;
        this.y = y;
        this.sprite = s;
    }

    public void render(Renderer renderer, int x, int y){
        renderer.renderTile(x, y, this);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public void setSolid(boolean isSolid) {
        this.isSolid = isSolid;
    }
}
