package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

public class Game {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;
    private long keyboardInput;
    private World w;
    private TETile[][] finalWorldFrame;
    private int x;
    private int y;
    private int x2;
    private int y2;
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        StdDraw.setCanvasSize(1000, 700);
        Menu.drawFrame();
        int choice = Menu.displayOption();
        Play.generateWorld(choice);
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) { //change to non static
        //TERenderer ter = new TERenderer();
        //ter.initialize(WIDTH, HEIGHT);
        char startGame = Character.toLowerCase(input.charAt(0));
        int dirIndex = 1;
        if (startGame == 'n') {
            String seed = "";
            for (int i = 1; i < input.length(); i++) {
                dirIndex += 1;
                if (input.charAt(i) == 's' || input.charAt(i) == 'S') {
                    break;
                }
                seed = seed + input.charAt(i);
            }
            long numSeed = 0;
            try {
                numSeed = Long.parseLong(seed);
            } catch (NumberFormatException nfe) {
                System.out.println(seed);
            }

            finalWorldFrame = new TETile[WIDTH][HEIGHT];
            w = new World(finalWorldFrame, numSeed);
            w.fillWithWorldTiles();
        } else if (startGame == 'l') {
            w = Play.loadWorld();
        }

        x = w.startingX;
        y = w.startingY;
        x2 = w.playerX;
        y2 = w.playerY;

        char command  = 0;
        walk(command, w, dirIndex, input);
        //ter.renderFrame(w.world);
        return w.world;
    }

    private void walk(char com, World wd, int dirI, String inp) {
        for (int i = dirI; i < inp.length(); i++) {

            com = Character.toLowerCase(inp.charAt(i));
            if (com == 'w') {
                if (wd.world[x][y + 1].equals(Tileset.FLOOR)) {
                    wd.world[x][y] = Tileset.FLOOR;
                    wd.world[x][y + 1] = Tileset.PLAYER;
                    y = y + 1;
                }
            } else if (com == 'a') {
                if (wd.world[x - 1][y].equals(Tileset.FLOOR)) {
                    wd.world[x][y] = Tileset.FLOOR;
                    wd.world[x - 1][y] = Tileset.PLAYER;
                    x = x - 1;
                }
            } else if (com == 's') {
                if (wd.world[x][y - 1].equals(Tileset.FLOOR)) {
                    wd.world[x][y] = Tileset.FLOOR;
                    wd.world[x][y - 1] = Tileset.PLAYER;
                    y = y - 1;
                }
            } else if (com == 'd') {
                if (wd.world[x + 1][y].equals(Tileset.FLOOR)) {
                    wd.world[x][y] = Tileset.FLOOR;
                    wd.world[x + 1][y] = Tileset.PLAYER;
                    x = x + 1;
                }
            } else if (com == 'u') {
                if (wd.world[x2][y2 + 1].equals(Tileset.FLOOR)) {
                    wd.world[x2][y2] = Tileset.FLOOR;
                    wd.world[x2][y2 + 1] = Tileset.PLAYER2;
                    y2 = y2 + 1;
                }
            } else if (com == 'h') {
                if (wd.world[x2 - 1][y2].equals(Tileset.FLOOR)) {
                    wd.world[x2][y2] = Tileset.FLOOR;
                    wd.world[x2 - 1][y2] = Tileset.PLAYER2;
                    x2 = x2 - 1;
                }
            } else if (com == 'j') {
                if (wd.world[x2][y2 - 1].equals(Tileset.FLOOR)) {
                    wd.world[x2][y2] = Tileset.FLOOR;
                    wd.world[x2][y2 - 1] = Tileset.PLAYER2;
                    y2 = y2 - 1;
                }
            } else if (com == 'k') {
                if (wd.world[x2 + 1][y2].equals(Tileset.FLOOR)) {
                    wd.world[x2][y2] = Tileset.FLOOR;
                    wd.world[x2 + 1][y2] = Tileset.PLAYER2;
                    x2 = x2 + 1;
                }
            } else if (com == ':') {
                com = Character.toLowerCase(inp.charAt(i + 1));
                if (com == 'q') {
                    wd.startingX = x;
                    wd.startingY = y;
                    wd.playerX = x2;
                    wd.playerY = y2;
                    Play.saveWorld(wd);
                }
            }
        }
    }
}


