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
    private int threshold = 50;
    private Vector center = new Vector(0, 200, -50);

    public synchronized void start() {
        isActive = true;
    }

    public synchronized void stop() {
        isActive = false;
    }

    @Override
    public void onConnect(Controller controller) {
        super.onConnect(controller);
        System.out.println("Controller connected...");
    }

    @Override
    public void onDisconnect(Controller controller) {
        super.onDisconnect(controller);
        System.out.println("Device disconnected...");
    }

    @Override
    public void onDeviceChange(Controller controller) {
        super.onDeviceChange(controller);
        System.out.println("Device changed...");
    }

    @Override
    public void onExit(Controller controller) {
        super.onExit(controller);
        System.out.println("Exited...");
    }

    @Override
    public void onFocusGained(Controller controller) {
        super.onFocusGained(controller);
        System.out.println("Focus gained...");
    }

    @Override
    public void onFocusLost(Controller controller) {
        super.onFocusLost(controller);
        System.out.println("Focus lost...");
    }

    @Override
    public void onImages(Controller controller) {
        super.onImages(controller);
        System.out.println("Images...");
    }

    @Override
    public void onInit(Controller controller) {
        super.onInit(controller);
        System.out.println("Initialised...");
    }

    @Override
    public void onServiceConnect(Controller controller) {
        super.onServiceConnect(controller);
        System.out.println("Service connected...");
    }

    @Override
    public void onServiceDisconnect(Controller controller) {
        super.onServiceDisconnect(controller);
        System.out.println("Service disconnected...");
    }

    @Override
    public void onFrame(Controller controller) {
//        System.out.println("New frame (" + controller.frame().id() + ")...");
        if (isActive) {
            Frame currentFrame = controller.frame();
            if (currentFrame.hands().count() > 0) {
                Hand firstHand = currentFrame.hands().get(0);
                if (firstHand.fingers().count() > 0) {
                    Finger firstFinger = firstHand.fingers().get(0);
                    Vector current = firstFinger.stabilizedTipPosition();
                    if (current.getX()> center.getX() + threshold) {
                        right = true;
//                        System.out.println("right = " + right);
                    }else{
                        right = false;
                    }
                    if (current.getX() < center.getX() - threshold) {
                        left = true;
//                        System.out.println("left = " + left);
                    }else{
                        left = false;
                    }
                    if (current.getY() > center.getY() + threshold) {
                        up = true;
//                        System.out.println("up = " + up);
                    }else{
                        up = false;
                    }
                    if (current.getY() < center.getY() - threshold) {
                        down = true;
//                        System.out.println("down = " + down);
                    }else{
                        down = false;
                    }
                    if (current.getY() > center.getY() - threshold) {
                        if (current.getY() < center.getY() + threshold) {
                            if (current.getX() > center.getX() - threshold) {
                                if (current.getX() < center.getX() + threshold) {
//                                    System.out.println("Center?");
                                } else {
                                    System.out.println(4);
                                }
                            } else {
                                System.out.println(3);
                            }
                        } else {
                            System.out.println(2);
                        }
                    } else {
                        System.out.println(1);
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

    public boolean isCenter() {
        return up && down && left && right;
    }

    public LeapImage getLeapState() {
        if (isDown() && !isLeft() && !isRight() && !isUp()) return LeapImage.DOWN;
        if (!isDown() && isLeft() && !isRight() && !isUp()) return LeapImage.LEFT;
        if (!isDown() && !isLeft() && isRight() && !isUp()) return LeapImage.RIGHT;
        if (!isDown() && !isLeft() && !isRight() && isUp()) return LeapImage.UP;

        if (isDown() && isLeft() && !isRight() && !isUp()) return LeapImage.LEFT_DOWN;
        if (isDown() && !isLeft() && isRight() && !isUp()) return LeapImage.RIGHT_DOWN;

        if (!isDown() && isLeft() && !isRight() && isUp()) return LeapImage.LEFT_UP;
        if (!isDown() && !isLeft() && isRight() && isUp()) return LeapImage.RIGHT_UP;

        return LeapImage.CENTER;
    }

}
