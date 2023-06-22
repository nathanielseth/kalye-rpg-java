import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.File;
import java.io.IOException;

import javax.swing.border.Border;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GamePanel extends JPanel {
    public int playerLevel = 1;
    public int experience;
    private JLabel playerLabel;
    private JPanel playerPanel;
    private JPanel imagePanel;
    JProgressBar playerHealthBar;
    private int playerCurrentHealth;
    private int playerMaxHealth;
    private JLabel enemyLabel;
    private JProgressBar enemyHealthBar;
    private JTextArea dialogueArea;
    private JButton movesButton;
    JButton searchButton;
    private JButton areasButton;
    JButton sariSariButton;
    private String selectedPokeKalye;
    private String pokeKalyeName;
    private boolean inBattle;
    private String enemyPokeKalye = "";
    private int enemyMaxHealth;
    JProgressBar playerExpBar;
    JLabel playerLevelLabel;
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
    private boolean taholDmgBoost = false;
    private JLabel yourPokeKalyeImage;
    private Clip loopingMusicClip;
    private static Clip clickSoundClip;
    private static Clip koSoundClip;
    private static Clip levelUpSoundClip;
    private static Clip fleeSoundClip;
    private static Clip reviveSoundClip;
    private static Clip lowHitSoundClip;
    private static Clip medHitSoundClip;
    private static Clip highHitSoundClip;
    private static Clip burrowSoundClip;
    private static Clip missSoundClip;
    private static Clip barkSoundClip;
    private static Clip hissSoundClip;
    private static Clip dengueSoundClip;
    private static Clip introSoundClip;
    private boolean hasRabies = false;
    private boolean rabiesVaccinated = false;
    private boolean rugbied;
    private double damageMultiplier = 1.0;
    private JLabel yourPokeKalyeDamageLabel;
    private JLabel enemyDamageLabel;
    private boolean hasDengue = false;
    private Timer dengueTimer;
    private int dengueDamage = 1;
    private int dengueInterval = 10000;
    private long dengueStartTime;
    private boolean purrOnCooldown = false;
    private int purrCooldown = 0;
    private double critRateModifier = 0.0;
    private int critDamageModifier = 0;
    private int earnedPesosMaxValue = 10;
    private int luck = 1;
    private JLabel luckyCatButton;
    private boolean isShopOpen = false;
    private ImageIcon emptyIcon = new ImageIcon("");
    private ImageIcon dengueIcon = new ImageIcon("media/images/dengue.png");
    private List<String> usedDialogues = new ArrayList<>();
    private int enemyMeditateCooldown = 0;
    private long startTime;
    private boolean gameCompleted = false;
    private int enemyDamageReductionCounter;
    private double enemyDamageReductionPercentage;
    String area = "Kalsada Central";
    private boolean boughtWaterBowl = false;
    private boolean boughtVitamins = false;
    private boolean boughtVest = false;
    private boolean boughtBike = false;
    private boolean boughtJersey = false;
    private int playerExtraLives;
    private Set<String> caughtPokeKalyes;
    private int defeatedEnemiesCount;
    private JButton kalsadaCentralButton;
    private JButton kalyeWestButton;
    private JButton gedliEastButton;
    private JButton professorsLabButton;

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
        if (selectedPokeKalye.equals("Langgam")) {
            catchPokeKalye("Puspin");
            catchPokeKalye("Askal");
        } else if (selectedPokeKalye.equals("Puspin")) {
            catchPokeKalye("Langgam");
            catchPokeKalye("Askal");
        } else if (selectedPokeKalye.equals("Askal")) {
            catchPokeKalye("Puspin");
            catchPokeKalye("Langgam");
        }
    }

    public GamePanel(String selectedPokeKalye, String pokeKalyeName) {
        this.enemyPokeKalye = "";
        search = new Search(this);
        this.pokeKalyeName = pokeKalyeName;
        this.selectedPokeKalye = selectedPokeKalye;
        this.playerData = new PokeKalyeData.PokeKalye(selectedPokeKalye, 0, MovePool.getMoves(selectedPokeKalye));
        this.enemyData = new PokeKalyeData.PokeKalye(enemyPokeKalye, 0, MovePool.getMoves(enemyPokeKalye));
        this.enemyCurrentHealth = getMaxHealth(enemyData);
        this.playerCurrentHealth = getMaxHealth(playerData);
        search = new Search(this);
        caughtPokeKalyes = new HashSet<>();
        preloadSounds();
        showIntroScreen();
        startTime = System.currentTimeMillis();

        playerMaxHealth = getMaxHealth(playerData);
        int enemyMaxHealth = getMaxHealth(enemyData);

        inBattle = false;
        moveButtons = new ArrayList<>();

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(700, 500));

        JPanel battlePanel = new JPanel();
        battlePanel.setLayout(new BorderLayout());
        battlePanel.setBackground(new Color(82, 113, 255));

        playerPanel = new JPanel();
        playerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        playerPanel.setBackground(new Color(82, 113, 255));
        playerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Border blackBorder = BorderFactory.createLineBorder(Color.WHITE);

        playerLabel = new JLabel(pokeKalyeName.toUpperCase());
        playerLabel.setForeground(Color.WHITE);
        playerHealthBar = new JProgressBar(0, playerMaxHealth);
        playerHealthBar.setForeground(new Color(102, 255, 51));
        playerHealthBar.setBorder(blackBorder);
        playerHealthBar.setPreferredSize(new Dimension(140, 16));

        playerPanel.add(playerLabel);
        playerPanel.add(playerHealthBar);

        playerExpBar = new JProgressBar(0, getLevelUpExperience(playerLevel));
        playerExpBar.setForeground(Color.BLUE);
        playerExpBar.setValue(experience);
        playerExpBar.setPreferredSize(new Dimension(50, 10));

        playerLevelLabel = new JLabel("LVL " + playerLevel);
        playerLevelLabel.setForeground(Color.WHITE);
        playerLevelLabel.setFont(new Font("Impact", Font.PLAIN, 12));
        ImageIcon rabiesIcon = new ImageIcon("empty.png");
        JLabel rabiesLabel = new JLabel(rabiesIcon);
        JLabel emptyLabel = new JLabel(emptyIcon);
        playerPanel.add(playerExpBar);
        playerPanel.add(playerLevelLabel);
        playerPanel.add(rabiesLabel);
        playerPanel.add(emptyLabel);

        imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g.setColor(new Color(240, 221, 226));
                g.fillRect(0, 0, getWidth(), getHeight());
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            }
        };
        imagePanel.setLayout(null);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        enemyImageLabel = new JLabel(new ImageIcon("images/empty.png"));
        enemyImageLabel.setBounds(240, 100, 100, 150);
        imagePanel.add(enemyImageLabel);

        yourPokeKalyeImage = new JLabel(new ImageIcon("media/images/" + selectedPokeKalye + "User.png"));
        yourPokeKalyeImage.setBounds(60, 10, 100, 100);
        imagePanel.add(yourPokeKalyeImage);

        yourPokeKalyeDamageLabel = new JLabel();
        yourPokeKalyeDamageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        yourPokeKalyeDamageLabel.setVerticalAlignment(SwingConstants.CENTER);
        yourPokeKalyeDamageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        yourPokeKalyeDamageLabel.setForeground(Color.RED);
        yourPokeKalyeDamageLabel.setBounds(120, 5, 100, 100);
        yourPokeKalyeDamageLabel.setVisible(false);
        imagePanel.add(yourPokeKalyeDamageLabel);

        enemyDamageLabel = new JLabel();
        enemyDamageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        enemyDamageLabel.setVerticalAlignment(SwingConstants.CENTER);
        enemyDamageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        enemyDamageLabel.setForeground(Color.RED);
        enemyDamageLabel.setBounds(280, 80, 100,
                100);
        enemyDamageLabel.setVisible(false);
        imagePanel.add(enemyDamageLabel);

        JPanel enemyPanel = new JPanel();
        enemyPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        enemyPanel.setBackground(new Color(82, 113, 255));
        enemyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        enemyHealthBar = new JProgressBar(0, enemyMaxHealth);
        enemyHealthBar.setBorder(blackBorder);
        enemyHealthBar.setPreferredSize(new Dimension(140, 16));

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

        luckyCatButton = new JLabel(new ImageIcon("media/images/lucky.gif"));
        luckyCatButton.setVisible(false);
        luckyCatButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                luckyCatButton.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                luckyCatButton.setBorder(null);
            }
        });

        enemyPanel.add(luckyCatButton);

        battlePanel.add(playerPanel, BorderLayout.NORTH);
        battlePanel.add(imagePanel, BorderLayout.CENTER);
        battlePanel.add(enemyPanel, BorderLayout.SOUTH);

        dialogueArea = new JTextArea(" What will " + pokeKalyeName + " do?");
        dialogueArea.setEditable(false);
        dialogueArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        dialogueArea.setBackground(new Color(240, 221, 226));

        JScrollPane dialogueScrollPane = new JScrollPane(dialogueArea);
        dialogueScrollPane.setPreferredSize(new Dimension(170, getHeight()));
        // dialogueScrollPane.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4,
        // Color.WHITE));

        this.buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 4, 10, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonsPanel.setBackground(new Color(82, 113, 255));

        movesButton = new JButton("Moves");
        searchButton = new JButton("Search");
        sariSariButton = new JButton("Sari-Sari");
        areasButton = new JButton("Areas");

        kalsadaCentralButton = new JButton("Kalsada Central");
        kalyeWestButton = new JButton("Kalye West");
        gedliEastButton = new JButton("Gedli East");
        professorsLabButton = new JButton("Professor's Lab");

        if (getArea().equals("Kalye West")) {
            sariSariButton.setText("Pet Shop");
        } else if (getArea().equals("Gedli East")) {
            sariSariButton.setText("Ukay-Ukay");
        } else if (getArea().equals("Professor's Lab")) {
            sariSariButton.setText("Laboratory");
        } else {
            sariSariButton.setText("Sari-Sari");
        }

        movesButton.setBackground(Color.WHITE);
        searchButton.setBackground(Color.WHITE);
        sariSariButton.setBackground(Color.WHITE);
        areasButton.setBackground(Color.WHITE);

        searchButton.setEnabled(false);
        sariSariButton.setEnabled(false);
        areasButton.setEnabled(false);

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

        luckyCatButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playLuckyCatSound();
                int pesoAmount = luckyRandom(1, 30);
                increasePesos(pesoAmount);
                luckyCatButton.setVisible(false);
                if (Math.random() < 0.69) {
                    int healthIncrease = luckyRandom(1, 30);
                    if (getPlayerCurrentHealth() < getPlayerMaxHealth()) {
                        setPlayerCurrentHealth(getPlayerCurrentHealth() + healthIncrease);
                    }
                    updateHealthBars();
                }
            }
        });

        movesButton.addMouseListener(buttonMouseListener);
        searchButton.addMouseListener(buttonMouseListener);
        sariSariButton.addMouseListener(buttonMouseListener);
        areasButton.addMouseListener(buttonMouseListener);

        buttonsPanel.add(movesButton);
        buttonsPanel.add(searchButton);
        buttonsPanel.add(sariSariButton);
        buttonsPanel.add(areasButton);

        add(battlePanel, BorderLayout.CENTER);
        add(dialogueScrollPane, BorderLayout.WEST);
        add(buttonsPanel, BorderLayout.SOUTH);

        movesButton.addActionListener(e -> {
            showMovesDialog();
            playButtonClickSound();
        });
        searchButton.addActionListener(e -> {
            areasButton.setEnabled(false);
            searchButton.setEnabled(false);
            fadeOutBattleMusic();
            luckyCatButton.setVisible(false);
            sariSariButton.setBackground(Color.WHITE);
            search.performSearch();
            playButtonClickSound();
        });
        sariSariButton.addActionListener(e -> {
            isShopOpen = true;
            pauseMusic();
            String area = getArea();
            if (area.equals("Kalye West")) {
                openPetShopPanel();
            } else if (area.equals("Gedli East")) {
                openUkayUkay();
            } else if (area.equals("Professor's Lab")) {
                openLaboratory();
            } else {
                openShopPanel();
            }

            playButtonClickSound();
            repaint();
            revalidate();
        });
        areasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(area);
                playButtonClickSound();
                buttonsPanel.removeAll();

                kalsadaCentralButton.setBackground(Color.WHITE);
                kalyeWestButton.setBackground(Color.WHITE);
                gedliEastButton.setBackground(Color.WHITE);
                professorsLabButton.setBackground(Color.WHITE);

                buttonsPanel.add(kalsadaCentralButton);
                buttonsPanel.add(kalyeWestButton);
                buttonsPanel.add(gedliEastButton);
                buttonsPanel.add(professorsLabButton);
                if (getLevel() >= 5) {
                    kalyeWestButton.setEnabled(true);
                } else {
                    kalyeWestButton.setEnabled(false);
                }
                if (getLevel() >= 10) {
                    gedliEastButton.setEnabled(true);
                } else {
                    gedliEastButton.setEnabled(false);
                }

                if (area.equals("Kalsada Central")) {
                    kalsadaCentralButton.setBackground(new Color(102, 255, 51));
                } else if (area.equals("Kalye West")) {
                    kalyeWestButton.setBackground(new Color(102, 255, 51));
                } else if (area.equals("Gedli East")) {
                    gedliEastButton.setBackground(new Color(102, 255, 51));
                } else if (area.equals("Professor's Lab")) {
                    professorsLabButton.setBackground(new Color(102, 255, 51));
                }
                luckyCatButton.setVisible(false);
                buttonsPanel.revalidate();
                buttonsPanel.repaint();
            }
        });

        kalsadaCentralButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playButtonClickSound();
                switchToKalsadaCentral();
                buttonsPanel.setBackground(new Color(82, 113, 255));
                restoreButtons();
                luckyCatButton.setVisible(false);
            }
        });

        kalyeWestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playButtonClickSound();
                switchToKalyeWest();
                buttonsPanel.setBackground(Color.ORANGE);
                restoreButtons();
                luckyCatButton.setVisible(false);
            }
        });

        gedliEastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playButtonClickSound();
                switchToGedliEast();
                buttonsPanel.setBackground(Color.MAGENTA);
                restoreButtons();
                luckyCatButton.setVisible(false);
            }
        });

        professorsLabButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playButtonClickSound();
                switchToProfessorsLab();
                buttonsPanel.setBackground(Color.WHITE);
                restoreButtons();
                luckyCatButton.setVisible(false);
            }
        });

        kalsadaCentralButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!area.equals("Kalsada Central")) {
                    kalsadaCentralButton.setBackground(new Color(102, 255, 51));
                    playHoverSound();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!area.equals("Kalsada Central")) {
                    kalsadaCentralButton.setBackground(Color.WHITE);
                }
            }
        });

        kalyeWestButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!area.equals("Kalye West") && kalyeWestButton.isEnabled()) {
                    kalyeWestButton.setBackground(new Color(102, 255, 51));
                    playHoverSound();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!area.equals("Kalye West") && kalyeWestButton.isEnabled()) {
                    kalyeWestButton.setBackground(Color.WHITE);
                }
            }
        });

        gedliEastButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!area.equals("Gedli East") && gedliEastButton.isEnabled()) {
                    gedliEastButton.setBackground(new Color(102, 255, 51));
                    playHoverSound();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!area.equals("Gedli East") && gedliEastButton.isEnabled()) {
                    gedliEastButton.setBackground(Color.WHITE);
                }
            }
        });

        professorsLabButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!area.equals("Professor's Lab")) {
                    professorsLabButton.setBackground(new Color(102, 255, 51));
                    playHoverSound();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!area.equals("Professor's Lab")) {
                    professorsLabButton.setBackground(Color.WHITE);
                }
            }
        });
        buttonsPanel.revalidate();
        buttonsPanel.repaint();
        playIntroSound();
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
            incrementDefeatedEnemiesCount();
            playEnemyDefeatedSound();
            catchPokeKalye(enemyPokeKalye);
            int levelUpExp = getLevelUpExperience(playerLevel);
            int minExperience = 35;
            int maxExperience = 75;
            if (enemyPokeKalye.equals("Askal") || enemyPokeKalye.equals("Tuta") || enemyPokeKalye.equals("Lamok")
                    || enemyPokeKalye.equals("Ahas") || enemyPokeKalye.equals("Bubuyog")) {
                minExperience = 45;
                maxExperience = 95;
            }
            if (enemyPokeKalye.equals("Kitty Yonarchy") || enemyPokeKalye.equals("Lolong")
                    || enemyPokeKalye.equals("THE GOAT")) {
                minExperience = 500;
                maxExperience = 505;
            }

            experience += getRandomNumber(minExperience, maxExperience);
            if (experience >= levelUpExp) {
                playerLevel++;
                experience -= levelUpExp;
                evolvePokeKalye();
                animateLevelUp(playerLevelLabel);
                playLevelUpSound();
                playerCurrentHealth += 5;
                updateHealthBars();
                if (playerLevel == 5 || playerLevel == 10 || playerLevel == 11 || playerLevel == 15
                        || playerLevel == 20) {
                    animateLevelUp(areasButton);
                }
            }
            int earnedPesos = 0;
            String area = getArea();
            if (area.equals("Kalsada Central")) {
                earnedPesos = getRandomNumber(1, earnedPesosMaxValue);
            } else if (area.equals("Kalye West")) {
                earnedPesos = getRandomNumber(1, earnedPesosMaxValue + 10);
            } else if (area.equals("Gedli East")) {
                earnedPesos = getRandomNumber(1, earnedPesosMaxValue + 20);
            }
            if (enemyPokeKalye.equals("Kitty Yonarchy") || enemyPokeKalye.equals("Lolong")
                    || enemyPokeKalye.equals("THE GOAT")) {
                earnedPesos = 300;
            }
            pesos += earnedPesos;

            int previousLuck = luck;
            boolean luckModified = false;
            if (pesos < 0 && luck >= 0) {
                luck = 0;
                luckModified = true;
            }

            playerExpBar.setMaximum(getLevelUpExperience(playerLevel));
            animateExpBar(playerExpBar, experience, getLevelUpExperience(playerLevel), 600);
            playerLevelLabel.setText("LVL " + playerLevel);
            if (enemyPokeKalye.equals("Professor Splinter")) {
                Timer victoryTimer = new Timer(4000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        playerVictory();
                    }
                });
                setGameCompleted();
                buttonsPanel.removeAll();
                moveButtons.clear();
                buttonsPanel.revalidate();
                buttonsPanel.repaint();
                playBossDefeat();
                search.stopBossMusic();
                search.stopBossMusic2();
                victoryTimer.setRepeats(false);
                victoryTimer.start();
            } else {
                Timer timer = new Timer(50, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        displayAfterBattleDialogue();
                        setInBattle(false);
                        restoreButtons();
                        if (boughtWaterBowl) {
                            int healthToAdd = 5;
                            int maxHealth = getPlayerMaxHealth();
                            int currentHealth = getPlayerCurrentHealth();

                            if (currentHealth + healthToAdd > maxHealth) {
                                healthToAdd = maxHealth - currentHealth;
                            }

                            setPlayerCurrentHealth(currentHealth + healthToAdd);
                            updateHealthBars();
                        }
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
            if (luckModified && pesos >= 0) {
                luck = previousLuck;
            }

            clearDialogue();
            enemyDamageReductionCounter = 0;
        } else if (playerCurrentHealth <= 0 && !gameOver) {
            if (playerExtraLives <= 0) {
                playerDefeat();
                return;
            } else {
                playReviveSound();
                playerExtraLives--;
                int maxHealth = getPlayerMaxHealth();
                setPlayerCurrentHealth(maxHealth);
                updateHealthBars();
            }
        }
    }

    private void showMovesDialog() {
        buttonsPanel.removeAll();
        moveButtons.clear();

        MovePool.Move[] moves = playerData.getMoves();
        int numMoves = 2;

        if (playerLevel >= 6 || rugbied) {
            numMoves = 3;
        }

        if (playerLevel >= 10 || selectedPokeKalye.equals("Puspin Boots")) {
            numMoves = 4;
        }

        int actualNumMoves = Math.min(numMoves, moves.length);

        for (int i = 0; i < actualNumMoves; i++) {
            MovePool.Move move = moves[i];
            JButton moveButton = new JButton(move.getName());
            moveButton.setBackground(Color.WHITE);

            moveButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (moveButton.isEnabled()) {
                        moveButton.setBackground(new Color(102, 255, 51));
                        playHoverSound();
                    }
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    moveButton.setBackground(Color.WHITE);

                }
            });

            moveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (moveButton.isEnabled()) {
                        playButtonClickSound();
                        if (playerTurn) {
                            performMove(move);
                        }
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

    private void openPetShopPanel() {
        if (loopingMusicClip != null && loopingMusicClip.isRunning()) {
            loopingMusicClip.stop();
        }
        PetShopPanel petShopPanel = new PetShopPanel(this);
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        currentFrame.getContentPane().removeAll();
        currentFrame.setContentPane(petShopPanel);
        currentFrame.pack();
        currentFrame.revalidate();
        currentFrame.repaint();
    }

    private void openLaboratory() {
        if (loopingMusicClip != null && loopingMusicClip.isRunning()) {
            loopingMusicClip.stop();
        }
        LaboratoryPanel laboratoryPanel = new LaboratoryPanel(this);
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        currentFrame.getContentPane().removeAll();
        currentFrame.setContentPane(laboratoryPanel);
        currentFrame.pack();
        currentFrame.revalidate();
        currentFrame.repaint();
    }

    private void openUkayUkay() {
        if (loopingMusicClip != null && loopingMusicClip.isRunning()) {
            loopingMusicClip.stop();
        }
        UkayPanel ukayPanel = new UkayPanel(this);
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        currentFrame.getContentPane().removeAll();
        currentFrame.setContentPane(ukayPanel);
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

        String imagePath = "media/images/" + enemyPokeKalye + "2.png";
        ImageIcon enemyImage;

        if (enemyData.getName().equals("Professor Splinter")) {
            imagePath = "media/images/ProfessorSplinter.png";
            enemyImage = new ImageIcon(imagePath);
            sariSariButton.setEnabled(false);
            enemyImageLabel.setBounds(240, 70, 100, 150);
            enemyHealthBar.setPreferredSize(new Dimension(230, 16));
        } else if (enemyData.getName().equals("Kitty Yonarchy")) {
            imagePath = "media/images/yona.png";
            enemyImage = new ImageIcon(imagePath);
            sariSariButton.setEnabled(false);
            enemyImageLabel.setBounds(240, 70, 100, 150);
            enemyHealthBar.setPreferredSize(new Dimension(200, 16));
        } else if (enemyData.getName().equals("Lolong")) {
            imagePath = "media/images/lolong.png";
            enemyImage = new ImageIcon(imagePath);
            sariSariButton.setEnabled(false);
            enemyImageLabel.setBounds(240, 70, 151, 135);
            enemyHealthBar.setPreferredSize(new Dimension(210, 16));
        } else if (enemyData.getName().equals("THE GOAT")) {
            imagePath = "media/images/Goat.png";
            enemyImage = new ImageIcon(imagePath);
            sariSariButton.setEnabled(false);
            enemyImageLabel.setBounds(240, 70, 100, 150);
            enemyHealthBar.setPreferredSize(new Dimension(220, 16));
        } else {
            enemyImage = new ImageIcon(imagePath);
            enemyHealthBar.setPreferredSize(new Dimension(140, 16));

            playBattleMusic();
            String cryPath = "media/audio/Cries/" + enemyPokeKalye + "Cry.wav";
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new File(cryPath)));
                clip.start();
            } catch (Exception e) {
                System.err.println("Error playing audio: " + e.getMessage());
            }
        }

        enemyImageLabel.setIcon(enemyImage);
        setInBattle(true);
        System.out.println("Enemy HP: " + enemyCurrentHealth + "/" + enemyMaxHealth);
        searchButton.setEnabled(false);
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

    public String getPokeKalyeCustomName() {
        return pokeKalyeName;
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
            searchButton.setEnabled(false);
            areasButton.setEnabled(false);
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

    void enableButtons() {
        searchButton.setEnabled(true);
        sariSariButton.setEnabled(true);
        areasButton.setEnabled(true);
    }

    public void setSearchLabelText(String text) {
        searchingLabel.setText(text);
    }

    public void setEnemyLabelText(String text) {
        enemyLabel.setVisible(true);
        enemyLabel.setText(text);
    }

    public void showSearchingLabel(boolean show) {
        searchingLabel.setVisible(show);
    }

    public void setEnemyData(PokeKalyeData.PokeKalye enemyData) {
        this.enemyData = enemyData;
        this.enemyMaxHealth = getMaxHealth(enemyData);
        int enemyHpIncrease = getLevel() - 1;
        enemyMaxHealth += enemyHpIncrease;

        if (getLevel() >= 5) {
            int levelDifference = getLevel() - 5;
            int healthIncrease = levelDifference * 2;
            enemyMaxHealth += healthIncrease;
        }
        if (playerLevel >= 10) {
            int levelDifference = playerLevel - 10;
            int healthIncrease = levelDifference * 12;
            enemyMaxHealth += healthIncrease;
        }
        if (playerLevel >= 15) {
            int levelDifference = playerLevel - 10;
            int healthIncrease = levelDifference * 45;
            enemyMaxHealth += healthIncrease;
        }
        if (damageMultiplier > 1.0) {
            enemyMaxHealth += 5;
        }
        if (damageMultiplier > 3.0) {
            enemyMaxHealth += 10;
        }

        if (damageMultiplier > 5.0) {
            enemyMaxHealth += 20;
        }
        String area = getArea();
        if (area.equals("Kalye West")) {
            enemyMaxHealth += 5;
        } else if (area.equals("Gedli East")) {
            enemyMaxHealth += 30;
        }
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

    public int getPlayerMaxHealth() {
        return playerMaxHealth;
    }

    public void setPlayerCurrentHealth(int health) {
        playerCurrentHealth = health;
    }

    public void setPlayerMaxHealth(int health) {
        playerMaxHealth = health;
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
                case "Bubuyog":
                    maxHealth = PokeKalyeData.BUBUYOG.getMaxHealth();
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
                case "Mandarangkal":
                    maxHealth = PokeKalyeData.MANDARANGKAL.getMaxHealth();
                    break;
                case "Kabayo":
                    maxHealth = PokeKalyeData.KABAYO.getMaxHealth();
                    break;
                case "Professor Splinter":
                    maxHealth = PokeKalyeData.PROFESSOR_SPLINTER.getMaxHealth();
                    break;
                case "Kitty Yonarchy":
                    maxHealth = PokeKalyeData.TRIBAL_KIP.getMaxHealth();
                    break;
                case "Lolong":
                    maxHealth = PokeKalyeData.LOLONG.getMaxHealth();
                    break;
                case "THE GOAT":
                    maxHealth = PokeKalyeData.THE_GOAT.getMaxHealth();
                    break;
                case "Butete":
                    maxHealth = PokeKalyeData.BUTETE.getMaxHealth();
                case "Uod":
                    maxHealth = PokeKalyeData.UOD.getMaxHealth();
                    break;
                case "Suso":
                    maxHealth = PokeKalyeData.SUSO.getMaxHealth();
                    break;
                case "Isda":
                    maxHealth = PokeKalyeData.ISDA.getMaxHealth();
                    break;
                case "Eagul":
                    maxHealth = PokeKalyeData.EAGUL.getMaxHealth();
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
            case "Professor Splinter":
                enemyData = PokeKalyeData.PROFESSOR_SPLINTER;
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
            case "Bubuyog":
                enemyData = PokeKalyeData.BUBUYOG;
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
            case "Mandarangkal":
                enemyData = PokeKalyeData.MANDARANGKAL;
                break;
            case "Kabayo":
                enemyData = PokeKalyeData.KABAYO;
                break;
            case "Kitty Yonarchy":
                enemyData = PokeKalyeData.TRIBAL_KIP;
                break;
            case "Lolong":
                enemyData = PokeKalyeData.LOLONG;
                break;
            case "THE GOAT":
                enemyData = PokeKalyeData.THE_GOAT;
                break;
            case "Butete":
                enemyData = PokeKalyeData.BUTETE;
                break;
            case "Uod":
                enemyData = PokeKalyeData.UOD;
                break;
            case "Suso":
                enemyData = PokeKalyeData.SUSO;
                break;
            case "Isda":
                enemyData = PokeKalyeData.ISDA;
                break;
            case "Eagul":
                enemyData = PokeKalyeData.EAGUL;
                break;
            default:
                enemyData = null;
        }
    }

    void updateHealthBars() {
        int playerCurrentHealth = this.playerCurrentHealth;
        int enemyMaxHealth = getMaxHealth(enemyData);
        int enemyCurrentHealth = this.enemyCurrentHealth;

        animateHealthBar(playerHealthBar, playerCurrentHealth, playerMaxHealth, 350);
        animateHealthBar(enemyHealthBar, enemyCurrentHealth, enemyMaxHealth, 350);

        System.out.println("Enemy HP: " + enemyCurrentHealth + "/" + enemyMaxHealth);
        System.out.println("Player HP: " + playerCurrentHealth + "/" + playerMaxHealth);

        if (hasDengue) {
            int elapsedTimeInSeconds = (int) ((System.currentTimeMillis() - dengueStartTime) / 1000);
            int damageTaken = elapsedTimeInSeconds / 30;
            playerCurrentHealth -= damageTaken;

            if (playerCurrentHealth < 0) {
                playerCurrentHealth = 0;
            }
        }

        this.playerCurrentHealth = playerCurrentHealth;
        this.enemyCurrentHealth = enemyCurrentHealth;
    }

    private void animateHealthBar(JProgressBar healthBar, int currentHealth, int maxHealth, int duration) {
        int startValue = healthBar.getValue();
        int endValue = currentHealth;
        int totalFrames = duration / 40;
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
        // playerData.getMaxHealth();

        if (playerMaxHealth < getPlayerCurrentHealth()) {
            playerHealthBar.setForeground(new Color(255, 82, 200));
        }
        if (healthPercentage >= 0.6) {
            healthBar.setForeground(new Color(102, 255, 51));
        } else if (healthPercentage >= 0.3) {
            healthBar.setForeground(new Color(255, 224, 82));
        } else {
            healthBar.setForeground(Color.RED);
        }
    }

    void animateExpBar(JProgressBar expBar, int currentExp, int maxExp, int duration) {
        int startValue = expBar.getValue();
        int endValue = Math.min(currentExp, maxExp);

        int totalFrames = duration / 10;
        double increment = (double) (endValue - startValue) / totalFrames;
        int delay = duration / totalFrames;

        Timer timer = new Timer(delay, null);
        timer.addActionListener(new ActionListener() {
            int frameCount = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                double value = startValue + increment * frameCount;
                expBar.setValue((int) value);
                expBar.setForeground(Color.BLUE);

                frameCount++;
                if (frameCount >= totalFrames) {
                    timer.stop();
                    expBar.repaint();
                    if (expBar.getValue() >= maxExp) {
                        resetExpBar(expBar, maxExp, duration);
                    }
                } else {
                    expBar.repaint();
                }
            }
        });

        if (endValue == maxExp) {
            expBar.setValue(0);
            timer.setInitialDelay(150);
        } else {
            timer.setInitialDelay(0);
        }

        timer.start();
    }

    private void resetExpBar(JProgressBar expBar, int currentExp, int duration) {
        int startValue = expBar.getValue();
        int endValue = 0;

        int totalFrames = duration / 10;
        double increment = (double) (endValue - startValue) / totalFrames;
        int delay = duration / totalFrames;
        int maxExp = getLevelUpExperience(playerLevel);

        Timer timer = new Timer(delay, null);
        timer.addActionListener(new ActionListener() {
            int frameCount = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                double value = startValue + increment * frameCount;
                expBar.setValue((int) value);
                expBar.setForeground(Color.BLUE);

                frameCount++;
                if (frameCount >= totalFrames) {
                    timer.stop();
                    expBar.repaint();
                    if (expBar.getValue() >= maxExp) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        animateExpBar(expBar, currentExp, maxExp, duration);
                    }
                } else {
                    expBar.repaint();
                }
            }
        });

        timer.setInitialDelay(0);
        timer.start();
    }

    private void performMove(MovePool.Move move) {
        clearDialogue();
        String moveName = move.getName();

        double chance = move.getChance();

        if (moveName.equals("Burrow")) {
            performBurrowMove(move, chance);
        } else if (moveName.equals("Tahol")) {
            performTaholMove(move);
        } else if (moveName.equals("Purr")) {
            if (purrOnCooldown) {
                appendToDialogue("\n Purr is on cooldown.");
                return;
            }
            performPurrMove(move, chance);
        } else if (moveName.equals("Gang Up")) {
            performGangUpMove(move, move.getChance());
        } else if (moveName.equals("Flee")) {
            performFleeMove(move);
            return;
        } else if (moveName.equals("Quantum Burrow")) {
            performQuantumBurrowMove(move, chance);
        } else if (moveName.equals("Hiss")) {
            performHissMove(move);
        } else {
            performRegularMove(move, chance);
        }

        setMoveButtonsEnabled(false);
        Timer timer = new Timer(2100, e -> {
            enemyTurn();
            checkBattleResult();
            setMoveButtonsEnabled(true);
            if (purrOnCooldown) {
                purrCooldown--;
                if (purrCooldown <= 0) {
                    purrOnCooldown = false;
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void performFleeMove(MovePool.Move move) {
        boolean moveMissed = false;
        String pokeKalye = getPokeKalyeName();
        if (pokeKalye.equals("Puspin") || pokeKalye.equals("Puspin Boots")) {
            moveMissed = Math.random() <= 0.2;
        } else {
            moveMissed = Math.random() <= 0.6;
        }

        if (enemyPokeKalye.equals("Professor Splinter")) {
            appendToDialogue("\n YOU CAN'T RUN FROM ME.");
            playMissSound();
            enemyTurn();
        } else if (!moveMissed) {
            fadeOutBattleMusic();
            search.stopBossMusic2();
            setInBattle(false);
            searchButton.setEnabled(true);
            restoreButtons();
            appendToDialogue("\n " + pokeKalyeName + " fled!");
            playFleeUpSound();
            luckyCatButton.setVisible(false);
        } else {
            appendToDialogue("\n " + pokeKalyeName + ", flee pa more!");
            playMissSound();
            enemyTurn();
        }
    }

    private void performPurrMove(MovePool.Move move, double chance) {
        double maxHealPercentage = 0.99;
        int maxHealAmount = (int) (playerMaxHealth * maxHealPercentage);

        int healAmount = (int) (Math.random() * maxHealAmount) + 1;

        double modifiedChance = chance * (1 - healAmount / (double) maxHealAmount);

        if (Math.random() <= modifiedChance) {
            if (playerCurrentHealth + healAmount > playerMaxHealth) {
                playerCurrentHealth = playerMaxHealth;
            } else {
                playerCurrentHealth += healAmount;
            }
            updateHealthBars();
            appendToDialogue("\n " + pokeKalyeName + " used " + move.getName() + "!");
            playBurrowSound();
        } else {
            appendToDialogue("\n Purr failed!");
            playMissSound();
        }
        purrOnCooldown = true;
        purrCooldown = 2;
    }

    private void performBurrowMove(MovePool.Move move, double chance) {
        double modifiedChance = chance;
        double playerHealthPercentage = (double) playerCurrentHealth / playerMaxHealth;
        if (playerHealthPercentage <= 0.33) {
            modifiedChance += 0.1;
        }

        if (Math.random() <= modifiedChance) {
            int damage = move.getDamage();
            boolean hasRabiesBeforeMove = hasRabies;

            int maxPlayerHealth = playerMaxHealth;
            if (playerCurrentHealth + 10 > maxPlayerHealth) {
                playerCurrentHealth = maxPlayerHealth;
                updateHealthBars();
            } else {
                playerCurrentHealth += 10;
                updateHealthBars();
            }

            if (hasRabiesBeforeMove) {
                damage += 3;
                playerCurrentHealth -= 3;
                updateHealthBars();
            }
            enemyCurrentHealth -= damage;
            updateHealthBars();
            appendToDialogue("\n " + pokeKalyeName + " used " + move.getName() + "!");
            System.out.println("Enemy HP: " + enemyCurrentHealth + "/" + enemyMaxHealth);
            System.out.println("Player HP: " + playerCurrentHealth + "/" + playerMaxHealth);
            playBurrowSound();
            if (damage > 0) {
                animateDamageBlink(enemyImageLabel);
                playDamageSound(damage);
            }
        } else {
            appendToDialogue("\n Burrow failed!");
            playMissSound();
        }
    }

    private void performQuantumBurrowMove(MovePool.Move move, double chance) {
        double modifiedChance = chance;
        double playerHealthPercentage = (double) playerCurrentHealth / playerMaxHealth;
        if (playerHealthPercentage <= 0.33) {
            modifiedChance += 0.1;
        }

        if (Math.random() <= modifiedChance) {
            int damage = move.getDamage();
            boolean hasRabiesBeforeMove = hasRabies;

            int maxPlayerHealth = playerMaxHealth;
            int minHealAmount = 30;
            int maxHealAmount = 60;
            int baseHealAmount = (int) (Math.random() * (maxHealAmount - minHealAmount + 1)) + minHealAmount;
            int additionalHealAmount = (int) (playerMaxHealth * 0.1);
            int healAmount = baseHealAmount + additionalHealAmount;

            if (playerCurrentHealth + healAmount > maxPlayerHealth) {
                playerCurrentHealth = maxPlayerHealth;
                updateHealthBars();
            } else {
                playerCurrentHealth += healAmount;
                updateHealthBars();
            }

            if (hasRabiesBeforeMove) {
                damage += 3;
                playerCurrentHealth -= 3;
                updateHealthBars();
            }
            enemyCurrentHealth -= damage;
            updateHealthBars();
            appendToDialogue("\n " + pokeKalyeName + " used " + move.getName() + "!");
            System.out.println("Enemy HP: " + enemyCurrentHealth + "/" + enemyMaxHealth);
            System.out.println("Player HP: " + playerCurrentHealth + "/" + playerMaxHealth);
            playBurrowSound();
            if (damage > 0) {
                animateDamageBlink(enemyImageLabel);
                playDamageSound(damage);
            }
        } else {
            appendToDialogue("\n Quantum Burrow failed!");
            playMissSound();
        }
    }

    private void performTaholMove(MovePool.Move move) {
        taholDmgBoost = true;
        appendToDialogue("\n " + pokeKalyeName + " used " + move.getName() + "!");
        playBarkSound();
    }

    private void performHissMove(MovePool.Move move) {
        enemyDamageReductionCounter = 3;
        enemyDamageReductionPercentage = 0.5;
        appendToDialogue("\n " + pokeKalyeName + " used " + move.getName() + "!");
        playHissSound();
    }

    private void performGangUpMove(MovePool.Move move, double chance) {
        int additionalDamage;
        double randomValue = Math.random();

        if (randomValue <= 0.6) {
            additionalDamage = (int) (Math.random() * 10) + 1;
        } else if (randomValue <= 0.9) {
            additionalDamage = (int) (Math.random() * 20) + 11;
        } else {
            additionalDamage = (int) (Math.random() * 20) + 41;
        }
        int damage = move.getDamage() + additionalDamage;

        double critChance = 0.06 + critRateModifier;
        boolean isCrit = Math.random() <= critChance;
        if (isCrit) {
            damage += critDamageModifier;
            damage *= 2;
        }

        if (Math.random() <= chance) {
            if (hasRabies) {
                damage += 5;
                playerCurrentHealth -= 3;
                updateHealthBars();
            }
            enemyCurrentHealth -= damage;
            updateHealthBars();
            String moveName = move.getName();
            if (damage >= 1 && damage <= 5) {
                appendToDialogue("\n " + pokeKalyeName + " used " + moveName.toLowerCase() + "!");
            } else if (damage >= 12) {
                appendToDialogue("\n " + pokeKalyeName + " used " + moveName.toUpperCase() + "!");
            } else if (damage >= 6 && damage <= 11) {
                appendToDialogue("\n " + pokeKalyeName + " used " + moveName + "!");
            }

            System.out.println("Enemy HP: " + enemyCurrentHealth + "/" + enemyMaxHealth);
            System.out.println("Player HP: " + playerCurrentHealth + "/" + getMaxHealth(playerData));

            if (damage > 0) {
                animateDamageBlink(enemyImageLabel);
                playDamageSound(damage);
                showDamageLabel(enemyDamageLabel, damage, isCrit);
            }
        } else {
            appendToDialogue("\n " + pokeKalyeName + " missed!");
            playMissSound();
        }
    }

    private void performRegularMove(MovePool.Move move, double chance) {
        int damage = move.getDamage();

        damage *= damageMultiplier;

        if (boughtJersey) {
            damage *= 2;
        }
        if (damageMultiplier > 1.0) {
            int additionalDamage = (int) (Math.random() * 10) + 1;
            damage += additionalDamage;
        }
        double critChance = 0.05 + critRateModifier;
        if (selectedPokeKalye.equals("Puspin Boots")) {
            critChance += 0.1 + critRateModifier;
        }
        boolean isCrit = Math.random() <= critChance;
        if (isCrit) {
            damage += critDamageModifier;
            damage *= 2;
        }

        if (taholDmgBoost) {
            damage *= 3;
            taholDmgBoost = false;
        }

        if (Math.random() <= chance) {
            if (hasRabies) {
                damage += 5;
                playerCurrentHealth -= 3;
                updateHealthBars();
                enemyCurrentHealth -= damage;
                updateHealthBars();
                appendToDialogue("\n " + pokeKalyeName + " used " + move.getName() + "!");
                System.out.println("Enemy HP: " + enemyCurrentHealth + "/" + enemyMaxHealth);
                System.out.println("Player HP: " + playerCurrentHealth + "/" + getMaxHealth(playerData));
                if (damage > 0) {
                    animateDamageBlink(enemyImageLabel);
                    playDamageSound(damage);
                    showDamageLabel(enemyDamageLabel, damage, isCrit);
                }
            } else {
                enemyCurrentHealth -= damage;
                updateHealthBars();
                appendToDialogue("\n " + pokeKalyeName + " used " + move.getName() + "!");
                System.out.println("Enemy HP: " + enemyCurrentHealth + "/" + enemyMaxHealth);
                if (damage > 0) {
                    animateDamageBlink(enemyImageLabel);
                    playDamageSound(damage);
                    showDamageLabel(enemyDamageLabel, damage, isCrit);
                }
            }
        } else {
            appendToDialogue("\n " + selectedPokeKalye + " missed!");
            playMissSound();
        }
    }

    private void enemyTurn() {
        if (enemyCurrentHealth > 0) {
            if (enemyMeditateCooldown > 0) {
                dialogueArea.append("\n Splinter is meditating.");
                enemyMeditateCooldown--;
            } else {
                MovePool.Move[] enemyMoves = enemyData.getMoves();
                int randomIndex = (int) (Math.random() * enemyMoves.length);
                MovePool.Move enemyMove = enemyMoves[randomIndex];
                String enemyMoveName = enemyMove.getName();

                double chance = enemyMove.getChance();

                if (enemyMoveName.equals("Flee")) {
                    performEnemyFleeMove(enemyMove, chance);
                    return;
                }

                if (enemyMoveName.equals("Burrow")) {
                    performEnemyBurrowMove(enemyMove, chance);
                } else if (enemyMoveName.equals("Tahol")) {
                    performEnemyTaholMove(enemyMove);
                } else if (enemyMoveName.equals("Bite")) {
                    performEnemyBiteMove(enemyMove, chance);
                } else if (enemyMoveName.equals("Purr") && enemyCurrentHealth < enemyData.getMaxHealth()) {
                    performEnemyPurrMove(enemyMove);
                } else if (enemyMoveName.equals("Suck")) {
                    performEnemySuckMove(enemyMove, chance);
                } else if (enemyMoveName.equals("Meditate")) {
                    performEnemyMeditateMove(enemyMove);
                } else if (enemyMoveName.equals("Sewer Focus")) {
                    performEnemySewerFocusMove(enemyMove, chance);
                } else {
                    performEnemyRegularMove(enemyMove, chance);
                }
            }
        }

        if (enemyCurrentHealth <= 0) {
            inBattle = false;
            fadeOutBattleMusic();
            search.stopBossMusic2();
        }
    }

    private void performEnemySewerFocusMove(MovePool.Move move, double chance) {
        int numUpdates = 5;
        int damage = move.getDamage();

        for (int i = 0; i < numUpdates; i++) {
            if (Math.random() < chance) {
                boolean isCrit = Math.random() < 0.2;

                if (damage > 0) {
                    if (isCrit) {
                        damage *= 2;
                    }

                    animateDamageBlink(yourPokeKalyeImage);
                    playDamageSound(damage);
                    showDamageLabel(yourPokeKalyeDamageLabel, damage, isCrit);
                    checkBattleResult();
                }

                playerCurrentHealth -= damage;
                if (playerCurrentHealth < 0) {
                    playerCurrentHealth = 0;
                }

                updateHealthBars();

                try {
                    Thread.sleep(110);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                dialogueArea.append("\n Sewer Focus missed!");
            }
        }

        dialogueArea.append("\n " + enemyPokeKalye + " used Sewer Focus!");
        checkBattleResult();

    }

    private void performEnemyMeditateMove(MovePool.Move move) {
        if (enemyMeditateCooldown > 0) {
            performEnemyRegularMove(move, 1.0);
            dialogueArea.append("\n Splinter is meditating.");
        } else {
            int healAmount = (int) (Math.random() * 5001) + 200;
            enemyCurrentHealth += healAmount;
            enemyMeditateCooldown = (int) (Math.random() * 2);
            dialogueArea.append("\n Splinter used " + move.getName() + ".");
            updateHealthBars();
            playMeditateSound();
        }
    }

    private void performEnemySuckMove(MovePool.Move move, double chance) {
        if (Math.random() <= chance) {
            int damage = move.getDamage();
            playerCurrentHealth -= damage;

            if (Math.random() <= 0.2) {
                applyDengueStatusEffect();
                Component[] components = playerPanel.getComponents();
                for (Component component : components) {
                    if (component instanceof JLabel) {
                        JLabel label = (JLabel) component;
                        Icon icon = label.getIcon();
                        if (icon != null && icon.equals(emptyIcon)) {
                            label.setIcon(dengueIcon);
                            label.setToolTipText("Your PokeKalye has suffered Dengue.");
                            break;
                        }
                    }
                }
            }

            updateHealthBars();
            dialogueArea.append("\n " + enemyPokeKalye + " used " + move.getName() + "!");
            System.out.println("Player HP: " + playerCurrentHealth + "/" + getMaxHealth(playerData));
            System.out.println("Enemy HP: " + enemyCurrentHealth + "/" + getMaxHealth(enemyData));

            if (damage > 0) {
                animateDamageBlink(yourPokeKalyeImage);
                playDamageSound(damage);
            }
        } else {
            dialogueArea.append("\n " + enemyPokeKalye + " missed!");
        }
    }

    private void performEnemyFleeMove(MovePool.Move move, double chance) {
        if (Math.random() <= chance) {
            fadeOutBattleMusic();
            clearDialogue();
            setInBattle(false);
            searchButton.setEnabled(true);
            restoreButtons();
            appendToDialogue("\n " + enemyPokeKalye + " fled!");
            playFleeUpSound();
            luckyCatButton.setVisible(false);
        } else {
            dialogueArea.append("\n " + enemyPokeKalye + ", flee pa more!");
        }
    }

    private void applyDengueStatusEffect() {
        hasDengue = true;
        dengueStartTime = System.currentTimeMillis();
        startDengueTimer();
    }

    private void startDengueTimer() {
        playDengueSound();
        dengueTimer = new Timer(dengueInterval, e -> {
            playerCurrentHealth -= dengueDamage;
            updateHealthBars();
            if (playerCurrentHealth <= 0) {
                playerDefeat();
                stopDengueTimer();
            }
        });
        dengueTimer.start();
    }

    void stopDengueTimer() {
        stopDengueSound();

        if (dengueTimer != null && dengueTimer.isRunning()) {
            dengueTimer.stop();
        }
    }

    private void playDengueSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("media/audio/dengue.wav"));
            dengueSoundClip = AudioSystem.getClip();
            dengueSoundClip.open(audioInputStream);
            dengueSoundClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void stopDengueSound() {
        if (dengueSoundClip != null && dengueSoundClip.isRunning()) {
            dengueSoundClip.stop();
            dengueSoundClip.close();
        }
    }

    public boolean setDengue(boolean dengue) {
        if (dengue != hasDengue) {
            hasDengue = dengue;
            if (!hasDengue) {
                stopDengueTimer();
                stopDengueSound();
                removeDengueIcon();
            }
            return false;
        }
        return true;
    }

    void removeDengueIcon() {
        Component[] components = playerPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                Icon icon = label.getIcon();
                if (icon != null && icon.equals(dengueIcon)) {
                    label.setIcon(emptyIcon);
                    label.setToolTipText(null);
                    break;
                }
            }
        }
    }

    private void performEnemyPurrMove(MovePool.Move move) {
        int healAmount = (int) (Math.random() * 30) + 1;
        enemyCurrentHealth += healAmount;
        updateHealthBars();
        dialogueArea.append("\n " + enemyPokeKalye + " used " + move.getName() + "!");
        System.out.println("Player HP: " + playerCurrentHealth + "/" + getMaxHealth(playerData));
        System.out.println("Enemy HP: " + enemyCurrentHealth + "/" + getMaxHealth(enemyData));
        playBurrowSound();
    }

    private void performEnemyBurrowMove(MovePool.Move move, double chance) {
        if (Math.random() <= chance) {
            int damage = move.getDamage();
            playerCurrentHealth -= damage;
            int maxEnemyHealth = getMaxHealth(enemyData);
            if (enemyCurrentHealth + 10 > maxEnemyHealth) {
                enemyCurrentHealth = maxEnemyHealth;
                updateHealthBars();
            } else {
                enemyCurrentHealth += 10;
                updateHealthBars();
            }
            dialogueArea.append("\n " + enemyPokeKalye + " used " + move.getName() + "!");
            System.out.println("Player HP: " + playerCurrentHealth + "/" + getMaxHealth(playerData));
            System.out.println("Enemy HP: " + enemyCurrentHealth + "/" + getMaxHealth(enemyData));
            playBurrowSound();
            if (damage > 0) {
                animateDamageBlink(yourPokeKalyeImage);
                playDamageSound(damage);
            }
        } else {
            dialogueArea.append("\n Burrow failed!");
            playMissSound();
        }
    }

    private void performEnemyTaholMove(MovePool.Move move) {
        dialogueArea.append("\n " + enemyPokeKalye + " used " + move.getName() + "!");
        playBarkSound();
    }

    private void performEnemyBiteMove(MovePool.Move move, double chance) {
        int damage = move.getDamage();

        if (Math.random() <= chance) {
            if (!rabiesVaccinated && !hasRabies
                    && (Math.random() <= 0.3 || (boughtVitamins && Math.random() <= 0.05))) {
                hasRabies = true;
                Component[] components = playerPanel.getComponents();
                for (Component component : components) {
                    if (component instanceof JLabel) {
                        JLabel label = (JLabel) component;
                        Icon icon = label.getIcon();
                        if (icon != null && icon.toString().contains("empty.png")) {
                            ImageIcon rabiesIcon = new ImageIcon("media/images/rabies.png");
                            Image image = rabiesIcon.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
                            rabiesIcon = new ImageIcon(image);
                            label.setIcon(rabiesIcon);
                            label.setToolTipText("Your PokeKalye is infected with rabies.");
                            break;
                        }
                    }
                }
            }

            playerCurrentHealth -= damage;
            updateHealthBars();
            dialogueArea.append("\n " + enemyPokeKalye + " used " + move.getName() + "!");
            System.out.println("Player HP: " + playerCurrentHealth + "/" + getMaxHealth(playerData));

            if (damage > 0) {
                animateDamageBlink(yourPokeKalyeImage);
                playDamageSound(damage);
            }
        } else {
            dialogueArea.append("\n " + enemyPokeKalye + " missed!");
            playMissSound();
            return;
        }
    }

    private void performEnemyRegularMove(MovePool.Move move, double chance) {
        int damage = move.getDamage();
        int originalDamage = damage;

        double critChance = 0.05;
        if (enemyPokeKalye.equals("Professor Splinter")) {
            critChance = 0.6;
        } else if (damageMultiplier > 1.0) {
            critChance = 0.06;
        }
        if (playerCurrentHealth > getMaxHealth(playerData)) {
            critChance += 0.2;
        }
        boolean isCrit = Math.random() <= critChance;
        if (playerLevel <= 10 && playerCurrentHealth <= 0.3 * getMaxHealth(playerData)) {
            chance -= 0.1;
        }
        if (isCrit) {
            if (damageMultiplier > 1.0) {
                damage *= 3;
            } else {
                damage *= 2;
            }
        }

        if (enemyDamageReductionCounter > 0) {
            damage *= (1 - enemyDamageReductionPercentage);
            enemyDamageReductionCounter--;
        }

        if (boughtVest) {
            damage /= 2;
        }

        if (Math.random() <= chance) {
            if (originalDamage > 0) {
                if (playerLevel >= 6) {
                    int levelDifference = playerLevel - 6;
                    double scalingFactor = 2;
                    damage += (int) (levelDifference * scalingFactor);

                    if (playerLevel >= 15) {
                        levelDifference = playerLevel - 15;
                        scalingFactor = 3;
                        damage += (int) (levelDifference * scalingFactor);
                    }
                }
            }
            playerCurrentHealth -= damage;
            String enemyName = enemyPokeKalye;
            if (enemyPokeKalye.equals("Splinter")) {
                enemyName = " Splinter " + move.getName() + ".";
            } else if (enemyPokeKalye.length() > 8) {
                enemyName = "It used " + move.getName() + "!";
            } else {
                enemyName += " used " + move.getName() + "!";
            }
            dialogueArea.append("\n " + enemyName);
            updateHealthBars();
            System.out.println("Player HP: " + playerCurrentHealth + "/" + getMaxHealth(playerData));
            if (damage > 0) {
                animateDamageBlink(yourPokeKalyeImage);
                playDamageSound(damage);
                showDamageLabel(yourPokeKalyeDamageLabel, damage, isCrit);
            }
            checkBattleResult();
        } else {
            dialogueArea.append("\n " + enemyPokeKalye + " missed!");
            playMissSound();
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
        sariSariButton.setEnabled(true);
        areasButton.setEnabled(true);

        movesButton.setBackground(Color.WHITE);
        searchButton.setBackground(Color.WHITE);
        sariSariButton.setBackground(Color.WHITE);
        areasButton.setBackground(Color.WHITE);

        buttonsPanel.add(movesButton);
        buttonsPanel.add(searchButton);
        buttonsPanel.add(sariSariButton);
        buttonsPanel.add(areasButton);

        if (getLuck() == 0) {
            if ((double) getPlayerCurrentHealth() / getPlayerMaxHealth() <= 0.3) {
                luckyCatButton.setVisible(Math.random() < 0.11);
            } else {
                luckyCatButton.setVisible(Math.random() < 0.01);
            }
        } else {
            if ((double) getPlayerCurrentHealth() / getPlayerMaxHealth() <= 0.3) {
                luckyCatButton.setVisible(Math.random() < (getLuck() * 0.011));
            } else {
                luckyCatButton.setVisible(Math.random() < (getLuck() * 0.01));
            }
        }

        if (getPlayerCurrentHealth() > getPlayerMaxHealth()) {
            setPlayerCurrentHealth(getPlayerMaxHealth());
            updateHealthBars();
        }
        String area = getArea();
        if (area.equals("Kalye West")) {
            sariSariButton.setText("Pet Shop");
        } else if (area.equals("Gedli East")) {
            sariSariButton.setText("Ukay-Ukay");
        } else if (area.equals("Professor's Lab")) {
            sariSariButton.setText("Laboratory");
        } else {
            sariSariButton.setText("Sari-Sari");
        }

        buttonsPanel.revalidate();
        buttonsPanel.repaint();
    }

    int getLevelUpExperience(int level) {
        return 100 + (level - 1) * 15;
    }

    private Random random = new Random();

    private int getRandomNumber(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private void appendToDialogue(String text) {
        dialogueArea.append("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n " + text);
        dialogueArea.setCaretPosition(dialogueArea.getDocument().getLength());
    }

    private void clearDialogue() {
        String dialogue = "";

        if (gameCompleted) {
            dialogue = " Congratulations!\n You beat the game!";
        } else {
            if (selectedPokeKalye.equals("Puspin Boots")) {
                dialogue = " What will the great\n Puspin Boots do?";
            } else if (selectedPokeKalye.equals("Ant-Man")) {
                dialogue = " What's it gonna be,\n Ant-Man?";
            } else if (selectedPokeKalye.equals("Big Dog")) {
                dialogue = " What will the\n Big Dog do?";
            } else {
                dialogue = " What will " + pokeKalyeName + " do?";
            }
        }

        dialogueArea.setText(dialogue);
    }

    private void preloadSounds() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                File clickSoundFile = new File("media/audio/click.wav");
                File koSoundFile = new File("media/audio/ko.wav");
                File levelUpSoundFile = new File("media/audio/levelup.wav");
                File fleeSoundFile = new File("media/audio/flee.wav");
                File lowHitSoundFile = new File("media/audio/lowHit.wav");
                File medHitSoundFile = new File("media/audio/medHit.wav");
                File highHitSoundFile = new File("media/audio/highHit.wav");
                File burrowSoundFile = new File("media/audio/burrow.wav");
                File missSoundFile = new File("media/audio/miss.wav");
                File barkSoundFile = new File("media/audio/bark.wav");
                File hissSoundFile = new File("media/audio/hiss.wav");
                File dengueSoundFile = new File("media/audio/dengue.wav");
                File introSoundFile = new File("media/audio/intro.wav");
                File reviveSoundFile = new File("media/audio/revive.wav");

                clickSoundClip = preloadClip(clickSoundFile);
                koSoundClip = preloadClip(koSoundFile);
                levelUpSoundClip = preloadClip(levelUpSoundFile);
                fleeSoundClip = preloadClip(fleeSoundFile);
                lowHitSoundClip = preloadClip(lowHitSoundFile);
                medHitSoundClip = preloadClip(medHitSoundFile);
                highHitSoundClip = preloadClip(highHitSoundFile);
                burrowSoundClip = preloadClip(burrowSoundFile);
                missSoundClip = preloadClip(missSoundFile);
                barkSoundClip = preloadClip(barkSoundFile);
                hissSoundClip = preloadClip(hissSoundFile);
                dengueSoundClip = preloadClip(dengueSoundFile);
                introSoundClip = preloadClip(introSoundFile);
                reviveSoundClip = preloadClip(reviveSoundFile);
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();
    }

    private Clip preloadClip(File audioFile)
            throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        return clip;
    }

    private void playBattleMusic() {
        if (loopingMusicClip == null || !loopingMusicClip.isRunning()) {
            try {
                File musicFile;
                if (playerLevel >= 15) {
                    musicFile = new File("media/audio/battle2.wav");
                } else {
                    musicFile = new File("media/audio/battle.wav");
                }
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
                loopingMusicClip = AudioSystem.getClip();
                loopingMusicClip.open(audioStream);
                loopingMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
                if (isShopOpen) {
                    pauseMusic();
                }
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
    }

    private void pauseMusic() {
        if (loopingMusicClip != null && loopingMusicClip.isRunning()) {
            loopingMusicClip.stop();
        }
    }

    public void resumeMusic() {
        if (inBattle && loopingMusicClip != null && !loopingMusicClip.isRunning()) {
            loopingMusicClip.start();
        }
    }

    private void fadeOutBattleMusic() {
        if (loopingMusicClip != null && loopingMusicClip.isRunning()) {
            FloatControl gainControl = (FloatControl) loopingMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
            float initialVolume = gainControl.getValue();
            float volume = initialVolume;
            float fadeOutSpeed = 0.99f;

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
        if (clickSoundClip != null) {
            clickSoundClip.setFramePosition(0);
            clickSoundClip.start();
        }
    }

    private void playEnemyDefeatedSound() {
        if (koSoundClip != null) {
            koSoundClip.setFramePosition(0);
            koSoundClip.start();
        }
    }

    void playIntroSound() {
        if (introSoundClip != null) {
            introSoundClip.setFramePosition(0);
            introSoundClip.start();
        }
    }

    void playLevelUpSound() {
        if (levelUpSoundClip != null) {
            levelUpSoundClip.setFramePosition(0);
            levelUpSoundClip.start();
        }
    }

    private void playFleeUpSound() {
        if (fleeSoundClip != null) {
            fleeSoundClip.setFramePosition(0);
            fleeSoundClip.start();
        }
    }

    private void playReviveSound() {
        if (reviveSoundClip != null) {
            reviveSoundClip.setFramePosition(0);
            reviveSoundClip.start();
        }
    }

    private void playDamageSound(int damage) {
        if (damage >= 1 && damage <= 5) {
            playLowHitSound();
        } else if (damage >= 6 && damage <= 12) {
            playMedHitSound();
        } else if (damage >= 13) {
            playHighHitSound();
        }
    }

    private void playLowHitSound() {
        if (lowHitSoundClip != null) {
            lowHitSoundClip.setFramePosition(0);
            new Thread(() -> {
                lowHitSoundClip.start();
                try {
                    Thread.sleep(lowHitSoundClip.getMicrosecondLength() / 50);
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lowHitSoundClip.stop();
                    lowHitSoundClip.flush();
                    lowHitSoundClip.setFramePosition(0);
                }
            }).start();
        }
    }

    private void playMedHitSound() {
        if (medHitSoundClip != null) {
            medHitSoundClip.setFramePosition(0);
            new Thread(() -> {
                medHitSoundClip.start();
                try {
                    Thread.sleep(medHitSoundClip.getMicrosecondLength() / 50);
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    medHitSoundClip.stop();
                    medHitSoundClip.flush();
                    medHitSoundClip.setFramePosition(0);
                }
            }).start();
        }
    }

    private void playHighHitSound() {
        if (highHitSoundClip != null) {
            highHitSoundClip.setFramePosition(0);
            new Thread(() -> {
                highHitSoundClip.start();
                try {
                    Thread.sleep(highHitSoundClip.getMicrosecondLength() / 50);
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    highHitSoundClip.stop();
                    highHitSoundClip.flush();
                    highHitSoundClip.setFramePosition(0);
                }
            }).start();
        }
    }

    private void playBurrowSound() {
        if (burrowSoundClip != null) {
            burrowSoundClip.setFramePosition(0);
            burrowSoundClip.start();
        }
    }

    private void playMissSound() {
        if (missSoundClip != null) {
            missSoundClip.setFramePosition(0);
            missSoundClip.start();
        }
    }

    private void playBarkSound() {
        if (barkSoundClip != null) {
            barkSoundClip.setFramePosition(0);
            barkSoundClip.start();
        }
    }

    private void playHissSound() {
        if (hissSoundClip != null) {
            hissSoundClip.setFramePosition(0);
            hissSoundClip.start();
        }
    }

    private void playEvolveSound() {
        animateLevelUp(playerLabel);
        try {
            File soundFile = new File("media/audio/evolve.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playBossDefeat() {
        try {
            String audioFilePath = "media/audio/splinterDeath.wav";
            File audioFile = new File(audioFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playMeditateSound() {
        try {
            File soundFile = new File("media/audio/meditate.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playLuckyCatSound() {
        try {
            File soundFile = new File("media/audio/luckycat.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public void evolvePokeKalye() {
        if (selectedPokeKalye.equals("Langgam") && (playerLevel >= 6 || rugbied)) {
            selectedPokeKalye = "Antik";
            this.playerData = new PokeKalyeData.PokeKalye(selectedPokeKalye, 30, MovePool.getMoves(selectedPokeKalye));
            playerMaxHealth += 14;
            playerLabel.setText("ANTIK");
            yourPokeKalyeImage.setIcon(new ImageIcon("media/images/AntikUser.png"));
            animateDamageBlink(yourPokeKalyeImage);
            playEvolveSound();
            playerHealthBar.setMaximum(playerMaxHealth);
            updateHealthBars();
            setRugbied(false);
        } else if (selectedPokeKalye.equals("Antik") && (playerLevel >= 11 || rugbied)) {
            selectedPokeKalye = "Ant-Man";
            this.playerData = new PokeKalyeData.PokeKalye(selectedPokeKalye, 0, MovePool.getMoves(selectedPokeKalye));
            playerMaxHealth += 45;
            playerLabel.setText(selectedPokeKalye.toUpperCase());
            yourPokeKalyeImage.setIcon(new ImageIcon("media/images/AntManUser.png"));
            animateDamageBlink(yourPokeKalyeImage);
            playEvolveSound();
            playerHealthBar.setMaximum(playerMaxHealth);
            updateHealthBars();
            setRugbied(false);
        } else if (selectedPokeKalye.equals("Puspin") && (playerLevel >= 11 || rugbied)) {
            selectedPokeKalye = "Puspin Boots";
            this.playerData = new PokeKalyeData.PokeKalye(selectedPokeKalye, 0, MovePool.getMoves(selectedPokeKalye));
            playerMaxHealth += 45;
            playerLabel.setText(selectedPokeKalye.toUpperCase());
            yourPokeKalyeImage.setIcon(new ImageIcon("media/images/PuspinBootsUser.png"));
            animateDamageBlink(yourPokeKalyeImage);
            playEvolveSound();
            playerHealthBar.setMaximum(playerMaxHealth);
            updateHealthBars();
        } else if (selectedPokeKalye.equals("Askal") && (playerLevel >= 11 || rugbied)) {
            selectedPokeKalye = "Big Dog";
            this.playerData = new PokeKalyeData.PokeKalye(selectedPokeKalye, 0, MovePool.getMoves(selectedPokeKalye));
            playerMaxHealth += 85;
            playerLabel.setText(selectedPokeKalye.toUpperCase());
            yourPokeKalyeImage.setIcon(new ImageIcon("media/images/BigDogUser.png"));
            animateDamageBlink(yourPokeKalyeImage);
            playEvolveSound();
            playerHealthBar.setMaximum(playerMaxHealth);
            updateHealthBars();
        }
    }

    void animateLevelUp(JComponent component) {
        Color originalBackground = component.getBackground();
        Color highlightBackground = Color.YELLOW;
        Color originalForeground;
        Color highlightForeground = Color.YELLOW;

        if (component instanceof JLabel) {
            originalForeground = ((JLabel) component).getForeground();
        } else {
            originalForeground = component.getForeground();
        }

        int animationDuration = 1000;
        int animationInterval = 110;
        int numIterations = animationDuration / animationInterval;

        Timer delayTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Timer animationTimer = new Timer(animationInterval, new ActionListener() {
                    private int iteration = 0;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (iteration % 2 == 0) {
                            component.setBackground(highlightBackground);
                            if (component instanceof JLabel) {
                                ((JLabel) component).setForeground(highlightForeground);
                            }
                        } else {
                            component.setBackground(originalBackground);
                            if (component instanceof JLabel) {
                                ((JLabel) component).setForeground(originalForeground);
                            }
                        }
                        iteration++;
                        if (iteration >= numIterations) {
                            component.setBackground(originalBackground);
                            if (component instanceof JLabel) {
                                ((JLabel) component).setForeground(originalForeground);
                            }
                            ((Timer) e.getSource()).stop();
                        }
                    }
                });
                animationTimer.start();
            }
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    }

    private void animateDamageBlink(Component component) {
        int numBlinks = 4;
        int blinkDuration = 450;
        int blinkInterval = blinkDuration / (2 * numBlinks);

        Timer timer = new Timer(blinkInterval, new ActionListener() {
            private int blinkCount = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (blinkCount % 2 == 0) {
                    component.setVisible(false);
                } else {
                    component.setVisible(true);
                }
                blinkCount++;
                if (blinkCount >= 2 * numBlinks) {
                    component.setVisible(true);
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        timer.start();
    }

    public void setRabiesVaccinated(boolean vaccinated) {
        rabiesVaccinated = vaccinated;
    }

    public boolean isRabiesVaccinated() {
        return rabiesVaccinated;
    }

    public void setRugbied(boolean rugbied) {
        this.rugbied = rugbied;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public void setDamageMultiplier(double multiplier) {
        damageMultiplier = multiplier;
    }

    private void showDamageLabel(JLabel label, int damage, boolean isCrit) {
        if (!isCrit && damage <= 20) {
            return;
        }

        label.setText("-" + damage);
        label.setBounds(label.getX(), label.getY(), 100, 100);
        label.setFont(new Font("Impact", Font.PLAIN, 30));
        label.setForeground(isCrit ? Color.ORANGE : (damage <= 20 ? Color.RED : Color.RED));
        label.setVisible(true);

        Timer timer = new Timer(690, e -> {
            label.setVisible(false);
            label.setText("");
            ((Timer) e.getSource()).stop();
        });
        timer.setRepeats(false);
        timer.start();

        Thread thread = new Thread(() -> {
            int originalX = label.getX();
            int originalY = label.getY();
            int shakeOffset = 5;

            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                int offsetX = (int) (Math.random() * shakeOffset * 2) - shakeOffset;
                int offsetY = (int) (Math.random() * shakeOffset * 2) - shakeOffset;
                label.setLocation(originalX + offsetX, originalY + offsetY);
            }
            label.setLocation(originalX, originalY);
        });
        thread.start();

        if (isCrit) {
            JLabel critLabel = new JLabel("CRIT!");
            critLabel.setBounds(label.getX() - 110, label.getY() + 10, 100, 100);
            critLabel.setFont(new Font("Impact", Font.PLAIN, 34));
            critLabel.setForeground(Color.ORANGE);
            critLabel.setVisible(true);
            label.getParent().add(critLabel);

            Timer critTimer = new Timer(690, e -> {
                critLabel.setVisible(false);
                label.getParent().remove(critLabel);
                ((Timer) e.getSource()).stop();
            });
            critTimer.setRepeats(false);
            critTimer.start();

            Thread critThread = new Thread(() -> {
                int originalX = critLabel.getX();
                int originalY = critLabel.getY();
                int shakeOffset = 5;

                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    int offsetX = (int) (Math.random() * shakeOffset * 2) - shakeOffset;
                    int offsetY = (int) (Math.random() * shakeOffset * 2) - shakeOffset;
                    critLabel.setLocation(originalX + offsetX, originalY + offsetY);
                }
                critLabel.setLocation(originalX, originalY);
            });
            critThread.start();
        }
    }

    public void playerVictory() {
        search.stopBossMusic();
        search.stopBossMusic2();
        fadeOutBattleMusic();
        stopDengueTimer();
        gameCompleted = true;
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        GameCompletedPanel gameCompletedScreen = new GameCompletedPanel(this, elapsedTime);
        Container parent = this.getParent();
        parent.removeAll();
        parent.add(gameCompletedScreen);
        parent.revalidate();
        parent.repaint();
    }

    public void playerDefeat() {
        search.stopBossMusic();
        search.stopBossMusic2();
        fadeOutBattleMusic();
        stopDengueTimer();
        gameOver = true;
        GameOverPanel gameOverScreen = new GameOverPanel(this);
        Container parent = this.getParent();
        parent.removeAll();
        parent.add(gameOverScreen);
        parent.revalidate();
        parent.repaint();
    }

    public void increaseCritRateModifier(double value) {
        critRateModifier += value;
    }

    public void increaseCritDamageModifier(int value) {
        critDamageModifier += value;
    }

    public void increaseEarnedPesosMaxValue(int value) {
        earnedPesosMaxValue += value;
    }

    public int getLuck() {
        return luck;
    }

    public void increaseLuck(int value) {
        luck += value;
    }

    private int luckyRandom(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }

    public void increasePesos(int amount) {
        pesos += amount;
    }

    public void setShopOpen(boolean isOpen) {
        isShopOpen = isOpen;
    }

    private void displayAfterBattleDialogue() {
        int level = playerLevel;
        String enemyPokeKalyeName = enemyPokeKalye;
        String dialogue;

        if (level >= 1 && level <= 5) {
            dialogue = "\n Prof RP caught the\n " + enemyPokeKalyeName + " for you!";
        } else if (level >= 6 && level <= 10) {
            String[] dialogueOptions = {
                    "Kailangan natin\n mahuli ang mga to..\n maaasahan ba kita?",
                    "\n Prof RP caught the\n " + enemyPokeKalyeName + " for you!",
                    "Your mother said\n \"Di mo pwede iuwi si\n " + selectedPokeKalye
                            + " sa bahay.\"",
                    "\n Sige lang...!\n Huli lang....!",
                    "\n Pampaswerte daw yung\nShtick-O, natry mo na?"
            };
            dialogue = getRandomDialogueWithReuse(dialogueOptions);
        } else if (level >= 11 && level <= 16) {
            String[] dialogueOptions = {
                    "\n " + enemyPokeKalyeName + " has been caught!",
                    "\n Let me just get these\n PokeKalyes to my Lab!",
                    "\n I think that's enough now.\n Thank you.",
                    "\n You can leave now.",
                    "\n I warned you.",
                    "NO!\n WHY ARE YOU CATCHING THEM?\n THEY'RE ALL MINE!"
            };
            dialogue = getNextSequentialDialogue(dialogueOptions, level - 11);
        } else if (level >= 16 && level <= 23) {
            dialogue = "\n " + enemyPokeKalyeName + " has been caught!";
        } else if (level == 24) {
            dialogue = "You cannot stop me.";
        } else {
            dialogue = "";
        }

        appendToDialogue(dialogue);
    }

    private String getRandomDialogueWithReuse(String[] dialogueOptions) {
        String enemyPokeKalyeName = enemyPokeKalye;
        List<String> availableDialogues = new ArrayList<>(Arrays.asList(dialogueOptions));
        availableDialogues.removeAll(usedDialogues);

        if (availableDialogues.isEmpty()) {
            return "\n Prof RP caught the\n " + enemyPokeKalyeName + " for you!";
        }

        Collections.shuffle(availableDialogues);
        String dialogue = availableDialogues.get(0);
        usedDialogues.add(dialogue);
        return dialogue;
    }

    private String getNextSequentialDialogue(String[] dialogueOptions, int index) {
        if (index >= 0 && index < dialogueOptions.length) {
            String dialogue = dialogueOptions[index];
            if (!usedDialogues.contains(dialogue)) {
                usedDialogues.add(dialogue);
                return dialogue;
            }
        }
        return "";
    }

    public long getStartTime() {
        return startTime;
    }

    private void setGameCompleted() {
        gameCompleted = true;
        buttonsPanel.removeAll();
        moveButtons.clear();
        buttonsPanel.revalidate();
        buttonsPanel.repaint();
    }

    private void switchToKalsadaCentral() {
        area = "Kalsada Central";
    }

    private void switchToKalyeWest() {
        area = "Kalye West";
    }

    private void switchToGedliEast() {
        area = "Gedli East";
    }

    private void switchToProfessorsLab() {
        area = "Professor's Lab";
    }

    public String getArea() {
        return area;
    }

    void setAreasButtonEnabled(boolean enabled) {
        areasButton.setEnabled(enabled);
    }

    public void incrementMaxHealth(int incrementValue) {
        playerMaxHealth += incrementValue;
        playerHealthBar.setMaximum(playerMaxHealth);
    }

    public void setBoughtWaterBowl(boolean bought) {
        boughtWaterBowl = bought;
    }

    public void setBoughtVitamins(boolean bought) {
        boughtVitamins = bought;
    }

    public void incrementPlayerExtraLives() {
        playerExtraLives++;
    }

    public void changeYourPokeKalyeImage(JLabel newImage) {
        yourPokeKalyeImage.setIcon(newImage.getIcon());
    }

    public void changeYourPokeKalyeImage(String imagePath) {
        yourPokeKalyeImage.setIcon(new ImageIcon(imagePath));
    }

    public void setBoughtVest(boolean bought) {
        boughtVest = bought;
    }

    public boolean boughtBike() {
        return boughtBike;
    }

    public void setBoughtBike(boolean bought) {
        boughtBike = bought;
    }

    public void setBoughtJersey(boolean bought) {
        boughtJersey = bought;
    }

    public void catchPokeKalye(String pokeKalye) {
        caughtPokeKalyes.add(pokeKalye);
    }

    public boolean hasCaughtPokeKalye(String pokeKalye) {
        return caughtPokeKalyes.contains(pokeKalye);
    }

    public int getDefeatedEnemiesCount() {
        return defeatedEnemiesCount;
    }

    public void incrementDefeatedEnemiesCount() {
        defeatedEnemiesCount++;
    }
}
