import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.Window;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class GameOverPanel extends JPanel {
    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 400;
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Font BIG_FONT = new Font("Impact", Font.BOLD, 90);
    private static final Font SMALL_FONT = new Font("Courier New", Font.PLAIN, 20);
    private static final int FADE_IN_DELAY = 100;
    private static final int FADE_IN_DURATION = 5000;
    private static final int ANIMATION_DELAY = 3000;
    private static final String QUIT_TEXT = "ESC to quit, ENTER to try again.";

    private JLabel randomTextLabel;
    private JLabel quitTextLabel;
    private float alpha;
    private String[] randomTexts = {
            "like John Cena, you must never give up...",
            "olats...!",
            "bounce back lang...!",
            "Professor Kalye is disappointed...",
            "so that's it huh...?"
    };
    private Timer fadeInTimer;
    private Timer typingTimer;

    public GameOverPanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        randomTextLabel = new JLabel();
        randomTextLabel.setForeground(TEXT_COLOR);
        randomTextLabel.setFont(SMALL_FONT);

        quitTextLabel = new JLabel();
        quitTextLabel.setForeground(TEXT_COLOR);
        quitTextLabel.setFont(SMALL_FONT);

        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;

        add(randomTextLabel, gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.PAGE_END;

        add(quitTextLabel, gbc);

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
                    startTypingAnimation();
                    startQuitTextAnimation();
                }
                repaint();
            }
        });
        fadeInTimer.setRepeats(true);
        fadeInTimer.start();

        Action quitAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };

        Action restartAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window window = SwingUtilities.getWindowAncestor(GameOverPanel.this);
                window.dispose();
                Professor.main(null);
            }
        };

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "quit");
        getActionMap().put("quit", quitAction);

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "restart");
        getActionMap().put("restart", restartAction);
    }

    private void startTypingAnimation() {
        typingTimer = new Timer(100, new ActionListener() {
            private int currentCharIndex = 0;
            private int randomTextIndex = (int) (Math.random() * randomTexts.length);

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentCharIndex < randomTexts[randomTextIndex].length()) {
                    String displayedText = randomTexts[randomTextIndex].substring(0, currentCharIndex + 1);
                    randomTextLabel.setText(displayedText);
                    currentCharIndex++;
                } else {
                    typingTimer.stop();
                }
            }
        });
        typingTimer.setInitialDelay(0);
        typingTimer.start();
    }

    private void startQuitTextAnimation() {
        Timer quitTextTimer = new Timer(100, new ActionListener() {
            private int currentCharIndex = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentCharIndex < QUIT_TEXT.length()) {
                    String displayedText = QUIT_TEXT.substring(0, currentCharIndex + 1);
                    quitTextLabel.setText(displayedText);
                    currentCharIndex++;
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        quitTextTimer.setInitialDelay(ANIMATION_DELAY);
        quitTextTimer.start();
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
        int labelWidth = g2d.getFontMetrics().stringWidth("GAME OVER");
        int labelX = (getWidth() - labelWidth) / 2;
        int labelY = PANEL_HEIGHT / 2 - 80;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawString("GAME OVER", labelX, labelY);
    }
}
