package game;

import java.awt.Color;

public class Piece {
    public static int BRICK_LENGTH = 50;
    public static int BRICK_WIDTH = 30;
    private int xPos;
    private int yPos;
    private Color color;

    public Color getColor() {
        return color;
    }

    public Piece(int x, int y, Color color) {
        xPos = x;
        yPos = y;
        this.color = color;

    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }
}
