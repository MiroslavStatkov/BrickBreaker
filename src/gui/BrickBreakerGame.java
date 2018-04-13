package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.Clip;
import javax.swing.*;


public class BrickBreakerGame extends JFrame implements KeyListener {


    private static final long serialVersionUID = 1L;
    private JPanel scorePanel;
    private JLabel lives, score;
    private JMenuItem menuItemStart;
    private JMenuItem menuItemPause;
    private JMenuItem menuItemResume;
    private JMenuItem menuItemExit;
    private JMenuItem menuItemAbout;
    private JRadioButtonMenuItem btnSoundON;
    private JRadioButtonMenuItem btnSoundOFF;
    private Board board;
    private boolean isPaused;
    private ScheduledExecutorService musicExecutor;
    private Music music;
    private Clip sound;
    private Runnable play;
    private boolean left = false;
    private boolean right = false;


    public BrickBreakerGame() {
        setSize(600, 640);
        setTitle("Brick Breaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setMenuBar();
        createEvents();
        createComponents();
        setProperties();
        addComponents();
        RunGame();
    }

    private void createEvents() {
        menuItemStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });

        menuItemPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPaused = true;
            }
        });

        menuItemResume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPaused = false;
            }
        });

        menuItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int prompt = JOptionPane.showOptionDialog(BrickBreakerGame.this,
                        "Are you sure you want to exit?",
                        "Brick Breaker", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (prompt == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        menuItemAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                About about = new About();
                about.setModal(true);
                about.setVisible(true);
            }
        });

        btnSoundON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sound == null) {
                    music.run();
                }
            }

        });

        btnSoundOFF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                music.stopMusic();
                if (sound != null) {
                    sound.stop();
                    sound = null;
                }
            }
        });

        addWindowListener(new WindowAdapter() {

            @Override

            public void windowClosing(WindowEvent we) {
                int prompt = JOptionPane.showOptionDialog(BrickBreakerGame.this,
                        "Are you sure you want to exit?",
                        "Brick Breaker", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (prompt == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }

        });
    }

    private void setMenuBar() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource
                ("/resources/wall1.jpg")));
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Game");
        JMenu menuSound = new JMenu("Sound");
        JMenu menuHelp = new JMenu("Help");

        menuItemStart = new JMenuItem("Restart");
        menuItemPause = new JMenuItem("Pause");
        menuItemResume = new JMenuItem("Resume");
        menuItemExit = new JMenuItem("Exit");
        menuItemAbout = new JMenuItem("About");
        btnSoundON = new JRadioButtonMenuItem("Sound ON");
        btnSoundOFF = new JRadioButtonMenuItem("Sound OFF");

        setJMenuBar(menuBar);
        menuBar.add(menuGame);
        menuBar.add(menuSound);
        menuBar.add(menuHelp);
        menuGame.add(menuItemStart);
        menuGame.add(menuItemPause);
        menuGame.add(menuItemResume);
        menuGame.add(menuItemExit);
        menuSound.add(btnSoundON);
        menuSound.add(btnSoundOFF);
        menuSound.addSeparator();
        menuHelp.add(menuItemAbout);
    }

    private void RunGame() {
        Runnable playSound = new Runnable() {

            public void run() {
                music = new Music();
                music.start();
            }
        };
        this.musicExecutor.scheduleAtFixedRate(playSound, 0, 30,
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

        scorePanel.setBackground(new java.awt.Color(65, 10, 65));
        lives = new JLabel();
        lives.setBackground(new java.awt.Color(65, 10, 65));
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
        music = new Music();
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
