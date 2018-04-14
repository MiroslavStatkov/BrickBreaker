package game;

import gui.Board;

import java.util.ArrayList;

public class Ball {
    public static final int BALL_DIAMETER = 10;
    private int xPos;
    private int yPos;
    private boolean isBrickHit;
    private boolean isMoveLeft;
    private boolean isMoveRight;
    private boolean isMoveUp;
    private boolean isMoveDown;

    public Ball(int x, int y) {
        xPos = x;
        yPos = y;
        isMoveUp = true;
        isMoveRight = true;
        isMoveDown = false;
        isMoveLeft = false;
        isBrickHit = false;
    }

    public Piece move(int paddleX, int paddleY, ArrayList<Piece> bricks) {
        isBrickHit = false;
        Piece hitBrick = setMoveDirection(paddleX, paddleY, bricks);

        if (isMoveLeft) {
            xPos -= 1;

        } else if (isMoveRight) {
            xPos += 1;

        }
        if (isMoveUp) {
            yPos -= 1;
        } else if (isMoveDown) {
            yPos += 1;
        }

        return hitBrick;
    }

    private Piece setMoveDirection(int x, int y, ArrayList<Piece> bricks) {

        checkHitWall();
        checkHitPaddle(x, y);
        for (Piece brick : bricks) {
            checkBrickCollision(brick);
            if (isBrickHit) {
                return brick;
            }
        }
        return null;
    }

    private void checkHitWall() {
        checkTopWall();
        checkLeftWall();
        checkRightWall();
    }

    private void checkTopWall() {
        if (yPos <= 0) {
            switchUpAndDown();
        }
    }

    private void checkLeftWall() {
        if (xPos <= 0) {
            switchRightAndLeft();
        }
    }

    private void checkRightWall() {

        if (xPos + BALL_DIAMETER + 6 >= Board.BOARD_WIDTH) {
            switchRightAndLeft();
        }
    }

    private void checkHitPaddle(int x, int y) {
        checkTopPaddle(x, y);
        checkSidePaddle(x, y);

    }

    private void checkTopPaddle(int x, int y) {
        if (yPos + BALL_DIAMETER == (y)) {
            if (xPos < (x + Paddle.PADDLE_LENGTH) && xPos > x) {
                switchUpAndDown();
            }
        }
    }

    private void checkSidePaddle(int x, int y) {

        if (((leftSide(x) || rightSide(x)) && yPos + BALL_DIAMETER >= y && yPos
                + BALL_DIAMETER <= (y + Paddle.PADDLE_HEIGHT))) {
            switchUpAndDown();
            switchRightAndLeft();
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
            switchRightAndLeft();
            isBrickHit = true;
        }
    }

    private void checkBrickLeft(Piece brick) {
        int brickY = brick.getY();

        if ((xPos + BALL_DIAMETER >= brick.getX()) && xPos < brick.getX()
                && (yPos + BALL_DIAMETER <= (brickY + Piece.BRICK_WIDTH))
                && (yPos + BALL_DIAMETER >= brickY)) {
            switchRightAndLeft();
            isBrickHit = true;
        }
    }

    private void checkBrickTopBottom(Piece brick) {
        int brickX = brick.getX();
        if ((brickTop(brick) || brickBottom(brick))
                && (xPos <= brickX + Piece.BRICK_LENGTH)) {
            if (isMoveLeft) {
                if (xPos + BALL_DIAMETER >= brickX) {
                    switchUpAndDown();
                    isBrickHit = true;
                }
            } else {
                if ((xPos >= brickX)) {
                    switchUpAndDown();
                    isBrickHit = true;
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

    private void switchRightAndLeft() {
        if (isMoveRight) {
            isMoveRight = false;
            isMoveLeft = true;
        } else {
            isMoveRight = true;
            isMoveLeft = false;
        }

    }

    private void switchUpAndDown() {
        if (isMoveUp) {
            isMoveUp = false;
            isMoveDown = true;
        } else {
            isMoveUp = true;
            isMoveDown = false;
        }

    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }
}
