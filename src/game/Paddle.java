package game;

import gui.Board;

public class Paddle {

    private static final int MOVE_AMOUNT = 2;
    private int xPos;
    private int yPos;
    public static final int PADDLE_LENGTH = 80;
    public static final int PADDLE_HEIGHT = 15;

    public Paddle() {

        xPos = (Board.BOARD_WIDTH - PADDLE_LENGTH) / 2;
        yPos = (Board.BOARD_HEIGHT - PADDLE_HEIGHT) - 75;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public void moveLeft() {
        if (xPos - MOVE_AMOUNT >= 0) {
            xPos -= MOVE_AMOUNT;
        }
    }

    public void moveRight() {

        if (xPos + MOVE_AMOUNT <= (Board.BOARD_WIDTH - PADDLE_LENGTH)) {
            xPos += MOVE_AMOUNT;
        }
    }
}
