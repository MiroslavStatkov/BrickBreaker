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

    private boolean rightSide(int x) {
        return xPos == (x + Paddle.PADDLE_LENGTH);
    }

    private boolean leftSide(int x) {
        return (((xPos + BALL_DIAMETER) >= x) && (xPos < x));
    }

    private void checkBrickCollision(Piece brick) {
        checkBrickRight(brick);
        checkBrickLeft(brick);
        checkBrickTopBottom(brick);

    }

    private void checkBrickRight(Piece brick) {
        int brickY = brick.getY();
        if ((xPos == (brick.getX() + Piece.BRICK_LENGTH))
                && (yPos <= (brickY + Piece.BRICK_WIDTH)) && (yPos >= brickY)) {
            switchRightandLeft();
            brickHit = true;
        }
    }

    private void checkBrickLeft(Piece brick) {
        int brickY = brick.getY();

        if ((xPos + BALL_DIAMETER >= brick.getX()) && xPos < brick.getX()
                && (yPos + BALL_DIAMETER <= (brickY + Piece.BRICK_WIDTH))
                && (yPos + BALL_DIAMETER >= brickY)) {
            switchRightandLeft();
            brickHit = true;
        }
    }

    private void checkBrickTopBottom(Piece brick) {
        int brickX = brick.getX();
        if ((brickTop(brick) || brickBottom(brick))
                && (xPos <= brickX + Piece.BRICK_LENGTH)) {
            if (moveLeft) {
                if (xPos + BALL_DIAMETER >= brickX) {
                    switchUpandDown();
                    brickHit = true;
                }
            } else {
                if ((xPos >= brickX)) {
                    switchUpandDown();
                    brickHit = true;
                }
            }
        }

    }

    private boolean brickTop(Piece brick) {

        return (yPos + BALL_DIAMETER > brick.getY() && yPos < brick.getY());
    }

    private boolean brickBottom(Piece brick) {
        int bottom = brick.getY() + Piece.BRICK_WIDTH;
        return ((yPos - BALL_DIAMETER / 2) < (bottom) && yPos > bottom);

    }

    private void switchRightandLeft() {
        if (moveRight) {
            moveRight = false;
            moveLeft = true;
        } else {
            moveRight = true;
            moveLeft = false;
        }

    }

    private void switchUpandDown() {
        if (moveUp) {
            moveUp = false;
            moveDown = true;
        } else {
            moveUp = true;
            moveDown = false;
        }

    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }
}
