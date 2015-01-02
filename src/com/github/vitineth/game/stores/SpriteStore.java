package com.github.vitineth.game.stores;

import com.github.vitineth.game.graphics.images.Sprite;
import com.github.vitineth.game.graphics.images.SpriteSheet;
import com.github.vitineth.game.misc.Coordinate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class SpriteStore {

    static {
        try {
            String path = "/textures/sheets/tiles/spritesheet.png";
            main = new SpriteSheet(ImageIO.read(SpriteStore.class.getResource(path)), 16);

            path = "/textures/sheets/tiles/fullsheet.png";
            full = new SpriteSheet(ImageIO.read(SpriteStore.class.getResource(path)), 16);

            path = "/textures/sheets/entities/players/player1.png";
            playerSheet = new SpriteSheet(ImageIO.read(SpriteStore.class.getResource(path)), 32);

            loadFullSprites();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SpriteSheet main;
    public static SpriteSheet full;
    public static SpriteSheet playerSheet;

    public static Sprite grass = new Sprite(main, 0, 0, 16);
    public static Sprite dirt = new Sprite(main, 1<<4, 0, 16);
    public static Sprite nullT = new Sprite(0xFF8f0d8d, 16);//main, 2<<4, 0, 16

    public static Sprite fullGrass;
    public static Sprite fullDirt;
    public static Sprite fullWater;
    public static Sprite fullWood;
    public static Sprite fullGlass;
    public static Sprite fullStone;

    public static Sprite glassStone;

    public static Sprite playerFront;

    private static void loadFullSprites(){
        Coordinate spriteSheet;

        spriteSheet = full.getSpriteCoordinate(3, 4);
        fullGrass = new Sprite(full, spriteSheet.getX(), spriteSheet.getY(), 16);

        spriteSheet = full.getSpriteCoordinate(5, 6);
        fullDirt = new Sprite(full, spriteSheet.getX(), spriteSheet.getY(), 16);

        spriteSheet = full.getSpriteCoordinate(6, 7);
        fullWater = new Sprite(full, spriteSheet.getX(), spriteSheet.getY(), 16);

        spriteSheet = full.getSpriteCoordinate(0, 2);
        fullWood = new Sprite(full, spriteSheet.getX(), spriteSheet.getY(), 16);

        spriteSheet = full.getSpriteCoordinate(1, 5);
        fullGlass = new Sprite(full, spriteSheet.getX(), spriteSheet.getY(), 16);

        spriteSheet = full.getSpriteCoordinate(4, 6);
        fullStone = new Sprite(full, spriteSheet.getX(), spriteSheet.getY(), 16);

        spriteSheet = full.getSpriteCoordinate(7, 0);
        glassStone = new Sprite(full, spriteSheet.getX(), spriteSheet.getY(), 16);


        spriteSheet = playerSheet.getSpriteCoordinate(0, 1);
        playerFront = new Sprite(playerSheet, spriteSheet.getX(), spriteSheet.getY(), 32);
    }

}
