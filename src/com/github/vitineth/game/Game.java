package com.github.vitineth.game;

import com.github.vitineth.game.graphics.Renderer;
import com.github.vitineth.game.input.KeyBoard;
import com.github.vitineth.game.items.creatures.players.Player;
import com.github.vitineth.game.leap.integration.LeapListener;
import com.github.vitineth.game.levels.derivatives.CastleLevel;
import com.github.vitineth.game.misc.Coordinate;
import com.leapmotion.leap.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class Game extends Canvas implements Runnable {

    private final int WIDTH =700; //GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();//300;
    private final int HEIGHT = WIDTH / 16 * 9;
    private final int SCALE = 2;

    private final int ORIGIN_X = 100;
    private final int ORIGIN_Y = 100;
    private final boolean IS_LEAP_ENABLED = true;

    private final int scale(int origin) {
        return origin * SCALE;

    };
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private boolean running = false;

    public JFrame frame;
    private Thread thread;
    private LeapListener listener;

    private Renderer renderer = new Renderer(WIDTH, HEIGHT);
    private KeyBoard board = new KeyBoard();

    public Game() {
        setPreferredSize(new Dimension(scale(WIDTH), scale(HEIGHT)));

        frame = new JFrame();
        frame.addKeyListener(board);
        addKeyListener(board);

    }

    public synchronized void start() {
        thread = new Thread(this, "Display thread");
        running = true;
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        if(IS_LEAP_ENABLED) {
            Controller controller = new Controller();
            listener = new LeapListener();
            controller.addListener(listener);
            listener.start();
        }

        double ns = 1000000000.0 / 60.0;
        double delta = 0;

        int frames = 0;
        int updates = 0;

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();

        requestFocus();

        while (running) {
            long now = System.nanoTime();

            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                milo();
                updates++;
                delta--;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - timer >= 1000) {
                timer += 1000;
                frame.setTitle("Game" + "  |  " + updates + " ups, " + frames + " fps");
                frames = 0;
                updates = 0;
            }
        }

        stop();
    }

    CastleLevel level = new CastleLevel();
    Player player = new Player(new Coordinate(7, 7));
    int xo, yo;

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        renderer.clear();
        renderer.render();

        level.renderMap(renderer);
        player.render(3, 3, renderer);

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = renderer.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        //
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        //
        g.dispose();
        bs.show();
    }

    private int[] lastOffset = new int[]{0, 0};

    public void milo() {
        if(IS_LEAP_ENABLED){
            if (listener.isUp()) {
                yo -= 2;
                player.move(0, -2, level);
            }
            if (listener.isDown()) {
                yo += 2;
                player.move(0, 2, level);
            }
            if (listener.isLeft()) {
                xo += 2;
                player.move(2, 0, level);
            }
            if (listener.isRight()) {
                xo -= 2;
                player.move(-2, 0, level);
            }
        }else {
            if (board.upDown()) {
                yo -= 2;
                player.move(0, -2, level);
            }
            if (board.downDown()) {
                yo += 2;
                player.move(0, 2, level);
            }
            if (board.leftDown()) {
                xo += 2;
                player.move(2, 0, level);
            }
            if (board.rightDown()) {
                xo -= 2;
                player.move(-2, 0, level);
            }
        }

        if (xo != lastOffset[0] || yo != lastOffset[1]) {
//            renderer.setOffset(xo, yo);
//            lastOffset = renderer.getOffset();
//            player.setX(xo);
//            player.setY(yo);
            lastOffset = new int[]{xo, yo};
            renderer.update(player);
        }
    }

    //////////////////////////////////

    public static void main(String[] args) {
        Game game = new Game();

        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setResizable(false);
        game.frame.setLocation(game.ORIGIN_X, game.ORIGIN_Y);
//        game.frame.setLocationRelativeTo(null);
        game.frame.add(game);
        game.frame.pack();
        game.frame.setTitle("Game");

        game.frame.setVisible(true);
//        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(game.frame);
        game.start();
    }

}
