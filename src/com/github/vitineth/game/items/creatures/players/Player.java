package com.github.vitineth.game.items.creatures.players;

import com.github.vitineth.game.graphics.Renderer;
import com.github.vitineth.game.graphics.images.Sprite;
import com.github.vitineth.game.items.creatures.Entity;
import com.github.vitineth.game.misc.Coordinate;
import com.github.vitineth.game.stores.SpriteStore;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class Player extends Entity{

    public Player(Coordinate starting){
        setX(starting.getX());
        setY(starting.getY());
        setSprite(SpriteStore.playerFront);
    }

    @Override
    public void render(int x, int y, Renderer renderer) {
        renderer.renderEntity(getX()-32, getY()-32, this);
    }
}
