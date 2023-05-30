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
    private String enemyPokeKalye;
    private int enemyLevel;
    private int enemyMaxHealth;
    private int enemyCurrentHealth;
    private JLabel battleStatusLabel;
    private int pesos;
    private Search search;
    private JLabel enemyImageLabel;
    private List<JButton> moveButtons;
    private JPanel buttonsPanel;

    void showIntroScreen() {
        this.setBackground(Color.BLACK);
        JFrame introFrame = new JFrame();
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        introFrame.setUndecorated(true);
        introFrame.getContentPane().setBackground(new Color(255, 51, 102));
        introFrame.setBackground(new Color(255, 51, 102));
        introFrame.setLayout(new BorderLayout());

        JLabel introLabel = new JLabel("PokeKalye", SwingConstants.CENTER);
        introLabel.setFont(new Font("Courier New", Font.BOLD, 90));
        introLabel.setForeground(Color.black);

        introFrame.add(introLabel, BorderLayout.CENTER);
        introFrame.pack();
        introFrame.setLocationRelativeTo(null);
        introFrame.setVisible(true);

        Timer timer = new Timer(2000, e -> {
            introFrame.dispose();
            enableButtons();
        });
        timer.setRepeats(false);
        timer.start();
    }

    public GamePanel(String playerName, String selectedPokeKalye) {
        search = new Search(this);
        this.playerName = playerName;
        this.selectedPokeKalye = selectedPokeKalye;
        playerLevel = 1;
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
        playerHealthBar = new JProgressBar(0, getMaxHealth(playerLevel));
        playerHealthBar.setValue(10);
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

        enemyLabel = new JLabel("Enemy");
        enemyLabel.setForeground(Color.WHITE);
        enemyHealthBar = new JProgressBar();
        enemyHealthBar.setForeground(new Color(102, 255, 51));

        enemyPanel.setBackground(new Color(255, 51, 102));
        enemyPanel.add(enemyHealthBar);
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

    private int getMaxHealth(int level) {
        if (level >= 5) {
            return 5 + (level - 5) * 20;
        }
        return level * 5;
    }

    public int getLevel() {
        return playerLevel;
    }

    private void showMovesDialog() {
        buttonsPanel.removeAll();
        moveButtons.clear();
        for (JButton button : moveButtons) {
            buttonsPanel.remove(button);
        }
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
                    // implement moves logic in the future
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

    private void healPokeKalye() {
    }

    private void openShopPanel() {
        ShopPanel shopPanel = new ShopPanel();
        shopPanel.setVisible(true);
    }

    public void goBackToMainPanel() {
        ((CardLayout) getParent().getLayout()).show(getParent(), "GamePanel");
    }

    public void setEnemyImage(String enemyPokeKalye) {
        enemyLabel.setText(enemyPokeKalye.toUpperCase());
        enemyHealthBar.setMaximum(getMaxHealth(enemyLevel));
        ImageIcon enemyImage = new ImageIcon("images/" + enemyPokeKalye + "2.png");
        enemyImageLabel.setIcon(enemyImage);
    }

    public void setDialogueText(String dialogue) {
        dialogueArea.setText(dialogue);
    }

    public void displayBattleResult(String result) {
        battleStatusLabel.setText(result);
    }

    public int getEnemyCurrentHealth() {
        return 10;
    }

    public int getPlayerCurrentHealth() {
        return 10;
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
        movesButton.setEnabled(true);
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
}
