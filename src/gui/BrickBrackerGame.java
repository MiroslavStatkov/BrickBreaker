package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class BrickBrackerGame extends JFrame implements KeyListener{

    private static final long serialVersionUID = 1L;
    private JPanel scorePanel;
    private JLabel lives, score;
    private Board board;
    private boolean isPaused;
    private ScheduledExecutorService musicExecutor;
    private MusicThread music;
    private Runnable play;
    private boolean left = false;
    private boolean right = false;


    public BrickBrackerGame() {
        setSize(600, 600);
        setTitle("Brick Breaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        createComponents();
        setProperties();
        addComponents();
        RunGame();
    }

    private void RunGame() {
        Runnable playSound = new Runnable() {

            public void run() {
                music = new MusicThread();
                music.start();
            }
        };
        this.musicExecutor.scheduleAtFixedRate(playSound, 0, 22,
                TimeUnit.SECONDS);

        play = new Runnable() {

            public void run() {
                while (true) {
                    try {
                        if (isPaused) {
                            Thread.sleep(100);
                        } else {
                            movePaddle();
                            board.moveBall();
                            board.repaint();
                            board.checkWinner();
                            Thread.sleep(5);
                        }
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted thread exception");
                    }
                }
            }

        };
        new Thread(play).start();

    }

    private void movePaddle() {
        if (left) {
            if (!isPaused) {
                board.movePaddleLeft();
            }
        } else if (right) {
            if (!isPaused) {
                board.movePaddleRight();
            }
        }
    }

    private void addComponents() {
        add(scorePanel, BorderLayout.NORTH);
        add(board, BorderLayout.CENTER);
    }

    private void setProperties() {
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.setFocusable(true);
        container.addKeyListener(this);

        scorePanel.setBackground(Color.BLACK);
        lives = new JLabel();
        lives.setBackground(Color.BLACK);
        lives.setForeground(Color.WHITE);
        lives.setFont(new Font(lives.getFont().getName(), Font.PLAIN, 24));
        setLivesText(3);
        scorePanel.add(lives, BorderLayout.WEST);
        score = new JLabel("Score: 0 ");
        score.setBackground(Color.BLACK);
        score.setForeground(Color.WHITE);
        score.setFont(new Font(score.getFont().getName(), Font.PLAIN, 24));
        scorePanel.add(score, BorderLayout.EAST);
    }

    private void createComponents() {
        board = new Board(this);
        scorePanel = new JPanel(new BorderLayout());
        isPaused = false;
        this.musicExecutor = Executors.newScheduledThreadPool(1);
        music = new MusicThread();
    }

    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        left = (c == KeyEvent.VK_LEFT);
        right = (c == KeyEvent.VK_RIGHT);

        if (c == KeyEvent.VK_P) {
            isPaused = true;
        } else if (c == KeyEvent.VK_R) {
            isPaused = false;
        }
    }

    public void keyReleased(KeyEvent e) {
        int c = e.getKeyCode();
        if (left && (c == KeyEvent.VK_LEFT)) {
            left = false;
        }
        if (right && (c == KeyEvent.VK_RIGHT)) {
            right = false;
        }
    }

    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
    }

    public void restart() {
        board = new Board(this);
        add(board, BorderLayout.CENTER);
        setLivesText(3);
        setScoreText();
    }

    public void setLivesText(int numLives) {
        StringBuilder builder = new StringBuilder();
        builder.append("Lives: ");
        builder.append(numLives + " ");
        for (int i = 1; i <= numLives; i++) {
            builder.append("\u25CF ");
        }
        lives.setText(builder.toString());
    }

    public void setScoreText() {
        score.setText("Score: " + board.getScore() + " ");
    }



}
