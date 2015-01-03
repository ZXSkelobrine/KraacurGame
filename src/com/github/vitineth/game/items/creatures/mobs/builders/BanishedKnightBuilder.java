package com.github.vitineth.game.items.creatures.mobs.builders;

import com.github.vitineth.game.graphics.images.Sprite;
import com.github.vitineth.game.items.creatures.mobs.BanishedKnight;

/**
 * Created by Ryan on 03/01/2015.
 *
 * @author Ryan
 * @since 03/01/2015
 */
public class BanishedKnightBuilder {

    public static BanishedKnightBuilder create() {
        BanishedKnightBuilder builder = new BanishedKnightBuilder();
        builder.knight = new BanishedKnight();
        return builder;
    }

    private BanishedKnight knight;

    public BanishedKnightBuilder setX(int x) {
        knight.setX(x);
        return this;
    }

    public BanishedKnightBuilder setY(int y) {
        knight.setY(y);
        return this;
    }

    public BanishedKnightBuilder setFollowing(boolean following) {
        knight.setFollowing(following);
        return this;
    }

    public BanishedKnightBuilder setHostile(boolean hostile) {
        knight.setHostile(hostile);
        return this;
    }

    public BanishedKnightBuilder setSprite(Sprite sprite) {
        knight.setSprite(sprite);
        return this;
    }

    public BanishedKnight finalise() {
        return knight;
    }


}
