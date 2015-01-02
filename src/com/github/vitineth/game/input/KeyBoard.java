package com.github.vitineth.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class KeyBoard implements KeyListener{
    
    private boolean[] keys = new boolean[1];

    @Override
    public void keyPressed(KeyEvent e) {
        if(keys.length < e.getKeyCode()){
            boolean[] old = keys;
            keys = new boolean[e.getKeyCode() + 1];
            for (int i = 0; i < old.length; i++) {
                keys[i] = old[i];
            }
        }
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(keys.length < e.getKeyCode()){
            boolean[] old = keys;
            keys = new boolean[e.getKeyCode() + 1];
            for (int i = 0; i < old.length; i++) {
                keys[i] = old[i];
            }
        }
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    
    public boolean getKeyStatus(int code){
        return keys[code];
    }

    public boolean upDown(){
        if(keys.length >= 87){
            return keys[87];
        }
        return false;
    }

    public boolean downDown(){
        if(keys.length >= 83){
            return keys[83];
        }
        return false;
    }

    public boolean leftDown(){
        if(keys.length >= 68){
            return keys[68];
        }
        return false;
    }

    public boolean rightDown(){
        if(keys.length >= 65){
            return keys[65];
        }
        return false;
    }
    
}
