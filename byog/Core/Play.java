package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.Color;
import java.awt.Font;

public class Play {
    private static long keyboardInput;
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;
    private static World w;
    private static TETile[][] finalWorldFrame;
    private static int x;
    private static int y;
    private static int x2;
    private static int y2;
    private static boolean end;
    static TERenderer ter = new TERenderer();

    public static void generateWorld(int choice) {
        if (choice == 0) {
            keyboardInput = Menu.enterSeed();
            finalWorldFrame = new TETile[WIDTH][HEIGHT];
            w = new World(finalWorldFrame, keyboardInput);
            w.fillWithWorldTiles();
            ter.initialize(WIDTH, HEIGHT);
            ter.renderFrame(w.world);
        } else if (choice == 1) {
            w = loadWorld();
            ter.initialize(WIDTH, HEIGHT);
            ter.renderFrame(w.world);
        } else if (choice == 2) {
            System.exit(0);
        }
        play();
    }

    public static void play() {
        x = w.startingX;
        y = w.startingY;
        x2 = w.playerX;
        y2 = w.playerY;
        char command  = 0;
        end = false;
        while (!end) {
            updateMouse(w);
            if (StdDraw.hasNextKeyTyped()) {
                command = Character.toLowerCase(StdDraw.nextKeyTyped());
                keyWalk(command, w);
                //Tileset.PLAYER.draw(x, y);
                //Tileset.PLAYER2.draw(x2, y2);
                StdDraw.show();
                ter.renderFrame(w.world);
            }
        }
    }

    private static void keyWalk(char command, World www) {
        if (command == 'w') {
            if (win(www, x, y + 1)) {
                Menu.drawWin(1);
            } else if (www.world[x][y + 1].equals(Tileset.FLOOR)) {
                www.world[x][y] = Tileset.FLOOR;
                www.world[x][y + 1] = Tileset.PLAYER;
                y = y + 1;
            }
        } else if (command == 'a') {
            if (win(www, x - 1, y)) {
                Menu.drawWin(1);
            } else if (www.world[x - 1][y].equals(Tileset.FLOOR)) {
                www.world[x][y] = Tileset.FLOOR;
                www.world[x - 1][y] = Tileset.PLAYER;
                x = x - 1;
            }
        } else if (command == 's') {
            if (win(www, x, y - 1)) {
                Menu.drawWin(1);
            } else if (www.world[x][y - 1].equals(Tileset.FLOOR)) {
                www.world[x][y] = Tileset.FLOOR;
                www.world[x][y - 1] = Tileset.PLAYER;
                y = y - 1;
            }
        } else if (command == 'd') {
            if (win(www, x + 1, y)) {
                Menu.drawWin(1);
            } else if (www.world[x + 1][y].equals(Tileset.FLOOR)) {
                www.world[x][y] = Tileset.FLOOR;
                www.world[x + 1][y] = Tileset.PLAYER;
                x = x + 1;
            }
        } else if (command == 'u') {
            if (win(www, x2, y2 + 1)) {
                Menu.drawWin(2);
            } else if (www.world[x2][y2 + 1].equals(Tileset.FLOOR)) {
                www.world[x2][y2] = Tileset.FLOOR;
                www.world[x2][y2 + 1] = Tileset.PLAYER2;
                y2 = y2 + 1;
            }
        } else if (command == 'h') {
            if (win(www, x2 - 1, y2)) {
                Menu.drawWin(2);
            } else if (www.world[x2 - 1][y2].equals(Tileset.FLOOR)) {
                www.world[x2][y2] = Tileset.FLOOR;
                www.world[x2 - 1][y2] = Tileset.PLAYER2;
                x2 = x2 - 1;
            }
        } else if (command == 'j') {
            if (win(www, x2, y2 - 1)) {
                Menu.drawWin(2);
            } else if (www.world[x2][y2 - 1].equals(Tileset.FLOOR)) {
                www.world[x2][y2] = Tileset.FLOOR;
                www.world[x2][y2 - 1] = Tileset.PLAYER2;
                y2 = y2 - 1;
            }
        } else if (command == 'k') {
            if (win(www, x2 + 1, y2)) {
                Menu.drawWin(2);
            } else if (www.world[x2 + 1][y2].equals(Tileset.FLOOR)) {
                www.world[x2][y2] = Tileset.FLOOR;
                www.world[x2 + 1][y2] = Tileset.PLAYER2;
                x2 = x2 + 1;
            }
        } else if (command == 'q') {
            www.startingX = x;
            www.startingY = y;
            www.playerX = x2;
            www.playerY = y2;
            saveWorld(www);
            System.exit(0);
        }
    }

    public static void saveWorld(World wd) {
        File f = new File("./world.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(wd);
            os.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            //System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            //System.exit(0);
        }
    }

    public static World loadWorld() {
        File f = new File("./world.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                World loadWorld = (World) os.readObject();
                os.close();
                return loadWorld;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                //System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                //System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                //System.exit(0);
            }
        }
        //System.exit(0);
        return new World();
        /* In the case no World has been saved yet, we return a new one. */
    }

    private static void updateMouse(World wd) {
        StdDraw.setPenColor(Color.pink);
        Font font = new Font("Arial", Font.BOLD, 15);
        StdDraw.setFont(font);
        int mx = (int) StdDraw.mouseX();
        int my = (int) StdDraw.mouseY();
        try {
            String object = wd.world[mx][my].description();
            StdDraw.textLeft(1, HEIGHT - 1, object);
            StdDraw.line(0, HEIGHT - 2, WIDTH, HEIGHT - 2);
            StdDraw.show();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledRectangle(0, HEIGHT - 1, 10, 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("It is okay~");
        }
    }

    private static boolean win(World ww, int xx, int yy) {
        if (ww.world[xx][yy].equals(Tileset.LOCKED_DOOR)) {
            end = true;
            return true;
        }
        return false;
    }
}
