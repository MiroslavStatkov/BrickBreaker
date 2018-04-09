package game;

import gui.Board;
import java.util.ArrayList;

public class Ball {
    private int xPos;
    private int yPos;
    private boolean brickHit;
    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;
    public static final int BALL_DIAMETER = 10;

    public Ball(int x, int y) {
        xPos = x;
        yPos = y;
        moveUp = true;
        moveRight = true;
        moveDown = false;
        moveLeft = false;
        brickHit = false;
    }

    // returns brick that was hit or null if no brick hit
    public Piece move(int paddleX, int paddleY, ArrayList<Piece> bricks) {
        brickHit = false;
        Piece hitBrick = setMoveDirection(paddleX, paddleY, bricks);

        if (moveLeft) {
            xPos -= 1;

        } else if (moveRight) {
            xPos += 1;

        }
        if (moveUp) {
            yPos -= 1;
        } else if (moveDown) {
            yPos += 1;
        }

        return hitBrick;
    }

    // returns brick that was hit or null if no brick hit
    private Piece setMoveDirection(int x, int y, ArrayList<Piece> bricks) {
        // check if the ball should move up or down and then right or left

        checkHitWall();
        checkHitPaddle(x, y);
        for (Piece brick : bricks) {
            checkBrickCollision(brick);
            if (brickHit) {
                return brick;
            }
        }
        // no brick hit
        return null;
    }

    private void checkHitWall() {
        checkTopWall();
        checkLeftWall();
        checkRightWall();
    }

    private void checkTopWall() {
        if (yPos <= 0) {
            switchUpandDown();
        }
    }

    private void checkLeftWall() {
        if (xPos <= 0) {
            switchRightandLeft();
        }
    }

    private void checkRightWall() {

        if (xPos + BALL_DIAMETER + 6 >= Board.BOARD_WIDTH) {
            switchRightandLeft();
        }
    }

    private void checkHitPaddle(int x, int y) {
        checkTopPaddle(x, y);
        checkSidePaddle(x, y);

    }

    private void checkTopPaddle(int x, int y) {
        if (yPos + BALL_DIAMETER == (y)) {
            /*
			 * if (xPos + BALL_DIAMETER == (x + Paddle.PADDLE_LENGTH)) {
			 * System.out.println("right corner"); if (moveLeft) {
			 * switchUpandDown(); switchRightandLeft(); } else {
			 * switchUpandDown(); }
			 *
			 * } else if (xPos + BALL_DIAMETER == x) {
			 * System.out.println("left corner"); if (moveRight) {
			 * switchUpandDown(); switchRightandLeft(); } else {
			 * switchUpandDown(); } } else
			 */
            if (xPos < (x + Paddle.PADDLE_LENGTH) && xPos > x) {
                switchUpandDown();
            }
        }
    }

    private void checkSidePaddle(int x, int y) {

        if (((leftSide(x) || rightSide(x)) && yPos + BALL_DIAMETER >= y && yPos
                + BALL_DIAMETER <= (y + Paddle.PADDLE_HEIGHT))) {
            switchUpandDown();
            switchRightandLeft();
        }
    }
}
