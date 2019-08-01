package byog.Core;
import edu.princeton.cs.introcs.StdDraw;
import java.io.Serializable;
//import java.awt.Color;
import java.awt.Font;
//import java.awt.event.KeyEvent;
//import java.util.Random;


public class Menu implements Serializable {
    private static long input;
    private static final long serialVersionUID = 45498234798734234L;

    public static long enterSeed() {
        drawFrame();
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(0.6, 0.5, "(Press s after entering the seed)");
        StdDraw.text(0.2, 0.45, "Enter Seed: ");

        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        StdDraw.text(0.2, 0.3, "Player 1: ");
        StdDraw.picture(0.3, 0.3, "/pig.png");
        StdDraw.picture(0.3, 0.1, "byog/Core/keyboard2.png");
        StdDraw.text(0.5, 0.3, "Player 2: ");
        StdDraw.picture(0.7, 0.3, "/cat.png");
        StdDraw.picture(0.7, 0.1, "byog/Core/keyboard3.png");
        StdDraw.show();

        char seed = 0;
        String seedInString = "";
        String seedString = "";
        double x = 0.3;
        input = 0;
        while (seed != ':') {
            StdDraw.show();
            if (StdDraw.hasNextKeyTyped()) {
                seed = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (seed == 's') {
                    input = Long.parseLong(seedInString);
                    return input;
                } else {
                    seedString = Character.toString(seed);
                    seedInString += seed;
                    StdDraw.text(x, 0.45, seedString);
                    x += 0.017;
                }
            }
        }
        return 0;
    }

    public static void drawWin(int x) {
        StdDraw.pause(500);
        StdDraw.setCanvasSize(1000, 700);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.PINK);

        Font font = new Font("Arial", Font.BOLD, 55);
        StdDraw.setFont(font);
        if (x == 1) {
            StdDraw.text(0.5, 0.5, "Player 1 win! Congrats!!!!");
        } else {
            StdDraw.text(0.5, 0.5, "Player 2 win! Congrats!!!!");
        }
        font = new Font("Arial", Font.BOLD, 35);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        StdDraw.text(0.5, 0.4, "press Q to quit :)");
        StdDraw.text(0.5, 0.3, "press N to back to the menu :)");
        StdDraw.show();

        char start = 0;
        boolean a = true;
        while (a) {
            if (StdDraw.hasNextKeyTyped()) {
                start = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (start == 'n') {
                    a = false;
                    StdDraw.clear(StdDraw.BLACK);
                    StdDraw.setPenColor(StdDraw.PINK);

                    font = new Font("Arial", Font.BOLD, 55);
                    StdDraw.setFont(font);
                    StdDraw.text(0.5, 0.7, "CS61B: The Game");
                    StdDraw.show();
                    int choice = displayOption();
                    Play.generateWorld(choice);

                } else if (start == 'q') {
                    a = false;
                    StdDraw.clear(StdDraw.BLACK);
                    StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                    StdDraw.text(0.5, 0.5, "Bye");
                    StdDraw.show();
                    StdDraw.pause(2000);
                    System.exit(0);
                }
            }
        }
    }

    public static void drawFrame() {
        StdDraw.setCanvasSize(1000, 700);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.PINK);

        Font font = new Font("Arial", Font.BOLD, 55);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.7, "CS61B: The Game");

    }

    public static int displayOption() {
        drawFrame();
        Font font = new Font("Arial", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.45, "New Game (N)");
        StdDraw.text(0.5, 0.4, "Load Game (L)");
        StdDraw.text(0.5, 0.35, "Quit (Q)");
        StdDraw.show();

        char start  = 0;
        while (start != 'q') {
            if (StdDraw.hasNextKeyTyped()) {
                start = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (start == 'n') {
                    return 0;
                } else if (start == 'l') {
                    return 1;
                } else {
                    while (!StdDraw.hasNextKeyTyped()) {
                        StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
                        StdDraw.text(0.5, 0.2, "Invalid input, please enter N, L, or Q");
                        StdDraw.pause(1000);
                        StdDraw.setPenColor(StdDraw.BLACK);
                        StdDraw.filledRectangle(0.5, 0.20, 0.3, 0.05);
                        StdDraw.pause(1000);
                    }

                }

            }
        }
        return 2;
    }
}
