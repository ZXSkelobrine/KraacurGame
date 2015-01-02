package com.github.vitineth.game.leap.integration;

import com.leapmotion.leap.*;

/**
 * Created by Ryan on 02/01/2015.
 *
 * @author Ryan
 * @since 02/01/2015
 */
public class LeapListener extends Listener{

    private boolean isActive = false;

    private boolean up, down, left, right;

    public synchronized void start(){
        isActive=true;
    }

    public synchronized void stop(){
        isActive=false;
    }

    @Override
    public void onConnect(Controller controller) {
        super.onConnect(controller);
    }

    private int threshold = 50;
    private Vector center = new Vector(0, 200, -50);

    @Override
    public void onFrame(Controller controller) {
        if (isActive) {
            Frame currentFrame = controller.frame();
            if (currentFrame.hands().count() > 0) {
                Hand firstHand = currentFrame.hands().get(0);
                if (firstHand.fingers().count() > 0) {
                    Finger firstFinger = firstHand.fingers().get(0);
                    Vector current = firstFinger.stabilizedTipPosition();
                    if (current.getX()> center.getX() + threshold) {
                        right = true;
                        System.out.println("right = " + right);
                    }else{
                        right = false;
                    }
                    if (current.getX() < center.getX() - threshold) {
                        left = true;
                        System.out.println("left = " + left);
                    }else{
                        left = false;
                    }
                    if (current.getY() > center.getY() + threshold) {
                        up = true;
                        System.out.println("up = " + up);
                    }else{
                        up = false;
                    }
                    if (current.getY() < center.getY() - threshold) {
                        down = true;
                        System.out.println("down = " + down);
                    }else{
                        down = false;
                    }
                }
            }
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }

}
