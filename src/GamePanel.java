import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Timer;
import javax.swing.border.Border;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel {
    private int playerLevel = 1;
    private int experience;
    private JLabel playerLabel;
    private JProgressBar playerHealthBar;
    private int playerCurrentHealth;
    private int playerMaxHealth;
    private JLabel enemyLabel;
    private JProgressBar enemyHealthBar;
    private JTextArea dialogueArea;
    private JButton movesButton;
    private JButton searchButton;
    private JButton settingsButton;
    private JButton sariSariButton;
    private String selectedPokeKalye;
    private boolean inBattle;
    private String enemyPokeKalye = "";
    private int enemyMaxHealth;
    private JProgressBar playerExpBar;
    private JLabel playerLevelLabel;
    private int enemyCurrentHealth;
    private JLabel battleStatusLabel;
    private int pesos = 0;
    private Search search;
    private JLabel enemyImageLabel;
    private List<JButton> moveButtons;
    private JPanel buttonsPanel;
    private JLabel searchingLabel;
    private PokeKalyeData.PokeKalye playerData;
    private PokeKalyeData.PokeKalye enemyData;
    private boolean playerTurn = true;
    private boolean gameOver = false;
    private boolean tripleDamageNextMove = false;
    private Clip loopingMusicClip;
    private JLabel yourPokeKalyeImage;

    void showIntroScreen() {
        this.setBackground(Color.BLACK);
        JFrame introFrame = new JFrame();
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        introFrame.setUndecorated(true);
        introFrame.getContentPane().setBackground(new Color(255, 51, 102));
        introFrame.setBackground(new Color(255, 51, 102));
        introFrame.setLayout(new BorderLayout());

        JLabel introLabel = new JLabel("PokeKalye", SwingConstants.CENTER);
        introLabel.setFont(new Font("Impact", Font.BOLD, 90));
        introLabel.setForeground(Color.black);

        introFrame.add(introLabel, BorderLayout.CENTER);
        introFrame.pack();
        introFrame.setLocationRelativeTo(null);
        introFrame.setVisible(true);

        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                introFrame.dispose();
                enableButtons();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public GamePanel(String playerName, String selectedPokeKalye) {
        this.enemyPokeKalye = "";
        search = new Search(this);
        this.selectedPokeKalye = selectedPokeKalye;
        this.playerData = new PokeKalyeData.PokeKalye(selectedPokeKalye, 0, MovePool.getMoves(selectedPokeKalye));
        this.enemyData = new PokeKalyeData.PokeKalye(enemyPokeKalye, 0, MovePool.getMoves(enemyPokeKalye));
        this.enemyCurrentHealth = getMaxHealth(enemyData);
        this.playerCurrentHealth = getMaxHealth(playerData);
        search = new Search(this);

        playerMaxHealth = getMaxHealth(playerData);
        int enemyMaxHealth = getMaxHealth(enemyData);

        inBattle = false;
        moveButtons = new ArrayList<>();

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));

        JPanel battlePanel = new JPanel();
        battlePanel.setLayout(new BorderLayout());
        battlePanel.setBackground(new Color(82, 113, 255));

        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        playerPanel.setBackground(new Color(82, 113, 255));
        playerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Border blackBorder = BorderFactory.createLineBorder(Color.WHITE);

        playerLabel = new JLabel(selectedPokeKalye.toUpperCase());
        playerLabel.setForeground(Color.WHITE);
        playerHealthBar = new JProgressBar(0, playerMaxHealth);
        playerHealthBar.setForeground(new Color(102, 255, 51));
        playerHealthBar.setBorder(blackBorder);

        playerPanel.add(playerLabel);
        playerPanel.add(playerHealthBar);

        playerExpBar = new JProgressBar(0, getLevelUpExperience(playerLevel));
        playerExpBar.setForeground(Color.BLUE);
        playerExpBar.setValue(experience);
        playerExpBar.setPreferredSize(new Dimension(50, 10));

        playerLevelLabel = new JLabel("" + playerLevel);
        playerLevelLabel.setForeground(Color.WHITE);
        playerLevelLabel.setFont(new Font("Impact", Font.BOLD, 15));

        playerPanel.add(playerExpBar);
        playerPanel.add(playerLevelLabel);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(null);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        enemyImageLabel = new JLabel(new ImageIcon("images/empty.png"));
        enemyImageLabel.setBounds(240, 120, 100, 100);
        imagePanel.add(enemyImageLabel);

        yourPokeKalyeImage = new JLabel(new ImageIcon("media/images/" + selectedPokeKalye + "User.png"));
        yourPokeKalyeImage.setBounds(70, 10, 100, 100);

        imagePanel.add(yourPokeKalyeImage);

        JPanel enemyPanel = new JPanel();
        enemyPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        enemyPanel.setBackground(new Color(82, 113, 255));
        enemyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        enemyHealthBar = new JProgressBar(0, enemyMaxHealth);
        enemyHealthBar.setBorder(blackBorder);
        enemyLabel = new JLabel(enemyData.getName().toUpperCase());
        enemyLabel.setForeground(Color.WHITE);

        searchingLabel = new JLabel("Searching...");
        searchingLabel.setForeground(Color.WHITE);
        searchingLabel.setVisible(false);

        enemyPanel.add(searchingLabel);
        enemyPanel.setBackground(new Color(255, 51, 102));
        enemyPanel.add(enemyHealthBar);
        enemyHealthBar.setVisible(inBattle);
        enemyPanel.add(enemyLabel);

        battlePanel.add(playerPanel, BorderLayout.NORTH);
        battlePanel.add(imagePanel, BorderLayout.CENTER);
        battlePanel.add(enemyPanel, BorderLayout.SOUTH);

        dialogueArea = new JTextArea(" What will " + selectedPokeKalye + " do?");
        dialogueArea.setEditable(false);
        dialogueArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane dialogueScrollPane = new JScrollPane(dialogueArea);
        dialogueScrollPane.setPreferredSize(new Dimension(170, getHeight()));

        this.buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 4, 10, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonsPanel.setBackground(Color.DARK_GRAY);

        movesButton = new JButton("Moves");
        searchButton = new JButton("Search");
        sariSariButton = new JButton("Sari-Sari");
        settingsButton = new JButton("Settings");

        movesButton.setBackground(Color.WHITE);
        searchButton.setBackground(Color.WHITE);
        settingsButton.setBackground(Color.WHITE);
        sariSariButton.setBackground(Color.WHITE);

        searchButton.setEnabled(false);
        settingsButton.setEnabled(false);
        sariSariButton.setEnabled(false);

        movesButton.setEnabled(inBattle);

        updateHealthBars();

        MouseListener buttonMouseListener = new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JButton button = (JButton) evt.getSource();
                if (button.isEnabled()) {
                    button.setBackground(new Color(102, 255, 51));
                    playHoverSound();
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                JButton button = (JButton) evt.getSource();
                button.setBackground(Color.WHITE);
            }
        };

        movesButton.addMouseListener(buttonMouseListener);
        searchButton.addMouseListener(buttonMouseListener);
        sariSariButton.addMouseListener(buttonMouseListener);
        settingsButton.addMouseListener(buttonMouseListener);

        buttonsPanel.add(movesButton);
        buttonsPanel.add(searchButton);
        buttonsPanel.add(sariSariButton);
        buttonsPanel.add(settingsButton);

        add(battlePanel, BorderLayout.CENTER);
        add(dialogueScrollPane, BorderLayout.WEST);
        add(buttonsPanel, BorderLayout.SOUTH);

        movesButton.addActionListener(e -> {
            showMovesDialog();
            playButtonClickSound();
        });
        searchButton.addActionListener(e -> {
            search.performSearch();
            playButtonClickSound();
        });
        settingsButton.addActionListener(e -> {
            openSettingsPanel();
            playButtonClickSound();
        });
        sariSariButton.addActionListener(e -> {
            openShopPanel();
            playButtonClickSound();
        });
        buttonsPanel.revalidate();
        buttonsPanel.repaint();
        showIntroScreen();
    }

    public int getLevel() {
        return playerLevel;
    }

    public int getPesos() {
        return pesos;
    }

    public void setPesos(int pesos) {
        this.pesos = pesos;
    }

    private void checkBattleResult() {
        if (enemyCurrentHealth <= 0) {
            playEnemyDefeatedSound();
            experience += getRandomNumber(45, 75);
            int levelUpExp = getLevelUpExperience(playerLevel);
            if (experience >= levelUpExp) {
                playerLevel++;
                experience -= levelUpExp;
                playLevelUpSound();
                evolvePokeKalye();
            }
            int earnedPesos = getRandomNumber(1, 10);
            pesos += earnedPesos;
            playerExpBar.setMaximum(getLevelUpExperience(playerLevel));
            playerExpBar.setValue(experience);
            playerLevelLabel.setText("" + playerLevel);
            Timer timer = new Timer(50, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    appendToDialogue("Prof RP caught the\n " + enemyPokeKalye + " for you!");
                    setInBattle(false);
                    searchButton.setEnabled(true);
                    restoreButtons();
                }
            });
            timer.setRepeats(false);
            timer.start();
            clearDialogue();
        } else if (playerCurrentHealth <= 0 && !gameOver) {
            fadeOutBattleMusic();
            gameOver = true;
            GameOverPanel gameOverScreen = new GameOverPanel();
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            currentFrame.getContentPane().removeAll();
            currentFrame.setContentPane(gameOverScreen);
            currentFrame.pack();
            currentFrame.revalidate();
            currentFrame.repaint();
        }
    }

    private void showMovesDialog() {
        buttonsPanel.removeAll();
        moveButtons.clear();

        MovePool.Move[] moves = playerData.getMoves();
        int numMoves = 2;

        if (playerLevel >= 5) {
            numMoves = 3;
        }

        if (playerLevel >= 10) {
            numMoves = 4;
        }

        int actualNumMoves = Math.min(numMoves, moves.length);

        for (int i = 0; i < actualNumMoves; i++) {
            MovePool.Move move = moves[i];
            JButton moveButton = new JButton(move.getName());
            moveButton.setBackground(Color.WHITE);

            moveButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    moveButton.setBackground(new Color(102, 255, 51));
                    playHoverSound();
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    moveButton.setBackground(Color.WHITE);
                }
            });

            moveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    playButtonClickSound();
                    if (playerTurn) {
                        performMove(move);
                    }
                }
            });

            moveButtons.add(moveButton);
            buttonsPanel.add(moveButton);
        }

        buttonsPanel.revalidate();
        buttonsPanel.repaint();
    }

    public void enableSearchButton(boolean enable) {
        SwingUtilities.invokeLater(() -> searchButton.setEnabled(enable));
    }

    private void openShopPanel() {
        if (loopingMusicClip != null && loopingMusicClip.isRunning()) {
            loopingMusicClip.stop();
        }
        ShopPanel shopPanel = new ShopPanel(this);
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        currentFrame.getContentPane().removeAll();
        currentFrame.setContentPane(shopPanel);
        currentFrame.pack();
        currentFrame.revalidate();
        currentFrame.repaint();
    }

    public void goBackToMainPanel() {
        ((CardLayout) getParent().getLayout()).show(getParent(), "GamePanel");
    }

    public void setEnemyImage(String enemyPokeKalye) {
        this.enemyPokeKalye = enemyPokeKalye;
        System.out.println(enemyPokeKalye);
        initializeEnemyData(enemyPokeKalye);
        setEnemyData(enemyData);
        updateHealthBars();
        enemyLabel.setText(enemyData.getName().toUpperCase());
        ImageIcon enemyImage = new ImageIcon("media/images/" + enemyPokeKalye + "2.png");
        enemyImageLabel.setIcon(enemyImage);
        setInBattle(true);
        System.out.println("Enemy HP: " + enemyCurrentHealth + "/" + enemyMaxHealth);
        playBattleMusic();
    }

    public void setDialogueText(String dialogue) {
        dialogueArea.setText(dialogue);
    }

    public void displayBattleResult(String result) {
        battleStatusLabel.setText(result);
    }

    public String getPokeKalyeName() {
        return selectedPokeKalye;
    }

    public void enableSearchButton() {
        searchButton.setEnabled(true);
    }

    public boolean isInBattle() {
        return inBattle;

    }

    public void setInBattle(boolean inBattle) {
        this.inBattle = inBattle;
        if (inBattle) {
            initializeEnemyData(enemyPokeKalye);
        } else {
            enemyData = null;
        }
        movesButton.setEnabled(inBattle);
        enemyLabel.setVisible(inBattle);
        enemyHealthBar.setVisible(inBattle);
        enemyImageLabel.setVisible(inBattle);
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    private void enableButtons() {
        searchButton.setEnabled(true);
        settingsButton.setEnabled(true);
        sariSariButton.setEnabled(true);
    }

    private void openSettingsPanel() {
        SettingsPanel settingsPanel = new SettingsPanel();
        settingsPanel.setVisible(true);
    }

    public void setSearchLabelText(String text) {
        searchingLabel.setText(text);
    }

    public void showSearchingLabel(boolean show) {
        searchingLabel.setVisible(show);
    }

    public void setEnemyData(PokeKalyeData.PokeKalye enemyData) {
        this.enemyData = enemyData;
        this.enemyMaxHealth = getMaxHealth(enemyData);
        this.enemyCurrentHealth = enemyMaxHealth;
        enemyHealthBar.setMaximum(enemyMaxHealth);
        enemyHealthBar.setValue(enemyCurrentHealth);
    }

    public int getEnemyCurrentHealth() {
        return enemyData.getCurrentHealth();
    }

    public int getPlayerCurrentHealth() {
        return playerCurrentHealth;
    }

    public void setPlayerCurrentHealth(int health) {
        playerCurrentHealth = health;
    }

    private int getMaxHealth(PokeKalyeData.PokeKalye pokekalye) {
        int maxHealth = 0;

        if (pokekalye != null) {
            switch (pokekalye.getName()) {
                case "Kuting":
                    maxHealth = PokeKalyeData.KUTING.getMaxHealth();
                    break;
                case "Puspin":
                    maxHealth = PokeKalyeData.PUSPIN.getMaxHealth();
                    break;
                case "Puspin Boots":
                    maxHealth = PokeKalyeData.PUSPIN_BOOTS.getMaxHealth();
                    break;
                case "Tuta":
                    maxHealth = PokeKalyeData.TUTA.getMaxHealth();
                    break;
                case "Askal":
                    maxHealth = PokeKalyeData.ASKAL.getMaxHealth();
                    break;
                case "Big Dog":
                    maxHealth = PokeKalyeData.BIG_DOG.getMaxHealth();
                    break;
                case "Langgam":
                    maxHealth = PokeKalyeData.LANGGAM.getMaxHealth();
                    break;
                case "Antik":
                    maxHealth = PokeKalyeData.ANTIK.getMaxHealth();
                    break;
                case "Ant-Man":
                    maxHealth = PokeKalyeData.ANTMAN.getMaxHealth();
                    break;
                case "Ipis":
                    maxHealth = PokeKalyeData.IPIS.getMaxHealth();
                    break;
                case "Flying Ipis":
                    maxHealth = PokeKalyeData.FLYING_IPIS.getMaxHealth();
                    break;
                case "Daga":
                    maxHealth = PokeKalyeData.DAGA.getMaxHealth();
                    break;
                case "Lamok":
                    maxHealth = PokeKalyeData.LAMOK.getMaxHealth();
                    break;
                case "Butiki":
                    maxHealth = PokeKalyeData.BUTIKI.getMaxHealth();
                    break;
                case "Ibon":
                    maxHealth = PokeKalyeData.IBON.getMaxHealth();
                    break;
                case "Colored Sisiw":
                    maxHealth = PokeKalyeData.COLORED_SISIW.getMaxHealth();
                    break;
                case "Salagubang":
                    maxHealth = PokeKalyeData.SALAGUBANG.getMaxHealth();
                    break;
                case "Dagang Kanal":
                    maxHealth = PokeKalyeData.DAGANG_KANAL.getMaxHealth();
                    break;
                case "Langaw":
                    maxHealth = PokeKalyeData.LANGAW.getMaxHealth();
                    break;
                case "Manok":
                    maxHealth = PokeKalyeData.MANOK.getMaxHealth();
                    break;
                case "Gagamba":
                    maxHealth = PokeKalyeData.GAGAMBA.getMaxHealth();
                    break;
                case "Tuko":
                    maxHealth = PokeKalyeData.TUKO.getMaxHealth();
                    break;
                case "Bangaw":
                    maxHealth = PokeKalyeData.BANGAW.getMaxHealth();
                    break;
                case "Paniki":
                    maxHealth = PokeKalyeData.PANIKI.getMaxHealth();
                    break;
                case "Palaka":
                    maxHealth = PokeKalyeData.PALAKA.getMaxHealth();
                    break;
                case "Kuto":
                    maxHealth = PokeKalyeData.KUTO.getMaxHealth();
                    break;
                case "Tutubi":
                    maxHealth = PokeKalyeData.TUTUBI.getMaxHealth();
                    break;
                case "Ahas":
                    maxHealth = PokeKalyeData.AHAS.getMaxHealth();
                    break;
                case "Paro-paro":
                    maxHealth = PokeKalyeData.PARO_PARO.getMaxHealth();
                    break;
                case "Higad":
                    maxHealth = PokeKalyeData.HIGAD.getMaxHealth();
                    break;
                case "Tipaklong":
                    maxHealth = PokeKalyeData.TIPAKLONG.getMaxHealth();
                    break;
                case "Kabayo":
                    maxHealth = PokeKalyeData.KABAYO.getMaxHealth();
                    break;
                case "Master Splinter":
                    maxHealth = PokeKalyeData.MASTER_SPLINTER.getMaxHealth();
                    break;
                default:
                    break;
            }
        }
        return maxHealth;
    }

    private void initializeEnemyData(String enemyPokeKalye) {
        switch (enemyPokeKalye) {
            case "Puspin":
                enemyData = PokeKalyeData.PUSPIN;
                break;
            case "Askal":
                enemyData = PokeKalyeData.ASKAL;
                break;
            case "Langgam":
                enemyData = PokeKalyeData.LANGGAM;
                break;
            case "Ipis":
                enemyData = PokeKalyeData.IPIS;
                break;
            case "Flying Ipis":
                enemyData = PokeKalyeData.FLYING_IPIS;
                break;
            case "Daga":
                enemyData = PokeKalyeData.DAGA;
                break;
            case "Lamok":
                enemyData = PokeKalyeData.LAMOK;
                break;
            case "Butiki":
                enemyData = PokeKalyeData.BUTIKI;
                break;
            case "Ibon":
                enemyData = PokeKalyeData.IBON;
                break;
            case "Salagubang":
                enemyData = PokeKalyeData.SALAGUBANG;
                break;
            case "Dagang Kanal":
                enemyData = PokeKalyeData.DAGANG_KANAL;
                break;
            case "Langaw":
                enemyData = PokeKalyeData.LANGAW;
                break;
            case "Master Splinter":
                enemyData = PokeKalyeData.MASTER_SPLINTER;
                break;
            case "Kuting":
                enemyData = PokeKalyeData.KUTING;
                break;
            case "Puspin Boots":
                enemyData = PokeKalyeData.PUSPIN_BOOTS;
                break;
            case "Tuta":
                enemyData = PokeKalyeData.TUTA;
                break;
            case "Big Dog":
                enemyData = PokeKalyeData.BIG_DOG;
                break;
            case "Antik":
                enemyData = PokeKalyeData.ANTIK;
                break;
            case "Ant-Man":
                enemyData = PokeKalyeData.ANTMAN;
                break;
            case "Colored Sisiw":
                enemyData = PokeKalyeData.COLORED_SISIW;
                break;
            case "Manok":
                enemyData = PokeKalyeData.MANOK;
                break;
            case "Gagamba":
                enemyData = PokeKalyeData.GAGAMBA;
                break;
            case "Tuko":
                enemyData = PokeKalyeData.TUKO;
                break;
            case "Bangaw":
                enemyData = PokeKalyeData.BANGAW;
                break;
            case "Paniki":
                enemyData = PokeKalyeData.PANIKI;
                break;
            case "Palaka":
                enemyData = PokeKalyeData.PALAKA;
                break;
            case "Kuto":
                enemyData = PokeKalyeData.KUTO;
                break;
            case "Tutubi":
                enemyData = PokeKalyeData.TUTUBI;
                break;
            case "Ahas":
                enemyData = PokeKalyeData.AHAS;
                break;
            case "Paro-paro":
                enemyData = PokeKalyeData.PARO_PARO;
                break;
            case "Higad":
                enemyData = PokeKalyeData.HIGAD;
                break;
            case "Tipaklong":
                enemyData = PokeKalyeData.TIPAKLONG;
                break;
            case "Kabayo":
                enemyData = PokeKalyeData.KABAYO;
                break;
            default:
                enemyData = null;
        }
    }

    void updateHealthBars() {
        int playerMaxHealth = getMaxHealth(playerData);
        int playerCurrentHealth = this.playerCurrentHealth;
        int enemyMaxHealth = getMaxHealth(enemyData);
        int enemyCurrentHealth = this.enemyCurrentHealth;

        animateHealthBar(playerHealthBar, playerCurrentHealth, playerMaxHealth, 300);
        animateHealthBar(enemyHealthBar, enemyCurrentHealth, enemyMaxHealth, 300);

        System.out.println("Enemy HP: " + enemyCurrentHealth + "/" + enemyMaxHealth);
        System.out.println("Player HP: " + playerCurrentHealth + "/" + playerMaxHealth);

        this.playerCurrentHealth = playerCurrentHealth;
        this.enemyCurrentHealth = enemyCurrentHealth;
    }

    private void animateHealthBar(JProgressBar healthBar, int currentHealth, int maxHealth, int duration) {
        int startValue = healthBar.getValue();
        int endValue = currentHealth;
        int totalFrames = duration / 60;
        double increment = (double) (endValue - startValue) / totalFrames;

        int delay = duration / totalFrames;

        Timer timer = new Timer(delay, new ActionListener() {
            int frameCount = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                double value = startValue + increment * frameCount;
                healthBar.setValue((int) value);
                updateBarColor(healthBar);

                frameCount++;
                if (frameCount >= totalFrames) {
                    healthBar.setValue(endValue);
                    ((Timer) e.getSource()).stop();
                    SwingUtilities.invokeLater(() -> {
                        updateBarColor(healthBar);
                        healthBar.repaint();
                    });
                } else {
                    healthBar.repaint();
                }
            }
        });

        timer.setInitialDelay(0);
        timer.start();
    }

    private void updateBarColor(JProgressBar healthBar) {
        int maxHealth = healthBar.getMaximum();
        int currentHealth = healthBar.getValue();
        double healthPercentage = (double) currentHealth / maxHealth;
        playerData.getMaxHealth();

        if (getMaxHealth(playerData) < getPlayerCurrentHealth()) {
            playerHealthBar.setForeground(new Color(255, 82, 200)); // Boosted HP color
        }
        if (healthPercentage >= 0.6) {
            healthBar.setForeground(new Color(102, 255, 51)); // Green
        } else if (healthPercentage >= 0.3) {
            healthBar.setForeground(new Color(255, 224, 82)); // Yellow
        } else {
            healthBar.setForeground(Color.RED); // Red
        }
    }

    private void performMove(MovePool.Move move) {
        clearDialogue();
        String moveName = move.getName();

        if (moveName.equals("Flee")) {
            fadeOutBattleMusic();
            setInBattle(false);
            searchButton.setEnabled(true);
            restoreButtons();
            appendToDialogue(selectedPokeKalye + " fled from\n the battle!");
            return;
        }

        double chance = move.getChance();

        if (moveName.equals("Burrow")) {
            if (Math.random() <= chance) {
                int damage = move.getDamage();
                if (tripleDamageNextMove) {
                    damage *= 3;
                    tripleDamageNextMove = false;
                }
                enemyCurrentHealth -= damage;
                updateHealthBars();
                int maxPlayerHealth = getMaxHealth(playerData);
                if (playerCurrentHealth + 10 > maxPlayerHealth) {
                    playerCurrentHealth = maxPlayerHealth;
                    updateHealthBars();
                } else {
                    playerCurrentHealth += 10;
                    updateHealthBars();
                }
                appendToDialogue(selectedPokeKalye + " used " + moveName + "!");
                System.out.println("Enemy HP: " + enemyCurrentHealth + "/" + enemyMaxHealth);
                System.out.println("Player HP: " + playerCurrentHealth + "/" + getMaxHealth(playerData));
            } else {
                appendToDialogue("Burrow failed!");
            }
        } else if (moveName.equals("Tahol")) {
            tripleDamageNextMove = true;
            appendToDialogue(selectedPokeKalye + " used " + moveName + "!");
        } else {
            int damage = move.getDamage();
            if (tripleDamageNextMove) {
                damage *= 3;
                tripleDamageNextMove = false;
            }
            if (Math.random() <= chance) {
                enemyCurrentHealth -= damage;
                updateHealthBars();
                appendToDialogue(selectedPokeKalye + " used " + moveName + "!");
                System.out.println("Enemy HP: " + enemyCurrentHealth + "/" + enemyMaxHealth);
            } else {
                appendToDialogue(selectedPokeKalye + " missed!");
            }
        }

        setMoveButtonsEnabled(false);
        Timer timer = new Timer(1800, e -> {
            enemyTurn();
            checkBattleResult();
            setMoveButtonsEnabled(true);
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void enemyTurn() {
        if (enemyCurrentHealth > 0) {
            MovePool.Move[] enemyMoves = enemyData.getMoves();
            int randomIndex = (int) (Math.random() * enemyMoves.length);
            MovePool.Move enemyMove = enemyMoves[randomIndex];
            String enemyMoveName = enemyMove.getName();

            if (enemyMoveName.equals("Flee")) {
                fadeOutBattleMusic();
                clearDialogue();
                setInBattle(false);
                searchButton.setEnabled(true);
                restoreButtons();
                appendToDialogue(enemyPokeKalye + " fled from\n the battle!");
                return;
            }

            double chance = enemyMove.getChance();

            if (enemyMoveName.equals("Burrow")) {
                if (Math.random() <= chance) {
                    // Move hits
                    int damage = enemyMove.getDamage();
                    playerCurrentHealth -= damage;
                    dialogueArea.append("\n " + enemyPokeKalye + " used " + enemyMoveName + "!");
                    updateHealthBars();
                    System.out.println("Player HP: " + playerCurrentHealth + "/" + getMaxHealth(playerData));
                } else {
                    dialogueArea.append("\n Burrow failed!");
                }
            } else {
                int damage = enemyMove.getDamage();
                if (Math.random() <= chance) {
                    playerCurrentHealth -= damage;
                    dialogueArea.append("\n " + enemyPokeKalye + " used " + enemyMoveName + "!");
                    updateHealthBars();
                    System.out.println("Player HP: " + playerCurrentHealth + "/" + getMaxHealth(playerData));
                } else {
                    dialogueArea.append("\n " + enemyPokeKalye + " missed!");
                }
            }
        }

        if (enemyCurrentHealth <= 0) {
            inBattle = false;
            fadeOutBattleMusic();
        }
    }

    private void setMoveButtonsEnabled(boolean enabled) {
        for (JButton moveButton : moveButtons) {
            moveButton.setEnabled(enabled);
        }
    }

    void restoreButtons() {
        buttonsPanel.removeAll();
        moveButtons.clear();

        movesButton.setEnabled(inBattle);
        searchButton.setEnabled(true);
        settingsButton.setEnabled(true);
        sariSariButton.setEnabled(true);

        movesButton.setBackground(Color.WHITE);
        searchButton.setBackground(Color.WHITE);
        settingsButton.setBackground(Color.WHITE);
        sariSariButton.setBackground(Color.WHITE);

        buttonsPanel.add(movesButton);
        buttonsPanel.add(searchButton);
        buttonsPanel.add(sariSariButton);
        buttonsPanel.add(settingsButton);

        buttonsPanel.revalidate();
        buttonsPanel.repaint();
    }

    private int getLevelUpExperience(int level) {
        return 100 + (level - 1) * 35;
    }

    private Random random = new Random();

    private int getRandomNumber(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private void appendToDialogue(String text) {
        dialogueArea.append("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n " + text);
        dialogueArea.setCaretPosition(dialogueArea.getDocument().getLength());
    }

    private void clearDialogue() {
        dialogueArea.setText(" What will " + selectedPokeKalye + " do?");
    }

    private void playBattleMusic() {
        try {
            File musicFile = new File("media/audio/battle.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            loopingMusicClip = AudioSystem.getClip();
            loopingMusicClip.open(audioStream);
            loopingMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    private void fadeOutBattleMusic() {
        if (loopingMusicClip != null && loopingMusicClip.isRunning()) {
            FloatControl gainControl = (FloatControl) loopingMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
            float initialVolume = gainControl.getValue();
            float volume = initialVolume;
            float fadeOutSpeed = 0.5f;

            while (volume > -80.0f) {
                volume -= fadeOutSpeed;
                if (volume < -80.0f) {
                    volume = -80.0f;
                }
                gainControl.setValue(volume);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            loopingMusicClip.stop();
            gainControl.setValue(initialVolume);
        }
    }

    private void playHoverSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("media/audio/hover.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playButtonClickSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("media/audio/click.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void playEnemyDefeatedSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("media/audio/ko.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playLevelUpSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("media/audio/levelup.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void evolvePokeKalye() {
        if (playerLevel == 5) {
            if (selectedPokeKalye.equals("Langgam")) {
                selectedPokeKalye = "Antik";
                this.playerData = new PokeKalyeData.PokeKalye(selectedPokeKalye, 30,
                        MovePool.getMoves(selectedPokeKalye));
                playerMaxHealth = getMaxHealth(this.playerData);
                playerLabel.setText("ANTIK");
                yourPokeKalyeImage.setIcon(new ImageIcon("media/images/AntikUser.png"));
            }
        } else if (playerLevel == 11) {
            if (selectedPokeKalye.equals("Antik")) {
                selectedPokeKalye = "Ant-Man";
                this.playerData = new PokeKalyeData.PokeKalye(selectedPokeKalye, 0,
                        MovePool.getMoves(selectedPokeKalye));
                playerMaxHealth = getMaxHealth(this.playerData);
                playerLabel.setText(selectedPokeKalye.toUpperCase());
                yourPokeKalyeImage.setIcon(new ImageIcon("media/images/AntManUser.png"));
            } else if (selectedPokeKalye.equals("Puspin")) {
                selectedPokeKalye = "Puspin Boots";
                this.playerData = new PokeKalyeData.PokeKalye(selectedPokeKalye, 0,
                        MovePool.getMoves(selectedPokeKalye));
                playerMaxHealth = getMaxHealth(this.playerData);
                playerLabel.setText(selectedPokeKalye.toUpperCase());
                yourPokeKalyeImage.setIcon(new ImageIcon("media/images/PuspinBootsUser.png"));
            } else if (selectedPokeKalye.equals("Askal")) {
                selectedPokeKalye = "Big Dog";
                this.playerData = new PokeKalyeData.PokeKalye(selectedPokeKalye, 0,
                        MovePool.getMoves(selectedPokeKalye));
                playerMaxHealth = getMaxHealth(this.playerData);
                playerLabel.setText(selectedPokeKalye.toUpperCase());
                yourPokeKalyeImage.setIcon(new ImageIcon("media/images/BigDogUser.png"));
            }
        }
    }
}
