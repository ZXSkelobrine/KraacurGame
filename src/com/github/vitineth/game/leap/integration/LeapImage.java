package com.github.vitineth.game.leap.integration;

import com.github.vitineth.game.misc.utils.ImageLoader;

import java.awt.image.BufferedImage;

/**
 * Created by Ryan on 03/01/2015.
 *
 * @author Ryan
 * @since 03/01/2015
 */
public enum LeapImage {

    LEFT(ImageLoader.loadRelativeImage("/images/LMI/control/left.png")),
    RIGHT(ImageLoader.loadRelativeImage("/images/LMI/control/right.png")),
    UP(ImageLoader.loadRelativeImage("/images/LMI/control/up.png")),
    DOWN(ImageLoader.loadRelativeImage("/images/LMI/control/down.png")),

    LEFT_UP(ImageLoader.loadRelativeImage("/images/LMI/control/left_up.png")),
    RIGHT_UP(ImageLoader.loadRelativeImage("/images/LMI/control/right_up.png")),

    LEFT_DOWN(ImageLoader.loadRelativeImage("/images/LMI/control/left_down.png")),
    RIGHT_DOWN(ImageLoader.loadRelativeImage("/images/LMI/control/right_down.png")),

    CENTER(ImageLoader.loadRelativeImage("/images/LMI/control/center.png"));


    private BufferedImage image;

    private LeapImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getRawImage() {
        return image;
    }

}
