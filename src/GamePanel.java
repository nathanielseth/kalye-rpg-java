import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private int playerLevel;
    private int experience;
    private JLabel playerLabel;
    private JProgressBar playerHealthBar;
    private int playerCurrentHealth;
    private JLabel enemyLabel;
    private JProgressBar enemyHealthBar;
    private JTextArea dialogueArea;
    private JButton movesButton;
    private JButton searchButton;
    private JButton settingsButton;
    private JButton sariSariButton;
    private String selectedPokeKalye;
    private String playerName;
    private boolean inBattle;
    private String enemyPokeKalye = "";
    private int enemyLevel;
    private int enemyMaxHealth;
    private int enemyCurrentHealth;
    private JLabel battleStatusLabel;
    private int pesos;
    private Search search;
    private JLabel enemyImageLabel;
    private List<JButton> moveButtons;
    private JPanel buttonsPanel;
    private JLabel searchingLabel;
    private PokeKalyeData.Pokemon playerData;
    private PokeKalyeData.Pokemon enemyData;

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
        playerLevel = 1;
        PokeKalyeData pokeKalyeData = new PokeKalyeData();
        this.enemyPokeKalye = "";
        search = new Search(this);
        this.playerName = playerName;
        this.selectedPokeKalye = selectedPokeKalye;
        this.playerData = new PokeKalyeData.Pokemon(selectedPokeKalye, 0);
        this.enemyData = new PokeKalyeData.Pokemon(enemyPokeKalye, 0);
        this.enemyCurrentHealth = getMaxHealth(enemyData);
        this.playerCurrentHealth = getMaxHealth(playerData);

        int playerMaxHealth = getMaxHealth(playerData);
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

        playerLabel = new JLabel(selectedPokeKalye.toUpperCase());
        playerLabel.setForeground(Color.WHITE);
        playerHealthBar = new JProgressBar(0, playerMaxHealth);
        playerHealthBar.setForeground(new Color(102, 255, 51));

        playerPanel.add(playerLabel);
        playerPanel.add(playerHealthBar);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(null);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        enemyImageLabel = new JLabel(new ImageIcon("images/empty.png"));
        enemyImageLabel.setBounds(240, 120, 100, 100);
        imagePanel.add(enemyImageLabel);

        JLabel yourPokeKalyeImage = new JLabel(new ImageIcon("images/" + selectedPokeKalye + "User.png"));
        yourPokeKalyeImage.setBounds(70, 10, 100, 100);

        imagePanel.add(yourPokeKalyeImage);

        JPanel enemyPanel = new JPanel();
        enemyPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        enemyPanel.setBackground(new Color(82, 113, 255));
        enemyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        enemyHealthBar = new JProgressBar(0, enemyMaxHealth);
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

        movesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (movesButton.isEnabled()) {
                    movesButton.setBackground(new Color(102, 255, 51));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                movesButton.setBackground(Color.WHITE);
            }
        });

        searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (searchButton.isEnabled()) {
                    searchButton.setBackground(new Color(102, 255, 51));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(Color.WHITE);
            }
        });

        sariSariButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (sariSariButton.isEnabled()) {
                    sariSariButton.setBackground(new Color(102, 255, 51));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                sariSariButton.setBackground(Color.WHITE);
            }
        });

        settingsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (settingsButton.isEnabled()) {
                    settingsButton.setBackground(new Color(102, 255, 51));
                    // added for testing / debugging
                    enemyCurrentHealth--;
                    System.out.println("Enemy HP: " + enemyCurrentHealth + "/" + enemyMaxHealth);
                    updateHealthBars();
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                settingsButton.setBackground(Color.WHITE);
            }
        });
        buttonsPanel.add(movesButton);
        buttonsPanel.add(searchButton);
        buttonsPanel.add(sariSariButton);
        buttonsPanel.add(settingsButton);

        add(battlePanel, BorderLayout.CENTER);
        add(dialogueScrollPane, BorderLayout.WEST);
        add(buttonsPanel, BorderLayout.SOUTH);

        dialogueArea
                .append("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n " + selectedPokeKalye + " Level: " + playerLevel
                        + "\n GCash: " + pesos);
        dialogueArea.setCaretPosition(dialogueArea.getDocument().getLength());

        // action listeners
        movesButton.addActionListener(e -> showMovesDialog());

        search = new Search(this);
        searchButton.addActionListener(e -> search.performSearch());

        settingsButton.addActionListener(e -> openSettingsPanel());

        sariSariButton.addActionListener(e -> openShopPanel());
        buttonsPanel.revalidate();
        buttonsPanel.repaint();
        showIntroScreen();
    }

    private int getMaxHealth(PokeKalyeData.Pokemon pokemon) {
        int maxHealth = 0;

        if (pokemon != null) {
            switch (pokemon.getName()) {
                case "Puspin":
                    maxHealth = PokeKalyeData.PUSPIN.getMaxHealth();
                    break;
                case "Askal":
                    maxHealth = PokeKalyeData.ASKAL.getMaxHealth();
                    break;
                case "Langgam":
                    maxHealth = PokeKalyeData.LANGGAM.getMaxHealth();
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
                case "Salagubang":
                    maxHealth = PokeKalyeData.SALAGUBANG.getMaxHealth();
                    break;
                case "Dagang Kanal":
                    maxHealth = PokeKalyeData.DAGANG_KANAL.getMaxHealth();
                    break;
                case "Langaw":
                    maxHealth = PokeKalyeData.LANGAW.getMaxHealth();
                    break;
                case "Batang Kalye":
                    maxHealth = PokeKalyeData.BATANG_KALYE.getMaxHealth();
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

    public int getLevel() {
        return playerLevel;
    }

    private void showMovesDialog() {
        buttonsPanel.removeAll();
        moveButtons.clear();

        int numMoves = Math.min(playerLevel < 5 ? 2 : playerLevel < 10 ? 3 : 4, 4);

        for (int i = 0; i < numMoves; i++) {
            JButton moveButton = new JButton("Move " + (i + 1));
            moveButton.setBackground(Color.WHITE);

            moveButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    moveButton.setBackground(new Color(102, 255, 51));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    moveButton.setBackground(Color.WHITE);
                }
            });

            moveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Implement moves logic in the future
                    System.out.println("Move button " + moveButton.getText() + " clicked");
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
        ShopPanel shopPanel = new ShopPanel();
        shopPanel.setVisible(true);
    }

    public void goBackToMainPanel() {
        ((CardLayout) getParent().getLayout()).show(getParent(), "GamePanel");
    }

    public void setEnemyImage(String enemyPokeKalye) {
        this.enemyPokeKalye = enemyPokeKalye;
        initializeEnemyData(enemyPokeKalye);
        setEnemyData(enemyData);
        updateHealthBars();
        enemyLabel.setText(enemyData.getName().toUpperCase());
        ImageIcon enemyImage = new ImageIcon("images/" + enemyPokeKalye + "2.png");
        enemyImageLabel.setIcon(enemyImage);
        setInBattle(true);
        System.out.println("Enemy HP: " + enemyCurrentHealth + "/" + enemyMaxHealth);
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

    public void setEnemyData(PokeKalyeData.Pokemon enemyData) {
        this.enemyData = enemyData;
        this.enemyMaxHealth = getMaxHealth(enemyData);
        this.enemyCurrentHealth = enemyMaxHealth;
        enemyHealthBar.setMaximum(enemyMaxHealth);
        enemyHealthBar.setValue(enemyCurrentHealth);
    }

    private void updateHealthBars() {
        int playerMaxHealth = getMaxHealth(playerData);
        int playerCurrentHealth = this.playerCurrentHealth;
        int enemyMaxHealth = getMaxHealth(enemyData);
        int enemyCurrentHealth = this.enemyCurrentHealth;

        // Update player health bar
        playerHealthBar.setMaximum(playerMaxHealth);
        playerHealthBar.setValue(playerCurrentHealth);

        // Update enemy health bar
        enemyHealthBar.setMaximum(enemyMaxHealth);
        enemyHealthBar.setValue(enemyCurrentHealth);
        updateBarColor(playerHealthBar, enemyHealthBar);
        // Set player health bar color

        System.out.println("Enemy HP: " + enemyCurrentHealth + "/" + enemyMaxHealth);
        System.out.println("Player HP: " + playerCurrentHealth + "/" +
                playerMaxHealth);
    }

    public int getEnemyCurrentHealth() {
        return enemyData.getCurrentHealth();
    }

    public int getPlayerCurrentHealth() {
        return playerData.getCurrentHealth();
    }

    private PokeKalyeData.Pokemon getEnemyPokemonData(String enemyPokeKalye) {
        PokeKalyeData.Pokemon enemyPokemonData = null;

        switch (enemyPokeKalye) {
            case "Puspin":
                enemyPokemonData = PokeKalyeData.PUSPIN;
                break;
            case "Askal":
                enemyPokemonData = PokeKalyeData.ASKAL;
                break;
            case "Langgam":
                enemyPokemonData = PokeKalyeData.LANGGAM;
                break;
            case "Ipis":
                enemyPokemonData = PokeKalyeData.IPIS;
                break;
            case "Flying Ipis":
                enemyPokemonData = PokeKalyeData.FLYING_IPIS;
                break;
            case "Daga":
                enemyPokemonData = PokeKalyeData.DAGA;
                break;
            case "Lamok":
                enemyPokemonData = PokeKalyeData.LAMOK;
                break;
            case "Butiki":
                enemyPokemonData = PokeKalyeData.BUTIKI;
                break;
            case "Ibon":
                enemyPokemonData = PokeKalyeData.IBON;
                break;
            case "Salagubs":
                enemyPokemonData = PokeKalyeData.SALAGUBANG;
                break;
            // Add more cases for other enemy Pokemons if needed

            default:
                // Handle the case when the enemy Pokemon is not found
                System.out.println("Enemy Pokemon not found: " + enemyPokeKalye);
                break;
        }

        return enemyPokemonData;
    }

    private PokeKalyeData.Pokemon getPlayerPokemonData(String selectedPokeKalye) {
        PokeKalyeData.Pokemon playerPokemonData = null;

        switch (selectedPokeKalye) {
            case "Puspin":
                playerPokemonData = PokeKalyeData.PUSPIN;
                break;
            case "Askal":
                playerPokemonData = PokeKalyeData.ASKAL;
                break;
            case "Langgam":
                playerPokemonData = PokeKalyeData.LANGGAM;
                break;
            default:
                break;
        }

        return playerPokemonData;
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
            case "Salagubs":
                enemyData = PokeKalyeData.SALAGUBANG;
                break;
            case "Dagang Kanal":
                enemyData = PokeKalyeData.DAGANG_KANAL;
                break;
            case "Langaw":
                enemyData = PokeKalyeData.LANGAW;
                break;
            case "Batang Kalye":
                enemyData = PokeKalyeData.BATANG_KALYE;
                break;
            case "Master Splinter":
                enemyData = PokeKalyeData.MASTER_SPLINTER;
                break;
            default:
                enemyData = null;
        }
    }

    private void updateBarColor(JProgressBar playerBar, JProgressBar enemyBar) {
        int enemyMaxHealth = enemyBar.getMaximum();
        int enemyCurrentHealth = enemyBar.getValue();
        int playerMaxHealth = playerBar.getMaximum();
        int playerCurrentHealth = playerBar.getValue();

        // Update player health bar color
        double playerHealthPercentage = (double) playerCurrentHealth / playerMaxHealth;

        // Set the color based on the percentage
        if (playerHealthPercentage >= 0.8) {
            playerBar.setForeground(new Color(102, 255, 51)); // Green
        } else if (playerHealthPercentage >= 0.4) {
            playerBar.setForeground(Color.YELLOW); // Yellow
        } else {
            playerBar.setForeground(Color.RED); // Red
        }

        // Update enemy health bar color
        double enemyHealthPercentage = (double) enemyCurrentHealth / enemyMaxHealth;

        // Set the color based on the percentage
        if (enemyHealthPercentage >= 0.8) {
            enemyBar.setForeground(new Color(102, 255, 51)); // Green
        } else if (enemyHealthPercentage >= 0.4) {
            enemyBar.setForeground(Color.YELLOW); // Yellow
        } else {
            enemyBar.setForeground(Color.RED); // Red
        }
    }
}
