import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class GameCompletedPanel extends JPanel {
    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 400;
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Font BIG_FONT = new Font("Impact", Font.BOLD, 70);
    private static final Font SMALL_FONT = new Font("Courier New", Font.PLAIN, 15);
    private static final int FADE_IN_DELAY = 100;
    private static final int FADE_IN_DURATION = 5000;
    private static final String COMPLETED_TEXT = "You have saved the PoKeKalyes of Queensrow!";
    private GamePanel gamePanel;
    private long elapsedTime;

    private JLabel completedTextLabel;
    private JLabel imageLabel;
    private JLabel timeCompletedLabel;
    private float alpha;
    private Timer fadeInTimer;
    private Clip musicClip;

    public GameCompletedPanel(GamePanel gamePanel, long elapsedTime) {
        this.gamePanel = gamePanel;
        this.elapsedTime = elapsedTime;
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        completedTextLabel = new JLabel(COMPLETED_TEXT);
        completedTextLabel.setForeground(TEXT_COLOR);
        completedTextLabel.setFont(SMALL_FONT);

        timeCompletedLabel = new JLabel();
        timeCompletedLabel.setForeground(TEXT_COLOR);
        timeCompletedLabel.setFont(SMALL_FONT);

        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR);

        String pokeKalyeName = gamePanel.getPokeKalyeName();
        imageLabel = new JLabel(new ImageIcon("media/images/" + pokeKalyeName + "1.png"));
        addImageLabel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;

        add(completedTextLabel, gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.PAGE_END;

        add(timeCompletedLabel, gbc);

        setBorder(BorderFactory.createEmptyBorder(70, 10, 10, 10));

        alpha = 0f;
        fadeInTimer = new Timer(FADE_IN_DELAY, new ActionListener() {
            private final long startTime = System.currentTimeMillis();

            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                alpha = (float) elapsedTime / FADE_IN_DURATION;
                if (alpha >= 1f) {
                    alpha = 1f;
                    fadeInTimer.stop();
                    displayTimeCompleted();
                }
                repaint();
            }
        });
        fadeInTimer.setRepeats(true);
        fadeInTimer.start();
        playMusic("media/audio/over.wav");

        Action quitAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopMusic();
                System.exit(0);
            }
        };

        Action restartAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopMusic();
                Window window = SwingUtilities.getWindowAncestor(GameCompletedPanel.this);
                window.dispose();
                PokeKalyeChooser.main(null);
            }
        };

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "quit");
        getActionMap().put("quit", quitAction);

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "restart");
        getActionMap().put("restart", restartAction);
    }

    private void displayTimeCompleted() {
        long elapsedTime = System.currentTimeMillis() - gamePanel.getStartTime();
        long seconds = elapsedTime / 1000;
        long minutes = seconds / 60;
        seconds %= 60;
        String timeCompleted = "Time Completed: " + minutes + " minutes " + seconds + " seconds";
        timeCompletedLabel.setText(timeCompleted);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(TEXT_COLOR);
        g2d.setFont(BIG_FONT);
        int labelWidth = g2d.getFontMetrics().stringWidth("GAME COMPLETED");
        int labelX = (getWidth() - labelWidth) / 2;
        int labelY = PANEL_HEIGHT / 2 - 80;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawString("GAME COMPLETED", labelX, labelY);
    }

    private void playMusic(String musicFilePath) {
        try {
            File musicFile = new File(musicFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioStream);

            musicClip.loop(Clip.LOOP_CONTINUOUSLY);

            musicClip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    private void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
        }
    }

    private void addImageLabel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(-90, 0, 20, 0);

        add(imageLabel, gbc);
    }
}
