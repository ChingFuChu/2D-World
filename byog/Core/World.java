package byog.Core;

//import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
//import java.lang.Character;
import java.util.Random;
import java.io.Serializable;

public class World implements Serializable {
    private static final long serialVersionUID = 45498234798734234L;
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;

    private Random RANDOM;
    protected TETile[][] world;
    protected int startingX;
    protected int startingY;

    protected int playerX;
    protected int playerY;


    public World(TETile[][] world, long seed) {
        this.world = world;
        this.startingX = 0;
        this.startingY = 0;
        this.playerX = 0;
        this.playerY = 0;
        RANDOM = new Random(seed);

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public World() {
    }

    public void fillWithWorldTiles() {
        addPath(world);
        world[startingX][startingY] = Tileset.PLAYER;
        boolean a = true;
        while (a) {
            playerX = RANDOM.nextInt(60) + 10;
            playerY = RANDOM.nextInt(30) + 10;
            if (world[playerX][playerY].equals(Tileset.FLOOR)) {
                world[playerX][playerY] = Tileset.PLAYER2;
                a = false;
            }
        }
    }

    private void drawWall(TETile[][] tiles, int x, int y) {
        if (tiles[x][y] == Tileset.FLOOR) {
            tiles[x][y] = Tileset.FLOOR;
        } else {
            tiles[x][y] = Tileset.WALL;
        }
    }

    private void drawFloor(TETile[][] tiles, int x, int y) {
        if (tiles[x][y] == Tileset.WALL) {
            tiles[x][y] = Tileset.WALL;
        }
        tiles[x][y] = Tileset.FLOOR;
    }

    private void drawEnding(TETile[][] tiles, int x, int y) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                drawWall(tiles, x + i, y + j);
            }
        }
        tiles[x][y - 1] = Tileset.LOCKED_DOOR;
    }


    private void goVertical(TETile[][] tiles, int x, int y, int distance, int v) {
        for (int k = -1; k <= 1; k++) {
            drawWall(tiles, x + k, y - v);
        }
        for (int j = 0; j < distance; j++) {
            drawFloor(tiles, x, y);
            drawWall(tiles, x - 1, y);
            drawWall(tiles, x + 1, y);
            y = y + v;
        }
        drawFloor(tiles, x, y);
    }

    private void goHorizontal(TETile[][] tiles, int x, int y, int distance, int v) {
        for (int k = -1; k <= 1; k++) {
            drawWall(tiles, x - v, y + k);
        }
        for (int j = 0; j < distance; j++) {
            drawFloor(tiles, x, y);
            drawWall(tiles, x, y + 1);
            drawWall(tiles, x, y - 1);
            x = x + v;
        }
        drawFloor(tiles, x, y);
    }


    public void addPath(TETile[][] tiles) {
        int x = RANDOM.nextInt(WIDTH - 20) + 10;
        int y = RANDOM.nextInt(HEIGHT - 20) + 10;
        startingX = x;
        startingY = y;
        int[] b;

        for (int i = 0; i < RANDOM.nextInt(15) + 15; i++) {
            int direction = RANDOM.nextInt(2);
            int direction2 = RANDOM.nextInt(2);
            int distance = RANDOM.nextInt(HEIGHT / 5) + 3;
            int distance2 = RANDOM.nextInt(WIDTH / 3) + 3;

            if (direction == 0) { //go Up
                if (y + distance < HEIGHT - 5) {
                    goVertical(tiles, x, y, distance, 1);
                    y = y + distance;
                    b = addRoomVertical(tiles, x, y, 1);
                    x = b[0];
                    y = b[1];
                } else {
                    int turn = RANDOM.nextInt(3) + 3;
                    if (x > 10) {
                        goHorizontal(tiles, x, y, turn, -1);
                        x = x - turn;
                        goVertical(tiles, x, y, turn, -1);
                        y = y - turn;
                    } else {
                        goHorizontal(tiles, x, y, turn, 1);
                        x = x + turn;
                        goVertical(tiles, x, y, turn, -1);
                        y = y - turn;
                    }
                }
            } else {
                if (y <= 10) {
                    int turn = RANDOM.nextInt(3) + 3;
                    if (x > 10) {
                        goHorizontal(tiles, x, y, turn, -1);
                        x = x - turn;
                        goVertical(tiles, x, y, turn, 1);
                        y = y + turn;
                    } else {
                        goHorizontal(tiles, x, y, turn, 1);
                        x = x + turn;
                        goVertical(tiles, x, y, turn, 1);
                        y = y + turn;
                    }
                } else if (y - distance > 5) {
                    goVertical(tiles, x, y, distance, -1);
                    y = y - distance;
                    b = addRoomVertical(tiles, x, y, -1);
                    x = b[0];
                    y = b[1];
                }
            }
            if (direction2 == 0) {
                if (x >= WIDTH - 5) {
                    distance2 = 0;
                } else if (x + distance2 > WIDTH - 5) {
                    distance2 = RANDOM.nextInt(WIDTH - 5 - x);
                }
                goHorizontal(tiles, x, y, distance2, 1);
                x = x + distance2;
            } else {
                if (x <= 5) {
                    distance2 = 0;
                } else if (x - distance2 < 5) {
                    distance2 = RANDOM.nextInt(x - 5);
                }
                goHorizontal(tiles, x, y, distance2, -1);
                x = x - distance2;
            }
        }
        drawEnding(tiles, x, y);
    }

    private int exitSide(int startPt, int finalPt) {
        if (finalPt > HEIGHT - 10) {
            if (startPt < 10) {
                return 2;
            } else {
                return 0;
            }
        } else {
            if (startPt < 10) {
                return 2;
            } else {
                return 0;
            }
        }
    }

    public int[] addRoomVertical(TETile[][] tiles, int x, int y, int dir) {
        int height = RANDOM.nextInt(6) + 4;
        int width = RANDOM.nextInt(6) + 4;
        int referenceX;
        int referenceY;
        int[] a = new int[2];
        int exitSide = RANDOM.nextInt(2); //0 is left, 1 is parallel, 2 is right
        int stPoint = x - RANDOM.nextInt(2) - 1;
        int finalPos = y + (height) * dir;

        if (finalPos > HEIGHT - 5 || finalPos < 5 || stPoint < 5 || stPoint + width > WIDTH - 5) {
            a[0] = x;
            a[1] = y;
            return a;
        }
        for (int i = stPoint; i < stPoint + width; i++) {
            drawWall(tiles, i, y);
        }
        if (finalPos > HEIGHT - 10 || finalPos < 10) {
            exitSide = exitSide(stPoint, finalPos);
        }
        if (exitSide == 1) {
            int exIndex = RANDOM.nextInt(width - 2) + 1;
            drawFloor(tiles, stPoint + exIndex, y + (height - 1) * dir);
            int dis = RANDOM.nextInt(3);
            goVertical(tiles, stPoint + exIndex, y + height * dir, dis, dir);
            referenceX = stPoint + exIndex;
            referenceY = y + (height + dis) * dir;
        } else {
            int exitIndex = RANDOM.nextInt(height - 2) + 1;
            //System.out.println("Adding room, side is " + exitSide);
            if (exitSide == 0) {
                drawFloor(tiles, stPoint, y + exitIndex * dir);
                int distance = RANDOM.nextInt(3);
                goHorizontal(tiles, stPoint - 1, y + exitIndex * dir, distance, -1);
                referenceX = stPoint - 1 - distance;
                referenceY = y + exitIndex * dir;
            } else {
                drawFloor(tiles, stPoint + width - 1, y + exitIndex * dir);
                int distance = RANDOM.nextInt(3);
                goHorizontal(tiles, stPoint + width, y + exitIndex * dir, distance, 1);
                referenceX = stPoint + width + distance;
                referenceY = y + exitIndex * dir;
            }
        }
        for (int i = stPoint; i < stPoint + width; i++) {
            drawWall(tiles, i, y + (height - 1) * dir);
        }
        if (dir == -1) {
            for (int j = y - (height - 1); j < y; j++) {
                drawWall(tiles, stPoint, j);
                drawWall(tiles, stPoint + width - 1, j);
            }
            for (int i = stPoint + 1; i < stPoint + width - 1; i++) {
                for (int j = y - (height - 1) + 1; j < y; j++) {
                    drawFloor(tiles, i, j);
                }
            }
        } else {
            for (int j = y + 1; j < y + height - 1; j++) {
                drawWall(tiles, stPoint, j);
                drawWall(tiles, stPoint + width - 1, j);
            }
            for (int i = stPoint + 1; i < stPoint + width - 1; i++) {
                for (int j = y + 1; j < y + height - 1; j++) {
                    drawFloor(tiles, i, j);
                }
            }
        }
        a[0] = referenceX;
        a[1] = referenceY;
        return a;
    }

    public int[] addRoomHorizontal(TETile[][] tiles, int x, int y, int dir) {
        int height = RANDOM.nextInt(5) + 5;
        int width = RANDOM.nextInt(5) + 5;
        int referenceX;
        int referenceY;
        int[] a = new int[2];

        int stPoint = x - 1 - RANDOM.nextInt(3);

        if (y + height > HEIGHT - 5 || stPoint < 5 || stPoint + width > WIDTH - 5) {
            a[0] = x;
            a[1] = y;
            return a;
        }

        for (int i = stPoint; i < stPoint + width; i++) {
            drawWall(tiles, i, y);
        }

        int exitSide = 1; //0 is left, 1 is parallel, 2 is right
        if (exitSide == 1) {
            int exIndex = RANDOM.nextInt(width - 2) + 1;
            drawFloor(tiles, stPoint + exIndex, y + (height - 1) * dir);
            int dis = 1;

            goVertical(tiles, stPoint + exIndex, y + height * dir, dis, dir);
            referenceX = stPoint + exIndex;
            referenceY = y + (height + dis) * dir;

        } else {
            int exitIndex = RANDOM.nextInt(height - 2) + 1;
            if (exitSide == 0) {
                drawFloor(tiles, stPoint, y + exitIndex * dir);
                int distance = 1;
                goHorizontal(tiles, stPoint - 1, y + exitIndex * dir, distance, -1);

                referenceX = stPoint - 1 - distance;
                referenceY = y + exitIndex * dir;

            } else {
                drawFloor(tiles, stPoint + width - 1, y + exitIndex * dir);
                int distance = 1;
                goHorizontal(tiles, stPoint + width, y + exitIndex * dir, distance, 1);

                referenceX = stPoint + width + distance;
                referenceY = y + exitIndex * dir;
            }
        }

        for (int i = stPoint; i < stPoint + width; i++) {
            drawWall(tiles, i, y + (height - 1) * dir);
        }

        if (dir == -1) {
            for (int j = y - (height - 1); j < y; j++) {
                drawWall(tiles, stPoint, j);
                drawWall(tiles, stPoint + width - 1, j);
            }
            for (int i = stPoint + 1; i < stPoint + width - 1; i++) {
                for (int j = y - (height - 1) + 1; j < y; j++) {
                    drawFloor(tiles, i, j);
                }
            }

        } else {
            for (int j = y + 1; j < y + height - 1; j++) {
                drawWall(tiles, stPoint, j);
                drawWall(tiles, stPoint + width - 1, j);
            }
            for (int i = stPoint + 1; i < stPoint + width - 1; i++) {
                for (int j = y + 1; j < y + height - 1; j++) {
                    drawFloor(tiles, i, j);
                }
            }
        }

        a[0] = referenceX;
        a[1] = referenceY;
        return a;
    }
}
