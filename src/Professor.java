import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.IOException;

public class Professor extends JFrame {
    private JLabel professorImageLabel;
    private SpeechBalloonPanel dialoguePanel;
    private JButton continueButton;
    private int dialogueIndex = 0;
    private JTextField nameTextField;
    private String playerName;
    private Timer typingTimer;
    private String currentDialogue;
    private int currentCharIndex;
    private Clip hoverSound;
    private Clip clickSound;
    private Clip backgroundMusic;

    public Professor() {
        setTitle("KalyeRPG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());
        String iconPath = "media/images/KalyeRPG.png";
        ImageIcon icon = new ImageIcon(iconPath);
        setIconImage(icon.getImage());
        playMusic("media/audio/professorTheme.wav");
        try {
            hoverSound = loadSound("media/audio/hover.wav");
            clickSound = loadSound("media/audio/click.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JPanel containerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(82, 113, 255));
                g2d.fillRect(0, 0, getWidth(), getHeight() / 2);
                g2d.setColor(new Color(255, 0, 130));
                g2d.fillRect(0, getHeight() / 2, getWidth(), getHeight() / 2);
            }
        };
        containerPanel.setLayout(new BorderLayout());
        add(containerPanel, BorderLayout.CENTER);

        dialoguePanel = new SpeechBalloonPanel();
        dialoguePanel.setOpaque(false);
        containerPanel.add(dialoguePanel, BorderLayout.CENTER);

        professorImageLabel = new JLabel();
        ImageIcon professorIcon = new ImageIcon("media/images/professor.png");
        Image scaledImage = professorIcon.getImage().getScaledInstance(205, -1, Image.SCALE_SMOOTH);
        professorImageLabel.setIcon(new ImageIcon(scaledImage));
        professorImageLabel.setPreferredSize(new Dimension(205, scaledImage.getHeight(null)));
        containerPanel.add(professorImageLabel, BorderLayout.LINE_START);

        continueButton = new JButton("CONTINUE");
        continueButton.setEnabled(false);
        add(continueButton, BorderLayout.SOUTH);

        continueButton.addActionListener(e -> {
            advanceDialogue();
            playSound(clickSound);
        });

        continueButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                continueButton.setBackground(new Color(102, 255, 51));
                playSound(hoverSound);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                continueButton.setBackground(UIManager.getColor("control"));
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    advanceDialogue();
                    playSound(clickSound);
                }
            }
        });

        setFocusable(true);
        requestFocusInWindow();

        displayDialogue();
    }

    private void advanceDialogue() {
        if (typingTimer != null && typingTimer.isRunning()) {
            finishTypingAnimation();
        } else {
            dialogueIndex++;
            if (dialogueIndex < 5) {
                displayDialogue();
            } else if (dialogueIndex == 5) {
                promptForName();
                dialogueIndex++;
            } else if (dialogueIndex == 6) {
                if (isNameValid()) {
                    displayFinalDialogue();
                    continueButton.setEnabled(true);
                    dialogueIndex++;
                } else {
                    dialogueIndex--;
                    JOptionPane.showMessageDialog(Professor.this,
                            "Prof RP: Ano?!", "What?!",
                            JOptionPane.PLAIN_MESSAGE);
                }
            } else if (dialogueIndex >= 7) {
                if (isNameValid()) {
                    stopMusic();
                    dispose();
                    PokeKalyeChooser.main(null);
                } else {
                    dialogueIndex--;
                }
            }
        }
    }

    private void displayDialogue() {
        String[] dialogues = { ". . .",
                "Hoy! Ikaw! Huwag ka lumabas! Deliks dito! May mga WILD na PokeKalye dito sa gedli! Kailangan mo ng sarili mong PokeKalye para sa proteksyon mo...",
                "Ako nga pala si PROF RP! Tinatawag nila ako bilang PROFESSOR KALYE! Mapapansin mo na lately napakaraming hayup na paligoy-ligoy rito sa Queensrow...",
                "Bilang professor, napaimbestiga ako rito dahil may mga kakaibang kaganapan talaga dito sa kalyeng to.. Kailangan mahuli na ang mga ito..",
                "Teka muna, ano ang iyong pangalan?"
        };
        currentDialogue = dialogues[dialogueIndex];
        startTypingAnimation();
        continueButton.setEnabled(true);
    }

    private void displayFinalDialogue() {
        if (isNameValid()) {
            String dialogue = playerName.toUpperCase() + ", kailangan ko ng tulong mo! "
                    + "Papipiliin na kita ng iyong PokeKalye... alagaan mo ito, "
                    + playerName.toUpperCase() + ", ang buong Queensrow ay nakasalalay sayo...!";
            currentDialogue = dialogue;
            startTypingAnimation();
            continueButton.setEnabled(true);
        } else {
            dialogueIndex--;
        }

    }

    private void promptForName() {
        JPanel namePanel = new JPanel();
        nameTextField = new JTextField();
        nameTextField.setColumns(20);
        nameTextField.setFont(new Font("Montserrat", Font.BOLD, 24));
        nameTextField.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        nameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                FocusGained(evt);
            }
        });
        namePanel.add(nameTextField);
        namePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        namePanel.setBackground(new Color(82, 113, 255));
        continueButton.setEnabled(false);
        add(namePanel, BorderLayout.NORTH);
        revalidate();
        nameTextField.requestFocus();

        nameTextField.setBounds(300, 25, 250, 30);

        ActionListener continueActionListener = e -> advanceDialogue();

        nameTextField.addActionListener(continueActionListener);

        nameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    playerName = nameTextField.getText();
                    if (isNameValid()) {
                        if (playerName.length() <= 10) {
                            remove(namePanel);
                            displayFinalDialogue();
                            validate();
                            repaint();
                        } else if (playerName.length() >= 10) {
                            JOptionPane.showMessageDialog(Professor.this,
                                    "Prof RP: Ang haba naman nyan lods...", "What?!",
                                    JOptionPane.PLAIN_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(Professor.this,
                                "                   Ha..? Pakiulit nga...", "Professor Kalye Asks",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });
    }

    private boolean isNameValid() {
        return playerName != null && !playerName.trim().isEmpty() && playerName.length() <= 10;
    }

    public String getPlayerName() {
        return playerName;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Professor professor = new Professor();
            professor.setLocationRelativeTo(null);
            professor.setResizable(false);
            professor.setVisible(true);
        });
    }

    private void startTypingAnimation() {
        currentCharIndex = 0;
        int typingSpeed = 50;
        typingTimer = new Timer(typingSpeed, e -> {
            if (currentCharIndex <= currentDialogue.length()) {
                dialoguePanel.setDialogue(currentDialogue.substring(0, currentCharIndex));
                dialoguePanel.repaint();
                currentCharIndex++;
            } else {
                finishTypingAnimation();
            }
        });
        typingTimer.start();
    }

    private void finishTypingAnimation() {
        currentCharIndex = currentDialogue.length();
        typingTimer = null;
        dialoguePanel.setDialogue(currentDialogue);
        dialoguePanel.repaint();
    }

    private class SpeechBalloonPanel extends JPanel {
        private String dialogue;

        public void setDialogue(String dialogue) {
            this.dialogue = dialogue;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            int x = 5;
            int y = getHeight() / 2 + 10;
            int width = getWidth() - 35;
            int height = getHeight() / 2 - 20;

            int cornerRadius = 20;
            int arrowSize = 20;
            int arrowPosition = width / 2 - arrowSize / 2;

            g2d.setColor(Color.YELLOW);
            g2d.setStroke(new BasicStroke(10));
            g2d.drawRoundRect(x, y, width, height, cornerRadius, cornerRadius);
            g2d.drawLine(x + arrowPosition, y + height / 2, x + arrowPosition + arrowSize, y + height / 2);

            g2d.setColor(Color.WHITE);
            g2d.fillRoundRect(x, y, width, height, cornerRadius, cornerRadius);
            g2d.fillRect(x, y + height / 2, arrowPosition, height / 2);
            g2d.fillRect(x + arrowPosition + arrowSize, y + height / 2, width - arrowPosition - arrowSize, height / 2);

            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Montserrat", Font.BOLD, 16));
            java.awt.FontMetrics fm = g2d.getFontMetrics();
            int textX = x + 20;
            int textY = y + 30;
            int maxLineWidth = width - 40;

            List<String> lines = splitDialogueIntoLines(dialogue, fm, maxLineWidth);

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                g2d.drawString(line, textX, textY + fm.getHeight() * i);
            }

            g2d.dispose();
        }

        private List<String> splitDialogueIntoLines(String dialogue, java.awt.FontMetrics fm, int maxLineWidth) {
            List<String> lines = new ArrayList<>();

            if (dialogue == null) {
                return lines;
            }

            String[] words = dialogue.split(" ");
            StringBuilder currentLine = new StringBuilder();

            for (String word : words) {
                String testLine = currentLine + " " + word;
                int testWidth = fm.stringWidth(testLine.trim());

                if (testWidth > maxLineWidth) {
                    lines.add(currentLine.toString());
                    currentLine = new StringBuilder(word);
                } else {
                    currentLine.append(" ").append(word);
                }
            }

            lines.add(currentLine.toString());

            return lines;
        }
    }

    private void FocusGained(java.awt.event.FocusEvent evt) {
        nameTextField.selectAll();
    }

    public String getSelectedPokeKalye() {
        return PokeKalyeChooser.getSelectedPokeKalye();
    }

    private Clip loadSound(String soundPath) throws Exception {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundPath).getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        return clip;
    }

    private void playSound(Clip sound) {
        sound.stop();
        sound.setFramePosition(0);
        sound.start();
    }

    private void stopMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
    }

    private void playMusic(String musicPath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(musicPath).getAbsoluteFile());
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
