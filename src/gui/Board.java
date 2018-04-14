package gui;

import game.Ball;
import game.Paddle;
import game.Piece;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel {

    private static final long serialVersionUID = 1L;
    public static final int BOARD_HEIGHT = 600;
    public static final int BOARD_WIDTH = 600;
    public static byte lives = 4;
    private Paddle paddle;
    private Ball ball;
    private ArrayList<Piece> bricks;
    private int livesLeft;
    private BrickBreakerGame frame;
    private int score;
    private BufferedImage img;

    public Board(BrickBreakerGame frame) {
        this.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        paddle = new Paddle();
        ball = new Ball(BOARD_WIDTH / 2,
                (paddle.getY() - Paddle.PADDLE_HEIGHT) - 10);
        bricks = new ArrayList<Piece>();
        
        for (int x = 0, y = 50; x <= 550; x += 50) {
           bricks.add(new Piece(x, y, Color.RED));
        }

        for (int x = 0, y = 80; x <= 550; x += 50) {
            bricks.add(new Piece(x, y, Color.ORANGE));
        }

        for (int x = 0, y = 110; x <= 550; x += 50) {
            bricks.add(new Piece(x, y, Color.YELLOW));
        }

        for (int x = 0, y = 140; x <= 550; x += 50) {
            bricks.add(new Piece(x, y, Color.GREEN));
        }

        for (int x = 0, y = 170; x <= 550; x += 50) {
            bricks.add(new Piece(x, y, Color.BLUE));
        }

        livesLeft = lives;
        score = 0;
        this.frame = frame;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/resources/wall1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        g.setColor(Color.BLUE);
        g.fillRect(paddle.getX(), paddle.getY(), Paddle.PADDLE_LENGTH,
                Paddle.PADDLE_HEIGHT);
        g.setColor(Color.cyan);
        g.fillOval(ball.getX(), ball.getY(), Ball.BALL_DIAMETER,
                Ball.BALL_DIAMETER);
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
            if (livesLeft == 0) {
                int playAgain = JOptionPane.showConfirmDialog(null,
                        "Game over! Would you like to play again?",
                        "Game Over", JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, new ImageIcon(
                                getClass().getResource("/resources/gameOver.jpg")));
                if (playAgain == 0) {
                    frame.restart();
                } else {
                    frame.dispose();
                    System.exit(0);
                }
            } else {
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
                            .getResource("/resources/cup.jpg")));
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
