package com.github.vitineth.game.items.creatures;

import com.github.vitineth.game.graphics.Renderer;
import com.github.vitineth.game.graphics.images.Sprite;
import com.github.vitineth.game.levels.Level;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class Entity {


    protected int x;
    protected int y;
    protected int size;
    protected Sprite sprite;
    protected boolean isHostile;
    protected boolean isSolid;
    protected boolean isFollowing;


    public int getSize() {
        return size;
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

    public boolean isFollowing() {
        return isFollowing;
    }

    public boolean isHostile() {
        return isHostile;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setFollowing(boolean isFollowing) {
        this.isFollowing = isFollowing;
    }

    public void setHostile(boolean isHostile) {
        this.isHostile = isHostile;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSolid(boolean isSolid) {
        this.isSolid = isSolid;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void render(int x, int y, Renderer renderer){
        renderer.renderEntity(x, y, this);
    }

    public void move(int xSpeed, int ySpeed, Level currentLevel){
        int nx = x+xSpeed;
        int ny = y+ySpeed;
        int ntX = nx / 16;
        int ntY = ny / 16;
        if (tileCollision(x, y, xSpeed, ySpeed, 16, currentLevel)) {

        }else{
            x=nx;
            y=ny;
        }
    }


    //Thanks to TheCherno for this method.
    public boolean tileCollision(double x, double y, double xa, double ya, int size, Level currentLevel) {
        boolean solid = false;
        for (int c = 0; c < 4; c++) {
            int xt = (((int) x + (int) xa) + c % 2 * size / 2 - 20) / 16;
            int yt = (((int) y + (int) ya) + c / 2 * (size + 2) / 2 - 20) / 16;
            if (xt >= currentLevel.getMap().getMapTileWidth() || xt < 0 || yt >= currentLevel.getMap().getMapTileHeight() || yt < 0) {
                solid = true;
                break;
            }
            if (currentLevel.getMap().getTiles()[xt][yt].isSolid()) {
                solid = true;
            }
        }
        return solid;
    }
}
