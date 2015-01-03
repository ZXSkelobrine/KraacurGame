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

    //Ints
    //>Static integers
    public static int WIDTH = 700; //GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();//300;
    public static int HEIGHT = WIDTH / 16 * 9;
    public static int SCALE = 2;
    //>Regular integers
    private int xo, yo;
    public static int ORIGIN_X = 100;
    public static int ORIGIN_Y = 100;

    //Booleans
    public static boolean IS_LEAP_ENABLED;
    private boolean running = false;

    //Images
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    //Pixel arrays
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private int[] lastOffset = new int[]{0, 0};

    //JFrames
    public JFrame frame;

    //Threads
    private Thread leapThread;
    private Thread thread;

    //Leap integration variabled
    private LeapListener listener;
    private Controller controller;
    private LeapImage currentLeapState = LeapImage.CENTER;

    //Game classes
    private KeyBoard board = new KeyBoard();
    private Renderer renderer = new Renderer(WIDTH, HEIGHT);
    private CastleLevel level = new CastleLevel();
    private Player player = new Player(new Coordinate(7, 7));

    public Game() {
        setPreferredSize(new Dimension(scale(WIDTH), scale(HEIGHT)));

        frame = new JFrame();
        frame.addKeyListener(board);
        addKeyListener(board);

    }

    /////////////////////////////////////////////

    public static void main(String[] args) {
        //Process the launch flags and save the results
        Object[] glags = processLaunchFlags(args);
        //Set each individual result.
        Game.IS_LEAP_ENABLED = (Boolean) glags[0];
        Game.WIDTH = (Integer) glags[1];
        Game.HEIGHT = (Integer) glags[2];
        Game.SCALE = (Integer) glags[3];
        Game.ORIGIN_X = (Integer) glags[4];
        Game.ORIGIN_Y = (Integer) glags[5];

        //Create a new game.
        Game game = new Game();
        //If the leap is enabled.
        if (game.IS_LEAP_ENABLED) {
            //If the java library path does not contain the path section "\LeapSDK\lib\" then
            if (!System.getProperty("java.library.path").contains("\\LeapSDK\\lib\\")) {
                //Ask the user to input the location of the files.
                String input = JOptionPane.showInputDialog("Please enter the path to the leap native files.");
                //Create a file from their input.
                File file = new File(input);
                //If it exists
                if (file.exists()) {
                    //And if it is a directory
                    if (file.isDirectory()) {
                        //Then reset the path
                        resetLibraryPath(input);
                    } else {
                        //Otherwise tell the user of the mistake
                        JOptionPane.showMessageDialog(null, "The specified path is not a directory.");
                    }
                } else {
                    //Otherwise tell the user of the mistake
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

    private static Object[] processLaunchFlags(String[] args) {
        Object[] results = new Object[6];
        for (String s : args) {
            if (s.matches("-leap:(?:tru|fals)e")) {
                boolean result = Boolean.parseBoolean(s.replace("-leap:", ""));
                results[0] = result;
                System.out.println("Leap set to: " + result);
            } else if (s.matches("-size:[0-9]*,[0-9]*")) {
                String[] split = s.replace("-size:", "").split(",");
                int width = Integer.parseInt(split[0]);
                int height = Integer.parseInt(split[1]);
                results[1] = width;
                results[2] = height;
                System.out.println("Size (double arg) has been set to: (" + width + ", " + height + ")");
            } else if (s.matches("-size:[0-9]*")) {
                int width = Integer.parseInt(s.replace("-size:", ""));
                int height = width / 16 * 9;
                results[1] = width;
                results[2] = height;
                System.out.println("Size (single arg) has been set to: (" + width + ", " + height + "[gen])");
            } else if (s.matches("-width:[0-9]*")) {
                int width = Integer.parseInt(s.replace("-width:", ""));
                results[1] = width;
                System.out.println("Width has been set to: " + width);
            } else if (s.matches("-height:[0-9]*")) {
                int height = Integer.parseInt(s.replace("-height:", ""));
                results[2] = height;
                System.out.println("Height has been set to: " + height);
            } else if (s.matches("-scale:[0-9]*")) {
                int scale = Integer.parseInt(s.replace("-scale:", ""));
                results[3] = scale;
                System.out.println("Scale has been set to: " + scale);
            } else if (s.matches("-originx:[0-9]*")) {
                int originx = Integer.parseInt(s.replace("-originx:", ""));
                results[4] = originx;

            } else if (s.matches("-originy:[0-9]*")) {
                int originy = Integer.parseInt(s.replace("-originy:", ""));
                results[5] = originy;
            } else {
                System.err.println("The given argument '" + s + "' is not known by the program. Here are the possible flags");
                System.err.println("\t-leap:boolean  # A true or false value whether to enable the Leap Motion Controller support.");
                System.err.println("\t-size:int,int  # Two ints (whole numbers) for the width and height in this format: -size:width,height");
                System.err.println("\t-size:int      # An int to represent the width. The height it then calculated using the calculation: WIDTH / 16 * 9 if the height is not specified");
                System.err.println("\t-width:int     # An int to represent the width. The height it then calculated using the calculation: WIDTH / 16 * 9 if the height is not specified");
                System.err.println("\t-height:int    # An int to represent the height. The width it then calculated using the calculation: (HEIGHT / 9) * 16 if the width is not specified.");
                System.err.println("\t-scale:int     # An int to represent the scale");
                System.err.println("\t-originx:int   # An int to represent where the window should appear on the x axis");
                System.err.println("\t-originy:int   # An int to represent where the window should appear on the x axis");
                System.err.println("The program will now exit");
                System.exit(1);
            }
        }
        if (results[0] == null) results[0] = false;
        if (results[1] == null) results[1] = results[2] == null ? 700 : (((Integer) results[2]).intValue() * 9) / 16;
        if (results[2] == null) results[2] = ((Integer) results[1]).intValue() / 16 * 9;
        if (results[3] == null) results[3] = 2;
        if (results[4] == null) results[4] = 100;
        if (results[5] == null) results[5] = 100;
        return results;
    }

    /////////////////////////////////////////

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
