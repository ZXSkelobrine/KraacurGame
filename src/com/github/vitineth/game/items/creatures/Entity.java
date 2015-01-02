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
        if(currentLevel.getMap().getTiles()[ntX][ntY].isSolid()){

        }else{
            x=nx;
            y=ny;
        }

    }
}
