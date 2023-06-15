import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class PokeKalyeChooser extends JFrame {
    private TooltipLabel puspinLabel;
    private TooltipLabel askalLabel;
    private TooltipLabel langgamLabel;
    private static JRadioButton puspinRadioButton;
    private static JRadioButton askalRadioButton;
    private static JRadioButton langgamRadioButton;
    private JButton startButton;
    private JLabel selectedPokeKalyeLabel;
    private static Clip puspinCrySound;
    private static Clip askalCrySound;
    private static Clip langgamCrySound;
    private Clip musicClip;
    private Clip currentCrySound;
    public static String pokeKalyeName;

    public PokeKalyeChooser() {
        setTitle("KalyeRPG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(null);
        String iconPath = "media/images/KalyeRPG.png";
        ImageIcon icon = new ImageIcon(iconPath);
        setIconImage(icon.getImage());

        setContentPane(new JLabel(new ImageIcon("media/images/bg.png")));

        JLabel pokeKalyeChooserLabel = new JLabel(
                "<html><p style='text-align:center; font-family:\"Impact\"; font-size:22px; color:white; letter-spacing:800em;'>CHOOSE YOUR POKEKALYE !</p></html>",
                JLabel.CENTER);

        pokeKalyeChooserLabel.setFont(new Font("Courier New", Font.PLAIN, 24));
        pokeKalyeChooserLabel.setForeground(Color.WHITE);
        pokeKalyeChooserLabel.setHorizontalAlignment(SwingConstants.CENTER);

        puspinLabel = new TooltipLabel("     PUSPIN ");
        askalLabel = new TooltipLabel("     ASKAL ");
        langgamLabel = new TooltipLabel("  LANGGAM ");

        puspinLabel.setOpaque(true);
        askalLabel.setOpaque(true);
        langgamLabel.setOpaque(true);

        puspinRadioButton = new JRadioButton();
        askalRadioButton = new JRadioButton();
        langgamRadioButton = new JRadioButton();

        puspinRadioButton.setOpaque(false);
        askalRadioButton.setOpaque(false);
        langgamRadioButton.setOpaque(false);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(puspinRadioButton);
        buttonGroup.add(askalRadioButton);
        buttonGroup.add(langgamRadioButton);

        startButton = new JButton("START JOURNEY");
        startButton.setEnabled(false);

        startButton.setBackground(Color.WHITE);

        ActionListener radioButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton selectedRadioButton = (JRadioButton) e.getSource();
                updatePokekalyePanel(selectedRadioButton);
                startButton.setEnabled(true);
                updateSelectedPokekalyeLabel(selectedRadioButton);
            }
        };
        puspinRadioButton.addActionListener(radioButtonListener);
        askalRadioButton.addActionListener(radioButtonListener);
        langgamRadioButton.addActionListener(radioButtonListener);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playClickSound();

                JTextField nameField = new JTextField(8);
                nameField.setPreferredSize(new Dimension(150, 25));
                ((AbstractDocument) nameField.getDocument()).setDocumentFilter(new DocumentFilter() {
                    @Override
                    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                            throws BadLocationException {
                        int currentLength = fb.getDocument().getLength();
                        int maxLength = 8;
                        int newLength = currentLength - length + text.length();

                        if (newLength <= maxLength) {
                            super.replace(fb, offset, length, text, attrs);
                        }
                    }
                });

                JPanel inputPanel = new JPanel();
                inputPanel.add(new JLabel("Give a name to " + getSelectedPokeKalye() + "?"));
                inputPanel.add(nameField);

                JButton okButton = new JButton("OK");
                okButton.setPreferredSize(new Dimension(140, okButton.getPreferredSize().height));

                okButton.setBackground(Color.WHITE);

                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String inputName = nameField.getText();
                        if (inputName == null || inputName.isEmpty()) {
                            inputName = getSelectedPokeKalye();
                        }

                        pokeKalyeName = inputName;

                        stopMusic();
                        playClickSound();
                        launchGame(pokeKalyeName);
                        dispose();
                    }
                });

                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        okButton.setBackground(new Color(102, 255, 51));
                        playHoverSound();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        okButton.setBackground(Color.WHITE);
                    }
                });

                Object[] options = { okButton };

                int result = JOptionPane.showOptionDialog(PokeKalyeChooser.this, inputPanel, "Enter Name",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

                if (result == JOptionPane.OK_OPTION) {
                    String inputName = nameField.getText();
                    if (inputName == null || inputName.isEmpty()) {
                        inputName = getSelectedPokeKalye();
                    }

                    pokeKalyeName = inputName;

                    stopMusic();
                    playClickSound();
                    launchGame(pokeKalyeName);
                    dispose();
                }
            }
        });

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (puspinRadioButton.isSelected() || askalRadioButton.isSelected()
                        || langgamRadioButton.isSelected()) {
                    startButton.setBackground(new Color(102, 255, 51));
                    playHoverSound();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setBackground(Color.WHITE);
            }
        });

        selectedPokeKalyeLabel = new JLabel();
        selectedPokeKalyeLabel.setForeground(Color.WHITE);
        selectedPokeKalyeLabel.setFont(new Font("Courier New", Font.BOLD, 16));
        selectedPokeKalyeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        selectedPokeKalyeLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.add(pokeKalyeChooserLabel);
        contentPane.add(puspinLabel);
        contentPane.add(askalLabel);
        contentPane.add(langgamLabel);
        contentPane.add(puspinRadioButton);
        contentPane.add(askalRadioButton);
        contentPane.add(langgamRadioButton);
        contentPane.add(startButton);
        contentPane.add(selectedPokeKalyeLabel);

        pokeKalyeChooserLabel.setBounds(100, 12, 400, 50);
        puspinLabel.setBounds(100, 90, 80, 30);
        askalLabel.setBounds(270, 90, 80, 30);
        langgamLabel.setBounds(420, 90, 80, 30);
        createImageLabel("media/images/puspin.png", 140, 140, "Puspin - A fluffy but sneaky little devil.").setBounds(
                80,
                130, 120, 140);
        createImageLabel("media/images/askal.png", 140, 140, "Askal - An unhinged creature and a companion for life.")
                .setBounds(240, 130, 140, 140);
        createImageLabel("media/images/langgam.png", 140, 140, "Langgam - Known to eliminate multiversal threats.")
                .setBounds(390, 160, 140, 140);
        puspinRadioButton.setBounds(120, 280, 20, 20);
        askalRadioButton.setBounds(290, 280, 20, 20);
        langgamRadioButton.setBounds(450, 280, 20, 20);
        startButton.setBounds(200, 322, 200, 30);
        selectedPokeKalyeLabel.setBounds(4, 340, 300, 20);

        setResizable(false);
        setVisible(true);
        playMusic("media/audio/choose.wav");
    }

    private JLabel createImageLabel(String imagePath, int width, int height, String tooltipText) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(image));
        label.setToolTipText(tooltipText);
        add(label);
        return label;
    }

    private void updatePokekalyePanel(JRadioButton selectedRadioButton) {
        puspinLabel.setForeground(Color.WHITE);
        askalLabel.setForeground(Color.WHITE);
        langgamLabel.setForeground(Color.WHITE);
        puspinLabel.setOpaque(false);
        askalLabel.setOpaque(false);
        langgamLabel.setOpaque(false);

        puspinLabel.setBackground(null);
        askalLabel.setBackground(null);
        langgamLabel.setBackground(null);

        if (selectedRadioButton == puspinRadioButton) {
            puspinLabel.setOpaque(true);
            puspinLabel.setBackground(new Color(102, 255, 51));
            puspinLabel.setForeground(Color.BLACK);
        } else if (selectedRadioButton == askalRadioButton) {
            askalLabel.setOpaque(true);
            askalLabel.setBackground(new Color(102, 255, 51));
            askalLabel.setForeground(Color.BLACK);
        } else if (selectedRadioButton == langgamRadioButton) {
            langgamLabel.setOpaque(true);
            langgamLabel.setBackground(new Color(102, 255, 51));
            langgamLabel.setForeground(Color.BLACK);
        }
    }

    private void updateSelectedPokekalyeLabel(JRadioButton selectedRadioButton) {
        stopCurrentCrySound();
        if (selectedRadioButton == puspinRadioButton) {
            selectedPokeKalyeLabel.setText("THICC AND QUICC");
            playPuspinCrySound();
        } else if (selectedRadioButton == askalRadioButton) {
            selectedPokeKalyeLabel
                    .setText("LOYAL AND UNHINGED");
            playAskalCrySound();
        } else if (selectedRadioButton == langgamRadioButton) {
            selectedPokeKalyeLabel.setText("SMALL BUT TERRIBLE");
            playLanggamCrySound();
        }
    }

    static String getSelectedPokeKalye() {
        if (puspinRadioButton.isSelected()) {
            return "Puspin";
        } else if (askalRadioButton.isSelected()) {
            return "Askal";
        } else if (langgamRadioButton.isSelected()) {
            return "Langgam";
        } else {
            return "";
        }
    }

    private static void launchGame(String pokeKalyeName) {
        Game.main(new String[] { pokeKalyeName });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                loadCrySounds();
                PokeKalyeChooser pokeKalyeChooser = new PokeKalyeChooser();
                pokeKalyeChooser.setLocationRelativeTo(null);
            }
        });
    }

    private class TooltipLabel extends JLabel {
        public TooltipLabel(String text) {
            super(text);
            setOpaque(true);
        }
    }

    private void playPuspinCrySound() {
        if (puspinCrySound != null) {
            puspinCrySound.setFramePosition(0);
            puspinCrySound.start();
            currentCrySound = puspinCrySound;
        }
    }

    private void playAskalCrySound() {
        if (askalCrySound != null) {
            askalCrySound.setFramePosition(0);
            askalCrySound.start();
            currentCrySound = askalCrySound;
        }
    }

    private void playLanggamCrySound() {
        if (langgamCrySound != null) {
            langgamCrySound.setFramePosition(0);
            langgamCrySound.start();
            currentCrySound = langgamCrySound;
        }
    }

    private void stopCurrentCrySound() {
        if (currentCrySound != null && currentCrySound.isRunning()) {
            currentCrySound.stop();
        }
    }

    private void playHoverSound() {
        try {
            File soundFile = new File("media/audio/hover.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playClickSound() {
        try {
            File soundFile = new File("media/audio/click.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadCrySounds() {
        try {
            AudioInputStream puspinStream = AudioSystem
                    .getAudioInputStream(new File("media/audio/cries/puspincry.wav"));
            puspinCrySound = AudioSystem.getClip();
            puspinCrySound.open(puspinStream);

            AudioInputStream askalStream = AudioSystem.getAudioInputStream(new File("media/audio/cries/askalcry.wav"));
            askalCrySound = AudioSystem.getClip();
            askalCrySound.open(askalStream);

            AudioInputStream langgamStream = AudioSystem
                    .getAudioInputStream(new File("media/audio/cries/langgamcry.wav"));
            langgamCrySound = AudioSystem.getClip();
            langgamCrySound.open(langgamStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
