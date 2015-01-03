package com.github.vitineth.game;

import com.github.vitineth.game.graphics.Renderer;
import com.github.vitineth.game.input.KeyBoard;
import com.github.vitineth.game.items.creatures.players.Player;
import com.github.vitineth.game.leap.integration.LeapImage;
import com.github.vitineth.game.leap.integration.LeapListener;
import com.github.vitineth.game.levels.derivatives.CastleLevel;
import com.github.vitineth.game.misc.Coordinate;
import com.leapmotion.leap.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.lang.reflect.Field;

/**
 * Created by Ryan on 01/01/2015.
 *
 * @author Ryan
 * @since 01/01/2015
 */
public class Game extends Canvas implements Runnable, WindowListener {

    private static final int WIDTH = 300; //GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();//300;
    private static final int HEIGHT = WIDTH / 16 * 9;
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private Renderer renderer = new Renderer(WIDTH, HEIGHT);
    private static final int SCALE = 2;
    private final int ORIGIN_X = 100;
    private final int ORIGIN_Y = 100;
    ;
    public JFrame frame;
    CastleLevel level = new CastleLevel();
    Player player = new Player(new Coordinate(7, 7));
    int xo, yo;
    private boolean IS_LEAP_ENABLED;
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private boolean running = false;
    private Thread thread;
    private Thread leapThread;
    private LeapListener listener;
    private Controller controller;
    private LeapImage currentLeapState = LeapImage.CENTER;
    private KeyBoard board = new KeyBoard();
    private int[] lastOffset = new int[]{0, 0};

    public Game() {
        setPreferredSize(new Dimension(scale(WIDTH), scale(HEIGHT)));

        frame = new JFrame();
        frame.addKeyListener(board);
        addKeyListener(board);

    }

    public static void main(String[] args) {
        Game game = new Game();
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("-leap")) {
                game.IS_LEAP_ENABLED = true;
            } else {
                game.IS_LEAP_ENABLED = false;
            }
        } else {
            game.IS_LEAP_ENABLED = false;
        }
        if (game.IS_LEAP_ENABLED) {
            System.out.println(System.getProperty("java.library.path"));
            if (!System.getProperty("java.library.path").contains("\\LeapSDK\\lib\\")) {
                String input = JOptionPane.showInputDialog("Please enter the path to the leap native files.");
                File file = new File(input);
                if (file.exists()) {
                    if (file.isDirectory()) {
                        resetLibraryPath(input);
                    } else {
                        JOptionPane.showMessageDialog(null, "The specified path is not a directory.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "The specified path does not exist.");
                }
            }
        }

        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setResizable(false);
        game.frame.setLocation(game.ORIGIN_X, game.ORIGIN_Y);
        game.frame.add(game);
        game.frame.pack();
        game.frame.setTitle("Game");
        game.frame.addWindowListener(game);
        game.frame.setVisible(true);
//        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(game.frame);
        game.start();
    }

    //Dirty hack: http://blog.cedarsoft.com/2010/11/setting-java-library-path-programmatically/
    public static void resetLibraryPath(String leapJavaInstallLocation) {
        try {
            System.setProperty("java.library.path", leapJavaInstallLocation);

            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static int getScreenWidth() {
        return WIDTH;
    }

    public static int getScreenHeight() {
        return HEIGHT;
    }

    private final int scale(int origin) {
        return origin * SCALE;

    }

    public synchronized void start() {
        thread = new Thread(this, "Display thread");
        if (IS_LEAP_ENABLED) leapThread = new Thread("Leap Motion Integration Thread") {
            @Override
            public void run() {
                controller = new Controller();
                listener = new LeapListener();
                controller.addListener(listener);
                listener.start();
                while (running) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        running = true;
        if (IS_LEAP_ENABLED) leapThread.start();
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
            leapThread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {

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
        if (IS_LEAP_ENABLED) {
            g.drawImage(currentLeapState.getRawImage(), getWidth() - 100, 0, 100, 100, null);
            g.setColor(new Color(0, 0, 0, 55));
            g.fillRect(getWidth() - 100, 0, 100, 100);
        }
        //
        g.dispose();
        bs.show();
    }

    public void milo() {
        if (IS_LEAP_ENABLED) {
            currentLeapState = listener.getLeapState();
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
                player.move(-2, 0, level);
            }
            if (listener.isRight()) {
                xo -= 2;
                player.move(2, 0, level);
            }
        } else {
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

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    //////////////////////////////////

    @Override
    public void windowDeactivated(WindowEvent e) {
        stop();
    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }
}
