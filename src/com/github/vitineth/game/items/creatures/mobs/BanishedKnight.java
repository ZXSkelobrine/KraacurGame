package com.github.vitineth.game.items.creatures.mobs;

import com.github.vitineth.game.graphics.Renderer;
import com.github.vitineth.game.items.creatures.Entity;
import com.github.vitineth.game.stores.SpriteStore;

/**
 * Created by Ryan on 03/01/2015.
 *
 * @author Ryan
 * @since 03/01/2015
 */
public class BanishedKnight extends Entity {

    public BanishedKnight() {
        super.setSolid(true);
        super.setFollowing(false);
        super.setHostile(true);
        super.setSprite(SpriteStore.banishedKnightFront);
    }

    @Override
    public void render(int x, int y, Renderer renderer) {
        renderer.renderEntity(getX(), getY(), this);
    }
}