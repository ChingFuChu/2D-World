package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int SIZE = 8;

    private static final int hexWidth = SIZE + (SIZE - 1) * 2;
    private static final int hexHeight = SIZE * 2 ;

    private static final int WIDTH = hexWidth * 4;
    private static final int HEIGHT = hexHeight * 5;

    private static final long SEED = 5678;
    private static final Random RANDOM = new Random(SEED);

    public static void addHexagon(TETile[][] tiles, int t, TETile z) {
        int height = SIZE * 2;
        int width = SIZE + (SIZE - 1) * 2;

        int h = 0;
        int v= 0;

        if(t <= 3) {
            h = t * hexHeight;
            v = 0;
        } else if (t <= 7) {
            h = (t-4) * hexHeight + SIZE;
            v = SIZE + (SIZE - 1);
        } else if (t <= 12) {
            h = (t-8) * hexHeight;
            v = 2 * (SIZE + (SIZE - 1));
        } else if (t <= 16) {
            h = (t-13) * hexHeight + SIZE;
            v = 3 * (SIZE + (SIZE - 1));
        } else {
            h = (t-16) * hexHeight;
            v = 4 * (SIZE + (SIZE - 1));
        }

        if(t == 1) {
            for (int x = 0; x < WIDTH; x += 1) {
                for (int y = 0; y < HEIGHT; y += 1) {
                    tiles[x][y] = Tileset.NOTHING;
                }
            }
        }

        for (int i = 0; i < SIZE - 1; i++) {
            for (int j = SIZE - 1 * (i + 1); j < SIZE - 1 * (i + 1) + 2 * (1 + i) && j < height && j >= 0; j++) {
                tiles[i + v][j + h] = z;
                tiles[v + width - i - 1][j + h] = z;
            }
        }

        for (int i = SIZE - 1; i < SIZE - 1 + SIZE; i++) {
            for (int j = 0; j < height; j++) {
                tiles[v + i][j + h] = z;

            }
        }
    }

    private static TETile random2Tile() {
        int tileNum = RANDOM.nextInt(11);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.PLAYER2;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.WATER;
            case 4: return Tileset.PLAYER;
            case 5: return Tileset.UNLOCKED_DOOR;
            case 6: return Tileset.LOCKED_DOOR;
            case 7: return Tileset.FLOOR;
            case 8: return Tileset.TREE;
            case 9: return Tileset.MOUNTAIN;
            case 10: return Tileset.SAND;
            default: return Tileset.NOTHING;
        }
    }


    public static void main(String [] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH,HEIGHT);

        TETile[][] randomTiles = new TETile[WIDTH][HEIGHT];
        for(int i = 1; i <= 19; i+=1){
            addHexagon(randomTiles, i, random2Tile());
        }

        ter.renderFrame(randomTiles);
    }
}
