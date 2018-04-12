package game;

import java.awt.Color;

public class Piece {
    private int xPos;
    private int yPos;
    private Color color;
    public static int BRICK_LENGTH = 50;
    public static int BRICK_WIDTH = 30;

    public Color getColor() {
        return color;
    }

    /*
     * the x is left most spot of rectangle
     */
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
