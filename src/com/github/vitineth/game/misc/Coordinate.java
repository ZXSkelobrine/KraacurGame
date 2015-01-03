package com.github.vitineth.game.misc;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class Coordinate {
    
    private int x, y;

    public Coordinate(){
        this(0, 0);
    }

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getScaledX(int scale) {
        return x * scale;
    }

    public int getScaledY(int scale) {
        return y * scale;
    }

}
