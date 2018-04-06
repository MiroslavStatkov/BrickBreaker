package gui;

import java.awt.event.KeyListener;
import java.util.concurrent.ScheduledExecutorService;
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


}
