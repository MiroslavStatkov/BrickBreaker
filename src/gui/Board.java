package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel{

    private static final long serialVersionUID = 1L;
    private Paddle paddle;
    private Ball ball;
    private ArrayList<Piece> bricks;
    public static final int BOARD_HEIGHT = 600;
    public static final int BOARD_WIDTH = 600;
    private int livesLeft;
    private BrickBrackerGame frame;
    private int score;

    public Board(BrickBrackerGame frame) {
        this.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        this.setBackground(Color.black);
        paddle = new Paddle();
        ball = new Ball(BOARD_WIDTH / 2,
                (paddle.getY() - Paddle.PADDLE_HEIGHT) - 10);
        bricks = new ArrayList<Piece>();
        bricks.add(new Piece(0, 50, Color.RED));
        bricks.add(new Piece(50, 50, Color.RED));
        bricks.add(new Piece(100, 50, Color.RED));
        bricks.add(new Piece(150, 50, Color.RED));
        bricks.add(new Piece(200, 50, Color.RED));
        bricks.add(new Piece(250, 50, Color.RED));
        bricks.add(new Piece(300, 50, Color.RED));
        bricks.add(new Piece(350, 50, Color.RED));
        bricks.add(new Piece(400, 50, Color.RED));
        bricks.add(new Piece(450, 50, Color.RED));
        bricks.add(new Piece(500, 50, Color.RED));
        bricks.add(new Piece(550, 50, Color.RED));
        bricks.add(new Piece(0, 80, Color.ORANGE));
        bricks.add(new Piece(50, 80, Color.ORANGE));
        bricks.add(new Piece(100, 80, Color.ORANGE));
        bricks.add(new Piece(150, 80, Color.ORANGE));
        bricks.add(new Piece(200, 80, Color.ORANGE));
        bricks.add(new Piece(250, 80, Color.ORANGE));
        bricks.add(new Piece(300, 80, Color.ORANGE));
        bricks.add(new Piece(350, 80, Color.ORANGE));
        bricks.add(new Piece(400, 80, Color.ORANGE));
        bricks.add(new Piece(450, 80, Color.ORANGE));
        bricks.add(new Piece(500, 80, Color.ORANGE));
        bricks.add(new Piece(550, 80, Color.ORANGE));
        bricks.add(new Piece(0, 110, Color.YELLOW));
        bricks.add(new Piece(50, 110, Color.YELLOW));
        bricks.add(new Piece(100, 110, Color.YELLOW));
        bricks.add(new Piece(150, 110, Color.YELLOW));
        bricks.add(new Piece(200, 110, Color.YELLOW));
        bricks.add(new Piece(250, 110, Color.YELLOW));
        bricks.add(new Piece(300, 110, Color.YELLOW));
        bricks.add(new Piece(350, 110, Color.YELLOW));
        bricks.add(new Piece(400, 110, Color.YELLOW));
        bricks.add(new Piece(450, 110, Color.YELLOW));
        bricks.add(new Piece(500, 110, Color.YELLOW));
        bricks.add(new Piece(550, 110, Color.YELLOW));
        bricks.add(new Piece(0, 140, Color.GREEN));
        bricks.add(new Piece(50, 140, Color.GREEN));
        bricks.add(new Piece(100, 140, Color.GREEN));
        bricks.add(new Piece(150, 140, Color.GREEN));
        bricks.add(new Piece(200, 140, Color.GREEN));
        bricks.add(new Piece(250, 140, Color.GREEN));
        bricks.add(new Piece(300, 140, Color.GREEN));
        bricks.add(new Piece(350, 140, Color.GREEN));
        bricks.add(new Piece(400, 140, Color.GREEN));
        bricks.add(new Piece(450, 140, Color.GREEN));
        bricks.add(new Piece(500, 140, Color.GREEN));
        bricks.add(new Piece(550, 140, Color.GREEN));
        bricks.add(new Piece(0, 170, Color.BLUE));
        bricks.add(new Piece(50, 170, Color.BLUE));
        bricks.add(new Piece(100, 170, Color.BLUE));
        bricks.add(new Piece(150, 170, Color.BLUE));
        bricks.add(new Piece(200, 170, Color.BLUE));
        bricks.add(new Piece(250, 170, Color.BLUE));
        bricks.add(new Piece(300, 170, Color.BLUE));
        bricks.add(new Piece(350, 170, Color.BLUE));
        bricks.add(new Piece(400, 170, Color.BLUE));
        bricks.add(new Piece(450, 170, Color.BLUE));
        bricks.add(new Piece(500, 170, Color.BLUE));
        bricks.add(new Piece(550, 170, Color.BLUE));
        livesLeft = 3;
        score = 0;
        this.frame = frame;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillRect(paddle.getX(), paddle.getY(), Paddle.PADDLE_LENGTH,
                Paddle.PADDLE_HEIGHT);
        g.setColor(Color.white);
        g.fillOval(ball.getX(), ball.getY(), Ball.BALL_DIAMETER,
                Ball.BALL_DIAMETER);
        // create loop to set up all pieces - make array of pieces
        for (int i = 0; i < bricks.size(); i++) {
            Piece brick = bricks.get(i);
            g.setColor(brick.getColor());
            g.fillRect(brick.getX(), brick.getY(), Piece.BRICK_LENGTH,
                    Piece.BRICK_WIDTH);
            g.setColor(Color.BLACK);
            g.drawRect(brick.getX(), brick.getY(), Piece.BRICK_LENGTH,
                    Piece.BRICK_WIDTH);
        }

    }

    public void movePaddleLeft() {
        paddle.moveLeft();
        repaint();
    }

    public void movePaddleRight() {
        paddle.moveRight();
        repaint();
    }

    public void moveBall() throws InterruptedException {

        if (ball.getY() > BOARD_HEIGHT) {
            // the ball died
            // pause time thread.sleep not working
            if (livesLeft == 0) {
                int playAgain = JOptionPane.showConfirmDialog(null,
                        "Game over! Would you like to play again?",
                        "Game Over", JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, new ImageIcon(
                                getClass().getResource("/gameOver.jpg")));
                if (playAgain == 0) {
                    frame.restart();
                } else {
                    frame.dispose();
                    System.exit(0);
                }
            } else {
                // send in new ball , remove this ball
                livesLeft--;
                frame.setLivesText(livesLeft);
                ball = new Ball(paddle.getX(),
                        (paddle.getY() - Paddle.PADDLE_HEIGHT) - 10);
            }
        } else {
            Piece hitBrick = ball.move(paddle.getX(), paddle.getY(), bricks);
            if (hitBrick != null) {
                Color brickColor = hitBrick.getColor();
                if (brickColor == Color.BLUE) {
                    score += 100;
                } else if (brickColor == Color.GREEN) {
                    score += 200;
                } else if (brickColor == Color.YELLOW) {
                    score += 300;
                } else if (brickColor == Color.ORANGE) {
                    score += 400;
                } else {
                    score += 500;
                }
                frame.setScoreText();
                bricks.remove(hitBrick);
            }
        }
    }

    public void checkWinner() {
        if (bricks.size() == 0) {
            int playAgain = JOptionPane.showConfirmDialog(null,
                    "You win! Would you like to play again?",
                    "Congratulations!!", JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass()
                            .getResource("/winner.jpg")));
            if (playAgain == 0) {
                frame.restart();
            } else {
                frame.dispose();
                System.exit(0);
            }
        }
    }

    public int getScore() {
        return score;
    }


}
